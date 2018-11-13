package com.nlombardi.scribbleiopainter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ImageRetriever {
    public static ImageRetriever instance;

    private static final String cx = "004830798009892852221:yb8c_a89hlu";
    private static final String apiKey = "AIzaSyBBao5bsFTvvHkrQlEH6jkEFYI1RYQup3I";

    protected ImageRetriever() {

    }

    public static ImageRetriever getInstance() {
        if (instance == null) {
            instance = new ImageRetriever();
        }
        return instance;
    }

    /**
     * Get a List of ImageResults containing the first 3 results on Google Image for a set query
     *
     * @param query The search query
     */
    public void getImages(String query, ImageRetrievedResponse callback) {
        Unirest.get("https://www.googleapis.com/customsearch/v1")
                .queryString("key", apiKey)
                .queryString("cx", cx)
                .queryString("q", query)
                .queryString("num", 3)
                .queryString("imgSize", "medium")
                .queryString("searchType", "image")

                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        List<ImageResult> imageResults = new ArrayList<>();
                        JSONArray items = httpResponse.getBody().getObject().getJSONArray("items");
                        for (int i = 0; i < items.length(); i++) {
                            imageResults.add(new ImageResult(items.getJSONObject(i)));
                        }
                        callback.completed(imageResults);
                    }

                    @Override
                    public void failed(UnirestException e) {
                        callback.error(e.getMessage());
                    }

                    @Override
                    public void cancelled() {
                        callback.error(null);
                    }
                });
    }


}
