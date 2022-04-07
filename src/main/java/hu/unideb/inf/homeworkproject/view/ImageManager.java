package hu.unideb.inf.homeworkproject.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ImageManager {
    /**
     * The {@code AnchorPane} (this is also the main pane)
     * to which we are attaching
     * the images/gifs.
     */
    private final AnchorPane mainPane;

    /**
     * Constructor for {@code ImageManager} class, which
     * operates on images and gifs.
     * @param pane the {@code AnchorPane} to which we
     * will attach the graphical content.
     */
    public ImageManager(final AnchorPane pane) {
        this.mainPane = pane;
    }

    /**
     * Displays a custom graphical content in a certain
     * point on the main {@code AnchorPane}.
     * @param path the path to the graphical content.
     * @param posX the X position where we want to add the graphical content.
     * @param posY the Y position where we want to add the graphical content.
     */
    public void playGif(final String path, final int posX, final int posY) {
        Image image = new Image(path);
        ImageView imgView = new ImageView(image);
        imgView.setX(posX);
        imgView.setY(posY);
        this.mainPane.getChildren().add(imgView);
    }
}
