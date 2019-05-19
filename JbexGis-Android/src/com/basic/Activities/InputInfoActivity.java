package com.basic.Activities;

import com.message.net.ChatMessage;
import com.message.net.Config;

import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class InputInfoActivity extends ZJBEXBaseActivity {
   
	private ActionBar actionBar;
	private Intent intent;
	private Button save_btn;
	private EditText editText;
	private String type;
	private String text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_info);
		
		intent=getIntent();
		save_btn=(Button) findViewById(R.id.save_btn);
		editText=(EditText) findViewById(R.id.editText);
		type=intent.getStringExtra("type");
		text=intent.getStringExtra("text");
		initView();
		initActionBar();
		initListener();
	}

	private void initListener() {
		
		// TODO 自动生成的方法存根
		save_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				String inputInfo=editText.getText().toString();
				Intent intent2=new Intent();
				intent2.putExtra("info", inputInfo);
				
				//设置返回数据
				if(type.equals("num"))
					InputInfoActivity.this.setResult(222,intent2);
				else if(type.equals("text"))
					InputInfoActivity.this.setResult(111,intent2);
				
				InputInfoActivity.this.finish();
			}
		});
		
	}

	private void initView() {
		// TODO 自动生成的方法存根
		editText.setText(text);
		if(type.equals("num"))
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		else if(type.equals("text"))
			editText.setInputType(InputType.TYPE_CLASS_TEXT);
	}

	private void initActionBar() {
		// TODO 自动生成的方法存根
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.session_top);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		View view = actionBar.getCustomView();
		ImageButton save_btn = (ImageButton) view
				.findViewById(R.id.btn_nnavigation);
		ImageButton back = (ImageButton) view.findViewById(R.id.btn_back);
		TextView txt = (TextView) view.findViewById(R.id.main_Text);
		TextView backtxt = (TextView) view.findViewById(R.id.backtxt);
        
		txt.setText("个人信息");
		
		backtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				InputInfoActivity.this.finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				InputInfoActivity.this.finish();
			}
		});
		
		save_btn.setBackgroundResource(0);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input_info, menu);
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
