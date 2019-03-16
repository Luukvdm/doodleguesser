package socketgameserver.model;

public interface IGame {
	void loginPlayer(String userName, String password, String sessionId);

	void registerNewPlayer(String username, String password, String sessionId);

	void processClientDisconnect(String sessionId);

	void startPath(String sessionId, double x, double y, double colorR, double colorG, double colorB);

	void addPointToPath(String sessionId, double x, double y);

	void clearCanvas(String sessionId);

	void guessWord(String sessionId, String guess);

	int getNumberOfPlayers();
}
