package com.coin.b8.model;

/**
 * Created by zhangyi on 2018/7/4.
 */
public class RegisterResponseInfo {


    /**
     * result : true
     * message : 请求成功
     * data : {"phoneType":null,"phoneStr":null,"id":557,"createTime":1530236737866,"updateTime":1530236737866,"phone":null,"name":"黑庄收割机","icon":null,"password":"awa2o1fs34ha8i19y39i91g24e25ybbaf0n6f|e4y50aa3n4","email":"liao@litsoft.com.cn","gender":1,"yn":1,"easename":"gr5ykT7oyVEW8"}
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
         * phoneType : null
         * phoneStr : null
         * id : 557
         * createTime : 1530236737866
         * updateTime : 1530236737866
         * phone : null
         * name : 黑庄收割机
         * icon : null
         * password : awa2o1fs34ha8i19y39i91g24e25ybbaf0n6f|e4y50aa3n4
         * email : liao@litsoft.com.cn
         * gender : 1
         * yn : 1
         * easename : gr5ykT7oyVEW8
         */

        private String phoneType;
        private String phoneStr;
        private int id;
        private long createTime;
        private long updateTime;
        private String phone;
        private String name;
        private String icon;
        private String password;
        private String email;
        private int gender;
        private int yn;
        private String easename;

        public String getPhoneType() {
            return phoneType;
        }

        public void setPhoneType(String phoneType) {
            this.phoneType = phoneType;
        }

        public String getPhoneStr() {
            return phoneStr;
        }

        public void setPhoneStr(String phoneStr) {
            this.phoneStr = phoneStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }

        public String getEasename() {
            return easename;
        }

        public void setEasename(String easename) {
            this.easename = easename;
        }
    }
}
