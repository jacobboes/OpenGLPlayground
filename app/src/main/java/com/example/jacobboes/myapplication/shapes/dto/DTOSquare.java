package com.example.jacobboes.myapplication.shapes.dto;

public class DTOSquare {
    private float x;
    private float y;
    private float[] color;

    public DTOSquare(float x, float y, float[] color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float[] getColor() {
        return color;
    }
}
