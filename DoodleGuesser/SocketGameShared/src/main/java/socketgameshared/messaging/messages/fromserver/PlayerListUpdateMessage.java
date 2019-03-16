package socketgameshared.messaging.messages.fromserver;

import socketgameshared.messaging.messages.enums.PlayerUpdate;

public class PlayerListUpdateMessage {
	private String playerName;
	private PlayerUpdate playerUpdate;

	public String getPlayerName() { return playerName; }
	public PlayerUpdate getPlayerUpdate() { return playerUpdate; }

	public PlayerListUpdateMessage(String playerName, PlayerUpdate playerUpdate) {
		this.playerName = playerName;
		this.playerUpdate = playerUpdate;
	}
}
