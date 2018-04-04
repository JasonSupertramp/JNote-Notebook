package bean;

public class Category {

	private int id;
	private int userId;
	private String categoryName;
	private String categoryCreateTime;

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the categoryCreateTime
	 */
	public String getCategoryCreateTime() {
		return categoryCreateTime;
	}

	/**
	 * @param categoryCreateTime
	 *            the categoryCreateTime to set
	 */
	public void setCategoryCreateTime(String categoryCreateTime) {
		this.categoryCreateTime = categoryCreateTime;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + "]";
	}

}
