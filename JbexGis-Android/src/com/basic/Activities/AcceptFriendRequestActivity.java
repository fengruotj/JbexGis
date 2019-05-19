package com.basic.Activities;

import java.util.ArrayList;
import java.util.List;

import com.basic.adapter.MenuItemAdapter;
import com.basic.connectservice.AttentionService;
import com.basic.connectservice.FriendRequestService;
import com.basic.connectservice.FriendService;
import com.basic.model.menuItemBean;
import com.basic.service.model.FriendRequest;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.basic.ui.OptionsPopupWindow;
import com.basic.ui.OptionsPopupWindow.OnOptionsSelectListener;
import com.message.net.ChatMessage;
import com.message.net.Config;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AcceptFriendRequestActivity extends ZJBEXBaseActivity implements OnItemClickListener{
	
	private ActionBar actionBar;
	private Intent intent;
	
	private ListView listMenu;
	private MenuItemAdapter itemAdapter;
	private List<menuItemBean> mList = new ArrayList<menuItemBean>();
	private OptionsPopupWindow pwOption;
	private ArrayList<String> Option = new ArrayList<String>();
	
	private User owneruser = new User(); // User
	private FriendRequest friendrequest=new FriendRequest();
	private String GroupName;
	
	private CustomProgressDialog progressDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept_friend_request);
		
		intent = getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			friendrequest=(FriendRequest) data.getSerializable("friendrequest");
			Log.d("user", owneruser.toString());
			Log.d("friendrequest", friendrequest.toString());
		}
		
		pwOption = new OptionsPopupWindow(this);
		listMenu = (ListView) super.findViewById(R.id.listmenu);
		
		initActionBar();
		initView();
		initListener();
	}

	private void initListener() {
		// TODO 自动生成的方法存根
		
	}

	private void initView() {
		// TODO 自动生成的方法存根
		menuItemBean item1 = new menuItemBean("备注", friendrequest.getOwneruser().getUser_nickname(), 0, 0);
		menuItemBean item2 = new menuItemBean("分组", "好友", 0 ,R.drawable.btn_right);
		
		GroupName="好友";
		
		mList.add(item1);
		mList.add(item2);
		
		itemAdapter = new MenuItemAdapter(this, mList);
		listMenu.setAdapter(itemAdapter);
		listMenu.setOnItemClickListener(this);
		
		Option=new ArrayList<String>();
		Option.add("好友");
		Option.add("同学");
	}

	private void initActionBar() {
		// TODO 自动生成的方法存根
		
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

		txt.setText("添加好友");

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				AcceptFriendRequestActivity.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				AcceptFriendRequestActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept_friend_request, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View currentview, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		switch (arg2) {
		case 0://点击了备注
			//Toast.makeText(MoreInfoActivity.this, "0", Toast.LENGTH_SHORT).show();
			break;
		case 1://点击了分组
			pwOption.setPicker(Option);
			pwOption.setLabels("选择分组");
			pwOption.setSelectOptions(0, 0, 0);
			
			pwOption.setOnoptionsSelectListener(new OnOptionsSelectListener() {
				
				@Override
				public void onOptionsSelect(int options1, int option2, int options3) {
					// TODO 自动生成的方法存根
					GroupName=Option.get(options1);
					 menuItemBean item = new menuItemBean("分组", GroupName, 0 ,R.drawable.btn_right);
					  mList.set(1, item);
					  itemAdapter.onDateChange(mList);
				}
			});
			
			pwOption.showAtLocation(currentview, Gravity.BOTTOM, 0, 0);
			break;
		}
	}

	public void addFriend(View source){
		addFriendAsyncTask a=new addFriendAsyncTask();
		a.execute();
	}
	
	public class addFriendAsyncTask extends AsyncTask<Integer, Integer, String>{
		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根	
				boolean flag;
				flag=FriendService.addFriendGroup(owneruser.getEmail(), friendrequest.getOwneruser().getEmail(),GroupName);
				flag=FriendService.addFriendGroup(friendrequest.getOwneruser().getEmail(), owneruser.getEmail(),friendrequest.getRequestgroup());
				FriendRequestService.deleteFriendReuqest(owneruser.getEmail(), friendrequest.getOwneruser().getEmail());
				return String.valueOf(flag);
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				String text="";
				if(result.endsWith("true")){
					text="恭喜你！添加好友成功";
				}
				else 
					text="添加好友失败";
				
				Toast.makeText(AcceptFriendRequestActivity.this, text, Toast.LENGTH_SHORT).show();
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
					progressDialog = CustomProgressDialog.createDialog(AcceptFriendRequestActivity.this);
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
