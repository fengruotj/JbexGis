package com.basic.service.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int user_id;
	private String user_name;
	private String password;
	private String person_signature; // 个性签名
	private int sex; // 性别，0、1、2分别为未知、男、女
	private String school;
	private String academy; // 院系
	private int state; // 0、1分别代表离线和在线
	private Date birthday;
	private String telephone;
	private String picture; // 照片的地址
	private String user_nickname;// 昵称
	private String email; // 邮箱
	private String flag; // 服务器返回的是否登陆成功，值为true或者false
	private Integer SecurityControl; // 权限 0完全不具备发布公众信息的权限 1可以发布社团的公众信息
									// 2可以发布校园官方的信息

	public User() {
		super();
	}

	public User(int user_id, String user_name, String password,
			String person_signature, int sex, String school, String academy,
			int state, Date birthday, String telephone, String picture,
			String user_nickname, String email, String flag,
			Integer securityControl) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.person_signature = person_signature;
		this.sex = sex;
		this.school = school;
		this.academy = academy;
		this.state = state;
		this.birthday = birthday;
		this.telephone = telephone;
		this.picture = picture;
		this.user_nickname = user_nickname;
		this.email = email;
		this.flag = flag;
		this.SecurityControl = securityControl;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name
				+ ", password=" + password + ", person_signature="
				+ person_signature + ", sex=" + sex + ", school=" + school
				+ ", academy=" + academy + ", state=" + state + ", birthday="
				+ birthday + ", telephone=" + telephone + ", picture="
				+ picture + ", user_nickname=" + user_nickname + ", email="
				+ email + ", flag=" + flag + ", SecurityControl="
				+ SecurityControl + "]";
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPerson_signature() {
		return person_signature;
	}

	public void setPerson_signature(String person_signature) {
		this.person_signature = person_signature;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSecurityControl() {
		return SecurityControl;
	}

	public void setSecurityControl(Integer securityControl) {
		SecurityControl = securityControl;
	}

}
