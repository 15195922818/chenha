package com.ai.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.web.entity.GameRecord;
import com.ai.web.entity.PkRecord;
import com.ai.web.entity.Question;
import com.ai.web.entity.User;

import com.ai.web.mapper.QuestionMapper;
@Repository
public class QuestionDao {
	@Autowired
    private QuestionMapper questionMapper;
	
	public int getQuestionNumber() {
		return questionMapper.countQuestionNumber();
	}
	public Question getQuestionById(String questionId) {
	
		return questionMapper.getQuestionById(questionId);
	}
	public String getMaxOfWeek(Map<String, String> paramMap) {
		
		return questionMapper.getMaxOfWeek(paramMap);
	}
	public void updatePreviousMax(Map<String, String> paramMap) {
		
		questionMapper.updatePreviousMax(paramMap);
	}
	public void insertGameRecord(GameRecord gameRecord) {
		questionMapper.insertGameRecord(gameRecord);
		
	}
	public void insertUser(User user) {
		questionMapper.insertUser(user);
		
	}
	public User getUserById(String personId) {
		
		return questionMapper.getUserById(personId);
	}
	public void updateUser(User user) {
	
		questionMapper.updateUser(user);
	}
	public void insertLog(Map<String,Object> logMap){
		
		questionMapper.insertLog(logMap);
	}
	public List<PkRecord> getPkList(String myId) {
		
		return questionMapper.getPkList(myId);
	}
	public List<User> getPkUser(String userId) {
		return questionMapper.getPkUser(userId);
	}
	public void insertPkRecord(Map<String, String> paramMap) {
		questionMapper.insertPkRecord(paramMap);
		
	}
	public void updateUserToOne(String userId) {
		questionMapper.updateUserToOne(userId);
	}
	public void updateUserToTwo(String personId) {
		questionMapper.updateUserToTwo(personId);
		
	}
	public void updateReadyNum(String pkId) {
		questionMapper.updateReadyNum(pkId);
		
	}
	public PkRecord getPkRecordById(String pkId) {
		
		return questionMapper.getPkRecordById(pkId);
	}
	public void updatePkStatus(String pkId) {
		questionMapper.updatePkStatus(pkId);
		
	}
	public void deletePkRecoed(String pkId) {
		questionMapper.deletePkRecoed(pkId);
		
	}
	public void insertPkGameRecord(GameRecord gameRecord) {
		
		questionMapper.insertPkGameRecord(gameRecord);
	}
	public void updatePkRecord(PkRecord pkRecord) {
		questionMapper.updatePkRecord(pkRecord);
		
	}
	public void updatePkToOne(Map<String, String> idsMap) {
		questionMapper.updatePkToOne(idsMap);
		
	}
	public void updatePkTimes(String myId) {
		questionMapper.updatePkTimes(myId);
	}
	public void updatePkTimesToOrigin() {
		questionMapper.updatePkTimesToOrigin();
		
	}
	public void updatePkTimesByShare(String myId) {
		questionMapper.updatePkTimesByShare(myId);
	}

}
