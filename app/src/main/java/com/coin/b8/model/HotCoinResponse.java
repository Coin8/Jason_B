package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/17.
 */
public class HotCoinResponse {


    /**
     * code : 200
     * data : [{"id":1,"name":"BTC","chineseName":"比特币","englishName":"Bitcoin","ranking":1,"countCap":"17214162.0000000000"},{"id":2,"name":"ETH","chineseName":"以太坊","englishName":"Ethereum","ranking":2,"countCap":"101373438.0000000000"},{"id":3,"name":"XRP","chineseName":"瑞波币","englishName":"Ripple","ranking":3,"countCap":"39372399467.0000000000"},{"id":4,"name":"BCH","chineseName":"比特现金","englishName":"Bitcoin Cash(BCC)","ranking":4,"countCap":"17297000.0000000000"},{"id":5,"name":"EOS","chineseName":"柚子","englishName":"EOS","ranking":5,"countCap":"906245118.0000000000"},{"id":6,"name":"LTC","chineseName":"莱特币","englishName":"Litecoin","ranking":6,"countCap":"57877984.0000000000"},{"id":7,"name":"XLM","chineseName":"恒星币","englishName":"Stellar","ranking":7,"countCap":"18771753750.0000000000"},{"id":8,"name":"ADA","chineseName":"艾达币","englishName":"Cardano","ranking":8,"countCap":"25927070538.0000000000"},{"id":9,"name":"MIOTA","chineseName":"埃欧塔","englishName":"IOTA","ranking":9,"countCap":"2779530283.0000000000"},{"id":12,"name":"USDT","chineseName":"泰达币","englishName":"Tether","ranking":10,"countCap":"2677140336.0000000000"}]
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
         * name : BTC
         * chineseName : 比特币
         * englishName : Bitcoin
         * ranking : 1
         * countCap : 17214162.0000000000
         */

        private int id;
        private String name;
        private String chineseName;
        private String englishName;
        private int ranking;
        private String countCap;

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

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public String getCountCap() {
            return countCap;
        }

        public void setCountCap(String countCap) {
            this.countCap = countCap;
        }
    }
}
