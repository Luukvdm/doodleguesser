package socketgameserver.messaging;

import socketgameshared.messaging.messages.AddPointToPathMessage;
import socketgameshared.messaging.messages.ClearCanvasMessage;
import socketgameshared.messaging.messages.StartPathMessage;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;
import fontysmultipurposelibrary.communication.websockets.IServerWebSocket;
import socketgameshared.messaging.messages.fromserver.*;

public class ServerMessageGenerator implements IServerMessageGenerator {

	private IServerWebSocket serverSocket;

	public ServerMessageGenerator(IServerWebSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void notifyPlayerListUpdate(String sessionId, String playerName, PlayerUpdate playerUpdate) {
		PlayerListUpdateMessage msg = new PlayerListUpdateMessage(playerName, playerUpdate);
		serverSocket.sendToOthers(sessionId, msg);
	}

	@Override
	public void notifyRegisterResult(String sessionId, boolean success, String reason) {
		RegistrationResultMessage msg = new RegistrationResultMessage(success, reason);
		serverSocket.sendTo(sessionId, msg);
	}

	@Override
	public void notifyLoginResult(String sessionId, String token, boolean isSuccess, String message) {
		LoginResultMessage msg = new LoginResultMessage(token, isSuccess, message);
		serverSocket.sendTo(sessionId, msg);
	}

	@Override
	public void notifyStartPath(String drawerId, double x, double y, double colorR, double colorG, double colorB) {
		StartPathMessage msg = new StartPathMessage(x, y, colorR, colorG, colorB);
		serverSocket.sendToOthers(drawerId, msg);
	}

	@Override
	public void notifyAddPointToPath(String drawerId, double x, double y) {
		AddPointToPathMessage msg = new AddPointToPathMessage(x, y);
		serverSocket.sendToOthers(drawerId, msg);
	}

	@Override
	public void notifyClearCanvas(String drawerId) {
		ClearCanvasMessage msg = new ClearCanvasMessage();
		serverSocket.sendToOthers(drawerId, msg);
	}

	@Override
	public void notifyUpdateGameState(GameState gameState) {
		UpdateGameStateMessage msg = new UpdateGameStateMessage(gameState);
		serverSocket.broadcast(msg);
	}

	@Override
	public void notifyStartNewRound(String drawerId, String word) {
		StartNewRoundMessage guesserMsg = new StartNewRoundMessage(PlayerRole.GUESSER, null);
		StartNewRoundMessage drawerMsg = new StartNewRoundMessage(PlayerRole.DRAWER, word);

		serverSocket.sendToOthers(drawerId, guesserMsg);
		serverSocket.sendTo(drawerId, drawerMsg);
	}

	@Override
	public void notifyUpdateChat(String sessionId, String playerName, String message) {
		UpdateChatMessage msg = new UpdateChatMessage(playerName, message);
		serverSocket.sendToOthers(sessionId, msg);
	}

	@Override
	public void notifyGuessResult(String sessionId, boolean guessedRight, String guess) {
		GuessResultMessage msg = new GuessResultMessage(guessedRight, guess);
		serverSocket.sendTo(sessionId, msg);
	}


}
