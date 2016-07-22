package com.example.mapdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public abstract class BaseActivity extends Activity implements OnClickListener {
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentLayout();
		dealLogicBeforeInitView();
		initView();
		dealLogicAfterInitView();


	}

	/**
	 * 设置布局文件
	 */
	public abstract void setContentLayout();

	/**
	 * 在实例化布局之前处理的事件
	 */
	public abstract void dealLogicBeforeInitView();

	/**
	 * 实例化布局组件
	 */
	public abstract void initView();

	/**
	 * 在实例化布局之后处理的事件
	 */
	public abstract void dealLogicAfterInitView();

	/**
	 * onClick方法的封装，在此方法中处理点击事件
	 */
	abstract public void onClickEvent(View view);

	@Override
	public void onClick(View v) {
		onClickEvent(v);
	}

	/**
	 * 得到屏幕宽度
	 * 
	 * @return 宽度
	 */
	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * 得到屏幕高度
	 * 
	 * @return 高度
	 */
	public int getScreenHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param info
	 *            显示的内容
	 */
	public void showToast(String info) {
		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param info
	 *            显示的内容
	 */
	public void showToastLong(String info) {
		Toast.makeText(this, info, Toast.LENGTH_LONG).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param info
	 *            显示的内容
	 */
	public void showToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param info
	 *            显示的内内容
	 */
	public void showToastLong(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示正在加载的进度条
	 * 
	 */
	public void showProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		progressDialog = new ProgressDialog(BaseActivity.this,ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage("加载中...");
		try {
			progressDialog.show();
		} catch (BadTokenException exception) {
			exception.printStackTrace();
		}
	}

	public void showProgressDialog(String msg) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		progressDialog = new ProgressDialog(BaseActivity.this,
				ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage(msg);
		try {
			progressDialog.show();
		} catch (BadTokenException exception) {
			exception.printStackTrace();
		}
	}
	public void showProgressViDialog(String msg) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		progressDialog = new ProgressDialog(BaseActivity.this,
				ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage(msg);
		progressDialog.setCanceledOnTouchOutside(false);
		try {
			progressDialog.show();
		} catch (BadTokenException exception) {
			exception.printStackTrace();
		}
	}

	public ProgressDialog createProgressDialog(String msg) {
		ProgressDialog progressDialog = new ProgressDialog(BaseActivity.this,
				ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage(msg);
		return progressDialog;
	}

	/**
	 * 隐藏正在加载的进度条
	 * 
	 */
	public void dismissProgressDialog() {
		if (null != progressDialog && progressDialog.isShowing() == true) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	/**
	 * 隐藏软键盘
	 */
	private InputMethodManager manager;

	public void hideKeyboard() {
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getWindow().getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
