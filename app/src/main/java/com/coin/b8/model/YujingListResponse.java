package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class YujingListResponse {

    /**
     * code : 200
     * data : [{"id":335,"exchangeName":"火币Pro","base":"eos","quote":"usdt","type":2,"price":"0.01","openStatus":1},{"id":334,"exchangeName":"火币Pro","base":"eos","quote":"usdt","type":1,"price":"10000","openStatus":1}]
     * message : success
     */

    private long code;
    private String message;
    private List<DataBean> data;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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
         * id : 335
         * exchangeName : 火币Pro
         * base : eos
         * quote : usdt
         * type : 2
         * price : 0.01
         * openStatus : 1
         */

        private int id;
        private String exchangeName;
        private String base;
        private String quote;
        private int type;  //1.上涨  2.下跌
        private String price;
        private int openStatus; //1 open ,2 close

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getExchangeName() {
            return exchangeName;
        }

        public void setExchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(int openStatus) {
            this.openStatus = openStatus;
        }
    }
}
