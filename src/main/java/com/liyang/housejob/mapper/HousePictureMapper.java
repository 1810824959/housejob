package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.HousePicture;
import com.liyang.housejob.pojo.HousePictureExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HousePictureMapper {
    int countByExample(HousePictureExample example);

    int deleteByExample(HousePictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HousePicture record);

    int insertSelective(HousePicture record);

    List<HousePicture> selectByExample(HousePictureExample example);

    HousePicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HousePicture record, @Param("example") HousePictureExample example);

    int updateByExample(@Param("record") HousePicture record, @Param("example") HousePictureExample example);

    int updateByPrimaryKeySelective(HousePicture record);

    int updateByPrimaryKey(HousePicture record);
}