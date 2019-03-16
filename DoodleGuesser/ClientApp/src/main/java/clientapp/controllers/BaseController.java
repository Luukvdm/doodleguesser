package clientapp.controllers;

import fontysmultipurposelibrary.logging.Logger;
import clientapp.messaging.IGameClient;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class BaseController {
	protected IGameClient getGameClient() {
		return gameClient  ;
	}
	protected Stage getPrimaryStage()     { return primaryStage; }

	private IGameClient gameClient;
	private Stage primaryStage;

	public BaseController(IGameClient gameClient, Stage primaryStage)
	{
		this.gameClient = gameClient;
		this.primaryStage = primaryStage;
	}

	public void loadFxml(String fxml)
	{
		try {
			ControllerHelper.getInstance().loadFxml(fxml);
		} catch (Exception e) {
			Logger.getInstance().log(e);
		}
	}

	protected void showAlert(String header, String content)
	{
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
