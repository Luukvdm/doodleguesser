package fontysmultipurposelibrary.communication.messaging;

import fontysmultipurposelibrary.serialization.ISerializer;
import fontysmultipurposelibrary.serialization.SerializationProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class MessageHandlerBase<T> implements IMessageHandler {

    public void handleMessage(String data, String sessionId) {
        ISerializer<String> ser = SerializationProvider.getSerializer();
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T msg = ser.deserialize(data, type);
        handleMessageInternal(msg, sessionId);
    }

    public abstract void handleMessageInternal(T message, String sessionId);

}
