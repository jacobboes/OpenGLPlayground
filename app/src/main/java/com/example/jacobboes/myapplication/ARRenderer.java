package com.example.jacobboes.myapplication;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import com.example.jacobboes.myapplication.shapes.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.List;

public class ARRenderer implements GLSurfaceView.Renderer {
    private List<Square> squares;

    // mvpMatrix is an abbreviation for "Model View Projection Matrix"
    private float[] mvpMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];

    private float[] modelMatrix = new float[16];
    private float[] rotationMatrix = new float[16];
    private float[] tempMatrix = new float[16];


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        squares = new ArrayList<>();
        squares.add(new Square(0, 0.05f, Color.BLUE));
        squares.add(new Square(0, 0.0f, Color.RED));
        squares.add(new Square(0, -0.05f, Color.GREEN));
        squares.add(new Square(0.05f, 0, Color.BLUE));
        squares.add(new Square(-0.05f, 0, Color.GREEN));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        for (Square square : squares) {
            float[] mvp = translateMatrix(square.getX(), square.getY());
            square.draw(mvp);
        }
    }

    private float[] translateMatrix(float x, float y) {

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, 0);
        // Create a rotation transformation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);
//
//        // Combine Rotation and Translation matrices
//        tempMatrix = modelMatrix.clone();
//        Matrix.multiplyMM(modelMatrix, 0, tempMatrix, 0, rotationMatrix, 0);
        // Combine the model matrix with the projection and camera view
        tempMatrix = mvpMatrix.clone();
        float[] mvp = new float[16];
        Matrix.multiplyMM(mvp, 0, tempMatrix, 0, modelMatrix, 0);
        return mvp;
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
