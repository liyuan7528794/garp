package com.osx.jzz.garp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.osx.jzz.garp.garp.R;


public class osxUpdate extends Dialog {

	private Button canlebutton;
	private View mMenuView;
	Context context;
	Activity activity;
	TextView tv_dialog_title;
	public String str_title = "提示:";
	public String str_btn = "取消";
	public View.OnClickListener btnlistener = null;
	public ProgressBar mProgress;
	LinearLayout ll_content;

	public osxUpdate(Activity activity, Context context) {
		super(context, R.style.MyDialog);
		this.context = context;
		this.activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);                  
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.osx_update, null);
		this.setContentView(mMenuView);
		LayoutParams lp = mMenuView.getLayoutParams();
		lp.width = osxShareData.screenWidth - 80;
		mMenuView.setLayoutParams(lp);
		ll_content = (LinearLayout) mMenuView.findViewById(R.id.ll_content);
		mProgress = (ProgressBar) mMenuView.findViewById(R.id.update_progress);
		canlebutton = (Button) mMenuView.findViewById(R.id.canlebutton);
		tv_dialog_title = (TextView) mMenuView
				.findViewById(R.id.tv_dialog_title);
		tv_dialog_title.setText(str_title);
		canlebutton.setText(str_btn);
		canlebutton.setOnClickListener(btnlistener);
	}
}
