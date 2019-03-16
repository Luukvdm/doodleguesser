package fontysmultipurposelibrary.communication.messaging;

public interface IMessageHandler {

    void handleMessage(String message, String sessionId);
}