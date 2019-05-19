package com.basic.bean;

/**
 * TMycollections entity. @author MyEclipse Persistence Tools
 */

public class TMycollections implements java.io.Serializable {

	// Fields

	private TMycollectionsId id;
	private TUser TUser;
	private TJbrxinfo TJbrxinfo;

	// Constructors

	/** default constructor */
	public TMycollections() {
	}

	/** full constructor */
	public TMycollections(TMycollectionsId id, TUser TUser, TJbrxinfo TJbrxinfo) {
		this.id = id;
		this.TUser = TUser;
		this.TJbrxinfo = TJbrxinfo;
	}

	// Property accessors

	public TMycollectionsId getId() {
		return this.id;
	}

	public void setId(TMycollectionsId id) {
		this.id = id;
	}

	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	public TJbrxinfo getTJbrxinfo() {
		return this.TJbrxinfo;
	}

	public void setTJbrxinfo(TJbrxinfo TJbrxinfo) {
		this.TJbrxinfo = TJbrxinfo;
	}

}