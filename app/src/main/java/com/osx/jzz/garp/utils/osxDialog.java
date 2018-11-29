package com.osx.jzz.garp.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.osx.jzz.garp.garp.R;


public class osxDialog extends Dialog {

	public Button button_left, button_right;
	private View mMenuView;
	Context context;
	Activity activity;
	Dialog self;
	TextView tv_dialog_title;
	TextView tv_exit;
	public String str_title = "提示:";
	public String str_content = "确认要退出吗?";
	public String str_btnleft = "退出";
	public String str_btnright = "取消";
	public View.OnClickListener leftbtn = null;
	public View.OnClickListener rightbtn = null;

	public osxDialog(Activity activity, Context context) {
		super(context, R.style.MyDialog);
		this.context = context;
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.osx_exit_view, null);
		this.setContentView(mMenuView);
		self = this;
		LayoutParams lp = mMenuView.getLayoutParams();
		lp.width = osxShareData.screenWidth - 80;
		mMenuView.setLayoutParams(lp);
		button_left = (Button) mMenuView.findViewById(R.id.button_left);
		button_right = (Button) mMenuView.findViewById(R.id.button_right);
		tv_dialog_title = (TextView) mMenuView
				.findViewById(R.id.tv_dialog_title);
		tv_exit = (TextView) mMenuView.findViewById(R.id.tv_exit);
		init();

	}

	public void init() {
		tv_dialog_title.setText(str_title);
		tv_exit.setText(Html.fromHtml("<br/>" + str_content + "<br/>"));
		button_left.setText(str_btnleft);
		button_right.setText(str_btnright);
		button_left.setOnClickListener(leftbtn);
		button_right.setOnClickListener(rightbtn);
	}
}
