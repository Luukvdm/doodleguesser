package socketgameshared.messaging.messages.fromserver;

public class GuessResultMessage {
	private boolean guessedRight;
	private String guess;

	public boolean getGuessedRight() {
		return this.guessedRight;
	}

	public String getGuess() {
		return this.guess;
	}

	public GuessResultMessage(boolean guessedRight, String guess) {
		this.guessedRight = guessedRight;
		this.guess = guess;
	}
}
