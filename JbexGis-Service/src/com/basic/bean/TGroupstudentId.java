package com.basic.bean;

/**
 * TGroupstudentId entity. @author MyEclipse Persistence Tools
 */

public class TGroupstudentId implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long friendsId;

	// Constructors

	/** default constructor */
	public TGroupstudentId() {
	}

	/** full constructor */
	public TGroupstudentId(Long userId, Long friendsId) {
		this.userId = userId;
		this.friendsId = friendsId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendsId() {
		return this.friendsId;
	}

	public void setFriendsId(Long friendsId) {
		this.friendsId = friendsId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TGroupstudentId))
			return false;
		TGroupstudentId castOther = (TGroupstudentId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getFriendsId() == castOther.getFriendsId()) || (this
						.getFriendsId() != null
						&& castOther.getFriendsId() != null && this
						.getFriendsId().equals(castOther.getFriendsId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getFriendsId() == null ? 0 : this.getFriendsId().hashCode());
		return result;
	}

}