package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class LoginResponseInfo {

    /**
     * result : true
     * message : 请求成功
     * data : {"uid":557,"password":"awa2o1fs34ha8i19y39i91g24e25ybbaf0n6f|e4y50aa3n4","easename":"gr5ykT7oyVEW8","token":"xRSXFcAuuwbpwCcbn/pINex8TF/b/qnl"}
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
         * uid : 557
         * password : awa2o1fs34ha8i19y39i91g24e25ybbaf0n6f|e4y50aa3n4
         * easename : gr5ykT7oyVEW8
         * token : xRSXFcAuuwbpwCcbn/pINex8TF/b/qnl
         */

        private int uid;
        private String password;
        private String easename;
        private String token;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEasename() {
            return easename;
        }

        public void setEasename(String easename) {
            this.easename = easename;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
