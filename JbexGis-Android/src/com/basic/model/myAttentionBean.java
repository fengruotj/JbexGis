package com.basic.model;

public class myAttentionBean {
      private String myAttentionPhoto;
      private String myAttentionUsername;
      private String myAttentionSchoolInfo;
      private String myAttentionMajor;
      private int myAttentionSex;
      

	
	public myAttentionBean(String myAttentionPhoto, String myAttentionUsername,
			String myAttentionSchoolInfo, String myAttentionMajor,
			int myAttentionSex) {
		super();
		this.myAttentionPhoto = myAttentionPhoto;
		this.myAttentionUsername = myAttentionUsername;
		this.myAttentionSchoolInfo = myAttentionSchoolInfo;
		this.myAttentionMajor = myAttentionMajor;
		this.myAttentionSex = myAttentionSex;
	}
	public String getMyAttentionMajor() {
		return myAttentionMajor;
	}
	public void setMyAttentionMajor(String myAttentionMajor) {
		this.myAttentionMajor = myAttentionMajor;
	}
	public String getMyAttentionPhoto() {
		return myAttentionPhoto;
	}
	public void setMyAttentionPhoto(String myAttentionPhoto) {
		this.myAttentionPhoto = myAttentionPhoto;
	}
	public String getMyAttentionUsername() {
		return myAttentionUsername;
	}
	public void setMyAttentionUsername(String myAttentionUsername) {
		this.myAttentionUsername = myAttentionUsername;
	}
	public String getMyAttentionSchoolInfo() {
		return myAttentionSchoolInfo;
	}
	public void setMyAttentionSchoolInfo(String myAttentionSchoolInfo) {
		this.myAttentionSchoolInfo = myAttentionSchoolInfo;
	}
	public int getMyAttentionSex() {
		return myAttentionSex;
	}
	public void setMyAttentionSex(int myAttentionSex) {
		this.myAttentionSex = myAttentionSex;
	}
      
      
}
