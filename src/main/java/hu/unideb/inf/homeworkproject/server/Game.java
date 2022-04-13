package hu.unideb.inf.homeworkproject.server;

import java.time.Instant;

public class Game {
    private Instant dateWhenPlayed;
    private String player1Name;
    private String player2Name;
    private String gameBoard;
    private int whoWasGoingNext;

    public Game(Instant dateWhenPlayed, String player1Name, String player2Name, String gameBoard, int whoWasGoingNext) {
        this.dateWhenPlayed = dateWhenPlayed;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.gameBoard = gameBoard;
        this.whoWasGoingNext = whoWasGoingNext;
    }

    public Game(String player1Name, String player2Name, String gameBoard, int whoWasGoingNext) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
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
        return "Player 1 name: " + this.player1Name + "\n"
                + "Player 2 name: " + this.player2Name + "\n"
                + "GameBoard: \n" + gameMap;
    }
}
