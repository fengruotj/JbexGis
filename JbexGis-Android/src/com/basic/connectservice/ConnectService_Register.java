package com.basic.connectservice;

import java.util.HashMap;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ConnectService_Register extends IntentService {

	private static final String ACTION_RECV_MSG = "com.basic.connectservice.action.RECEIVE_MESSAGE";

	public ConnectService_Register() {
		super("TestIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		/**
		 * 经测试，IntentService里面是可以进行耗时的操作的 IntentService使用队列的方式将请求的Intent加入队列，
		 * 然后开启一个worker thread(线程)来处理队列中的Intent
		 * 对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
		 */
		// 通过intent获取主线程传来的邮箱、昵称和密码字符串
		String email = intent.getStringExtra("email");
		String password = intent.getStringExtra("password");
		String user_nickname = intent.getStringExtra("user_nickname");
		Boolean flag = doRegister(email, password, user_nickname);
		if (!flag) {
			Log.d("注册结果", flag.toString());
		} else if (flag) {
			Log.d("注册结果", flag.toString());
		}
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_RECV_MSG);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra("result", flag.toString());
		sendBroadcast(broadcastIntent);
	}

	private Boolean doRegister(String email, String password,
			String user_nickname) {
		String strFlag = "";
		// 使用Map封装请求参数
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		map.put("user_nickname", user_nickname);
		// 定义发送请求的URL
		// String url = HttpUtil.BASE_URL + "servlet/RegisterServlet?username="
		// + username + "&password=" + password + "&email=" + email; // GET方式
		String url = HttpUtil.BASE_URL + "servlet/RegisterServlet"; // POST方式
		Log.d("url", url);
		Log.d("user_nickname", user_nickname);
		Log.d("email", email);
		Log.d("password", password);
		try {
			// 发送请求
			strFlag = HttpUtil.postRequest(url, map); // POST方式
			// strFlag = HttpUtil.getRequest(url); // GET方式
			// if (strFlag == null) {
			// Log.d("http请求参数", "false");
			// strFlag = "false";
			// } else {
			// Log.d("注册结果", strFlag);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("http请求结果", "失败！");
		}

		if (strFlag.trim().equals("true")) {
			return true;
		} else {
			return false;
		}
	}
}
