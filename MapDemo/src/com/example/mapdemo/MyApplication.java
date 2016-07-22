package com.example.mapdemo;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

/**
 * 
 * @Description:  
 * @author 
 * @date 
 *
 */
public class MyApplication extends Application{
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(this);
	}
}
