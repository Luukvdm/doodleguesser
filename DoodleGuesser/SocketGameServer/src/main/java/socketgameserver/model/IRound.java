package socketgameserver.model;

public interface IRound {
	String getWord();

	IPlayer getDrawingPlayer();

	int getRoundNumber();

	int getTimerDelay();

	boolean guessWord(String guess);

	void startNewRound(IPlayer newDrawer);
}
