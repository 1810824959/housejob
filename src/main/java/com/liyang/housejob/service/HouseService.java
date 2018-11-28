package com.liyang.housejob.service;

import com.liyang.housejob.base.SupportAddrDTO;
import com.liyang.housejob.pojo.SupportAddress;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface HouseService {
    Response uploadFile(File file) throws QiniuException;
    Response uploadFile(InputStream in) throws QiniuException;
    Response delete(String key) throws QiniuException;

    List<SupportAddrDTO> getSupportAddrListByLevel(String level);

}
