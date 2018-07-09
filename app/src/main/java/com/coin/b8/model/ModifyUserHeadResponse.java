package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/5.
 */
public class ModifyUserHeadResponse {

    /**
     * result : true
     * message : 请求成功
     * data : http://cdn.diyli.cn/8ce3f4174eae40298e5c0276f521283f.png
     * code : 200
     * exception : null
     */

    private boolean result;
    private String message;
    private String data;
    private int code;
    private Object exception;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
}
