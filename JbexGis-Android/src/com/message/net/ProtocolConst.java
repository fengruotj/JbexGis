package com.message.net;

public class ProtocolConst {

	// protected static final String ip = "192.168.1.102";
	protected static final String ip = "10.66.41.7";
	// protected static final String ip = "192.168.0.117";
	protected static final int port = 8888;
	public static final int CMD_REGISTER = 1;              //’注册‘命令
	public static final int CMD_LOGIN = 2;				   //’登录‘命令
	public static final int CMD_CHECK_IN = 3;			   //’向服务器检入‘命令
	public static final int CMD_GET_ALL_LIST = 4;          //’获取用户相关的所有列表‘命令
	public static final int CMD_SEND_INFO_TO_USER = 5;     //’发送信息给好友‘命令 
	public static final int CMD_SEND_INFO_TO_GROUP = 6;    //’发送信息给组‘命令

	public static final int CMD_LOGIN_SUCESS = 100;        //服务器回馈的’登录成功‘命令
	public static final int CMD_LOGIN_FAILD = 101;         //服务器回馈的’登录失败‘命令
	public static final int CMD_REGISTER_SUCESS = 102;     //服务器回馈的’注册成功‘命令
	public static final int CMD_REGISTER_FAILD = 103;      //服务器回馈的’注册失败‘命令
	public static final int CMD_CHECK_IN_SUCESS = 104;     //服务器回馈的’登录成功‘命令
	public static final int CMD_CHECK_IN_FAILD = 105;		//服务器回馈的’向服务器检入失败‘命令
	public static final int CMD_GET_ALL_LIST_SUCESS = 106;  //服务器回馈的’获取所有列表成功‘命令
	public static final int CMD_GET_ALL_LIST_FAILD = 107;   //服务器回馈的’获取所有列表失败‘命令
	public static final int CMD_HAS_USER_ONLINE = 108;      //服务器回馈的’用户上线‘命令
	public static final int CMD_HAS_USER_OFFLINE = 109;     //服务器回馈的’用户下线‘命令
	public static final int CMD_SEND_INFO_SUCESS = 110;     //服务器回馈的'发送信息给好友成功‘命令
	public static final int CMD_SEND_INFO_FAILD = 111;      //服务器回馈的'发送信息给好友失败‘命令
	
	public static final int CMD_RECEIVE_INFO = 112;             //。。。。。。。
	public static final int CMD_RECEIVE_INFO_ON_MAIN = 113;     //在Main界面接收到消息（当前显示的是Main界面）
	public static final int CMD_RECEIVE_INFO_FROM_GROUP = 114;  //接收到来自组的消息

	public static final int CMD_PASSWORD_NOT_SAME = 200;                //两次密码不一致
	public static final int CMD_UPDATE_RECEIVE_INFO = 201;              //更新接收到的消息
	public static final int CMD_UPDATE_RECEIVE_INFO_FROM_GROUP = 202;   //更新接收到来自组的消息

	public static final int CMD_PLAY_MSG = 500;             //播放提示音
	public static final int CMD_SYSTEM_INFO = 900;          //系统消息
	public static final int CMD_SYSTEM_ERROR = 901;         //系统错误

}
