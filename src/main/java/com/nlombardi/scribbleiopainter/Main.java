package com.nlombardi.scribbleiopainter;

import com.nlombardi.scribbleiopainter.skribbl.Drawing;
import com.nlombardi.scribbleiopainter.skribbl.Palette;
import com.nlombardi.scribbleiopainter.skribbl.PaletteColor;
import com.nlombardi.scribbleiopainter.skribbl.Pixel;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private static Config conf;
    private static Palette palette;
    private static int canvasX, canvasY, brushSize;
    private ImageRetriever imageRetriever = ImageRetriever.getInstance();
    private ImageView previews[];
    private ArrayList<Drawing> lastDrawings;
    private Label status;


    public static void main(String[] args) {
        conf = new Config();
        palette = new Palette();
        String res = conf.getStringProperty("resolution") + "_";
        palette.setParameters(
                conf.getIntProperty(res + "paletteX"),
                conf.getIntProperty(res + "paletteY"),
                conf.getIntProperty(res + "paletteXStep"),
                conf.getIntProperty(res + "paletteYStep"));

        canvasX = conf.getIntProperty(res + "canvasX");
        canvasY = conf.getIntProperty(res + "canvasY");
        brushSize = conf.getIntProperty(res + "brushSize");
        setUpPalette();

        launch(args);
    }

    private static void setUpPalette() {
        palette.addColor(new PaletteColor(255, 255, 255));
        palette.addColor(new PaletteColor(193, 193, 193));
        palette.addColor(new PaletteColor(239, 19, 11));
        palette.addColor(new PaletteColor(255, 113, 0));
        palette.addColor(new PaletteColor(255, 228, 0));
        palette.addColor(new PaletteColor(0, 204, 0));
        palette.addColor(new PaletteColor(0, 178, 255));
        palette.addColor(new PaletteColor(35, 31, 211));
        palette.addColor(new PaletteColor(163, 0, 186));
        palette.addColor(new PaletteColor(211, 124, 170));
        palette.addColor(new PaletteColor(160, 82, 45));
        palette.addColor(new PaletteColor(0, 0, 0));
        palette.addColor(new PaletteColor(76, 76, 76));
        palette.addColor(new PaletteColor(116, 11, 7));
        palette.addColor(new PaletteColor(194, 56, 0));
        palette.addColor(new PaletteColor(232, 162, 0));
        palette.addColor(new PaletteColor(0, 85, 16));
        palette.addColor(new PaletteColor(0, 86, 158));
        palette.addColor(new PaletteColor(14, 8, 101));
        palette.addColor(new PaletteColor(85, 0, 105));
        palette.addColor(new PaletteColor(167, 85, 116));
        palette.addColor(new PaletteColor(99, 48, 13));

        for (int i = 0; i < palette.getColorsCount(); i++) {
            int posY = palette.getY() + palette.getYStep() / 2;
            if (i > 10) {
                posY += palette.getYStep();
            }
            int posX = palette.getX() + palette.getXStep() / 2 + (palette.getXStep() * (i % 11));
            palette.getColor(i).setPositionX(posX);
            palette.getColor(i).setPositionY(posY);
            palette.getColor(i).setID(i);
            palette.getColor(i).setToUse(true);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        lastDrawings = new ArrayList<>();
        VBox rootLayout = new VBox();
        GridPane inputGrid = new GridPane();


        inputGrid.setPadding(new Insets(10, 10, 10, 10));
        inputGrid.setVgap(5);
        inputGrid.setHgap(5);

        GridPane images = new GridPane();
        images.setPadding(new Insets(10, 10, 10, 10));
        images.setVgap(5);
        images.setHgap(5);

        TextField queryField = new TextField();
        queryField.setPrefColumnCount(15);


        Button searchButton = new Button("Search");

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.VERTICAL);


        status = new Label("Not ready");

        CheckBox cartoonChk = new CheckBox();
        cartoonChk.setText("Cartoon suffix");
        CheckBox sketchChk = new CheckBox();
        sketchChk.setText("Sketch suffix");


        GridPane.setConstraints(queryField, 0, 0);
        GridPane.setConstraints(searchButton, 1, 0);
        GridPane.setConstraints(separator1, 2, 0);
        GridPane.setConstraints(status, 3, 0);
        GridPane.setConstraints(separator2, 4, 0);
        GridPane.setConstraints(cartoonChk, 5, 0);
        GridPane.setConstraints(sketchChk, 6, 0);


        inputGrid.getChildren().addAll(queryField, searchButton, separator1, status, separator2, cartoonChk, sketchChk);

        previews = new ImageView[9];

        for (int i = 0; i < previews.length; i++) {
            final int index = i;
            previews[i] = new ImageView();
            previews[i].setFitHeight(90);
            previews[i].setFitWidth(116);
            previews[i].setPreserveRatio(true);
            previews[i].setOnMouseClicked(event -> {
                paintImage(index);
            });
            GridPane.setConstraints(previews[i], i % 3, i / 3);
            images.getChildren().add(previews[i]);
        }


        searchButton.setOnMouseClicked(event -> {
            searchImage(
                    queryField.getCharacters().toString() +
                            (cartoonChk.isSelected() ? " cartoon" : "") +
                            (sketchChk.isSelected() ? " sketch" : "")
            );
            status.setText("Not ready");
        });

        rootLayout.getChildren().addAll(inputGrid, images);

        primaryStage.setTitle("Scribble.io Painter");
        primaryStage.setScene(new Scene(rootLayout, 600, 600));
        primaryStage.show();

    }

    private void searchImage(String query) {
        imageRetriever.getImages(query, new ImageRetrievedResponse() {
            @Override
            public void error(String message) {

            }

            @Override
            public void completed(List<ImageResult> images) {
                lastDrawings.clear();
                for (int i = 0; i < 9; i++) {
                    BufferedImage selectedImage = images.get(i).getResizedImage();

                    Drawing drawing = ImageElaboration.floydSteinbergDithering(selectedImage, palette);


                    lastDrawings.add(i, drawing);

                    for (int x = 0; x < selectedImage.getWidth(); x++) {
                        for (int y = 0; y < selectedImage.getHeight(); y++) {
                            selectedImage.setRGB(x, y, drawing.getPixel(x, y).getColor().toRGB());
                        }
                    }

                    Image image = SwingFXUtils.toFXImage(selectedImage, null);
                    previews[i].setImage(image);

                }
            }
        });
    }

    private void paintImage(int index) {
        Drawing selectedDrawing = lastDrawings.get(index);

        ArrayList<Pixel> ops[] = new ArrayList[palette.getColorsCount()];
        for (int i = 0; i < ops.length; i++) {
            ops[i] = new ArrayList<>();
        }


        for (int i = 0; i < selectedDrawing.getWidth(); i++) {
            for (int j = 0; j < selectedDrawing.getHeight(); j++) {
                ops[selectedDrawing.getPixel(i, j).getColor().getID()].add(selectedDrawing.getPixel(i, j));
            }
        }
        //Remove white since it's already the background of the canvas
        ops[0].clear();

        status.setText("Ready");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Robot bot = null;
                try {
                    bot = new Robot();
                } catch (AWTException e) {
                    e.printStackTrace();
                }

                int lastColorID = -1;

                int moveDelay = conf.getIntProperty("mouse_move_ms");
                int pressDelay = conf.getIntProperty("mouse_press_ms");
                int endDelay = conf.getIntProperty("mouse_end_ms");

                for (int i = 0; i < ops.length; i++) {
                    for (int j = 0; j < ops[i].size(); j++) {
                        int colorX = ops[i].get(j).getColor().getPositionX();
                        int colorY = ops[i].get(j).getColor().getPositionY();

                        if (ops[i].get(j).getColor().getID() != lastColorID) {
                            bot.mouseMove(colorX, colorY);
                            bot.delay(moveDelay);
                            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            bot.delay(pressDelay);
                            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            bot.delay(endDelay);
                            lastColorID = ops[i].get(j).getColor().getID();
                        }


                        bot.mouseMove(canvasX + brushSize / 2 + brushSize * ops[i].get(j).getPositionX(), canvasY + brushSize / 2 + brushSize * ops[i].get(j).getPositionY());
                        bot.delay(moveDelay);
                        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        bot.delay(pressDelay);
                        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        bot.delay(endDelay);

                    }
                }

            }
        }, 3000);


    }
}
