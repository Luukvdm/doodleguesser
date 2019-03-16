package socketgameserver.model;

import fontysmultipurposelibrary.logging.LogLevel;
import fontysmultipurposelibrary.logging.Logger;
import restauthshared.client.IAuthRestClient;
import socketgameserver.messaging.IServerMessageGenerator;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
THIS CLASS IS ONLY RESPONSIBLE FOR HANDLING GAME LOGIC / RULES
THE METHODS ARE CALLED FROM THE MESSAGE HANDLERS
WHEN A MESSAGE NEEDS TO BE SENT TO CLIENTS, THE MESSAGEGENERATOR CLASS IS USED
 */
public class Game implements IGame {

	private static final int MIN_PLAYER_AMOUNT = 2;
	private static final int MAX_PLAYER_AMOUNT = 10;
	private static final String WORD_FILE = "/words.txt";

	private IRound round;

	private ArrayList<IPlayer> players;

	private Timer roundTimer;

	private IServerMessageGenerator messageGenerator;
	private IAuthRestClient restClient;
	private GameState gameState = GameState.WAITING_FOR_PLAYERS;

	public Game(IServerMessageGenerator messageGenerator, IAuthRestClient restClient) {
		this.messageGenerator = messageGenerator;
		this.players = new ArrayList<>();
		this.restClient = restClient;
		this.roundTimer = new Timer();
	}

	@Override
	public void loginPlayer(String userName, String password, String sessionId) {
		if (players.size() < MAX_PLAYER_AMOUNT) {
			if (checkPlayerNameAlreadyExists(userName)) {
				messageGenerator.notifyLoginResult(sessionId, null, false, "Username already exists");
				return;
			}

			//TODO debug code weghalen
			/*messageGenerator.notifyLoginResult(sessionId, "123", true, null);
			Player p = new Player(sessionId, userName);
			players.add(p);
			Logger.getInstance().log(p.getUsername() + " joined the game", LogLevel.INFORMATION);
			messageGenerator.notifyPlayerListUpdate(sessionId, userName, PlayerUpdate.JOINED);
			if (checkStartingCondition()) {
				Logger.getInstance().log("Starting game", LogLevel.INFORMATION);
				startNewRound();
			}*/
			//TODO dit terugzetten wanneer klaar met debuggen

			String token = restClient.login(userName,password);
			if(token != null && !token.equals("")) {
				messageGenerator.notifyLoginResult(sessionId, token, true, null);
				Player p = new Player(sessionId, userName);
				players.add(p);
				messageGenerator.notifyPlayerListUpdate(sessionId, userName, PlayerUpdate.JOINED);

				if(checkStartingCondition()) {
					startNewRound();
				}
			} else {
				messageGenerator.notifyLoginResult(sessionId, null, false, "Login failed");
			}
		} else {
			messageGenerator.notifyLoginResult(sessionId, null, false, "Game is already full");
		}
	}

	@Override
	public void registerNewPlayer(String username, String password, String sessionId) {
		Logger.getInstance().log("Registering player '" + username + "'", LogLevel.INFORMATION);
		boolean success = restClient.register(username, password);
		if (success) {
			Logger.getInstance().log("Successfully registered player '" + username + "'", LogLevel.INFORMATION);
			messageGenerator.notifyRegisterResult(sessionId, true, null);
		} else {
			Logger.getInstance().log("Failed to register player '" + username + "'", LogLevel.INFORMATION);
			messageGenerator.notifyRegisterResult(sessionId, false, "Could'nt register player");
		}

	}

	@Override
	public void processClientDisconnect(String sessionId) {
		for (IPlayer pl : players)
			if (pl.getSessionId().equals(sessionId)) {
				players.remove(pl);
				Logger.getInstance().log(pl.getUsername() + " disconnected from the game", LogLevel.INFORMATION);
				messageGenerator.notifyPlayerListUpdate(sessionId, pl.getUsername(), PlayerUpdate.LEFT);

				if (pl.getIsDrawing()) {
					startNewRound();
				}
			}
	}

	@Override
	public void startPath(String sessionId, double x, double y, double colorR, double colorG, double colorB) {

		IPlayer p = getPlayerWithSession(sessionId);
		//Check if the player is allowed to draw
		if (p != null && p.getIsDrawing()) {
			messageGenerator.notifyStartPath(sessionId, x, y, colorR, colorG, colorB);
		}

	}

	@Override
	public void addPointToPath(String sessionId, double x, double y) {
		IPlayer p = getPlayerWithSession(sessionId);
		//Check if the player is allowed to draw
		if (p != null && p.getIsDrawing()) {
			messageGenerator.notifyAddPointToPath(sessionId, x, y);
		}
	}

	@Override
	public void clearCanvas(String sessionId) {
		IPlayer p = getPlayerWithSession(sessionId);
		//Check if the player is allowed to draw
		if (p != null && p.getIsDrawing()) {
			messageGenerator.notifyClearCanvas(sessionId);
		}
	}

	@Override
	public void guessWord(String sessionId, String guess) {
		IPlayer p = getPlayerWithSession(sessionId);
		//Check if the player is allowed to draw
		if (p != null && !p.getIsDrawing() && gameState == GameState.ROUND_STARTED) {
			boolean guessedRight = round.guessWord(guess);
			if (guessedRight) {
				p.setHasGuessedRight(true);
				p.givePoint();
				messageGenerator.notifyUpdateChat(sessionId, p.getUsername(), "Guessed right!");
			} else {
				messageGenerator.notifyUpdateChat(sessionId, p.getUsername(), guess);
			}
			messageGenerator.notifyGuessResult(sessionId, guessedRight, guess);
		}
	}

	@Override
	public int getNumberOfPlayers() {
		return players.size();
	}

	private boolean checkPlayerNameAlreadyExists(String userName) {
		for (IPlayer pl : players)
			if (pl.getUsername().equals(userName)) {
				return true;
			}

		return false;
	}

	private IPlayer getPlayerWithSession(String sessionId) {
		for (IPlayer p : players)
			if (p.getSessionId().equals(sessionId))
				return p;
		return null;
	}

	private boolean checkStartingCondition() {
		return (players.size() >= MIN_PLAYER_AMOUNT);
	}

	private boolean checkGameEnded() {
		for (IPlayer p : players) {
			if(!p.getIsDrawing()) {
				if(!p.getHasGuessedRight()) {
					return false;
				}
			}
		}

		return true;
	}

	private void startNewRound() {
		Logger.getInstance().log("Starting new round", LogLevel.INFORMATION);
		if (round == null) {
			round = new Round(players.get(0), WORD_FILE);
		} else {
			int index = players.indexOf(round.getDrawingPlayer()) + 1;
			if (index >= players.size()) index = 0;
			round.startNewRound(players.get(index));
		}

		IPlayer p = round.getDrawingPlayer();
		messageGenerator.notifyStartNewRound(p.getSessionId(), round.getWord());

		//Start the round and set the timer
		gameState = GameState.ROUND_STARTED;
		messageGenerator.notifyUpdateGameState(GameState.ROUND_STARTED);

		startTimer();
	}

	private void endRound() {
		Logger.getInstance().log("Round ended", LogLevel.INFORMATION);
		gameState = GameState.ROUND_ENDED;
		messageGenerator.notifyUpdateGameState(GameState.ROUND_ENDED);
		startNewRound();
	}

	private void startTimer() {
		roundTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				//TODO checken of dit wel werkt en/of nodig is. Ik denk dat de volgende timer nu op het thread van de vorige timer start
				endRound();
			}
		}, round.getTimerDelay());
	}
}
