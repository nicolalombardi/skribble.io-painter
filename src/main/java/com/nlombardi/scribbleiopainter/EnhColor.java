package com.nlombardi.scribbleiopainter;


public class EnhColor {
    private int red, green, blue;

    public EnhColor(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    EnhColor(int rgb) {
        java.awt.Color temp = new java.awt.Color(rgb);
        this.red = temp.getRed();
        this.green = temp.getGreen();
        this.blue = temp.getBlue();
    }

    EnhColor add(EnhColor o) {
        return new EnhColor(this.getRed() + o.getRed(), this.getGreen() + o.getGreen(), this.getBlue() + o.getBlue());
    }

    EnhColor sub(EnhColor o) {
        return new EnhColor(getRed() - o.getRed(), getGreen() - o.getGreen(), getBlue() - o.getBlue());
    }

    EnhColor mul(double d) {
        return new EnhColor((int) (d * this.getRed()), (int) (d * this.getGreen()), (int) (d * this.getBlue()));
    }

    public int diff(EnhColor o) {
        return Math.abs(this.getRed() - o.getRed()) + Math.abs(this.getGreen() - o.getGreen()) + Math.abs(this.getBlue() - o.getBlue());
    }

    public int toRGB() {
        EnhColor trueColor = toColor();
        java.awt.Color temp = new java.awt.Color(trueColor.getRed(), trueColor.getGreen(), trueColor.getBlue());
        return temp.getRGB();
    }

    private EnhColor toColor() {
        return new EnhColor(clamp(this.getRed()), clamp(this.getGreen()), clamp(this.getBlue()));
    }

    private int clamp(int c) {
        return Math.max(0, Math.min(255, c));
    }

    private int getRed() {
        return red;
    }


    private int getGreen() {
        return green;
    }


    private int getBlue() {
        return blue;
    }


    @Override
    public String toString() {
        return "[" + getRed() + ", " + getGreen() + ", " + getBlue() + "]";
    }
}
