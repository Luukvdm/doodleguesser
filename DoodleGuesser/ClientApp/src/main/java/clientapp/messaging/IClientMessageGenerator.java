package clientapp.messaging;

public interface IClientMessageGenerator {
	void registerPlayerOnServer(String username, String password);

	void login(String username, String password);

	void startPath(double x, double y, double colorR, double colorG, double colorB);

	void addPointToPath(double x, double y);

	void clearCanvas();

	void makeGuess(String guess);
}
