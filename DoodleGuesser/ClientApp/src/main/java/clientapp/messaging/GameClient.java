package clientapp.messaging;

import clientapp.messaging.clientgui.IClientGameGUI;
import clientapp.messaging.clientgui.IClientLoginGUI;
import clientapp.messaging.clientgui.IClientRegisterGui;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

public class GameClient implements IGameClient {
	IClientMessageGenerator messageGenerator;

	private IClientGameGUI gameGUI;
	private IClientLoginGUI loginGUI;
	private IClientRegisterGui registerGUI;

	public GameClient(IClientMessageGenerator generator)
	{
		this.messageGenerator = generator;
	}

	@Override
	public void registerClientLoginGUI(IClientLoginGUI gui) {
		this.loginGUI = gui;
	}

	@Override
	public void removeClientLoginGUI() {
		this.loginGUI = null;
	}

	@Override
	public void registerClientRegisterGUI(IClientRegisterGui gui) {
		this.registerGUI = gui;
	}

	@Override
	public void removeClientRegisterGUI() {
		this.registerGUI = null;
	}

	@Override
	public void registerClientGameGUI(IClientGameGUI gui) {
		this.gameGUI = gui;
	}

	@Override
	public void removeClientGameGUI() {
		this.gameGUI = null;
	}

	@Override
	public void registerPlayer(String userName, String password)
	{
		messageGenerator.registerPlayerOnServer(userName, password);
	}

	@Override
	public void loginPlayer(String userName, String password)
	{
		messageGenerator.login(userName, password);
	}

	@Override
	public void startPath(double x, double y, double colorR, double colorG, double colorB) {
		messageGenerator.startPath(x, y, colorR, colorG, colorB);
	}

	@Override
	public void addPointToPath(double x, double y) {
		messageGenerator.addPointToPath(x, y);
	}

	@Override
	public void clearCanvas() {
		messageGenerator.clearCanvas();
	}

	@Override
	public void makeGuess(String guess) {
		messageGenerator.makeGuess(guess);
	}

	@Override
	public void handlePlayerRegistrationResponse(boolean success, String reason) {
		if(this.registerGUI != null) registerGUI.processRegistrationResponse(success, reason);
	}

	@Override
	public void handleLoginResponse(String token, boolean isSuccess, String message)
	{
		if(this.loginGUI != null) loginGUI.processLoginResponse(token, isSuccess, message);
	}

	@Override
	public void handlePlayerListUpdate(String playerName, PlayerUpdate update) {
		if(this.gameGUI != null) gameGUI.processPlayerListUpdate(playerName, update);
	}

	@Override
	public void handleStartPath(double x, double y, double colorR, double colorG, double colorB) {
		if(this.gameGUI != null) gameGUI.processStartPath(x, y, colorR, colorG, colorB);
	}

	@Override
	public void handleAddPointToPath(double x, double y) {
		if(this.gameGUI != null) gameGUI.processAddPointToPath(x, y);
	}

	@Override
	public void handleClearCanvas() {
		if(this.gameGUI != null) gameGUI.processClearCanvas();
	}

	@Override
	public void handleStartNewRound(PlayerRole role, String word) {
		if(this.gameGUI != null) gameGUI.processStartNewRound(role, word);
	}

	@Override
	public void handleUpdateGameState(GameState gameState) {
		if(this.gameGUI != null) gameGUI.processUpdateGameState(gameState);
	}

	@Override
	public void handleGuessResult(boolean guessedRight, String guess) {
		if(this.gameGUI != null) gameGUI.processGuessResult(guessedRight, guess);
	}

	@Override
	public void handleUpdateChat(String playerName, String message) {
		if(this.gameGUI != null) gameGUI.processUpdateChat(playerName, message);
	}
}
