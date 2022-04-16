package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;

import java.time.Instant;

public class Game {
    private Instant dateWhenPlayed;
    private String player1Name;
    private String player1Color;
    private String player2Name;
    private String player2Color;
    private String gameBoard;
    private int whoWasGoingNext;

    public Game(Instant dateWhenPlayed, String player1Name, String player1Color, String player2Name, String player2Color, String gameBoard, int whoWasGoingNext) {
        this.dateWhenPlayed = dateWhenPlayed;
        this.player1Name = player1Name;
        this.player1Color = player1Color;
        this.player2Name = player2Name;
        this.player2Color = player2Color;
        this.gameBoard = gameBoard;
        this.whoWasGoingNext = whoWasGoingNext;
    }

    public Game(String player1Name, String player1Color, String player2Name, String player2Color, String gameBoard, int whoWasGoingNext) {
        this.player1Name = player1Name;
        this.player1Color = player1Color;
        this.player2Name = player2Name;
        this.player2Color = player2Color;
        this.gameBoard = gameBoard;
        this.whoWasGoingNext = whoWasGoingNext;
    }

    public Instant getDateWhenPlayed() {
        return this.dateWhenPlayed;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public int getWhoWasGoingNext() {
        return whoWasGoingNext;
    }

    public String getPlayer1Color() {
        return player1Color;
    }

    public String getPlayer2Color() {
        return player2Color;
    }

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
