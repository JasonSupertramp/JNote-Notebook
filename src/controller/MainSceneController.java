package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import application.Main;
import bean.ArticleProperty;
import bean.CategoryProperty;
import dao.ArticleDao;
import dao.CategoryDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainSceneController {

	/** -----菜单栏-------- */
	@FXML
	MenuItem aboutItem;
	@FXML
	MenuBar menuBar;
	@FXML
	MenuItem closeMenuItem;
	@FXML
	MenuItem closeMenuItem1;
	@FXML
	MenuItem logoffMenuItem;

	/** -----左边栏 文章和类别管理-------- */
	@FXML
	Button articleManagement;
	@FXML
	Button categoryManagement;

	/** -----右侧显示界面-------- */
	@FXML
	BorderPane root;
	@FXML
	AnchorPane infoArea;
	@FXML
	BorderPane articleArea;
	@FXML
	AnchorPane bottomArea;
	@FXML
	AnchorPane sideArea;
	@FXML
	TitledPane naviArea;
	@FXML
	AnchorPane categoryBlock;
	@FXML
	AnchorPane articleBlock;

	/** -----文章管理界面-------- */
	@FXML
	Button newArticleButton;
	@FXML
	Button updateArticleButton;
	@FXML
	Button deleteArticleButton;
	@FXML
	final static TableView<ArticleProperty> articleTable = new TableView<ArticleProperty>();
	@FXML
	final TableColumn<ArticleProperty, String> artId = new TableColumn<ArticleProperty, String>("Id");
	@FXML
	final TableColumn<ArticleProperty, String> artIndex = new TableColumn<ArticleProperty, String>("序号");
	@FXML
	final TableColumn<ArticleProperty, String> artName = new TableColumn<ArticleProperty, String>("标题");
	@FXML
	final TableColumn<ArticleProperty, String> catNameOfArt = new TableColumn<ArticleProperty, String>("类名");
	@FXML
	final TableColumn<ArticleProperty, String> artTime = new TableColumn<ArticleProperty, String>("创建时间");

	// 相关文章变量
	private ArticleProperty ap;
	public static ArticleProperty getSelectedRowArt;
	private final VBox avbox = new VBox();
	private final HBox ahbox = new HBox();
	private int readOnce = 1;
	ObservableList<ArticleProperty> artData = FXCollections.observableArrayList();
	private static final Label artlabel = new Label();
	private Map<Integer, String> map = new LinkedHashMap<>();
	/** -----类别管理界面-------- */
	@FXML
	Button newCategoryButton;
	@FXML
	Button updateCategoryButton;
	@FXML
	Button deleteCategoryButton;
	@FXML
	final static TableView<CategoryProperty> categoryTable = new TableView<CategoryProperty>();
	@FXML
	final TableColumn<CategoryProperty, String> catId = new TableColumn<CategoryProperty, String>("Id");
	@FXML
	final TableColumn<CategoryProperty, String> catIndex = new TableColumn<CategoryProperty, String>("序号");
	@FXML
	final TableColumn<CategoryProperty, String> catName = new TableColumn<CategoryProperty, String>("类名");
	@FXML
	final TableColumn<CategoryProperty, String> catCreateTime = new TableColumn<CategoryProperty, String>("创建时间");

	// 相关类别变量
	public static CategoryProperty getSelectedRowCat;
	private static ObservableList<CategoryProperty> catData = FXCollections.observableArrayList();
	private CategoryProperty cp;
	private final VBox vbox = new VBox();
	private final HBox hbox = new HBox();
	private int readOrNot = 1;
	private static final Label label = new Label();
	/** -----全局变量-------- */
	private Stage stage = Main.getPrimaryStage();
	private Scene scene;

	public MainSceneController() {
		getSelectedRowCat = null;
		getSelectedRowArt = null;
		categoryTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getSelectedRowCat = (CategoryProperty) categoryTable.getSelectionModel().getSelectedItem();
			}
		});
		articleTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getSelectedRowArt = (ArticleProperty) articleTable.getSelectionModel().getSelectedItem();
				if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2) {
					stage = new Stage();
					try {
						scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/ArticleReadScene.fxml")));
					} catch (IOException e) {
						e.printStackTrace();
					}
					stage.setTitle("查看文章");
					stage.setScene(scene);
					stage.getIcons()
							.add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
					stage.show();
				}
			}
		});
	}

	// 菜单
	@FXML
	public void aboutItemClick(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION, "    这是一个基于JavaFX的笔记本Demo");
		alert.setTitle("关于");
		alert.setGraphic(
				new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("resources/logo.png"))));
		alert.setHeaderText("     Author:" + " Jason" + "\n\r" + "     Version: 1.0");
		alert.initOwner(stage);
		alert.show();
	}

	@FXML
	public void closeMenuItemClick(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	public void logoffMenuItemClick(ActionEvent event) {
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/LoginScene.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.setScene(scene);
		stage.show();
	}

	// 左边栏，点击文章按钮
	@FXML
	public void articleManagementClick(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		// 设置路径基准
		loader.setLocation(Main.class.getResource("/layout/ArticleScene.fxml"));
		try {
			articleBlock = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		articleArea.setCenter(articleBlock);

		newArticleButton = new Button("新建");
		newArticleButton.setPrefWidth(70);
		newArticleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage = new Stage();
				try {
					scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/ArticleEditScene.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				stage.setTitle("新建文章");
				stage.setScene(scene);
				stage.getIcons()
						.add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
				stage.show();
			}
		});

		updateArticleButton = new Button("修改");
		updateArticleButton.setPrefWidth(70);
		updateArticleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (getSelectedRowArt != null) {
					stage = new Stage();
					try {
						scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/ArticleUpdateScene.fxml")));
					} catch (IOException e) {
						e.printStackTrace();
					}
					stage.setTitle("修改文章");
					stage.setScene(scene);
					stage.getIcons()
							.add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
					stage.show();
				}
			}
		});

		deleteArticleButton = new Button("删除");
		deleteArticleButton.setPrefWidth(70);
		deleteArticleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (getSelectedRowArt != null) {
					ArticleDao.delete(getSelectedRowArt);
					loadArtData();
				}
			}
		});

		artId.setCellValueFactory(new PropertyValueFactory<ArticleProperty, String>("artId"));
		artIndex.setCellValueFactory(new PropertyValueFactory<ArticleProperty, String>("artIndex"));
		artName.setCellValueFactory(new PropertyValueFactory<ArticleProperty, String>("artName"));
		catNameOfArt.setCellValueFactory(new PropertyValueFactory<ArticleProperty, String>("catName"));
		artTime.setCellValueFactory(new PropertyValueFactory<ArticleProperty, String>("artCreateTime"));

		artId.setVisible(false);

		artId.setSortable(false);
		artIndex.setSortable(false);
		artName.setSortable(false);
		catNameOfArt.setSortable(false);
		artTime.setSortable(false);

		artId.setMaxWidth(100.0);
		artIndex.setMaxWidth(100.0);
		artName.setMaxWidth(150.0);
		catNameOfArt.setMaxWidth(150.0);
		artTime.setMaxWidth(150.0);

		artId.setMinWidth(100.0);
		artIndex.setMinWidth(100.0);
		artName.setMinWidth(150);
		catNameOfArt.setMinWidth(150.0);
		artTime.setMinWidth(150.0);
		loadArtData();

		if (readOnce == 1) {
			articleTable.getColumns().addAll(artId, artIndex, artName, catNameOfArt, artTime);
			// 调整控件间隔
			ahbox.setSpacing(5.0);
			ahbox.getChildren().addAll(newArticleButton, updateArticleButton, deleteArticleButton);
			avbox.setSpacing(5.0);
			avbox.getChildren().addAll(ahbox, articleTable, artlabel);
			avbox.setPadding(new Insets(50, 0, 0, 16));
			++readOnce;
		}
		artlabel.setText("共" + String.valueOf(artData.size()) + "条记录");
		articleBlock.getChildren().addAll(avbox);

	}

	// 点击分类按钮
	@FXML
	public void categoryManagementClick(ActionEvent event) {
		catData.clear();
		// 点击按钮，出现界面
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/layout/CategoryScene.fxml"));
		try {
			categoryBlock = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		articleArea.setCenter(categoryBlock);

		newCategoryButton = new Button("新建");
		newCategoryButton.setPrefWidth(70);
		newCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				stage = new Stage();
				try {
					scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/CategoryEditScene.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				stage.setTitle("新建类别");
				stage.getIcons()
						.add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
				stage.setScene(scene);
				stage.show();

			}
		});

		updateCategoryButton = new Button("修改");
		updateCategoryButton.setPrefWidth(70);
		updateCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (getSelectedRowCat != null) {
					stage = new Stage();
					try {
						scene = new Scene(FXMLLoader.load(getClass().getResource("/layout/CategoryUpdateScene.fxml")));
					} catch (IOException e) {
						e.printStackTrace();
					}
					stage.setTitle("修改类别");
					stage.getIcons()
							.add(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/logo.png")));
					stage.setScene(scene);
					stage.show();
				}
			}
		});

		deleteCategoryButton = new Button("删除");
		deleteCategoryButton.setPrefWidth(70);
		deleteCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (getSelectedRowCat != null) {
					if (!delDateExistInArtOrNot()) {
						CategoryDao.delete(getSelectedRowCat);
						loadCatData();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("删除失败");
						alert.setContentText("文章包含该类名，请先修改或删除文章");
						alert.initOwner(stage);
						alert.show();
					}
				}
			}
		});

		catId.setCellValueFactory(new PropertyValueFactory<CategoryProperty, String>("catId"));
		catIndex.setCellValueFactory(new PropertyValueFactory<CategoryProperty, String>("catIndex"));
		catName.setCellValueFactory(new PropertyValueFactory<CategoryProperty, String>("catName"));
		catCreateTime.setCellValueFactory(new PropertyValueFactory<CategoryProperty, String>("catCreateTime"));

		catId.setVisible(false);

		catId.setSortable(false);
		catIndex.setSortable(false);
		catName.setSortable(false);
		catCreateTime.setSortable(false);

		catId.setMaxWidth(100.0);
		catIndex.setMaxWidth(300.0);
		catName.setMaxWidth(225.0);
		catCreateTime.setMaxWidth(225.0);

		catId.setMinWidth(100.0);
		catIndex.setMinWidth(100.0);
		catName.setMinWidth(225.0);
		catCreateTime.setMinWidth(225.0);
		loadCatData();
		if (readOrNot == 1) {
			categoryTable.getColumns().addAll(catId, catIndex, catName, catCreateTime);
			// 调整控件间隔
			hbox.setSpacing(5.0);
			hbox.getChildren().addAll(newCategoryButton, updateCategoryButton, deleteCategoryButton);
			vbox.setSpacing(5.0);
			vbox.getChildren().addAll(hbox, categoryTable, label);
			vbox.setPadding(new Insets(50, 0, 0, 16));
			++readOrNot;
		}
		label.setText("共" + String.valueOf(catData.size()) + "条记录");
		categoryBlock.getChildren().addAll(vbox);
	}

	/** -----------------工具类------------------------- */
	// 类别表载入数据

	public void loadCatData() {
		int num = 1;
		catData.clear();
		// 读取数据库中数据
		ResultSet rs = CategoryDao.select();
		try {
			while (rs.next()) {
				String id = String.valueOf(rs.getInt("id"));
				String index = String.valueOf(num);
				String name = rs.getString("categoryName");
				String time = rs.getString("categoryCreateTime");
				cp = new CategoryProperty(id, index, name, time);
				catData.add(cp);
				num++;
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		label.setText("共" + String.valueOf(catData.size()) + "条记录");
		categoryTable.setItems(catData);
	}

	public void loadArtData() {
		int num = 1;
		initArtTable();
		artData.clear();
		// 读取数据库中数据
		ResultSet rs = ArticleDao.select();
		try {
			while (rs.next()) {
				String artId = rs.getString("articleId");
				String catId = rs.getString("categoryId");
				String index = String.valueOf(num);
				String artName = rs.getString("titleName");
				String catName = map.get(Integer.parseInt(catId));
				String content = rs.getString("articleContent");
				String time = rs.getString("createTime");
				ap = new ArticleProperty(artId, catId, index, artName, catName, content, time);
				artData.add(ap);
				num++;
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		artlabel.setText("共" + String.valueOf(artData.size()) + "条记录");
		articleTable.setItems(artData);
	}

	private void initArtTable() {
		ResultSet rs = CategoryDao.select();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("categoryName");
				map.put(id, name);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean delDateExistInArtOrNot() {
		boolean flag = false;
		ResultSet rs = ArticleDao.select();
		try {
			while (rs.next()) {
				int catId = rs.getInt("categoryId");
				if (getSelectedRowCat.getCatId() == catId) {
					flag = true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
// private void getSelectedRowId() {
// categoryTable.setOnMouseClicked(e -> {
// if (e.getButton().equals(MouseButton.PRIMARY)) {
// 展示 选中行列。注意：TablePosition还有表的 选中游标
// TablePosition pos = (TablePosition)
// categoryTable.getSelectionModel().getSelectedCells().get(0);
// String str = "ROW:" + Integer.toString(pos.getRow()) + ",COL:" +
// Integer.toString(pos.getColumn());
// CategoryProperty catProp = (CategoryProperty)
// categoryTable.getSelectionModel().getSelectedItem();
// Alert alert = new Alert(AlertType.INFORMATION);
// alert.setContentText(catProp.getCatName());
// alert.show();
// }
// });
// categoryTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
// @Override
// public void handle(MouseEvent event) {
// CategoryProperty cp2 = (CategoryProperty)
// categoryTable.getSelectionModel().getSelectedItem();
// cp2.setCatCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//
// Alert alert = new Alert(AlertType.INFORMATION);
// alert.setContentText(String.valueOf(getSelectedRowCat.getCatId()));
// alert.show();
// if (event.getClickCount() == 2) {
// stage = new Stage();
// try {
// scene = new
// Scene(FXMLLoader.load(getClass().getResource("/layout/CategoryUpdateScene.fxml")));
// } catch (IOException e) {
// e.printStackTrace();
// }
// stage.setTitle("修改类别");
// stage.setScene(scene);
// stage.show();
// }
// }
// });
// }