package com.basic.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyJbexRequest implements Serializable{
     JbexInfo JbexInfo;
      List<User>  JbexuserList;
      
	public JbexInfo getJbexInfo() {
		return JbexInfo;
	}
	public void setJbexInfo(JbexInfo jbexInfo) {
		JbexInfo = jbexInfo;
	}
	public List<User> getJbexuserList() {
		return JbexuserList;
	}
	public void setJbexuserList(List<User> jbexuserList) {
		JbexuserList = jbexuserList;
	}
      
      
}
