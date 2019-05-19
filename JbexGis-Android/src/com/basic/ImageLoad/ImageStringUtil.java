package com.basic.ImageLoad;

import com.basic.connectservice.HttpUtil;

public class ImageStringUtil {
	
      public static String getImageURL(String url){
    	  String URL=HttpUtil.BASE_URL+"userimages/"+url;
    	  return URL;
      }
      
      public static String getPublicImageURL(String url){
    	  return "";
      }
      
      public static String getImageURLByjbex(String url){
    	  String URL=HttpUtil.BASE_URL+"jbexinfoimages/"+url;
    	  return URL;
      }
      
      public static String getImageURLBydynamic(String url){
    	  String URL=HttpUtil.BASE_URL+"dynamicinfoimages/"+url;
    	  return URL;
      }
}
