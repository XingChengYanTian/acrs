package com.acrs.bean;

import java.io.Serializable;

import com.acrs.digest.MD5Digest;

public class User implements Serializable{
	private static final long serialVersionUID = -6458361206492721575L;
	private int id;
	private String userName;
	private String userPassword;
	private String encryptPassword;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEncryptPassword() {
		return encryptPassword;
	}
	public void setEncryptPassword(String encryptPassword) {
		this.encryptPassword = MD5Digest.md5Crypt(encryptPassword);
	}
	
}
