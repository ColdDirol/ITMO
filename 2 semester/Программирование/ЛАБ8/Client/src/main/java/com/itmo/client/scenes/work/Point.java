package com.itmo.client.scenes.work;

import javafx.scene.paint.Color;

public class Point {
    private Double x;
    private Double y;
    private Color color;

    public Point(Double x, Double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }


    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
