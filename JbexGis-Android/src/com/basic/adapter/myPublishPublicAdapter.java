package com.basic.adapter;

import java.util.List;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.myPublishDynamicAdapter.ViewHolder;
import com.basic.service.model.DynamicInfo;
import com.basic.service.model.PublicInfo;
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

public class myPublishPublicAdapter extends BaseAdapter{
	private List <PublicInfo> MyPublicInfoList;
	private LayoutInflater mInflater;
	
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public myPublishPublicAdapter( List <PublicInfo> MyPublicInfoList,
			Context mContext, ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		this.imageLoader=imageLoader;
		this.options=option;
		this.MyPublicInfoList = MyPublicInfoList;
		mInflater= LayoutInflater.from(mContext);
	}
	
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return MyPublicInfoList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return MyPublicInfoList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}
    
	public void onDateChange( List <PublicInfo>apk_list) {
		// TODO 自动生成的方法存根
		this.MyPublicInfoList = apk_list;
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
			convertView= mInflater.inflate(R.layout.mypublishpublic_item_layout, null);
			viewHolder.mypublishpublicPhoto=(ImageView) convertView.findViewById(R.id.mypublishpublicPhoto);
			viewHolder.mypublishpublicUsername=(TextView)convertView.findViewById(R.id.mypublishpublicUsername);
			viewHolder.mypublicjbexSex=(ImageView) convertView.findViewById(R.id.mypublicjbexSex);
			viewHolder.mypublishpublicTime=(TextView) convertView.findViewById(R.id.mypublishpublicTime);
			viewHolder.mypublishpublicPlace=(TextView) convertView.findViewById(R.id.mypublishpublicPlace);
			viewHolder.mypublishpublicType=(TextView) convertView.findViewById(R.id.mypublishpublicType);
			viewHolder.mypublishpublicTitle=(TextView) convertView.findViewById(R.id.mypublishpublicTitle);
			viewHolder.mypublishpublicDetails=(TextView) convertView.findViewById(R.id.mypublishpublicDetails);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		PublicInfo publicinfo=MyPublicInfoList.get(position);
		imageLoader.displayImage(ImageStringUtil.getImageURL(publicinfo.getTUser().getPicture()), viewHolder.mypublishpublicPhoto, options, animateFirstListener);
		
		viewHolder.mypublishpublicUsername.setText(publicinfo.getTUser().getUser_nickname());

		if(publicinfo.getTUser().getSex()==1)
			viewHolder.mypublicjbexSex.setImageResource(R.drawable.man);
		else if(publicinfo.getTUser().getSex()==2)
			viewHolder.mypublicjbexSex.setImageResource(R.drawable.woman);
		else 
			viewHolder.mypublicjbexSex.setImageResource(0);
		
		viewHolder.mypublishpublicTime.setText(TimeUtil.getRelativeTime(sdf.format(publicinfo.getTime())));
		viewHolder.mypublishpublicPlace.setText("中国地质大学");
		viewHolder.mypublishpublicType.setText(publicinfo.getLabel());
		viewHolder.mypublishpublicTitle.setText(publicinfo.getTitle());
		viewHolder.mypublishpublicDetails.setText(publicinfo.getDetail());
		return convertView;
	}
    
	class ViewHolder{
		 public ImageView mypublishpublicPhoto;
		 public TextView  mypublishpublicUsername;
		 public ImageView mypublicjbexSex;
		 public TextView    mypublishpublicTime;
		 public TextView mypublishpublicPlace;
		 public TextView    mypublishpublicType;
		 public TextView    mypublishpublicTitle;
		 public TextView    mypublishpublicDetails;
	   }
}
