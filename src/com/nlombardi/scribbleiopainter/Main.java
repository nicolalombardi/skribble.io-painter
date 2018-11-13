package com.nlombardi.scribbleiopainter;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    ImageRetriever imageRetriever = ImageRetriever.getInstance();
    ImageView previews[];
    List<ImageResult> lastImages = new ArrayList<>();

    ArrayList<Color> colorPalette = new ArrayList<>();

    TextField multiR;
    TextField multiG;
    TextField multiB;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setColorPalette();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        TextField queryField = new TextField();
        queryField.setPrefColumnCount(15);
        multiR = new TextField();
        multiG = new TextField();
        multiB = new TextField();
        GridPane.setConstraints(multiR, 0, 2);
        GridPane.setConstraints(multiG, 1, 2);
        GridPane.setConstraints(multiB, 2, 2);

        grid.getChildren().addAll(multiR, multiG, multiB);

        Button searchButton = new Button("Search");
        GridPane.setConstraints(queryField, 0, 0);
        GridPane.setConstraints(searchButton, 1, 0);
        grid.getChildren().addAll(queryField, searchButton);

        previews = new ImageView[3];

        for (int i = 0; i < 3; i++) {
            final int index = i;
            previews[i] = new ImageView();
            previews[i].setFitHeight(90);
            previews[i].setFitWidth(116);
            previews[i].setPreserveRatio(true);
            previews[i].setOnMouseClicked(event -> {
                elaborateImage(index);
            });
            GridPane.setConstraints(previews[i], i, 1);
            grid.getChildren().add(previews[i]);
        }


        searchButton.setOnMouseClicked(event -> {
            searchImage(queryField.getCharacters().toString());
        });


        primaryStage.setTitle("Scribble.io Painter");
        primaryStage.setScene(new Scene(grid, 640, 480));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    private void searchImage(String query) {
        imageRetriever.getImages(query, new ImageRetrievedResponse() {
            @Override
            public void error(String message) {

            }

            @Override
            public void completed(List<ImageResult> images) {
                lastImages = images;
                for (int i = 0; i < 3; i++) {
                    previews[i].setImage(new Image(images.get(i).getLink()));
                    System.out.println(images.get(i).toString());
                }
            }
        });
    }

    private void elaborateImage(int index){
        BufferedImage selectedImage = lastImages.get(index).getResizedImage();
        for (int i = 0; i < selectedImage.getWidth(); i++) {
            for (int j = 0; j < selectedImage.getHeight(); j++) {
                Color c = new Color(selectedImage.getRGB(i, j));
                double minColorDistance = colorDistance(c, colorPalette.get(0));
                int minColorDistanceIndex = 0;
                for (int k = 1; k < colorPalette.size(); k++) {
                    double newColorDistance = colorDistance(c, colorPalette.get(k));
                    if(newColorDistance < minColorDistance){
                        minColorDistance = newColorDistance;
                        minColorDistanceIndex = k;
                    }

                }
                selectedImage.setRGB(i, j, colorPalette.get(minColorDistanceIndex).getRGB());
            }

        }

        Image image = SwingFXUtils.toFXImage(selectedImage, null);
        previews[index].setImage(image);

    }

    double colorDistance(Color c1, Color c2){
        float hsv1[] = new float[3];
        float hsv2[] = new float[3];

        Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), hsv1);
        Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), hsv2);

        double h1 = hsv1[0] * 360;
        double h2 = hsv2[0] * 360;


        System.out.println("Palette: " + h2);
        return Math.min(Math.abs(h1 - h2), 360-Math.abs(h1 - h2));
    }

    private void setColorPalette(){
        colorPalette.add(new Color(0, 0, 0));
        colorPalette.add(new Color(255, 255, 255));
        colorPalette.add(new Color(193, 193, 193));
        colorPalette.add(new Color(76, 76, 76));
        colorPalette.add(new Color(239, 19, 11));
        colorPalette.add(new Color(116, 11, 7));
        colorPalette.add(new Color(255, 113, 0));
        colorPalette.add(new Color(194, 56, 0));
        colorPalette.add(new Color(255, 228, 0));
        colorPalette.add(new Color(232, 162, 0));
        colorPalette.add(new Color(0, 204, 0));
        colorPalette.add(new Color(0, 85, 16));
        colorPalette.add(new Color(0, 178, 255));
        colorPalette.add(new Color(35, 31, 211));
        colorPalette.add(new Color(163, 0, 186));
        colorPalette.add(new Color(85, 0, 105));
        colorPalette.add(new Color(211, 124, 170));
        colorPalette.add(new Color(167, 85, 116));
        colorPalette.add(new Color(160, 82, 45));
        colorPalette.add(new Color(99, 48, 13));
    }
}
