package socketgameserver.messaging.messagehandlers;

import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.fromclient.RegisterPlayerMessage;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;

public class RegisterPlayerMessageHandler extends MessageHandlerBase<RegisterPlayerMessage> {

	private IGame game;

	public RegisterPlayerMessageHandler(IGame game) {
		this.game = game;
	}

	@Override
	public void handleMessageInternal(RegisterPlayerMessage message, String sessionId) {
		game.registerNewPlayer(message.getPlayerName(), message.getPassword(), sessionId);
	}
}
