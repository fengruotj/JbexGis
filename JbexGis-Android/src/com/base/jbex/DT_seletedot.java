package com.base.jbex;

import com.basic.Activities.R;


import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.service.model.User;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.zondy.mapgis.android.annotation.Annotation;
import com.zondy.mapgis.android.annotation.AnnotationView;
import com.zondy.mapgis.android.mapview.MapView;
import com.zondy.mapgis.android.mapview.MapView.MapViewAnnotationListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewLongTapListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewRenderContextListener;
import com.zondy.mapgis.android.mapview.MapView.MapViewTapListener;
import com.zondy.mapgis.geometry.Dot;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;


public class DT_seletedot extends ZJBEXBaseActivity implements MapViewTapListener ,MapViewLongTapListener,
MapViewRenderContextListener,MapViewAnnotationListener 
{

	private Intent intent;
	private User owneruser=new User();
	
	private Bitmap bmp1 = null;
	public MapView mapview = null;
	ActionBar actionBar =null;
	AnnotationView annotationView = null;
	private String path = Environment.getExternalStorageDirectory().getPath();
	double double_x = 0 , double_y = 0;
	int count = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dt_seletedot);
		
        initActionBar();
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("owneruser", owneruser.toString());
		}
		
		Toast.makeText(this, "长按选择一个地点", Toast.LENGTH_LONG).show();
		
		mapview= (MapView) super.findViewById(R.id.mapview6);
		mapview.loadFromFile(path + "/mapgis/map/wuhan/wuhan.xml"); // 加载地图
		mapview.setRenderContextListener(this); // 需要注册相应监听接口类的监听器
		mapview.setAnnotationListener(this);
		mapview.setTapListener(this);
		mapview.setLongTapListener(this);
	}

	private void initActionBar() {
		// TODO 自动生成的方法存根
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
		actionBar.setDisplayShowCustomEnabled(true);
		View view=actionBar.getCustomView();
		ImageButton rightButton=(ImageButton) view.findViewById(R.id.btn_nnavigation);
		ImageButton back=(ImageButton) view.findViewById(R.id.btn_back);
		TextView txt = null;	
		txt=(TextView) view.findViewById(R.id.main_Text);
		rightButton.setBackgroundResource(R.drawable.button_select);	
		txt.setText("选择地点");
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		
		rightButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub                                          传递数据  跳转页面
				if(double_x!=0 && double_y!=0){
				Intent intent = new Intent(DT_seletedot.this,DT_edit.class);
                
				Bundle data = new Bundle();
				data.putSerializable("owneruser", owneruser);
				intent.putExtras(data);
				
				intent.putExtra("dot1", double_x);
				intent.putExtra("dot2", double_y);
				DT_seletedot.this.startActivity(intent);
				DT_seletedot.this.finish();
				}
				else{
					Toast.makeText(DT_seletedot.this, "请选择一个坐标", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				DT_seletedot.this.finish();
			}
		});
		
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				DT_seletedot.this.finish();
			}
		});
	}
	
	@Override
	public void mapViewClickAnnotation(MapView arg0, Annotation arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mapViewClickAnnotationView(MapView arg0, AnnotationView arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AnnotationView mapViewViewForAnnotation(MapView arg0, Annotation arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean mapViewLongTap(PointF viewPoint) {
		// TODO Auto-generated method stub
		if (count == 2)
		{
			mapview.getAnnotationLayer().removeAllAnnotations();
			count =0;
		}
		count++;
		if (count ==1)
		{
			// 将视图坐标转换成地图坐标
			Dot point = mapview.viewPointToMapPoint(viewPoint);
			String strDeString = String.format("x = %f,%ny = %f", point.getX(),point.getY());
			double_x =  mapview.mapPointToLocation(point).getX();
			double_y =  mapview.mapPointToLocation(point).getY();				
			// 创建annotation
			Annotation annotationTap = new Annotation("标注", strDeString, point,null);
			bmp1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.annotation1)).getBitmap();
			mapview.getAnnotationLayer().addAnnotation(new Annotation("zuobiao", strDeString, point, bmp1));
			mapview.refresh();
			// 在annotationLayer中添加annotation
			mapview.getAnnotationLayer().addAnnotation(annotationTap);
			// 控制是否显示annotationview
			annotationTap.showAnnotationView();
		}
		return false;
	}

	@Override
	public boolean mapViewWillHideAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mapViewWillShowAnnotationView(MapView arg0,
			AnnotationView arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mapViewRenderContextCreated() {
		// TODO Auto-generated method stub
		mapview.setShowUserLocation(true);
		mapview.zoomTo(4.0f, false);
	}

	@Override
	public void mapViewRenderContextDestroyed() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mapViewTap(PointF arg0) {
		// TODO Auto-generated method stub
		
	}
/*
	@Override
	public void processMessage(Message msg) {
		// TODO 自动生成的方法存根
		if(msg.what==Config.SEND_NOTIFICATION){
			Bundle bundle=msg.getData();
			ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
			saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);

			sendNotifycation(chatMessage.getSelf(),chatMessage.getFriend());}
		else if (msg.what==Config.SEND_NOTIFICATION_JBEX_FRIEND){
			sendNotifycation_JBEXFriend();
		}
	}*/

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
		if(msg.what==Config.SEND_NOTIFICATION){
			Bundle bundle=msg.getData();
			ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
			saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);

			sendNotifycation(chatMessage.getSelf(),chatMessage.getFriend());}
		else if (msg.what==Config.SEND_NOTIFICATION_JBEX_FRIEND){
			sendNotifycation_JBEXFriend();
		}
	}
}
