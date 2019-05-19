package com.mapgis.model;

import android.graphics.Bitmap;

import com.zondy.mapgis.android.annotation.Annotation;
import com.zondy.mapgis.geometry.Dot;

@SuppressWarnings("serial")
public class DynamciInfoAnnotation extends Annotation{
    
	private String JBuserPicture;
	private int mdynamciuserifoID;
	private int which;
	
	
	public int getWhich() {
		return which;
	}

	public void setWhich(int which) {
		this.which = which;
	}

	public String getJBuserPicture() {
		return JBuserPicture;
	}

	public void setJBuserPicture(String jBuserPicture) {
		JBuserPicture = jBuserPicture;
	}


	public DynamciInfoAnnotation(long arg0) {
		super(arg0);
	}

	public DynamciInfoAnnotation(String arg0, String arg1, Dot arg2, Bitmap arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO 自动生成的构造函数存根
	}

	public DynamciInfoAnnotation(String arg0, String arg1, String arg2,
			Dot arg3, Bitmap arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
		// TODO 自动生成的构造函数存根
	}

	public int getMdynamciuserifoID() {
		return mdynamciuserifoID;
	}

	public void setMdynamciuserifoID(int mdynamciuserifoID) {
		this.mdynamciuserifoID = mdynamciuserifoID;
	}
    
}
