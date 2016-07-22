package com.example.mapdemo;

import android.view.View;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends BaseActivity{

	private BaiduMap mBaiduMap = null;
	private MapView mMapView;
	// 定位相关
	LocationClient mLocClient;
	private double latitude;
	private double longitude;
	private BDLocation bdLocation;
	public MyLocationListenner myListener = new MyLocationListenner();

	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			dismissProgressDialog();
			bdLocation = location;
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			mLocClient.stop();
			setMap();
			addMapMark();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void setContentLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		// option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		option.setAddrType("all");
		option.setPriority(LocationClientOption.GpsFirst);
		mLocClient.setLocOption(option);
		mLocClient.start();

		mBaiduMap.getUiSettings().setCompassEnabled(true);// 隐藏百度地图指南针
		mMapView.removeViewAt(1);// 隐藏百度地图logo
		mMapView.removeViewAt(2);// 隐藏比例尺
		//设置缩放比例
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(12).build()));
		int childCount = mMapView.getChildCount();
		View zoom = null;
		for (int i = 0; i < childCount; i++) {
			View child = mMapView.getChildAt(i);
			if (child instanceof ZoomControls) {
				zoom = child;
				break;
			}
		}
		zoom.setVisibility(View.GONE);
		showProgressDialog("定位中……");
	}

	public void addMapMark(){
		addMark(22.573515, 114.105752,1);
		addMark(22.570067, 114.109374,2);
		addMark(22.564653, 114.102337,3);
	}
	
	@Override
	public void dealLogicAfterInitView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClickEvent(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dealLogicBeforeInitView() {
		// TODO Auto-generated method stub
		
	}

	
	/***
	 * 设置自己在地图的位置
	 */
	public void setMap(){
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(bdLocation.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(latitude)
				.longitude(longitude).build();
		mBaiduMap.setMyLocationData(locData);
		LatLng ll = new LatLng(latitude,
				longitude);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
		
	}
	
	public void addMark(double latitude,double longitude,int i){
		LatLng latLng = new LatLng(latitude, longitude);
		//准备 marker 的图片  
		View view = getLayoutInflater().inflate(R.layout.text, null);
		TextView textview = (TextView) view.findViewById(R.id.textview);
		textview.setText(i+"");
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
		MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(latLng);  
		mBaiduMap.addOverlay(markerOptions);
	}

}
