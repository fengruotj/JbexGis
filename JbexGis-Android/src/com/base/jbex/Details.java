package com.base.jbex;

import com.basic.Activities.R;
import com.basic.Activities.R.layout;
import com.basic.Activities.R.menu;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.base.jbex.Details;
import com.mapgis.model.PublicUserIfo;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends ZJBEXBaseActivity {

	
	private ActionBar actionBar = null;
	TextView uname = null;
	TextView utime = null;
	TextView utitle = null;
	TextView udetails = null;
	ImageView userimage=null;
	
	//图片加载框架组件
		private DisplayImageOptions options;
		private ImageLoader mImageLoader;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		initDisplayOption();
		
		initActionBar();
		ongetdata();
	}
    
	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}
	
	
	public void ongetdata()
	{
		uname = (TextView) super.findViewById(R.id.details_username);
		utime = (TextView) super.findViewById(R.id.details_time);
		utitle = (TextView) super.findViewById(R.id.details_title);
		udetails = (TextView) super.findViewById(R.id.details_details);
		userimage=(ImageView) super.findViewById(R.id.detal_image);
		
		Intent intent = this.getIntent();
		Bundle data = intent.getExtras();
		PublicUserIfo publicuserifo= (PublicUserIfo) data.getSerializable("Mpoublicuserifo");

		uname.setText(publicuserifo.getUsername().toString());
		utitle.setText(publicuserifo.getTitle());
		utime.setText(publicuserifo.getTime());
		udetails.setText(publicuserifo.getDetail());
		
		mImageLoader.displayImage(ImageStringUtil.getImageURL(publicuserifo.getFavicon()), userimage, options, animateFirstListener);
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
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		rightButton.setBackgroundResource(0);	
		txt.setText("结伴详情");
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Details.this.finish();
			}
		});
		
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Details.this.finish();
			}
		});
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
