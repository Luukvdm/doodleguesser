package socketgameshared.messaging.messages.fromserver;

public class LoginResultMessage {
	private String token;
	private boolean isSuccess;
	private String msg;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public boolean getSuccess() { return isSuccess; }
	public String getMessage()  { return msg; }

	public LoginResultMessage(String token, boolean isSuccess, String msg) {
		this.token = token;
		this.isSuccess = isSuccess;
		this.msg = msg;
	}
}
