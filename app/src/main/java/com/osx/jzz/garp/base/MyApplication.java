package com.osx.jzz.garp.base;


import android.app.Activity;
import android.app.Application;

import com.osx.jzz.garp.utils.osxConfig;
import com.osx.jzz.garp.utils.CrashHandler;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {
	private static MyApplication instance;
	public static String TAG = "MyApplication";
	private List<Activity> list = new LinkedList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		if (!osxConfig.IS_TEST) {
			CrashHandler crashHandler = CrashHandler.getInstance();
			crashHandler.init(getApplicationContext());
		}
	}

	// 把activity添加到容器里
	public void addActivity(Activity activity) {
		list.add(activity);
	}

	// 退出
	public void exit() {
		// 遍历，并且分别释放内存
		for (Activity activity : list) {
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
		System.exit(0);
	}

	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}
	
}