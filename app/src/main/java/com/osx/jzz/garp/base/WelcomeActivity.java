package com.osx.jzz.garp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.osx.jzz.garp.garp.MainActivity;
import com.osx.jzz.garp.garp.R;
import com.osx.jzz.garp.utils.osxConfig;
import com.osx.jzz.garp.utils.osxDialog;
import com.osx.jzz.garp.utils.osxShareData;
import com.osx.jzz.garp.utils.osxUtil;

public class WelcomeActivity extends BaseActivity {
	boolean networking = false;
	boolean isFirstOpen = true;
	Context context;
	Activity activity;
	TextView tv_wel_state;
	osxDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.osx_welcome);
		context = this;
		activity = this;
		super.init(context, activity);
		// startService(new Intent(this, XabService.class));
		tv_wel_state = (TextView) findViewById(R.id.tv_wel_state);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		osxShareData.screenHeight = dm.heightPixels;
		osxShareData.screenWidth = dm.widthPixels;
		osxShareData.contentHeight = dm.heightPixels - 140;
		isFirstOpen = sharePreference.getBoolean("isFirstOpen", true);
		checkInfo();
	}

	private void checkInfo() {
		networking = osxUtil.isConnec(this);
		if (networking) {
			if (osxConfig.IS_TEST) {
				mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY, 1000);
			} else {
				if (isFirstOpen) {
					sharePreference.saveBoolean("isFirstOpen", false);
					mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY, 1);
				} else {
					mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY, 3000);
				}
			}
		} else {
			dialog = new osxDialog(activity, basecontext);
			dialog.str_content = "请检查一下您的网络吧 !";
			dialog.str_btnleft = "设置网络";
			dialog.str_btnright = "退出";
			dialog.leftbtn = LeftBtnListener;
			dialog.rightbtn = RightBtnListener;
			dialog.show();
		}

	}

	private OnClickListener LeftBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.cancel();
			if (dialog.str_btnleft.equals("设置网络")) {
				osxUtil.OpenSetting(activity, Settings.ACTION_SETTINGS);
			}
			dialog.str_content = "是否重新加载呢?";
			dialog.str_btnleft = "重新加载";
			dialog.str_btnright = "退出";
			dialog.leftbtn = reload;
			dialog.rightbtn = RightBtnListener;
			dialog.init();
			dialog.show();

		}
	};
	private OnClickListener RightBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.cancel();
			MyApplication.getInstance().exit();
		}
	};
	private OnClickListener reload = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.cancel();
			checkInfo();
		}
	};
	// *************************************************
	// Handler:跳转至不同页�?
	// *************************************************
	private final static int SWITCH_MAINACTIVITY = 1000;
	private final static int SWITCH_GUIDACTIVITY = 1001;
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SWITCH_MAINACTIVITY:
				Intent mIntent = new Intent();
				mIntent.setClass(WelcomeActivity.this, MainActivity.class);
				WelcomeActivity.this.startActivity(mIntent);
				WelcomeActivity.this.finish();
				break;
			case SWITCH_GUIDACTIVITY:
				mIntent = new Intent();
				mIntent.setClass(WelcomeActivity.this, GuideActivity.class);
				WelcomeActivity.this.startActivity(mIntent);
				WelcomeActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void onStart() {
		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return false;
	}

}
