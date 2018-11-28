package com.liyang.housejob.service.Impl;

import com.liyang.housejob.base.SupportAddrDTO;
import com.liyang.housejob.service.HouseService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService , InitializingBean {
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
    public List<SupportAddrDTO> getSupportAddrListByLevel(String level) {
        return null;
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
