package com.basic.Activities;

import java.util.ArrayList;
import java.util.List;

import com.basic.adapter.ChooseSchoolAdapter;
import com.basic.adapter.MenuItemAdapter;
import com.basic.model.menuItemBean;
import com.message.net.ChatMessage;
import com.message.net.Config;

import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class ChooseSchoolActivity extends ZJBEXBaseActivity {
    
	private ActionBar actionBar;
	private Intent intent;
	private ListView listMenu;
	private ChooseSchoolAdapter itemAdapter;
	private List<menuItemBean>  mList=new ArrayList<menuItemBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_school);
		
        intent=getIntent();
		
		listMenu=(ListView) super.findViewById(R.id.listMenu);
		
		initActionBar();
		initView();
		initListener();
		
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
		
		txt.setText("选择学院");
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				ChooseSchoolActivity.this.finish();
			}
		});
	}

	private void initListener() {
		// TODO 自动生成的方法存根
		
		//设置Listmenu的监听并上传数据给上一个Activity
		listMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 自动生成的方法存根
				String school=mList.get(arg2).getLeftmenu_item_txt();
				//数据是使用Intent返回
				Intent intent2=new Intent();
				//把返回数据存入Intent
				intent2.putExtra("school", school);
				
				//设置返回数据
				ChooseSchoolActivity.this.setResult(333,intent2);
				ChooseSchoolActivity.this.finish();
				}
			
		});
	}

	private void initView() {
		// TODO 自动生成的方法存根
		menuItemBean item1=new menuItemBean("地球与科学学院", "  ", 0,0);
		menuItemBean item2=new menuItemBean("资源学院", "  ", 0,0);
		menuItemBean item3=new menuItemBean("材化与化学学院", "  ", 0,0);
		menuItemBean item4=new menuItemBean("环境学院", "  ", 0,0);
		menuItemBean item5=new menuItemBean("工程学院", "  ", 0,0);
		menuItemBean item6=new menuItemBean("地理物理与空间信息学院", "  ", 0,0);
		menuItemBean item7=new menuItemBean("机械与电子工程学院", "  ", 0,0);
		menuItemBean item8=new menuItemBean("经济管理学院", "  ", 0,0);
		menuItemBean item9=new menuItemBean("外国语学院", "  ", 0,0);
		menuItemBean item10=new menuItemBean("信息工程学院", "  ", 0,0);
		menuItemBean item11=new menuItemBean("数学与物理学院", "  ", 0,0);
		menuItemBean item12=new menuItemBean("珠宝学院", "  ", 0,0);
		menuItemBean item13=new menuItemBean("艺术与传媒学院", "  ", 0,0);
		menuItemBean item14=new menuItemBean("公共管理学院", "  ", 0,0);
		menuItemBean item15=new menuItemBean("马克思主义学院", "  ", 0,0);
		menuItemBean item16=new menuItemBean("计算机学院", "  ", 0,0);
		menuItemBean item17=new menuItemBean("国际教育学院", "  ", 0,0);
		menuItemBean item18=new menuItemBean("李四光学院学院", "  ", 0,0);
		menuItemBean item19=new menuItemBean("自动化学院学院", "  ", 0,0);
		
		mList.add(item1);
		mList.add(item2);
		mList.add(item3);
		mList.add(item4); 
		mList.add(item5);
		mList.add(item6);
		mList.add(item7); 
		mList.add(item8); 
		mList.add(item9);
		mList.add(item10);
		mList.add(item11);
		mList.add(item12);
		mList.add(item13);
		mList.add(item14);
		mList.add(item15);
		mList.add(item16);
		mList.add(item17);
		mList.add(item18);
		mList.add(item19);
		
		itemAdapter=new ChooseSchoolAdapter(this, mList,intent.getStringExtra("school"));
		listMenu.setAdapter(itemAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_school, menu);
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
