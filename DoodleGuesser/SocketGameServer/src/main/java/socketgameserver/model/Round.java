package socketgameserver.model;

import java.io.InputStream;

public class Round implements IRound {

	private static final int delay = 60 * 1000; //1 minute
	private int roundNr = 0;
	private String word;
	private IPlayer drawer;
	private String wordFile;

	public Round(IPlayer startingDrawer, String wordFile) {
		this.wordFile = wordFile;
		startNewRound(startingDrawer);
	}

	@Override
	public String getWord() {
		return word;
	}

	@Override
	public IPlayer getDrawingPlayer() {
		return drawer;
	}

	@Override
	public int getRoundNumber() {
		return roundNr;
	}

	@Override
	public int getTimerDelay() {
		return delay;
	}

	@Override
	public boolean guessWord(String guess) {
		//Normalize the guess
		guess = guess.replace("-", "")
				.replace("!", "")
				.replace("?", "");

		return guess.equals(word);
	}

	@Override
	public void startNewRound(IPlayer newDrawer) {
		this.roundNr++;

		InputStream stream = this.getClass().getResourceAsStream(wordFile);
		word = WordPicker.getRandomWord(stream);
		if (drawer != null) {
			drawer.setIsDrawing(false);
		}
		drawer = newDrawer;
		newDrawer.setIsDrawing(true);
	}
}
