package com.osx.jzz.garp.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.osx.jzz.garp.base.BaseActivity;
import com.osx.jzz.garp.garp.R;
import com.osx.jzz.garp.rewrite.CustomImageView;

public class LoginActivity extends BaseActivity {
    Context context;
    Activity activity;
    CustomImageView login_touxian;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        activity = this;
        super.init(context, activity);
        this.initHead();
        this.head_RL.setBackgroundResource(R.color.unified);
        this.headerContentTitle.setText(R.string.app_name_);
        login_touxian=(CustomImageView)findViewById (R.id.login_touxian);
        login_touxian.bringToFront();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }
}
