package com.example.jacobboes.myapplication.shapes;


import com.example.jacobboes.myapplication.shapes.dto.DTOSquare;

public class Square extends Shape {
    private static float width = 0.1f;
    private static float height = 0.1f;

    private float[] color;
    private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices
    private float x;
    private float y;

    public Square(float x, float y, float[] color) {
        this.x = x;
        this.y = y;
        this.color = color;
        initialize();
    }

    public Square(DTOSquare square) {
        this(square.getX(),square.getY(),square.getColor());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    protected float[] getShapeCoordinates() {
        float[] squareCoords = {
                (-width / 2) + x, (height / 2) + y, 0.0f,   // top left
                (-width / 2) + x, (-height / 2) + y, 0.0f,  // bottom left
                (width / 2) + x, (-height / 2) + y, 0.0f,   // bottom right
                (width / 2) + x, (height / 2) + y, 0.0f};   // top right;

        return squareCoords;
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
