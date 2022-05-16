package hu.unideb.inf.homeworkproject.view;

import hu.unideb.inf.homeworkproject.model.CircleNode;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * A class which is used to display and animate images.
 */
public class ImageManager {
    /**
     * A {@code String} array representing the amounts each {@code ImageView}
     * has to move, in order to fully display the animation.
     * The format is {@code indexOfImageViewer(out of the 4),yTranslateValue}.
     */
    private final String[][] positionShiftValues =
            new String[][] {{"0,445", "1,445", "2,445", "3,445"},
                            {"0,375", "1,375", "2,375", "3,375"},
                            {"0,305", "1,305", "2,305", "3,305"},
                            {"0,235", "1,235", "2,235", "3,235"}};

    /**
     * Logger for {@code ImageManager} class to display useful
     * information while the program is running.
     */
    final static Logger imageManagerLogger = LogManager.getLogger();

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

    /**
     * Creates and returns a {@code Translate} transition for a certain {@code ImageView},
     * in a specific shift value, and duration provided.
     * @param imageView the {@code ImageView} which we want to translate.
     * @param duration the duration of the translation animation.
     * @param shiftValue the number representing how much we want to shift the {@code ImageView}.
     * @return a {@code TranslateTransition} of the {@code ImageView}.
     */
    private TranslateTransition createTranslateTransition(ImageView imageView, Duration duration, double shiftValue) {
        TranslateTransition transition = new TranslateTransition();

        transition.setNode(imageView);
        transition.setDuration(duration);
        transition.setByY(shiftValue);

        return transition;
    }

    /**
     * This scales down a provided {@code Node}, to be able to finish the animation
     * before trully removing the node. This way the user does not see the node
     * removed, but it is still there but with a scale of {@code scaleValue}.
     * @param node the {@code Node} we want to scale down.
     * @param scaleValue the amount to which we want to scale down the {@code Node}.
     * @return a {@code ScaleTransition} where the provided {@code Node} scales down
     * to a specific scale value.
     */
    private ScaleTransition createScaleTransition(CircleNode node, double scaleValue) {
        ScaleTransition scaleTransition = new ScaleTransition();

        scaleTransition.setNode(node.getNode());
        scaleTransition.setToX(scaleValue);
        scaleTransition.setToY(scaleValue);

        return scaleTransition;
    }

    /**
     * This method plays the whole animation, including the two {@code TranslateTransition} and the
     * {@code ScaleTransition}.
     * It creates a copy of the {@code removableNodes}, because the animation
     * removes them from the {@code ArrayList}.
     * @param removableNodes the container for the {@code Node}s we want to be removed.
     * @param imageViews the {@code ImageView}s for the hands.
     * @param gameBoard the main {@code GameBoard} on which are the {@code Node}s.
     */
    public void playAnimation(ArrayList<CircleNode> removableNodes, ImageView[] imageViews, GridPane gameBoard) {
        ArrayList<CircleNode> copy = (ArrayList<CircleNode>) removableNodes.clone();
        imageManagerLogger.debug("Removing {} nodes...", copy.size());

        // I use this, not to duplicate that much repetitive code
        TranslateTransition[] upTranslateTransitions = new TranslateTransition[copy.size()];
        ScaleTransition[] scaleTransitions = new ScaleTransition[copy.size()];
        TranslateTransition[] downTranslateTransitions = new TranslateTransition[copy.size()];

        for (int i = 0; i < copy.size(); i++) {
            var str = this.positionShiftValues[removableNodes.get(i).getRow()][removableNodes.get(i).getColumn()].split(",");
            final int imgViewIndex = Integer.parseInt(str[0]);
            final double shiftValue = Integer.parseInt(str[1]);

            upTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), -shiftValue);
            scaleTransitions[i] = createScaleTransition(removableNodes.get(i), 0);
            downTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), shiftValue);
        }

        switch (copy.size()) {
            case 1 -> {
                SequentialTransition sequentialTransition = new SequentialTransition(
                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0]
                );

                sequentialTransition.setOnFinished(event -> {
                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
                        imageManagerLogger.info("Node removed!");
                });

                sequentialTransition.play();
            }
            case 2 -> {
                SequentialTransition sequentialTransition = new SequentialTransition(
                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1]
                );

                sequentialTransition.setOnFinished(event -> {
                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
                        imageManagerLogger.debug("Node removed!");
                });

                sequentialTransition.play();
            }
            case 3 -> {
                SequentialTransition sequentialTransition = new SequentialTransition(
                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1],
                        upTranslateTransitions[2], scaleTransitions[2], downTranslateTransitions[2]
                );

                sequentialTransition.setOnFinished(event -> {
                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
                        imageManagerLogger.debug("Node removed!");
                });

                sequentialTransition.play();
            }
            case 4 -> {
                SequentialTransition sequentialTransition = new SequentialTransition(
                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1],
                        upTranslateTransitions[2], scaleTransitions[2], downTranslateTransitions[2],
                        upTranslateTransitions[3], scaleTransitions[3], downTranslateTransitions[3]
                );

                sequentialTransition.setOnFinished(event -> {
                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
                        imageManagerLogger.debug("Node removed!");
                });

                sequentialTransition.play();
            }
            default -> imageManagerLogger.error("Invalid size of removableNodes!");
        }
    }
}
