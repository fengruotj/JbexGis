package com.basic.model;

public class MessageBean {
	private String PhotoDrawableId;    //聊天的图片的ID号
	private String MessageName;      //聊天好友的姓名
	private String MessageContent;   //聊天的内容
	private String MessageTime;      //聊天的时间
	private int  num;
	public MessageBean(){
		
	}
	
	public MessageBean(String photoDrawableId, String messageName,
			String messageContent, String messageTime) {
		super();
		PhotoDrawableId = photoDrawableId;
		MessageName = messageName;
		MessageContent = messageContent;
		MessageTime = messageTime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPhotoDrawableId() {
		return PhotoDrawableId;
	}
	public void setPhotoDrawableId(String mPhotoDrawableId) {
		this.PhotoDrawableId = mPhotoDrawableId;
	}
	public String getMessageName() {
		return MessageName;
	}
	public void setMessageName(String messageName) {
		MessageName = messageName;
	}
	public String getMessageContent() {
		return MessageContent;
	}
	public void setMessageContent(String messageContent) {
		MessageContent = messageContent;
	}
	public String getMessageTime() {
		return MessageTime;
	}
	public void setMessageTime(String messageTime) {
		MessageTime = messageTime;
	}
	@Override
	public String toString() {
		return "MessageBean [mPhotoDrawableId=" + PhotoDrawableId
				+ ", MessageName=" + MessageName + ", MessageContent="
				+ MessageContent + ", MessageTime=" + MessageTime + "]";
	}
	
	
	
}
