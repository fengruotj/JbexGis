package com.basic.bean;

/**
 * TGroupfamily entity. @author MyEclipse Persistence Tools
 */

public class TGroupfamily implements java.io.Serializable {

	// Fields

	private TGroupfamilyId id;
	private TUser TUserByFriendsId;
	private TUser TUserByUserId;

	// Constructors

	/** default constructor */
	public TGroupfamily() {
	}

	/** full constructor */
	public TGroupfamily(TGroupfamilyId id, TUser TUserByFriendsId,
			TUser TUserByUserId) {
		this.id = id;
		this.TUserByFriendsId = TUserByFriendsId;
		this.TUserByUserId = TUserByUserId;
	}

	// Property accessors

	public TGroupfamilyId getId() {
		return this.id;
	}

	public void setId(TGroupfamilyId id) {
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