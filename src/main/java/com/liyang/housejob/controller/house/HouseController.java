package com.liyang.housejob.controller.house;

import com.google.gson.Gson;
import com.liyang.housejob.base.ApiResponse;
import com.liyang.housejob.base.QiniuResponse;
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


    @GetMapping("/address/support/cities")
    @ResponseBody
    public ApiResponse getCityList(){
        return ApiResponse.ofSuccess(null);
    }

}

