package com.ai.web.entity;

import java.util.Map;

public class PkQuestion {
  private int responseNum;
  private int questionIndex;
  private Map<String,Object> showPictueMap;
public int getResponseNum() {
	return responseNum;
}
public void setResponseNum(int responseNum) {
	this.responseNum = responseNum;
}
public int getQuestionIndex() {
	return questionIndex;
}
public void setQuestionIndex(int questionIndex) {
	this.questionIndex = questionIndex;
}
public Map<String, Object> getShowPictueMap() {
	return showPictueMap;
}
public void setShowPictueMap(Map<String, Object> showPictueMap) {
	this.showPictueMap = showPictueMap;
}

  
}
