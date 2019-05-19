package com.basic.bean;

/**
 * TMyattention entity. @author MyEclipse Persistence Tools
 */

public class TMyattention implements java.io.Serializable {

	// Fields

	private TMyattentionId id;
	private TUser TUserByUserId;
	private TUser TUserByAttentionId;

	// Constructors

	/** default constructor */
	public TMyattention() {
	}

	/** full constructor */
	public TMyattention(TMyattentionId id, TUser TUserByUserId,
			TUser TUserByAttentionId) {
		this.id = id;
		this.TUserByUserId = TUserByUserId;
		this.TUserByAttentionId = TUserByAttentionId;
	}

	// Property accessors

	public TMyattentionId getId() {
		return this.id;
	}

	public void setId(TMyattentionId id) {
		this.id = id;
	}

	public TUser getTUserByUserId() {
		return this.TUserByUserId;
	}

	public void setTUserByUserId(TUser TUserByUserId) {
		this.TUserByUserId = TUserByUserId;
	}

	public TUser getTUserByAttentionId() {
		return this.TUserByAttentionId;
	}

	public void setTUserByAttentionId(TUser TUserByAttentionId) {
		this.TUserByAttentionId = TUserByAttentionId;
	}

}