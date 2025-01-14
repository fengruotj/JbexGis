package com.basic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;
import com.basic.model.MessageBean;
import com.basic.model.menuItemBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MenuItemAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
    private List<menuItemBean> mlist=new ArrayList<menuItemBean>();
	
    public MenuItemAdapter(Context contenxt,List<menuItemBean> list) {
		mContext=contenxt;
		mlist=list;
		
		mInflater = LayoutInflater.from(mContext);
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
			convertView= mInflater.inflate(R.layout.menu_item, null);
			viewHolder=new ViewHolder();
			convertView= mInflater.inflate(R.layout.menu_item, null);
			//viewHolder.imageView=(ImageView)convertView.findViewById(R.id.leftmenu_item_image);
			viewHolder.leftmenu_item_txt=(TextView) convertView.findViewById(R.id.leftmenu_item_txt);
			viewHolder.right_menu_txt=(TextView) convertView.findViewById(R.id.right_menu_txt);
			viewHolder.leftmenu_image=(ImageView) convertView.findViewById(R.id.leftmenu_image);
			viewHolder.rightmenu_image=(ImageView) convertView.findViewById(R.id.rightmenu_image);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		if((mlist.get(arg0).getLeftmenu_itemImage())!=0){
		viewHolder.leftmenu_image.setImageResource(mlist.get(arg0).getLeftmenu_itemImage());
		}
		
		if((mlist.get(arg0).getRightmenu_itemImage())!=0){
			viewHolder.rightmenu_image.setImageResource(mlist.get(arg0).getRightmenu_itemImage());
			}
		
		if(mlist.get(arg0).getLeftmenu_item_txt().equals("")==false){
		viewHolder.leftmenu_item_txt.setText(mlist.get(arg0).getLeftmenu_item_txt());
		viewHolder.leftmenu_item_txt.setTextColor(Color.BLACK);
		}
		
		if(mlist.get(arg0).getRightmenu_item_txt().equals("")==false){
			viewHolder.right_menu_txt.setText(mlist.get(arg0).getRightmenu_item_txt());
			}
		return convertView;
		
	}
	
	public void onDateChange(List<menuItemBean> mList2) {
		// TODO 自动生成的方法存根
		this.mlist = mList2;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		   public ImageView leftmenu_image;
		   public ImageView rightmenu_image;
		   public TextView  leftmenu_item_txt;
		   public TextView right_menu_txt;
	   }
}
