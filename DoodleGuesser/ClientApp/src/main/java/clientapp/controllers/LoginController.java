package clientapp.controllers;

import clientapp.messaging.clientgui.IClientLoginGUI;
import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BaseController implements Initializable, IClientLoginGUI {

	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	@FXML
	private Label lblMessage;
	@FXML
	private Button btnLogin;

	private BooleanProperty isWaiting = new SimpleBooleanProperty(false);

	private void setIsWaiting(boolean waiting) {
		this.isWaiting.setValue(waiting);
		Cursor cursor = Cursor.DEFAULT;
		if(isWaiting.getValue()) cursor = Cursor.WAIT;
		getPrimaryStage().getScene().setCursor(cursor);
	}

	public ObservableBooleanValue getIsWaiting() {
		return isWaiting;
	}

	public ObservableBooleanValue getFieldsNotEmpty() {
		return Bindings.or(tfUsername.textProperty().isEmpty(), tfPassword.textProperty().isEmpty());
	}

	public LoginController(IGameClient gameClient, Stage primaryStage) {
		super(gameClient, primaryStage);
		getGameClient().registerClientLoginGUI(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.disableProperty().bind(Bindings.or(getIsWaiting(), getFieldsNotEmpty()));
		tfUsername.disableProperty().bind(getIsWaiting());
		tfPassword.disableProperty().bind(getIsWaiting());
	}

	private void login() {
		//Doe de login shizzle
		String username = tfUsername.getText();
		String password = tfPassword.getText();

		getGameClient().loginPlayer(username, password);
		setIsWaiting(true);
	}

	private void register() {
		try {
			loadFxml("/fxml/RegisterForm.fxml");
		} catch (Exception e) {
			Logger.getInstance().log(e);
		}
	}

	@Override
	public void processLoginResponse(String token, boolean isSuccess, String message) {
		Platform.runLater(() -> {
			setIsWaiting(false);
			if(isSuccess) {
				getGameClient().removeClientLoginGUI();
				loadFxml("/fxml/GameGui.fxml");
			} else {
				lblMessage.setText(message);
			}
		});
	}

	public void btnSignInClicked(ActionEvent actionEvent) {
		login();
	}

	public void btnRegisterClicked(ActionEvent actionEvent) {
		register();
	}

	public void KeyPressed(KeyEvent keyEvent) {
		if(keyEvent.getCode().toString().equals("ENTER")) {
			login();
		}
	}
}
