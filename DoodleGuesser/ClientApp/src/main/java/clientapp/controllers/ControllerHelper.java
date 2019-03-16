package clientapp.controllers;

import clientapp.messaging.IGameClient;
import fontysmultipurposelibrary.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class ControllerHelper {

	private Stage primaryStage;
	private IGameClient gameClient;

	//Methods for Singleton behaviour
	private static ControllerHelper instance = null;

	public static ControllerHelper getInstance() {
		if (instance == null) {
			instance = new ControllerHelper();
		}
		return instance;
	}

	public void loadFxml(String fxml) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        /*
                USE REFLECTION FOR DEPENDENCY INJECTION:
                INJECT EVERY CONTROLLER INSTANCE, WHICH IS CREATED DYNAMICALLY WHILE LOADING THE FXML,
                WITH THE SAME GAME CLIENT INSTANCE
         */
		loader.setControllerFactory((Class<?> type) -> {
			try {
				// look for constructor taking MyService as a parameter
				for (Constructor<?> c : type.getConstructors()) {
					if (c.getParameterCount() == 2 && c.getParameterTypes()[0] == IGameClient.class && c.getParameterTypes()[1] == Stage.class) {
						return c.newInstance(gameClient, primaryStage);
					}
				}
				// didn't find appropriate constructor, just use default constructor:
				return type.newInstance();
			} catch (Exception exc) {
				Logger.getInstance().log(exc);
				return null;
			}
		});


		Parent root = loader.load();
		this.primaryStage.setTitle("Doodle Guesser");
		this.primaryStage.setScene(new Scene(root, 1200, 800));
		this.primaryStage.setResizable(false);
		this.primaryStage.show();
	}

	public void loadFxml(String fxml,IGameClient client, Stage primaryStage) throws IOException {
		if(this.gameClient== null)
			this.gameClient = client;
		if(this.primaryStage==null)
			this.primaryStage = primaryStage;

		loadFxml(fxml);
	}

}
