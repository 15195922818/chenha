package com.ai.web.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: GameList
* @Description: game_list表对应的java bean类
* @author: liaowh
 */
public class GameList implements Serializable {
    /**
     * @Fields serialVersionUID : 自动生成默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Fields game_list.game_id :
     */
    private String gameId;

    /**
     * @Fields game_list.person_id :
     */
    private String personId;

    /**
     * @Fields game_list.jifen :
     */
    private String jifen;

    /**
     * @Fields game_list.game_time :
     */
    private String gameTime;

    /**
     * @Fields game_list.game_week :
     */
    private String gameWeek;

    /**
     * @Fields game_list.is_week_max :
     */
    private String isWeekMax;

    /**
     * @Fields game_list.extend_field1 :
     */
    private String extendField1;

    /**
     * @Fields game_list.extend_field2 :
     */
    private String extendField2;

    /**
     * @Fields game_list.extend_field3 :
     */
    private String extendField3;

    private String ranking;
    
    private String nickName;
    
    private String headPicture;
    
    private List<String> jifenList;
    
    private List<String> rankingList;
    /**
     * 当前年的第几周
     */
    private String yearWeek;
    public List<String> getJifenList() {
		return jifenList;
	}

	public void setJifenList(List<String> jifenList) {
		this.jifenList = jifenList;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPicture() {
		return headPicture;
	}

	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}

	/**
     * @return game_list.game_id : 返回 
     */
    public String getGameId() {
        return gameId;
    }

    /**
     * @param gameId of game_list : 设置 
     */
    public void setGameId(String gameId) {
        this.gameId = gameId == null ? null : gameId.trim();
    }

    /**
     * @return game_list.person_id : 返回 
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * @param personId of game_list : 设置 
     */
    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    /**
     * @return game_list.jifen : 返回 
     */
    public String getJifen() {
        return jifen;
    }

    /**
     * @param jifen of game_list : 设置 
     */
    public void setJifen(String jifen) {
        this.jifen = jifen == null ? null : jifen.trim();
    }

    /**
     * @return game_list.game_time : 返回 
     */
    public String getGameTime() {
        return gameTime;
    }

    /**
     * @param gameTime of game_list : 设置 
     */
    public void setGameTime(String gameTime) {
        this.gameTime = gameTime == null ? null : gameTime.trim();
    }

    /**
     * @return game_list.game_week : 返回 
     */
    public String getGameWeek() {
        return gameWeek;
    }

    /**
     * @param gameWeek of game_list : 设置 
     */
    public void setGameWeek(String gameWeek) {
        this.gameWeek = gameWeek == null ? null : gameWeek.trim();
    }

    /**
     * @return game_list.is_week_max : 返回 
     */
    public String getIsWeekMax() {
        return isWeekMax;
    }

    /**
     * @param isWeekMax of game_list : 设置 
     */
    public void setIsWeekMax(String isWeekMax) {
        this.isWeekMax = isWeekMax == null ? null : isWeekMax.trim();
    }

    /**
     * @return game_list.extend_field1 : 返回 
     */
    public String getExtendField1() {
        return extendField1;
    }

    /**
     * @param extendField1 of game_list : 设置 
     */
    public void setExtendField1(String extendField1) {
        this.extendField1 = extendField1 == null ? null : extendField1.trim();
    }

    /**
     * @return game_list.extend_field2 : 返回 
     */
    public String getExtendField2() {
        return extendField2;
    }

    /**
     * @param extendField2 of game_list : 设置 
     */
    public void setExtendField2(String extendField2) {
        this.extendField2 = extendField2 == null ? null : extendField2.trim();
    }

    /**
     * @return game_list.extend_field3 : 返回 
     */
    public String getExtendField3() {
        return extendField3;
    }

    /**
     * @param extendField3 of game_list : 设置 
     */
    public void setExtendField3(String extendField3) {
        this.extendField3 = extendField3 == null ? null : extendField3.trim();
    }

	public List<String> getRankingList() {
		return rankingList;
	}

	public void setRankingList(List<String> rankingList) {
		this.rankingList = rankingList;
	}

	public String getYearWeek() {
		return yearWeek;
	}

	public void setYearWeek(String yearWeek) {
		this.yearWeek = yearWeek;
	}
    
}