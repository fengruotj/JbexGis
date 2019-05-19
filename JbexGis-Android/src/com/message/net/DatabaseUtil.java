package com.message.net;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.service.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseUtil extends SQLiteOpenHelper{
	private final String TAG="DatabaseUtil";
	private final String TABLE_NAME="message"; 
	private final static String DB_NAME="jbex_db";
	
	public DatabaseUtil(Context context){
		this(context, DB_NAME, null, 1);
	}

	public DatabaseUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table "+TABLE_NAME+"( self_Id varchar2(10) , friend_Id varchar2(10), direction int, type int, content varchar2(240), time varchar2(12));";
		Log.i(TAG, "sql="+sql);
		db.execSQL(sql);
		String userSql="create table friend(selfId varchar2(10), friendId varchar2(10), type int, content varchar2(240), time varchar2(12),num int)";
		db.execSQL(userSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
	public ArrayList<ChatMessage> queryMessages(String selfId, String friendId){
		ArrayList<ChatMessage> list=new ArrayList<ChatMessage>();
		SQLiteDatabase db=getReadableDatabase();
		String selection="self_Id=? and friend_Id=?";
		String[] selectionArgs=new String[]{"'"+selfId+"'","'"+friendId+"'"};
//		Cursor cursor=db.query("message", null, selection, null, null, null, "time");
		Cursor cursor=db.query("message", null, selection, selectionArgs, null, null, "time");
		
		//如果游标为空（查找失败）或查到的信息数位0，返回null
		if(cursor==null || cursor.getCount()==0){
			return list;
		}
		Log.i(TAG, "queryMessages() cursor.count="+cursor.getCount());
				
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			ChatMessage message=new ChatMessage();
			message.setSelf(cursor.getString(cursor.getColumnIndex("self_Id")));
			message.setFriend(cursor.getString(cursor.getColumnIndex("friend_Id")));
			message.setDirection((cursor.getInt(cursor.getColumnIndex("direction"))));
			message.setType(cursor.getInt(cursor.getColumnIndex("type")));
			message.setContent(cursor.getString(cursor.getColumnIndex("content")));
			message.setTime(cursor.getString(cursor.getColumnIndex("time")));
			list.add(message);
			cursor.moveToNext();
//			Log.i(TAG, "queryMessages()：查到一条消息");
		}
		Collections.sort(list);
		cursor.close();
		db.close();
		
		return list;
		
	}
	
	//每次进入ChatActivity时 都将Friend表中的num设置为0
	public void setFriendNum(String self_Id, String friend_Id,int num){
		String sql="select type,content,time,num from friend where selfId=? and friendId=? ";
		String[] args = { self_Id, friend_Id };
		SQLiteDatabase db=getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, args);
		Log.i(TAG, "cursor.getCount="+cursor.getCount());
		if(cursor != null && cursor.getCount() != 0){
		cursor.moveToFirst();
		ContentValues updates=new ContentValues();
		updates.put("num", num);
		String where="selfId=? and friendId=? ";
		String[] args2 = {self_Id, friend_Id }; 
		int number=db.update("friend", updates, where, args2);
		Log.i(TAG, "setFriendNum update friend number="+number);
		}
	}
	
	//查询自己的所有好友信息
		public ArrayList<Friend> queryFriends(String selfId){
			ArrayList<Friend> list=new ArrayList<Friend>();
			SQLiteDatabase db=getReadableDatabase();
			String sql="select friendId,type,content,time,num from friend where selfId=? ";
			String[] args = { "'"+selfId+"'"};
			Cursor cursor = db.rawQuery(sql, args);
			
			Log.i(TAG, "queryFriends() "+selfId);
			Log.i(TAG, "queryFriends() cursor.count="+cursor.getCount());
			//如果游标为空（查找失败）或查到的信息数位0，返回null
			if(cursor==null || cursor.getCount()==0){
				db.close();
				Log.i(TAG, "DatabaseUtil queryFriends() 异常：游标为空");
				return list;
			}
			
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				Friend friend=new Friend();
				friend.setFriendID(cursor.getString(cursor.getColumnIndex("friendId")));
				friend.setType(cursor.getInt(cursor.getColumnIndex("type")));
				friend.setContent(cursor.getString(cursor.getColumnIndex("content")));
				friend.setTime(cursor.getString(cursor.getColumnIndex("time")));
				friend.setNum(cursor.getInt(cursor.getColumnIndex("num")));
				
				list.add(friend);
				cursor.moveToNext();
			}
			db.close();
			return list; 
		}
		
	/**
	 * 插入消息的步骤：1.往message表中插入一条新消息
	 *            2.更新friend表的type,content,time字段
	 * @param values
	 */
	public void insertMessage(ContentValues values,int Data_Base_type){
		SQLiteDatabase db=getWritableDatabase();
	
		//插入一条新消息
		db.insert(TABLE_NAME, null, values);
		
		int type=values.getAsInteger("type");
		String content=values.getAsString("content");
		String time=values.getAsString("time");
		String self_Id=values.getAsString("self_Id");
		String friend_Id=values.getAsString("friend_Id");
		Log.i(TAG, "InsertMessage type="+type+" content="+content+" time="+time+" self_Id="+self_Id+" friend_Id="+friend_Id);
        
		String sql="select type,content,time,num from friend where selfId=? and friendId=? ";
		String[] args = { self_Id, friend_Id };
		
		Cursor cursor = db.rawQuery(sql, args);
		Log.i(TAG, "cursor.getCount="+cursor.getCount());
		if(cursor != null && cursor.getCount() != 0){
		cursor.moveToFirst();
		int num=cursor.getInt(cursor.getColumnIndex("num"));
		num=num+1;
		//更新同好友最后的一条聊天消息的时间、内容、类型
		ContentValues updates=new ContentValues();
		updates.put("type", type);
		updates.put("content", content);
		updates.put("time", time);
		
		if(Data_Base_type==Config.DateBase_SEND_MESSAGE)
			updates.put("num",0);
		else if(Data_Base_type==Config.DateBase_GET_MESSAGE)
		updates.put("num",num);
		
		String where="selfId=? and friendId=? ";
		String[] args2 = {self_Id, friend_Id }; 
		int number=db.update("friend", updates, where, args2);
		Log.i(TAG, "insertfriend update friend number="+number);
		}
		else{
			ContentValues value=new ContentValues();
			value.put("selfId", self_Id);
			value.put("friendId", friend_Id);
			value.put("type", type);
			value.put("content", content);
			value.put("time", time);
			
			if(Data_Base_type==Config.DateBase_SEND_MESSAGE)
				value.put("num",0);
			else if(Data_Base_type==Config.DateBase_GET_MESSAGE)
				value.put("num",1);
			
			long number=db.insert("friend", null, value);
			Log.i(TAG, "insertfriend insert friend number="+number);
		}
		db.close();
	}
	
	
	/** 单纯地删除一套消息，由于该消息不是列表中的最后一条，所以无需更新friend表中的字段
	 *	删除某一条消息的步骤：1.在message表中删除这条消息  
	 *                 2.如果被删除的消息类型为图片或语音，则应删除相应的图片、语音文件 
	 * @param ChatMessage msg :待删除的消息
	 */
	public boolean deleteMessageOnly(ChatMessage msg){
		SQLiteDatabase db=getWritableDatabase();
		
		String self=msg.getSelf(),friend=msg.getFriend(),time=msg.getTime();
		String where="self_Id=? and friend_Id=? and time=?";
		String[] whereArgs={self, friend, time};
		int number=db.delete(TABLE_NAME, where, whereArgs);
		db.close();
		db=null;
		
		if(number!=0){
		    Log.i(TAG, "");
		    Log.i(TAG, "-------------------------------------------------------------");
			Log.i(TAG, "deleteMessageOnly() 成功删除一条消息");
			Log.i(TAG, "消息:self_Id="+self+", friend_Id="+friend+", time="+time);
            Log.i(TAG, "-------------------------------------------------------------");
            Log.i(TAG, "");
			//消息类型为图片或语音需要删除保存在SDcard上的图片、语音文件
			if(msg.getType()!=Config.MESSAGE_TYPE_TXT){
				String filePath=msg.getContent();
				File file=new File(filePath);
				if(file.exists()==true){
					file.delete();
				}
				file=null;
			}
			
			return true;
		}else{
		    Log.i(TAG, "");
            Log.i(TAG, "-------------------------------------------------------------");
			Log.i(TAG, "deleteMessageOnly() 删除一条消息失败");
			Log.i(TAG, "消息:self_Id="+self+", friend_Id="+friend+", time="+time);
            Log.i(TAG, "-------------------------------------------------------------");
            Log.i(TAG, "");
			return false;
		}
	}
	
	/** 删除一条消息,由于该消息位于列表的最后一条，所以需要更新friend表的type,content,time字段
	 *	删除某一条消息的步骤：1.在message表中删除这条消息  
	 *				   2.获取message表中time字段值最大的消息  
	 *                 3.用time字段值最大的消息更新friend表的type,content,time字段  
	 *                 4.如果被删除的消息类型为图片或语音，则应删除相应的图片、语音文件 
	 *                 
	 * @param lastMsg :  待删除的消息，位于消息列表的最后一条
	 * @param preMsg   : 待删除消息的前一条消息,当待删除消息被成功删除后，preMsg就成为消息列表中的最后一条消息了 
	 */
	public boolean deleteMessageUpdateFriend(ChatMessage lastMsg, ChatMessage preMsg){
		boolean result=false;
		SQLiteDatabase db=getWritableDatabase();
		db.beginTransaction();
		try{
			//删除一条消息
			String self=lastMsg.getSelf(),friend=lastMsg.getFriend(),time=lastMsg.getTime();
			String where="self_Id=? and friend_Id=? and time=?";
			String[] whereArgs={"'"+self+"'", "'"+friend+"'", time};
			int number=db.delete(TABLE_NAME, where, whereArgs);
			if(number!=0){
			    Log.i(TAG, "");
			    Log.i(TAG, "--------------------------------------------------------------");
				Log.i(TAG, "deleteMessageUpdateFriend() 成功删除一条消息");
				Log.i(TAG, "消息:self_Id="+self+", friend_Id="+friend+", time="+time);
				Log.i(TAG, "-------------------------------------------------------------");
				 Log.i(TAG, "");
			}else{
			    Log.i(TAG, "");
                Log.i(TAG, "--------------------------------------------------------------");
				Log.i(TAG, "deleteMessageUpdateFriend() 删除一条消息失败");
				Log.i(TAG, "消息:self_Id="+self+", friend_Id="+friend+", time="+time);
				Log.i(TAG, "-------------------------------------------------------------");
				Log.i(TAG, "");
			}
	
			//更新friend表的type,content,time字段
			ContentValues values=new ContentValues();
			values.put("type", preMsg.getType());
			values.put("content", preMsg.getContent());
			values.put("time", preMsg.getTime());
			String where2="selfId="+preMsg.getSelf()+" and friendId="+preMsg.getFriend();
			int number2=db.update("friend", values, where2, null);
			
			if(number!=0 && number2!=0){
				// 消息类型为图片或语音需要删除保存在SDcard上的图片、语音文件
				if (lastMsg.getType() != Config.MESSAGE_TYPE_TXT) {
					String filePath = lastMsg.getContent();
					File file = new File(filePath);
					if (file.exists() == true) {
						file.delete();
					}
					file = null;
				}
				
				db.setTransactionSuccessful();    //调用次方法则提交修改，不调用此方法则回滚
				result=true;
			}
		}finally{
            db.endTransaction();
            db.close();
        }
		
		return result;
	}
	
	// 查找出message表中time字段最大的记录
	public ChatMessage queryMaxTimeOfMessage(String self, String friend) {
		ChatMessage msg = new ChatMessage();
		SQLiteDatabase db = getReadableDatabase();
		
		String sql = "select type,content,time from message where self_Id=? and friend_Id=? and time=(select max(time) from message)";
		String[] args = { self, friend };
		Cursor cursor = db.rawQuery(sql, args);
		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			msg.setType(type);
			String content = cursor.getString(cursor.getColumnIndex("content"));
			msg.setContent(content);
			String time = cursor.getString(cursor.getColumnIndex("time"));
			msg.setTime(time);

			Log.i(TAG, "queryMsgForUpdate() type=" + type + " content="
					+ content + " time=" + time);
		}
		msg.setSelf(self);
		msg.setFriend(friend);
		
		db.close();
		return msg;
	}		
	
	//更新头像
	public void updateFirendHead(String selfId, String friendId, String headPath, String modifyTime){
	    SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("head", headPath);
        values.put("modifyTime", modifyTime);
        String where="selfId="+selfId+" and friendId="+friendId;
        int number=db.update("friend", values, where, null);
        db.close();
	}
	
	//更新好友的type,content,time字段
	public boolean updateFriend(ChatMessage message){
		SQLiteDatabase db=getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("type", message.getType());
		values.put("content", message.getContent());
		values.put("time", message.getTime());
		String where="selfId="+message.getSelf()+" and friendId="+message.getFriend();
		int number=db.update("friend", values, where, null);
		db.close();
		
		if(number!=0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除与某人的会话———删除同某好友的全部聊天记录
	 * @param self
	 * @param friend
	 */
	public boolean deleteAllMessages(String self, String friend){
		boolean result=false;
		//查出同某好友的全部聊天消息
		List<ChatMessage> list=queryMessages(self, friend);
		
		SQLiteDatabase db=getWritableDatabase();
		db.beginTransaction();
		try{
			//删除同某好友的全部聊天消息
			String where="self_Id=? and friend_Id=?";
			String[] whereArgs={"'"+self+"'", "'"+friend+"'"};
			int number=db.delete(TABLE_NAME, where, whereArgs);
			if(number!=0){
				Log.i(TAG, "deleteAllMessages() 删除全部聊天消息成功");
			}else{
				Log.i(TAG, "deleteAllMessages() 删除全部聊天消息失败");
			}
			
			//更新friend表的type,content,time字段
			ChatMessage message=new ChatMessage();
			message.setSelf(self);
			message.setFriend(friend);
			
			String where2="selfId=? and friendId=?";
			int number2=db.delete("friend",  where2, whereArgs);
			if(number2!=0){
				Log.i(TAG, "deleteAllMessages() 删除friend表成功");
			}else{
				Log.i(TAG, "deleteAllMessages() 删除friend表失败");
			}

			if(number!=0 && number2!=0){
				//删除所有相关的图片、语音文件
				ChatMessage msg;
				File file;
				int size=list.size();
				for(int i=0; i<size; i++){
					msg=list.get(i);
					if(msg.getType()!=Config.MESSAGE_TYPE_TXT){
						file=new File(msg.getContent());
						if(file.exists()==true){
							file.delete();
						}
					}
				}
				Log.i(TAG, "deleteAllMessages() 删除全部聊天消息成功，并且删除了friend表");
				result=true;
				db.setTransactionSuccessful();
			}
		}finally{
			db.endTransaction();
			db.close();
		}
		
		return result;
	}
}
