 package com.basic.facedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.basic.Activities.R;
import com.basic.model.MessagePlusEndity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessagePlusAdapter extends BaseAdapter {

	Context mContext;
	List<MessagePlusEndity> mData = new ArrayList<MessagePlusEndity>();
	LayoutInflater inflater;
    
	// 构造函数
	public MessagePlusAdapter(Context context) {
		mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 构造函数
	public MessagePlusAdapter(Context context,
			List<MessagePlusEndity> mdata) {
		mContext = context;
		mData = mdata;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 添加数据
	public void addItem(MessagePlusEndity item) {
		mData.add(item);
	}

	// 移除数据\
	public void removeItem(int position) {
		mData.remove(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewFolder folder;
		MessagePlusEndity item = mData.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.message_function_layout, null);
			folder = new ViewFolder();
			folder.imagebtn = (ImageView) convertView
					.findViewById(R.id.messageFunctionBtn);
			folder.txtView = (TextView) convertView
					.findViewById(R.id.messageFunctionName);
			convertView.setTag(folder);
		} else
			folder = (ViewFolder) convertView.getTag();
		folder.imagebtn.setBackgroundResource(item.icon);
		folder.txtView.setText(item.name);
		folder.imagebtn.setTag((Integer)position);

//		folder.imagebtn.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					folder.imagebtn.setImageResource(R.drawable.chat_tool_mask);
//					return false;
//				} else {
//					folder.imagebtn.setImageBitmap(null);
//					return true;
//				}
//			}
//		});
		return convertView;
	}

	private static class ViewFolder {
		ImageView imagebtn;
		TextView txtView;
	}
}
