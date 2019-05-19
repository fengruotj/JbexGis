package com.basic.bean;

/**
 * TMycollectionsId entity. @author MyEclipse Persistence Tools
 */

public class TMycollectionsId implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long collectionId;

	// Constructors

	/** default constructor */
	public TMycollectionsId() {
	}

	/** full constructor */
	public TMycollectionsId(Long userId, Long collectionId) {
		this.userId = userId;
		this.collectionId = collectionId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCollectionId() {
		return this.collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMycollectionsId))
			return false;
		TMycollectionsId castOther = (TMycollectionsId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getCollectionId() == castOther.getCollectionId()) || (this
						.getCollectionId() != null
						&& castOther.getCollectionId() != null && this
						.getCollectionId().equals(castOther.getCollectionId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37
				* result
				+ (getCollectionId() == null ? 0 : this.getCollectionId()
						.hashCode());
		return result;
	}

}