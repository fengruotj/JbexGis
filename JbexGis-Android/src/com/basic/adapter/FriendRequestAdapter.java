package com.basic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.myAttentionAdapter.ViewHolder;
import com.basic.model.myAttentionBean;
import com.basic.service.model.FriendRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendRequestAdapter extends BaseAdapter{
    private List<FriendRequest> FriendRequestList;
    private LayoutInflater mInflater;
    
    private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public FriendRequestAdapter(List<FriendRequest> FriendRequestList,
			Context mContext, ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		this.FriendRequestList = FriendRequestList;
		this.imageLoader=imageLoader;
		this.options=option;
		mInflater= LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return FriendRequestList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return FriendRequestList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.friendrequest_item, null);
			viewHolder.frendrequestUsername=(TextView) convertView.findViewById(R.id.frendrequestUsername);
			viewHolder.friendrequestPhoto=(ImageView) convertView.findViewById(R.id.friendrequestPhoto);
			viewHolder.friendrequestSex=(ImageView) convertView.findViewById(R.id.friendrequestSex);
			viewHolder.friendrequestValidationMessage=(TextView) convertView.findViewById(R.id.friendrequestValidationMessage);
			viewHolder.friendrequestTime=(TextView) convertView.findViewById(R.id.friendrequestTime);
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		imageLoader.displayImage(ImageStringUtil.getImageURL(FriendRequestList.get(position).getOwneruser().getPicture()), viewHolder.friendrequestPhoto, options, animateFirstListener);
		
		viewHolder.frendrequestUsername.setText(FriendRequestList.get(position).getOwneruser().getUser_nickname());
		if(FriendRequestList.get(position).getOwneruser().getSex()==1)
		viewHolder.friendrequestSex.setImageResource(R.drawable.man);
		else if(FriendRequestList.get(position).getOwneruser().getSex()==2)
			viewHolder.friendrequestSex.setImageResource(R.drawable.woman);
		else
			viewHolder.friendrequestSex.setImageResource(0);
		
		viewHolder.friendrequestValidationMessage.setText(FriendRequestList.get(position).getValidationmessage());
		viewHolder.friendrequestTime.setText(FriendRequestList.get(position).getRequestime().toLocaleString());
		return convertView;
	}
	
	public void onDateChange(ArrayList<FriendRequest> apk_list) {
		// TODO 自动生成的方法存根
		this.FriendRequestList = apk_list;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		private ImageView  friendrequestPhoto;
		private TextView frendrequestUsername;
		private ImageView friendrequestSex;
		private TextView friendrequestValidationMessage;
		private TextView friendrequestTime;
	}
}
