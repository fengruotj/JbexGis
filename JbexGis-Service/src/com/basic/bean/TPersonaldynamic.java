package com.basic.bean;

import java.sql.Timestamp;

/**
 * TPersonaldynamic entity. @author MyEclipse Persistence Tools
 */

public class TPersonaldynamic implements java.io.Serializable {

	// Fields

	private Long id;
	private TUser TUser;
	private Double dotX;
	private Double dotY;
	private String detail;
	private String picture1;
	private String picture2;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public TPersonaldynamic() {
	}

	/** minimal constructor */
	public TPersonaldynamic(TUser TUser, Double dotX, Double dotY,
			String detail, Timestamp time) {
		this.TUser = TUser;
		this.dotX = dotX;
		this.dotY = dotY;
		this.detail = detail;
		this.time = time;
	}

	/** full constructor */
	public TPersonaldynamic(TUser TUser, Double dotX, Double dotY,
			String detail, String picture1, String picture2, Timestamp time) {
		this.TUser = TUser;
		this.dotX = dotX;
		this.dotY = dotY;
		this.detail = detail;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.time = time;
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

	public Double getDotX() {
		return this.dotX;
	}

	public void setDotX(Double dotX) {
		this.dotX = dotX;
	}

	public Double getDotY() {
		return this.dotY;
	}

	public void setDotY(Double dotY) {
		this.dotY = dotY;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPicture1() {
		return this.picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	public String getPicture2() {
		return this.picture2;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}