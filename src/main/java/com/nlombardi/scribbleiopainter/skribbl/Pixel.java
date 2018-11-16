package com.nlombardi.scribbleiopainter.skribbl;

public class Pixel {
    private PaletteColor color;
    private int positionX, positionY;

    Pixel(PaletteColor color, int x, int y) {
        this.color = color;
        this.positionX = x;
        this.positionY = y;
    }

    public PaletteColor getColor() {
        return color;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
