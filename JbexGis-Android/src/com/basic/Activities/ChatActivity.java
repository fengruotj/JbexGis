package com.basic.Activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.basic.model.ChatMsgEntity;
import com.basic.model.MessageBean;
import com.basic.service.model.User;
import com.basic.connectservice.UserService;
import com.basic.facedemo.adapter.ChatMsgAdapter;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.message.net.ReceiveInfoListener;
import com.message.net.TimeUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends ZJBEXBaseActivity implements OnClickListener,ReceiveInfoListener{
	protected static final String TAG = "ChatActivity";
	private Button mBtnSend;

	private EditText mEditTextContent;

	private ListView mListView;
	
	private ChatMsgAdapter mAdapter;

	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	
	private ActionBar actionBar;
	
	private TextView maintext;
	private Intent intent;
	private int frienduser;
	private int owneruser;
	private User friendUser;
	private User ownerUser;
	private String name="";
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO 自动生成的方法存根
		super.onCreateContextMenu(menu, v, menuInfo);
		
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chat);
		intent=getIntent();
		
		con.addReceiveInfoListener(this);

		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser =data.getInt("owneruser");
			frienduser =data.getInt("frienduser");
		}
		//写入数据库
		setsetFriendNumToDb(String.valueOf(owneruser), String.valueOf(frienduser), 0);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initActionBar();
		LoadDataAsyncTask a=new LoadDataAsyncTask();
		a.execute();
	
	}
   
	private void initActionBar() {
		
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
		actionBar.setDisplayShowCustomEnabled(true);
		View view=actionBar.getCustomView();
		ImageButton nnavigation=(ImageButton) view.findViewById(R.id.btn_nnavigation);
		ImageButton back=(ImageButton) view.findViewById(R.id.btn_back);
		maintext=(TextView) view.findViewById(R.id.main_Text);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
        
	    nnavigation.setBackgroundResource(R.drawable.guide_img);
	   
		backtxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ChatActivity.this.finish();
			}
		});
		nnavigation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intentnnavigation = new Intent(ChatActivity.this,
						RouteActivity.class);
				
				Bundle data = new Bundle();
				data.putSerializable("owneruser", ownerUser);
				intentnnavigation.putExtras(data);
				
				startActivity(intentnnavigation);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ChatActivity.this.finish();
			}
		});
	}
	
	public void initMessages(){
		messages=dbUtil.queryMessages(String.valueOf(ownerUser.getUser_id()),String.valueOf( friendUser.getUser_id()));
		Log.i(TAG, "messages="+messages);
		if(messages!=null){
			Log.i(TAG, "initMessages() messages.size="+messages.size());
		}
	}
	
	public void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	}

// 聊天 
	private final static int COUNT = 8;

	public void initData() {
		initMessages();
		for(int i=0;i<messages.size();i++){
			ChatMsgEntity entity = new ChatMsgEntity();
			ChatMessage message=messages.get(i);
			entity.setDate(message.getTime());
			entity.setName(message.getFriend());
			entity.setText(message.getContent());
			int direction=message.getDirection();   //direction: to or from ?
			if (direction== Config.MESSAGE_FROM) {
				entity.setMsgType(true);
			}
			else if(direction==Config.MESSAGE_TO){
				entity.setMsgType(false);
			}
			mDataArrays.add(entity);
		}
		
		mAdapter = new ChatMsgAdapter(this, mDataArrays);
		mAdapter.setFrienduser(friendUser);
		mAdapter.setOwneruser(ownerUser);
		mListView.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& ((FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout))
						.hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setMsgType(false);
			entity.setText(contString);
			
		    sendChatMessage(Config.MESSAGE_TYPE_TXT ,contString);
		   
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH)+1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		String miao=String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins+":"+miao);

		return sbBuffer.toString();
	}

	
	public class LoadDataAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				ownerUser=UserService.getUser(String.valueOf(owneruser), "id");
			    friendUser=UserService.getUser(String.valueOf(frienduser), "id");
			    name=friendUser.getUser_nickname();
				return "success";
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				
				initView();
				initData();
				maintext.setText(name);
				super.onPostExecute(result);
			    
			}
			
			//这里是最终用户调用Excute时的接口，当任务执行之前开始调用此方法，可以在这里显示进度对话框。
			@Override
			protected void onPreExecute() {
				// TODO 自动生成的方法存根
				super.onPreExecute();	
			}
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		con.deleteReceiveInfoListener(this);
		super.onDestroy();
	}

	@Override
	public void processMessage(Message msg) {
		// TODO 自动生成的方法存根
		playMsg();
		
		Log.i(TAG, "****聊天界面中接收到新的消息****");
		if(msg.what==Config.RECEIVE_MESSAGE){
			Bundle bundle=msg.getData();
			ChatMessage message=(ChatMessage)bundle.getSerializable("chatMessage");
			ChatMsgEntity entity=new ChatMsgEntity(message.getFriend(), message.getTime(), message.getContent(), true);
			//将接受到得消息保存到数据库中
			saveMessageToDb(String.valueOf(ownerUser.getUser_id()),String.valueOf(friendUser.getUser_id()),Config.MESSAGE_FROM, message.getType(), message.getTime(), message.getContent(),Config.DateBase_SEND_MESSAGE);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mListView.getCount() - 1);
			
		}if(msg.what==Config.SEND_NOTIFICATION){
			Bundle bundle=msg.getData();
			ChatMessage chatMessage=(ChatMessage)bundle.getSerializable("chatMessage");
			saveToDb(chatMessage,Config.DateBase_GET_MESSAGE);
			
			sendNotifycation(chatMessage.getSelf(),chatMessage.getFriend());
			}
		 if (msg.what==Config.SEND_NOTIFICATION_JBEX_FRIEND){
				sendNotifycation_JBEXFriend();
			}
	}
	
	private void sendChatMessage(int type, String content) {
		String time=getDate();
		
		boolean result=true;
		if(type==Config.MESSAGE_TYPE_TXT){
		    Log.i(TAG, "sendChatMessage():MESAGE_TYPE_TXT, content="+content);
			result=con.sendText(String.valueOf(ownerUser.getUser_id()), String.valueOf(friendUser.getUser_id()), time, content);
		}else if(type==Config.MESSAGE_TYPE_AUDIO){
			result=con.sendText(String.valueOf(ownerUser.getUser_id()), String.valueOf(friendUser.getUser_id()), time, content);
		}else if(type==Config.MESSAGE_TYPE_IMG){
			result=con.sendText(String.valueOf(ownerUser.getUser_id()), String.valueOf(friendUser.getUser_id()), time, content);
		}
		
		if(result==true){
			saveMessageToDb(String.valueOf(ownerUser.getUser_id()),String.valueOf(friendUser.getUser_id()),Config.MESSAGE_TO, type, time, content,Config.DateBase_SEND_MESSAGE);
		}else{
			makeTextShort("消息发送失败!");
		}
	}

	@Override
	public boolean receive(ChatMessage message) {
		// TODO 自动生成的方法存根
		Log.i(TAG, "message.getFriend()="+message.getFriend()+", friend="+friendUser.getUser_id());
		if(message.getFriend().equals(String.valueOf(friendUser.getUser_id()))){    //这里曾犯下严重的错误：两个字符串相比较，竟然是用‘==’来判断，无语了
			return true;
		}
		return false;
	}
}