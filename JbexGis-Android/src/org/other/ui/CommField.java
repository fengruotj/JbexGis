package org.other.ui;
import com.zondy.mapgis.geometry.Dot;

import android.view.View.OnClickListener;

public class CommField {
	private String strName;
	private Object strValue;
	private boolean isMore = false;
	private Dot graphicPoint;

	private OnClickListener littleImgClickListener = null;
	private int userMoreImg = 0;
	private int userBigImg = 0;
	private int id = 0;

	private float nameTextSize = 0;

	private String strLittleName = "";

	public CommField() {
		this.strName = "";
		this.strValue = "";
	}


	public CommField(String strName, Object fieldValue) {
		this.strName = strName;
		this.strValue = fieldValue;
	}
	
	public CommField(String strName, String strValue, boolean isMore) {
		this.strName = strName;
		this.strValue = strValue;
		this.isMore = isMore;
	}
	
	public CommField(String strName, String strValue, Dot graphicPoint) {
		this.strName = strName;
		this.strValue = strValue;
		this.graphicPoint = graphicPoint;
	}
	
	public Dot getGraphicPoint(){
		return graphicPoint;
	}

	public void setGraphicPoint(Dot graphicPoint){
		this.graphicPoint = graphicPoint;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public Object getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	
	public boolean isMore() {
		return isMore;
	}

	public void setMore(boolean isMore) {
		this.isMore = isMore;
	}

	public OnClickListener getLittleImgClickListener() {
		return littleImgClickListener;
	}

	public void setLittleImgClickListener(OnClickListener littleImgClickListener) {
		this.littleImgClickListener = littleImgClickListener;
	}

	public int getUserMoreImg() {
		return userMoreImg;
	}

	public void setUserMoreImg(int userMoreImg) {
		this.userMoreImg = userMoreImg;
	}

	public int getUserBigImg() {
		return userBigImg;
	}

	public void setUserBigImg(int userBigImg) {
		this.userBigImg = userBigImg;
	}

	public float getNameTextSize() {
		return nameTextSize;
	}

	public void setNameTextSize(float nameTextSize) {
		this.nameTextSize = nameTextSize;
	}

	public String getStrLittleName() {
		return strLittleName;
	}

	public void setStrLittleName(String strLittleName) {
		this.strLittleName = strLittleName;
	}
}
