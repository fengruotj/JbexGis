package com.basic.model;

import java.util.List;

public class FriendsGroupBean {

    private String GroupName;      //聊天分组的名字
    private String online_txt;     // 分组中在线的数目
    private List<FriendsBean> Friendslist;    //聊天中好友的列表
    
    
    public FriendsGroupBean(String groupName, String online_txt,
			List<FriendsBean> friendslist) {
		super();
		GroupName = groupName;
		this.online_txt = online_txt;
		Friendslist = friendslist;
	}

	public String getOnline_txt() {
		return online_txt;
	}

	public void setOnline_txt(String online_txt) {
		this.online_txt = online_txt;
	}

	public List<FriendsBean> getFriendslist() {
		return Friendslist;
	}
	
	public void setFriendslist(List<FriendsBean> friendslist) {
		Friendslist = friendslist;
	}

	public FriendsGroupBean() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	@Override
	public String toString() {
		return "FriendsGroupBean [GroupName=" + GroupName + ", online_txt="
				+ online_txt + ", Friendslist=" + Friendslist + "]";
	}

}
