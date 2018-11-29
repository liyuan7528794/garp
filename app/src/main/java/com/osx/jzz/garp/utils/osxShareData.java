package com.osx.jzz.garp.utils;

import android.content.Context;

import com.osx.jzz.garp.dao.UserLogin;

import java.util.HashMap;
import java.util.List;

public class osxShareData {
	public static osxShareUtil sharePreference;
	public static int screenWidth;
	public static int screenHeight;
	public static int contentHeight;

	public static String web_base_url = "https://www.9zhouzhi.com/";
	public static Context baseContext;

	public static UserLogin userLogin = new UserLogin();
	public static boolean isLogin = false;
	public static int noRead = 0;

	public static int areaSelectId = 0;// 地区
	public static int displayStartId = 0;// 防止显示重复数据
	public static String businessId = "";// 防止显示重复数据

	public static List<HashMap<String, Object>> detailData;
}
