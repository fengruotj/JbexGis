package com.basic.bean;

/**
 * TMessageflag entity. @author MyEclipse Persistence Tools
 */

public class TMessageflag implements java.io.Serializable {

	// Fields

	private Long id;
	private TUser TUser;
	private Boolean flag;

	// Constructors

	/** default constructor */
	public TMessageflag() {
	}

	/** full constructor */
	public TMessageflag(TUser TUser, Boolean flag) {
		this.TUser = TUser;
		this.flag = flag;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}