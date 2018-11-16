package com.nlombardi.scribbleiopainter.skribbl;

import java.util.ArrayList;

public class Palette {
    private ArrayList<PaletteColor> palette;
    private int xStep;
    private int yStep;
    private int x;
    private int y;

    public Palette() {
        this(new ArrayList<>(), 0, 0, 0, 0);
    }

    public Palette(ArrayList<PaletteColor> palette) {
        this(palette, 0, 0, 0, 0);
    }

    private Palette(ArrayList<PaletteColor> palette, int x, int y, int xStep, int yStep) {
        this.palette = palette;
        setParameters(x, y, xStep, yStep);
    }

    public void addColor(PaletteColor pc) {
        this.palette.add(pc);
    }

    public void setParameters(int paletteX, int paletteY, int paletteXStep, int paletteYStep) {
        this.x = paletteX;
        this.y = paletteY;
        this.xStep = paletteXStep;
        this.yStep = paletteYStep;
    }

    public ArrayList<PaletteColor> getPalette() {
        return palette;
    }

    public int getXStep() {
        return xStep;
    }

    public int getYStep() {
        return yStep;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColorsCount() {
        return palette.size();
    }

    public PaletteColor getColor(int index) {
        return palette.get(index);
    }
}

