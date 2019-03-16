package socketgameserver.messaging.messagehandlers;

import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.fromclient.GuessWordMessage;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;

public class GuessWordMessageHandler extends MessageHandlerBase<GuessWordMessage> {

	private IGame game;

	public GuessWordMessageHandler(IGame game) {
		this.game = game;
	}

	@Override
	public void handleMessageInternal(GuessWordMessage message, String sessionId) {
		game.guessWord(sessionId, message.getGuess());
	}
}
