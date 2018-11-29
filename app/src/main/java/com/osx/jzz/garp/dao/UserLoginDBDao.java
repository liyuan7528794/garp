package com.osx.jzz.garp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserLoginDBDao {

	UserLoginDB userLoginDB;
	UserLogin userlogin;

	public UserLoginDBDao(Context context) {
		this.userLoginDB = new UserLoginDB(context);
	}

	// 添加用户到数据库表
	public void add(UserLogin userlogin) {
		SQLiteDatabase db = userLoginDB.getWritableDatabase();
		db.execSQL(
				"insert into userlogin (UsersId,password,LoginName,NickName,Gender,RolesId) values (?,?,?,?,?,?)",
				new Object[] { userlogin.getUsersId(), userlogin.getPassword(),
						userlogin.getLoginName(), userlogin.getNickName(),
						userlogin.getGender(), userlogin.getRolesId() });
		db.close();
	}

	// 用户名是否存在
	public Boolean IsNoExist(String userid) {
		Boolean booleans = false;
		SQLiteDatabase db = userLoginDB.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select userid from userlogin where userid=?",
				new String[] { userid });
		if (cursor.moveToNext()) {
			booleans = true;
		} else {
			booleans = false;
		}
		cursor.close();
		db.close();
		return booleans;
	}

	// 删除所有数据
	public void deleteAll() {
		SQLiteDatabase db = userLoginDB.getWritableDatabase();
		db.execSQL("delete from userlogin", new Object[] {});
		db.close();
	}
	//最后一条
	public UserLogin findTheLast() {
		SQLiteDatabase db = userLoginDB.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from userlogin order by id desc",
				null);
		userlogin = null;
		if (cursor.moveToNext()) {
			String LoginName = cursor.getString(cursor.getColumnIndex("LoginName"));
			String Password = cursor.getString(cursor
					.getColumnIndex("password"));
			String NickName = cursor.getString(cursor
					.getColumnIndex("NickName"));
			int UsersId = cursor.getInt(cursor.getColumnIndex("UsersId"));
			int RolesId = cursor.getInt(cursor.getColumnIndex("RolesId"));
			int Gender = cursor.getInt(cursor
					.getColumnIndex("Gender"));
			userlogin = new UserLogin();
			userlogin.setLoginName(LoginName);
			userlogin.setPassword(Password);
			userlogin.setNickName(NickName);
			userlogin.setUsersId(UsersId);
			userlogin.setRolesId(RolesId);
			userlogin.setGender(Gender);
		}
		cursor.close();
		db.close();
		return userlogin;

	}
}
