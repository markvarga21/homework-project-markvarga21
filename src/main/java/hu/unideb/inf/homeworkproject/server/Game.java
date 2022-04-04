package hu.unideb.inf.homeworkproject.server;

import java.util.Date;

public class Game {
    private String gameID;
    private Date dateWhenPlayed;
    private String player1Name;
    private String player2Name;
    private String gameBoard;

    public Game(String gameID, Date dateWhenPlayed, String player1Name, String player2Name, String gameBoard) {
        this.gameID = gameID;
        this.dateWhenPlayed = dateWhenPlayed;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.gameBoard = gameBoard;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Date getDateWhenPlayed() {
        return dateWhenPlayed;
    }

    public void setDateWhenPlayed(Date dateWhenPlayed) {
        this.dateWhenPlayed = dateWhenPlayed;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String gameBoard) {
        this.gameBoard = gameBoard;
    }
}
