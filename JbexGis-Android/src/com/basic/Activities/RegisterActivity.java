package com.basic.Activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.basic.connectservice.AttentionService;
import com.basic.connectservice.ConnectService_Register;
import com.basic.connectservice.UserService;
import com.basic.service.model.User;
import com.basic.ui.CustomProgressDialog;
import com.message.net.ChatMessage;
import com.message.net.Config;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends ZJBEXBaseActivity {

	private EditText editText_user_nickname = null; // 用户昵称
	private EditText editText_password = null; // 密码
	private EditText editText_repassword = null; // 重复输入密码
	private EditText editText_email = null; // 邮箱
	private Button button_register = null; // 注册按钮
	private ImageView imageView_goback = null; // 返回
	private boolean emailCursor = true;// 判读邮箱输入框是失去光标还是获得光标
	private boolean repasswordCursor = true;// 判读重复密码输入框是失去光标还是获得光标

	private String nickname;
	private String email;
	private String password;
	private String repassword;

	private CustomProgressDialog progressDialog = null;
	
	User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);

		init();
		initListener();

		initView();
	}

	private void initView() {
		button_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (matchRegisterMsg()) {
					// 如果校验成功
					RegisterAsyncTask a=new RegisterAsyncTask();
					a.execute();
				}
			}
		});
	}

	protected boolean matchRegisterMsg() {
		nickname = editText_user_nickname.getText().toString().trim();
		email = editText_email.getText().toString().trim();
		password = editText_password.getText().toString().trim();
		repassword = editText_repassword.getText().toString().trim();
		if (nickname.equals("")) {
			Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (email.equals("")) {
			Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (password.equals("")) {
			Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (repassword.equals("")) {
			Toast.makeText(RegisterActivity.this, "重复密码不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!checkPassword(password, repassword)) {
			Toast.makeText(RegisterActivity.this, "两次密码不相等", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	/**
	 * 页面元素初始化
	 */
	private void init() {
		this.editText_user_nickname = (EditText) findViewById(R.id.editText_register_user_nickname);
		this.editText_email = (EditText) findViewById(R.id.editText_register_email);
		this.editText_password = (EditText) findViewById(R.id.editText_register_password);
		this.editText_repassword = (EditText) findViewById(R.id.editText_register_repassword);
		this.button_register = (Button) findViewById(R.id.button_register);
		this.imageView_goback = (ImageView) findViewById(R.id.imageView_register_goback);
	}

	/**
	 * 监听事件的初始化 邮箱输入框光标失去监听，密码重新输入框的光标失去监听，注册按钮点击监听，返回按钮点击监听
	 */
	private void initListener() {
		this.editText_email.setOnFocusChangeListener(new CheckEmailListener());
		this.editText_repassword
				.setOnFocusChangeListener(new RePasswordListener());
		// this.button_register.setOnClickListener(new RegisterListener());
		this.imageView_goback.setOnClickListener(new ExitListener());
	}

	/**
	 * CheckUsernameListener 当输入完邮箱后，输入框失去光标后,提示该邮箱的格式是否正确
	 */
	private class CheckEmailListener implements OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			String myEmail = editText_email.getText().toString();
			if (emailCursor = !emailCursor) {
				if (!isEmail(myEmail)) {
					Toast.makeText(RegisterActivity.this, "邮箱格式不正确",
							Toast.LENGTH_SHORT).show();
					// editText_email.requestFocus();
				}
			}
		}
	}

	public boolean isEmail(String email) {
		// String str =
		// "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		// System.out.println(m.matches());
		return m.matches();
	}

	/**
	 * RePasswordListener 重复输入密码失去光标的监听类
	 */
	private class RePasswordListener implements OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (repasswordCursor = !repasswordCursor) {
				if (!checkPassword(editText_password.getText().toString(),
						editText_repassword.getText().toString())) {
					// editText_password.setText("");
					editText_repassword.setText("");
					// editText_repassword.requestFocus();
					Toast.makeText(RegisterActivity.this, "两次密码不一样，请重新输入",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	/**
	 * 比较两次输入的密码是否一致 editText_repassword输入完成后，光标改变监听，和editText_password进行比较，
	 * 如果不一致，会有提示，并且两次密码密码清空
	 * 
	 * @param psw1
	 *            密码框中输入的密码
	 * @param psw2
	 *            重复密码框中输入的密码
	 * @return 两次密码一致返回true，否则返回false
	 */
	private boolean checkPassword(String psw1, String psw2) {
		if (psw1.equals(psw2))
			return true;
		else
			return false;
	}

	/**
	 * ExitListener 设置“返回按钮的点击监听，点击后回到登陆界面
	 */
	private class ExitListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			RegisterActivity.this.finish();
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
   
	public class RegisterAsyncTask extends AsyncTask<Integer, Integer, String>{
	     
	     //后台执行，比较耗时的操作都可以放在这里。注意这里不能直接操作UI。
		//此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间。在执行过程中可以调用publicProgress(Progress…)来更新任务的进度。
			@Override
			protected String doInBackground(Integer... arg0) {
				// TODO 自动生成的方法存根
				boolean flag;
				flag=UserService.registerUser(editText_user_nickname.getText().toString(), editText_email.getText().toString(), editText_password.getText().toString());
				
				return String.valueOf(flag);
			}
	     //相当于Handler 处理UI的方式，在这里面可以使用在doInBackground
		//得到的结果处理操作UI。 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			@Override
			protected void onPostExecute(String result) {
				// TODO 自动生成的方法存根
				if(result.equals("true")){
					
					Toast.makeText(RegisterActivity.this, "注册成功！！",
							Toast.LENGTH_SHORT).show();
					// 结束该Activity
					RegisterActivity.this.finish();
				}else if(result.equals("false")){
					Toast.makeText(RegisterActivity.this, "邮箱或用户名已被注册，请修改邮箱!",
							Toast.LENGTH_SHORT).show();
				}
				
				super.onPostExecute(result);
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
					progressDialog = CustomProgressDialog.createDialog(RegisterActivity.this);
			    	progressDialog.setMessage("正在加载中...");
				}
				
		    	progressDialog.show();
			}
			
		}
}
