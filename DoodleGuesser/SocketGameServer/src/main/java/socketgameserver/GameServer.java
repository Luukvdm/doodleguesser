package socketgameserver;

import socketgameserver.messaging.*;
import socketgameserver.model.Game;
import socketgameserver.model.IGame;
import fontysmultipurposelibrary.communication.messaging.IMessageHandlerFactory;
import fontysmultipurposelibrary.communication.websockets.ServerWebSocket;
import fontysmultipurposelibrary.logging.LogLevel;
import fontysmultipurposelibrary.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import restauthshared.client.AuthRestClient;
import restauthshared.client.IAuthRestClient;

import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

public class GameServer {

	private static final int PORT = 8095;

	public static void main(String[] args) {
		Logger.getInstance().log("Starting service...", LogLevel.INFORMATION);
		IMessageHandlerFactory factory = new ServerMessageHandlerFactory();
		IServerMessageProcessor messageProcessor = new ServerMessageProcessor(factory);
		final ServerWebSocket socket = new ServerWebSocket();
		socket.setMessageProcessor(messageProcessor);

		IServerMessageGenerator messageGenerator = new ServerMessageGenerator(socket);

		String restServiceUrl = "http://localhost:8096/auth";
		Logger.getInstance().log("Authentication server located at " + restServiceUrl, LogLevel.INFORMATION);
		IAuthRestClient restClient = new AuthRestClient(restServiceUrl);

		IGame game = new Game(messageGenerator, restClient);
		messageProcessor.registerGame(game);

		Logger.getInstance().log("Creating websocket on port " + PORT, LogLevel.INFORMATION);
		Server webSocketServer = new Server();
		ServerConnector connector = new ServerConnector(webSocketServer);
		connector.setPort(PORT);
		webSocketServer.addConnector(connector);

		// Setup the basic application "context" for this application at "/"
		// This is also known as the handler tree (in jetty speak)
		ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		webSocketContext.setContextPath("/");
		webSocketServer.setHandler(webSocketContext);

		try {
			// Initialize javax.websocket layer
			Logger.getInstance().log("Initializing javax.websocket layer", LogLevel.INFORMATION);
			ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webSocketContext);

			// Add WebSocket endpoint to javax.websocket layer
			Logger.getInstance().log("Adding WebSocket endpoint to the javax.websocket layer", LogLevel.INFORMATION);
			ServerEndpointConfig config = ServerEndpointConfig.Builder.create(socket.getClass(), socket.getClass().getAnnotation(ServerEndpoint.class).value())
					.configurator(new ServerEndpointConfig.Configurator() {
						@Override
						public <T> T getEndpointInstance(Class<T> endpointClass) {
							return (T) socket;
						}
					})
					.build();
			wscontainer.addEndpoint(config);
			webSocketServer.start();
			webSocketServer.join();

		} catch (Exception ex) {
			Logger.getInstance().log(ex);
		}
	}
}
