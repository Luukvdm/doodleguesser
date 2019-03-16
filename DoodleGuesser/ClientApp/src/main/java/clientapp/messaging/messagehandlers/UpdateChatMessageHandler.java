package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.fromserver.UpdateChatMessage;

public class UpdateChatMessageHandler extends MessageHandlerBase<UpdateChatMessage> {

	private IGameClient gc;

	public UpdateChatMessageHandler(IGameClient gc) {
		this.gc = gc;
	}


	@Override
	public void handleMessageInternal(UpdateChatMessage message, String sessionId) {
		gc.handleUpdateChat(message.getPlayerName(), message.getMessage());
	}
}
