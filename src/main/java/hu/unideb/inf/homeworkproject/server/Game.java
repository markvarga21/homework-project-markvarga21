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
        return "Player1 name: " + this.player1Name + " | "
                + "Player2 name: " + this.player2Name + " | "
                + "GameBoard: " + this.gameBoard;
    }
}
