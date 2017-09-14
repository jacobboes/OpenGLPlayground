package com.example.jacobboes.myapplication.shapes;

public class Diamond extends Shape {
    private static float diamondCoords[] = {
            0.0f, 0.25f, 0.0f,      // top
            -0.25f, 0.0f, 0.0f,     // left
            0.25f, 0.0f, 0.0f,      // right
            0.0f, -0.25f, 0.0f};    // bottom

    private float color[] = {0.8f, 0.0f, 0.0f, 1.0f};
    private short drawOrder[] = {0, 1, 2, 1, 2, 3}; // order to draw vertices

    public Diamond() {
        initialize();
    }

    @Override
    protected float[] getShapeCoordinates() {
        return diamondCoords;
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
