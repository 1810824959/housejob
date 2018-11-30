package com.liyang.housejob.service;

import com.liyang.housejob.service.result.ServiceMultiResult;
import com.liyang.housejob.service.result.ServiceResult;
import com.liyang.housejob.web.DTO.*;
import com.liyang.housejob.web.form.HouseForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

public interface HouseService {
    Response uploadFile(File file) throws QiniuException;
    Response uploadFile(InputStream in) throws QiniuException;
    Response delete(String key) throws QiniuException;

    ServiceMultiResult<SupportAddrDTO> getSupportAddrListByLevel(String level);
    ServiceMultiResult<SupportAddrDTO> getAllRegionsByCityName(String cityName);
    ServiceMultiResult<SubWayDTO> getAllSubwayByCity(String cityName);
    ServiceMultiResult<SubWayDTO> getAllStationBySubwayId(int subwayId);

    ServiceResult<HouseDTO> saveHouse(HouseForm form);
}
