package com.basic.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */

public class TUser implements java.io.Serializable {

	// Fields

	private Long userId;
	private String email;
	private String userName;
	private String password;
	private String userNickname;
	private String personSignature;
	private Integer sex;
	private String school;
	private String academy;
	private Integer state;
	private Date birthday;
	private String telephone;
	private String picture;
	private Integer securityControl;
	private Set TGroupstudentsForFriendsId = new HashSet(0);
	private Set TPublicinfos = new HashSet(0);
	private Set TGroupstudentsForUserId = new HashSet(0);
	private Set TJbrxfriendsForJbrxfriendId = new HashSet(0);
	private Set TJbrxfriendsForUserId = new HashSet(0);
	private Set TMessagebeansForReceiverId = new HashSet(0);
	private Set TPersonaldynamics = new HashSet(0);
	private Set TGroupfriendsesForUserId = new HashSet(0);
	private Set TMessageflags = new HashSet(0);
	private Set TGroupfriendsesForFriendsId = new HashSet(0);
	private Set TMessagebeansForSendId = new HashSet(0);
	private Set TMycollectionses = new HashSet(0);
	private Set TFriendrequestsForFriendId = new HashSet(0);
	private Set TJbrxinfos = new HashSet(0);
	private Set TFriendrequestsForUserId = new HashSet(0);
	private Set TGroupfamiliesForFriendsId = new HashSet(0);
	private Set TMyattentionsForUserId = new HashSet(0);
	private Set TMyattentionsForAttentionId = new HashSet(0);
	private Set TGroupfamiliesForUserId = new HashSet(0);

	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** minimal constructor */
	public TUser(String email, String userName, String password) {
		this.email = email;
		this.userName = userName;
		this.password = password;
	}

	/** full constructor */
	public TUser(String email, String userName, String password,
			String userNickname, String personSignature, Integer sex,
			String school, String academy, Integer state, Date birthday,
			String telephone, String picture, Integer securityControl,
			Set TGroupstudentsForFriendsId, Set TPublicinfos,
			Set TGroupstudentsForUserId, Set TJbrxfriendsForJbrxfriendId,
			Set TJbrxfriendsForUserId, Set TMessagebeansForReceiverId,
			Set TPersonaldynamics, Set TGroupfriendsesForUserId,
			Set TMessageflags, Set TGroupfriendsesForFriendsId,
			Set TMessagebeansForSendId, Set TMycollectionses,
			Set TFriendrequestsForFriendId, Set TJbrxinfos,
			Set TFriendrequestsForUserId, Set TGroupfamiliesForFriendsId,
			Set TMyattentionsForUserId, Set TMyattentionsForAttentionId,
			Set TGroupfamiliesForUserId) {
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.userNickname = userNickname;
		this.personSignature = personSignature;
		this.sex = sex;
		this.school = school;
		this.academy = academy;
		this.state = state;
		this.birthday = birthday;
		this.telephone = telephone;
		this.picture = picture;
		this.securityControl = securityControl;
		this.TGroupstudentsForFriendsId = TGroupstudentsForFriendsId;
		this.TPublicinfos = TPublicinfos;
		this.TGroupstudentsForUserId = TGroupstudentsForUserId;
		this.TJbrxfriendsForJbrxfriendId = TJbrxfriendsForJbrxfriendId;
		this.TJbrxfriendsForUserId = TJbrxfriendsForUserId;
		this.TMessagebeansForReceiverId = TMessagebeansForReceiverId;
		this.TPersonaldynamics = TPersonaldynamics;
		this.TGroupfriendsesForUserId = TGroupfriendsesForUserId;
		this.TMessageflags = TMessageflags;
		this.TGroupfriendsesForFriendsId = TGroupfriendsesForFriendsId;
		this.TMessagebeansForSendId = TMessagebeansForSendId;
		this.TMycollectionses = TMycollectionses;
		this.TFriendrequestsForFriendId = TFriendrequestsForFriendId;
		this.TJbrxinfos = TJbrxinfos;
		this.TFriendrequestsForUserId = TFriendrequestsForUserId;
		this.TGroupfamiliesForFriendsId = TGroupfamiliesForFriendsId;
		this.TMyattentionsForUserId = TMyattentionsForUserId;
		this.TMyattentionsForAttentionId = TMyattentionsForAttentionId;
		this.TGroupfamiliesForUserId = TGroupfamiliesForUserId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserNickname() {
		return this.userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getPersonSignature() {
		return this.personSignature;
	}

	public void setPersonSignature(String personSignature) {
		this.personSignature = personSignature;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getAcademy() {
		return this.academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getSecurityControl() {
		return this.securityControl;
	}

	public void setSecurityControl(Integer securityControl) {
		this.securityControl = securityControl;
	}

	public Set getTGroupstudentsForFriendsId() {
		return this.TGroupstudentsForFriendsId;
	}

	public void setTGroupstudentsForFriendsId(Set TGroupstudentsForFriendsId) {
		this.TGroupstudentsForFriendsId = TGroupstudentsForFriendsId;
	}

	public Set getTPublicinfos() {
		return this.TPublicinfos;
	}

	public void setTPublicinfos(Set TPublicinfos) {
		this.TPublicinfos = TPublicinfos;
	}

	public Set getTGroupstudentsForUserId() {
		return this.TGroupstudentsForUserId;
	}

	public void setTGroupstudentsForUserId(Set TGroupstudentsForUserId) {
		this.TGroupstudentsForUserId = TGroupstudentsForUserId;
	}

	public Set getTJbrxfriendsForJbrxfriendId() {
		return this.TJbrxfriendsForJbrxfriendId;
	}

	public void setTJbrxfriendsForJbrxfriendId(Set TJbrxfriendsForJbrxfriendId) {
		this.TJbrxfriendsForJbrxfriendId = TJbrxfriendsForJbrxfriendId;
	}

	public Set getTJbrxfriendsForUserId() {
		return this.TJbrxfriendsForUserId;
	}

	public void setTJbrxfriendsForUserId(Set TJbrxfriendsForUserId) {
		this.TJbrxfriendsForUserId = TJbrxfriendsForUserId;
	}

	public Set getTMessagebeansForReceiverId() {
		return this.TMessagebeansForReceiverId;
	}

	public void setTMessagebeansForReceiverId(Set TMessagebeansForReceiverId) {
		this.TMessagebeansForReceiverId = TMessagebeansForReceiverId;
	}

	public Set getTPersonaldynamics() {
		return this.TPersonaldynamics;
	}

	public void setTPersonaldynamics(Set TPersonaldynamics) {
		this.TPersonaldynamics = TPersonaldynamics;
	}

	public Set getTGroupfriendsesForUserId() {
		return this.TGroupfriendsesForUserId;
	}

	public void setTGroupfriendsesForUserId(Set TGroupfriendsesForUserId) {
		this.TGroupfriendsesForUserId = TGroupfriendsesForUserId;
	}

	public Set getTMessageflags() {
		return this.TMessageflags;
	}

	public void setTMessageflags(Set TMessageflags) {
		this.TMessageflags = TMessageflags;
	}

	public Set getTGroupfriendsesForFriendsId() {
		return this.TGroupfriendsesForFriendsId;
	}

	public void setTGroupfriendsesForFriendsId(Set TGroupfriendsesForFriendsId) {
		this.TGroupfriendsesForFriendsId = TGroupfriendsesForFriendsId;
	}

	public Set getTMessagebeansForSendId() {
		return this.TMessagebeansForSendId;
	}

	public void setTMessagebeansForSendId(Set TMessagebeansForSendId) {
		this.TMessagebeansForSendId = TMessagebeansForSendId;
	}

	public Set getTMycollectionses() {
		return this.TMycollectionses;
	}

	public void setTMycollectionses(Set TMycollectionses) {
		this.TMycollectionses = TMycollectionses;
	}

	public Set getTFriendrequestsForFriendId() {
		return this.TFriendrequestsForFriendId;
	}

	public void setTFriendrequestsForFriendId(Set TFriendrequestsForFriendId) {
		this.TFriendrequestsForFriendId = TFriendrequestsForFriendId;
	}

	public Set getTJbrxinfos() {
		return this.TJbrxinfos;
	}

	public void setTJbrxinfos(Set TJbrxinfos) {
		this.TJbrxinfos = TJbrxinfos;
	}

	public Set getTFriendrequestsForUserId() {
		return this.TFriendrequestsForUserId;
	}

	public void setTFriendrequestsForUserId(Set TFriendrequestsForUserId) {
		this.TFriendrequestsForUserId = TFriendrequestsForUserId;
	}

	public Set getTGroupfamiliesForFriendsId() {
		return this.TGroupfamiliesForFriendsId;
	}

	public void setTGroupfamiliesForFriendsId(Set TGroupfamiliesForFriendsId) {
		this.TGroupfamiliesForFriendsId = TGroupfamiliesForFriendsId;
	}

	public Set getTMyattentionsForUserId() {
		return this.TMyattentionsForUserId;
	}

	public void setTMyattentionsForUserId(Set TMyattentionsForUserId) {
		this.TMyattentionsForUserId = TMyattentionsForUserId;
	}

	public Set getTMyattentionsForAttentionId() {
		return this.TMyattentionsForAttentionId;
	}

	public void setTMyattentionsForAttentionId(Set TMyattentionsForAttentionId) {
		this.TMyattentionsForAttentionId = TMyattentionsForAttentionId;
	}

	public Set getTGroupfamiliesForUserId() {
		return this.TGroupfamiliesForUserId;
	}

	public void setTGroupfamiliesForUserId(Set TGroupfamiliesForUserId) {
		this.TGroupfamiliesForUserId = TGroupfamiliesForUserId;
	}

}