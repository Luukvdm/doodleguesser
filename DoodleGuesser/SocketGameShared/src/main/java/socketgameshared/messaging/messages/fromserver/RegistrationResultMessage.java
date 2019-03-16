package socketgameshared.messaging.messages.fromserver;

public class RegistrationResultMessage {
	private boolean result;
	private String reason;

	public boolean isResult() {
		return result;
	}
	public String  getReason() { return reason; }

	public RegistrationResultMessage(boolean result, String reason)
	{
		this.result = result;
		this.reason = reason;
	}
}
