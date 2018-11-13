package com.nlombardi.scribbleiopainter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ImageRetriever {
    public static ImageRetriever instance;

    private static final String cx = "004830798009892852221:yb8c_a89hlu";
    private static final String apiKey = "AIzaSyBBao5bsFTvvHkrQlEH6jkEFYI1RYQup3I";

    protected ImageRetriever(){

    }

    public static ImageRetriever getInstance(){
        if(instance == null){
            instance = new ImageRetriever();
        }
        return instance;
    }

    /**
     * Get a List of ImageResults containing the first 3 results on Google Image for a set query
     * @param query The search query
     * @return List of ImageResult
     */
    public List<ImageResult> getImages(String query){
        List<ImageResult> imageResults = new ArrayList<>();

        try {
            HttpResponse<JsonNode> res =
            Unirest.get("https://www.googleapis.com/customsearch/v1")
                    .queryString("key", apiKey)
                    .queryString("cx", cx)
                    .queryString("q", query)
                    .queryString("num", 3)
                    .queryString("searchType", "image")
                    .asJson();

            JSONArray items = res.getBody().getObject().getJSONArray("items");
            for(int i = 0; i < items.length(); i++){
                imageResults.add(new ImageResult(items.getJSONObject(i)));
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return imageResults;
    }



}
