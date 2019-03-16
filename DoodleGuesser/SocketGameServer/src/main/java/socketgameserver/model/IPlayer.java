package socketgameserver.model;

public interface IPlayer {
	String getUsername();

	String getSessionId();

	boolean getIsDrawing();

	void setIsDrawing(boolean isDrawing);

	boolean getHasGuessedRight();

	void setHasGuessedRight(boolean hasGuessedRight);

	void givePoint();

	int getPoints();
}
