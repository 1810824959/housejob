package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.HouseDetail;
import com.liyang.housejob.pojo.HouseDetailExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseDetailMapper {
    int countByExample(HouseDetailExample example);

    int deleteByExample(HouseDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HouseDetail record);

    int insertSelective(HouseDetail record);

    List<HouseDetail> selectByExample(HouseDetailExample example);

    HouseDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HouseDetail record, @Param("example") HouseDetailExample example);

    int updateByExample(@Param("record") HouseDetail record, @Param("example") HouseDetailExample example);

    int updateByPrimaryKeySelective(HouseDetail record);

    int updateByPrimaryKey(HouseDetail record);
}