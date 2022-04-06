package hu.unideb.inf.homeworkproject.model;

import hu.unideb.inf.homeworkproject.server.Client;
import javafx.scene.control.Alert;

import java.util.ArrayList;

/**
 * The main class for the mode of the game. It handles many data manipulations,
 * has many methods which are called outside this class, in order to perform
 * some actions.
 */
public class GameModel {
    /**
     * The number of {@code CircleNode}s already clicked.
     */
    private int clickedCirclesCount;

    /**
     * The maximum number of {@code CircleNode}s we can select at once.
     */
    public static final int MAX_NUMBER_OF_CIRCLES_TO_CLICK = 3; // why not 4?

    /**
     * It stores the {@code CircleNodes}s which tend to be removed after
     * the player is finished.
     */
    private ArrayList<CircleNode> removableNodes;

    /**
     * It stores the {@code CircleNodes}s for one step, for making able
     * to undo a step.
      */
    private ArrayList<CircleNode> prevNodes;

    /**
     * It stores the status of each cell:
     * 0 if it is empty, and 1 if it occupied
     * by a {@code CircleNode}. It is used to
     * save a game state.
     */
    private int[][] gameBoardStatus;

    /**
     * The size of our {@code gameBoard}.
     */
    public static final int GAME_BOARD_SIZE = 4;

    /**
     * The name of the first player, which is indicated
     * with the value of 1 in the program.
     */
    private String player1Name;

    /**
     * The name of the second player, which is indicated
     * with the value of -1 in the program.
     */
    private String player2Name;

    /**
     * Handles who is coming next.
     */
    private int whosComingNext = -1;

    /**
     * Stores the first players score.
     */
    private int player1Score;

    /**
     * Stores the second players score.
     */
    private int player2Score;

    /**
     * Represents the client which operates with the {@code Server}.
     */
    private Client client;

    /**
     * An empty constructor, which initializes the classes
     * field with some default values.
     */
    public GameModel() {
        this.removableNodes = new ArrayList<>();
        this.clickedCirclesCount = 0;
        this.gameBoardStatus = new int[GAME_BOARD_SIZE][GAME_BOARD_SIZE];
        this.prevNodes = new ArrayList<>();
        this.client = new Client(this);
        this.player1Name = "";
        this.player2Name = "";
    }

    /**
     * It sets the value of a field in the {@code gameBoardStatus}.
     * @param row the row to which we want to set the value.
     * @param column the column to which we want to set the value.
     * @param value the value which we want to set to a column/row.
     * @return {@code true} if it succeeds, and {@code false} if not.
     */
    public boolean setStatus(final int row, final int column, final int value) {
        if (row < 0 || row > GAME_BOARD_SIZE) {
            alert("Wrong row number when invoking setStatus() method!");
            return false;
        } else if (column < 0 || column > GAME_BOARD_SIZE) {
            alert("Wrong column number when invoking setStatus() method!");
            return false;
        } else if (value != 1 && value != 0) {
            alert("Wrong value number when invoking setStatus() method!");
            return false;
        } else {
            this.gameBoardStatus[row][column] = value;
            return true;
        }
    }

    /**
     * Alerts the user of a certain problem/message passed
     * by the {@code message} parameter.
     * @param message the message the alert dialogue displays.
     */
    public void alert(final String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears the {@code removableNodes} for further correct additions.
     * ALso resets to 0 the {@code clickedCirclesCount}.
     */
    public void clearDeletions() {
        this.clickedCirclesCount = 0;
        this.removableNodes.clear();
    }

    /**
     * Switches who is coming next.
     */
    public void switchPlayer() {
        this.whosComingNext *= -1;
    }

    /**
     * Adds a {@code CircleNode} to {@code removableNodes}, which will be
     * removed if the player is done selecting.
     * @param node the {@code CircleNode} we want to remove in the future.
     * @return {@code true} if it succeeds, and {@code false} if not.
     */
    public boolean addRemovableNode(final CircleNode node) {
        if (!this.removableNodes.contains(node)) {
            this.removableNodes.add(node);
            this.clickedCirclesCount++;
            return true;
        } else {
            this.removableNodes.remove(node);
            this.clickedCirclesCount--;
            return false;
        }
    }

    /**
     * Adds nodes to the {@code prevNodes} too, in order to be able
     * to undo a step, and replace them to the board.
     * @param node the {@code CircleNode} we want to undo in the future.
     * @return {@code true} if it succeeds, and {@code false} if not.
     */
    public boolean addPrevNode(final CircleNode node) {
        return this.prevNodes.add(node);
    }

    /**
     * Cleares the nodes in {@code prevNodes}.
     */
    public void clearPrev() {
        this.prevNodes.clear();
    }

    /**
     * Invokes the {@code client}s {@code saveGame()} method,
     * which saves the game in the database.
     */
    public void save() {
        this.client.saveGame();
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    /**
     * Getter method for a {@code String} representing the winner.
     * @return the winner.
     */
    public String getWinner() {
        return switch (this.whosComingNext) {
            case -1 -> this.player1Name;
            case 1 -> this.player2Name;
            default -> "NoOne";
        };
    }

    /**
     * Getter method for the {@code gameBoardStatus}, for further usages
     * outside this class.
     * @return a 2D array representing the {@code gameBoard} status.
     */
    public int[][] getGameBoardStatus() {
        return this.gameBoardStatus;
    }

    /**
     * Getter method for the {@code removableNodes} for further usages
     * outside this class.
     * @return an {@code ArrayList} containing {@code CircleNode}s,
     * representing the {@code removableNodes}.
     */
    public ArrayList<CircleNode> getRemovableNodes() {
        return this.removableNodes;
    }

    /**
     * Getter method for the index of upcoming player.
     * @return 1 if it is {@code player1},
     * and -1 if it is {@code player2}.
     */
    public int getWhosComingNext() {
        return this.whosComingNext;
    }

    /**
     * Getter method for the {@code client}.
     * @return the {@code client}.
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Getter for the {@code clickedCirclesCount}.
     * @return the number of {@code CircleNodes} clicked.
     */
    public int getClickedCirclesCount() {
        return clickedCirclesCount;
    }

    /**
     * Getter for {@code prevNoeds}.
     * @return an {@code ArrayList} containing {@code CircleNode}s.
     */
    public ArrayList<CircleNode> getPrevNodes() {
        return prevNodes;
    }
}
