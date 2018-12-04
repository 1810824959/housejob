package com.liyang.housejob.service;

import com.liyang.housejob.pojo.House;
import com.liyang.housejob.service.result.ServiceMultiResult;
import com.liyang.housejob.service.result.ServiceResult;
import com.liyang.housejob.web.DTO.*;
import com.liyang.housejob.web.form.DatatableSearch;
import com.liyang.housejob.web.form.HouseForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface HouseService {
    Response uploadFile(File file) throws QiniuException;
    Response uploadFile(InputStream in) throws QiniuException;
    Response delete(String key) throws QiniuException;

    // CRUD
//    List<House> houselist =
    // CRUD


    /**
     * 获取一级城市
     * @param level
     * @return
     */
    ServiceMultiResult<SupportAddrDTO> getSupportAddrListByLevel(String level);

    /**
     * 根据一级城市名字 ，获取下级辖区
     * @param cityName
     * @return
     */
    ServiceMultiResult<SupportAddrDTO> getAllRegionsByCityName(String cityName);

    /**
     * 根据辖区，获取地铁线路
     * @param cityName
     * @return
     */
    ServiceMultiResult<SubWayDTO> getAllSubwayByCity(String cityName);

    /**
     * 根据地铁线路 ID ，获取地铁站点
     * @param subwayId
     * @return
     */
    ServiceMultiResult<SubWayDTO> getAllStationBySubwayId(int subwayId);

    /**
     * 保存 form
     * @param form
     * @return
     */
    ServiceResult<HouseDTO> saveHouse(HouseForm form);

    /**
     *
     * @return
     */
    ServiceMultiResult<HouseDTO> getAllHouses(DatatableSearch datatableSearch);


}
