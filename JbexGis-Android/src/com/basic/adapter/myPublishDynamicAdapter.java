package com.basic.adapter;

import java.util.List;

import org.other.ui.DraggableFlagView;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageOptions;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.myPublishJbexAdapter.ViewHolder;
import com.basic.service.model.DynamicInfo;
import com.basic.service.model.MyJbexRequest;
import com.message.net.TimeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class myPublishDynamicAdapter  extends BaseAdapter{
	private List <DynamicInfo> MyDynamicInfoList=null;
	private LayoutInflater mInflater;
	
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public myPublishDynamicAdapter(List <DynamicInfo> MyDynamicInfoList,
			Context mContext, ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		this.imageLoader=imageLoader;
		this.options=option;
		this.MyDynamicInfoList = MyDynamicInfoList;
		mInflater= LayoutInflater.from(mContext);
	}
	
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return MyDynamicInfoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return MyDynamicInfoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}
    
	public void onDateChange( List <DynamicInfo>apk_list) {
		// TODO 自动生成的方法存根
		this.MyDynamicInfoList = apk_list;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.mypublishdynamic_item_layout, null);
			viewHolder.userimage=(ImageView) convertView.findViewById(R.id.userimage);
			viewHolder.usertime=(TextView)convertView.findViewById(R.id.usertime);
			viewHolder.username=(TextView) convertView.findViewById(R.id.username);
			viewHolder.dynamicontent=(TextView) convertView.findViewById(R.id.dynamicontent);
			viewHolder.dynamicinfo1=(ImageView) convertView.findViewById(R.id.dynamicinfo1);
			viewHolder.dynamicinfo2=(ImageView) convertView.findViewById(R.id.dynamicinfo2);
			viewHolder.dynamicpicture=(LinearLayout) convertView.findViewById(R.id.dynamicpicture);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		DynamicInfo dyanmicinfo=MyDynamicInfoList.get(position);
		imageLoader.displayImage(ImageStringUtil.getImageURL(dyanmicinfo.getTUser().getPicture()), viewHolder.userimage, ImageOptions.initDisplayOptions(), animateFirstListener);
		
		viewHolder.usertime.setText(TimeUtil.getRelativeTime(sdf.format(dyanmicinfo.getTime())));
		
		viewHolder.username.setText(dyanmicinfo.getTUser().getUser_nickname());
		viewHolder.dynamicontent.setText(dyanmicinfo.getDetail());
		
		if(dyanmicinfo.getPicture1().equals("null")&&dyanmicinfo.getPicture2().equals("null")){
			viewHolder.dynamicpicture.setVisibility(View.GONE);
		}
		else {
			 if(dyanmicinfo.getPicture1().equals("null")){
				 viewHolder.dynamicinfo1.setVisibility(View.GONE);
			 }
			 else{
				 imageLoader.displayImage(ImageStringUtil.getImageURLBydynamic(dyanmicinfo.getPicture1()), viewHolder.dynamicinfo1, options, animateFirstListener);
			 }
			 
			 if(dyanmicinfo.getPicture2().equals("null")){
				 viewHolder.dynamicinfo2.setVisibility(View.GONE);
			 }
			 else{
				 imageLoader.displayImage(ImageStringUtil.getImageURLBydynamic(dyanmicinfo.getPicture2()), viewHolder.dynamicinfo2, options, animateFirstListener);
			 }
		}
		return convertView;
	}
    
	class ViewHolder{
		 public ImageView userimage;
		 public TextView  usertime;
		 public TextView username;
		 public TextView    dynamicontent;
		 public ImageView    dynamicinfo1;
		 public ImageView    dynamicinfo2;
		 public LinearLayout    dynamicpicture;
	   }
}
