package com.basic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.FriendRequestAdapter.ViewHolder;
import com.basic.service.model.FriendRequest;
import com.basic.service.model.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class JbexRequestAdapter extends BaseAdapter {
	private List <User> jbexReqeustList;
    private LayoutInflater mInflater;
    
    private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public JbexRequestAdapter(List <User> jbexReqeustList,
			Context mContext, ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		this.jbexReqeustList = jbexReqeustList;
		this.imageLoader=imageLoader;
		this.options=option;
		mInflater= LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return jbexReqeustList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return jbexReqeustList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.jbexrequest_item, null);
			viewHolder.jbexrequestPhoto=(ImageView) convertView.findViewById(R.id.jbexrequestPhoto);
			viewHolder.jbexrequestUsername=(TextView) convertView.findViewById(R.id.jbexrequestUsername);
			viewHolder.jbexrequestSex=(ImageView) convertView.findViewById(R.id.jbexrequestSex);
			viewHolder.jbexrequestValidationMessage=(TextView) convertView.findViewById(R.id.jbexrequestValidationMessage);
			viewHolder.jbexrequestTime=(TextView) convertView.findViewById(R.id.jbexrequestTime);
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		imageLoader.displayImage(ImageStringUtil.getImageURL(jbexReqeustList.get(position).getPicture()), viewHolder.jbexrequestPhoto, options, animateFirstListener);
		
		viewHolder.jbexrequestUsername.setText(jbexReqeustList.get(position).getUser_nickname());
		if(jbexReqeustList.get(position).getSex()==1)
		viewHolder.jbexrequestSex.setImageResource(R.drawable.man);
		else if(jbexReqeustList.get(position).getSex()==2)
			viewHolder.jbexrequestSex.setImageResource(R.drawable.woman);
		else
			viewHolder.jbexrequestSex.setImageResource(0);
		
		viewHolder.jbexrequestValidationMessage.setText("请求结伴");
		return convertView;
	}
	
	public  void onDateChange(List <User> jbexReqeustList ) {
		// TODO 自动生成的方法存根
		this.jbexReqeustList = jbexReqeustList;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		private ImageView  jbexrequestPhoto;
		private TextView jbexrequestUsername;
		private ImageView jbexrequestSex;
		private TextView jbexrequestValidationMessage;
		private TextView jbexrequestTime;
	}
}
