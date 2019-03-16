package clientapp;

import clientapp.controllers.ControllerHelper;
import clientapp.messaging.*;
import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.communication.messaging.IMessageHandlerFactory;
import fontysmultipurposelibrary.communication.websockets.ClientWebSocket;
import fontysmultipurposelibrary.communication.websockets.IClientWebSocket;
import clientapp.messaging.ClientMessageHandlerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {

    private IClientWebSocket socket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        socket = new ClientWebSocket();
        socket.setServerUri("ws://localhost:8095/doodleguesser/");
        IClientMessageGenerator generator = new ClientMessageGenerator(socket);

        IGameClient gameClient = new GameClient(generator);
        IMessageHandlerFactory factory = new ClientMessageHandlerFactory();

        IClientMessageProcessor processor = new ClientMessageProcessor(factory);
        socket.setMessageProcessor(processor);

        socket.start();
        processor.registerGameClient(gameClient);

        ControllerHelper.getInstance().loadFxml("/fxml/LoginForm.fxml", gameClient, primaryStage);
    }

    @Override
    public void stop() {
        socket.stop();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
