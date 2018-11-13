package com.nlombardi.scribbleiopainter;

import org.json.JSONObject;

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
