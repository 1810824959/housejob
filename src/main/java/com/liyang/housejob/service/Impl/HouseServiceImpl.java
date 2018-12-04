package com.liyang.housejob.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyang.housejob.base.LoginUserUtil;
import com.liyang.housejob.mapper.*;
import com.liyang.housejob.pojo.*;
import com.liyang.housejob.web.DTO.*;
import com.liyang.housejob.service.HouseService;
import com.liyang.housejob.service.result.ServiceMultiResult;
import com.liyang.housejob.service.result.ServiceResult;
import com.liyang.housejob.web.form.DatatableSearch;
import com.liyang.housejob.web.form.HouseForm;
import com.liyang.housejob.web.form.PhotoForm;
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
import java.util.Date;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService , InitializingBean {
    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;

    @Resource
    private HousePictureMapper housePictureMapper;
    @Resource
    private HouseDetailMapper houseDetailMapper;
    @Resource
    private HouseTagMapper houseTagMapper;
    
    @Resource
    private HouseMapper houseMapper;

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
    public ServiceResult<HouseDTO> saveHouse(HouseForm form) {
        HouseDetail detail = new HouseDetail();
        ServiceResult<HouseDTO> subwayValidResult = wrapperDetailInfo(detail, form);
        if (subwayValidResult != null){  // 验证类里，return 有东西就是有错，，null就是正常
            return subwayValidResult;
        }
        // house 表插入
        House house = modelMapper.map(form, House.class);
        Date now = new Date();
        house.setCreateTime(now);
        house.setStatus(0);//教程中暂时没提到，我改成了0
        house.setLastUpdateTime(now);
        house.setAdminId(LoginUserUtil.getLoginUserId());
        int houseId = houseMapper.insert(house); // insert house

        // detail 表插入
        detail.setHouseId(house.getId());
        int detailId = houseDetailMapper.insert(detail);

        // picture 表插入
        List<HousePicture> pictures = generatePictures(form,house.getId());
        for (HousePicture picture : pictures) {
            housePictureMapper.insert(picture);
        }

        // tag表插入
        List<String> tags = form.getTags();
        for (String tag : tags) {
            HouseTag Tag = new HouseTag();
            Tag.setHouseId(house.getId());
            Tag.setName(tag);
            houseTagMapper.insert(Tag);
        }

        HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
        HouseDetailDTO houseDetailDTO = modelMapper.map(detail, HouseDetailDTO.class);

        houseDTO.setHouseDetail(houseDetailDTO);

        List<HousePictureDTO> pictureDTOS = new ArrayList<>();
        pictures.forEach(housePicture -> pictureDTOS.add(modelMapper.map(pictures, HousePictureDTO.class)));
        houseDTO.setPictures(pictureDTOS);
        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());
        houseDTO.setTags(tags);

        return new ServiceResult<HouseDTO>(true, null, houseDTO);
    }

    @Override
    public ServiceMultiResult<HouseDTO> getAllHouses(DatatableSearch datatableSearch) {
        PageHelper.startPage(datatableSearch.getDraw(),datatableSearch.getLength());

        HouseExample houseExample = new HouseExample();
        houseExample.createCriteria();
        List<House> houseList = houseMapper.selectByExample(houseExample);
        PageInfo pageInfo = new PageInfo<>(houseList);
//        System.out.println("size"+pageInfo.getSize());   3
//        System.out.println("pages"+pageInfo.getPages()*3);  12
//        System.out.println("total"+pageInfo.getTotal());  10

        List<HouseDTO> houseDTOList = new ArrayList<>();
        houseList.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house,HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            houseDTOList.add(houseDTO);
        });

        return new ServiceMultiResult<>(pageInfo.getTotal(),houseDTOList);
    }

    /**
     * subway信息 校验 并且封装 detail
     * @param detail
     * @param form
     * @return
     */
    private ServiceResult<HouseDTO> wrapperDetailInfo(HouseDetail detail,HouseForm form){
        Subway subway = subwayMapper.selectByPrimaryKey(form.getSubwayLineId());
        if (subway == null){
            return new ServiceResult<>(false,"not valid subway line");
        }

        SubwayStation subwayStation = subwayStationMapper.selectByPrimaryKey(form.getSubwayStationId());
        if (subwayStation == null || subway.getId() != subwayStation.getSubwayId()){
            return new ServiceResult<>(false,"not valid subway");
        }

        detail.setSubwayLineId(subway.getId());
        detail.setSubwayLineName(subway.getName());

        detail.setSubwayStationId(subwayStation.getId());
        detail.setSubwayStationName(subwayStation.getName());

        detail.setDescription(form.getDescription());
        detail.setAddress(form.getDetailAddress());
        detail.setLayoutDesc(form.getLayoutDesc());
        detail.setRentWay(form.getRentWay());
        detail.setRoundService(form.getRoundService());
        detail.setTraffic(form.getTraffic());

        return null;
    }

    private List<HousePicture> generatePictures(HouseForm form,int houseId){
        List<HousePicture> pictures = new ArrayList<>();
        if (form.getPhotos()==null || form.getPhotos().isEmpty()){
            return pictures;
        }
        for (PhotoForm photo : form.getPhotos()) {
            HousePicture picture = new HousePicture();
            picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            picture.setPath(photo.getPath());
            picture.setWidth(photo.getWidth());
            picture.setHeight(photo.getHeight());
            pictures.add(picture);
        }
        return pictures;

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
