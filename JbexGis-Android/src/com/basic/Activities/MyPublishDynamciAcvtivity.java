package com.basic.Activities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.basic.Activities.MyPublishJbexActivity.deleteMyPublishJbexAsyncTask;
import com.basic.Activities.MyPublishJbexActivity.setDataAsyncTask;
import com.basic.ImageLoad.ImageOptions;
import com.basic.adapter.myPublishDynamicAdapter;
import com.basic.connectservice.JbexFriendService;
import com.basic.connectservice.MyJbexRequestService;
import com.basic.connectservice.dynamicInfoService;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView;
import com.basic.pulltorefresh.RefreshTime;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView.IXListViewListener;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView.OnMenuItemClickListener;
import com.basic.service.model.DynamicInfo;
import com.basic.service.model.MyJbexRequest;
import com.basic.service.model.User;
import com.basic.swipemenulistview.SwipeMenu;
import com.basic.swipemenulistview.SwipeMenuCreator;
import com.basic.swipemenulistview.SwipeMenuItem;
import com.basic.ui.CustomProgressDialog;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyPublishDynamciAcvtivity extends ZJBEXBaseActivity implements IXListViewListener{
    
	private ActionBar actionBar;
	private Intent intent;
	
	private PullToRefreshSwipeMenuListView listmenu;
	private User owneruser=new User();
	
	private CustomProgressDialog progressDialog = null;	
	private SwipeMenuCreator creator;
	
	private List <DynamicInfo> MyDynamicInfoList;
	private myPublishDynamicAdapter mpublishdynamicadapter;
	
	//图片加载框架组件
    private DisplayImageOptions options;
	private ImageLoader mImageLoader;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_publish_dynamci_acvtivity);
		
		listmenu=(PullToRefreshSwipeMenuListView) findViewById(R.id.listmypublishdynamic);
		
		intent=getIntent();
		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("user", owneruser.toString());
		}
		
		initDisplayOption();
		initActionBar();
		setDataAsyncTask load=new setDataAsyncTask();
		load.execute();
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
		rightButton.setBackgroundResource(R.drawable.loginname);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		
		txt.setText("我的动态");
		
		backtxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				MyPublishDynamciAcvtivity.this.finish();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				MyPublishDynamciAcvtivity.this.finish();
			}
		});
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptionsRange();
		mImageLoader = ImageLoader.getInstance();
	}

	private void onLoad() {
		// TODO 自动生成的方法存根
		 SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
			RefreshTime.setRefreshTime(getApplicationContext(),
					df.format(new Date()));
			listmenu.setRefreshTime(RefreshTime
					.getRefreshTime(getApplicationContext()));
			listmenu.stopRefresh();

			listmenu.stopLoadMore();

	}
	private void initListener() {
		// TODO 自动生成的方法存根
		listmenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				//frienduser=attentionUser.get(position);
				switch (index) {
				case 0:
					deletePublishDynamicAsyncTask delete=new deletePublishDynamicAsyncTask(position);
					delete.execute();
					break;
				}
			}
		});
	}
	private void initView() {
		// TODO 自动生成的方法存根
		mpublishdynamicadapter=new myPublishDynamicAdapter(MyDynamicInfoList, this, mImageLoader, options);
		listmenu.setAdapter(mpublishdynamicadapter);
		listmenu.setPullRefreshEnable(true);
		listmenu.setPullLoadEnable(true);
		listmenu.setXListViewListener(this);
		
		// step 1. create a MenuCreator
				creator=new SwipeMenuCreator() {
					
					@Override
					public void create(SwipeMenu menu) {
						
						// create "delete" item
						SwipeMenuItem deleteItem = new SwipeMenuItem(
								getApplicationContext());
						// set item background
						deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
								0x3F, 0x25)));
						// set item width
						deleteItem.setWidth(dp2px(90));
						// set a icon
						deleteItem.setIcon(R.drawable.ic_delete);
						// add to menu
						menu.addMenuItem(deleteItem);
					}
				};
				// set creator
				listmenu.setMenuCreator(creator);
	}
	
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_publish_dynamci_acvtivity, menu);
		return true;
	}

	public class setDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				
				MyDynamicInfoList=dynamicInfoService.getdynamicInfoListByUserid(String.valueOf(owneruser.getUser_id()));
				//setData();
				
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				initView();
				initListener();
				onLoad();
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
					progressDialog = CustomProgressDialog.createDialog(MyPublishDynamciAcvtivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}

	@Override
	public void onRefresh() {
		// TODO 自动生成的方法存根
		setDataAsyncTask load=new setDataAsyncTask();
		load.execute();
	}

	@Override
	public void onLoadMore() {
		// TODO 自动生成的方法存根
		
	}
	
	public class deletePublishDynamicAsyncTask extends AsyncTask<Integer, Integer, String>{
	     private int position;
	     
	     public deletePublishDynamicAsyncTask(int position) {
			super();
			this.position = position;
		}
		//后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				String flag;
				flag=dynamicInfoService.deletedynamicinfo(String.valueOf(MyDynamicInfoList.get(position).getId()));
				return flag;
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				String text="";
				if(result.endsWith("true")){
					text="恭喜你！删除成功";
					MyDynamicInfoList.remove(position);
					mpublishdynamicadapter.onDateChange(MyDynamicInfoList);
				}
				else 
					text="删除失败";
				
				Toast.makeText(MyPublishDynamciAcvtivity.this, text, Toast.LENGTH_SHORT).show();
				
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
					progressDialog = CustomProgressDialog.createDialog(MyPublishDynamciAcvtivity.this);
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
