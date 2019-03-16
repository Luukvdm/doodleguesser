package clientapp.messaging.messagehandlers;

import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import clientapp.messaging.IGameClient;
import socketgameshared.messaging.messages.fromserver.RegistrationResultMessage;

public class RegistrationResultMessageHandler extends MessageHandlerBase<RegistrationResultMessage> {

	private IGameClient gc;

	public RegistrationResultMessageHandler(IGameClient client)
	{
		this.gc = client;
	}

	@Override
	public void handleMessageInternal(RegistrationResultMessage message, String sessionId) {
		gc.handlePlayerRegistrationResponse(message.isResult(), message.getReason());
	}
}
