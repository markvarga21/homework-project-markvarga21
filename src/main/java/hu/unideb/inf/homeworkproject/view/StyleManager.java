package hu.unideb.inf.homeworkproject.view;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class StyleManager {
    public static void styleGameBoard(GridPane pane) {
        String style = """
                        -fx-border-width: 2;
                        -fx-border-color: black;
                        -fx-effect: dropshadow(three-pass-box, green, 10, 0, 0, 0);
                        """;
        pane.setStyle(style);
    }
    public static void highlightNode(int playerIndex, Node node, Color player1Color, Color player2Color) {
        switch (playerIndex) {
            case 1 -> {
                Circle player1Circle = (Circle) node;
                player1Circle.setStrokeWidth(3);
                player1Circle.setStroke(player1Color);
            }
            case -1 -> {
                Circle player2Circle = (Circle) node;
                player2Circle.setStrokeWidth(3);
                player2Circle.setStroke(player2Color);
            }
        }
    }

    public static void removeHighlight(Node node) {
        Circle temp = (Circle) node;
        temp.setStrokeWidth(0);
    }

}
