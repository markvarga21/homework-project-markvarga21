package hu.unideb.inf.homeworkproject.view;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class managing the styling of the application.
 */
public final class StyleManager {
    /**
     * The width of the highlight stroke.
     */
    private static final int HIGHLIGHT_STROKE_WIDTH = 3;

    private static final Logger styleManagerLogger = LogManager.getLogger();

    /**
     * Private constructor, because it cannot be initialized.
     */
    private StyleManager() {
        throw new UnsupportedOperationException("Utility class!");
    }

    /**
     * A method which applies a specific style
     * to the passed {@code GridPane}.
     * @param pane the {@code GridPane} which we want to get styled.
     */
    public static void styleGameBoard(final GridPane pane) {
        @SuppressWarnings("CheckStyle") String style = """
                        -fx-border-width: 2;
                        -fx-border-color: black;
                        -fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);
                        """;
        pane.setStyle(style);
    }

    /**
     * A method which highlights a node clicked by a player.
     * @param playerIndex to identify which players color
     * to use for highlighting.
     * @param node the {@code Node} we want to highlight.
     * @param player1Color player1's {@code Color}.
     * @param player2Color player2's {@code Color}.
     */
    @SuppressWarnings("CheckStyle")
    public static void highlightNode(final int playerIndex, final Node node, final Color player1Color, final Color player2Color) {
        switch (playerIndex) {
            case 1 -> {
                Circle player1Circle = (Circle) node;
                player1Circle.setStrokeWidth(HIGHLIGHT_STROKE_WIDTH);
                player1Circle.setStroke(player1Color);
            }
            case -1 -> {
                Circle player2Circle = (Circle) node;
                player2Circle.setStrokeWidth(HIGHLIGHT_STROKE_WIDTH);
                player2Circle.setStroke(player2Color);
            }
            default -> styleManagerLogger.error("Something went wrong!");
        }
    }

    /**
     * Removes  the highlight from a specific {@code Node}.
     * @param node the {@code Node} from which we want to
     * remove the highlighting.
     */
    public static void removeHighlight(final Node node) {
        Circle temp = (Circle) node;
        temp.setStrokeWidth(0);
    }

    /**
     * Applies a hover style to a {@code Node}.
     * NOT USED YET
     * @param node the {@code Node} which is hovered.
     */
    public static void hoverStyle(final Node node) {
        styleManagerLogger.info("Hovered!");
    }
}
