package com.basic.bean;

/**
 * TMyattentionId entity. @author MyEclipse Persistence Tools
 */

public class TMyattentionId implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long attentionId;

	// Constructors

	/** default constructor */
	public TMyattentionId() {
	}

	/** full constructor */
	public TMyattentionId(Long userId, Long attentionId) {
		this.userId = userId;
		this.attentionId = attentionId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAttentionId() {
		return this.attentionId;
	}

	public void setAttentionId(Long attentionId) {
		this.attentionId = attentionId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMyattentionId))
			return false;
		TMyattentionId castOther = (TMyattentionId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getAttentionId() == castOther.getAttentionId()) || (this
						.getAttentionId() != null
						&& castOther.getAttentionId() != null && this
						.getAttentionId().equals(castOther.getAttentionId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37
				* result
				+ (getAttentionId() == null ? 0 : this.getAttentionId()
						.hashCode());
		return result;
	}

}