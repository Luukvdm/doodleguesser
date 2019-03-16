package clientapp.controllers;

import clientapp.messaging.IGameClient;
import clientapp.messaging.clientgui.IClientGameGUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class GameController extends BaseController implements IClientGameGUI, Initializable {

	@FXML private ListView listGuesses;
	@FXML private ListView listMessagers;
	@FXML private TextField txtGuess;
	@FXML private Label lblTitle;
	@FXML private Label lblTimer;
	@FXML private Label lblCurrentColor;
	@FXML private Canvas cvDrawBoard;
	@FXML private Button btnClearCanvas;

	private GraphicsContext drawBoardGraphics;
	private PlayerRole myCurrentRole;
	private GameState currentGameState;

	private Timeline timeline;

	private Color drawingColor;

	public GameController(IGameClient gameClient, Stage primaryStage) {
		super(gameClient, primaryStage);
		getGameClient().registerClientGameGUI(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drawBoardGraphics = cvDrawBoard.getGraphicsContext2D();
		resetCanvas();
		drawingColor = Color.BLACK;

		setToWaitingForPlayers();

		currentGameState = GameState.WAITING_FOR_PLAYERS;
		myCurrentRole = PlayerRole.GUESSER;
		addChatMessage("You", "Joined the game");
	}

	@Override
	public void processPlayerListUpdate(String playerName, PlayerUpdate playerUpdate) {
		Platform.runLater(() -> {
			String messageForChat = "";
			if (playerUpdate == PlayerUpdate.LEFT) {
				messageForChat = "Left the game";
			} else if (playerUpdate == PlayerUpdate.JOINED) {
				messageForChat = "Joined the game";
			}

			addChatMessage(playerName, messageForChat);
		});
	}

	@Override
	public void processStartPath(double x, double y, double colorR, double colorG, double colorB) {
		Platform.runLater(() -> startPath(x, y, Color.color(colorR, colorG, colorB)));
	}

	@Override
	public void processAddPointToPath(double x, double y) {
		Platform.runLater(() -> {
			addPointToPath(x, y);
			addPointToPath(x, y);
		});
	}

	@Override
	public void processClearCanvas() {
		Platform.runLater(() -> resetCanvas());
	}

	@Override
	public void processStartNewRound(PlayerRole playerRole, String word) {
		Platform.runLater(() -> initNewRound(playerRole, word));
	}

	@Override
	public void processUpdateGameState(GameState gameState) {
		Platform.runLater(() -> {
			switch (gameState) {
				case ROUND_STARTED:
					startNewRound();
					break;
				case WAITING_FOR_PLAYERS:
					setToWaitingForPlayers();
					break;
				case ROUND_ENDED:
					endRound();
					break;
				case GAME_ENDED:
					endGame();
					break;
			}
			currentGameState = gameState;
		});
	}

	@Override
	public void processGuessResult(boolean guessedRight, String guess) {
		Platform.runLater(() -> {
			if(guessedRight) {
				guessedRight(guess);
			} else {
				addChatMessage("You", guess);
			}
		});
	}

	@Override
	public void processUpdateChat(String playerName, String message) {
		Platform.runLater(() -> addChatMessage(playerName, message));
	}

	public void DrawBoardMousePressed(MouseEvent mouseEvent) {
		if(myCurrentRole == PlayerRole.DRAWER && currentGameState == GameState.ROUND_STARTED) {
			double x = mouseEvent.getX();
			double y = mouseEvent.getY();
			Color color = drawingColor;
			startPath(x, y, color);
			getGameClient().startPath(x, y, color.getRed(), color.getGreen(), color.getBlue());
		}
	}

	public void DrawBoardMouseDragged(MouseEvent mouseEvent) {
		if(myCurrentRole == PlayerRole.DRAWER && currentGameState == GameState.ROUND_STARTED) {
			double x = mouseEvent.getX();
			double y = mouseEvent.getY();
			addPointToPath(x, y);
			getGameClient().addPointToPath(x, y);
		}
	}

	public void DrawBoardMouseReleased() {
		//close Path?
		drawBoardGraphics.closePath();
	}

	public void btnMakeGuessAction() {
		makeGuess();
	}

	public void btnClearAction() {
		resetCanvas();
		getGameClient().clearCanvas();
	}

	public void KeyPressed(KeyEvent keyEvent) {
		if(keyEvent.getCode().toString().equals("ENTER")) {
			makeGuess();
		}
	}

	private void makeGuess() {
		String guess = txtGuess.getText();
		txtGuess.setText("");
		//addChatMessage("You", guess);
		getGameClient().makeGuess(guess);
	}

	private void startPath(double x, double y, Color color) {
		drawBoardGraphics.setStroke(color);
		drawBoardGraphics.beginPath();
		drawBoardGraphics.moveTo(x, y);
		drawBoardGraphics.stroke();
	}

	private void addPointToPath(double x, double y) {

		drawBoardGraphics.lineTo(x, y);
		drawBoardGraphics.stroke();
	}

	private void addChatMessage(String playerName, String message) {
		listMessagers.getItems().add(playerName + ":");
		listGuesses.getItems().add(message);
	}

	private void setUpTimer(int seconds) {
		long endTime = (seconds * 1000) + System.currentTimeMillis();
		DateFormat timeFormat = new SimpleDateFormat( "mm:ss" );
		timeline = new Timeline(
				new KeyFrame(
						Duration.millis( 500 ),
						event -> {
							final long diff = endTime - System.currentTimeMillis();
							if ( diff < 0 ) {
								lblTimer.setText( timeFormat.format( 0 ) );

							} else {
								lblTimer.setText( timeFormat.format( diff ) );
							}
						}
				)
		);
		timeline.setCycleCount( 124 );
		timeline.play();
	}

	private void setToWaitingForPlayers() {
		setTitleContent("Waiting for players...", Color.BLACK);
	}

	private void initNewRound(PlayerRole playerRole, String word) {
		myCurrentRole = playerRole;
		String text;
		Boolean isDrawer = myCurrentRole == PlayerRole.DRAWER;
		btnClearCanvas.disableProperty().set(!isDrawer);
		if(isDrawer) {
			text = "Draw the word: '" + word + "'";
		} else {
			text = "Guess the word";
		}
		setTitleContent(text, Color.BLACK);
	}

	private void startNewRound() {
		setUpTimer(60);
	}

	private void endRound() {
		setTitleContent("Round ended", Color.BLACK);
		if(timeline != null) timeline.stop();
		resetCanvas();
	}

	private void endGame() {
		setTitleContent("Game ended", Color.BLACK);
	}

	private void guessedRight(String word) {
		String text = "You guessed right! the word was " + word;
		Color color = Color.GREEN;
		setTitleContent(text, color);
	}

	private void setTitleContent(String text, Color textColor) {
		lblTitle.setText(text);
		lblTitle.textFillProperty().set(textColor);
	}

	private void resetCanvas() {
		drawBoardGraphics.setFill(Color.LIGHTGRAY);
		drawBoardGraphics.fillRect(0, 0, cvDrawBoard.getWidth(), cvDrawBoard.getHeight());
		drawBoardGraphics.setStroke(Color.BLACK);
		drawBoardGraphics.setLineWidth(3);
		drawBoardGraphics.strokeRect(0, 0, cvDrawBoard.getWidth(), cvDrawBoard.getHeight());
	}

	public void btnColorAction(ActionEvent actionEvent) {
		Button btn = ((Button)actionEvent.getSource());
		String id = btn.getId();
		switch (id) {
			case "btnBlack":
				drawingColor = Color.BLACK;
				break;
			case "btnWhite":
				drawingColor = Color.WHITE;
				break;
			case "btnRed":
				drawingColor = Color.RED;
				break;
			case "btnYellow":
				drawingColor = Color.YELLOW;
				break;
			case "btnBlue":
				drawingColor = Color.BLUE;
				break;
			case "btnGreen":
				drawingColor = Color.GREEN;
				break;
			case "btnPurple":
				drawingColor = Color.PURPLE;
				break;
			case "btnBrown":
				drawingColor = Color.BROWN;
				break;
		}
		lblCurrentColor.textFillProperty().set(drawingColor);
	}
}
