package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.SubwayStation;
import com.liyang.housejob.pojo.SubwayStationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SubwayStationMapper {
    int countByExample(SubwayStationExample example);

    int deleteByExample(SubwayStationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubwayStation record);

    int insertSelective(SubwayStation record);

    List<SubwayStation> selectByExample(SubwayStationExample example);

    SubwayStation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubwayStation record, @Param("example") SubwayStationExample example);

    int updateByExample(@Param("record") SubwayStation record, @Param("example") SubwayStationExample example);

    int updateByPrimaryKeySelective(SubwayStation record);

    int updateByPrimaryKey(SubwayStation record);
}