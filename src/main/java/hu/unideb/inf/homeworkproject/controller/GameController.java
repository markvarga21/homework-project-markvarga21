package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.model.CircleNode;
import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.model.Validator;
import hu.unideb.inf.homeworkproject.view.ImageManager;
import hu.unideb.inf.homeworkproject.view.StyleManager;
import javafx.application.Platform;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

// REMOVE IT
@SuppressWarnings("Checkstyle")
public class GameController implements Initializable {
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

    private Color player1Color;
    private Color player2Color;

    private Logger gameControllerLogger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameBoardCircles = new Circle[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        this.nodeArray = new Node[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        this.gameModel = new GameModel();
        this.validator = new Validator(this.gameModel);
        this.imageManager = new ImageManager(this.mainPane);
        // if color is not selected by player
        this.player1Color = Color.BLACK;
        this.player2Color = Color.BLACK;
        this.gameControllerLogger = LogManager.getLogger();

        StyleManager.styleGameBoard(this.gameBoard);
        fillGameBoard();
    }

    private void fillGameBoard() {
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
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

            this.gameControllerLogger.debug("Clicked on: " + clickedNode.getId() + ", on row: " + rowIndex + ", column: " + colIndex);

            if (this.validator.isValidSelection(node)) {
                if (this.gameModel.addRemovableNode(node)) {
                    // Highlighting
                    StyleManager.highlightNode(this.gameModel.getWhosComingNext(), clickedNode, this.player1Color, this.player2Color);
                } else {
                    // Remove highlighting
                    StyleManager.removeHighlight(clickedNode);
                }
            } else this.gameModel.feedBackUser("Invalid selection!", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void isPlayerDone(final ActionEvent e) {
        if (this.validator.isValidSelection()) {
            removeNodes();
            this.gameModel.switchPlayer();
            this.gameModel.clearDeletions();
        } else {
            this.gameModel.feedBackUser("Invalid selection!", Alert.AlertType.WARNING);
        }
        if (this.validator.checkWinner()) {
            // to correct
            this.imageManager.playGif("D://....................Egyetem//4. felev//software-engeneering//HomeworkProject//src//main//resources//images//celebration.gif", 0, 0);
            displayWinner();
            this.gameModel.getClient().updateLeaderBoard(this.gameModel.getWinner());
        }
    }
    
//    private Timeline createTimeLine(Duration duration, String message) {
//        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(final ActionEvent event) {
//                System.out.println("Delayed remove...");
//                System.out.println(message);
////                gameModel.clearPrev();
////
////                for (var node : gameModel.getRemovableNodes()) {
////                    System.out.println("Removing node: " + node.getNode() + ", on row: " + node.getRow() + ", and column: " + node.getColumn());
////                    gameBoard.getChildren().remove(node.getNode());
////
////                    final int columnIndex = node.getColumn();
////                    final int rowIndex = node.getRow();
////
////                    gameModel.setStatus(rowIndex, columnIndex, 0);
////
////                    gameModel.addPrevNode(node);
////                }
//                System.out.println("Delayed remove done.");
//            }
//        }));
//        return timeline;
//    }

    private void removeNodes() {
//        this.imageManager.playGif("D://....................Egyetem//4. felev//software-engeneering//HomeworkProject//src//main//resources//images//kezes_proba1.gif");
//        this.imageManager = new ImageManager(this.imageHolder, this.mainPane, "D://....................Egyetem//4. felev//software-engeneering//HomeworkProject//src//main//resources//images//kezes_proba1.gif");
//        this.imageManager.start();
//        Thread.sleep(2000);
//
//        Timeline timeline = createTimeLine(Duration.seconds(2), "LOL");
//        timeline.play();

//        this.gameModel.clearPrev();
//
        for (var node : this.gameModel.getRemovableNodes()) {
            this.gameControllerLogger.debug("Removing node: " + node.getNode() + ", on row: " + node.getRow() + ", and column: " + node.getColumn());
            this.gameBoard.getChildren().remove(node.getNode());

            final int columnIndex = node.getColumn();
            final int rowIndex = node.getRow();

            this.gameModel.setStatus(rowIndex, columnIndex, 0);

            this.gameModel.addPrevNode(node);

            // we flush it before we add further nodes to it
            // do I need this?
//        this.mainPane.getChildren().remove(imgView);
        }
    }

    private void displayWinner() {
        this.gameModel.feedBackUser(this.gameModel.getWinner() + " had just won the game!", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void undoButtonClick() {
        if (this.gameModel.getPrevNodes().size() > 0) {
            this.gameControllerLogger.info("Undo...");
            this.gameControllerLogger.debug("prevNodes size before putting back: " + this.gameModel.getPrevNodes().size());

            putBackNodes();

            // clearing the remained nodes in prevNodes
            this.gameModel.clearPrev();
            this.gameModel.switchPlayer();
            this.gameControllerLogger.debug("prevNodes size after putting back: " + this.gameModel.getPrevNodes().size());
        } else this.gameModel.feedBackUser("You cannot undo more than one steps!", Alert.AlertType.WARNING);
    }

    @FXML
    public void saveGame() {
        this.gameModel.save();
    }

    @FXML
    public void newGame() {
        this.gameModel.startNewGame();
        fillGameBoard();
    }

    @FXML
    public void loadGame() {
        this.gameControllerLogger.info("Loading game...");
    }

    @FXML
    public void exitGame(ActionEvent event) {
        this.gameControllerLogger.info("Exiting game...");
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.close();
        Platform.exit();
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

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public Color getPlayer1Color() {
        return this.player1Color;
    }

    public void setPlayer1Color(Color player1Color) {
        this.player1Color = player1Color;
    }

    public Color getPlayer2Color() {
        return this.player2Color;
    }

    public void setPlayer2Color(Color player2Color) {
        this.player2Color = player2Color;
    }
}