package com.basic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

public class LocationUtil implements
AMapLocationListener{
	private Activity mActivity;
	private LocationManagerProxy mLocationManagerProxy;
	public double mLocationLatlngLatitude;// 坐标信息
	public double mLocationLatlngLongitude; //坐标信息
	public float mLocationAccurancy;// 定位精确信息
	public String mLocationMethod;// 定位方法信息
	public String mLocationTime;// 定位时间信息
	public String mLocationDes;// 定位描述信息
	public String mLocationCountry;// 所在国家
	public String mLocationProvince;// 所在省
	public String mLocationCity;// 所在市
	public String mLocationCounty;// 所在区县
	public String mLocationRoad;// 所在街道
	public String mLocationPOI;// POI名称
	public String mLocationCityCode;// 城市编码
	public String mLocationAreaCode;// 区域编码
	
	public Activity getmActivity() {
		return mActivity;
	}

	public void setmActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	/**
	 * 初始化定位
	 */
	public void init(Activity activity) {
		// 初始化定位，只采用网络定位
		this.mActivity=activity;
		mLocationManagerProxy = LocationManagerProxy.getInstance(mActivity);
		mLocationManagerProxy.setGpsEnable(true);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
	}
	
	public void Onpause(){
		mLocationManagerProxy.removeUpdates(this);
		// 销毁定位
		mLocationManagerProxy.destroy();
	}
	
	@Override
	public void onLocationChanged(Location amapLocation) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO 自动生成的方法存根
		if (amapLocation != null
				&& amapLocation.getAMapException().getErrorCode() == 0) {
			// 定位成功回调信息，设置相关消息
			mLocationLatlngLatitude=amapLocation.getLatitude();
			mLocationLatlngLongitude=amapLocation.getLongitude();
			mLocationAccurancy=amapLocation.getAccuracy();
			mLocationMethod=amapLocation.getProvider();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(amapLocation.getTime());
			mLocationTime=df.format(date);
			mLocationDes=amapLocation.getAddress();
			mLocationCountry=amapLocation.getCountry();
			if (amapLocation.getProvince() == null) {
				mLocationProvince="null";
			} else {
				mLocationProvince=amapLocation.getProvince();
			}
			mLocationCity=amapLocation.getCity();
			mLocationCounty=amapLocation.getDistrict();
			mLocationRoad=amapLocation.getRoad();
			mLocationPOI=amapLocation.getPoiName();
			mLocationCityCode=amapLocation.getCityCode();
			mLocationAreaCode=amapLocation.getAdCode();
		} else {
			Log.e("AmapErr","Location ERR:" + amapLocation.getAMapException().getErrorCode());
		}
	}
}
