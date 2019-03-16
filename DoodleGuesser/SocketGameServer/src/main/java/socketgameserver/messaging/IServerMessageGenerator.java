package socketgameserver.messaging;

import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

public interface IServerMessageGenerator {
	void notifyPlayerListUpdate(String sessionId, String playerName, PlayerUpdate playerUpdate);

	void notifyRegisterResult(String sessionId, boolean success, String reason);

	void notifyLoginResult(String sessionId, String token, boolean isSuccess, String message);

	void notifyStartPath(String drawerId, double x, double y, double r, double g, double b);

	void notifyAddPointToPath(String drawerId, double x, double y);

	void notifyClearCanvas(String drawerId);

	void notifyUpdateGameState(GameState gameState);

	void notifyStartNewRound(String drawerId, String word);

	void notifyUpdateChat(String sessionId, String playerName, String message);

	void notifyGuessResult(String sessionId, boolean guessedRight, String guess);
}
