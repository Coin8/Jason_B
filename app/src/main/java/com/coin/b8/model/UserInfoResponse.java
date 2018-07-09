package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/5.
 */
public class UserInfoResponse {

    /**
     * result : true
     * message : 请求成功
     * data : {"gender":2,"name":"Dsfadf","icon":"http://cdn.diyli.cn/5ce1cd9f4cd14a28b9ba2cde3e961ab2.png","id":557}
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
         * gender : 2
         * name : Dsfadf
         * icon : http://cdn.diyli.cn/5ce1cd9f4cd14a28b9ba2cde3e961ab2.png
         * id : 557
         */

        private int gender;
        private String name;
        private String icon;
        private int id;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
