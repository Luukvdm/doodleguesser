package fontysmultipurposelibrary.communication.messaging;


public interface IMessageHandlerFactory {
    IMessageHandler getHandler(String simpleType, Object game);
}
