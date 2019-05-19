package com.message.net;


import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.basic.Activities.ChatActivity;
import com.basic.Activities.MainActivity;
import com.basic.Activities.ZJBEXBaseActivity;

public class NetWorker extends Thread{
	private final String TAG="NetWorker";
	
	final static String IP="192.168.56.1";
	final static int PORT=4040;
	
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	Context mContext;
	Vector<ReceiveInfoListener> listeners=new Vector<ReceiveInfoListener>();
	protected final byte connect = 1;
	protected final byte running = 2;
	protected byte state = connect;      //状态（默认为连接状态）
	
	private boolean onWork=true;

	public NetWorker(Context context) {
		mContext=context;
	}
	
	public NetWorker(){
		
	}
	
	@Override
	public void run() {
		while(onWork){
			switch(state){
			case connect:
				connect();
				break;
			case running:
				receiveMsg();
				break;
			}
		}
		
		try {
			if(socket!=null)
				socket.close();
			if(dis!=null)
				dis.close();
			if(dos!=null)
				dos.close();
			
			onWork=true;
			state=connect;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void connect(){
		//连接服务器端
		try {
//			Log.i(TAG, "客户端，开始连接服务器");
			socket=new Socket(IP, PORT);
			Log.i(TAG, "客户端，连接服务器成功");
			Log.i(TAG, "client socket="+socket);
			
			state=running;
			dis=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dos=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			
		} catch (UnknownHostException e) {
			Log.i(TAG, "NetWorker connect() 异常："+e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, "NetWorker connect() 异常："+e.toString());
			e.printStackTrace();
		}
	}
	
	// 接收消息
	public synchronized void receiveMsg() {
		try {
			int type = dis.readInt();
			switch (type) {
			case Config.RECEIVE_TEXT:
				Log.i(TAG, "接收好友的文本消息");
				handReceiveText();
				break;
			case Config.RECEIVE_JBEXFriend:
				Log.i(TAG, "接收好友的结伴而行消息");
				handReceiveJBexFriend();
				break;
			case Config.RECEIVE_IMG:
				handReceiveData(Config.MESSAGE_TYPE_IMG);
				break;
			case Config.RECEIVE_AUDIO:
				handReceiveData(Config.MESSAGE_TYPE_AUDIO);
				break;
			case Config.RESULT_GET_OFFLINE_MSG:
				handGetOfflineMsg();
				break;
			}
		} catch (Exception e) {
//			 Log.e(TAG, "receiveMsg() exception:"+e.toString());
		}
	}
	


	public void addReceiveInfoListener(ReceiveInfoListener listener) {
		listeners.add(listener);
	}
	
	public boolean receive(ChatMessage message){
		Log.i(TAG, "NetWorker中的listener数为：" + listeners.size());
		
		//现阶段只有ChatActivity注册了ReceiveInfoListener,所以listeners里只有一个ReceiveInfoListener
		ReceiveInfoListener listener = listeners.get(0);
		boolean result = listener.receive(message);

		return result;
	}
	
	
	/*******************以下方法是客户端向服务器发送请求的方法************************/
	
	public boolean sendUid(int userid) {
		// TODO 自动生成的方法存根
		boolean result=true;
		try {
			dos.writeInt(Config.REQUEST_LOGIN);
			dos.writeInt(userid);
			dos.flush();
		}catch (Exception e){
			e.printStackTrace();
			result=false;
		}
		return result;
	}	
	
	//发送文本消息
	public boolean sendText(String self, String receiver, String time, String content){
		boolean result=true;
		try {
			dos.writeInt(Config.REQUEST_SEND_TXT);
			dos.writeUTF(self);
			dos.writeUTF(receiver);
			dos.writeUTF(time);
			dos.writeUTF(content);
			
			dos.flush();
			Log.i(TAG, "用户"+self+"向用户"+receiver+"发送文本消息："+content);
		} catch (Exception e) {
			Log.e(TAG, "sendText() exception:"+e.toString());
			e.printStackTrace();
			result=false;
		}
		
		return result;
	}
	
	//发送语音消息
	public boolean sendAudio(String self, String receiver, String time, String filePath){
		boolean result=true;
		try {
			dos.writeInt(Config.REQUEST_SEND_AUDIO);
			dos.writeUTF(self);
			dos.writeUTF(receiver);
			dos.writeUTF(time);
			
			//根据content指定的位置，获取语音文件的字节数组
			Log.i(TAG, "audio path="+filePath);
			readFileSendData(filePath);
			
			Log.i(TAG, "用户"+self+"向好友"+receiver+"发送语音消息");
		} catch (IOException e) {
			Log.e(TAG, "sendAudio() exception:"+e.toString());
			e.printStackTrace();
			result=false;
		}
		
		return result;
	}
	
	//发送图片消息
	public boolean sendImg(String self, String receiver, String time, String filePath){
		boolean result=true;
		
		try {
			dos.writeInt(Config.REQUEST_SEND_IMG);
			dos.writeUTF(self);
			dos.writeUTF(receiver);
			dos.writeUTF(time);
			dos.flush();
			
			readFileSendData(filePath);
			
			Log.i(TAG, "用户"+self+"向好友"+receiver+"发送图片消息：");
			
		} catch (IOException e) {
			Log.e(TAG, "sendImg() exception:"+e.toString());
			e.printStackTrace();
			result=false;
		}
		
		return result;
	}
	
	//发送结伴而行消息
	public boolean sendJbexFriend(String owneruser, String frienduser,
			String jbexinfoId) {
		// TODO 自动生成的方法存根
		boolean result=true;
		
		try {
			dos.writeInt(Config.REQUEST_SEND_JBEXFriend);
			dos.writeUTF(owneruser);
			dos.writeUTF(frienduser);
			dos.writeUTF(jbexinfoId);
			dos.flush();
			
			Log.i(TAG, "用户"+owneruser+"向好友"+frienduser+"发送结伴而行消息：");
			
		} catch (IOException e) {
			Log.e(TAG, "sendJbexFriend() exception:"+e.toString());
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	/**
	 * 读取文件(图片、语音)，发送数据
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
    private void readFileSendData(String filePath) throws FileNotFoundException, IOException {
        DataInputStream ddis=new DataInputStream(new FileInputStream(filePath));
        int length=0;
        int totalNum=0;
        byte[] buffer=new byte[1024];
        Log.i(TAG, "img.avaliable="+ddis.available());
        
        while((length=ddis.read(buffer))!=-1){
            totalNum+=length;
            dos.writeInt(length);
            dos.write(buffer, 0, length);
            dos.flush();
        }
        
        dos.writeInt(0);
        dos.flush();
        
        if(ddis!=null){
            ddis.close();
            ddis=null;
        }
        
        Log.i(TAG, "readFileSendData(): send bytes="+totalNum);
    }
	
	//获取暂存在服务器端的离线消息
	public void getOfflineMessage(){
		try {
			dos.writeInt(Config.REQUEST_GET_OFFLINE_MSG);
			dos.writeUTF(String.valueOf(ZJBEXBaseActivity.getSelf().getUser_id()));
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendExitQuest(){
		try{
			dos.writeInt(Config.REQUEST_EXIT);
			//dos.writeInt(ZJBEXBaseActivity.getSelf().getUser_id());
			dos.flush();
		}catch(IOException e){
			Log.e(TAG, "NetWorker sendExitQuest() 退出程序异常："+e.toString());
		}
	}
	
	
	/*******************以下方法是客户端接收服务器响应的方法************************/
	//
	
	
	//处理文本消息
	private void handReceiveText() {
		try {
			Log.i(TAG, "---开始接收文本消息---");
			String friend = dis.readUTF();      //消息的发送者，在这里强调他是消息的另一方
			String self   = dis.readUTF();	  	//别人发给自己的消息，自己是该消息的接收者
			String time=dis.readUTF();
			String content=dis.readUTF();
			
			Log.i(TAG, "接收到"+friend+"发给"+self+"的文本消息："+content);
			
			ChatMessage chatMessage=new ChatMessage(self, friend, Config.MESSAGE_FROM, Config.MESSAGE_TYPE_TXT, time, content);
			Message msg=new Message();
			Bundle bundle=new Bundle();
			bundle.putSerializable("chatMessage", chatMessage);
			msg.setData(bundle);
			
			//先判断当前的Activity是不是MainActivity,若不是则发出Notification
			ZJBEXBaseActivity activity=ZJBEXBaseActivity.getCurrentActivity();
			
			if(activity instanceof ChatActivity){
				if(receive(chatMessage)){   
					Log.i(TAG, "-- 用户和正在和消息的发送者聊天，所以直接将消息发送到聊天界面中--");
					msg.what=Config.RECEIVE_MESSAGE;
					ZJBEXBaseActivity.sendMessage(msg);
				}else{                      
					msg.what=Config.SEND_NOTIFICATION;    
					Log.i(TAG, "-- 用户和别的好友正在聊天，所以状态栏发出来消息通知--");
					ZJBEXBaseActivity.sendMessage(msg);
				}
			}else  if(activity instanceof MainActivity){
				msg.what=Config.SEND_MessageFragment; 
				Log.i(TAG, "-- 当前Activity是MainAcitivity--msg.what="+msg.what);
				ZJBEXBaseActivity.sendMessage(msg);
			}else{
				msg.what=Config.SEND_NOTIFICATION;    
				Log.i(TAG, "-- 当前Activity不是MainAcitivity--msg.what="+msg.what);
				ZJBEXBaseActivity.sendMessage(msg);
			}
			
			Log.i(TAG, "----文本消息接收完毕----");
			Log.i(TAG, "用户"+self+"接收到好友"+friend+"发来的文本消息："+content);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "handleTxt() exception:"+e.toString());
		}
	}
	
	
	 private void handReceiveJBexFriend() {
			// TODO 自动生成的方法存根
			Message msg=new Message();
			msg.what=Config.SEND_NOTIFICATION_JBEX_FRIEND;    
			ZJBEXBaseActivity.sendMessage(msg);
		}
	 
	//处理语音、图片消息
	private void handReceiveData(int type){
	    if(type==Config.MESSAGE_TYPE_IMG){
	        Log.i(TAG, "------开始接收图片消息------");
	    }else if(type==Config.MESSAGE_TYPE_AUDIO){
	        Log.i(TAG, "------开始接收语音消息------");
	    }
		
		File file=null;
		try{
			String friend =dis.readUTF();
			String self = dis.readUTF();
			String time=dis.readUTF();
			
			file=FileUtil.createFile(self, type);
			String filePath=file.getAbsolutePath();
			Log.i(TAG, "handReceiveData() filePath="+filePath);
		
			receiveDataWriteFile(filePath);
			
			ChatMessage chatMessage=new ChatMessage(self, friend, Config.MESSAGE_FROM, type, time, filePath);
			Message msg=new Message();
			Bundle bundle=new Bundle();
			bundle.putSerializable("chatMessage", chatMessage);
			msg.setData(bundle);
			/*
			//先判断当前的Activity是不是MainActivity,若不是则发出Notification
			WoliaoBaseActivity activity=WoliaoBaseActivity.getCurrentActivity();
			if(activity instanceof ChatActivity){
				if(receive(chatMessage)){   
					Log.i(TAG, "-- 用户和正在和消息的发送者聊天，所以直接将消息发送到聊天界面中--");
					msg.what=Config.RECEIVE_MESSAGE;
					WoliaoBaseActivity.sendMessage(msg);
				}else{                      
					msg.what=Config.SEND_NOTIFICATION;    
					Log.i(TAG, "-- 用户和别的好友正在聊天，所以状态栏发出来消息通知--");
					WoliaoBaseActivity.sendMessage(msg);
				}
			}else{
				msg.what=Config.SEND_NOTIFICATION;
				
				//设置消息的发送者(即好友)的id号
				WoliaoBaseActivity.friend.setFriendID(friend);
				
				Log.i(TAG, "-- 当前界面不是聊天界面，在状态栏发出来消息通知--");
				WoliaoBaseActivity.sendMessage(msg);
			}
			
			 if(type==Config.MESSAGE_TYPE_IMG){
		            Log.i(TAG, "------图片消息接收完毕------");
		        }else if(type==Config.MESSAGE_TYPE_AUDIO){
		            Log.i(TAG, "------语音消息接收完毕------");
		        }*/
		}catch (Exception e) {
			Log.e(TAG, "handReceiveData exception:"+e.toString());
			e.printStackTrace();
		}finally{
			if(file!=null){
				file=null;
			}
		}
	}

    private void receiveDataWriteFile(String filePath) throws FileNotFoundException, IOException {
        DataOutputStream ddos=new DataOutputStream(new FileOutputStream(filePath));   //将语音或图片写入本地SD卡
        int length=0;
        int totalNum=0;
        byte[] buffer=new byte[2048];
        while((length=dis.readInt())!=0){
            length=dis.read(buffer, 0, length);
            totalNum+=length;
            ddos.write(buffer, 0, length);
            ddos.flush();
        }
        
        if(ddos!=null){
            try {
                ddos.close();
                ddos=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        Log.i(TAG, "receiveDataWriteFile(): receive bytes="+totalNum);
    }
	
	//
	
	
	
	
	public void handGetOfflineMsg(){
		ArrayList<ChatMessage> ChatMessageList=new ArrayList<ChatMessage>();
		try {
			Log.i(TAG, "---------开始接收离线消息-----------");
			int listSize=dis.readInt();
			Log.i(TAG, "共有"+listSize+"条离线消息");
			for(int i=0; i<listSize; i++){
				String friendId=dis.readUTF();
				String selfId=dis.readUTF();
				int type=dis.readInt();
				String time=dis.readUTF();
				
				String content="";
				
				if(type==Config.MESSAGE_TYPE_TXT){
					content=dis.readUTF();
				}else if(type==Config.MESSAGE_TYPE_ADD_FRIEND){
				    //向服务器发出查询详细信息的请求
				    dos.writeInt(Config.REQUEST_GET_USER);
				    dos.writeUTF(selfId);
				    dos.flush();
				}else{
					int length=dis.readInt();
					byte[] data=new byte[length];
					int size=dis.read(data);
					File file=FileUtil.createFile(selfId, type);
					FileOutputStream out=new FileOutputStream(file);
					out.write(data, 0, size);
					out.flush();
					out.close();
					out=null;
					
					content=file.getAbsolutePath();
				}
				
				//向界面发送通知
				ChatMessage chatMessage=new ChatMessage(selfId, friendId, Config.MESSAGE_FROM, type, time, content);
				ChatMessageList.add(chatMessage);
				Log.i(TAG, "接收到"+i+"条离线消息，已发往程序界面");
			}
			
			
			Message msg=new Message();
			Bundle bundle=new Bundle();
			bundle.putParcelableArrayList("chatMessageList", ChatMessageList);
			msg.setData(bundle);
			//先判断当前的Activity是不是MainActivity,若不是则发出Notification
			ZJBEXBaseActivity activity=ZJBEXBaseActivity.getCurrentActivity();
			  if(activity instanceof MainActivity){
				msg.what=Config.SEND_MessageOffline; 
				Log.i(TAG, "-- 当前Activity是MainAcitivity--msg.what="+msg.what);
				ZJBEXBaseActivity.sendMessage(msg);
			}
			  
			Log.i(TAG, "");
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setOnWork(boolean onWork){
		this.onWork=onWork;
	}

	public boolean writeBuf(byte[] data) {
		int length=data.length;
		try {
			dos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void deleteReceiveInfoListener(ReceiveInfoListener listener) {
		// TODO 自动生成的方法存根
		listeners.remove(listener);
	}

	
}
