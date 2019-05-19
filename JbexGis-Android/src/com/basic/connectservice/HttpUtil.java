 	package com.basic.connectservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * HTTP的访问公共类，用于处理GET和POST请求
 * 
 * @author lc
 *
 */
public class HttpUtil {

	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	// public static final String BASE_URL = "http://192.168.1.107:8080/test1/";
	public static final String BASE_URL = "http://192.168.56.1:8080/test/";

	/**
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(String url) throws Exception {
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(url);
		// 发送GET请求
		HttpResponse httpResponse = httpClient.execute(get);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			// String result = EntityUtils.toString(httpResponse.getEntity());
			// return result;
			InputStream inputStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder builder = new StringBuilder();
			String s = null;
			for (s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			return builder.toString();

		} else {
			Log.d("服务器响应代码", (new Integer(httpResponse.getStatusLine()
					.getStatusCode())).toString());
			return null;
		}
	}

	/**
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(String url, Map<String, String> rawParams)
			throws Exception {
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		// 发送POST请求
		HttpResponse httpResponse = httpClient.execute(post);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			// String result = EntityUtils.toString(httpResponse.getEntity());
			// return result;
			InputStream inputStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder builder = new StringBuilder();
			String s = null;
			for (s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
			return builder.toString();
		}
		return null;
	}
}
