package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.fromserver.PlayerListUpdateMessage;

public class PlayerListUpdateMessageHandler extends MessageHandlerBase<PlayerListUpdateMessage> {
	private IGameClient gc;

	public PlayerListUpdateMessageHandler(IGameClient gc) {
		this.gc = gc;
	}

	@Override
	public void handleMessageInternal(PlayerListUpdateMessage message, String sessionId) {
		gc.handlePlayerListUpdate(message.getPlayerName(), message.getPlayerUpdate());
	}
}
