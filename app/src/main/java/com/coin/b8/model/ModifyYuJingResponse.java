package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class ModifyYuJingResponse {

    /**
     * result : true
     * message : 请求成功
     * data : null
     * code : 200
     * exception : null
     */

    private boolean result;
    private String message;
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
