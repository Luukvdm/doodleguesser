package socketgameserver.messaging.messagehandlers;

import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.StartPathMessage;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;

public class StartPathMessageHandler extends MessageHandlerBase<StartPathMessage> {

	private IGame game;

	public StartPathMessageHandler(IGame game) {
		this.game = game;
	}

	@Override
	public void handleMessageInternal(StartPathMessage message, String sessionId) {
		game.startPath(sessionId, message.getX(), message.getY(), message.getColorR(), message.getColorG(), message.getColorB());
	}
}
