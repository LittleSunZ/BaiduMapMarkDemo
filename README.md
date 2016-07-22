# BaiduMapMarkDemo
百度地图根据经纬度添加自定义mark


关键函数

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
