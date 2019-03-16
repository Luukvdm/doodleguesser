package clientapp.messaging;

import fontysmultipurposelibrary.communication.websockets.IClientWebSocket;
import socketgameshared.messaging.messages.AddPointToPathMessage;
import socketgameshared.messaging.messages.ClearCanvasMessage;
import socketgameshared.messaging.messages.fromclient.GuessWordMessage;
import socketgameshared.messaging.messages.fromclient.LoginMessage;
import socketgameshared.messaging.messages.fromclient.RegisterPlayerMessage;
import socketgameshared.messaging.messages.StartPathMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {
	private IClientWebSocket clientSocket;

	public ClientMessageGenerator(IClientWebSocket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void registerPlayerOnServer(String username, String password) {
		clientSocket.send(new RegisterPlayerMessage(username, password));
	}

	@Override
	public void login(String username, String password) {
		clientSocket.send(new LoginMessage(username, password));
	}

	@Override
	public void startPath(double x, double y, double colorR, double colorG, double colorB) {
		clientSocket.send(new StartPathMessage(x, y, colorR, colorG, colorB));
	}

	@Override
	public void addPointToPath(double x, double y) {
		clientSocket.send(new AddPointToPathMessage(x, y));
	}

	@Override
	public void clearCanvas() {
		clientSocket.send(new ClearCanvasMessage());
	}

	@Override
	public void makeGuess(String guess) {
		clientSocket.send(new GuessWordMessage(guess));
	}
}
