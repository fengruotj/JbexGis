package com.basic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;

public class mainActivity_menu_adapter extends BaseAdapter{
    
	
	private Context mContext;
	private LayoutInflater mInflater;
	
	class data{
		public data(String string, int i) {
			name=string;
			picture=i;
		}
		public String name;
		public int picture;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPicture() {
			return picture;
		}
		public void setPicture(int picture) {
			this.picture = picture;
		}
		
	}
	
	private List<data> mlist=new ArrayList<mainActivity_menu_adapter.data>();
	
	public mainActivity_menu_adapter(Context contenxt) {
		mContext=contenxt;
		mInflater = LayoutInflater.from(mContext);
		mlist.add(new data("我的基本信息",R.drawable.actionbar_add_icon));
		mlist.add(new data("我的收藏",R.drawable.actionbar_add_icon));
		mlist.add(new data("我的关注",R.drawable.actionbar_add_icon));
		mlist.add(new data("我的计划",R.drawable.actionbar_add_icon));
		mlist.add(new data("设置",R.drawable.actionbar_add_icon));
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自动生成的方法存根
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO 自动生成的方法存根
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder=null;
		
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.menu_item, null);
			//viewHolder.imageView=(ImageView)convertView.findViewById(R.id.leftmenu_item_image);
			viewHolder.txtView=(TextView) convertView.findViewById(R.id.leftmenu_item_txt);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		viewHolder.imageView.setImageResource(mlist.get(arg0).getPicture());
		viewHolder.txtView.setText(mlist.get(arg0).getName());
		return convertView;
	}
	
   class ViewHolder{
	   public ImageView imageView;
	   public TextView txtView;
   }
}
