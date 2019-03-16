package clientapp.messaging;

import clientapp.messaging.messagehandlers.*;
import fontysmultipurposelibrary.communication.messaging.IMessageHandler;
import fontysmultipurposelibrary.communication.messaging.IMessageHandlerFactory;

public class ClientMessageHandlerFactory implements IMessageHandlerFactory {
	@Override
	public IMessageHandler getHandler(String simpleType, Object game) {
		IGameClient gc = (IGameClient)game;

		switch(simpleType) {
			case "GuessResultMessage":
				return new GuessResultMessageHandler(gc);
			case "LoginResultMessage":
				return new LoginResultMessageHandler(gc);
			case "PlayerListUpdateMessage":
				return new PlayerListUpdateMessageHandler(gc);
			case "RegistrationResultMessage":
				return new RegistrationResultMessageHandler(gc);
			case "StartNewRoundMessage":
				return new StartNewRoundMessageHandler(gc);
			case "UpdateChatMessage":
				return new UpdateChatMessageHandler(gc);
			case "UpdateGameStateMessage":
				return new UpdateGameStateMessageHandler(gc);
			case "AddPointToPathMessage":
				return new AddPointToPathMessageHandler(gc);
			case "StartPathMessage":
				return new StartPathMessageHandler(gc);
			case "ClearCanvasMessage":
				return new ClearCanvasMessageHandler(gc);
			default:
				return null;
		}
	}
}
