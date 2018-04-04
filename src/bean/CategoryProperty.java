package bean;

import javafx.beans.property.SimpleStringProperty;

public class CategoryProperty {

	private final SimpleStringProperty catId;
	private final SimpleStringProperty userId;
	private final SimpleStringProperty catIndex;
	private final SimpleStringProperty catName;
	private final SimpleStringProperty catCreateTime;

	public CategoryProperty(String catId, String userId, String catIndex, String catName, String catCreateTime) {
		this.catId = new SimpleStringProperty(catId);
		this.userId = new SimpleStringProperty(userId);
		this.catIndex = new SimpleStringProperty(catIndex);
		this.catName = new SimpleStringProperty(catName);
		this.catCreateTime = new SimpleStringProperty(catCreateTime);
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return Integer.parseInt(userId.get());
	}

	public void setUserId(String id) {
		userId.set(id);
	}

	/**
	 * @return the catIndex
	 */
	public String getCatIndex() {
		return catIndex.get();
	}

	/**
	 * @return the catId
	 */
	public int getCatId() {
		return Integer.parseInt(catId.get());
	}

	public String getCatName() {
		return catName.get();
	}

	public String getCatCreateTime() {
		return catCreateTime.get();
	}

	public void setCatIndex(String index) {
		catName.set(index);
	}

	public void setCatName(String fName) {
		catName.set(fName);
	}

	public void setCatCreateTime(String fName) {
		catCreateTime.set(fName);
	}
}
