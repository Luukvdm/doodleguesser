package socketgameshared.messaging.messages.fromserver;

import socketgameshared.messaging.messages.enums.GameState;

public class UpdateGameStateMessage {
	private GameState gameState;

	public GameState getGameState() {
		return gameState;
	}

	public UpdateGameStateMessage(GameState gameState) {
		this.gameState = gameState;
	}
}
