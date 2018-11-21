package com.liyang.housejob.mapper;

import com.liyang.housejob.pojo.SupportAddress;
import com.liyang.housejob.pojo.SupportAddressExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SupportAddressMapper {
    int countByExample(SupportAddressExample example);

    int deleteByExample(SupportAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupportAddress record);

    int insertSelective(SupportAddress record);

    List<SupportAddress> selectByExample(SupportAddressExample example);

    SupportAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupportAddress record, @Param("example") SupportAddressExample example);

    int updateByExample(@Param("record") SupportAddress record, @Param("example") SupportAddressExample example);

    int updateByPrimaryKeySelective(SupportAddress record);

    int updateByPrimaryKey(SupportAddress record);
}