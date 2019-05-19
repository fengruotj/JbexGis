package com.basic.bean;

/**
 * TGroupfriends entity. @author MyEclipse Persistence Tools
 */

public class TGroupfriends implements java.io.Serializable {

	// Fields

	private TGroupfriendsId id;
	private TUser TUserByFriendsId;
	private TUser TUserByUserId;

	// Constructors

	/** default constructor */
	public TGroupfriends() {
	}

	/** full constructor */
	public TGroupfriends(TGroupfriendsId id, TUser TUserByFriendsId,
			TUser TUserByUserId) {
		this.id = id;
		this.TUserByFriendsId = TUserByFriendsId;
		this.TUserByUserId = TUserByUserId;
	}

	// Property accessors

	public TGroupfriendsId getId() {
		return this.id;
	}

	public void setId(TGroupfriendsId id) {
		this.id = id;
	}

	public TUser getTUserByFriendsId() {
		return this.TUserByFriendsId;
	}

	public void setTUserByFriendsId(TUser TUserByFriendsId) {
		this.TUserByFriendsId = TUserByFriendsId;
	}

	public TUser getTUserByUserId() {
		return this.TUserByUserId;
	}

	public void setTUserByUserId(TUser TUserByUserId) {
		this.TUserByUserId = TUserByUserId;
	}

}