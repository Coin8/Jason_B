package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class BannerResponse {


    /**
     * code : 200
     * data : [{"id":1,"title":"美国内华达州埃尔科县或建\u201c比特币合众国\u201d，比特币为其\u201c官方货币\u201d","pic":"http://cdn.diyli.cn/2805f1e955d74ccbaf1cf97fd530eb68.jpg","type":0,"yn":1,"sort":1}]
     * message : success
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 美国内华达州埃尔科县或建“比特币合众国”，比特币为其“官方货币”
         * pic : http://cdn.diyli.cn/2805f1e955d74ccbaf1cf97fd530eb68.jpg
         * type : 0
         * yn : 1
         * sort : 1
         */

        private long id;
        private String title;
        private String pic;
        private int type;
        private int yn;
        private int sort;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
