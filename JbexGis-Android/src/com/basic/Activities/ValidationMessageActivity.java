package com.basic.Activities;

import java.util.ArrayList;
import java.util.Date;

import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.connectservice.FriendRequestService;
import com.basic.connectservice.SettingUserInfo;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.basic.ui.OptionsPopupWindow;
import com.basic.ui.OptionsPopupWindow.OnOptionsSelectListener;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ValidationMessageActivity extends ZJBEXBaseActivity {
    
	private ActionBar actionBar;
	private Intent intent;
	private ImageView image;
	private TextView friendsName;
	private ImageView sex;
	private TextView GroupName;
	private EditText signature_et;
	
    private CustomProgressDialog progressDialog = null;
    
    private OptionsPopupWindow pwOption;
	private ArrayList<String> group = new ArrayList<String>();// Group的数据源
	
	private User frienduser;
	private User owneruser;
	
	private String requestGroup;
	private String validationmessage="";
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validation_message);
		
		intent=getIntent();
		if((intent.getExtras())!=null){
			Bundle data = intent.getExtras();
			owneruser=(User) data.getSerializable("owneruser");
			frienduser = (User) data.getSerializable("frienduser");
			Log.d("frienduser", frienduser.toString());
			Log.d("owneruser", owneruser.toString());
		}
		
		pwOption = new OptionsPopupWindow(this);
		image=(ImageView) findViewById(R.id.image);
		friendsName=(TextView) findViewById(R.id.friendsName);
		sex=(ImageView) findViewById(R.id.sex);
		GroupName=(TextView) findViewById(R.id.GroupName);
		signature_et=(EditText) findViewById(R.id.signature_et);
		
		initDisplayOption();
		initActionBar();
		initView();
		initListener();
		initOptions();
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}

	private void initOptions() {
		// TODO 自动生成的方法存根
		group.add("好友");
		group.add("同学");
	}

	private void initListener() {
		// TODO 自动生成的方法存根
		
	}

	private void initView() {
		// TODO 自动生成的方法存根
		
		requestGroup="好友";
		GroupName.setText(requestGroup);
		mImageLoader.displayImage(ImageStringUtil.getImageURL(frienduser.getPicture()), image, options, animateFirstListener);
		friendsName.setText(frienduser.getUser_nickname());
		
		if(frienduser.getSex()==0)
		sex.setImageResource(0);
		else if(frienduser.getSex()==1)
		sex.setImageResource(R.drawable.man);
		else if(frienduser.getSex()==2)
		sex.setImageResource(R.drawable.woman);
	}

	public void addfriend(View source){
		
		pwOption.setPicker(group);
		pwOption.setLabels("放入的群组");
		// 设置默认选中的三级项目
		pwOption.setSelectOptions(0, 0, 0);
		pwOption.setOnoptionsSelectListener(new OnOptionsSelectListener() {
			
			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				// TODO 自动生成的方法存根
				GroupName.setText(String.valueOf(group.get(options1)));
				requestGroup=group.get(options1);
			}
		});
		pwOption.showAtLocation(source, Gravity.BOTTOM, 0, 0);
	}
	
	public void sendRequest(View source){
		validationmessage=signature_et.getText().toString();
		addFriendsRequestAsyncTask a=new addFriendsRequestAsyncTask();
		a.execute();
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
		TextView txt=(TextView) view.findViewById(R.id.main_Text);
		rightButton.setBackgroundResource(0);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		
		txt.setText("验证信息");
		
		backtxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ValidationMessageActivity.this.finish();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ValidationMessageActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.validation_message, menu);
		return true;
	}
	
	public class addFriendsRequestAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				boolean flag;
				flag=FriendRequestService.addFriendRequest(owneruser.getEmail(), frienduser.getEmail(), requestGroup, new Date(), validationmessage);
				if(flag)
					return "true";
				else return "false";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				if(result.endsWith("true"))
					Toast.makeText(ValidationMessageActivity.this, "请求成功！", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(ValidationMessageActivity.this, "您已经请求过了，请不要重复请求", Toast.LENGTH_SHORT).show();
				if (progressDialog != null){
					progressDialog.dismiss();
					progressDialog = null;
				}	
				
			}
			
	      //这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			@Override
			protected void onPreExecute() {
				// TODO 自动生成的方法存根
				super.onPreExecute();
				
				if (progressDialog == null){
					progressDialog = CustomProgressDialog.createDialog(ValidationMessageActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
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
