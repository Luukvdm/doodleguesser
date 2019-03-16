package clientapp.messaging.messagehandlers;

import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import clientapp.messaging.IGameClient;
import socketgameshared.messaging.messages.fromserver.LoginResultMessage;

public class LoginResultMessageHandler extends MessageHandlerBase<LoginResultMessage> {

	private IGameClient gc;

	public LoginResultMessageHandler(IGameClient client) {
		this.gc = client;
	}

	@Override
	public void handleMessageInternal(LoginResultMessage message, String sessionId) {
		gc.handleLoginResponse(message.getToken(), message.getSuccess(), message.getMessage());
	}
}
