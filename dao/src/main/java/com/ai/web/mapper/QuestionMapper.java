package com.ai.web.mapper;

import java.util.List;
import java.util.Map;

import com.ai.web.entity.GameRecord;
import com.ai.web.entity.PkRecord;
import com.ai.web.entity.Question;
import com.ai.web.entity.User;

public interface QuestionMapper {

	 Question getQuestionById(String questionId);
	 int countQuestionNumber();
	String getMaxOfWeek(Map<String, String> paramMap);
	void updatePreviousMax(Map<String, String> paramMap);
	void insertGameRecord(GameRecord gameRecord);
	void insertUser(User user);
	User getUserById(String personId);
	void updateUser(User user);
	void insertLog(Map<String,Object> logMap);
	List<PkRecord> getPkList(String myId);
	List<User> getPkUser(String userId);
	void insertPkRecord(Map<String, String> paramMap);
	void updateUserToOne(String UserId);
	void updateUserToTwo(String personId);
	void updateReadyNum(String pkId);
	PkRecord getPkRecordById(String pkId);
	void updatePkStatus(String pkId);
	void deletePkRecoed(String pkId);
	void insertPkGameRecord(GameRecord gameRecord);
	void updatePkRecord(PkRecord pkRecord);
	void updatePkToOne(Map<String, String> idsMap);
	void updatePkTimes(String myId);
	void updatePkTimesToOrigin();
	void updatePkTimesByShare(String myId);
}
