package socketgameshared.messaging.messages.fromclient;

public class LoginMessage {
	private String playerUsername;
	private String password;

	public String getPassword() { return password; }
	public String getPlayerUsername(){
		return playerUsername;
	}

	public LoginMessage(String username, String password)
	{
		this.playerUsername = username;
		this.password = password;
	}
}
