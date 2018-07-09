package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class CollectionListInfoResponse {

    /**
     * result : true
     * message : 请求成功
     * data : [{"id":1056,"uid":250,"articleId":918,"createTime":1531101355731,"updateTime":null,"publishTime":1531013758,"yn":1,"targetType":1,"pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/7fc47427-1a69-41d1-8954-279040758d45.jpg","title":"通证（Token）：开启共识经济未来的钥匙"},{"id":1055,"uid":250,"articleId":919,"createTime":1531101347785,"updateTime":null,"publishTime":1531020477,"yn":1,"targetType":1,"pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/cd664877-ee50-4988-a493-a0c74047c74d.jpg","title":"Huobi Blockchain Big Data Weekly Insights Vol. 4"}]
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
         * id : 1056
         * uid : 250
         * articleId : 918
         * createTime : 1531101355731
         * updateTime : null
         * publishTime : 1531013758
         * yn : 1
         * targetType : 1
         * pic : https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/7fc47427-1a69-41d1-8954-279040758d45.jpg
         * title : 通证（Token）：开启共识经济未来的钥匙
         */

        private long id;
        private int uid;
        private int articleId;
        private long createTime;
        private Object updateTime;
        private int publishTime;
        private int yn;
        private int targetType;
        private String pic;
        private String title;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }

        public int getTargetType() {
            return targetType;
        }

        public void setTargetType(int targetType) {
            this.targetType = targetType;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
