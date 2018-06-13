package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/6/11.
 */
public class B8UpdateInfo {


    /**
     * result : true
     * message : 为了方便调试，先弄个假接口，各个参数都提供假数据
     * data : {"apkMd5":"1e602eb5ae41f27bea082dd76c5defeb","apkUrl":"https://share.be8.io/1523499696484fa364e1c969b411ddae08175567db656.apk","isNew":true,"isForce":false,"versionCode":"1.0.1"}
     * code : 200
     * exception : null
     */

    private boolean result;
    private String message;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * apkMd5 : 1e602eb5ae41f27bea082dd76c5defeb
         * apkUrl : https://share.be8.io/1523499696484fa364e1c969b411ddae08175567db656.apk
         * isNew : true
         * isForce : false
         * versionCode : 1.0.1
         */

        private String apkMd5;
        private String apkUrl;
        private boolean isNew;
        private boolean isForce;
        private String versionCode;

        public String getApkMd5() {
            return apkMd5;
        }

        public void setApkMd5(String apkMd5) {
            this.apkMd5 = apkMd5;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public boolean isIsForce() {
            return isForce;
        }

        public void setIsForce(boolean isForce) {
            this.isForce = isForce;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }
    }
}
