package com.acrs.bean;

import java.io.Serializable;
import java.util.Date;

import com.acrs.digest.Base64Digest;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7169156450581115716L;
	/**
	 * ID
	 */
	private int id;
	/**
	 * 网站名称
	 */
	private String siteName;
	/**
	 * 网站URL
	 */
	private String siteUrl;
	/**
	 * 帐户名
	 */
	private String accountName;
	/**
	 * 通过Base64编码加密后的帐户名
	 */
	private String encryptName;
	/**
	 * 账户密码
	 */
	private String accountPwd;
	/**
	 * 通过Base64 （先）和 MD5（后）编码加密后的帐户密码
	 */
	private String encryptPwd;
	/**
	 * 账户说明
	 */
	private String accountMemo;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	private int userId;
	public Account() {
		super();
	}

	public Account(String siteName, String siteUrl, String encryptName,
			String encryptPwd, String accountMemo) {
		this.siteName = siteName;
		this.siteUrl = siteUrl;
		this.encryptName = encryptName;
		this.encryptPwd = encryptPwd;
		this.accountMemo = accountMemo;
	}
 
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = Base64Digest.decode(accountName);
	}
	public String getEncryptName() {
		return encryptName;
	}
	public void setEncryptName(String encryptName) {
		this.encryptName = encryptName;
	}
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = Base64Digest.decode(accountPwd);
	}
	public String getEncryptPwd() {
		return encryptPwd;
	}
	public void setEncryptPwd(String encryptPwd) {
		this.encryptPwd = encryptPwd;
	}
	public String getAccountMemo() {
		return accountMemo;
	}
	public void setAccountMemo(String accountMemo) {
		this.accountMemo = accountMemo;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	@Override
	public String toString() {
		return id + "," + siteName + ","+Base64Digest.decode(siteUrl) + ", " + accountName + "," + accountPwd;
	}
 
}
