package com.basic.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewHeightBaseOnChildren {
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// TODO 自动生成的方法存根
		// 获取ListView对应的Adapter   
		ListAdapter listAdapter = listView.getAdapter();   
		if (listAdapter == null) {   
			return;   
		}   

		int totalHeight = 0;   
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
			// listAdapter.getCount()返回数据项的数目   
			View listItem = listAdapter.getView(i, null, listView);   
			// 计算子项View 的宽高   
			listItem.measure(0, 0);    
			// 统计所有子项的总高度   
			totalHeight += listItem.getMeasuredHeight();    
		}   

		ViewGroup.LayoutParams params = listView.getLayoutParams();   
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
		// listView.getDividerHeight()获取子项间分隔符占用的高度   
		// params.height最后得到整个ListView完整显示需要的高度   
		listView.setLayoutParams(params);   
	}   
	
	public static void setListViewHeight(ExpandableListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		int totalHeight = 0;
		int count = listAdapter.getCount();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
}
