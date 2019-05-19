package com.basic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;
import com.basic.model.menuItemBean;

import android.R.color;
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
public class ChooseSchoolAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
    private List<menuItemBean> mlist=new ArrayList<menuItemBean>();
    private String s;
	
    public ChooseSchoolAdapter(Context contenxt,List<menuItemBean> list,String school) {
		mContext=contenxt;
		mlist=list;
		
		mInflater = LayoutInflater.from(mContext);
		s=school;
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



		if(mlist.get(arg0).getLeftmenu_item_txt().equals(s)==true){
			viewHolder.rightmenu_image.setImageResource(R.drawable.right_btn);
			viewHolder.leftmenu_item_txt.setTextColor(Color.RED);
		}else
		{
			viewHolder.rightmenu_image.setImageResource(mlist.get(arg0).getRightmenu_itemImage());
			viewHolder.leftmenu_item_txt.setTextColor(Color.BLACK);
		}


		if(mlist.get(arg0).getLeftmenu_item_txt().equals("")==false){
			viewHolder.leftmenu_item_txt.setText(mlist.get(arg0).getLeftmenu_item_txt());
		}

		if(mlist.get(arg0).getRightmenu_item_txt().equals("")==false){
			viewHolder.right_menu_txt.setText(mlist.get(arg0).getRightmenu_item_txt());
		}


		return convertView;
		
	}
     
	class ViewHolder{
		   public ImageView leftmenu_image;
		   public ImageView rightmenu_image;
		   public TextView  leftmenu_item_txt;
		   public TextView right_menu_txt;
	   }
}
