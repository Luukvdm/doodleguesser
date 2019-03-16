package clientapp.messaging;

import fontysmultipurposelibrary.communication.messaging.IMessageProcessor;

public interface IClientMessageProcessor extends IMessageProcessor {
	void registerGameClient(IGameClient gameClient);
}
