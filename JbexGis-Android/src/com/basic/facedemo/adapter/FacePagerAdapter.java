package com.basic.facedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class FacePagerAdapter extends PagerAdapter {
	
	/** 表情页界面集合 */
	List<View> mData = new ArrayList<View>();

	public FacePagerAdapter(List<View> data) {
		this.mData = data;
	}
	
	//ViewPager每次滑动都会调用instantiateItem  初始化Item
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(mData.get(position));
		return mData.get(position);
	}
    
	//毁灭Item
	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mData.get(position));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
