package com.nlombardi.scribbleiopainter.skribbl;

import com.nlombardi.scribbleiopainter.EnhColor;

public class PaletteColor extends EnhColor {
    private int ID;
    private int positionX, positionY;
    private boolean toUse;

    public PaletteColor(int r, int g, int b) {
        super(r, g, b);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isToUse() {
        return toUse;
    }

    public void setToUse(boolean toUse) {
        this.toUse = toUse;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
