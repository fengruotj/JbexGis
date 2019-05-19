package com.basic.bean;

/**
 * TJbrxfriendId entity. @author MyEclipse Persistence Tools
 */

public class TJbrxfriendId implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long jbrxfriendId;
	private Long jbrxinfoId;

	// Constructors

	/** default constructor */
	public TJbrxfriendId() {
	}

	/** full constructor */
	public TJbrxfriendId(Long userId, Long jbrxfriendId, Long jbrxinfoId) {
		this.userId = userId;
		this.jbrxfriendId = jbrxfriendId;
		this.jbrxinfoId = jbrxinfoId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getJbrxfriendId() {
		return this.jbrxfriendId;
	}

	public void setJbrxfriendId(Long jbrxfriendId) {
		this.jbrxfriendId = jbrxfriendId;
	}

	public Long getJbrxinfoId() {
		return this.jbrxinfoId;
	}

	public void setJbrxinfoId(Long jbrxinfoId) {
		this.jbrxinfoId = jbrxinfoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TJbrxfriendId))
			return false;
		TJbrxfriendId castOther = (TJbrxfriendId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getJbrxfriendId() == castOther.getJbrxfriendId()) || (this
						.getJbrxfriendId() != null
						&& castOther.getJbrxfriendId() != null && this
						.getJbrxfriendId().equals(castOther.getJbrxfriendId())))
				&& ((this.getJbrxinfoId() == castOther.getJbrxinfoId()) || (this
						.getJbrxinfoId() != null
						&& castOther.getJbrxinfoId() != null && this
						.getJbrxinfoId().equals(castOther.getJbrxinfoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37
				* result
				+ (getJbrxfriendId() == null ? 0 : this.getJbrxfriendId()
						.hashCode());
		result = 37
				* result
				+ (getJbrxinfoId() == null ? 0 : this.getJbrxinfoId()
						.hashCode());
		return result;
	}

}