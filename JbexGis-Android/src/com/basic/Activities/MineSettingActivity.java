package com.basic.Activities;

import java.util.List;

import org.other.actionsheet.Method.Action1;

import com.basic.service.model.User;
import com.basic.ui.ActionSheet;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.message.net.Friend;

import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineSettingActivity extends ZJBEXBaseActivity {
	private ActionBar actionBar;
	private Intent intent;
	private User owneruser = new User(); // User
	private RelativeLayout group_layout_about;
	private RelativeLayout group_layout_chat;
	private Button  exit__btn;
	private ActionSheet actionSheet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_setting);
		
		intent = getIntent();

		if ((intent.getExtras()) != null) {
			Bundle data = intent.getExtras();
			owneruser = (User) data.getSerializable("owneruser");
			Log.d("user", owneruser.toString());
		}
		
		group_layout_about=(RelativeLayout) findViewById(R.id.group_layout_about);
		group_layout_chat=(RelativeLayout) findViewById(R.id.group_layout_chat);
		exit__btn=(Button) findViewById(R.id.exit__btn);
		initActionBar();
		initListener();
		
	}

	private void initListener() {
		// TODO 自动生成的方法存根
		group_layout_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		group_layout_chat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				List<Friend> friendList=dbUtil.queryFriends(String.valueOf(owneruser.getUser_id()));
				for(int i=0;i<friendList.size();i++){
					Friend friend=friendList.get(i);
					String friendid=friend.getFriendID();
					friendid=friendid.substring(friendid.indexOf("'")+1, friendid.lastIndexOf("'"));
					dbUtil.deleteAllMessages(String.valueOf(owneruser.getUser_id()), friendid);
				}
				Toast.makeText(MineSettingActivity.this, "恭喜你删除成功",Toast.LENGTH_SHORT).show();
			}
		});
		
		exit__btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				actionSheet = new ActionSheet(MineSettingActivity.this);
				actionSheet.show("确定要退出么？", new String[] { "退出" },
						new Action1<Integer>() {
							@Override
							public void invoke(Integer index) {
								actionSheet.hide();
								if (index == 0) {
									MineSettingActivity.this.setResult(222);
									MineSettingActivity.this.finish();
								}
							}
						});
			}
		});
	}

	private void initActionBar() {
		// TODO 自动生成的方法存根
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
		actionBar.setDisplayShowCustomEnabled(true);
		View view=actionBar.getCustomView();
		ImageButton nnavigation=(ImageButton) view.findViewById(R.id.btn_nnavigation);
		ImageButton back=(ImageButton) view.findViewById(R.id.btn_back);
		TextView maintext=(TextView) view.findViewById(R.id.main_Text);
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
        
	    nnavigation.setBackgroundResource(0);
	    maintext.setText("设置");
	    
		backtxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				MineSettingActivity.this.finish();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				MineSettingActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_setting, menu);
		return true;
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
