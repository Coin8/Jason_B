package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class ExchangeListResponse {

    /**
     * result : true
     * message : 请求成功
     * data : [{"value":259,"key":"火币Pro"}]
     * code : 200
     * exception : null
     */

    private boolean result;
    private String message;
    private int code;
    private Object exception;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * value : 259
         * key : 火币Pro
         */


        private int viewType;

        private int value;
        private String key;

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
