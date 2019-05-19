package com.basic.bean;

import java.sql.Timestamp;

/**
 * TFriendrequest entity. @author MyEclipse Persistence Tools
 */

public class TFriendrequest implements java.io.Serializable {

	// Fields

	private Long id;
	private TUser TUserByUserId;
	private TUser TUserByFriendId;
	private String requestgroup;
	private Timestamp requestime;
	private String validationmessage;

	// Constructors

	/** default constructor */
	public TFriendrequest() {
	}

	/** minimal constructor */
	public TFriendrequest(TUser TUserByUserId, TUser TUserByFriendId) {
		this.TUserByUserId = TUserByUserId;
		this.TUserByFriendId = TUserByFriendId;
	}

	/** full constructor */
	public TFriendrequest(TUser TUserByUserId, TUser TUserByFriendId,
			String requestgroup, Timestamp requestime, String validationmessage) {
		this.TUserByUserId = TUserByUserId;
		this.TUserByFriendId = TUserByFriendId;
		this.requestgroup = requestgroup;
		this.requestime = requestime;
		this.validationmessage = validationmessage;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TUser getTUserByUserId() {
		return this.TUserByUserId;
	}

	public void setTUserByUserId(TUser TUserByUserId) {
		this.TUserByUserId = TUserByUserId;
	}

	public TUser getTUserByFriendId() {
		return this.TUserByFriendId;
	}

	public void setTUserByFriendId(TUser TUserByFriendId) {
		this.TUserByFriendId = TUserByFriendId;
	}

	public String getRequestgroup() {
		return this.requestgroup;
	}

	public void setRequestgroup(String requestgroup) {
		this.requestgroup = requestgroup;
	}

	public Timestamp getRequestime() {
		return this.requestime;
	}

	public void setRequestime(Timestamp requestime) {
		this.requestime = requestime;
	}

	public String getValidationmessage() {
		return this.validationmessage;
	}

	public void setValidationmessage(String validationmessage) {
		this.validationmessage = validationmessage;
	}

}