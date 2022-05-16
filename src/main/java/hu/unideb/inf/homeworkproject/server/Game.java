package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;

import java.time.Instant;

/**
 * It represents a game state with information like the
 * {@code date} when the game was played, the names
 * of the player and others.
 */
public class Game {
    /**
     * An {@code Instant} object, which represents when
     * was the game played.
     */
    private Instant dateWhenPlayed;

    /**
     * The {@code name} of the first player.
     */
    private String player1Name;

    /**
     * The {@code color} of the first player.
     */
    private String player1Color;

    /**
     * The {@code name} of the second player.
     */
    private String player2Name;

    /**
     * The {@code color} of the second player.
     */
    private String player2Color;

    /**
     * A {@code String} representing the flattened
     * states of the board.
     */
    private String gameBoard;

    /**
     * An {@code int} representing who was coming.
     * -1 means that the first player, and +1 means
     * the second player.
     */
    private int whoWasGoingNext;

    /**
     * Constructor for {@code Game} class.
     * @param dateWhenPlayed the date.
     * @param player1Name the name of the first player.
     * @param player1Color the color of the first player.
     * @param player2Name the name of the second player.
     * @param player2Color the color of the second player.
     * @param gameBoard the state of the {@code GameBoard}.
     * @param whoWasGoingNext an index which is used to indicate who was coming next.
     */
    public Game(Instant dateWhenPlayed, String player1Name, String player1Color, String player2Name, String player2Color, String gameBoard, int whoWasGoingNext) {
        this.dateWhenPlayed = dateWhenPlayed;
        this.player1Name = player1Name;
        this.player1Color = player1Color;
        this.player2Name = player2Name;
        this.player2Color = player2Color;
        this.gameBoard = gameBoard;
        this.whoWasGoingNext = whoWasGoingNext;
    }

    /**
     * Constructor overloading without the {@code Instant} field, because MySQL cannot handle back
     * and forth this type of {@code Date}.
     * @param player1Name the name of the first player.
     * @param player1Color the color of the first player.
     * @param player2Name the name of the second player.
     * @param player2Color the color of the second player.
     * @param gameBoard the state of the {@code GameBoard}.
     * @param whoWasGoingNext an index which is used to indicate who was coming next.
     */
    public Game(String player1Name, String player1Color, String player2Name, String player2Color, String gameBoard, int whoWasGoingNext) {
        this.player1Name = player1Name;
        this.player1Color = player1Color;
        this.player2Name = player2Name;
        this.player2Color = player2Color;
        this.gameBoard = gameBoard;
        this.whoWasGoingNext = whoWasGoingNext;
    }

    /**
     * A method for getting the date when the game
     * was played on.
     * @return a {@code Instant} object, representing
     * the date when the game was saved.
     */
    public Instant getDateWhenPlayed() {
        return this.dateWhenPlayed;
    }

    /**
     * A method for getting the first players name.
     * @return the name of the first player.
     */
    public String getPlayer1Name() {
        return player1Name;
    }

    /**
     * A method for getting the second players name.
     * @return the name of the second player.
     */
    public String getPlayer2Name() {
        return player2Name;
    }

    /**
     * A method for getting the game board {@code String}.
     * @return a {@code String} representing the game board states.
     */
    public String getGameBoard() {
        return gameBoard;
    }

    /**
     * A method for getting who was coming next.
     * @return the index of a player. -1 means the first player
     * and +1 means the second player.
     */
    public int getWhoWasGoingNext() {
        return whoWasGoingNext;
    }

    /**
     * A method for getting the first players color.
     * @return a {@code String} representing the color code
     * used by the first player.
     */
    public String getPlayer1Color() {
        return player1Color;
    }

    /**
     * A method for getting the second players color.
     * @return a {@code String} representing the color code
     * used by the second player.
     */
    public String getPlayer2Color() {
        return player2Color;
    }

    /**
     * A method which converts back a flattened game board state
     * {@code String} to 2 dimensional {@code int} array, where
     * +1 means that on the current array index there is a {@code Circle},
     * and 0 if on the current index there is no {@code Circle}.
     * @return a 2D {@code int} array representing the game board state.
     */
    public int[][] toStateArray() {
        int[][] temp = new int[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        int index = 0;
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                temp[i][j] = Integer.parseInt(String.valueOf(this.gameBoard.charAt(index++)));
            }
        }
        return temp;
    }

    /**
     * An override {@code toString} method, in order to display a
     * {@code Game} more aesthetic.
     * @return a {@code String} which illustrates a {@code Game}.
     */
    @Override
    public String toString() {
        StringBuilder gameMap = new StringBuilder();
        char square = 11036;
        char circle = 11044;
        String copyGameBoard = this.gameBoard.replace('1', circle).replace('0', square);
        gameMap.append(copyGameBoard, 0, 4).append("\n")
                .append(copyGameBoard, 4, 8).append("\n")
                .append(copyGameBoard, 8, 12).append("\n")
                .append(copyGameBoard, 12, 16).append("\n");
        return "Player 1 name: " + this.player1Name + " with the color " + this.player1Color + "\n"
                + "Player 2 name: " + this.player2Name + " with the color " + this.player2Color + "\n"
                + "GameBoard: \n" + gameMap;
    }
}
