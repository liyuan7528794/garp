package com.osx.jzz.garp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class osxUtil {
	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");;
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");

	public static String getTime(long time) {
		if (time != 0) {
			String dateTemp = format.format(new Time(time * 1000L));
			return dateTemp;
		} else {
			return null;
		}
	}

	public static String getDateByTime(long time) {
		if (time != 0) {
			String dateTemp = dateFormat.format(new Time(time * 1000L));
			return dateTemp;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 检查设备是否连接到网络
	 * 
	 * @date 2013-4-19 下午2:12:37
	 */
	public static boolean isConnec(Context context) {
		boolean flag = false;
		ConnectivityManager cconnManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cconnManager != null) {
			NetworkInfo netInfo = cconnManager.getActiveNetworkInfo();
			if (netInfo != null) {
				if (netInfo.isConnected()) {
					flag = true;
				}
			}
			clear(netInfo);
		}
		clear(cconnManager);
		return flag;
	}

	/**
	 * 
	 * 检测设备是否连接到wifi
	 * 
	 * @date 2013-4-19 下午2:20:14
	 */
	public static boolean isConnecWIFI(Context context) {
		boolean flag = false;
		ConnectivityManager cconnManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cconnManager != null) {
			NetworkInfo wifiInfo = cconnManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifiInfo != null && wifiInfo.isAvailable()) {
				if (wifiInfo.isConnected()) {
					flag = true;
				}
			}
			clear(wifiInfo);
		}
		clear(cconnManager);
		return flag;
	}

	/**
	 * 时间格式化
	 * 
	 * @Description: TODO
	 * @date 2013-4-25 下午3:09:15
	 */
	public static String formatToStr(Date paramDate) {
		return format.format(paramDate);
	}

	public static String formatDateToStr(Date paramDate) {
		return dateFormat.format(paramDate);
	}

	public static String formatTimeToStr(Date paramDate) {
		return timeFormat.format(paramDate);
	}

	public static Date formatToDate(String paramDate) {
		Date date = null;
		try {
			date = format.parse(paramDate);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	public static Date formatDateToDate(String paramDate) {
		Date date = null;
		try {
			date = dateFormat.parse(paramDate);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	public static Date formatTimeToDate(String paramDate) {
		Date date = null;
		try {
			date = timeFormat.parse(paramDate);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	/**
	 * 
	 * 释放内存
	 * 
	 * @date 2013-4-19 下午2:32:56
	 */
	public static void clear(Object... o) {
		int i = 0;
		if (o != null && o.length > 0) {
			int j = o.length;
			for (; i < j; i++) {
				o[i] = null;
			}
		}
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOpenGPS(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	/**
	 * 强制帮用户打开GPS
	 * 
	 * @param context
	 */
	public static final void OpenSetting(Activity activity, String url) {
		Intent myIntent = new Intent(url);
		activity.startActivityForResult(myIntent, 0);
	}

	public static double GetShortDistance(double lon1, double lat1,
			double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	public double GetLongDistance(double lon1, double lat1, double lon2,
			double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1)
				* Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return distance;
	}
}
