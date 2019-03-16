package socketgameserver.messaging;

import socketgameserver.model.IGame;
import fontysmultipurposelibrary.communication.messaging.IMessageHandler;
import fontysmultipurposelibrary.communication.messaging.IMessageHandlerFactory;
import fontysmultipurposelibrary.communication.messaging.MessageProcessorBase;

public class ServerMessageProcessor extends MessageProcessorBase implements IServerMessageProcessor {

	private IGame game;

	public ServerMessageProcessor(IMessageHandlerFactory messageHandlerFactory) {
		super(messageHandlerFactory);
	}

	@Override
	public void registerGame(IGame game) {
		this.game = game;
	}

	public IGame getGame() {
		return this.game;
	}

	@Override
	public void processMessage(String sessionId, String type, String data) {
		//Get the last part from the full package and type name
		String simpleType = type.split("\\.")[type.split("\\.").length - 1];

		IMessageHandler handler = getMessageHandlerFactory().getHandler(simpleType, getGame());
		handler.handleMessage(data, sessionId);
	}

	@Override
	public void handleDisconnect(String sessionId) {
		getGame().processClientDisconnect(sessionId);
	}
}
