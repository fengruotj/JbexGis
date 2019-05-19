package com.basic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.model.myAttentionBean;
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

public class myAttentionAdapter extends BaseAdapter {

	private List<myAttentionBean> mListmyAttentionBean = null;
	private LayoutInflater mInflater;
	
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public myAttentionAdapter(List<myAttentionBean> mListmyAttentionBean,
			Context mContext, ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		this.imageLoader=imageLoader;
		this.options=option;
		this.mListmyAttentionBean = mListmyAttentionBean;
		mInflater= LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mListmyAttentionBean.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return mListmyAttentionBean.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.myattetnion_item, null);
			viewHolder.myAttentionPhoto=(ImageView) convertView.findViewById(R.id.myAttentionPhoto);
			viewHolder.myAttentionSchoolInfo=(TextView)convertView.findViewById(R.id.myAttentionSchoolInfo);
			viewHolder.myAttentionSex=(ImageView) convertView.findViewById(R.id.myAttentionSex);
			viewHolder.myAttentionUsername=(TextView) convertView.findViewById(R.id.myAttentionUsername);
			viewHolder.myAttentionMajor=(TextView) convertView.findViewById(R.id.myAttentionMajor);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
       
		imageLoader.displayImage(ImageStringUtil.getImageURL(mListmyAttentionBean.get(position).getMyAttentionPhoto()), viewHolder.myAttentionPhoto, options, animateFirstListener);
		
		if(mListmyAttentionBean.get(position).getMyAttentionSex()==1)
		viewHolder.myAttentionSex.setImageResource(R.drawable.man);
		else if(mListmyAttentionBean.get(position).getMyAttentionSex()==2)
			viewHolder.myAttentionSex.setImageResource(R.drawable.woman);
		else 
			viewHolder.myAttentionSex.setImageResource(0);
		
		viewHolder.myAttentionSchoolInfo.setText(mListmyAttentionBean.get(position).getMyAttentionSchoolInfo());
		viewHolder.myAttentionUsername.setText(mListmyAttentionBean.get(position).getMyAttentionUsername());
		viewHolder.myAttentionMajor.setText(mListmyAttentionBean.get(position).getMyAttentionMajor());
		return convertView;
	}
    
	public void onDateChange(ArrayList<myAttentionBean> apk_list) {
		// TODO 自动生成的方法存根
		this.mListmyAttentionBean = apk_list;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		 public ImageView myAttentionPhoto;
		 public TextView  myAttentionUsername;
		 public TextView myAttentionSchoolInfo;
		 public ImageView myAttentionSex;
		 public TextView    myAttentionMajor;
	   }
}
