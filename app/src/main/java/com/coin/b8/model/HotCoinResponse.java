package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class HotCoinResponse {

    /**
     * result : true
     * message : 请求成功
     * data : ["BTC","ETH","XRP","BCH","LTC","EOS"]
     * code : 200
     * exception : null
     */

    private boolean result;
    private String message;
    private int code;
    private Object exception;
    private List<String> data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
