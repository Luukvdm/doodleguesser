package socketgameserver.messaging;

import socketgameserver.model.IGame;
import fontysmultipurposelibrary.communication.messaging.IMessageProcessor;

public interface IServerMessageProcessor extends IMessageProcessor {
	void registerGame(IGame game);
}
