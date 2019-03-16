package clientapp.messaging;

import fontysmultipurposelibrary.communication.messaging.IMessageHandler;
import fontysmultipurposelibrary.communication.messaging.IMessageHandlerFactory;
import fontysmultipurposelibrary.communication.messaging.MessageProcessorBase;

public class ClientMessageProcessor extends MessageProcessorBase implements IClientMessageProcessor {

	private IGameClient gameClient;

	public ClientMessageProcessor(IMessageHandlerFactory messageHandlerFactory)
	{
		super(messageHandlerFactory);
	}

	public void registerGameClient(IGameClient gameClient)
	{
		this.gameClient = gameClient;
	}

	public void handleDisconnect(String sessionId)
	{
		//Do nothing
	}

	@Override
	public void processMessage(String sessionId, String type, String data) {
		String simpleType = type.split("\\.")[type.split("\\.").length - 1];

		IMessageHandler handler = getMessageHandlerFactory().getHandler(simpleType, gameClient);
		handler.handleMessage(data, sessionId);
	}
}
