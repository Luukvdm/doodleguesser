package clientapp.messaging.clientgui;

public interface IClientLoginGUI {
	void processLoginResponse(String token, boolean isSuccess, String message);
}
