package clientapp.controllers;

import clientapp.messaging.clientgui.IClientRegisterGui;
import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController extends BaseController implements IClientRegisterGui {

	@FXML private TextField tfUsername;
	@FXML private PasswordField tfPassword1;
	@FXML private PasswordField tfPassword2;
	@FXML private Label lblMessage;
	@FXML private Button btnRegister;

	public RegisterController(IGameClient gameClient, Stage primaryStage) {
		super(gameClient, primaryStage);
		getGameClient().registerClientRegisterGUI(this);
	}

	private void register() {
		String username = tfUsername.getText();
		String password1 = tfPassword1.getText();
		String password2 = tfPassword2.getText();
		if(!password1.equals(password2)) {
			lblMessage.setText("Passwords don't match");
		} else if(username.isEmpty() || password1.isEmpty()) {
			lblMessage.setText("Username or password is empty");
		} else {
			//Doe de register shizzle
			getGameClient().registerPlayer(username, password1);
			startWaiting();
		}
	}

	private void startWaiting() {
		getPrimaryStage().getScene().setCursor(Cursor.WAIT);
		//Lock the controls
		tfUsername.setDisable(true);
		tfPassword1.setDisable(true);
		tfPassword2.setDisable(true);
		btnRegister.setDisable(true);
	}

	private void endWaiting() {
		getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
		//UnLock the controls
		tfUsername.setDisable(false);
		tfPassword1.setDisable(false);
		tfPassword2.setDisable(false);
		btnRegister.setDisable(false);
	}

	@Override
	public void processRegistrationResponse(boolean resp, String reason) {
		Platform.runLater(() -> {
			if(resp) {
				getGameClient().removeClientRegisterGUI();
				loadFxml("/fxml/LoginForm.fxml");
			} else {
				showAlert("Register result", reason);
			}
			endWaiting();
		});
	}

	public void btnBackClicked() {
		try {
			ControllerHelper.getInstance().loadFxml("/fxml/LoginForm.fxml");
		} catch (Exception e) {
			Logger.getInstance().log(e);
		}
	}

	public void btnRegisterClicked() {
		register();
	}
}
