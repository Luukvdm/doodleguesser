package clientapp.messaging.messagehandlers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.MessageHandlerBase;
import socketgameshared.messaging.messages.ClearCanvasMessage;

public class ClearCanvasMessageHandler extends MessageHandlerBase<ClearCanvasMessage> {

	private IGameClient gc;

	public ClearCanvasMessageHandler(IGameClient gc) {
		this.gc = gc;
	}

	@Override
	public void handleMessageInternal(ClearCanvasMessage message, String sessionId) {
		gc.handleClearCanvas();
	}
}
