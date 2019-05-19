package com.basic.Activities;

import com.basic.service.model.User;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.zondy.mapgis.android.locationmanager.GPSManager;
import com.zondy.mapgis.android.locationmanager.GpsLocationInfo;
import com.zondy.mapgis.android.locationmanager.LOCManager;
import com.zondy.mapgis.android.mapview.MapView;
import com.zondy.mapgis.core.android.locationmanager.GpsInfo;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class NavigationActivity extends ZJBEXBaseActivity {
	
	private ActionBar actionBar;
	private Intent intent;
	private User owneruser;
	private GpsInfo gpsinfo;
	private GPSManager gpsmanger;
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
	    intent=getIntent();
	    if((intent.getExtras())!=null){
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("user",owneruser.toString());	
		};
		
		gpsmanger=GPSManager.getInstance();
		gpsmanger.create(this);
		gpsmanger.start();
        /*
		gpsinfo=
		double x=gpsinfo.getmlatitude();
		double y=gpsinfo.getmlongitude();*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}

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
	}

}
