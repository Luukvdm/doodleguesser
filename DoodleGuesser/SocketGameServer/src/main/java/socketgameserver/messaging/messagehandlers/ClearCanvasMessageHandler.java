package socketgameserver.messaging.messagehandlers;

import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.ClearCanvasMessage;

public class ClearCanvasMessageHandler extends MessageHandlerBase<ClearCanvasMessage> {

	private IGame game;

	public ClearCanvasMessageHandler(IGame  game) {
		this.game = game;
	}

	@Override
	public void handleMessageInternal(ClearCanvasMessage message, String sessionId) {
		game.clearCanvas(sessionId);
	}
}
