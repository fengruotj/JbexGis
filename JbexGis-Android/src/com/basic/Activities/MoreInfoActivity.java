package com.basic.Activities;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.PersonInfoActivity.SchooltemListener;
import com.basic.Activities.PersonInfoActivity.SetFriendsAsyncTask;
import com.basic.adapter.MenuItemAdapter;
import com.basic.connectservice.AttentionService;
import com.basic.connectservice.FriendService;
import com.basic.connectservice.SettingUserInfo;
import com.basic.model.menuItemBean;
import com.basic.service.model.User;
import com.basic.ui.ActionSheet;
import com.basic.ui.CustomProgressDialog;
import com.basic.ui.OptionsPopupWindow;
import com.basic.ui.OptionsPopupWindow.OnOptionsSelectListener;
import com.basic.ui.SlipButton;
import com.basic.ui.SlipButton.OnChangedListener;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MoreInfoActivity extends ZJBEXBaseActivity {
    
	private ActionBar actionBar;
	private Intent intent;
	private ListView listMenu;
	private MenuItemAdapter itemAdapter;
	private List<menuItemBean> mList = new ArrayList<menuItemBean>();
	private ActionSheet actionSheet;
	private SlipButton attentionSlipButton=null;
	private OptionsPopupWindow pwOption;
	private ArrayList<String> Option = new ArrayList<String>();  //Option的数据源
	
	private CustomProgressDialog progressDialog = null;
	
	private User owneruser = new User(); // User
	private User frienduser=new User();
	private String GroupName="";
	private List<User> attentionUser=new ArrayList<User>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info);
		
		intent = getIntent();

		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			frienduser=(User) data.getSerializable("frienduser");
		    GroupName=data.getString("GroupName");
			Log.d("user", owneruser.toString());
		}
		
		attentionSlipButton=(SlipButton) super.findViewById(R.id.attentionSlipButton);
		listMenu = (ListView) super.findViewById(R.id.listmenu);
		pwOption = new OptionsPopupWindow(this);
		LoadAttentionAsyncTask load=new LoadAttentionAsyncTask();
		load.execute();
		
		initListener();
		initActionBar();
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

		txt.setText("更多");

		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				MoreInfoActivity.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				MoreInfoActivity.this.finish();
			}
		});

	}

	private void initListener() {
		// TODO 自动生成的方法存根
		attentionSlipButton.SetOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(boolean CheckState) {
				// TODO 自动生成的方法存根
				setAttentionAsyncTask set=new setAttentionAsyncTask(CheckState);
				set.execute();
			}
		});;
	}

	private void initView() {
		// TODO 自动生成的方法存根
		menuItemBean item1 = new menuItemBean("备注", frienduser.getUser_nickname(), 0, 0);
		menuItemBean item2 = new menuItemBean("分组", GroupName, 0 ,R.drawable.btn_right);
		
		mList.add(item1);
		mList.add(item2);
		
		itemAdapter = new MenuItemAdapter(this, mList);
		listMenu.setAdapter(itemAdapter);
		listMenu.setOnItemClickListener(new ItemListener());
	}
   
	public void deleteFriend(View source){
		deleteFriendAsyncTask delete=new deleteFriendAsyncTask();
		delete.execute();
	}
	
	class ItemListener implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View currentview, int arg2,
				long arg3) {
			// TODO 自动生成的方法存根
			switch (arg2) {
			case 0://点击了备注
				//Toast.makeText(MoreInfoActivity.this, "0", Toast.LENGTH_SHORT).show();
				break;
			case 1://点击了分组
				Option=new ArrayList<String>();
				if(GroupName.endsWith("结伴的好友")){
					Option.add("同学");
					Option.add("好友");
				}else if(GroupName.endsWith("同学"))
				{
					Option.add("结伴的好友");
					Option.add("好友");
				}else if(GroupName.endsWith("好友")){
					Option.add("结伴的好友");
					Option.add("同学");
				}
				
				pwOption.setPicker(Option);
				pwOption.setLabels("选择分组");
				pwOption.setSelectOptions(0, 0, 0);
				
				pwOption.setOnoptionsSelectListener(new OnOptionsSelectListener() {
					
					@Override
					public void onOptionsSelect(int options1, int option2, int options3) {
						// TODO 自动生成的方法存根
						setFriendsGroupAsyncTask a=new setFriendsGroupAsyncTask(Option.get(options1));
						a.execute();
						GroupName=Option.get(options1);
						Intent intent2=new Intent();
						intent2.putExtra("GroupName", GroupName);
						MoreInfoActivity.this.setResult(444,intent2);
					}
				});
				pwOption.showAtLocation(currentview, Gravity.BOTTOM, 0, 0);
				break;
			case 2://点击了特别关心
				Toast.makeText(MoreInfoActivity.this, "2", Toast.LENGTH_SHORT).show();
				break;
			}
		}	
	}
	
	
	public class LoadAttentionAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				
				attentionUser=AttentionService.GetAttentionList(owneruser.getEmail());
				Log.d("attentionUser", attentionUser.toString());
				Log.d("frienduser", frienduser.toString());
				boolean flag=false;
				for(User user :attentionUser){
					if(user.getEmail().endsWith(frienduser.getEmail())){
						flag=true;
					}
				}
				return String.valueOf(flag);
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				if(result.equals("true")){
					attentionSlipButton.setCheck(true);
				}
				else {
					attentionSlipButton.setCheck(false);
				}
				initView();
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
					progressDialog = CustomProgressDialog.createDialog(MoreInfoActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	public class setFriendsGroupAsyncTask extends AsyncTask<Integer, Integer, String>{
	      private String GroupName;
	      
	     public setFriendsGroupAsyncTask(String groupName) {
			super();
			GroupName = groupName;
		}
		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根	
				FriendService.setFriendsGroup(owneruser.getEmail(),frienduser.getEmail(), GroupName);
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
			      menuItemBean item = new menuItemBean("分组", GroupName, 0 ,R.drawable.btn_right);
				  mList.set(1, item);
				  itemAdapter.onDateChange(mList);
				  
				  Toast.makeText(MoreInfoActivity.this, "修改分组完成", Toast.LENGTH_SHORT).show();
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
					progressDialog = CustomProgressDialog.createDialog(MoreInfoActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
	
	public class setAttentionAsyncTask extends AsyncTask<Integer, Integer, String>{
	      private boolean CheckState;
	      
      	public setAttentionAsyncTask(boolean checkState) {
			super();
			CheckState = checkState;
		}
		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根	
				boolean flag;
				if(CheckState){
					flag=AttentionService.addAttentionUser(owneruser.getEmail(), frienduser.getEmail());
					Log.d("attentionflag", String.valueOf(flag));
					return "true";
				}else{
					flag=AttentionService.deleteAttentionUser(owneruser.getEmail(), frienduser.getEmail());
					Log.d("attentionflag", String.valueOf(flag));
					return "false";
				}
				
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				if(result.equals("true"))
					Toast.makeText(MoreInfoActivity.this, "特别关注完成", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(MoreInfoActivity.this, "取消特别关注完成", Toast.LENGTH_SHORT).show();
				
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
					progressDialog = CustomProgressDialog.createDialog(MoreInfoActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}

	public class deleteFriendAsyncTask extends AsyncTask<Integer, Integer, String>{

		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根	
				return String.valueOf(FriendService.deleteFriend(owneruser.getEmail(), frienduser.getEmail()));
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				
				if(result.equals("true"))
					Toast.makeText(MoreInfoActivity.this, "删除好友成功", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(MoreInfoActivity.this, "删除好友失败", Toast.LENGTH_SHORT).show();
				
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
					progressDialog = CustomProgressDialog.createDialog(MoreInfoActivity.this);
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
