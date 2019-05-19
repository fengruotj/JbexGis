package com.basic.service.model;

import java.io.Serializable;
import java.sql.Timestamp;


@SuppressWarnings("serial")
public class FriendRequest implements Serializable{
	private int id;
    User owneruser;    //好友请求的发出者
    private String requestgroup;
	private Timestamp requestime;
	private String validationmessage;
	
	public FriendRequest() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwneruser() {
		return owneruser;
	}
	public void setOwneruser(User owneruser) {
		this.owneruser = owneruser;
	}
	public String getRequestgroup() {
		return requestgroup;
	}
	public void setRequestgroup(String requestgroup) {
		this.requestgroup = requestgroup;
	}
	public Timestamp getRequestime() {
		return requestime;
	}
	public void setRequestime(Timestamp requestime) {
		this.requestime = requestime;
	}
	public String getValidationmessage() {
		return validationmessage;
	}
	public void setValidationmessage(String validationmessage) {
		this.validationmessage = validationmessage;
	}
	@Override
	public String toString() {
		return "FriendRequest [id=" + id + ", owneruser=" + owneruser
				+ ", requestgroup=" + requestgroup + ", requestime="
				+ requestime + ", validationmessage=" + validationmessage + "]";
	}
	
	
}
