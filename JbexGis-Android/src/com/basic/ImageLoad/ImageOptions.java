package com.basic.ImageLoad;

import java.io.File;

import android.util.Log;

import com.basic.Activities.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ImageOptions {
     public static DisplayImageOptions initDisplayOptions(){
    	 DisplayImageOptions options;
    	 options = new DisplayImageOptions.Builder()
 		//.showStubImage(R.drawable.ic_stub)	//设置正在加载图片
 		.showImageOnLoading(R.drawable.ic_stub) //1.8.7新增
 		.showImageForEmptyUri(R.drawable.ic_empty)	
 		.showImageOnFail(R.drawable.ic_error)	//设置加载失败图片
 		.cacheInMemory(true)
 		.cacheOnDisk(true)
 		.displayer(new RoundedBitmapDisplayer(360))	//设置图片角度,0为方形，360为圆角
 		.build();
        return options;
     }
     
     public static DisplayImageOptions initDisplayOptionsRange(){
    	 DisplayImageOptions options;
    	 options = new DisplayImageOptions.Builder()
 		//.showStubImage(R.drawable.ic_stub)	//设置正在加载图片
 		.showImageOnLoading(R.drawable.ic_stub) //1.8.7新增
 		.showImageForEmptyUri(R.drawable.ic_empty)	
 		.showImageOnFail(R.drawable.ic_error)	//设置加载失败图片
 		.cacheInMemory(true)
 		.cacheOnDisk(true)
 		.displayer(new RoundedBitmapDisplayer(20))	//设置图片角度,0为方形，360为圆角
 		.build();
        return options;
     }
}
