package hu.unideb.inf.homeworkproject.model;

import hu.unideb.inf.homeworkproject.controller.GameLoaderController;
import hu.unideb.inf.homeworkproject.server.Client;
import hu.unideb.inf.homeworkproject.server.Game;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * by the value of 1 in the program.
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

//    /**
//     * Stores the first players score.
//     */
//    private int player1Score;
//
//    /**
//     * Stores the second players score.
//     */
//    private int player2Score;

    /**
     * Represents the client which operates with the {@code Server}.
     */
    private final Client client;

    // TODO
    private final Logger mainLogger;

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
        this.mainLogger = LogManager.getLogger();
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
            this.mainLogger.fatal("Wrong row number when invoking setStatus() method!");
            return false;
        } else if (column < 0 || column > GAME_BOARD_SIZE) {
            this.mainLogger.fatal("Wrong column number when invoking setStatus() method!");
            return false;
        } else if (value != 1 && value != 0) {
            this.mainLogger.fatal("Wrong value number when invoking setStatus() method!");
            return false;
        } else {
            this.gameBoardStatus[row][column] = value;
            return true;
        }
    }

    /**
     * Informs the user of an error, warning, or information occurred while
     * using the application.
     * @param message the message we want to show for the user.
     * @param alertType the type of the information.
     */
    public void feedBackUser(final String message, final Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
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
        this.prevNodes = new ArrayList<>(this.removableNodes);
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
     */
    public void addPrevNode(final CircleNode node) {
        this.prevNodes.add(node);
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

    public void startNewGame() {
        this.mainLogger.info("Starting new game...");
//        System.out.println("Starting new game...");
        // we should prompt the user first...
        this.clickedCirclesCount = 0;
        this.prevNodes.clear();
        this.removableNodes.clear();
        for (int i = 0; i < GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GAME_BOARD_SIZE; j++) {
                this.gameBoardStatus[i][j] = 1;
            }
        }
    }

    public void replaceGameInfosWithLoaded(final Game gameToLoad) {
        this.setWhosComingNext(gameToLoad.getWhoWasGoingNext());
        this.setPlayer1Name(gameToLoad.getPlayer1Name());
        this.setPlayer2Name(gameToLoad.getPlayer2Name());
        this.setGameBoardStatus(gameToLoad.toStateArray());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(this.getGameBoardStatus()[i][j]);
            }
            System.out.println();
        }
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public void setPrevNodes(ArrayList<CircleNode> prevNodes) {
        this.prevNodes = prevNodes;
    }

    public void setClickedCirclesCount(int clickedCirclesCount) {
        this.clickedCirclesCount = clickedCirclesCount;
    }

    public void setRemovableNodes(ArrayList<CircleNode> removableNodes) {
        this.removableNodes = removableNodes;
    }

    public void setGameBoardStatus(int[][] gameBoardStatus) {
        this.gameBoardStatus = gameBoardStatus;
    }

    public void setWhosComingNext(int whosComingNext) {
        this.whosComingNext = whosComingNext;
    }

    public String getPlayer1Name() { return this.player1Name; }

    public String getPlayer2Name() { return this.player2Name; }

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
     * A method for returning the player name,
     * based on the {@code whosComingNext} index.
     * @param index the index of the {@code player}, from which
     * we want to extract the exact name.
     * @return the name of the {@code player} represented
     * by the index passed as a parameter.
     */
    public String getPlayerForIndex(final int index) {
        return switch (index) {
            case 1 -> this.player1Name;
            case -1 -> this.player2Name;
            default -> "No player";
        };
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
