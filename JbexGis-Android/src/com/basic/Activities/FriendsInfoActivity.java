package com.basic.Activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.MenuItemAdapter;
import com.basic.model.menuItemBean;
import com.basic.service.model.User;
import com.message.net.ChatMessage;
import com.message.net.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class FriendsInfoActivity extends ZJBEXBaseActivity {
   
	private ActionBar actionBar;
	private Intent intent;
	private ListView listMenu;
	private MenuItemAdapter itemAdapter;
	private List<menuItemBean>  mList=new ArrayList<menuItemBean>();
	private ImageView image;
	private TextView friendsName;
	private ImageView sex;
	private Button sendMessage;
	private User frienduser;
	private User owneruser;
	private String GroupName;
	
	private boolean flag;
	
	//图片加载框架组件
	private DisplayImageOptions options;
	private ImageLoader mImageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends_info);

		intent=getIntent();
		if((intent.getExtras())!=null){
			Bundle data = intent.getExtras();
			owneruser=(User) data.getSerializable("owneruser");
			frienduser = (User) data.getSerializable("frienduser");
			GroupName=data.getString("GroupName");
			flag=data.getBoolean("flag");
			Log.d("frienduser", frienduser.toString());
			Log.d("owneruser", owneruser.toString());
		}
		
		listMenu=(ListView) findViewById(R.id.listMenu);
		image=(ImageView) findViewById(R.id.image);
		friendsName=(TextView) findViewById(R.id.friendsName);
		sex=(ImageView) findViewById(R.id.sex);
		sendMessage=(Button) findViewById(R.id.sendMessage);
		
		initDisplayOption();
		initActionBar();
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
		sendMessage.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				FriendsInfoActivity.this.setSelf(owneruser);
				FriendsInfoActivity.this.setFriend(frienduser);
				
				Intent Chatintent=new Intent(FriendsInfoActivity.this, ChatActivity.class);
				Bundle data=new Bundle();
				data.putInt("frienduser", frienduser.getUser_id());
				data.putInt("owneruser", owneruser.getUser_id());
				Chatintent.putExtras(data);
				FriendsInfoActivity.this.startActivity(Chatintent);
				
				//Toast.makeText(FriendsInfoActivity.this, "点击了发送消息按钮", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initView() {
		// TODO 自动生成的方法存根
		mImageLoader.displayImage(ImageStringUtil.getImageURL(frienduser.getPicture()), image, options, animateFirstListener);

		friendsName.setText(frienduser.getUser_nickname());
		
		if(frienduser.getSex()==0)
		sex.setImageResource(0);
		else if(frienduser.getSex()==1)
		sex.setImageResource(R.drawable.man);
		else if(frienduser.getSex()==2)
		sex.setImageResource(R.drawable.woman);
		
		menuItemBean item1=new menuItemBean("邮箱", frienduser.getEmail(), 0, 0);
		menuItemBean item2=new menuItemBean("个性签名", frienduser.getPerson_signature(), 0, 0);
		menuItemBean item3=new menuItemBean("手机号码", frienduser.getTelephone(), 0, 0);
		menuItemBean item4=new menuItemBean("学校", frienduser.getSchool(), 0, 0);
		menuItemBean item5=new menuItemBean("学院", frienduser.getAcademy(), 0, 0);
		SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd");     
		menuItemBean item6=new menuItemBean("生日", myFmt.format(frienduser.getBirthday()), 0, 0);
		
		mList.add(item1);
		mList.add(item2);mList.add(item3);mList.add(item4); mList.add(item5);
		mList.add(item6);
		
		itemAdapter=new MenuItemAdapter(this, mList);
		listMenu.setAdapter(itemAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 444:
			GroupName=data.getStringExtra("GroupName");
			break;

		default:
			break;
		}
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
		TextView backtxt=(TextView) view.findViewById(R.id.backtxt);
		txt.setText("好友信息");
		
		if(flag){
		rightButton.setBackgroundResource(R.drawable.actionbar_add_icon);
		rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent moreInfo=new Intent(FriendsInfoActivity.this, MoreInfoActivity.class);
				Bundle data=new Bundle();
				data.putSerializable("owneruser", owneruser);
				data.putSerializable("frienduser", frienduser);
				data.putString("GroupName", GroupName);
				moreInfo.putExtras(data);
				FriendsInfoActivity.this.startActivityForResult(moreInfo, 1);
			}
		});
		}
		else {
			rightButton.setBackgroundResource(0);
		}
		
		backtxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				FriendsInfoActivity.this.finish();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				FriendsInfoActivity.this.finish();
			}
		});
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
