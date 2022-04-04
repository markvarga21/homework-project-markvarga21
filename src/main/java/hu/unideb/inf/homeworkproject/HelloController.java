package hu.unideb.inf.homeworkproject;

import hu.unideb.inf.homeworkproject.model.CircleNode;
import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.model.Validator;
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

    private final Color player1Color = Color.BLACK;
    private final Color player2Color = Color.RED;
    private final int highlightStrokeWidth = 3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameBoardCircles = new Circle[4][4];
        this.nodeArray = new Node[4][4];
        this.gameModel = new GameModel();
        this.validator = new Validator(this.gameModel);

        this.gameBoard.setStyle("-fx-border-width: 2; -fx-border-color: black; -fx-effect: dropshadow(three-pass-box, green, 10, 0, 0, 0);");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
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
                    highlightNode(this.gameModel.getWhosComingNext(), clickedNode);
                } else {
                    // Remove highlighting
                    Circle temp = (Circle) clickedNode;
                    temp.setStrokeWidth(0);
                }
            } else this.gameModel.alert("Invalid selection!");
        }
    }

    private void highlightNode(int playerIndex, Node node) {
        switch (playerIndex) {
            case 1 -> {
                Circle player1Circle = (Circle) node;
                player1Circle.setStrokeWidth(this.highlightStrokeWidth);
                player1Circle.setStroke(this.player1Color);
            }
            case -1 -> {
                Circle player2Circle = (Circle) node;
                player2Circle.setStrokeWidth(this.highlightStrokeWidth);
                player2Circle.setStroke(this.player2Color);
            }
        }
    }

    @FXML
    public void isPlayerDone(ActionEvent e) {
        if (!this.validator.checkWinner()) {
            if (this.validator.isValidSelection()) {
                removeNodes();
                this.gameModel.switchPlayer();
                this.gameModel.clearDeletions();
            } else this.gameModel.alert("Invalid selection!");
        } else displayWinner();
    }

    private void removeNodes() {
//        Image image = new Image("resources/images/kezes_proba1.gif");
//        ImageView imgView = new ImageView(image);
//        imgView.setX(0);
//        imgView.setY(0);
//        this.mainPane.getChildren().add(imgView);

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

}