package com.nlombardi.scribbleiopainter.skribbl;

public class Drawing {
    private Pixel canvas[][];
    private int width, height;

    public Drawing(int width, int height) {
        this.canvas = new Pixel[width][height];
        this.width = width;
        this.height = height;
    }

    public Pixel getPixel(int x, int y) {
        return canvas[x][y];
    }

    public void setPixel(int x, int y, Pixel color) {
        this.canvas[x][y] = color;
    }

    public void setPixel(int x, int y, PaletteColor pc) {
        this.canvas[x][y] = new Pixel(pc, x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                s += getPixel(i, j).toString() + ", ";
            }
            s += "\n";
        }
        return s;
    }
}
