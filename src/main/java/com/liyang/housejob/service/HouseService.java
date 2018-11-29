package com.liyang.housejob.service;

import com.liyang.housejob.service.DTO.ServiceMultiResult;
import com.liyang.housejob.service.DTO.SubWayDTO;
import com.liyang.housejob.service.DTO.SupportAddrDTO;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface HouseService {
    Response uploadFile(File file) throws QiniuException;
    Response uploadFile(InputStream in) throws QiniuException;
    Response delete(String key) throws QiniuException;

    ServiceMultiResult<SupportAddrDTO> getSupportAddrListByLevel(String level);
    ServiceMultiResult<SupportAddrDTO> getAllRegionsByCityName(String cityName);
    ServiceMultiResult<SubWayDTO> getAllSubwayByCity(String cityName);
    ServiceMultiResult<SubWayDTO> getAllStationBySubwayId(int subwayId);


}
