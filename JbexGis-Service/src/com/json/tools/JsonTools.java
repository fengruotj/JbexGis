package com.json.tools;

import net.sf.json.JSONObject;

public class JsonTools {

	/**
	 * @param key
	 *            表示json字符串的头信息
	 * @param object
	 *            是对解析的集合的类型
	 * @return
	 */
	public static String createJsonString(String key, Object value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);
		return jsonObject.toString();
	}

}
