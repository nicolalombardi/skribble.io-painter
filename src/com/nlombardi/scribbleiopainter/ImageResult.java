package com.nlombardi.scribbleiopainter;

import com.sun.javafx.fxml.builder.URLBuilder;
import com.sun.javafx.iio.ImageStorage;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;

public class ImageResult {
    private String title;
    private int byteSize;

    private int width, height;
    private String link;

    private int thumbWidth, thumbHeight;
    private String thumbLink;

    public ImageResult(JSONObject obj){
        JSONObject image = obj.getJSONObject("image");

        this.title = obj.getString("title");
        this.byteSize = image.getInt("byteSize");
        this.width = image.getInt("width");
        this.height = image.getInt("height");
        this.link = obj.getString("link");
        this.thumbWidth = image.getInt("thumbnailWidth");
        this.thumbHeight = image.getInt("thumbnailHeight");
        this.thumbLink= image.getString("thumbnailLink");
    }

    public String getTitle() {
        return title;
    }

    public int getByteSize() {
        return byteSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getLink() {
        return link;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public int getThumbHeight() {
        return thumbHeight;
    }

    public String getThumbLink() {
        return thumbLink;
    }

    public BufferedImage getResizedImage(){
        int resizedWidth = 116;
        int resizedHeight = (int) Math.min((116.0f/getWidth()) * height, 90);
        BufferedImage resized = null;
        try {
            BufferedImage image = ImageIO.read(new URL(getLink()));
            resized = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g = resized.createGraphics();
            g.drawImage(image, 0, 0, resizedWidth, resizedHeight, null);
            g.dispose();
            return resized;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resized;
    }

    @Override
    public String toString() {
        return "ImageResult{" +
                "title='" + title + '\'' +
                ", byteSize=" + byteSize +
                ", width=" + width +
                ", height=" + height +
                ", link='" + link + '\'' +
                ", thumbWidth=" + thumbWidth +
                ", thumbHeight=" + thumbHeight +
                ", thumbLink='" + thumbLink + '\'' +
                '}';
    }
}
