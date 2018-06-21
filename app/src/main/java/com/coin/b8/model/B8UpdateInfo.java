package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/6/11.
 */
public class B8UpdateInfo {


    /**
     * result : true
     * message : 请求成功
     * data : {"apkMd5":"553091bfb5fc8290a4ea38a601a1246d","apkUrl":"http://cdn.diyli.cn/3982ea9440be4c9ba00b272c4c9b9f6b.apk","versionDesc":"更新描述","isNew":false,"apkSize":"13MB","isForce":false,"versionName":"1.0.0.14","versionCode":1000014}
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
         * apkMd5 : 553091bfb5fc8290a4ea38a601a1246d
         * apkUrl : http://cdn.diyli.cn/3982ea9440be4c9ba00b272c4c9b9f6b.apk
         * versionDesc : 更新描述
         * isNew : false
         * apkSize : 13MB
         * isForce : false
         * versionName : 1.0.0.14
         * versionCode : 1000014
         */

        private String apkMd5;
        private String apkUrl;
        private String versionDesc;
        private boolean isNew;
        private String apkSize;
        private boolean isForce;
        private String versionName;
        private int versionCode;

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

        public String getVersionDesc() {
            return versionDesc;
        }

        public void setVersionDesc(String versionDesc) {
            this.versionDesc = versionDesc;
        }

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public String getApkSize() {
            return apkSize;
        }

        public void setApkSize(String apkSize) {
            this.apkSize = apkSize;
        }

        public boolean isIsForce() {
            return isForce;
        }

        public void setIsForce(boolean isForce) {
            this.isForce = isForce;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }
    }
}
