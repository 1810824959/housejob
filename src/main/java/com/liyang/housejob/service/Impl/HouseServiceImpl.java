package com.liyang.housejob.service.Impl;

import com.liyang.housejob.pojo.*;
import com.liyang.housejob.service.DTO.ServiceMultiResult;
import com.liyang.housejob.service.DTO.SubWayDTO;
import com.liyang.housejob.service.DTO.SupportAddrDTO;
import com.liyang.housejob.mapper.SubwayMapper;
import com.liyang.housejob.mapper.SubwayStationMapper;
import com.liyang.housejob.mapper.SupportAddressMapper;
import com.liyang.housejob.service.HouseService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService , InitializingBean {
    @Autowired
    private ModelMapper modelMapper;

    @Resource
    private SubwayMapper subwayMapper;

    @Resource
    private SubwayStationMapper subwayStationMapper;
    
    @Resource
    private SupportAddressMapper supportAddressMapper;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;

    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, null, getUploadTocken());
        int retry = 0;
        while (response.needRetry() && retry<3){
            response = this.uploadManager.put(file, null, getUploadTocken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream in) throws QiniuException {
        Response response = this.uploadManager.put(in, null, getUploadTocken(),null,null);
        int retry = 0;
        while (response.needRetry() && retry<3){
            response = this.uploadManager.put(in, null, getUploadTocken(),null,null);
            retry++;
        }
        return response;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        Response delete = bucketManager.delete(bucket, key);
        int retry = 0;
        while (delete.needRetry() && retry<3){
            delete = bucketManager.delete(bucket, key);
            retry++;
        }
        return null;
    }

    @Override
    public ServiceMultiResult<SupportAddrDTO> getSupportAddrListByLevel(String level) {
        SupportAddressExample example = new SupportAddressExample();
        example.createCriteria().andLevelEqualTo(level);
        List<SupportAddress> supportAddressList = supportAddressMapper.selectByExample(example);
        List<SupportAddrDTO> supportAddrDTOList = new ArrayList<>();
        for (SupportAddress supportAddress : supportAddressList) {
            SupportAddrDTO target = modelMapper.map(supportAddress,SupportAddrDTO.class);
            supportAddrDTOList.add(target);
        }

        return new ServiceMultiResult<>(supportAddrDTOList.size(),supportAddrDTOList);
    }

    @Override
    public ServiceMultiResult<SupportAddrDTO> getAllRegionsByCityName(String cityName) {
        SupportAddressExample example = new SupportAddressExample();
        example.createCriteria().andLevelEqualTo("region").andBelongToEqualTo(cityName);
        List<SupportAddress> supportAddressList = supportAddressMapper.selectByExample(example);
        List<SupportAddrDTO> supportAddrDTOList = new ArrayList<>();
        for (SupportAddress supportAddress : supportAddressList) {
            SupportAddrDTO target = modelMapper.map(supportAddress,SupportAddrDTO.class);
            supportAddrDTOList.add(target);
        }

        return new ServiceMultiResult<>(supportAddrDTOList.size(),supportAddrDTOList);
    }

    @Override
    public ServiceMultiResult<SubWayDTO> getAllSubwayByCity(String cityName) {
        SubwayExample example = new SubwayExample();
        example.createCriteria().andCityEnNameEqualTo(cityName);
        List<Subway> subwayList = subwayMapper.selectByExample(example);
        List<SubWayDTO> subwayDTOList = new ArrayList<>();
        for (Subway subway : subwayList) {
            SubWayDTO target = modelMapper.map(subway,SubWayDTO.class);
            subwayDTOList.add(target);
        }

        return new ServiceMultiResult<>(subwayDTOList.size(),subwayDTOList);
    }

    @Override
    public ServiceMultiResult<SubWayDTO> getAllStationBySubwayId(int subwayId) {
        SubwayStationExample example = new SubwayStationExample();
        example.createCriteria().andSubwayIdEqualTo(subwayId);
        List<SubwayStation> subwayStationList =  subwayStationMapper.selectByExample(example);
        List<SubWayDTO> subwayStationDTOList = new ArrayList<>();
        for (SubwayStation subwayStation : subwayStationList) {
            SubWayDTO target = modelMapper.map(subwayStation,SubWayDTO.class);
            subwayStationDTOList.add(target);
        }
        return new ServiceMultiResult<>(subwayStationDTOList.size(),subwayStationDTOList);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy=new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");
    }

    private String getUploadTocken(){
        return this.auth.uploadToken(bucket,null,3600,putPolicy);
    }
}
