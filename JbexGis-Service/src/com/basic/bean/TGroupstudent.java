package com.basic.bean;

/**
 * TGroupstudent entity. @author MyEclipse Persistence Tools
 */

public class TGroupstudent implements java.io.Serializable {

	// Fields

	private TGroupstudentId id;
	private TUser TUserByFriendsId;
	private TUser TUserByUserId;

	// Constructors

	/** default constructor */
	public TGroupstudent() {
	}

	/** full constructor */
	public TGroupstudent(TGroupstudentId id, TUser TUserByFriendsId,
			TUser TUserByUserId) {
		this.id = id;
		this.TUserByFriendsId = TUserByFriendsId;
		this.TUserByUserId = TUserByUserId;
	}

	// Property accessors

	public TGroupstudentId getId() {
		return this.id;
	}

	public void setId(TGroupstudentId id) {
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