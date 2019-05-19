package com.basic.model;

public class CollectionsBean {
	private int CollectionsPhoto;    //收藏的用户名的图片ID号
	private String CollectionsName;      //收藏好友的姓名
	private int    CollectionsSex;       //收藏好友的性别      0 未知 1男  2女
	private String CollectionsRestTime;      //收藏的剩余时间
	private String CollectionsTime;       //收藏的结伴时间
	private String Collectionsplace;      //收藏的结伴地点
	private String CollectionsType;       //收藏结伴的类型
	private String CollectionsTitle;      //收藏结伴的标题
	private String CollectionsDetails;      //收藏结伴的细节
	
	public CollectionsBean(int collectionsPhoto, String collectionsName,
            int collectionsSex,
			String collectionsRestTime, String collectionsTime,
			String collectionsplace, String collectionsType,
			String collectionsTitle, String collectionsDetails) {
		super();
		CollectionsPhoto = collectionsPhoto;
		CollectionsName = collectionsName;
		CollectionsSex = collectionsSex;
		CollectionsRestTime = collectionsRestTime;
		CollectionsTime = collectionsTime;
		Collectionsplace = collectionsplace;
		CollectionsType = collectionsType;
		CollectionsTitle = collectionsTitle;
		CollectionsDetails = collectionsDetails;
	}

	public int getCollectionsSex() {
		return CollectionsSex;
	}

	public void setCollectionsSex(int collectionsSex) {
		CollectionsSex = collectionsSex;
	}

	public int getCollectionsPhoto() {
		return CollectionsPhoto;
	}

	public void setCollectionsPhoto(int collectionsPhoto) {
		CollectionsPhoto = collectionsPhoto;
	}

	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return super.toString();
	}
	
	public String getCollectionsName() {
		return CollectionsName;
	}
	public void setCollectionsName(String collectionsName) {
		CollectionsName = collectionsName;
	}

	public String getCollectionsRestTime() {
		return CollectionsRestTime;
	}
	public void setCollectionsRestTime(String collectionsRestTime) {
		CollectionsRestTime = collectionsRestTime;
	}
	public String getCollectionsTime() {
		return CollectionsTime;
	}
	public void setCollectionsTime(String collectionsTime) {
		CollectionsTime = collectionsTime;
	}
	public String getCollectionsplace() {
		return Collectionsplace;
	}
	public void setCollectionsplace(String collectionsplace) {
		Collectionsplace = collectionsplace;
	}
	public String getCollectionsType() {
		return CollectionsType;
	}
	public void setCollectionsType(String collectionsType) {
		CollectionsType = collectionsType;
	}
	public String getCollectionsTitle() {
		return CollectionsTitle;
	}
	public void setCollectionsTitle(String collectionsTitle) {
		CollectionsTitle = collectionsTitle;
	}
	public String getCollectionsDetails() {
		return CollectionsDetails;
	}
	public void setCollectionsDetails(String collectionsDetails) {
		CollectionsDetails = collectionsDetails;
	}
	
	
}

