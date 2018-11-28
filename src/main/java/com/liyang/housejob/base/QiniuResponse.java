package com.liyang.housejob.base;

public final class QiniuResponse {
    public String key;

    @Override
    public String toString() {
        return "QiniuResponse{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public String hash;
    public String bucket;
    public int width;
    public int height;


}
