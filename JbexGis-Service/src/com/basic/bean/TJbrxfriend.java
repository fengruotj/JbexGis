package com.basic.bean;

/**
 * TJbrxfriend entity. @author MyEclipse Persistence Tools
 */

public class TJbrxfriend implements java.io.Serializable {

	// Fields

	private TJbrxfriendId id;
	private TUser TUserByUserId;
	private TUser TUserByJbrxfriendId;
	private TJbrxinfo TJbrxinfo;

	// Constructors

	/** default constructor */
	public TJbrxfriend() {
	}

	/** full constructor */
	public TJbrxfriend(TJbrxfriendId id, TUser TUserByUserId,
			TUser TUserByJbrxfriendId, TJbrxinfo TJbrxinfo) {
		this.id = id;
		this.TUserByUserId = TUserByUserId;
		this.TUserByJbrxfriendId = TUserByJbrxfriendId;
		this.TJbrxinfo = TJbrxinfo;
	}

	// Property accessors

	public TJbrxfriendId getId() {
		return this.id;
	}

	public void setId(TJbrxfriendId id) {
		this.id = id;
	}

	public TUser getTUserByUserId() {
		return this.TUserByUserId;
	}

	public void setTUserByUserId(TUser TUserByUserId) {
		this.TUserByUserId = TUserByUserId;
	}

	public TUser getTUserByJbrxfriendId() {
		return this.TUserByJbrxfriendId;
	}

	public void setTUserByJbrxfriendId(TUser TUserByJbrxfriendId) {
		this.TUserByJbrxfriendId = TUserByJbrxfriendId;
	}

	public TJbrxinfo getTJbrxinfo() {
		return this.TJbrxinfo;
	}

	public void setTJbrxinfo(TJbrxinfo TJbrxinfo) {
		this.TJbrxinfo = TJbrxinfo;
	}

}