package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private static Stage primaryStage;
	private AnchorPane loginPane;

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.primaryStage = primaryStage;

			loginPane = FXMLLoader.load(getClass().getResource("/layout/LoginScene.fxml"));
			Scene scene = new Scene(loginPane);
			primaryStage.setTitle("JNote");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons()
					.add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ·µ»ØÖ÷ÎèÌ¨ main stage
	 * 
	 * @return
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
