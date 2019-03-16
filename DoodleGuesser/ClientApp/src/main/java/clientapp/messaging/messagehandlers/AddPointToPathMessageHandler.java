package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.AddPointToPathMessage;

public class AddPointToPathMessageHandler extends MessageHandlerBase<AddPointToPathMessage> {

	private IGameClient gc;

	public AddPointToPathMessageHandler(IGameClient client) { this.gc = client;}

	@Override
	public void handleMessageInternal(AddPointToPathMessage message, String sessionId) {
		gc.handleAddPointToPath(message.getX(), message.getY());
	}
}