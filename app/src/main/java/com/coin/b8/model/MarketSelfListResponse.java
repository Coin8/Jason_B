package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/13.
 */
public class MarketSelfListResponse {


    /**
     * result : true
     * message : 请求成功
     * data : [{"open":"76.0200","close":"76.9400","closeCny":"515.498","amount":"9.14万","count":"21146","vol":"697.66万","symbol":"ltcusdt","base":"ltc","quote":"usdt","rank":6,"rateStr":"1.21%","rate":0.9200000000000017,"exchangeName":"火币Pro","stockValue":"295.78亿","isSelect":0,"coinId":165,"exchangeAndSymbol":"火币Pro:ltcusdt","ucrid":801,"chineseName":"莱特币","openCny":"509.334","low":"74.3100","high":"78.0000","lowCny":"497.877","highCny":"522.600","volForSort":"6976660.320223785"},{"open":"0.10999","close":"0.11163","closeCny":"4681.67","amount":"8143.1403","count":"15293","vol":"903.4030625022","symbol":"bchbtc","base":"bch","quote":"btc","rank":4,"rateStr":"1.49%","rate":0.0016380000000000006,"exchangeName":"火币Pro","stockValue":"806.85亿","isSelect":0,"coinId":164,"exchangeAndSymbol":"火币Pro:bchbtc","ucrid":796,"chineseName":"比特现金","openCny":"4612.98","low":"0.10875","high":"0.11297","lowCny":"4560.97","highCny":"4737.82","volForSort":"903.4030625022"},{"open":"0.01231","close":"0.01232","closeCny":"516.882","amount":"2.60万","count":"10773","vol":"320.6864715674","symbol":"ltcbtc","base":"ltc","quote":"btc","rank":6,"rateStr":"0.06%","rate":7.999999999999327E-6,"exchangeName":"火币Pro","stockValue":"296.58亿","isSelect":0,"coinId":165,"exchangeAndSymbol":"火币Pro:ltcbtc","ucrid":802,"chineseName":"莱特币","openCny":"516.546","low":"0.01217","high":"0.01242","lowCny":"510.759","highCny":"521.243","volForSort":"320.6864715674"}]
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
         * open : 76.0200
         * close : 76.9400
         * closeCny : 515.498
         * amount : 9.14万
         * count : 21146
         * vol : 697.66万
         * symbol : ltcusdt
         * base : ltc
         * quote : usdt
         * rank : 6
         * rateStr : 1.21%
         * rate : 0.9200000000000017
         * exchangeName : 火币Pro
         * stockValue : 295.78亿
         * isSelect : 0
         * coinId : 165
         * exchangeAndSymbol : 火币Pro:ltcusdt
         * ucrid : 801
         * chineseName : 莱特币
         * openCny : 509.334
         * low : 74.3100
         * high : 78.0000
         * lowCny : 497.877
         * highCny : 522.600
         * volForSort : 6976660.320223785
         */

        private String open;
        private String close;
        private String closeShow;
        private String amount;
        private String amountShow;
        private String count;
        private String vol;
        private String symbol;
        private long symbolId;
        private String base;
        private String quote;
        private long rank;
        private String rateStr;
        private double rate;
        private String exchangeName;
        private String stockValue;
        private int isSelect;
        private long coinId;
        private long uniqueId;
        private String exchangeAndSymbol;
        private long ucrid;
        private String chineseName;
        private String openShow;
        private String low;
        private String high;
        private String lowShow;
        private String highShow;
        private String volForSort;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getVol() {
            return vol;
        }

        public void setVol(String vol) {
            this.vol = vol;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
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

        public long getRank() {
            return rank;
        }

        public void setRank(long rank) {
            this.rank = rank;
        }

        public String getRateStr() {
            return rateStr;
        }

        public void setRateStr(String rateStr) {
            this.rateStr = rateStr;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getExchangeName() {
            return exchangeName;
        }

        public void setExchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
        }

        public String getStockValue() {
            return stockValue;
        }

        public void setStockValue(String stockValue) {
            this.stockValue = stockValue;
        }

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }

        public long getCoinId() {
            return coinId;
        }

        public void setCoinId(long coinId) {
            this.coinId = coinId;
        }

        public String getExchangeAndSymbol() {
            return exchangeAndSymbol;
        }

        public void setExchangeAndSymbol(String exchangeAndSymbol) {
            this.exchangeAndSymbol = exchangeAndSymbol;
        }

        public long getUcrid() {
            return ucrid;
        }

        public void setUcrid(long ucrid) {
            this.ucrid = ucrid;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getVolForSort() {
            return volForSort;
        }

        public void setVolForSort(String volForSort) {
            this.volForSort = volForSort;
        }

        public String getCloseShow() {
            return closeShow;
        }

        public void setCloseShow(String closeShow) {
            this.closeShow = closeShow;
        }

        public String getAmountShow() {
            return amountShow;
        }

        public void setAmountShow(String amountShow) {
            this.amountShow = amountShow;
        }

        public long getSymbolId() {
            return symbolId;
        }

        public void setSymbolId(long symbolId) {
            this.symbolId = symbolId;
        }

        public long getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(long uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getOpenShow() {
            return openShow;
        }

        public void setOpenShow(String openShow) {
            this.openShow = openShow;
        }

        public String getLowShow() {
            return lowShow;
        }

        public void setLowShow(String lowShow) {
            this.lowShow = lowShow;
        }

        public String getHighShow() {
            return highShow;
        }

        public void setHighShow(String highShow) {
            this.highShow = highShow;
        }
    }
}
