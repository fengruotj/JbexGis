package com.basic.model;

public class menuItemBean {
	
    String leftmenu_item_txt;
    String rightmenu_item_txt;
    int leftmenu_itemImage;
    int rightmenu_itemImage;
    
    
	public menuItemBean(String leftmenu_item_txt, String rightmenu_item_txt,
			int leftmenu_itemImage, int rightmenu_itemImage) {
		super();
		this.leftmenu_item_txt = leftmenu_item_txt;
		this.rightmenu_item_txt = rightmenu_item_txt;
		this.leftmenu_itemImage = leftmenu_itemImage;
		this.rightmenu_itemImage = rightmenu_itemImage;
	}
	
	
	public String getLeftmenu_item_txt() {
		return leftmenu_item_txt;
	}
	public void setLeftmenu_item_txt(String leftmenu_item_txt) {
		this.leftmenu_item_txt = leftmenu_item_txt;
	}
	public String getRightmenu_item_txt() {
		return rightmenu_item_txt;
	}
	public void setRightmenu_item_txt(String rightmenu_item_txt) {
		this.rightmenu_item_txt = rightmenu_item_txt;
	}
	public int getLeftmenu_itemImage() {
		return leftmenu_itemImage;
	}
	public void setLeftmenu_itemImage(int leftmenu_itemImage) {
		this.leftmenu_itemImage = leftmenu_itemImage;
	}
	public int getRightmenu_itemImage() {
		return rightmenu_itemImage;
	}
	public void setRightmenu_itemImage(int rightmenu_itemImage) {
		this.rightmenu_itemImage = rightmenu_itemImage;
	}
    
    
}
