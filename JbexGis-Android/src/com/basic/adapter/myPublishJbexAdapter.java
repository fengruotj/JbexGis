package com.basic.adapter;

import java.util.ArrayList;
import java.util.List;

import org.other.ui.DraggableFlagView;

import com.basic.Activities.R;
import com.basic.ImageLoad.AnimateFirstDisplayListener;
import com.basic.ImageLoad.ImageStringUtil;
import com.basic.adapter.myAttentionAdapter.ViewHolder;
import com.basic.model.myAttentionBean;
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
import android.widget.TextView;

public class myPublishJbexAdapter extends BaseAdapter{
	private List <MyJbexRequest> MyJbexRequestList = null;
	private LayoutInflater mInflater;
	
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	public myPublishJbexAdapter(List <MyJbexRequest> mListmyJbexRequestList,
			Context mContext, ImageLoader imageLoader,DisplayImageOptions option) {
		super();
		this.imageLoader=imageLoader;
		this.options=option;
		this.MyJbexRequestList = mListmyJbexRequestList;
		mInflater= LayoutInflater.from(mContext);
	}
	
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return MyJbexRequestList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return MyJbexRequestList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}
    
	public void onDateChange(List <MyJbexRequest> apk_list) {
		// TODO 自动生成的方法存根
		this.MyJbexRequestList = apk_list;
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
			convertView= mInflater.inflate(R.layout.mypublishjbex_item_layout, null);
			viewHolder.mypublishjbexPhoto=(ImageView) convertView.findViewById(R.id.mypublishjbexPhoto);
			viewHolder.mypublishjbexUsername=(TextView)convertView.findViewById(R.id.mypublishjbexUsername);
			viewHolder.mypublishjbexSex=(ImageView) convertView.findViewById(R.id.mypublishjbexSex);
			//viewHolder.mypublishjbexRestTime=(TextView) convertView.findViewById(R.id.mypublishjbexRestTime);
			viewHolder.mypublishjbexTime=(TextView) convertView.findViewById(R.id.mypublishjbexTime);
			viewHolder.mypublishjbexPlace=(TextView) convertView.findViewById(R.id.mypublishjbexPlace);
			viewHolder.mypublishjbexType=(TextView) convertView.findViewById(R.id.mypublishjbexType);
			viewHolder.mypublishjbexTitle=(TextView) convertView.findViewById(R.id.mypublishjbexTitle);
			viewHolder.mypublishjbexDetails=(TextView) convertView.findViewById(R.id.mypublishjbexDetails);
			viewHolder.draggableFlagView=(DraggableFlagView) convertView.findViewById(R.id.dragFlag);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
       
		imageLoader.displayImage(ImageStringUtil.getImageURL(MyJbexRequestList.get(position).getJbexInfo().getTUser().getPicture()), viewHolder.mypublishjbexPhoto, options, animateFirstListener);
		
		if(MyJbexRequestList.get(position).getJbexInfo().getTUser().getSex()==1)
		viewHolder.mypublishjbexSex.setImageResource(R.drawable.man);
		else if(MyJbexRequestList.get(position).getJbexInfo().getTUser().getSex()==2)
			viewHolder.mypublishjbexSex.setImageResource(R.drawable.woman);
		else 
			viewHolder.mypublishjbexSex.setImageResource(0);
		viewHolder.mypublishjbexUsername.setText(MyJbexRequestList.get(position).getJbexInfo().getTUser().getUser_nickname());
		//viewHolder.mypublishjbexRestTime.setText(TimeUtil.getRestTime(sdf.format(MyJbexRequestList.get(position).getJbexInfo().getTime())));
		viewHolder.mypublishjbexTime.setText(sdf.format(MyJbexRequestList.get(position).getJbexInfo().getTime()));
		viewHolder.mypublishjbexPlace.setText("中国地质大学(武汉)");
		viewHolder.mypublishjbexType.setText(MyJbexRequestList.get(position).getJbexInfo().getLabel());
		viewHolder.mypublishjbexTitle.setText(MyJbexRequestList.get(position).getJbexInfo().getTitle());
		viewHolder.mypublishjbexDetails.setText(MyJbexRequestList.get(position).getJbexInfo().getDetail());
		
		int num=MyJbexRequestList.get(position).getJbexuserList().size();
		if(num!=0)
		viewHolder.draggableFlagView.setText(String.valueOf(num));
		else
			viewHolder.draggableFlagView.setVisibility(View.GONE);
		return convertView;
	}
    
	class ViewHolder{
		 public ImageView mypublishjbexPhoto;
		 public TextView  mypublishjbexUsername;
		 public ImageView mypublishjbexSex;
		// public TextView mypublishjbexRestTime;
		 public TextView    mypublishjbexTime;
		 public TextView    mypublishjbexPlace;
		 public TextView    mypublishjbexType;
		 public TextView    mypublishjbexTitle;
		 public TextView    mypublishjbexDetails;
		 private DraggableFlagView draggableFlagView;
	   }
}
