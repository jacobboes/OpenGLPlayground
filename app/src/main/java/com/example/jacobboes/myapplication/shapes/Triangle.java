package com.example.jacobboes.myapplication.shapes;

public class Triangle extends Shape {

    private static float triangleCoords[] = {
            0.0f, 0.622008459f, 0.0f,   // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f   // bottom right
    };
    private float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
    private short drawOrder[] = {0, 1, 2}; // order to draw vertices

    public Triangle() {
        initialize();
    }

    @Override
    protected float[] getShapeCoordinates() {
        return triangleCoords;
    }

    @Override
    protected float[] getColor() {
        return color;
    }

    @Override
    protected short[] getDrawOrder() {
        return drawOrder;
    }
}
