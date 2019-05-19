package com.message.net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Communication类是用户和实际发送、接收消息线程之间的桥梁，用户向Communication发送命令，Communication调用线程执行发送请求
 * @author hu
 *
 */
public class Communication {

	private NetWorker netWorker;              //这是一个线程类
	public static Communication instance;    //一个静态的Communication实例
	public static MessageDigest md;          //消息摘要

	private Communication() {
		netWorker = new NetWorker();
		
		//线程开始执行
		netWorker.start();
		try {
			if (md == null)
				md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String MD5(String strSrc) {
		byte[] bt = strSrc.getBytes();
		md.update(bt);
		String strDes = bytes2Hex(md.digest());   // 字节数组变成16进制字符串
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		StringBuffer des = new StringBuffer();
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des.append("0");
			}
			des.append(tmp);
		}
		return des.toString();
	}

	public static Communication newInstance() {
		if (instance == null)
			instance = new Communication();
		return instance;
	}
	
	public void setInstanceNull(){
		instance=null;
	}

	public NetWorker getTransportWorker() {
		return netWorker;
	}

	//程序退出时的清理工作
	public void stopWork(){
		netWorker.setOnWork(false);
	}

	
	public boolean  sendJbexFriend(String owneruser,String frienduser,String jbexinfoId){
		return netWorker.sendJbexFriend(owneruser,frienduser,jbexinfoId);
	}
	
	public boolean sendUid(int userid){
		return netWorker.sendUid(userid);
	}
	public boolean sendImg(String self, String friend, String time, String content) {
		return netWorker.sendImg(self, friend, time, content);
	}

	public boolean sendText(String self, String friend, String time, String content) {
		return netWorker.sendText(self, friend, time, content);
	}

	public boolean sendAudio(String self, String friend, String time,
			String content) {
		return netWorker.sendAudio(self, friend, time, content);
	}
	
	//接收暂存在服务器端的离线消息
	public void getOfflineMessage(){
		netWorker.getOfflineMessage();
	}
	
	public void sendExitQuest(){
		netWorker.sendExitQuest();
	}

	public void addReceiveInfoListener(ReceiveInfoListener listener) {
		netWorker.addReceiveInfoListener(listener);
	}
	public void deleteReceiveInfoListener(ReceiveInfoListener listener) {
		netWorker.deleteReceiveInfoListener(listener);
	}
	public String newSessionID() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	public void reconnect() {
		netWorker.notify();
	}
}
