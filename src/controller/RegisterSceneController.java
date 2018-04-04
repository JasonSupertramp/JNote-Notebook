package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

import application.Main;
import bean.User;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterSceneController {

	@FXML
	private TextField registerUserName;
	@FXML
	private TextField registerPassword;
	@FXML
	private PasswordField registerPasswordRepeat;
	@FXML
	private Button registerButton2;
	@FXML
	private Button cancelButton2;
	@FXML
	private Button resetButton2;

	private User user = null;
	private ResultSet rs = null;
	private boolean flag = false;
	private Stage stage = Main.getPrimaryStage();
	private Scene scene;

	@FXML
	public void resetButtonPress(ActionEvent event) {
		registerUserName.setText("");
		registerPassword.setText("");
		registerPasswordRepeat.setText("");
		registerUserName.requestFocus();
	}

	@FXML
	public void cancelButtonPress(ActionEvent event) throws IOException {
		// ObservableList<Stage> stage = FXRobotHelper.getStages();
		scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/LoginScene.fxml")));
		stage.setScene(scene);
	}

	@FXML
	public void registerButtonPress(ActionEvent event) throws IOException {
		registerVerify();
	}

	public void registerVerify() throws IOException {
		if (Pattern.matches("^[a-zA-Z]{5,16}$", registerUserName.getText())
				& Pattern.matches("^(?![a-zA-Z]+$)(?![0-9]+$)[a-zA-Z_0-9]{5,16}$", registerPassword.getText())
				& registerPassword.getText().equals(registerPasswordRepeat.getText())) {
			user = new User();
			user.setUsername(registerUserName.getText());
			user.setPassword(registerPassword.getText());

			if (!usernameIsExist(user.getUsername())) {
				UserDao.insert(user);
				Alert alert = new Alert(AlertType.CONFIRMATION, "�Ƿ�Ҫ��ת��½���棿");
				alert.setTitle("ע��ɹ�");
				alert.setHeaderText("ע��ɹ�");
				alert.initOwner(stage);	
				// Caused by: java.lang.IllegalStateException: Stage already visible
				 //alert.show();
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/LoginScene.fxml")));
					stage.setScene(scene);
				}
			} else {
				Alert alert2 = new Alert(AlertType.CONFIRMATION, "�˺��Ѵ��ڣ��Ƿ���ת��½��");
				alert2.setTitle("�˺��Ѵ���");
				alert2.setHeaderText("�˺��Ѵ���");
				alert2.initOwner(stage);
				// alert2.show();
				Optional<ButtonType> result2 = alert2.showAndWait();
				if (result2.get() == ButtonType.OK) {
					scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/LoginScene.fxml")));
					stage.setScene(scene);
				}
			}
		} else {
			Alert alert3 = new Alert(AlertType.ERROR, "�˺Ż���������������ע��");
			alert3.setTitle("ע��ʧ��");
			alert3.setHeaderText("ע��ʧ��");
			alert3.initOwner(stage);
			alert3.show();
		}
	}

	private boolean usernameIsExist(String usernamefield) {
		rs = UserDao.select();
		try {
			while (rs.next()) {
				String username = rs.getString("username");
				if (username.equals(usernamefield)) {
					flag = true;
					rs.close();
					return flag;
				}
			}
			System.out.println(flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
