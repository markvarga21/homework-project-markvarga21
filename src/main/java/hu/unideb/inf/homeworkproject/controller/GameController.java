package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.model.CircleNode;
import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.model.Validator;
import hu.unideb.inf.homeworkproject.server.Client;
import hu.unideb.inf.homeworkproject.server.Game;
import hu.unideb.inf.homeworkproject.view.ImageManager;
import hu.unideb.inf.homeworkproject.view.StyleManager;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class which is used in playing the main game,
 * manipulating the view and also the model
 * components too.
 */
public class GameController implements Initializable {
    /**
     * A {@code GridPane} which stores the stones/circles.
     */
    @FXML
    private GridPane gameBoard;

    /**
     * The main {@code AnchorPane} of the application.
     */
    @FXML
    private AnchorPane mainPane;

    /**
     * A 2D array of {@code Node}s for easier {@code Node}
     * reference index identification.
     */
    private Node[][] nodeArray;

    /**
     * The main {@code GameModel} of the application/game.
     */
    private GameModel gameModel;

    /**
     * The {@code Validator} of the application/game for
     * validating certain moves/actions.
     */
    private Validator validator;

    /**
     * An object used by the animations when
     * circle takeoff.
     */
    private ImageManager imageManager;

    /**
     * The {@code Color} of the first player.
     */
    private Color player1Color;

    /**
     * The {@code Color} of the second player.
     */
    private Color player2Color;

    /**
     * Logger for {@code GameController} class.
     */
    static final Logger gameControllerLogger = LogManager.getLogger();

    /**
     * A {@code Parent} object representing the root.
     */
    private Parent root;

    private Parent infoRoot;

    /**
     * The main {@code GameLoaderController} for further invocations.
     */
    private GameLoaderController gameLoaderController;

    private InfoViewController infoViewController;

    /**
     * A {@code MenuBar} used to identify the current stage for
     * loading the {@code loader} scene.
     */
    @FXML
    private MenuBar gameControllerMenu;

    /**
     * An {@code ImageView} which holds an image
     * of a hand.
     */
    @FXML
    private ImageView handImageView1;

    /**
     * An {@code ImageView} which holds an image
     * of a hand.
     */
    @FXML
    private ImageView handImageView2;

    /**
     * An {@code ImageView} which holds an image
     * of a hand.
     */
    @FXML
    private ImageView handImageView3;

    /**
     * An {@code ImageView} which holds an image
     * of a hand.
     */
    @FXML
    private ImageView handImageView4;

    /**
     * An {@code ImageView} array, which stores all
     * the hands which are separately inside a
     * {@code ImageView}.
     */
    private ImageView[] handImageViewHolder;

    /**
     * A {@code Label} for displaying the name of the
     * player who is coming next.
     */
    @FXML
    private Label whosComingLabel;

    /**
     * This initializes everything before loading the scene.
     * It nests the scene/fxml loading, because if there is another
     * {@code initialize()} method inside {@code GameLoaderController},
     * there will be a concurrence.
     * Also, we have to initialize here, in order to be able
     * to switch to that scene, when clicking load menu item.
     * @param url for resolving relative paths for the root object.
     * @param resourceBundle used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.nodeArray = new Node[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        this.gameModel = new GameModel();
        this.validator = new Validator(this.gameModel);
        this.imageManager = new ImageManager(this.mainPane);

        this.player1Color = Color.BLACK;
        this.player2Color = Color.BLACK;
        this.handImageViewHolder = new ImageView[4];

        StyleManager.styleGameBoard(this.gameBoard);
        fillGameBoard();

        Image image = new Image(String.valueOf(getClass().getResource("/images/hand-and-arm.png")));
        this.handImageView1.setImage(image);
        this.handImageView2.setImage(image);
        this.handImageView3.setImage(image);
        this.handImageView4.setImage(image);

        this.handImageViewHolder[0] = handImageView1;
        this.handImageViewHolder[1] = handImageView2;
        this.handImageViewHolder[2] = handImageView3;
        this.handImageViewHolder[3] = handImageView4;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-loader.fxml"));
        try {
            this.root = fxmlLoader.load();
            this.gameLoaderController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxmls/info-view.fxml"));
        try {
            this.infoRoot = fxmlLoader2.load();
            this.infoViewController = fxmlLoader2.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method which initially
     * fills the {@code GameBoard} with {@code Circle}s.
     */
    private void fillGameBoard() {
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                Circle circle = new Circle(10,  Color.DEEPSKYBLUE);
                circle.setRadius(25);

                this.gameModel.setStatus(i, j, 1);
                this.gameBoard.add(circle, j, i);
                this.nodeArray[i][j] = circle;

                GridPane.setHalignment(circle, HPos.CENTER);
                GridPane.setValignment(circle, VPos.CENTER);
            }
        }
    }

    /**
     * It is used when there is a game loading.
     * It will replace all the nodes and states with
     * the new ones passed by the parameter.
     * Also, it refreshes the node references too.
     * @param gameToLoad the new {@code Game} we want to load.
     */
    public void replaceNodesWithLoaded(final Game gameToLoad) {
        clearBoard();

        this.whosComingLabel.setText(String.valueOf(gameToLoad.getWhoWasGoingNext()).equals("1")
                ? "It is " + gameToLoad.getPlayer1Name() + "'s turn now!"
                : "It is " + gameToLoad.getPlayer2Name() + "'s turn now!");

        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                if (gameToLoad.toStateArray()[i][j] == 1) {
                    Circle circle = new Circle(10,  Color.DEEPSKYBLUE);
                    circle.setRadius(25);

                    this.gameBoard.add(circle, j, i);

                    this.nodeArray[i][j] = circle;

                    GridPane.setHalignment(circle, HPos.CENTER);
                    GridPane.setValignment(circle, VPos.CENTER);
                }
            }
        }
    }

    /**
     * A method used to clear the {@code GameBoard}.
     */
    public void clearBoard() {
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                this.gameBoard.getChildren().remove(this.nodeArray[i][j]);
            }
        }
    }

    /**
     * A method which is invoked when a {@code Circle} is clicked.
     * @param event representing a {@code MouseEvent}.
     */
    @FXML
    public void onButtonClick(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gameBoard) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            CircleNode node = new CircleNode(clickedNode, rowIndex, colIndex);

            gameControllerLogger.debug("Clicked on: {}, on row: {}, column: {}", clickedNode.getId(), rowIndex, + colIndex);

            if (this.validator.isValidSelection(node)) {
                if (this.gameModel.addRemovableNode(node)) {
                    StyleManager.highlightNode(this.gameModel.getWhosComingNext(), clickedNode, this.player1Color, this.player2Color);
                } else {
                    StyleManager.removeHighlight(clickedNode);
                }
            } else this.gameModel.feedBackUser("Invalid selection!", Alert.AlertType.WARNING);
        }
    }

    /**
     * Checks whether a player is done selecting the circles or not
     * by pressing the DONE button. It also checks if there is a
     * winner or not.
     * @param e representing a {@code ActionEvent}.
     */
    @FXML
    public void isPlayerDone(final ActionEvent e) {
        gameControllerLogger.info("Player is done!");

        this.whosComingLabel.setText(String.valueOf(this.gameModel.getWhosComingNext()).equals("1")
                                                    ? "It is " + this.gameModel.getPlayer2Name() + "'s turn now!"
                                                    : "It is " + this.gameModel.getPlayer1Name() + "'s turn now!");

        if (this.validator.isValidSelection()) {
            removeNodes();
            this.gameModel.switchPlayer();
            this.gameModel.clearDeletions();

        } else {
            this.gameModel.feedBackUser("Invalid selection!", Alert.AlertType.WARNING);
        }
        if (this.validator.checkWinner()) {
//            correct this, bcs it plays in infinity
            this.imageManager.playGif(String.valueOf(getClass().getResource("/images/celebration.gif")), 0, 0);
            displayWinner();
            Client client = new Client(this.gameModel);
            client.updateLeaderBoard(this.gameModel.getWinner());
        }
    }

    /**
     * A method which removes all the nodes from the {@code board}.
     * It also plays an animation for each
     * {@code Circle}, showing the takeaway.
     */
    private void removeNodes() {
        this.imageManager.playAnimation(this.gameModel.getRemovableNodes(), this.handImageViewHolder, this.gameBoard);

        for (var node : this.gameModel.getRemovableNodes()) {
            final int columnIndex = node.getColumn();
            final int rowIndex = node.getRow();

            this.gameModel.setStatus(rowIndex, columnIndex, 0);
        }
    }

    /**
     * A method for displaying the winner inside an {@code Alert} box.
     */
    private void displayWinner() {
        this.gameModel.feedBackUser(this.gameModel.getWinner() + " had just won the game!", Alert.AlertType.INFORMATION);
    }

    /**
     * Invokes the {@code GameModel}s {@code save()} method.
     */
    @FXML
    public void saveGame() {
        this.gameModel.save();
    }

    /**
     * Invokes the {@code GameModel}s {@code startNewGame()} method.
     * It also clears the board before putting back the {@code Circle}s
     * for the new game.
     */
    @FXML
    public void newGame() {
        this.gameModel.startNewGame();
        clearBoard();
        fillGameBoard();
    }

    /**
     * A method which is used to load games, and for
     * switching back to the main scene.
     * It also uses the {@code Client}, because
     * there is no need to open the server connection
     * in each {@code GameController} class instantiation.
     */
    @FXML
    public void loadGame() {
        gameControllerLogger.info("Loading game...");
        Stage stage = (Stage)this.gameControllerMenu.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Client client = new Client(this.gameModel);
        var ls = client.returnSavedGames();
        this.gameLoaderController.setSavedGamesList(ls);
        this.gameLoaderController.setCurrentGame(getCurrentGameInfos());
    }

    @FXML
    public void loadAbout() {
        gameControllerLogger.info("Loading about...");

        this.infoViewController.displayAbout(getCurrentGameInfos());

        Stage stage = (Stage)this.gameControllerMenu.getScene().getWindow();
        Scene scene = new Scene(infoRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadRules() {
        gameControllerLogger.info("Loading rules...");

        this.infoViewController.displayRules(getCurrentGameInfos());

        Stage stage = (Stage)this.gameControllerMenu.getScene().getWindow();
        Scene scene = new Scene(infoRoot);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loadLeaderboard() {
        gameControllerLogger.info("Loading leaderboard...");

        Client client = new Client();

        this.infoViewController.displayLeaderboard(client.returnLeaderboard(), getCurrentGameInfos());

        Stage stage = (Stage)this.gameControllerMenu.getScene().getWindow();
        Scene scene = new Scene(infoRoot);
        stage.setScene(scene);
        stage.show();
    }

    private Game getCurrentGameInfos() {
        StringBuilder gameBoard = new StringBuilder();
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                gameBoard.append(this.gameModel.getGameBoardStatus()[i][j]);
            }
        }
        return new Game(this.gameModel.getPlayer1Name(), this.gameModel.getPlayer1Color(),
                this.gameModel.getPlayer2Name(), this.gameModel.getPlayer2Color(),
                gameBoard.toString(), this.gameModel.getWhosComingNext());
    }

    /**
     * A method for exiting the application/game.
     * @param event representing an {@code ActionEvent}.
     */
    @FXML
    public void exitGame(ActionEvent event) {
        gameControllerLogger.info("Exiting game...");
        Platform.exit();
    }

    /**
     * A method for getting the {@code GameModel}.
     * @return the main {@code GameModel}.
     */
    public GameModel getGameModel() {
        return this.gameModel;
    }

    /**
     * A method for getting the {@code Color} of the first player.
     * @return the {@code Color} of the first player.
     */
    public Color getPlayer1Color() {
        return this.player1Color;
    }

    /**
     * Getter for {@code getWhosComingLabel} Label.
     * @return the {@code Label} for the getWhosComing label.
     */
    public Label getWhosComingLabel() {
        return this.whosComingLabel;
    }

    /**
     * A method for setting the {@code Color} of the first player.
     * @param player1Color the new {@code Color} of the first player.
     */
    public void setPlayer1Color(Color player1Color) {
        this.player1Color = player1Color;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * A method for getting the {@code Color} of the second player.
     * @return the {@code Color} of the second player.
     */
    public Color getPlayer2Color() {
        return this.player2Color;
    }

    /**
     * A method for setting the {@code Color} of the second player.
     * @param player2Color the new {@code Color} of the second player.
     */
    public void setPlayer2Color(Color player2Color) {
        this.player2Color = player2Color;
    }
}