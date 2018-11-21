package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.Subway;
import com.liyang.housejob.pojo.SubwayExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SubwayMapper {
    int countByExample(SubwayExample example);

    int deleteByExample(SubwayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Subway record);

    int insertSelective(Subway record);

    List<Subway> selectByExample(SubwayExample example);

    Subway selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Subway record, @Param("example") SubwayExample example);

    int updateByExample(@Param("record") Subway record, @Param("example") SubwayExample example);

    int updateByPrimaryKeySelective(Subway record);

    int updateByPrimaryKey(Subway record);
}