package hu.unideb.inf.homeworkproject.view;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ImageManager {
    private ImageView imageView;
    private AnchorPane mainPane;

    public ImageManager(ImageView imageView, AnchorPane mainPane) {
        this.imageView = imageView;
        this.mainPane = mainPane;
    }

    private void printHello() {
        System.out.println("Hello!");
    }

    public void playGif(String path) {
        Image image = new Image(path);
        ImageView imgView = new ImageView(image);
        imgView.setX(0);
        imgView.setY(0);
        this.mainPane.getChildren().add(imgView);
    }
}
