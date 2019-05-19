package com.basic.facedemo.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.model.ChatMsgEntity;
import com.basic.service.model.User;
import com.basic.util.FaceConversionUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ChatMsgAdapter extends BaseAdapter {
    
	//聊天消息的适配器
	
	//首先定义聊天消息是  COM收到  TO  发送
	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
    private User owneruser;
    private User frienduser;
    
    private ImageLoader imageLoader;
  	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
  	private DisplayImageOptions options;
  	
	//数据ChatMsgEntity模型的链表
	private List<ChatMsgEntity> coll;
	
	//布局控制器
	private LayoutInflater mInflater;
	private Context context;
	public ChatMsgAdapter(Context context, List<ChatMsgEntity> coll) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.context = context;
		initDisplayOption();
	}

	private void initDisplayOption() {
		// TODO 自动生成的方法存根
		options=ImageOptions.initDisplayOptions();
		imageLoader = ImageLoader.getInstance();
	}

	public User getOwneruser() {
		return owneruser;
	}

	public void setOwneruser(User owneruser) {
		this.owneruser = owneruser;
	}

	public User getFrienduser() {
		return frienduser;
	}

	public void setFrienduser(User frienduser) {
		this.frienduser = frienduser;
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}

	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
      
		//返回聊天消息的ITem的View 
		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}
          
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.isComMsg = isComMsg;
			viewHolder.userimage=(ImageView) convertView.findViewById(R.id.iv_userhead);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
    
		//给每个布局文件的各个组件添加数据
		viewHolder.tvSendTime.setText(entity.getDate());
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, entity.getText());
		viewHolder.tvContent.setText(spannableString);
		if (isComMsg) 
			imageLoader.displayImage(ImageStringUtil.getImageURL(frienduser.getPicture()), viewHolder.userimage, options, animateFirstListener);
		else
			imageLoader.displayImage(ImageStringUtil.getImageURL(owneruser.getPicture()), viewHolder.userimage, options, animateFirstListener);
		return convertView;
	}

	class ViewHolder {
		public ImageView userimage;
		public TextView tvSendTime;
		public TextView tvContent;
		public boolean isComMsg = true;
	}

}
