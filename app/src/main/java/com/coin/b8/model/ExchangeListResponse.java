package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class ExchangeListResponse {

    /**
     * code : 200
     * data : [{"id":1,"name":"huobi","chineseName":"火币","englishName":"huobi","showName":"火币Pro","tradePairCount":232,"createTime":1111,"updateTime":1535094494,"logo":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1060519085,1077247018&fm=58&bpow=1024&bpoh=768","butt":1,"showStatus":1,"symbolCount":7,"yn":1}]
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
         * name : huobi
         * chineseName : 火币
         * englishName : huobi
         * showName : 火币Pro
         * tradePairCount : 232
         * createTime : 1111
         * updateTime : 1535094494
         * logo : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1060519085,1077247018&fm=58&bpow=1024&bpoh=768
         * butt : 1
         * showStatus : 1
         * symbolCount : 7
         * yn : 1
         */

        private int viewType;

        private int id;
        private String name;
        private String chineseName;
        private String englishName;
        private String showName;
        private int tradePairCount;
        private int createTime;
        private int updateTime;
        private String logo;
        private int butt;
        private int showStatus;
        private int symbolCount;
        private int yn;

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public int getTradePairCount() {
            return tradePairCount;
        }

        public void setTradePairCount(int tradePairCount) {
            this.tradePairCount = tradePairCount;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(int updateTime) {
            this.updateTime = updateTime;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getButt() {
            return butt;
        }

        public void setButt(int butt) {
            this.butt = butt;
        }

        public int getShowStatus() {
            return showStatus;
        }

        public void setShowStatus(int showStatus) {
            this.showStatus = showStatus;
        }

        public int getSymbolCount() {
            return symbolCount;
        }

        public void setSymbolCount(int symbolCount) {
            this.symbolCount = symbolCount;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }
    }
}
