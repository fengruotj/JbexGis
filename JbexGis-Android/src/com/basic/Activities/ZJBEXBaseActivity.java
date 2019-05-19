package com.basic.Activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.basic.connectservice.UserService;
import com.basic.service.model.User;
import com.message.net.ChatMessage;
import com.message.net.Communication;
import com.message.net.Config;
import com.message.net.DatabaseUtil;
import com.message.net.Friend;
import com.message.net.ProtocolConst;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public abstract class ZJBEXBaseActivity extends Activity{
	//Activity的集合，将开启的Activity记录于此，退出程序时，逐个关闭Activity
	protected static LinkedList<ZJBEXBaseActivity> queue = new LinkedList<ZJBEXBaseActivity>();
	public static final String communication = "请稍后，正在通信……";
	public static final String communication_faild = "对不起，通信失败！";
	protected static Communication con;
	private static MediaPlayer player;
	protected static DatabaseUtil dbUtil;
	private static final String TAG="ZJBEXBaseActivity";
	public static User self=new User();
	public static User friend=new User();
	
	final int EXIT_DIALOG=0x12;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (!queue.contains(this))
			queue.add(this);
		if (player == null) {
			player = MediaPlayer.create(this, R.raw.msg);
			Log.i(TAG, TAG+" player="+player);
			try {
				player.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(dbUtil==null){
		    dbUtil=new DatabaseUtil(this);
		}
		Log.i(TAG, "目前Activity number="+queue.size());
	}
	
	public static ZJBEXBaseActivity getActivity(int index) {
		if (index < 0 || index >= queue.size())
			throw new IllegalArgumentException("out of queue");
		return queue.get(index);
	}
	
	public static User getSelf() {
		return self;
	}

	public static void setSelf(User self) {
		ZJBEXBaseActivity.self = self;
	}
   
	public static DatabaseUtil getDbUtil() {
		return dbUtil;
	}

	public static void setDbUtil(DatabaseUtil dbUtil) {
		ZJBEXBaseActivity.dbUtil = dbUtil;
	}
	
	public static User getFriend() {
		return friend;
	}

	public static void setFriend(User friend) {
		ZJBEXBaseActivity.friend = friend;
	}

	public static ZJBEXBaseActivity getCurrentActivity() {
		return queue.getLast();
	}
	
	public void makeTextShort(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void makeTextLong(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public abstract void processMessage(Message msg);

	public static void sendMessage(int cmd, String text) {
		Message msg = new Message();
		msg.what = cmd;
		msg.obj = text;
		sendMessage(msg);
	}
    
	public static void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}

	public static void sendEmptyMessage(int what) {
		handler.sendEmptyMessage(what);
	}
   
	
	public static void saveMessageToDb(String owneruser,String frienduser,int direction, int type, String time, String content,int Data_Base_type) {
		ContentValues values=new ContentValues();
		values.put("self_Id", "'"+owneruser+"'");
		values.put("friend_Id", "'"+frienduser+"'");
		values.put("direction", direction);
		values.put("type", type);
		values.put("time", time);
		values.put("content", content);
		dbUtil.insertMessage(values,Data_Base_type);
	}
	
	public static List<Friend> getFriendMessageList(String userid){
		List<Friend> list=new ArrayList<Friend>();
		list=dbUtil.queryFriends(userid);
		return list;
	}
	
	public static void setsetFriendNumToDb(String self_Id, String friend_Id,int num){
		self_Id="'"+self_Id+"'";
		friend_Id="'"+friend_Id+"'";
		dbUtil.setFriendNum(self_Id, friend_Id, num);
	}
	
	public static void saveToDb(ChatMessage msg,int Data_Base_type) {
		ContentValues values=new ContentValues();
		values.put("self_Id", "'"+msg.getSelf()+"'");
		values.put("friend_Id", "'"+msg.getFriend()+"'");
		values.put("direction", msg.getDirection());
		values.put("type", msg.getType());
		values.put("time", msg.getTime());
		values.put("content", msg.getContent());
		dbUtil.insertMessage(values,Data_Base_type);
	}
	
	public static void MessageOfflineSaveToDb(ArrayList<ChatMessage> chatMessageList) {
		// TODO 自动生成的方法存根
		for(int i=0;i<chatMessageList.size();i++){
			ChatMessage chatmes=chatMessageList.get(i);
			saveToDb(chatmes,Config.DateBase_GET_MESSAGE);
		}
	}

	//Handler对象是静态的，则所有TuliaoBaseActivity的子类都是共用同一个消息队列
		private static Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ProtocolConst.CMD_SYSTEM_INFO: {
					queue.getLast().makeTextShort(msg.obj.toString());
				}
					break;
				case ProtocolConst.CMD_SYSTEM_ERROR: {
					queue.getLast().makeTextShort(msg.obj.toString());
				}
					break;
				case ProtocolConst.CMD_PLAY_MSG: {
					playMsg();
				}
					break;
				case Config.finFrienduser:{
					
					final String friendId=msg.getData().getString("friendID");
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO 自动生成的方法存根
						User friend=UserService.getUser(friendId,"id");
						ZJBEXBaseActivity.setFriend(friend);
						}
					}).start();	
				}
				break;
					
				default:
					if(!queue.isEmpty()){
						queue.getLast().processMessage(msg);
					}
					break;
				}
			}
		};
   
		public static void playMsg() {
			try {
				player.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		
		public static String getTime() {
			return DateFormat.format("hh:mm:ss", Calendar.getInstance()).toString();
		}
		
		public    void sendNotifycation_JBEXFriend(){
			//发送Notification
			playMsg();
			NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			Notification notification=new Notification(R.drawable.notify_icon, "结伴而行请求", System.currentTimeMillis());
			Intent intent=new Intent(this, MyPublishJbexActivity.class);
			
			Bundle data=new Bundle();
			data.putSerializable("owneruser", self);
			intent.putExtras(data);
			
			PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(this, "结伴而行请求", "查看结伴请求", pendingIntent);
			notification.flags|=Notification.FLAG_AUTO_CANCEL;
			manager.notify(0, notification);
		}
		
		public void sendNotifycation(String selfid,String friendid){
			//发送Notification
			
			playMsg();
			// 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。  
			NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			// 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，
			//所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
			Intent intent=new Intent(this, ChatActivity.class);
			Bundle data=new Bundle();
			data.putInt("frienduser", Integer.valueOf(friendid));
			data.putInt("owneruser", Integer.valueOf(selfid));
			intent.putExtras(data);
			
			PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Notification notification=new Notification(R.drawable.notify_icon, "我聊新消息", System.currentTimeMillis());
			notification.setLatestEventInfo(this, "新消息", "查看新消息", pendingIntent);
			// FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。  
            // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示  
			notification.flags|=Notification.FLAG_AUTO_CANCEL;
			
			manager.notify(1, notification);
			
			/*
			 * Pendingintent传值问题
               pendingintent传值经常获取到的值是第一次的值或者null，这个跟第二个参数和最后一个参数选择有关系。
               FLAG_ONE_SHOT
               FLAG_NO_CREATE
               FLAG_CANCEL_CURRENT
               FLAG_UPDATE_CURRENT
                              上面4个flag中最经常使用的是FLAG_UPDATE_CURRENT，
                              因为描述的Intent有更新的时候需要用到这个flag去更新你的描述，
                              否则组件在下次事件发生或时间到达的时候extras永远是第一次Intent的extras。
                              使用FLAG_CANCEL_CURRENT也能做到更新extras，只不过是先把前面的extras清除，
                              另外FLAG_CANCEL_CURRENT和FLAG_UPDATE_CURRENT的区别在于能否新new一个Intent，
               FLAG_UPDATE_CURRENT能够新new一个Intent，而FLAG_CANCEL_CURRENT则不能，只能使用第一次的Intent。

另外两flag就比较少用，利用FLAG_ONE_SHOT获取的PendingIntent只能使用一次，再使用PendingIntent也将失败，利用FLAG_NO_CREAT获取的PendingIntent若描述的Intent不存在则返回NULL值.
			 */
		}
		
		@Override
		public void onBackPressed() {
			Log.i(TAG, "Activity number="+queue.size());
			if(queue.size()==1){	//当前Activity是最后一个Activity了
				showDialog(EXIT_DIALOG);
			}else{
				queue.getLast().finish();
			}
		}
		
		@Override
		public void finish() {
			super.finish();
			if(!queue.isEmpty()){
				queue.removeLast();
			}
		}

		public void exit() {
			//关闭Socket连接、输入输出流
			con.stopWork();
			con.setInstanceNull();
			
			//关闭数据库、MediaPlayer
							
			if(player!=null)
				player=null;
			//关闭数据库、MediaPlayer
			if(dbUtil!=null){
				dbUtil.close();
			}	
			//销毁Activity
			while (queue.size() > 0)
				queue.getLast().finish();
		}
		
		@Override
		protected Dialog onCreateDialog(int id) {
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			Log.i(TAG, "dialog id="+id);
			switch(id){
			case EXIT_DIALOG:{
				Log.i(TAG, "要弹出的是退出提醒对话框");
				builder.setMessage("退出微行？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//向服务器发送“退出”请求
						con.sendExitQuest();
						
						//关闭到服务器的Socket连接，输入流、输出流
						exit();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
			}
				break;
			}
			AlertDialog dialog=builder.create();
			Log.i(TAG, "dialog="+dialog);
			return dialog;
		}
		
		private void showExitDialog(){
			AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
			builder.setMessage("退出我聊？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//向服务器发送“退出”请求
					con.sendExitQuest();
					
					//关闭到服务器的Socket连接，输入流、输出流
					exit();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.create().show();
		}
	
}
