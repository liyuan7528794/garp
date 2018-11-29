package com.osx.jzz.garp.rewrite;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageSwitcher;

public class CustomImageSwitcher extends ImageSwitcher {
	private ViewPager mPager;

	public CustomImageSwitcher(Context paramContext) {
		super(paramContext);
	}

	public CustomImageSwitcher(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
		// this.mPager.requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(paramMotionEvent);
	}

	public ViewPager getmPager() {
		return this.mPager;
	}

	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
		// this.mPager.requestDisallowInterceptTouchEvent(true);
		return super.onInterceptTouchEvent(paramMotionEvent);
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		// this.mPager.requestDisallowInterceptTouchEvent(true);
		return super.onTouchEvent(paramMotionEvent);
	}

	public void setmPager(ViewPager paramViewPager) {
		this.mPager = paramViewPager;
	}
}