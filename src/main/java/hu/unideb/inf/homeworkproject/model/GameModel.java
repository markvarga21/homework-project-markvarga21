package hu.unideb.inf.homeworkproject.model;

import hu.unideb.inf.homeworkproject.server.Client;
import javafx.scene.control.Alert;

import java.util.*;

public class GameModel {
    private int clickedCirclesCount;
    private ArrayList<CircleNode> removableNodes;
    // for going back
    private ArrayList<CircleNode> prevNodes;
    private int stepCount;

    private int[][] gameBoardStatus;

    // REMOVE, this is HARD CODED
    private String player1Name = "Aladar"; // nr. 1
    private String player2Name = "Anna"; // nr. -1

    private int whosComingNext = -1;

    private int player1Score;
    private int player2Score;

    private Client client;

    public GameModel() {
        this.removableNodes = new ArrayList<>();
        this.clickedCirclesCount = 0;
        this.gameBoardStatus = new int[4][4];
        this.prevNodes = new ArrayList<>();
        this.stepCount = 0;
        this.client = new Client(this);
    }

    public Client getClient() {
        return client;
    }

    public boolean setStatus(int row, int column, int value) {
        if (row < 0 || row > 4) {
            alert("Wrong row number when invoking setStatus() method!");
            return false;
        } else if (column < 0 || column > 4) {
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

    public int[][] getGameBoardStatus() {
        return gameBoardStatus;
    }

    public void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearDeletions() {
        this.clickedCirclesCount = 0;
        this.removableNodes.clear();
    }

    public String getWinner() {
        return switch (this.whosComingNext) {
            case -1 -> this.player1Name;
            case 1 -> this.player2Name;
            default -> "NoOne";
        };
    }

    public ArrayList<CircleNode> getRemovableNodes() {
        return removableNodes;
    }

    public int getWhosComingNext() {
        return whosComingNext;
    }

    public boolean addRemovableNode(CircleNode node) {
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

    public int getClickedCirclesCount() {
        return clickedCirclesCount;
    }

    public void switchPlayer() {
        this.whosComingNext *= -1;
    }

    public boolean addPrevNode(CircleNode node) {
        return this.prevNodes.add(node);
    }

    public void clearPrev() {
        this.prevNodes.clear();
    }

    public ArrayList<CircleNode> getPrevNodes() {
        return prevNodes;
    }

    public void save() {
        this.client.saveGame();
    }
}