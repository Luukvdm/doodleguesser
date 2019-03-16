package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.fromserver.GuessResultMessage;

public class GuessResultMessageHandler extends MessageHandlerBase<GuessResultMessage> {

	private IGameClient gc;

	public GuessResultMessageHandler(IGameClient gc) {
		this.gc = gc;
	}

	@Override
	public void handleMessageInternal(GuessResultMessage message, String sessionId) {
		gc.handleGuessResult(message.getGuessedRight(), message.getGuess());
	}
}
