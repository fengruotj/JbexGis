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
import com.basic.model.CollectionsBean;
import com.basic.service.model.JbexInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class CollectionsAdapter extends BaseAdapter{
	private List<JbexInfo> jbexinfoList=new ArrayList<JbexInfo>();
	private LayoutInflater mInflater;
	
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public CollectionsAdapter( List<JbexInfo> jbexinfoList,
			Context mContext,ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		
		this.imageLoader=imageLoader;
		this.options=option;
		this.jbexinfoList = jbexinfoList;
		this.mInflater= LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return jbexinfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return jbexinfoList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.collection_item_layout, null);
			viewHolder.collectionsPhoto=(ImageView) convertView.findViewById(R.id.collectionsPhoto);
			viewHolder.collectionSex=(ImageView)convertView.findViewById(R.id.collectionSex);
			viewHolder.collectionsUsername=(TextView) convertView.findViewById(R.id.collectionsUsername);
			//viewHolder.CollectionsRestTime=(TextView)convertView.findViewById(R.id.CollectionsRestTime);
			viewHolder.CollectionsTime=(TextView)convertView.findViewById(R.id.CollectionsTime);
			viewHolder.collectionsPlace=(TextView)convertView.findViewById(R.id.collectionsPlace);
			viewHolder.collectionsType=(TextView)convertView.findViewById(R.id.collectionsType);
			viewHolder.collectionsTitle=(TextView)convertView.findViewById(R.id.collectionsTitle);
			viewHolder.collectionsDetails=(TextView)convertView.findViewById(R.id.collectionsDetails);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}

		imageLoader.displayImage(ImageStringUtil.getImageURL(jbexinfoList.get(position).getTUser().getPicture()), viewHolder.collectionsPhoto, options, animateFirstListener);
		
		if(jbexinfoList.get(position).getTUser().getSex()==1)
		viewHolder.collectionSex.setImageResource(R.drawable.man);
		else
			if(jbexinfoList.get(position).getTUser().getSex()==2)
			viewHolder.collectionSex.setImageResource(R.drawable.woman);
		
		viewHolder.collectionsUsername.setText(jbexinfoList.get(position).getTUser().getUser_nickname());
		//viewHolder.CollectionsRestTime.setText(mListCollectionsBean.get(position).getCollectionsRestTime());
		viewHolder.CollectionsTime.setText(sdf.format(jbexinfoList.get(position).getTime()));
		viewHolder.collectionsPlace.setText("中国地质大学(武汉)");
		viewHolder.collectionsType.setText(jbexinfoList.get(position).getLabel());
		viewHolder.collectionsTitle.setText(jbexinfoList.get(position).getTitle());
		viewHolder.collectionsDetails.setText(jbexinfoList.get(position).getDetail());

		return convertView;
	}
	
	public void onDateChange(List<JbexInfo> apk_list) {
		// TODO 自动生成的方法存根
		this.jbexinfoList = apk_list;
		this.notifyDataSetChanged();
	}
	
	 class ViewHolder{
		   public ImageView collectionsPhoto;    //收藏的用户名的图片ID号
		   public ImageView collectionSex;        //收藏好友的性别
		   public TextView collectionsUsername;    //收藏好友的姓名
		   //public TextView CollectionsRestTime;     //收藏的剩余时间
		   public TextView CollectionsTime;         //收藏的结伴时间
		   public TextView collectionsPlace;          //收藏的结伴地点
		   public TextView collectionsType;          //收藏结伴的类型
		   public TextView collectionsTitle;          //收藏结伴的标题
		   public TextView collectionsDetails;         //收藏结伴的细节
	   }
	 
}
