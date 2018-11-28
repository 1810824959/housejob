package com.liyang.housejob.service.house;

import com.liyang.housejob.HousejobApplicationTests;
import com.liyang.housejob.service.HouseService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;


public class UploadTest extends HousejobApplicationTests {
    @Autowired
    private HouseService houseService;

    @Test
    public void uploadTest(){
        String fileName = "E:/java/housejob/tmp/2.jpg";
        File file = new File(fileName);

        System.out.println(file.exists());
        try {
            Response response = houseService.uploadFile(file);
            System.out.println(response);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
