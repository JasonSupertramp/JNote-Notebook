package controller;

import java.io.IOException;

import application.Main;
import bean.User;
import javafx.event.ActionEvent;
/*
 * 每个FXML只有一个控制器，用于响应页面的各种事件
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CheckLogin;

public class LoginController {

	@FXML
	private Button loginButton;
	@FXML
	private Button registerButton;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField userNameField;
	@FXML
	private Button cancelButton;
	@FXML
	private Button resetButton;
	@FXML
	private AnchorPane loginScene;
	@FXML
	private Button exitButton;

	private Stage stage = Main.getPrimaryStage();;
	private Scene scene;
	private static final User user = new User();

	@FXML
	public void loginButtonClick(ActionEvent event) throws IOException {
		if (CheckLogin.isLogin(userNameField.getText(), passwordField.getText()) != -1) {
			scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/MainScene.fxml")));
			stage.setScene(scene);
			stage.show();
			user.setUserId(CheckLogin.isLogin(userNameField.getText(), passwordField.getText()));
			System.out.println(user.getUserId());
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("登录失败");
			alert.setHeaderText("登录失败");
			alert.setContentText("账号或密码错误");
			alert.initOwner(stage);
			alert.show();
		}
	}

	@FXML
	public void registerButtonClick(ActionEvent event) throws IOException {
		scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/RegisterScene.fxml")));
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void resetButtonClick(ActionEvent event) {
		userNameField.setText("");
		passwordField.setText("");
		userNameField.requestFocus();
	}

	@FXML
	public void exitButtonClick(ActionEvent event) {
		stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	public int getUserId() {
		return user.getUserId();
	}
}
