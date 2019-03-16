package socketgameshared.messaging.messages.fromserver;

import socketgameshared.messaging.messages.enums.PlayerRole;

public class StartNewRoundMessage {
	private String word;
	private PlayerRole playerRole;

	public String getWord() {
		if(playerRole == PlayerRole.DRAWER) return word;
		else return null;
	}

	public PlayerRole getPlayerRole() {
		return playerRole;
	}

	public StartNewRoundMessage(PlayerRole playerRole, String word) {
		this.playerRole = playerRole;
		this.word = word;
	}
}
