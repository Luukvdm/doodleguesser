package clientapp.messaging.clientgui;

import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

public interface IClientGameGUI {

	void processPlayerListUpdate(String playerName, PlayerUpdate playerUpdate);

	void processStartPath(double x, double y, double colorR, double colorG, double colorB);

	void processAddPointToPath(double x, double y);

	void processClearCanvas();

	void processStartNewRound(PlayerRole playerRole, String word);

	void processUpdateGameState(GameState gameState);

	void processGuessResult(boolean guessedRight, String guess);

	void processUpdateChat(String playerName, String message);
}
