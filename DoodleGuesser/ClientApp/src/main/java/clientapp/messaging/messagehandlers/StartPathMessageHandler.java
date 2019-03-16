package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.StartPathMessage;

public class StartPathMessageHandler extends MessageHandlerBase<StartPathMessage> {

	private IGameClient gc;

	public StartPathMessageHandler(IGameClient client) {
		this.gc = client;
	}

	@Override
	public void handleMessageInternal(StartPathMessage message, String sessionId) {
		gc.handleStartPath(message.getX(), message.getY(), message.getColorR(), message.getColorG(), message.getColorB());
	}
}
