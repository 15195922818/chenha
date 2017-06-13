package com.ai.web.entity;

public class ShowPicture {
   private int showIndex;
   private String pictureUrl;
   private boolean isCorrect;

public int getShowIndex() {
	return showIndex;
}
public void setShowIndex(int showIndex) {
	this.showIndex = showIndex;
}
public String getPictureUrl() {
	return pictureUrl;
}
public void setPictureUrl(String pictureUrl) {
	this.pictureUrl = pictureUrl;
}
public boolean isCorrect() {
	return isCorrect;
}
public void setCorrect(boolean isCorrect) {
	this.isCorrect = isCorrect;
}
   
}
