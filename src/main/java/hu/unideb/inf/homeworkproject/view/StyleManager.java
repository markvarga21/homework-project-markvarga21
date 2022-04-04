package hu.unideb.inf.homeworkproject.view;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class StyleManager {
    public static void styleGameBoard(GridPane pane) {
        String style = """
                        -fx-border-width: 2;
                        -fx-border-color: black;
                        -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);
                        """;
        pane.setStyle(style);
    }

    public static void highlightNode(int playerIndex, Node node, Color player1Color, Color player2Color) {
        switch (playerIndex) {
            case 1 -> {
                Circle player1Circle = (Circle) node;
                player1Circle.setStrokeWidth(2);
                player1Circle.setStroke(player1Color);
//                player1Circle.setEffect(new DropShadow(20, Color.rgb(255, 0, 0, 0.5)));
            }
            case -1 -> {
                Circle player2Circle = (Circle) node;
                player2Circle.setStrokeWidth(2);
                player2Circle.setStroke(player2Color);
//                player2Circle.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.5)));
            }
        }
    }

    public static void removeHighlight(Node node) {
        Circle temp = (Circle) node;
        temp.setStrokeWidth(0);
    }

    public static void hoverStyle(Node node) {
        System.out.println("Hovered!");
    }
}
