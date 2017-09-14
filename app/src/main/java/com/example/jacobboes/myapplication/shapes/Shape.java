package com.example.jacobboes.myapplication.shapes;

import android.opengl.GLES20;
import com.example.jacobboes.myapplication.ARRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public abstract class Shape {
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private static final int COORDS_PER_VERTEX = 3;
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private int program;

    protected abstract float[] getShapeCoordinates();

    protected abstract float[] getColor();

    protected abstract short[] getDrawOrder();

    protected void initialize() {
        initVertexBuffer();
        initDrawListBuffer();
        compileShape();
    }

    private void initVertexBuffer() {
        // initialize vertex byte buffer for shape coordinates

        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                getShapeCoordinates().length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(getShapeCoordinates());
        vertexBuffer.position(0);
    }

    private void initDrawListBuffer() {
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                getDrawOrder().length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(getDrawOrder());
        drawListBuffer.position(0);
    }

    private void compileShape() {
        int vertexShader = ARRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = ARRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        program = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(program, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(program, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(program);                  // create OpenGL program executables
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        // get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the shape vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        int vertexStride = COORDS_PER_VERTEX * 4;
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        int colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the shape
        GLES20.glUniform4fv(colorHandle, 1, getColor(), 0);

        // get handle to shape's transformation matrix
        int mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the shape
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, getDrawOrder().length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
