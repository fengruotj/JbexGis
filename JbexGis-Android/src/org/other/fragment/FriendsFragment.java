package org.other.fragment;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.other.constant.Constant;
import org.other.fragment.adapter.FriendsAdapter;
import org.other.fragment.adapter.MessageAdapter;

import com.basic.Activities.ChatActivity;
import com.basic.Activities.FriendRequestActivity;
import com.basic.Activities.FriendsInfoActivity;
import com.basic.Activities.MainActivity;
import com.basic.Activities.MyPublishActivity;
import com.basic.Activities.MyPublishJbexActivity;
import com.basic.Activities.PersonInfoActivity;
import com.basic.Activities.R;
import com.basic.ImageLoad.ImageOptions;
import com.basic.connectservice.GetListUser;
import com.basic.connectservice.SettingUserInfo;
import com.basic.model.FriendsBean;
import com.basic.model.FriendsGroupBean;
import com.basic.model.MessageBean;
import com.basic.service.model.User;
import com.basic.ui.CustomExpandableListView;
import com.basic.ui.CustomProgressDialog;
import com.basic.util.ListViewHeightBaseOnChildren;
import com.lee.pullrefresh.ui.PullToRefreshBase;
import com.lee.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.lee.pullrefresh.ui.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;

public class FriendsFragment extends BaseFragment implements OnRefreshListener<ScrollView>{
	
	private static final String TAG = "FriendsFragment";
	private CustomExpandableListView m_exlist=null;
	private ScrollView mScrollView;
	private RelativeLayout  friendrequest;
	private RelativeLayout new_Jbex_Request;
	private PullToRefreshScrollView mPullScrollView=null;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    
	private MainActivity mMainActivity ;
	private FriendsAdapter mFriendsAdapter=null;
	private List<FriendsBean> mListFriendsBean = new ArrayList<FriendsBean>();
	private List<FriendsGroupBean> mListFriendsGroupBean  = new ArrayList<FriendsGroupBean>();
	
    private User user;
	//数据流
    private List<User> classmatesList=new ArrayList<User>();
	private List<User> friendsList=new ArrayList<User>();
	private List<User> familyList=new ArrayList<User>();
	
	private CustomProgressDialog progressDialog = null;
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View FriendsLayout = inflater.inflate(R.layout.friends_layout, container,
				false);
		
		mPullScrollView = new PullToRefreshScrollView(mMainActivity);
		mPullScrollView.setOnRefreshListener(this);
		
		mScrollView = mPullScrollView.getRefreshableView();
		mScrollView.addView(FriendsLayout,0);
		setLastUpdateTime();
		
		m_exlist=(CustomExpandableListView) FriendsLayout.findViewById(R.id.exlistView);
		friendrequest=(RelativeLayout) FriendsLayout.findViewById(R.id.new_lay);
		new_Jbex_Request=(RelativeLayout) FriendsLayout.findViewById(R.id.new_Jbex_Request);
		mFragmentManager = getActivity().getFragmentManager();

		Log.d(TAG, "onCreateView---->");
		
		if(mFriendsAdapter!=null){
		mFriendsAdapter = new FriendsAdapter( mListFriendsGroupBean,mMainActivity,mImageLoader,options);
		m_exlist.setAdapter(mFriendsAdapter);
		ListViewHeightBaseOnChildren.setListViewHeight(m_exlist);
		initListener();
		}
		
		return mPullScrollView;
	}
	

	private void setLastUpdateTime() {
		// TODO 自动生成的方法存根
		  String text = formatDateTime(System.currentTimeMillis());
	        mPullScrollView.setLastUpdatedLabel(text);
	}

    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        } 
        return mDateFormat.format(new Date(time));
    }

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.e(TAG, "onAttach-----");
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate------");
		mMainActivity = (MainActivity) getActivity();	
		user=mMainActivity.getUser();
		
		initDisplayOption();
		
		getFriendsListAsyncTask a=new getFriendsListAsyncTask();
		a.execute();
	}
	
	//初始化化加载图片框架
	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
   	 
   	     File cacheDir = StorageUtils.getCacheDirectory(mMainActivity);
		Log.e(TAG, "cacheDir path="+cacheDir.getAbsolutePath());
	}


	private void setData() {
		// TODO 自动生成的方法存根
		mListFriendsGroupBean=new  ArrayList<FriendsGroupBean>();
		FriendsBean classmates[]=new FriendsBean[classmatesList.size()];
		FriendsBean family[]=new FriendsBean[familyList.size()];
		FriendsBean friends[]=new FriendsBean[friendsList.size()];
		
		List<FriendsBean> list1=new ArrayList<FriendsBean>();   //家人
		List<FriendsBean> list2=new ArrayList<FriendsBean>();   //同学
		List<FriendsBean> list3=new ArrayList<FriendsBean>();   //好友
		
		int familyonline=0;  int classmatesonline=0; int friendsonline=0;
		
		for(int i=0;i<classmatesList.size();i++){
			boolean flag;
			if(classmatesList.get(i).getState()==1){
			flag=true;
			classmatesonline++;
			}
			else flag=false;
			
			classmates[i]=new FriendsBean(classmatesList.get(i).getPicture(),flag , classmatesList.get(i).getUser_nickname(), classmatesList.get(i).getPerson_signature());
			list2.add(classmates[i]);
		}
		
		for(int i=0;i<familyList.size();i++){
			boolean flag;
			if(familyList.get(i).getState()==1){
			flag=true;
			familyonline++;
			}
			else flag=false;
			family[i]=new FriendsBean(familyList.get(i).getPicture(),flag , familyList.get(i).getUser_nickname(), familyList.get(i).getPerson_signature());
			list1.add(family[i]);
		}
		
		for(int i=0;i<friendsList.size();i++){
			boolean flag;
			if(friendsList.get(i).getState()==1){
			flag=true;
			friendsonline++;
			}
			else flag=false;
			friends[i]=new FriendsBean(friendsList.get(i).getPicture(),flag , friendsList.get(i).getUser_nickname(), friendsList.get(i).getPerson_signature());
			list3.add(friends[i]);
		}
		
   		mListFriendsGroupBean.add(new FriendsGroupBean("结伴的好友", familyonline+"/"+familyList.size(), list1));
   		mListFriendsGroupBean.add(new FriendsGroupBean("同学", classmatesonline+"/"+classmatesList.size(), list2));
   		mListFriendsGroupBean.add(new FriendsGroupBean("好友",friendsonline+"/"+friendsList.size(), list3));
	}

	 private void initListener() {
			// TODO 自动生成的方法存根
		 new_Jbex_Request.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent MyPublishJbex = new Intent(mMainActivity,
						MyPublishJbexActivity.class);
				Bundle data = new Bundle();
				data.putSerializable("owneruser", user);
				MyPublishJbex.putExtras(data);
				startActivity(MyPublishJbex);
			}
		});
		 
		 friendrequest.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View arg0) {
				 // TODO 自动生成的方法存根
				 Intent FriendRequestIntent=new Intent(mMainActivity, FriendRequestActivity.class);
				 Bundle data=new Bundle();
				 data.putSerializable("owneruser",user);
				 FriendRequestIntent.putExtras(data);
				 mMainActivity.startActivityForResult(FriendRequestIntent,1);
			 }
		 });
		 
		 m_exlist.setOnChildClickListener(new OnChildClickListener() {

			 @Override
			 public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
					 int arg3, long arg4) {

				 Intent nextintent=new Intent(mMainActivity, FriendsInfoActivity.class);
				 Bundle data=new Bundle();
				 if(arg2==0){
					 data.putSerializable("owneruser",user);
					 data.putString("GroupName", "结伴的好友");
					 data.putSerializable("frienduser",familyList.get(arg3));
				 }
				 else if(arg2==1){
					 data.putSerializable("owneruser",user);
					 data.putString("GroupName", "同学");
					 data.putSerializable("frienduser",classmatesList.get(arg3));
				 }
				 else if(arg2==2){
					 data.putSerializable("owneruser",user);
					 data.putString("GroupName", "好友");
					 data.putSerializable("frienduser",friendsList.get(arg3));
				 }
				 data.putBoolean("flag", true);
				 nextintent.putExtras(data);
				 mMainActivity.startActivityForResult(nextintent, 1);

				 return false;
			 }
		 });
	 }
	 
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.e(TAG, "onActivityCreated-------");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//getFriendsListAsyncTask a=new getFriendsListAsyncTask();
		//a.execute();
		Log.e(TAG, "onStart----->");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "onresume---->");
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_Friends;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		Log.e(TAG, "onpause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e(TAG, "onStop");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.e(TAG, "ondestoryView");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//mImageLoader.clearMemoryCache();
		//mImageLoader.clearDiskCache();
		super.onDestroy();
		Log.e(TAG, "ondestory");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d(TAG, "onDetach------");

	}
	
    public void friendrequest(View soucre){
    	Intent FriendRequestIntent=new Intent(mMainActivity, FriendRequestActivity.class);
    	this.startActivity(FriendRequestIntent);
    }
    
	public class getFriendsListAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
		
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override	
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				
				classmatesList = GetListUser.GetGroupList(user.getEmail(), "classmates");
				friendsList=GetListUser.GetGroupList(user.getEmail(), "friends");
				familyList=GetListUser.GetGroupList(user.getEmail(), "family");
			    setData();
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				if(mPullScrollView!=null){
					mPullScrollView.onPullDownRefreshComplete();
		            setLastUpdateTime();
				}
				mFriendsAdapter = new FriendsAdapter( mListFriendsGroupBean,mMainActivity,mImageLoader,options);
				m_exlist.setAdapter(mFriendsAdapter);
				ListViewHeightBaseOnChildren.setListViewHeight(m_exlist);
				initListener();
				
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
					progressDialog = CustomProgressDialog.createDialog(mMainActivity);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO 自动生成的方法存根
		getFriendsListAsyncTask a=new getFriendsListAsyncTask();
		a.execute();
	}


	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO 自动生成的方法存根
		
	}



}
