package com.osx.jzz.garp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserLoginDB extends SQLiteOpenHelper {

	public UserLoginDB(Context context) {
		super(context, "userlogin.db", null, 1);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE userlogin( "
				+ " id INTEGER PRIMARY KEY autoincrement," + // 默认id
				" LoginName VARCHAR(200)," + // 用户名
				" UsersId INTEGER(50)," + // 用户id
				" password VARCHAR(200)," + // 用户密码
				" NickName VARCHAR(200)," + // 地位
				" Gender INTEGER(50)," + // 性别
				" RolesId INTEGER(50))");// 角色
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
