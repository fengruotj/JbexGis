package org.other.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.other.constant.Constant;
import org.other.fragment.adapter.MessageAdapter;
import org.other.ui.DraggableFlagView;

import com.basic.Activities.ChatActivity;
import com.basic.Activities.FriendRequestActivity;
import com.basic.Activities.MainActivity;
import com.basic.Activities.R;
import com.basic.Activities.ZJBEXBaseActivity;
import com.basic.ImageLoad.ImageOptions;
import com.basic.connectservice.FriendService;
import com.basic.connectservice.GetListUser;
import com.basic.connectservice.UserService;
import com.basic.model.MessageBean;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView;
import com.basic.pulltorefresh.RefreshTime;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView.IXListViewListener;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView.OnMenuItemClickListener;
import com.basic.pulltorefresh.PullToRefreshSwipeMenuListView.OnSwipeListener;
import com.basic.service.model.FriendRequest;
import com.basic.service.model.User;
import com.basic.swipemenulistview.SwipeMenu;
import com.basic.swipemenulistview.SwipeMenuCreator;
import com.basic.swipemenulistview.SwipeMenuItem;
import com.basic.ui.CustomProgressDialog;
import com.message.net.DatabaseUtil;
import com.message.net.Friend;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
@SuppressLint("SimpleDateFormat")
public class MessageFragment extends BaseFragment implements IXListViewListener{

	private static final String TAG = "MessageFragment";
	private MainActivity mMainActivity ;
	
	private PullToRefreshSwipeMenuListView mListView;
	private MessageAdapter mMsgAdapter=null;
	private List<MessageBean> mMsgBean = new ArrayList<MessageBean>();
	private List<Friend> listmessage =new ArrayList<Friend>();
	private View messageLayout ;
	private SwipeMenuCreator creator;
	
	private User owneruser;
	
    private CustomProgressDialog progressDialog = null;
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		mFragmentManager = getActivity().getFragmentManager();
		mMainActivity = (MainActivity) getActivity();	
		owneruser=mMainActivity.getUser();
		Log.e(TAG, "onCreate------");
		
		initDisplayOption();
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView---->");
		
		messageLayout = inflater.inflate(R.layout.message_layout, container,
				false);
		mListView=(PullToRefreshSwipeMenuListView) messageLayout.findViewById(R.id.listview_message);
		//LoadDataAsyncTask a=new LoadDataAsyncTask();
		//a.execute();
		
        if(mMsgAdapter!=null){
        	initView();
			initListener();
        }
        
		return messageLayout;
	}


	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		mImageLoader = ImageLoader.getInstance();
	}


	private void initListener() {
						
			  mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View currentView, int arg2,
						long arg3) {
					// TODO 自动生成的方法存根
					Friend friend=listmessage.get(arg2-1);
					String friendid=friend.getFriendID();
					Intent intent=new Intent(mMainActivity, ChatActivity.class);
					
					friendid=friendid.substring(friendid.indexOf("'")+1, friendid.lastIndexOf("'"));
					Bundle data=new Bundle();
					data.putInt("frienduser", Integer.valueOf(friendid));
					data.putInt("owneruser", owneruser.getUser_id());
					intent.putExtras(data);
					
					mMainActivity.startActivity(intent);
				}
			});
	}
	
	private void initView() {
	
		mMsgAdapter=new MessageAdapter(mMsgBean, mMainActivity,mImageLoader,options);
		mListView.setAdapter(mMsgAdapter);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		
	}
   
	private void onLoad() {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		RefreshTime.setRefreshTime(mMainActivity.getApplicationContext(),
				df.format(new Date()));
		mListView.setRefreshTime(RefreshTime
				.getRefreshTime(mMainActivity.getApplicationContext()));
		mListView.stopRefresh();

		mListView.stopLoadMore();

	}
	
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.e(TAG, "onAttach-----");

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
		LoadDataAsyncTask a=new LoadDataAsyncTask();
		a.execute();
		Log.e(TAG, "onStart----->");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "onresume---->");
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_MESSAGE;
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
		super.onDestroy();
		Log.e(TAG, "ondestory");
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.d(TAG, "onDetach------");

	}
   
	public class LoadDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				listmessage=ZJBEXBaseActivity.getFriendMessageList((String.valueOf(owneruser.getUser_id())));
				mMsgBean=new ArrayList<MessageBean>();
				for(int i=0;i<listmessage.size();i++){
					Friend friend=listmessage.get(i);
					MessageBean message=new MessageBean();
					message.setMessageTime(friend.getTime());
					message.setMessageContent(friend.getContent());
					String friendid=friend.getFriendID();
					friendid=friendid.substring(friendid.indexOf("'")+1, friendid.lastIndexOf("'"));
					User frienduser=UserService.getUser(friendid, "id");
					message.setPhotoDrawableId(frienduser.getPicture());
					message.setMessageName(frienduser.getUser_nickname());
					message.setNum(friend.getNum());
					mMsgBean.add(message);
				}
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

			}
			
			//这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			@Override
			protected void onPreExecute() {
				// TODO 自动生成的方法存根
				super.onPreExecute();	
			}
	}
   
	@Override
	public void onRefresh() {
		// TODO 自动生成的方法存根
		LoadDataAsyncTask a=new LoadDataAsyncTask();
		a.execute();
	}


	@Override
	public void onLoadMore() {
		// TODO 自动生成的方法存根
		
	}
}
