package com.liyang.housejob.web.controller.house;

import com.google.gson.Gson;
import com.liyang.housejob.base.ApiDataTableResponse;
import com.liyang.housejob.base.ApiResponse;
import com.liyang.housejob.base.QiniuResponse;
import com.liyang.housejob.service.result.ServiceMultiResult;
import com.liyang.housejob.service.result.ServiceResult;
import com.liyang.housejob.web.DTO.HouseDTO;
import com.liyang.housejob.web.DTO.SubWayDTO;
import com.liyang.housejob.web.DTO.SupportAddrDTO;
import com.liyang.housejob.service.HouseService;
import com.liyang.housejob.web.form.DatatableSearch;
import com.liyang.housejob.web.form.HouseForm;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    @PostMapping("/admin/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm form , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            //有错
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(),bindingResult.getAllErrors().get(0).getDefaultMessage(),null);
        }
        if (form.getPhotos().size()==0 || form.getCover()==null){
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(),"图片为空");
        }
        ServiceResult<HouseDTO> result = houseService.saveHouse(form);
        if (result.isSuccess()){
            return ApiResponse.ofSuccess(result.getResult());
        }
        return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAM);
    }

    @PostMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse getHouses(@ModelAttribute DatatableSearch datatableSearch){
        ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.getAllHouses(datatableSearch);
        ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS.getCode(),ApiResponse.Status.SUCCESS.getStandardMessage(),null);

        response.setData(serviceMultiResult.getResult());
        response.setRecordsFiltered(serviceMultiResult.getTotal());
        response.setRecordsTotal(serviceMultiResult.getTotal());
        response.setDraw(datatableSearch.getDraw());
        return response;
    }


}

