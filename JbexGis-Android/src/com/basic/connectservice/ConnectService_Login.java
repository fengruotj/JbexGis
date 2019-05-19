package com.basic.connectservice;

import java.util.HashMap;

import com.basic.service.model.User;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * IntentService服务，用于在后台以队列方式处理耗时操作。
 * 
 * @author lc
 *
 */
public class ConnectService_Login extends IntentService {

	private static final String ACTION_RECV_MSG = "com.basic.connectservice.action.RECEIVE_MESSAGE";

	public ConnectService_Login() {
		super("TestIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		/**
		 * 经测试，IntentService里面是可以进行耗时的操作的 IntentService使用队列的方式将请求的Intent加入队列，
		 * 然后开启一个worker thread(线程)来处理队列中的Intent
		 * 对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
		 */
		String jsonString = "";
		String flag;
		// 通过intent获取主线程传来的用户名和密码字符串
		String username = intent.getStringExtra("username");
		String password = intent.getStringExtra("password");
		jsonString = doLogin(username, password);
		Log.d("登录结果", jsonString);
		if (jsonString.equals("flase")) {
			flag = "flase";
		} else {
			User user = GetUser.getSimpleUser("user", jsonString);
			flag = user.getFlag();
		}
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_RECV_MSG);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra("result", flag);
		broadcastIntent.putExtra("jsonString", jsonString);
		sendBroadcast(broadcastIntent);

	}

	// 定义发送请求的方法
	private String doLogin(String username, String password) {
		String jsonString = "";
		// 使用Map封装请求参数
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		// 定义发送请求的URL
		// String url = HttpUtil.BASE_URL + "servlet/LoginServlet?username=" +
		// username
		// + "&password=" + password; // GET方式
		String url = HttpUtil.BASE_URL + "servlet/LoginServlet"; // POST方式
		Log.d("url", url);
		Log.d("username", username);
		Log.d("password", password);
		try {
			// 发送请求
			jsonString = HttpUtil.postRequest(url, map); // POST方式
			// strFlag = HttpUtil.getRequest(url); // GET方式
			Log.d("服务器返回json值", jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonString;

	}
}
