package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.HouseSubscribe;
import com.liyang.housejob.pojo.HouseSubscribeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HouseSubscribeMapper {
    int countByExample(HouseSubscribeExample example);

    int deleteByExample(HouseSubscribeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HouseSubscribe record);

    int insertSelective(HouseSubscribe record);

    List<HouseSubscribe> selectByExample(HouseSubscribeExample example);

    HouseSubscribe selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HouseSubscribe record, @Param("example") HouseSubscribeExample example);

    int updateByExample(@Param("record") HouseSubscribe record, @Param("example") HouseSubscribeExample example);

    int updateByPrimaryKeySelective(HouseSubscribe record);

    int updateByPrimaryKey(HouseSubscribe record);
}