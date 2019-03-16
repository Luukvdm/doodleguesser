package fontysmultipurposelibrary.communication.websockets;

import fontysmultipurposelibrary.communication.messaging.EncapsulatingMessageGenerator;
import fontysmultipurposelibrary.communication.messaging.IEncapsulatingMessageGenerator;
import fontysmultipurposelibrary.serialization.ISerializer;
import fontysmultipurposelibrary.serialization.SerializationProvider;

public abstract class WebSocketBase {

    public IEncapsulatingMessageGenerator getEncapsulatingMessageGenerator() {
        return encapsulatingMessageGenerator;
    }

    private IEncapsulatingMessageGenerator encapsulatingMessageGenerator = new EncapsulatingMessageGenerator();

    public WebSocketBase() {

    }

    public abstract void start();

    public abstract void stop();

    public ISerializer<String> getSerializer() {
        return SerializationProvider.getSerializer();
    }
}
