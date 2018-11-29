package com.osx.jzz.garp.dao;

public class UserLogin {
	private int UsersId;
	private String LoginName;
	private String Password;
	private String NickName;
	private int Gender;
	private int RolesId;
	public int getUsersId() {
		return UsersId;
	}
	public void setUsersId(int usersId) {
		UsersId = usersId;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public int getGender() {
		return Gender;
	}
	public void setGender(int gender) {
		Gender = gender;
	}
	public int getRolesId() {
		return RolesId;
	}
	public void setRolesId(int rolesId) {
		RolesId = rolesId;
	}
	

	
}
