package hu.unideb.inf.homeworkproject;

import hu.unideb.inf.homeworkproject.model.CircleNode;
import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.model.Validator;
import hu.unideb.inf.homeworkproject.view.ImageManager;
import hu.unideb.inf.homeworkproject.view.StyleManager;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private GridPane gameBoard;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView imageHolder;

    private Circle[][] gameBoardCircles;
    private Node[][] nodeArray;

    private GameModel gameModel;
    private Validator validator;
    private ImageManager imageManager;

    private final Color player1Color = Color.BLACK; // Aladar
    private final Color player2Color = Color.RED; // Anna
    private final int highlightStrokeWidth = 3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameBoardCircles = new Circle[GameModel.GAMEBOARD_SIZE][GameModel.GAMEBOARD_SIZE];
        this.nodeArray = new Node[GameModel.GAMEBOARD_SIZE][GameModel.GAMEBOARD_SIZE];
        this.gameModel = new GameModel();
        this.validator = new Validator(this.gameModel);
//        this.imageManager = new ImageManager(this.imageHolder, this.mainPane);

        StyleManager.styleGameBoard(this.gameBoard);

        for (int i = 0; i < GameModel.GAMEBOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAMEBOARD_SIZE; j++) {
                Circle circle = new Circle(10, Color.SKYBLUE);
                circle.setRadius(25);

//                this.gameBoardCircles[i][j] = circle;
                this.gameModel.setStatus(i, j, 1);
                this.gameBoard.add(circle, j, i);
                this.nodeArray[i][j] = circle;

                GridPane.setHalignment(circle, HPos.CENTER);
                GridPane.setValignment(circle, VPos.CENTER);
            }
        }
    }

    @FXML
    public void onButtonClick(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gameBoard) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            CircleNode node = new CircleNode(clickedNode, rowIndex, colIndex);

            System.out.println("Clicked on: " + clickedNode.getId() + ", on row: " + rowIndex + ", column: " + colIndex);

            if (this.validator.isValidSelection(node)) {
                if (this.gameModel.addRemovableNode(node)) {
                    // Highlighting
                    StyleManager.highlightNode(this.gameModel.getWhosComingNext(), clickedNode, this.player1Color, this.player2Color);
                } else {
                    // Remove highlighting
                    StyleManager.removeHighlight(clickedNode);
                }
            } else this.gameModel.alert("Invalid selection!");
        }
    }

    @FXML
    public void isPlayerDone(ActionEvent e) throws InterruptedException {
        if (this.validator.isValidSelection()) {
            removeNodes();
            this.gameModel.switchPlayer();
            this.gameModel.clearDeletions();
        } else this.gameModel.alert("Invalid selection!");
        if (this.validator.checkWinner()) {
            displayWinner();
            this.gameModel.getClient().updateLeaderBoard(this.gameModel.getWinner());
        }
    }

    private void removeNodes() throws InterruptedException {
//        this.imageManager.playGif("D://....................Egyetem//4. felev//software-engeneering//HomeworkProject//src//main//resources//images//kezes_proba1.gif");
//        this.imageManager = new ImageManager(this.imageHolder, this.mainPane, "D://....................Egyetem//4. felev//software-engeneering//HomeworkProject//src//main//resources//images//kezes_proba1.gif");
//        this.imageManager.start();
//        Thread.sleep(2000);

        // we flush it before we add further nodes to it
        this.gameModel.clearPrev();

        for (var node : this.gameModel.getRemovableNodes()) {
            System.out.println("Removing node: " + node.getNode() + ", on row: " + node.getRow() + ", and column: " + node.getColumn());
            this.gameBoard.getChildren().remove(node.getNode());

            final int columnIndex = node.getColumn();
            final int rowIndex = node.getRow();

            this.gameModel.setStatus(rowIndex, columnIndex, 0);

            this.gameModel.addPrevNode(node);
        }
        // do I need this?
//        this.mainPane.getChildren().remove(imgView);
    }

    private void displayWinner() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(this.gameModel.getWinner() + " had just won the game!");
        // TODO store it in redis database
        alert.showAndWait();
    }

    @FXML
    public void undoButtonClick() {
        if (this.gameModel.getPrevNodes().size() > 0) {
            System.out.println("Undo...");
            System.out.println("prevNodes size before putting back: " + this.gameModel.getPrevNodes().size());

            putBackNodes();

            // clearing the remained nodes in prevNodes
            this.gameModel.clearPrev();
            this.gameModel.switchPlayer();
            System.out.println("prevNodes size after putting back: " + this.gameModel.getPrevNodes().size());
        } else this.gameModel.alert("You cannot undo more than one steps!");
    }

    @FXML
    public void saveButtonClick() {
        this.gameModel.save();
    }

    private void putBackNodes() {
        var nodesToPutBack = this.gameModel.getPrevNodes();
        for (var node : nodesToPutBack) {
            this.gameBoard.add(node.getNode(), node.getColumn(), node.getRow());

            // Remove highlighting
            Circle temp = (Circle) node.getNode();
            temp.setStrokeWidth(0);
        }
    }

    @FXML
    public void onMouseEnter(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gameBoard) {
            StyleManager.hoverStyle(clickedNode);
        }
    }

}