package com.liyang.housejob.service.result;

import java.util.List;

/**
 * 服务接口通用结构  多个
 * @param <T>
 */
public class ServiceMultiResult<T> {
    private long total;
    private List<T> result;

    public ServiceMultiResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize(){
        if (this.result==null){
            return 0;
        }
        return this.result.size();
    }
}
