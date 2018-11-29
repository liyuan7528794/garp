package com.osx.jzz.garp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits")
public class osxShareUtil {
	private static SharedPreferences sp;
	private static SharedPreferences.Editor editor;
	private static osxShareUtil instance = null;

	/**
	 * 构造方法
	 * 
	 * @date 2013-4-19 下午2:43:55
	 */
	private osxShareUtil(Context context) {
		sp = context.getSharedPreferences("xabShare", Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public synchronized static osxShareUtil getInstance(Context context) {
		if (instance == null) {
			instance = new osxShareUtil(context);
		}
		return instance;
	}

	/**
	 * 从SharedPreferences获取数据
	 * 
	 * @Description: TODO
	 * @date 2013-4-19 下午3:06:50
	 */
	public String getString(String key) {
		return getString(key, "");
	}

	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	public Long getLong(String key) {
		return getLong(key, 0L);
	}

	public Long getLong(String key, Long defValue) {
		return sp.getLong(key, defValue);
	}

	public Float getFloat(String key) {
		return getFloat(key, 0F);
	}

	public Float getFloat(String key, Float defValue) {
		return sp.getFloat(key, defValue);
	}

	/**
	 * SharedPreferences保存数据
	 * 
	 * @Description: TODO
	 * @date 2013-4-19 下午3:10:43
	 */
	public void saveString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	public void saveInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void saveFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public void saveLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void saveBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 清空SharedPreferences数据
	 * 
	 * @Description: TODO
	 * @date 2013-4-19 下午3:13:47
	 */
	public static void clear() {
		editor.clear();
		editor.commit();
	}

	/**
	 * 通过key删除SharedPreferences
	 * 
	 * @Description: TODO
	 * @date 2013-4-19 下午3:14:36
	 */
	public static void clear(String key) {
		editor.remove(key);
		editor.commit();
	}
}
