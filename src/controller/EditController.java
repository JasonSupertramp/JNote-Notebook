package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import bean.Article;
import bean.Category;
import dao.ArticleDao;
import dao.CategoryDao;
import javafx.event.ActionEvent;
import javafx.scene.web.HTMLEditor;

public class EditController implements Initializable {

	@FXML
	ChoiceBox<String> readCategoryBox = new ChoiceBox<>();
	@FXML
	TextField artTitleField;
	@FXML
	HTMLEditor editorField;
	@FXML
	Button artSubmitButton;
	@FXML
	Button artCancelButton;
	@FXML
	TextField newCategoryField;
	@FXML
	Button catSubmitButton;
	@FXML
	Button catCancelButton;
	@FXML
	Button catResetButton;
	@FXML
	Button artResetButton;

	private Stage stage = null;
	private Map<Integer, String> map = new LinkedHashMap<>();
	private ResultSet rs = null;
	private int count = 0;
	private Set<Integer> keySet = null;
	private int j = 1;
	private int k = 1;
	private int l = 1;

	private Article art;
	private Category cat;
	@FXML
	ChoiceBox<String> readCategoryUpdateBox = new ChoiceBox<>();
	@FXML
	TextField artTitleUpdateField = new TextField();
	@FXML
	HTMLEditor editorUpdateField = new HTMLEditor();
	@FXML
	Button artSubmitUpdateButton;
	@FXML
	Button artCancelUpdateButton;
	@FXML
	Button artResetUpdateButton;
	@FXML
	TextField newCategoryUpdateField;
	@FXML
	Button catSubmitUpdateButton;
	@FXML
	Button catCancelUpdateButton;
	@FXML
	Button catResetUpdateButton;
	@FXML
	ChoiceBox<String> readCategoryReadBox = new ChoiceBox<>();
	@FXML
	TextField artTitleReadField = new TextField();
	@FXML
	HTMLEditor editorReadField = new HTMLEditor();
	@FXML
	Button artCloseReadButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (MainSceneController.getSelectedRowArt != null) {
			// 修改文章
			readCategoryUpdateBox.setValue(MainSceneController.getSelectedRowArt.getCatName());
			artTitleUpdateField.setText(MainSceneController.getSelectedRowArt.getArtName());
			editorUpdateField.setHtmlText(MainSceneController.getSelectedRowArt.getArtContent());
			updateChoiceBoxReadCat();
			// 读取文章

			readCategoryReadBox.setValue(MainSceneController.getSelectedRowArt.getCatName());
			artTitleReadField.setText(MainSceneController.getSelectedRowArt.getArtName());
			editorReadField.setHtmlText(MainSceneController.getSelectedRowArt.getArtContent());
			readChoiceBoxReadCat();
			readCategoryReadBox.setDisable(true);
			artTitleReadField.setDisable(true);
			editorReadField.setDisable(true);

		}

	}

	/** --------文章新建页面------------ */
	@FXML
	public void readCategoryBoxClick(MouseEvent event) {
		choiceBoxReadCat();
	}

	@FXML
	public void artSubmitButtonClick(ActionEvent event) {
		try {
			art = new Article();
			art.setUserId(new LoginController().getUserId());
			art.setCategoryId(getCatId(readCategoryBox.getValue()));
			art.setTitleName(artTitleField.getText());
			art.setArticleContent(editorField.getHtmlText());
			art.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			System.out.println(art.getCreateTime());
			if (readCategoryBox.getValue() != "" && artTitleField.getText().length() != 0) {
				ArticleDao.insert(art);
				stage = (Stage) artSubmitButton.getScene().getWindow();
				stage.close();
				new MainSceneController().loadArtData();
			} else {
				Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
				alert.setTitle("创建失败");
				alert.setHeaderText("创建失败");
				alert.show();
			}
		} catch (NullPointerException ne) {
			Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
			alert.setTitle("创建失败");
			alert.setHeaderText("创建失败");
			alert.show();
		}
	}

	@FXML
	public void artCancelButtonClick(ActionEvent event) {
		Stage stage = (Stage) artCancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void artResetButtonClick(ActionEvent event) {
		editorField.setHtmlText("");
		editorField.requestFocus();
	}

	/** -----------------------文章修改界面-------------------------- */
	@FXML
	public void readCategoryBoxUpdateClick() {
		choiceBoxReadCat();
	}

	@FXML
	public void artSubmitButtonUpdateClick(ActionEvent event) {
		try {
			art = new Article();
			art.setArticleId(MainSceneController.getSelectedRowArt.getArtId());			
			art.setCategoryId(getCatId(readCategoryUpdateBox.getValue()));
			art.setTitleName(artTitleUpdateField.getText());
			art.setArticleContent(editorUpdateField.getHtmlText());
			art.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

			if (readCategoryUpdateBox.getValue() != "" && artTitleUpdateField.getText().length() != 0) {
				ArticleDao.update(art);
				stage = (Stage) artSubmitUpdateButton.getScene().getWindow();
				stage.close();
				new MainSceneController().loadArtData();
			} else {
				Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
				alert.setTitle("创建失败");
				alert.setHeaderText("创建失败");
				alert.show();
			}
		} catch (NullPointerException ne) {
			Alert alert = new Alert(AlertType.ERROR, "类名或题目不能为空");
			alert.setTitle("创建失败");
			alert.setHeaderText("创建失败");
			alert.show();
		}
	}

	@FXML
	public void artCancelButtonUpdateClick(ActionEvent event) {
		Stage stage = (Stage) artCancelUpdateButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void artResetButtonUpdateClick(ActionEvent event) {
		artTitleUpdateField.setText("");
		editorUpdateField.setHtmlText("");
		editorUpdateField.requestFocus();
	}

	/** -------------------文章查看页面--------------------------- */
	@FXML
	public void artCloseButtonReadClick(ActionEvent event) {
		Stage stage = (Stage) artCloseReadButton.getScene().getWindow();
		stage.close();
	}

	/** -------------------类别新建页面--------------------------- */
	@FXML
	public void catSubmitButtonClick(ActionEvent event) {
		cat = new Category();
		cat.setUserId(new LoginController().getUserId());
		System.out.println(cat.getUserId());
		cat.setCategoryName(newCategoryField.getText());
		cat.setCategoryCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if (newCategoryField.getText().length() != 0) {
			CategoryDao.insert(cat);
			stage = (Stage) catSubmitButton.getScene().getWindow();
			stage.close();
			new MainSceneController().loadCatData();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "类名不能为空");
			alert.setTitle("创建失败");
			alert.setHeaderText("创建失败");
			alert.show();
		}
	}

	@FXML
	public void catCancelButtonClick(ActionEvent event) {
		Stage stage = (Stage) catCancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void catResetButtonClick(ActionEvent event) {
		newCategoryField.setText("");
		newCategoryField.requestFocus();
	}

	/** ----------------类别修改界面--------------------------------------- */
	@FXML
	public void catSubmitUpdateButtonClick(ActionEvent event) {
		MainSceneController.getSelectedRowCat.setCatCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		MainSceneController.getSelectedRowCat.setCatName(newCategoryUpdateField.getText());
		if (newCategoryUpdateField.getText().length() != 0) {
			CategoryDao.update(MainSceneController.getSelectedRowCat);
			stage = (Stage) catSubmitUpdateButton.getScene().getWindow();
			stage.close();
			new MainSceneController().loadCatData();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "类名不能为空");
			alert.setTitle("创建失败");
			alert.setHeaderText("创建失败");
			alert.show();
		}
	}

	@FXML
	public void catCancelUpdateButtonClick(ActionEvent event) {
		Stage stage = (Stage) catCancelUpdateButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void catResetUpdateButtonClick(ActionEvent event) {
		newCategoryUpdateField.setText("");
		newCategoryUpdateField.requestFocus();
	}

	/** -----------------------工具--------------------------------- */
	// 新建文章初始化
	private void choiceBoxReadCat() {
		initArticleEditScene();
		keySet = map.keySet();
		for (Integer i : keySet) {
			String name = map.get(i);
			if (j < count) {
				readCategoryBox.getItems().add(name);
				j++;
			}
		}
	}

	// 修改文章初始化
	private void updateChoiceBoxReadCat() {
		initArticleEditScene();
		keySet = map.keySet();
		for (Integer i : keySet) {
			String name = map.get(i);
			if (k < count) {
				readCategoryUpdateBox.getItems().add(name);
				k++;
			}
		}
	}

	// 查看文章初始化
	private void readChoiceBoxReadCat() {
		initArticleEditScene();
		keySet = map.keySet();
		for (Integer i : keySet) {
			String name = map.get(i);
			if (l < count) {
				readCategoryReadBox.getItems().add(name);
				l++;
			}
		}
	}

	// 文章编辑界面初始化时获取类别数据
	private void initArticleEditScene() {
		count = 1;
		rs = CategoryDao.select();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId=rs.getInt("userId");
				String name = rs.getString("categoryName");
				if(userId==new LoginController().getUserId()) {
					map.put(id, name);
					count++;	
				}			
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** ---------------根据键找值---------------- */
	private Integer getCatId(String catName) {
		Set<Map.Entry<Integer, String>> set = map.entrySet();
		for (Map.Entry<Integer, String> me : set) {
			Integer id = me.getKey();
			String name = me.getValue();
			if (name.equals(catName)) {
				return id;
			}
		}
		return null;
	}

}
