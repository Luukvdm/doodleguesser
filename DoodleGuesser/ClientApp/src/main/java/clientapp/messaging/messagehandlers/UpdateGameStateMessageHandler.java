package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.fromserver.UpdateGameStateMessage;

public class UpdateGameStateMessageHandler extends MessageHandlerBase<UpdateGameStateMessage> {

	private IGameClient gc;

	public UpdateGameStateMessageHandler(IGameClient gc) {
		this.gc = gc;
	}

	@Override
	public void handleMessageInternal(UpdateGameStateMessage message, String sessionId) {
		gc.handleUpdateGameState(message.getGameState());
	}
}
