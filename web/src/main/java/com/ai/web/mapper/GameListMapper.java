package com.ai.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.core.util.dao.Criteria;
import com.ai.web.entity.GameList;

/**
 * @ClassName: GameListMapper
* @Description: game_list表对应的dao操作Mapper映射类
* @author: liaowh
 */
public interface GameListMapper {
    /**
    * @Title GameListMapper.countByExample
    * @Description: 根据查询条件，计算game_list个数
    * @param example 通用查询条件类
    * @return int 结果个数
     */
    int countByExample(Criteria example);

    /**
    * @Title GameListMapper.deleteByExample
    * @Description: 根据查询条件，删除game_list
    * @param example 通用查询条件类
    * @return int  删除个数
     */
    int deleteByExample(Criteria example);

    /**
    * @Title GameListMapper.deleteByPrimaryKey
    * @Description: 根据属性名称，删除game_list
    * @param gameId gameId
    * @return int  删除个数
     */
    int deleteByPrimaryKey(String gameId);

    /**
    * @Title GameListMapper.insert
    * @Description: 插入一个game_list
    * @param record game_list的bean对象
    * @return int  插入个数
     */
    int insert(GameList record);

    /**
    * @Title GameListMapper.insertSelective
    * @Description: 插入一个只有部分字段的game_list
    * @param record 只含部分字段的game_list的bean对象
    * @return int  插入个数
     */
    int insertSelective(GameList record);

    /**
    * @Title GameListMapper.selectByExample
    * @Description: 根据查询条件类，返回game_list结果集
    * @param example 通用查询条件类
    * @return List<GameList>game_list结果集
     */
    List<GameList> selectByExample(Criteria example);

    /**
    * @Title GameListMapper.selectByPrimaryKey
    * @Description: 根据主键类，返回game_list
    * @param gameId gameId
    * @return GameList bean对象
     */
    GameList selectByPrimaryKey(String gameId);

    /**
    * @Title GameListMapper.updateByExampleSelective
    * @Description: 根据查询条件更新game_list部分字段
    * @param record 要更新成为的GameList对象
    * @param criteria 更新记录的查询条件
    * @return int 更新记录数
     */
    int updateByExampleSelective(@Param("record") GameList record, @Param("example") Criteria criteria);

    /**
    * @Title GameListMapper.updateByExample
    * @Description: 根据查询条件更新game_list全表字段
    * @param record 要更新成为的GameList对象
    * @param criteria 更新记录的查询条件
    * @return int 更新记录数
     */
    int updateByExample(@Param("record") GameList record, @Param("example") Criteria criteria);

    /**
    * @Title GameListMapper.updateByPrimaryKeySelective
    * @Description: 根据主键更新game_list部分字段
    * @param record 要更新成为的GameList对象
    * @return int 更新记录数
     */
    int updateByPrimaryKeySelective(GameList record);

    /**
    * @Title GameListMapper.updateByPrimaryKey
    * @Description: 根据主键更新game_list全部字段
    * @param record 要更新成为的GameList对象
    * @return int 更新记录数
     */
    int updateByPrimaryKey(GameList record);
    
    List<GameList> getThisWeekScore();
    
    List<GameList> getPreviousWeekScore();
}