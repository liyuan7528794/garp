package com.osx.jzz.garp.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.osx.jzz.garp.garp.R;
import com.osx.jzz.garp.utils.osxUpdate;
import com.osx.jzz.garp.utils.osxDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class UpdateManager {
	/* URL */
	private URL url = null;
	HttpURLConnection urlConn;
	InputStream inputStream;
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	private Activity mActivity;
	/* 更新进度条 */
	private ProgressBar mProgress;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};
	osxDialog dialog;
	osxUpdate updatedialog;

	public UpdateManager(Context context, Activity activity) {
		this.mContext = context;
		this.mActivity = activity;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate() {

		if (isUpdate()) {
			// 显示提示对话框
			showNoticeDialog();
		} else {
			Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG)
					.show();
		}
	}

	public InputStream getInputStreamFromUrl() {
		try {
			url = new URL("http://www.5c5h.cn/mobile/usuj.xml");
			urlConn = (HttpURLConnection) url.openConnection();
			inputStream = urlConn.getInputStream();
		} catch (Exception e) {
		}
		return inputStream;
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private boolean isUpdate() {
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		// 把version.xml放到网络上，然后获取文件信息

		InputStream inStream = getInputStreamFromUrl();
		// 版本判断
		System.out.println(versionCode);
		return true;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"com.example.xxx.Base", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		// 更新
		dialog = new osxDialog(mActivity, mContext);
		dialog.str_title = mContext.getString(R.string.soft_update_title);
		dialog.str_content = mContext
				.getString(R.string.soft_update_title_tixin);
		dialog.str_btnleft = mContext.getString(R.string.soft_update_updatebtn);
		dialog.str_btnright = mContext.getString(R.string.soft_update_later);
		dialog.leftbtn = LeftBtnListener;
		dialog.rightbtn = RightBtnListener;
		dialog.show();
	}

	private OnClickListener LeftBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.dismiss();
			// 显示下载对话框
			showDownloadDialog();
		}
	};
	private OnClickListener RightBtnListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.cancel();
		}
	};

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		updatedialog = new osxUpdate(mActivity, mContext);
		updatedialog.str_title = mContext.getString(R.string.soft_updating);
		updatedialog.btnlistener = new OnClickListener() {
			@Override
			public void onClick(View c) {
				dialog.dismiss();
				cancelUpdate = true;
			}
		};
		updatedialog.show();
		mProgress = updatedialog.mProgress;
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";

					URL url = new URL(
							"http://www.5c5h.cn/yousheyoujia_for_android.apk");
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];

					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			updatedialog.cancel();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("name"));
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
