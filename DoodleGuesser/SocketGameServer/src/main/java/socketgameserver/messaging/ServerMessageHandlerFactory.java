package socketgameserver.messaging;

import socketgameserver.messaging.messagehandlers.*;
import socketgameserver.model.IGame;
import fontysmultipurposelibrary.communication.messaging.IMessageHandler;
import fontysmultipurposelibrary.communication.messaging.IMessageHandlerFactory;

public class ServerMessageHandlerFactory implements IMessageHandlerFactory {
	@Override
	public IMessageHandler getHandler(String simpleType, Object game) {
		IGame igame = (IGame) game;
		switch (simpleType) {
			case "AddPointToPathMessage":
				return new AddPointToPathMessageHandler(igame);
			case "GuessWordMessage":
				return new GuessWordMessageHandler(igame);
			case "LoginMessage":
				return new LoginMessageHandler(igame);
			case "RegisterPlayerMessage":
				return new RegisterPlayerMessageHandler(igame);
			case "StartPathMessage":
				return new StartPathMessageHandler(igame);
			case "ClearCanvasMessage":
				return new ClearCanvasMessageHandler(igame);
			default:
				return null;
		}
	}
}
