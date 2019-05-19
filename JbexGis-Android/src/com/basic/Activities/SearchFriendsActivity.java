package com.basic.Activities;

import com.basic.connectservice.UserService;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.basic.util.StringUtils;
import com.message.net.ChatMessage;
import com.message.net.Config;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFriendsActivity extends ZJBEXBaseActivity {
	
	private ActionBar actionBar;
	private Intent intent;
	private User owner;
	private User frienduser;
	private EditText edittext;
	private Button btn_OK;
	private HandlerSearchFriends handler;   //创建一个Handler实例
	private CustomProgressDialog progressDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_friends);
		
		 intent=getIntent();
		 if ((intent.getExtras()) != null) {
				Bundle data = intent.getExtras();
				owner = (User) data.getSerializable("user");
				Log.d("user", owner.toString());
			}
		 
		 edittext=(EditText) super.findViewById(R.id.emailText);
		 btn_OK=(Button) super.findViewById(R.id.btn_OK);
		 handler=new HandlerSearchFriends();  
		 initActionBar();
	     initView();
		 initListener();
	}

	private void initListener() {
		// TODO Auto-generated method stu
	}
	
	public void searchfriend(View soucre){
		 String email=edittext.getText().toString();
		if(email.equals(owner.getEmail())){
			Toast.makeText(SearchFriendsActivity.this, "查询的好友是自己=.=", Toast.LENGTH_SHORT).show();
		}
		else if(StringUtils.isEmail(email)){
			/*new Thread (new Runnable() {
				@Override
				public void run() {
					// TODO 自动生成的方法存根
					frienduser=getFriendsUser.getUser(email);
					 Message msg = new Message(); 
					if(frienduser==null){
						msg.what=1;
						SearchFriendsActivity.this.handler.sendMessage(msg);
					}
					else {
						//Toast.makeText(SearchFriendsActivity.this, frienduser.toString(), Toast.LENGTH_SHORT).show();
					}
					
				}
			}).start();	*/
			SearchFriendsAsyncTask a=new SearchFriendsAsyncTask(email);
			a.execute();
		}
			else{
				Toast.makeText(SearchFriendsActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
			}
	}
	private void initView() {
		// TODO Auto-generated method stub
		
	}

	private void initActionBar() {
		// TODO Auto-generated method stub
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
		
		txt.setText("寻找好友");
		
		backtxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				SearchFriendsActivity.this.finish();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				SearchFriendsActivity.this.finish();
			}
		});
	}
  
	public class HandlerSearchFriends extends  Handler{

		public HandlerSearchFriends() { 
		} 

		public HandlerSearchFriends(Looper L) { 
			super(L); 
		} 
		
		// 子类必须重写此方法，接受数据 
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			super.handleMessage(msg);
			//此处更新UI
			switch (msg.what) {
			case 1:
				Toast.makeText(SearchFriendsActivity.this, "搜索不到该用户！", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}	
		}
		
	}
	
	public class SearchFriendsAsyncTask extends AsyncTask<Integer, Integer, String>{
       private String email;
       
		public SearchFriendsAsyncTask(String email) {
		super();
		this.email = email;
	}
     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
	//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO 自动生成的方法存根
			frienduser=UserService.getUser(email,"email");
			if(frienduser==null){
				return "null";
			}
			else if(UserService.Isfriend(owner.getEmail(), frienduser.getEmail())){
				return "yes";
			}
			else return "no";
			
		}
     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
	//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
		@Override
		protected void onPostExecute(String result) {
			// TODO 自动生成的方法存根
			super.onPostExecute(result);
			
			if (progressDialog != null){
				progressDialog.dismiss();
				progressDialog = null;
			}
			
			if(result.equals("null")){
				Toast.makeText(SearchFriendsActivity.this, "搜索不到该用户！", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("yes")){
				Intent friendInfo=new Intent(SearchFriendsActivity.this, FriendsInfoActivity.class);
				Bundle data=new Bundle();
				data.putSerializable("owneruser", owner);
				data.putSerializable("frienduser", frienduser);
				data.putBoolean("flag", false);
				friendInfo.putExtras(data);
				SearchFriendsActivity.this.startActivity(friendInfo);
			}
			else if(result.equals("no")){
				Intent addfriend=new Intent(SearchFriendsActivity.this, AddFriendActivity.class);
				Bundle data=new Bundle();
				data.putSerializable("owneruser", owner);
				data.putSerializable("frienduser", frienduser);
				addfriend.putExtras(data);
				SearchFriendsActivity.this.startActivity(addfriend);
			}
			
		}
		
      //这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
		@Override
		protected void onPreExecute() {
			// TODO 自动生成的方法存根
			super.onPreExecute();
			
			if (progressDialog == null){
				progressDialog = CustomProgressDialog.createDialog(SearchFriendsActivity.this);
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
