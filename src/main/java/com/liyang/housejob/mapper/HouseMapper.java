package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.House;
import com.liyang.housejob.pojo.HouseExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HouseMapper {
    int countByExample(HouseExample example);

    int deleteByExample(HouseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(House record);

    int insertSelective(House record);

    List<House> selectByExample(HouseExample example);

    House selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") House record, @Param("example") HouseExample example);

    int updateByExample(@Param("record") House record, @Param("example") HouseExample example);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);
}