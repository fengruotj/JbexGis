package com.basic.Application;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.message.net.Communication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends Application {
	
	private static Communication con;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		con.newInstance();
//		if (Util.DEVELOPER_MODE
//				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//					.detectAll().penaltyDialog().build());
//			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//					.detectAll().penaltyDeath().build());
//		}

		super.onCreate();

		initImageLoader(getApplicationContext());
	}

	public static Communication getCon() {
		return con;
	}

	public static void setCon(Communication con) {
		MyApplication.con = con;
	}

	public static void initImageLoader(Context context) {
		// Create default options which will be used for every
		// displayImage(...) call if no options will be passed to this method
		
		//缓存目录
				//有SD卡 path=/sdcard/Android/data/com.example.universalimageloadertest/cache
				//无SD卡 path=/data/data/com.example.universalimageloadertest/cache
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "JBEX/Cache/images");   
       // File cacheDir=new File(path);
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		        .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)// default
				.threadPriority(Thread.NORM_PRIORITY - 1)// default
				.denyCacheImageMultipleSizesInMemory()  
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密  
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值  
                .diskCacheSize(50 * 1024 * 1024)  // SD卡缓存的最大值 
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径 
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间    
				.defaultDisplayImageOptions(defaultOptions)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}