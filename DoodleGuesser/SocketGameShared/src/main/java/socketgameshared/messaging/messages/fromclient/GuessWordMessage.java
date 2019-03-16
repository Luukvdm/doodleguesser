package socketgameshared.messaging.messages.fromclient;

public class GuessWordMessage {
	private String guess;

	public String getGuess() {
		return guess;
	}

	public GuessWordMessage(String guess) {
		this.guess = guess;
	}
}
