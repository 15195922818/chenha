package com.ai.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ai.core.util.dao.Criteria;
import com.ai.web.entity.GameList;
import com.ai.web.mapper.GameListMapper;

/**
 * @ClassName: GameListDao
* @Description: game_list表对应dao操作接口实现类
* @author: liaowh
 */
@Repository
public class GameListDao {
    /**
     * @Fields LOGGER : 日志操作类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameListDao.class);

    /**
     * @Fields GameList表的Mybatis Mapper操作映射类
     */
    @Resource
    private GameListMapper gameListMapper;

    /**
    * @Title GameListDao.countByExample
    * @Description: 根据查询条件，计算game_list个数
    * @param example 通用查询条件类
    * @return int 结果个数
     */
    public int countByExample(Criteria example) {
        int count = this.gameListMapper.countByExample(example);
        LOGGER.debug("count: {}", count);
        return count;
    }

    /**
    * @Title GameListDao.selectByPrimaryKey
    * @Description: 根据主键类，返回game_list
    * @param gameId gameId
    * @return GameList bean对象
     */
    public GameList selectByPrimaryKey(String gameId) {
        return this.gameListMapper.selectByPrimaryKey(gameId);
    }

    /**
    * @Title GameListDao.selectByExample
    * @Description: 根据查询条件类，返回game_list结果集
    * @param example 通用查询条件类
    * @return List<GameList>game_list结果集
     */
    public List<GameList> selectByExample(Criteria example) {
        return this.gameListMapper.selectByExample(example);
    }

    /**
    * @Title GameListDao.deleteByPrimaryKey
    * @Description: 根据属性名称，删除game_list
    * @param gameId gameId
    * @return int  删除个数
     */
    public int deleteByPrimaryKey(String gameId) {
        return this.gameListMapper.deleteByPrimaryKey(gameId);
    }

    /**
    * @Title GameListDao.updateByPrimaryKeySelective
    * @Description: 根据主键更新game_list部分字段
    * @param record 要更新成为的GameList对象
    * @return int 更新记录数
     */
    public int updateByPrimaryKeySelective(GameList record) {
        return this.gameListMapper.updateByPrimaryKeySelective(record);
    }

    /**
    * @Title GameListDao.updateByPrimaryKey
    * @Description: 根据主键更新game_list全部字段
    * @param record 要更新成为的GameList对象
    * @return int 更新记录数
     */
    public int updateByPrimaryKey(GameList record) {
        return this.gameListMapper.updateByPrimaryKey(record);
    }

    /**
    * @Title GameListDao.deleteByExample
    * @Description: 根据查询条件，删除game_list
    * @param example 通用查询条件类
    * @return int  删除个数
     */
    public int deleteByExample(Criteria example) {
        return this.gameListMapper.deleteByExample(example);
    }

    /**
    * @Title GameListDao.updateByExampleSelective
    * @Description: 根据查询条件更新game_list部分字段
    * @param record 要更新成为的GameList对象
    * @param example 更新记录的查询条件
    * @return int 更新记录数
     */
    public int updateByExampleSelective(GameList record, Criteria example) {
        return this.gameListMapper.updateByExampleSelective(record, example);
    }

    /**
    * @Title GameListDao.updateByExample
    * @Description: 根据查询条件更新game_list全表字段
    * @param record 要更新成为的GameList对象
    * @param example 更新记录的查询条件
    * @return int 更新记录数
     */
    public int updateByExample(GameList record, Criteria example) {
        return this.gameListMapper.updateByExample(record, example);
    }

    /**
    * @Title GameListDao.insert
    * @Description: 插入一个game_list
    * @param record game_list的bean对象
    * @return int  插入个数
     */
    public int insert(GameList record) {
        return this.gameListMapper.insert(record);
    }

    /**
    * @Title GameListDao.insertSelective
    * @Description: 插入一个只有部分字段的game_list
    * @param record 只含部分字段的game_list的bean对象
    * @return int  插入个数
     */
    public int insertSelective(GameList record) {
        return this.gameListMapper.insertSelective(record);
    }
    
    public List<GameList> getThisWeekScore(){
    	return this.gameListMapper.getThisWeekScore();
    }
    
    public List<GameList> getPreviousWeekScore(){
    	return this.gameListMapper.getPreviousWeekScore();
    }
}