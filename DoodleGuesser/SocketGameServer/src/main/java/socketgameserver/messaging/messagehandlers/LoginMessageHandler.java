package socketgameserver.messaging.messagehandlers;

import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.fromclient.LoginMessage;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;

public class LoginMessageHandler extends MessageHandlerBase<LoginMessage> {

	private IGame game;

	public LoginMessageHandler(IGame game) {
		this.game = game;
	}

	@Override
	public void handleMessageInternal(LoginMessage message, String sessionId) {
		game.loginPlayer(message.getPlayerUsername(), message.getPassword(), sessionId);
	}
}
