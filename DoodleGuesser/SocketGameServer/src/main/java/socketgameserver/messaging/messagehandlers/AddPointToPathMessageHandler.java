package socketgameserver.messaging.messagehandlers;

import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.AddPointToPathMessage;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;

public class AddPointToPathMessageHandler extends MessageHandlerBase<AddPointToPathMessage> {

	private IGame game;

	public AddPointToPathMessageHandler(IGame game) {
		this.game = game;
	}

	@Override
	public void handleMessageInternal(AddPointToPathMessage message, String sessionId) {
		game.addPointToPath(sessionId, message.getX(), message.getY());
	}
}
