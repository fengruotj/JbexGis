package com.basic.model;

public class FriendsBean {
      private String PhotoDrawableId;
      private boolean Online;
      private String FriendsName;
      private String FriendsAutograph;
      
	public FriendsBean(String photoDrawableId, boolean online, String friendsName,
			String friendsAutograph) {
		super();
		PhotoDrawableId = photoDrawableId;
		Online = online;
		FriendsName = friendsName;
		FriendsAutograph = friendsAutograph;
	}
	
	public String getPhotoDrawableId() {
		return PhotoDrawableId;
	}
	
	public void setPhotoDrawableId(String photoDrawableId) {
		PhotoDrawableId = photoDrawableId;
	}
	
	public boolean isOnline() {
		return Online;
	}
	
	public void setOnline(boolean online) {
		Online = online;
	}
	
	public String getFriendsName() {
		return FriendsName;
	}
	
	public void setFriendsName(String friendsName) {
		FriendsName = friendsName;
	}
	
	public String getFriendsAutograph() {
		return FriendsAutograph;
	}
	
	public void setFriendsAutograph(String friendsAutograph) {
		FriendsAutograph = friendsAutograph;
	}
	
	public FriendsBean() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public String toString() {
		return "FriendsBean [PhotoDrawableId=" + PhotoDrawableId + ", Online="
				+ Online + ", FriendsName=" + FriendsName
				+ ", FriendsAutograph=" + FriendsAutograph + "]";
	}
      
      
}
