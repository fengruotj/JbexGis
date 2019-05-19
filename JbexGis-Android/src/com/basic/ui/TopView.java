package com.basic.ui;

import com.basic.Activities.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopView extends LinearLayout{
	
	private Context mContext = null;
	private ImageView mImageView = null;
	private TextView mTextView = null;
	private TextView Autograph_text=null;
	

	public TopView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View parentView = inflater.inflate(R.layout.top_view_layout, this, true);
		mImageView = (ImageView)findViewById(R.id.topView_image);
		mTextView = (TextView)findViewById(R.id.topView_text);
		Autograph_text=(TextView) findViewById(R.id.Autograph_text);
	}
	
	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public ImageView getmImageView() {
		return mImageView;
	}

	public void setmImageView(ImageView mImageView) {
		this.mImageView = mImageView;
	}

	public TextView getmTextView() {
		return mTextView;
	}

	public void setmTextView(TextView mTextView) {
		this.mTextView = mTextView;
	}

	public TextView getAutograph_text() {
		return Autograph_text;
	}

	public void setAutograph_text(TextView autograph_text) {
		Autograph_text = autograph_text;
	}

	public void setImage(int id){
		if(mImageView != null){
			mImageView.setImageResource(id);
		}
	}

	public void setText(String s){
		if(mTextView != null){
			mTextView.setText(s);
		}
	}
	
   public void setAutographText(String s){
	   if(Autograph_text != null){
		   Autograph_text.setText(s);
		}
   }
   
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
