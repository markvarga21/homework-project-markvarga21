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

import java.util.ArrayList;

public class ImageManager {
    private String[][] positionShiftValues;


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
        // FORMAT: nrOfImageViewer (out of the 4),yTranslateValue
        this.positionShiftValues = new String[][] {{"0,445", "1,445", "2,445", "3,445"},
                                                    {"0,375", "1,375", "2,375", "3,375"},
                                                    {"0,305", "1,305", "2,305", "3,305"},
                                                    {"0,235", "1,235", "2,235", "3,235"}};
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

    private TranslateTransition createTranslateTransition(ImageView imageView, Duration duration, double shiftValue) {
        TranslateTransition transition = new TranslateTransition();

        transition.setNode(imageView);
        transition.setDuration(duration);
        transition.setByY(shiftValue);

        return transition;
    }

    private ScaleTransition createScaleTransition(CircleNode node, double scaleValue) {
        ScaleTransition scaleTransition = new ScaleTransition();

        scaleTransition.setNode(node.getNode());
        scaleTransition.setToX(scaleValue);
        scaleTransition.setToY(scaleValue);

        return scaleTransition;
    }

    public void playAnimation(ArrayList<CircleNode> removableNodes, ImageView[] imageViews, GridPane gameBoard) {
        // creating a copy of the removableNodes, bcs the animation suddenly removes them from the ArrayList
        ArrayList<CircleNode> copy = (ArrayList<CircleNode>) removableNodes.clone();
        System.out.println("Removing " + copy.size() + " nodes...");

        // I use this, not to duplicate that much repetitive code
        TranslateTransition[] upTranslateTransitions = new TranslateTransition[copy.size()];
        ScaleTransition[] scaleTransitions = new ScaleTransition[copy.size()];
        TranslateTransition[] downTranslateTransitions = new TranslateTransition[copy.size()];

        // setting up animations
        for (int i = 0; i < copy.size(); i++) {
            var str = this.positionShiftValues[removableNodes.get(i).getRow()][removableNodes.get(i).getColumn()].split(",");
            final int imgViewIndex = Integer.parseInt(str[0]);
            final double shiftValue = Integer.parseInt(str[1]);

            upTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), -shiftValue);
            scaleTransitions[i] = createScaleTransition(removableNodes.get(i), 0);
            downTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), shiftValue);
        }

        // setting up sequential transition
        switch (copy.size()) {
            case 1 -> {
                SequentialTransition sequentialTransition = new SequentialTransition(
                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0]
                );

                sequentialTransition.setOnFinished(event -> {
                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
                        System.out.println("Node removed!");
                });

                // playing the whole animation
                sequentialTransition.play();
            }
            case 2 -> {
                SequentialTransition sequentialTransition = new SequentialTransition(
                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1]
                );

                sequentialTransition.setOnFinished(event -> {
                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
                        System.out.println("Node removed!");
                });

                // playing the whole animation
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
                        System.out.println("Node removed!");
                });

                // playing the whole animation
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
                        System.out.println("Node removed!");
                });

                // playing the whole animation
                sequentialTransition.play();
            }
            default -> System.out.println("Invalid size of removableNodes!");
        }


    }

    // FOR BACKUP ONLY
//    public void playAnimation(ArrayList<CircleNode> removableNodes, ImageView[] imageViews, GridPane gameBoard) {
////        switch (removableNodes.size()) {
////            case 1 -> {
////                // creating a copy of the removableNodes, bcs the animation suddenly removes them from the ArrayList
////                ArrayList<CircleNode> copy = (ArrayList<CircleNode>) removableNodes.clone();
//////                System.out.println("removableNodes before all: " + removableNodes.size());
////                System.out.println("Removing 1 node...");
////
////                // extracting indexes
////                var str = this.positionShiftValues[removableNodes.get(0).getRow()][removableNodes.get(0).getColumn()].split(","); // bcs they start from 0
////                final int imgViewIndex = Integer.parseInt(str[0]);
////                final double shiftValue = Integer.parseInt(str[1]);
////
////                // setting up animation
//////                System.out.println("remsize before animations: " + removableNodes.size());
////                TranslateTransition upTransition = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), -shiftValue);
////
////                ScaleTransition scaleTransition = createScaleTransition(removableNodes.get(0), 0);
//////                System.out.println("remsize after scale animations: " + removableNodes.size());
////
////                TranslateTransition downTransition = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), shiftValue);
//////                System.out.println("remsize after transform animations: " + removableNodes.size());
////
////                // creating sequence transition
////                SequentialTransition transition = new SequentialTransition(upTransition, scaleTransition, downTransition);
////
////                // after playing the animation event
////                transition.setOnFinished(event -> {
////                    if (gameBoard.getChildren().remove(copy.get(0).getNode()))
////                        System.out.println("Node removed!");
////                });
////
////                // playing the animation
////                transition.play();
////
////            }
////            case 2 -> {
////                // creating a copy of the removableNodes, bcs the animation suddenly removes them from the ArrayList
////                ArrayList<CircleNode> copy = (ArrayList<CircleNode>) removableNodes.clone();
////                System.out.println("Removing 2 nodes...");
////
////                // I use this, not to duplicate that much repetitive code
////                TranslateTransition[] upTranslateTransitions = new TranslateTransition[2];
////                ScaleTransition[] scaleTransitions = new ScaleTransition[2];
////                TranslateTransition[] downTranslateTransitions = new TranslateTransition[2];
////
////                // setting up animations
////                for (int i = 0; i < 2; i++) {
////                    var str = this.positionShiftValues[removableNodes.get(i).getRow()][removableNodes.get(i).getColumn()].split(",");
////                    final int imgViewIndex = Integer.parseInt(str[0]);
////                    final double shiftValue = Integer.parseInt(str[1]);
////
////                    upTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), -shiftValue);
////                    scaleTransitions[i] = createScaleTransition(removableNodes.get(i), 0);
////                    downTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), shiftValue);
////                }
////
////                // setting up sequential transition
////                SequentialTransition sequentialTransition = new SequentialTransition(
////                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
////                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1]
////                );
////
////                // removing nodes after the animation finishes
////                sequentialTransition.setOnFinished(event -> {
////                    for (var node : copy) {
////                        if (gameBoard.getChildren().remove(node.getNode()))
////                            System.out.println("Node removed!");
////                    }
////                });
////
////                // playing the whole animation
////                sequentialTransition.play();
////            }
////            case 3 -> {
////                ArrayList<CircleNode> copy = (ArrayList<CircleNode>) removableNodes.clone();
////                System.out.println("Removing 3 nodes...");
////                // creating a copy of the removableNodes, bcs the animation suddenly removes them from the ArrayList
////
////                // I use this, not to duplicate that much repetitive code
////                TranslateTransition[] upTranslateTransitions = new TranslateTransition[3];
////                ScaleTransition[] scaleTransitions = new ScaleTransition[3];
////                TranslateTransition[] downTranslateTransitions = new TranslateTransition[3];
////
////                // setting up animations
////                for (int i = 0; i < 3; i++) {
////                    var str = this.positionShiftValues[removableNodes.get(i).getRow()][removableNodes.get(i).getColumn()].split(",");
////                    final int imgViewIndex = Integer.parseInt(str[0]);
////                    final double shiftValue = Integer.parseInt(str[1]);
////
////                    upTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), -shiftValue);
////                    scaleTransitions[i] = createScaleTransition(removableNodes.get(i), 0);
////                    downTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), shiftValue);
////                }
////
////                // setting up sequential transition
////                SequentialTransition sequentialTransition = new SequentialTransition(
////                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
////                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1],
////                        upTranslateTransitions[2], scaleTransitions[2], downTranslateTransitions[2]
////                );
////
////                // removing nodes after the animation finishes
////                sequentialTransition.setOnFinished(event -> {
////                    for (var node : copy) {
////                        if (gameBoard.getChildren().remove(node.getNode()))
////                            System.out.println("Node removed!");
////                    }
////                });
////
////                // playing the whole animation
////                sequentialTransition.play();
////            }
////            case 4 -> {
////                // creating a copy of the removableNodes, bcs the animation suddenly removes them from the ArrayList
////                ArrayList<CircleNode> copy = (ArrayList<CircleNode>) removableNodes.clone();
////                System.out.println("Removing 4 nodes...");
////
////                // I use this, not to duplicate that much repetitive code
////                TranslateTransition[] upTranslateTransitions = new TranslateTransition[4];
////                ScaleTransition[] scaleTransitions = new ScaleTransition[4];
////                TranslateTransition[] downTranslateTransitions = new TranslateTransition[4];
////
////                // setting up animations
////                for (int i = 0; i < 4; i++) {
////                    var str = this.positionShiftValues[removableNodes.get(i).getRow()][removableNodes.get(i).getColumn()].split(",");
////                    final int imgViewIndex = Integer.parseInt(str[0]);
////                    final double shiftValue = Integer.parseInt(str[1]);
////
////                    upTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), -shiftValue);
////                    scaleTransitions[i] = createScaleTransition(removableNodes.get(i), 0);
////                    downTranslateTransitions[i] = createTranslateTransition(imageViews[imgViewIndex], Duration.seconds(1), shiftValue);
////                }
////
////                // setting up sequential transition
////                SequentialTransition sequentialTransition = new SequentialTransition(
////                        upTranslateTransitions[0], scaleTransitions[0], downTranslateTransitions[0],
////                        upTranslateTransitions[1], scaleTransitions[1], downTranslateTransitions[1],
////                        upTranslateTransitions[2], scaleTransitions[2], downTranslateTransitions[2],
////                        upTranslateTransitions[3], scaleTransitions[3], downTranslateTransitions[3]
////                );
////
////                // removing nodes after the animation finishes
////                sequentialTransition.setOnFinished(event -> {
////                    for (var node : copy) {
////                        if (gameBoard.getChildren().remove(node.getNode()))
////                            System.out.println("Node removed!");
////                    }
////                });
////
////                // playing the whole animation
////                sequentialTransition.play();
////            }
////            default -> System.out.println("Something went wrong when removing the nodes!");
////        }
//
////        var str = this.positionShiftValues[node.getRow()][node.getColumn()].split(","); // bcs they start from 0
////        final int imgViewIndex = Integer.parseInt(str[0]);
////        final double shiftValue = Integer.parseInt(str[1]);
////
////        TranslateTransition transition1= new TranslateTransition();
////        transition1.setNode(imageViews[imgViewIndex]);
////        transition1.setDuration(Duration.seconds(1));
////        transition1.setByY(-shiftValue);
////
////        ScaleTransition scaleTransition = new ScaleTransition();
////        scaleTransition.setNode(node.getNode());
////        scaleTransition.setToX(0f);
////        scaleTransition.setToY(0f);
////
////        TranslateTransition transition2= new TranslateTransition();
////        transition2.setNode(imageViews[imgViewIndex]);
////        transition2.setDuration(Duration.seconds(1));
////        transition2.setByY(shiftValue);
////        transition2.play();
////
////        SequentialTransition transition = new SequentialTransition(transition1, scaleTransition, transition2);
////        transition.setOnFinished(event -> {
////            if (gameBoard.getChildren().remove(node.getNode()))
////                System.out.println("Node removed!");
////        });
////        transition.play();
//    }

}
