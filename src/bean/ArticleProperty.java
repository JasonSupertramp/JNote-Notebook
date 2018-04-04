package bean;

import javafx.beans.property.SimpleStringProperty;

public class ArticleProperty {
	private final SimpleStringProperty artId;
	private final SimpleStringProperty catId;
	private final SimpleStringProperty catName;
	private final SimpleStringProperty artIndex;
	private final SimpleStringProperty artName;
	private final SimpleStringProperty artContent;
	private final SimpleStringProperty artCreateTime;

	public ArticleProperty(String artId, String catId, String artIndex, String artName, String catName,
			String artContent, String artCreateTime) {
		this.artId = new SimpleStringProperty(artId);
		this.catId = new SimpleStringProperty(catId);
		this.artIndex = new SimpleStringProperty(artIndex);
		this.artName = new SimpleStringProperty(artName);
		this.catName = new SimpleStringProperty(catName);
		this.artContent = new SimpleStringProperty(artContent);
		this.artCreateTime = new SimpleStringProperty(artCreateTime);
	}

	// getter
	public int getArtId() {
		return Integer.parseInt(artId.get());
	}

	public int getCatId() {
		return Integer.parseInt(catId.get());
	}

	public String getCatName() {
		return catName.get();
	}
	
	public String getArtIndex() {
		return artIndex.get();
	}

	public String getArtName() {
		return artName.get();
	}

	public String getArtContent() {
		return artContent.get();
	}

	public String getArtCreateTime() {
		return artCreateTime.get();
	}

	// setter
	public void setArtIndex(String index) {
		this.artIndex.set(index);
	}

	public void setArtName(String name) {
		this.artIndex.set(name);
	}

	public void setArtContent(String content) {
		this.artIndex.set(content);
	}

	public void setArtTime(String time) {
		this.artIndex.set(time);
	}

}
