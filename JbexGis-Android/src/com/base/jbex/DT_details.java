package com.base.jbex;

import com.basic.Activities.R;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.service.model.DynamicInfo;
import com.basic.service.model.User;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DT_details extends ZJBEXBaseActivity {

	private ActionBar actionBar;
	private Intent intent;
	private User owneruser = new User(); // User
	private DynamicInfo dynamicinfo=new DynamicInfo();
	
	private ImageView dt_userimage;
	private TextView dt_username;
	private TextView dt_details;
	private TextView dt_time;
	private ImageView dynamicinfo1;
	private ImageView dynamicinfo2;
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dt_details);

		intent = getIntent();

		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			dynamicinfo = (DynamicInfo) data.getSerializable("dynamicinfo");
			Log.d("user", owneruser.toString());
		}
		
		dt_userimage=(ImageView) findViewById(R.id.dt_userimage);
		dt_username=(TextView) findViewById(R.id.dt_username);
		dt_details=(TextView) findViewById(R.id.dt_details);
		dt_time=(TextView) findViewById(R.id.dt_time);
		dynamicinfo1=(ImageView) findViewById(R.id.dynamicinfo1);
		dynamicinfo2=(ImageView) findViewById(R.id.dynamicinfo2);
		
		initActionBar();
		initDisplayOption();
		initView();
		initListener();
		
	}

	
	
	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}



	private void initListener() {
		// TODO 自动生成的方法存根
		
	}



	private void initView() {
		// TODO 自动生成的方法存根
		mImageLoader.displayImage(ImageStringUtil.getImageURL(dynamicinfo.getTUser().getPicture()), dt_userimage, options, animateFirstListener);
		dt_username.setText(dynamicinfo.getTUser().getUser_nickname());
		dt_details.setText(dynamicinfo.getDetail());
		dt_time.setText(sdf.format(dynamicinfo.getTime()));
		if(dynamicinfo.getPicture1().equals("null")!=true)
		mImageLoader.displayImage(ImageStringUtil.getImageURLBydynamic(dynamicinfo.getPicture1()), dynamicinfo1, ImageOptions.initDisplayOptionsRange(), animateFirstListener);
		if(dynamicinfo.getPicture2().equals("null")!=true)
		mImageLoader.displayImage(ImageStringUtil.getImageURLBydynamic(dynamicinfo.getPicture2()), dynamicinfo2, ImageOptions.initDisplayOptionsRange(), animateFirstListener);
	}



	/**
	 * 加载改变actionBar
	 */
	private void initActionBar() {
		// TODO 自动生成的方法存根
		actionBar=getActionBar();
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

		txt.setText("动态详情");

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				DT_details.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				DT_details.this.finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dt_details, menu);
		return true;
	}
  
	@Override
	public void processMessage(Message msg) {
		// TODO 自动生成的方法存根
		 if(msg.what==Config.SEND_NOTIFICATION){
				Bundle bundle=msg.getData();
				ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
				saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);
				
				sendNotifycation(chatMessage.getSelf(),chatMessage.getFriend());
			}
	}
}
