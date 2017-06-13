package com.ai.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ai.web.dao.QuestionDao;
import com.ai.web.entity.GameRecord;
import com.ai.web.entity.PkRecord;
import com.ai.web.entity.Question;
import com.ai.web.entity.User;

import com.ai.web.service.QuestionSevice;
@Service
public class QuestionSeviceImpl implements QuestionSevice{

    @Autowired
    private QuestionDao questionDao;
	 
	@Override
	public int getQuestionNumber() {
		return questionDao.getQuestionNumber();
	}

	@Override
	@Cacheable(value="default",key="#questionId")
	public Question getQuestionById(String questionId) {
		Question q = questionDao.getQuestionById(questionId);
		for(int i=0;i<200;i++){
			questionDao.getQuestionById(questionId);
		}
		return q;
	}
	
	@CacheEvict(value="default",key="#questionId")
	public void delQuestionById(String questionId) {
	}
	
	

	@Override
	public String getMaxOfWeek(Map<String, String> paramMap) {
		
		return questionDao.getMaxOfWeek(paramMap);
	}

	@Override
	public void updatePreviousMax(Map<String, String> paramMap) {
		questionDao.updatePreviousMax(paramMap);
		
	}

	@Override
	public void insertGameRecord(GameRecord gameRecord) {
		questionDao.insertGameRecord(gameRecord);
		
	}

	@Override
	public void insertUser(User user) {
		questionDao.insertUser(user);		
	}

	@Override
	public User getUserById(String personId) {
		
		return questionDao.getUserById(personId);
	}

	@Override
	public void updateUser(User user) {

		questionDao.updateUser(user);
	}

	public void insertLog(Map<String,Object> logMap){
		
		questionDao.insertLog(logMap);
	}

	@Override
	public List<PkRecord> getPkList(String myId) {
		
		return questionDao.getPkList(myId);
	}

	@Override
	public List<User> getPkUser(String userId) {
		
		return questionDao.getPkUser(userId);
	}

	@Override
	public void insertPkRecord(Map<String, String> paramMap) {
		questionDao.insertPkRecord(paramMap);
		
	}

	@Override
	public void updateUserToOne(String userId) {
		questionDao.updateUserToOne(userId);
	}

	@Override
	public void updateUserToTwo(String personId) {
		questionDao.updateUserToTwo(personId);
	}

	@Override
	public void updateReadyNum(String pkId) {
		questionDao.updateReadyNum(pkId);
	}

	@Override
	public PkRecord getPkRecordById(String pkId) {
		
		return questionDao.getPkRecordById(pkId);
	}

	@Override
	public void updatePkStatus(String pkId) {
		
		questionDao.updatePkStatus(pkId);
	}

	@Override
	public void deletePkRecoed(String pkId) {
		
		questionDao.deletePkRecoed(pkId);
	}

	@Override
	public void insertPkGameRecord(GameRecord gameRecord) {
		
		questionDao.insertPkGameRecord(gameRecord);
	}

	@Override
	public void updatePkRecord(PkRecord pkRecord) {
		questionDao.updatePkRecord(pkRecord);
	}

	@Override
	public void updatePkToOne(Map<String, String> idsMap) {
		
		questionDao.updatePkToOne(idsMap);
	}

	@Override
	public void updatePkTimes(String myId) {
		questionDao.updatePkTimes(myId);
	}

	@Override
	public void updatePkTimesToOrigin() {
		questionDao.updatePkTimesToOrigin();
	}

	@Override
	public void updatePkTimesByShare(String myId) {
		questionDao.updatePkTimesByShare(myId);
	}
}
