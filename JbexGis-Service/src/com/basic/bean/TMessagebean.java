package com.basic.bean;

import java.sql.Timestamp;

/**
 * TMessagebean entity. @author MyEclipse Persistence Tools
 */

public class TMessagebean implements java.io.Serializable {

	// Fields

	private Long id;
	private TUser TUserBySendId;
	private TUser TUserByReceiverId;
	private Timestamp time;
	private String msg;
	private Integer type;

	// Constructors

	/** default constructor */
	public TMessagebean() {
	}

	/** minimal constructor */
	public TMessagebean(TUser TUserBySendId, TUser TUserByReceiverId,
			Timestamp time, Integer type) {
		this.TUserBySendId = TUserBySendId;
		this.TUserByReceiverId = TUserByReceiverId;
		this.time = time;
		this.type = type;
	}

	/** full constructor */
	public TMessagebean(TUser TUserBySendId, TUser TUserByReceiverId,
			Timestamp time, String msg, Integer type) {
		this.TUserBySendId = TUserBySendId;
		this.TUserByReceiverId = TUserByReceiverId;
		this.time = time;
		this.msg = msg;
		this.type = type;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TUser getTUserBySendId() {
		return this.TUserBySendId;
	}

	public void setTUserBySendId(TUser TUserBySendId) {
		this.TUserBySendId = TUserBySendId;
	}

	public TUser getTUserByReceiverId() {
		return this.TUserByReceiverId;
	}

	public void setTUserByReceiverId(TUser TUserByReceiverId) {
		this.TUserByReceiverId = TUserByReceiverId;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}