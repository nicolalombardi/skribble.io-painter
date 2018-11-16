package com.nlombardi.scribbleiopainter;


import com.nlombardi.scribbleiopainter.skribbl.Drawing;
import com.nlombardi.scribbleiopainter.skribbl.Palette;
import com.nlombardi.scribbleiopainter.skribbl.PaletteColor;

import java.awt.image.BufferedImage;

class ImageElaboration {
    public static Drawing floydSteinbergDithering(BufferedImage img, Palette palette) {
        int width = img.getWidth();
        int height = img.getHeight();

        EnhColor[][] sourceImage = new EnhColor[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sourceImage[i][j] = new EnhColor(img.getRGB(i, j));
            }
        }

        Drawing drawing = new Drawing(width, height);


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                EnhColor oldColor = sourceImage[x][y];

                PaletteColor newColor = findClosestPaletteColor(oldColor, palette);
                drawing.setPixel(x, y, newColor);

                EnhColor err = oldColor.sub(newColor);

                if (x + 1 < width) {
                    sourceImage[x + 1][y] = sourceImage[x + 1][y].add(err.mul(7. / 16));
                }
                if (x - 1 >= 0 && y + 1 < height) {
                    sourceImage[x - 1][y + 1] = sourceImage[x - 1][y + 1].add(err.mul(3. / 16));
                }
                if (y + 1 < height) {
                    sourceImage[x][y + 1] = sourceImage[x][y + 1].add(err.mul(5. / 16));
                }
                if (x + 1 < width && y + 1 < height) {
                    sourceImage[x + 1][y + 1] = sourceImage[x + 1][y + 1].add(err.mul(1. / 16));
                }
            }
        }

        return drawing;
    }

    private static PaletteColor findClosestPaletteColor(EnhColor c, Palette palette) {
        PaletteColor closest = palette.getColor(0);

        for (PaletteColor pc : palette.getPalette()) {
            if (pc.diff(c) < closest.diff(c) && pc.isToUse()) {
                closest = pc;
            }
        }
        return closest;
    }


}