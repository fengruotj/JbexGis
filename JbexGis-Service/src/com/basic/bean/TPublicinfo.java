package com.basic.bean;

import java.sql.Timestamp;

/**
 * TPublicinfo entity. @author MyEclipse Persistence Tools
 */

public class TPublicinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private TUser TUser;
	private Double dotX;
	private Double dotY;
	private String title;
	private String detail;
	private Timestamp time;
	private String label;
	private String picture1;
	private String picture2;

	// Constructors

	/** default constructor */
	public TPublicinfo() {
	}

	/** minimal constructor */
	public TPublicinfo(TUser TUser, Double dotX, Double dotY, String title,
			String detail, Timestamp time) {
		this.TUser = TUser;
		this.dotX = dotX;
		this.dotY = dotY;
		this.title = title;
		this.detail = detail;
		this.time = time;
	}

	/** full constructor */
	public TPublicinfo(TUser TUser, Double dotX, Double dotY, String title,
			String detail, Timestamp time, String label, String picture1,
			String picture2) {
		this.TUser = TUser;
		this.dotX = dotX;
		this.dotY = dotY;
		this.title = title;
		this.detail = detail;
		this.time = time;
		this.label = label;
		this.picture1 = picture1;
		this.picture2 = picture2;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
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

}