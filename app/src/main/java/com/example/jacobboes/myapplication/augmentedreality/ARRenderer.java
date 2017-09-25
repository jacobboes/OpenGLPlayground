package com.example.jacobboes.myapplication.augmentedreality;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import com.example.jacobboes.myapplication.R;
import com.example.jacobboes.myapplication.common.RawResourceReader;
import com.example.jacobboes.myapplication.common.ShaderHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class ARRenderer implements GLSurfaceView.Renderer {
    private static final int POSITION_OFFSET = 0;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_DATA_SIZE = 3;
    private static final int STRIDE_BYTES = 7 * BYTES_PER_FLOAT;
    private static final int COLOR_OFFSET = 0;
    private static final int COLOR_DATA_SIZE = 4;
    private static final int BYTE_PER_SHORT = 2;

    private final FloatBuffer triangleVertices;
    private final FloatBuffer colorBuffer;
    private final ShortBuffer indexBuffer;

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private float[] mvpMatrix = new float[16];

    private int programHandle;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int colorHandle;

    private Context context;

    public ARRenderer(Context context) {
        this.context = context;
        final float[] triangleVerticesData = {
                // X, Y, Z,
                // R, G, B, A
                0.0f, 0.809016994f, 0.0f,
                -0.5f, 0.0f, 0.0f,
                0.5f, 0.0f, 0.0f,
                0.0f, -0.809016994f, 0.0f};

        final float[] colorData = {};

        final short[] indexData = {0, 1, 2, 2, 1, 3};


        triangleVertices = ByteBuffer.allocateDirect(triangleVerticesData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        triangleVertices.put(triangleVerticesData).position(0);

        colorBuffer = ByteBuffer.allocateDirect(colorData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colorData).position(0);

        indexBuffer = ByteBuffer.allocateDirect(indexData.length * BYTE_PER_SHORT)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indexData).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background clear color to gray.
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 1.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        final String vertexShader = RawResourceReader.readTextFileFromRawResource(context, R.raw.color_vertex_shader);
        final String fragmentShader = RawResourceReader.readTextFileFromRawResource(context, R.raw.color_fragment_shader);

        final int vertexShaderHandle = ShaderHelper.compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = ShaderHelper.compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        programHandle = ShaderHelper.createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[]{"a_Position", "a_Color"});

        mvpMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        positionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");

        GLES20.glUseProgram(programHandle);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 1000.0f;

        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        // Draw the triangle facing straight on.
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0.0f, 0.0f, 0.0f);
        drawTriangle(triangleVertices, colorBuffer, indexBuffer);
    }

    /**
     * Draws a triangle from the given vertex data.
     */
    private void drawTriangle(final FloatBuffer vertexBuffer, final FloatBuffer colorBuffer, final ShortBuffer indexBuffer) {
        // Pass in the position information
//        vertexBuffer.position(POSITION_OFFSET);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false,
                POSITION_DATA_SIZE * BYTES_PER_FLOAT, vertexBuffer);


        // Pass in the color information
//        colorBuffer.position(COLOR_OFFSET);
        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glVertexAttribPointer(colorHandle, COLOR_DATA_SIZE, GLES20.GL_FLOAT, false,
                COLOR_DATA_SIZE * BYTES_PER_FLOAT, colorBuffer);


        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
    }
}
