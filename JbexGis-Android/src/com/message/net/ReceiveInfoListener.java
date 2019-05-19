package com.message.net;


/**
 * 接收消息的接口,子类需实现receive(Message info)方法
 * @author Administrator
 *
 */
public interface ReceiveInfoListener {

	public boolean receive(ChatMessage info);
}
