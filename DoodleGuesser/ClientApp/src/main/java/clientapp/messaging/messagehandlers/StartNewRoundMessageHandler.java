package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.fromserver.StartNewRoundMessage;

public class StartNewRoundMessageHandler extends MessageHandlerBase<StartNewRoundMessage> {

	private IGameClient gc;

	public StartNewRoundMessageHandler(IGameClient gc) {
		this.gc = gc;
	}

	@Override
	public void handleMessageInternal(StartNewRoundMessage message, String sessionId) {
		gc.handleStartNewRound(message.getPlayerRole(), message.getWord());
	}
}
