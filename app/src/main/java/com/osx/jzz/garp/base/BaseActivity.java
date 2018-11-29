package com.osx.jzz.garp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.gyf.barlibrary.ImmersionBar;
import com.osx.jzz.garp.garp.R;
import com.osx.jzz.garp.utils.osxDialog;
import com.osx.jzz.garp.utils.osxLoadDialog;
import com.osx.jzz.garp.utils.osxShareData;
import com.osx.jzz.garp.utils.osxShareUtil;


public class BaseActivity extends AppCompatActivity implements ViewFactory,
        OnClickListener, OnCheckedChangeListener, OnItemClickListener, OnScrollListener {
    public Button headerLeftButtonContansLogo, headerLogoChangeButton,
            headerRightButtonContansLogo;
    public RelativeLayout head_RL;
    public Button headerRightButton;
    public TextView headerContentTitle;
    public Button headerLogoChangeImage;
    public Button headerChangeImage;
    public ImageView headerLogoImage, selectImage, headerLeftButton;
    protected osxShareUtil sharePreference;
    protected Context basecontext;
    private osxLoadDialog progressDialog;
    protected String TAG = "BaseActivity";
    private osxDialog dialog;
    protected Activity activity;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
    }

    /**
     * 初始化
     *
     * @Description: TODO
     * @date 2013-4-20 上午9:33:46
     */
    protected void init(Context context, Activity activity) {
        this.basecontext = context;
        this.activity = activity;

        MyApplication.getInstance().addActivity(activity);
        osxShareData.baseContext = basecontext;
        sharePreference = osxShareData.sharePreference;
        if (sharePreference == null) {
            sharePreference = osxShareUtil.getInstance(context);
            osxShareData.sharePreference = sharePreference;
        }
        Log.i(TAG, context.getClass().getName() + " IS START !");
    }

    // 初始化带logo图标的head
    public void initLogoHead() {
        headerLeftButtonContansLogo = (Button) findViewById(R.id.headLeftButton);
        headerRightButtonContansLogo = (Button) findViewById(R.id.headRightButton);
        headerLogoImage = (ImageView) findViewById(R.id.headLogo);
        headerLogoChangeImage = (Button) findViewById(R.id.headChangebutton);
        selectImage = (ImageView) findViewById(R.id.selectImage);

    }

    // 不带图标的head
    public void initHead() {
        head_RL = (RelativeLayout) findViewById(R.id.head_RL);
        headerLeftButton = (ImageView) findViewById(R.id.head_left_button);
        headerRightButton = (Button) findViewById(R.id.head_right_button);
        headerContentTitle = (TextView) findViewById(R.id.head_content_title);
        headerChangeImage = (Button) findViewById(R.id.head_change_button);
    }

    public void showText(Object obj) {
        Toast.makeText(basecontext, "" + obj, Toast.LENGTH_SHORT).show();
    }

    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = osxLoadDialog.createDialog(basecontext);
            progressDialog.setMessage("正在加载中...");
        }
        progressDialog.show();
    }

    public void startProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = osxLoadDialog.createDialog(basecontext);
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }

    public int parseLocationPoint(String point) {
        return (int) ((Double.parseDouble(point)) * 1E6);
    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(AdapterView<?> adapterview, View view,
                            int position, long id) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog = new osxDialog(activity, basecontext);
            dialog.str_content = "您确定要退出吗?";
            dialog.leftbtn = LeftBtnListener;
            dialog.rightbtn = RightBtnListener;
            dialog.show();
        }
        return false;
    }

    private OnClickListener LeftBtnListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            MyApplication.getInstance().exit();
        }
    };
    private OnClickListener RightBtnListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.cancel();
        }
    };

    /*
     * (non-Javadoc)
     *
     * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
     * AbsListView, int, int, int)
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
     * .widget.AbsListView, int)
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
     * .widget.RadioGroup, int)
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面 bar 发生改变，在不关闭 app 的情况下，退出此界面再进入将记忆最后一次 bar 改变的状态
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.ViewSwitcher.ViewFactory#makeView()
     */
    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        return null;
    }


    public void hint(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
