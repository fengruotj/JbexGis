package com.message.net;


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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basic.bean.TMessagebean;
import com.basic.dao.TJbexinfoAction;
import com.basic.dao.TMessagebeanAction;
/**
 * 服务器端进行消息转发的Task
 * @author hu
 *
 */
public class ForwardTask extends Task{
	static HashMap<String, Socket> map=new HashMap<String, Socket>();
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	LogUtil log;
	int  userId;
	private boolean onWork=true;
	
	public ForwardTask(Socket socket){
		this.socket=socket;
		log=new LogUtil();
		
		
		try {
			dis=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			dos=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Task[] taskCore() throws Exception {
		return null;
	}

	@Override
	protected boolean useDb() {
		return false;
	}

	@Override
	protected boolean needExecuteImmediate() {
		return false;
	}

	@Override
	public String info() {
		return null;
	}
	
	/**
	 * 设置线程工作状态，true表示运行，false表示将关闭
	 * @param state
	 */
	private void setWorkState(boolean state){
		onWork=state;
	}
	
	 /**
	  * 任务执行入口
	  */
	public void run() {
		while(onWork){
			//读数据
			try{
				receiveMsg();
			}catch(Exception e){   //发生异常————通常是连接断开，则跳出循环，一个Task任务执行完毕
				e.printStackTrace();
				handExit();
				System.out.println("发生异常————通常是连接断开");
				break;
			}
		}
		
		try{
			if(socket!=null)
				socket.close();
			if(dis!=null)
				dis.close();
			if(dos!=null)
				dos.close();
			
			socket=null;
			dis=null;
			dos=null;
		}catch (IOException e) {
			LogUtil.record("在线用户退出时发生异常");
		}
	}
	
	// 接收消息
		public void receiveMsg() throws IOException {
			// 读取请求类型，登录，注册，更新头像等等
			int requestType = dis.readInt();
			switch (requestType) {
			case Config.REQUEST_SEND_TXT:     //处理“发送文本消息”请求
				handSendText();
				break;
			case Config.REQUEST_SEND_JBEXFriend:     //处理“发送结伴而行”请求
				handJBEXFriend();
				break;
			case Config.REQUEST_SEND_IMG:     //处理“发送图片消息”请求
			    handSendImgOrAudio(Config.RECEIVE_IMG, Config.MESSAGE_TYPE_IMG);
				break;
			case Config.REQUEST_SEND_AUDIO:  //处理“发送语音消息”请求
			    handSendImgOrAudio(Config.RECEIVE_AUDIO, Config.MESSAGE_TYPE_AUDIO);
				break;
			case Config.REQUEST_GET_OFFLINE_MSG:   //处理“获取离线消息”请求
				handGetOfflineMsg();
				break;
			case Config.REQUEST_LOGIN:      //处理“登陆”请求
				handLogin();
				break;
			case Config.REQUEST_EXIT:              //处理“退出程序”请求
				handExit();
				break;
			case -1:
			  handExit();
			   break;
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
	        
//	        Log.i(TAG, "handReceiveData() 接收 img.totalNum="+totalNum);
	    }


	    private void handLogin() {
		// TODO 自动生成的方法存根
	    	try {
	    		userId=dis.readInt();
	    		map.put(String.valueOf(userId), socket);
	    		System.out.println("用户"+userId+"登录成功");
				LogUtil.record("用户"+userId+"登录成功");
	    	} catch (IOException e) {
				e.printStackTrace();
			}
	   }

	    private void handExit() {
			
				String userId=String.valueOf(this.userId);
				
				LogUtil.record("---------------------------------------");
				LogUtil.record("在线用户"+userId+"请求退出登录");
				System.out.println("在线用户"+userId+"请求退出登录");
				//结束线程
				setWorkState(false);
				
				LogUtil.record("用户"+userId+"退出前,共有"+map.size()+"个用户在线");
				System.out.println("用户"+userId+"退出前,共有"+map.size()+"个用户在线");
				//注意这里不是在转发消息，并不需要查询发送退出请求的用户是否在线。只要是同服务器有Socket连接就是在线的
				map.remove(userId);
				LogUtil.record("用户"+userId+"退出后,还有"+map.size()+"个用户在线");
				System.out.println("用户"+userId+"退出后,共有"+map.size()+"个用户在线");
				//dbUtil.close();		//关闭到数据库的连接
				
		}
	    
        //处理结伴而行消息
		private void handJBEXFriend() {
			// TODO 自动生成的方法存根
			try {
				String sendId=dis.readUTF();
				String receiveId=dis.readUTF();
				String jbexinfoID=dis.readUTF();
				System.out.println("接收到客户端"+sendId+"发来的结伴而行请求");
				log.record("------------------------------------------------------------------------");
				log.record("用户"+sendId+" 向用户"+receiveId+"发送结伴而行请求='"+jbexinfoID+"'");
				
				TJbexinfoAction.addJbexSize(jbexinfoID);
				//判断接收者是否在线
				if(map.containsKey(receiveId)){
					Socket socket=map.get(receiveId);
					log.record("服务器同消息发送者"+sendId+"连接的Socket="+map.get(sendId));
					log.record("服务器同消息接收者"+receiveId+"连接的Socket="+socket);
					
					DataOutputStream out=new DataOutputStream(socket.getOutputStream());
					out.writeInt(Config.RECEIVE_JBEXFriend);
					log.record("用户"+receiveId+"在线，可以直接接收到消息，消息已转发给接收者"+receiveId);
					out.flush();
					
				}else{
					//不在线，先把消息暂存在服务器端
			    }
				
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
		//处理文本消息
		public void handSendText(){
			try {
				String sendId=dis.readUTF();
				String receiveId=dis.readUTF();
				String time = dis.readUTF();
				String content=dis.readUTF();
				System.out.println("接收到客户端"+sendId+"发来的消息");
				log.record("------------------------------------------------------------------------");
				log.record("用户"+sendId+" 向用户"+receiveId+"发送文本消息='"+content+"'");
				//判断接收者是否在线
				if(map.containsKey(receiveId)){
					Socket socket=map.get(receiveId);
					log.record("服务器同消息发送者"+sendId+"连接的Socket="+map.get(sendId));
					log.record("服务器同消息接收者"+receiveId+"连接的Socket="+socket);
                    
					DataOutputStream out=new DataOutputStream(socket.getOutputStream());
					out.writeInt(Config.RECEIVE_TEXT);
					out.writeUTF(sendId);
					out.writeUTF(receiveId);
					out.writeUTF(time);
					out.writeUTF(content);
					out.flush();
					
					log.record("用户"+receiveId+"在线，可以直接接收到消息，消息已转发给接收者"+receiveId);
				}else{
					//写入数据库
					Timestamp newtime=Timestamp.valueOf(time);
					TMessagebeanAction.addTMessagebeanByID(sendId, receiveId, newtime, content, Config.MESSAGE_TYPE_TXT);
					//dbUtil.saveMessage(sendId, receiveId, Config.MESSAGE_TYPE_TXT, time, content);
					log.record("用户"+receiveId+"不在线，先把消息暂存在服务器端");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		

		private void handSendImg() {
			try {
				String sendId=dis.readUTF();
				String receiveId=dis.readUTF();
				String time = dis.readUTF();
			
				log.record("用户"+sendId+" 向用户"+receiveId+"发送图片消息'");
				//判断接收者是否在线
				if(map.containsKey(receiveId)){
					Socket socket=map.get(receiveId);
					log.record("服务器同用户"+receiveId+"连接的Socket="+socket);
					DataOutputStream out=new DataOutputStream(socket.getOutputStream());
					out.writeInt(Config.RECEIVE_IMG);
					out.writeUTF(sendId);
					out.writeUTF(receiveId);
					out.writeUTF(time);
					out.flush();
					
					int length=0;
		            int totalNum=0;
		            byte[] buffer=new byte[2048];
		            while((length=dis.readInt())!=0){
		                length=dis.read(buffer, 0, length);
		                totalNum+=length;
		                
		                out.writeInt(length);
		                out.write(buffer, 0, length);
		                out.flush();
		            }
					
					out.writeInt(0);
					out.flush();
					
					System.out.println("img.totalNum="+totalNum);
					
					log.record("用户"+receiveId+"在线，可以直接接收到消息");
				}else{
					//写入数据库
					File file=FileUtil.createFile(sendId, Config.MESSAGE_TYPE_IMG);
					FileOutputStream ou=new FileOutputStream(file);
					
					int length=0;
	                int totalNum=0;
	                byte[] buffer=new byte[2048];
	                while((length=dis.readInt())!=0){
	                    length=dis.read(buffer, 0, length);
	                    totalNum+=length;
	                    ou.write(buffer, 0, length);
	                    ou.flush();
	                }
					ou.close();
					ou=null;
					
					System.out.println("img.totalNum="+totalNum);
					
					//dbUtil.saveMessage(sendId, receiveId, Config.MESSAGE_TYPE_IMG, time, file.getAbsolutePath().replace("\\", "\\\\"));
					log.record("用户"+receiveId+"不在线，先把消息暂存在服务器端");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		private void handSendImgOrAudio(int requestType, int messageType) {
	        try {
	            String sendId=dis.readUTF();
	            String receiveId=dis.readUTF();
	            String time = dis.readUTF();
	            
	            if(messageType==Config.MESSAGE_TYPE_IMG){
	                log.record("用户"+sendId+" 向用户"+receiveId+"发送图片消息'");
	            }else{
	                log.record("用户"+sendId+" 向用户"+receiveId+"发送语音消息'");
	            }
	            
	            //判断接收者是否在线
	            if(map.containsKey(receiveId)){
	                Socket socket=map.get(receiveId);
	                log.record("服务器同用户"+receiveId+"连接的Socket="+socket);
	                DataOutputStream out=new DataOutputStream(socket.getOutputStream());
	                out.writeInt(requestType);
	                out.writeUTF(sendId);
	                out.writeUTF(receiveId);
	                out.writeUTF(time);
	                out.flush();
	                
	                int length=0;
	                int totalNum=0;
	                byte[] buffer=new byte[1024];
	                while((length=dis.readInt())!=0){
	                    length=dis.read(buffer, 0, length);
	                    totalNum+=length;
	                    
	                    out.writeInt(length);
	                    out.write(buffer, 0, length);
	                    out.flush();
	                }
	                
	                out.writeInt(0);
	                out.flush();
	                
	                System.out.println("img.totalNum="+totalNum);
	                
	                log.record("用户"+receiveId+"在线，可以直接接收到消息");
	            }else{
	                //写入数据库
	                File file=FileUtil.createFile(sendId, messageType);
	                FileOutputStream ou=new FileOutputStream(file);
	                
	                int length=0;
	                int totalNum=0;
	                byte[] buffer=new byte[1024];
	                while((length=dis.readInt())!=0){
	                    length=dis.read(buffer, 0, length);
	                    totalNum+=length;
	                    ou.write(buffer, 0, length);
	                    ou.flush();
	                }
	                ou.close();
	                ou=null;
	                
	                System.out.println("img.totalNum="+totalNum);
	                
	               // dbUtil.saveMessage(sendId, receiveId, messageType, time, file.getAbsolutePath().replace("\\", "\\\\"));
	                log.record("用户"+receiveId+"不在线，先把消息暂存在服务器端");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    }
		
		//
		private void handSendAudio() {
			try {
				String sendId=dis.readUTF();
				String receiveId=dis.readUTF();
				String time = dis.readUTF();
				int length=dis.readInt();
				byte[] data=new byte[length];
				int realNum=dis.read(data);
				
				log.record("用户"+sendId+" 向用户"+receiveId+"发送语音消息'");
				//判断接收者是否在线
				if(map.containsKey(receiveId)){
					Socket socket=map.get(receiveId);
					log.record("服务器同用户"+receiveId+"连接的Socket="+socket);
					DataOutputStream out=new DataOutputStream(socket.getOutputStream());
					out.writeInt(Config.RECEIVE_AUDIO);
					out.writeUTF(sendId);
					out.writeUTF(receiveId);
					out.writeUTF(time);
					out.writeInt(realNum);
					out.write(data, 0, realNum);
					out.flush();
					
					log.record("用户"+receiveId+"在线，可以直接接收到消息");
				}else{
					//写入数据库
					File file=FileUtil.createFile(sendId, Config.MESSAGE_TYPE_AUDIO);
					FileOutputStream ou=new FileOutputStream(file);
					ou.write(data, 0, realNum);
					ou.flush();
					ou.close();
					ou=null;
					
					//dbUtil.saveMessage(sendId, receiveId, Config.MESSAGE_TYPE_AUDIO, time, file.getAbsolutePath());
					log.record("用户"+receiveId+"不在线，先把消息暂存在服务器端");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		
		//处理”获取离线消息的请求“
		public void handGetOfflineMsg(){
			
			try {
				String selfId=dis.readUTF();
				List<TMessagebean> list=TMessagebeanAction.getMessageBeanByid(selfId);
				int listSize=list.size();
				
				LogUtil.record("----------------------------------------------");
				LogUtil.record("用户"+selfId+"发来获取离线消息的请求,共找到与他相关的离线消息"+listSize+"条");
				if(list!=null && listSize>0){
					LogUtil.record("因服务器已发送离线消息给接收者，所以可以删除相关的消息");
					TMessagebeanAction.deleteMessageBeanByid(selfId);
					
					TMessagebean msg=null;
					
					dos.writeInt(Config.RESULT_GET_OFFLINE_MSG);
					dos.writeInt(listSize);
					LogUtil.record("总共有条"+listSize+"离线消息");
					for(int i=0; i<listSize; i++){
						msg=list.get(i);
						int type=msg.getType();
						if(type==Config.MESSAGE_TYPE_TXT){
							dos.writeUTF(String.valueOf(msg.getTUserBySendId().getUserId()));
							dos.writeUTF(String.valueOf(msg.getTUserByReceiverId().getUserId()));
							dos.writeInt(msg.getType());
							java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							dos.writeUTF(timeformat.format(msg.getTime()));
							dos.writeUTF(String.valueOf(msg.getMsg()));
							dos.flush();
						}else {
							dos.writeUTF(String.valueOf(msg.getTUserBySendId().getUserId()));
							dos.writeUTF(String.valueOf(msg.getTUserByReceiverId().getUserId()));
							dos.writeInt(msg.getType());
							java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							dos.writeUTF(timeformat.format(msg.getTime()));
							//获取音频文件的字节数组
							File file=new File(msg.getMsg());
							DataInputStream in=new DataInputStream(new FileInputStream(file));
							int length=in.available();
							byte[] data=new byte[length];
							int size=in.read(data);
							dos.writeInt(size);
							dos.write(data, 0, size);
							dos.flush();
							
							in.close();
							in=null;
							
							//删除图片或语音文件
							file.delete();
						}
						
						LogUtil.record("发送第"+(i+1)+"离线条消息");
					}
				}else {
					System.out.println("服务器端没有有关用户"+selfId+"的未发消息");
				}
			} 
			catch (IOException e) {
				LogUtil.record("handGetOfflineMsg 发生异常："+e.toString());
				e.printStackTrace();
			}
			
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
	    }
	}
