package socketgameserver.model;

import fontysmultipurposelibrary.dataacces.Entity;

public class Player extends Entity implements IPlayer {

	private String playerUsername;
	private String sessionId;
	private String password;
	private boolean isDrawing = false, hasGuessedRight = false;
	private int score;

	public Player(String sessionId, String username) {
		this.playerUsername = username;
		this.sessionId = sessionId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return playerUsername;
	}

	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public boolean getIsDrawing() {
		return isDrawing;
	}

	@Override
	public void setIsDrawing(boolean isDrawing) {
		this.isDrawing = isDrawing;
	}

	@Override
	public boolean getHasGuessedRight() {
		if (this.isDrawing) return true;
		return hasGuessedRight;
	}

	@Override
	public void setHasGuessedRight(boolean hasGuessedRight) {
		this.hasGuessedRight = hasGuessedRight;
	}

	@Override
	public void givePoint() {
		score++;
	}

	@Override
	public int getPoints() {
		return this.score;
	}
}
