package com.basic.Activities;

import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.basic.Activities.R;
import com.basic.Activities.R.layout;
import com.basic.Activities.R.menu;
import com.basic.service.model.User;
import com.basic.util.LocationUtil;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.zondy.mapgis.android.annotation.Annotation;
import com.zondy.mapgis.android.annotation.AnnotationView;
import com.zondy.mapgis.android.graphic.GraphicPolylin;
import com.zondy.mapgis.android.graphic.GraphicText;
import com.zondy.mapgis.android.mapview.MapView;
import com.zondy.mapgis.android.mapview.MapView.MapViewAnnotationListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewLongTapListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewRenderContextListener;
import com.zondy.mapgis.geometry.Dot;
import com.zondy.mapgis.geometry.Rect;
import com.zondy.mapgis.route.Route;
import com.zondy.mapgis.route.RouteAnalysis;
import com.zondy.mapgis.route.RouteAnalysis.FromAndTo;

import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RouteActivity extends ZJBEXBaseActivity implements
		MapViewAnnotationListener, MapViewRenderContextListener,
		AMapLocationListener, MapViewLongTapListener {

	private User owneruser = new User();
	private Intent intent;
	private ActionBar actionBar;
	private MapView mapView;
	private String path = Environment.getExternalStorageDirectory().getPath();

	private LocationManagerProxy mLocationManagerProxy;
	private double dot_x, dot_y;

	private RouteAnalysis rtAnalysis;
	Route testRoute;
	private int count = 0;
	Dot point[] = new Dot[2];
	Dot pointofmine = new Dot();
	private Bitmap bmp1 = null;
	private Bitmap mybmp = null;
    private boolean flag=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);

		initactionBar();
		initLocation();
		mapView = (MapView) super.findViewById(R.id.mapview);
		mapView.loadFromFile(path + "/mapgis/map/wuhan/wuhan.xml"); // 加载地图
		mapView.setRenderContextListener(RouteActivity.this); // 注册四个监听
		mapView.setAnnotationListener(RouteActivity.this);
		mapView.setLongTapListener(this);

		bmp1 = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.locationofend)).getBitmap();
		mybmp = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.locationofmine)).getBitmap();
	}

	private void initLocation() {
		// TODO 自动生成的方法存根
		// 初始化定位，只采用网络定位
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.setGpsEnable(true);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, -1, 15, this);
	}

	private void initView() {
		// TODO 自动生成的方法存根
		point[0] = new Dot(dot_x, dot_y);

		Dot mpoint = mapView.locationToMapPoint(point[0]);
		mapView.zoomToCenter(mpoint, 4.0f, false);
		Annotation annotation = new Annotation("", "", mpoint, mybmp);
		mapView.getAnnotationLayer().addAnnotation(annotation);
		mapView.refresh();
	}

	private void initactionBar() {
		// TODO 自动生成的方法存根x
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);

		View view = actionBar.getCustomView();
		ImageButton nnavigation = (ImageButton) view
				.findViewById(R.id.btn_nnavigation);
		ImageButton back = (ImageButton) view.findViewById(R.id.btn_back);
		TextView txt = (TextView) view.findViewById(R.id.main_Text);
		TextView backtxt = (TextView) view.findViewById(R.id.backtxt);

		nnavigation.setBackgroundResource(0);

		txt.setText("导航");

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				RouteActivity.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				RouteActivity.this.finish();
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		// 移除定位请求
		// mLocationManagerProxy.removeUpdates(this);
		// 销毁定位
		// mLocationManagerProxy.destroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.route, menu);
		return true;
	}

	@Override
	public void mapViewRenderContextCreated() {
		// TODO 自动生成的方法存根
		rtAnalysis=new RouteAnalysis();	
		rtAnalysis.setMap(mapView.getMap());
	}

	@Override
	public void mapViewRenderContextDestroyed() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mapViewClickAnnotation(MapView arg0, Annotation arg1) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mapViewClickAnnotationView(MapView arg0, AnnotationView arg1) {
		// TODO 自动生成的方法存根

	}

	@Override
	public AnnotationView mapViewViewForAnnotation(MapView arg0, Annotation arg1) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public boolean mapViewWillHideAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean mapViewWillShowAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void processMessage(Message msg) {
		// TODO 自动生成的方法存根
		if (msg.what == Config.SEND_NOTIFICATION) {
			Bundle bundle = msg.getData();
			ChatMessage chatMessage = (ChatMessage) bundle
					.getSerializable("chatMessage");
			saveToDb(chatMessage, Config.DateBase_GET_MESSAGE);

			sendNotifycation(chatMessage.getSelf(), chatMessage.getFriend());
		} else if (msg.what == Config.SEND_NOTIFICATION_JBEX_FRIEND) {
			sendNotifycation_JBEXFriend();
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
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
			dot_y = amapLocation.getLatitude();
			dot_x = amapLocation.getLongitude();
			if(flag){
			initView();
			flag=false;
			}
		}
	}

	@Override
	public boolean mapViewLongTap(PointF viewPoint) {
		// TODO Auto-generated method stub
		if (testRoute != null) {
			// 清空之前的路径
			mapView.getGraphicLayer().removeAllGraphics();
		}

		if (count == 2) {
			// 移除标注
			mapView.getAnnotationLayer().removeAnnotation(1);
			//mapView.getAnnotationLayer().removeAllAnnotations();
			mapView.getGraphicLayer().removeAllGraphics();
			count = 0;
			
			
		}

		count++;

		if (count == 1) {
			// 将视图坐标转换成地图坐标
			point[1] = mapView.viewPointToMapPoint(viewPoint);
			Annotation annotation0 = new Annotation("Annotation1", "标注1",point[1], bmp1);
			// 不弹出标注视图
			annotation0.setCanShowAnnotationView(false);
			mapView.getAnnotationLayer().addAnnotation(annotation0);
			mapView.refresh();
			doResearch();
			
		}

		return false;
	}

 public void doResearch() {
		Dot transPnts[] = new Dot[2];
		//地图坐标转化为定位坐标
		//transPnts[0] = mapView.mapPointToLocation(point[0]);
		transPnts[1] = mapView.mapPointToLocation(point[1]);
		
		FromAndTo fromAndTo = new FromAndTo(point[0], transPnts[1]);

		// 获取两点之间存在的路径
		List<Route> testRoutes = rtAnalysis.calculateRoute(fromAndTo, rtAnalysis.DrivingLeastDistance, null, null, 0);
		
		if (testRoutes.size() < 1) {
			Toast.makeText(this, "两者之间不存在相应的路径", Toast.LENGTH_SHORT).show();
			return;
		}

	
		// 取出第一条路径
		testRoute = testRoutes.get(0);
		// 获取外包
		Rect bRect = testRoute.getBoundingRect();
		// 定位坐标转换成地图坐标
		Dot blDot = mapView.locationToMapPoint(new Dot(bRect.getXMin(), bRect
				.getYMin()));
		Dot brDot = mapView.locationToMapPoint(new Dot(bRect.getXMax(), bRect
				.getYMax()));
		Rect disRect = new Rect(blDot.getX(), blDot.getY(), brDot.getX(),
				brDot.getY());
		mapView.zoomToRange(disRect, false);

		// 获取路段数目
		int segCount = testRoute.getStepCount();
		// 获取路径描述
		String overViewString = testRoute.getOverview();
		// 获取路径坐标
		Dot[] coorsDots = testRoute.getCoors();
		Dot[] routeDots = new Dot[coorsDots.length];
		for (int i = 0; i < coorsDots.length; i++) {
			routeDots[i] = mapView.locationToMapPoint(coorsDots[i]);
		}

		// 设置显示的文本
		GraphicText routeText = new GraphicText(routeDots[1], overViewString);
		routeText.setFontSize(25);
		

		// 设置绘制的路径
		GraphicPolylin routeLine = new GraphicPolylin(routeDots);
		routeLine.setColor(Color.argb(255, 0, 255,0 ));
		routeLine.setLineWidth(10);

		// 绘制该路径
		mapView.getGraphicLayer().addGraphic(routeLine);
		// 添加路径描述
		mapView.getGraphicLayer().addGraphic(routeText);

		mapView.refresh();
	}

}
