package com.liyang.housejob.controller.house;

import com.google.gson.Gson;
import com.liyang.housejob.base.ApiResponse;
import com.liyang.housejob.base.QiniuResponse;
import com.liyang.housejob.service.DTO.ServiceMultiResult;
import com.liyang.housejob.service.DTO.SubWayDTO;
import com.liyang.housejob.service.DTO.SupportAddrDTO;
import com.liyang.housejob.service.HouseService;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class HouseController {
    @Autowired
    private Gson gson;

    @Autowired
    private HouseService houseService;


    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping(value = "/admin/upload/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()){
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAM);
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Response response = houseService.uploadFile(inputStream);
            if (response.isOK()){
                String str = response.bodyString();
                QiniuResponse qiniuResponse = gson.fromJson(str, QiniuResponse.class);
                return ApiResponse.ofSuccess(qiniuResponse);
            }else {
                return ApiResponse.ofMessage(response.statusCode,response.getInfo());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取 一级城市接口
     * @return
     */
    @GetMapping("/address/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities(){
        ServiceMultiResult<SupportAddrDTO> result = houseService.getSupportAddrListByLevel("city");
        if (result.getResultSize()==0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }


    /**
     * 获取对应城市支持区域列表
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/regions")
    @ResponseBody
    public ApiResponse getSupportRegions(@RequestParam(name = "city_name") String cityEnName) {
        ServiceMultiResult<SupportAddrDTO> result = houseService.getAllRegionsByCityName(cityEnName);
        if (result.getResultSize()==0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

    /**
     * 获取具体城市所支持的地铁线路
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        ServiceMultiResult<SubWayDTO> result = houseService.getAllSubwayByCity(cityEnName);
        if (result.getResultSize()==0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

    /**
     * 获取对应地铁线路所支持的地铁站点
     * @param subwayId
     * @return
     */
    @GetMapping("address/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") int subwayId) {
        ServiceMultiResult<SubWayDTO> result = houseService.getAllStationBySubwayId(subwayId);
        if (result.getResultSize()==0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

}

