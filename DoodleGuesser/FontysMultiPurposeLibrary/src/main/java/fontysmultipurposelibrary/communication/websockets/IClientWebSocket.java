package fontysmultipurposelibrary.communication.websockets;

public interface IClientWebSocket extends IWebSocket {

    void setServerUri(String serverUri);

    void send(Object object);

    void onWebSocketMessageReceived(String message, String sessionId);
}
