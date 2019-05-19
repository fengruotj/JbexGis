package com.basic.ui;

import com.basic.Activities.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class menuItem extends LinearLayout{

     private Context mContext = null;
     private ImageView leftmenu_image=null;
     private ImageView rightmenu_image=null;
	 private TextView leftmenu_item_txt=null;
	 private TextView rightmenu_item_txt=null;
	 
	public Context getmContext() {
		return mContext;
	}


	public ImageView getLeftmenu_image() {
		return leftmenu_image;
	}


	public ImageView getRightmenu_image() {
		return rightmenu_image;
	}


	public TextView getLeftmenu_item_txt() {
		return leftmenu_item_txt;
	}


	public TextView getRightmenu_item_txt() {
		return rightmenu_item_txt;
	}


	public menuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		mContext=context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View parentView = inflater.inflate(R.layout.menu_item, this, true);
		leftmenu_image=(ImageView) findViewById(R.id.leftmenu_image);
		rightmenu_image=(ImageView) findViewById(R.id.rightmenu_image);
		leftmenu_item_txt=(TextView) findViewById(R.id.leftmenu_item_txt);
		rightmenu_item_txt=(TextView) findViewById(R.id.right_menu_txt);
		
	}

	
	public menuItem(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}
	
	public void setleftmenu_image(int id){
		if(leftmenu_image != null){
			leftmenu_image.setImageResource(id);
		}
	}
	
	public void setrightmenu_image(int id){
		if(rightmenu_image != null){
			rightmenu_image.setImageResource(id);
		}
	}
	
	public void setleftmenu_item_txt(String s){
		leftmenu_item_txt.setText(s);
	}
	
	public void setrightmenu_item_txt(String s){
		rightmenu_item_txt.setText(s);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}

}
