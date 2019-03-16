package fontysmultipurposelibrary.communication.websockets;

import fontysmultipurposelibrary.communication.messaging.IMessageProcessor;

public interface IWebSocket {
    void start();

    void stop();

    void setMessageProcessor(IMessageProcessor processor);
}
