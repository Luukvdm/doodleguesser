package clientapp.messaging;

import clientapp.messaging.clientgui.IClientGameGUI;
import clientapp.messaging.clientgui.IClientLoginGUI;
import clientapp.messaging.clientgui.IClientRegisterGui;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

public interface IGameClient {
	void registerPlayer(String userName, String password);

	void loginPlayer(String userName, String password);

	void startPath(double x, double y, double colorR, double colorG, double colorB);

	void addPointToPath(double x, double y);

	void clearCanvas();

	void makeGuess(String guess);

	//------------------------------------------------------------------------------------------------\\

	void registerClientLoginGUI(IClientLoginGUI gui);
	void removeClientLoginGUI();

	void registerClientRegisterGUI(IClientRegisterGui gui);
	void removeClientRegisterGUI();

	void registerClientGameGUI(IClientGameGUI gui);
	void removeClientGameGUI();

	//------------------------------------------------------------------------------------------------\\

	void handleLoginResponse(String token, boolean isSuccess, String message);

	void handlePlayerRegistrationResponse(boolean success, String reason);

	void handlePlayerListUpdate(String playerName, PlayerUpdate update);

	void handleStartPath(double x, double y, double colorR, double colorG, double colorB);

	void handleAddPointToPath(double x, double y);

	void handleClearCanvas();

	void handleStartNewRound(PlayerRole role, String word);

	void handleUpdateGameState(GameState gameState);

	void handleGuessResult(boolean guessedRight, String guess);

	void handleUpdateChat(String playerName, String message);
}
