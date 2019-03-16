package socketgameshared.messaging.messages.fromserver;

public class UpdateChatMessage {
	private String playerName, message;

	public String getMessage() {
		return this.message;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public UpdateChatMessage(String playerName, String message) {
		this.playerName = playerName;
		this.message = message;
	}
}
