package com.message.net;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.location.f.c;

/**
 * 聊天信息的格式
 * @author hu
 *
 */
public class ChatMessage implements Parcelable ,Serializable,Comparable<ChatMessage>{
	private String self;
	private String friend;
	private int direction;
	private int type;		//消息类型：纯文本、表情+文本、图片、语音
	private String content;
	private String time;
	
	public ChatMessage(String self, String friend,int direction, int type, String time, String content) {
		this.self=self;
		this.friend=friend;
		this.direction = direction;
		this.type=type;
		this.time=time;
		this.content = content;
	}

	public ChatMessage(){
		
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	@Override
	public int compareTo(ChatMessage arg0) {
		// TODO 自动生成的方法存根c
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime1 = null,dateTime2 = null;
		try {
			 dateTime1 = dateFormat.parse(this.time);
			 dateTime2 = dateFormat.parse(arg0.time);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return dateTime1.compareTo(dateTime2);
	}

	@Override
	public int describeContents() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO 自动生成的方法存根
		 dest.writeString(self);
		 dest.writeString(friend);
	     dest.writeString(content);
	     dest.writeString(time);  
	     dest.writeInt(direction);
	     dest.writeInt(type);
	}

	public static final Parcelable.Creator<ChatMessage> CREATOR
	= new Parcelable.Creator<ChatMessage>() {
		public ChatMessage createFromParcel(Parcel in) {
			return new ChatMessage(in);
		}

		public ChatMessage[] newArray(int size) {
			return new ChatMessage[size];
		}
	};

	private ChatMessage(Parcel in) {
		self =in.readString();
		friend =in.readString();
		content =in.readString();
		time =in.readString();
		direction =in.readInt();
		type =in.readInt();
		
}
}
