package fontysmultipurposelibrary.communication.websockets;

import fontysmultipurposelibrary.communication.messaging.EncapsulatingMessage;
import fontysmultipurposelibrary.communication.messaging.IMessageProcessor;
import fontysmultipurposelibrary.logging.LogLevel;
import fontysmultipurposelibrary.logging.Logger;
import fontysmultipurposelibrary.serialization.ISerializer;
import fontysmultipurposelibrary.serialization.SerializationProvider;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class ClientWebSocket extends WebSocketBase implements IClientWebSocket {

    //TODO Statiche url is niet heel handig
    private String serverUri = "ws://localhost:8095/doodleguesser/";

    private Session session;

    private static ClientWebSocket instance = null;

    public static ClientWebSocket getInstance() {
        if (instance == null) {
            instance = new ClientWebSocket();
        }
        return instance;
    }

    @Override
    public void start() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(serverUri));

        } catch (Exception ex) {
            Logger.getInstance().log(ex);
        }
    }

    @Override
    public void stop() {
        try {
            if (session != null)
                session.close();

        } catch (Exception ex) {
            Logger.getInstance().log(ex);
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session) {
        onWebSocketMessageReceived(message, session.getId());
    }

    public void onWebSocketMessageReceived(String message, String sessionId) {
        ISerializer<String> ser = SerializationProvider.getSerializer();
        EncapsulatingMessage msg = ser.deserialize(message, EncapsulatingMessage.class);
        messageProcessor.processMessage(sessionId, msg.getMessageType(), msg.getMessageData());
    }

    IMessageProcessor messageProcessor;

    @Override
    public void setMessageProcessor(IMessageProcessor handler) {
        this.messageProcessor = handler;
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        Logger.getInstance().log(cause.getMessage(), LogLevel.ERROR);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        session = null;
    }

    private void sendMessageToServer(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            Logger.getInstance().log(ex);
        }
    }

    @Override
    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public void send(Object object) {
        String msg = getEncapsulatingMessageGenerator().generateMessageString(object);
        sendMessageToServer(msg);
    }
}
