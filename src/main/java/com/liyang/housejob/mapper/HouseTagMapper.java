package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.HouseTag;
import com.liyang.housejob.pojo.HouseTagExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HouseTagMapper {
    int countByExample(HouseTagExample example);

    int deleteByExample(HouseTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HouseTag record);

    int insertSelective(HouseTag record);

    List<HouseTag> selectByExample(HouseTagExample example);

    HouseTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HouseTag record, @Param("example") HouseTagExample example);

    int updateByExample(@Param("record") HouseTag record, @Param("example") HouseTagExample example);

    int updateByPrimaryKeySelective(HouseTag record);

    int updateByPrimaryKey(HouseTag record);
}