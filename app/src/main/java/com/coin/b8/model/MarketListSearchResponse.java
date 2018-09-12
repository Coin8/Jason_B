package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/11.
 */
public class MarketListSearchResponse {

    /**
     * result : true
     * message : 请求成功
     * data : [{"open":"7.26780","close":"7.17740","closeCny":"47.8732","amount":"1552.66万","count":"112648","vol":"1.10亿","symbol":"eosusdt","base":"eos","quote":"usdt","rank":5,"rateStr":"-1.24%","rate":-0.0904000000000007,"exchangeName":"火币Pro","stockValue":"429.01亿","isSelect":0,"coinId":169,"exchangeAndSymbol":"火币Pro:eosusdt","ucrid":null,"chineseName":"柚子","openCny":"48.4762","low":"6.69990","high":"7.53670","lowCny":"44.6883","highCny":"50.2697","volForSort":"110639327.36947675"},{"open":"6381.40","close":"6376.37","closeCny":"42530.3","amount":"1.55万","count":"182249","vol":"9842.59万","symbol":"btcusdt","base":"btc","quote":"usdt","rank":1,"rateStr":"-0.08%","rate":-5.029999999999745,"exchangeName":"火币Pro","stockValue":"7290.81亿","isSelect":0,"coinId":161,"exchangeAndSymbol":"火币Pro:btcusdt","ucrid":null,"chineseName":"比特币","openCny":"42563.9","low":"6270.00","high":"6410.00","lowCny":"41820.9","highCny":"42754.7","volForSort":"98425935.60176463"},{"open":"0.00019","close":"0.00017","closeCny":"7.50236","amount":"6256.32万","count":"83617","vol":"1.07万","symbol":"paibtc","base":"pai","quote":"btc","rank":1372,"rateStr":"-11.30%","rate":-2.2479999999999988E-5,"exchangeName":"火币Pro","stockValue":"0","isSelect":0,"coinId":7197,"exchangeAndSymbol":"火币Pro:paibtc","ucrid":null,"chineseName":"","openCny":"8.45844","low":"0.00014","high":"0.00019","lowCny":"6.08227","highCny":"8.50182","volForSort":"10766.397231925903"},{"open":"440.690","close":"444.990","closeCny":"2968.08","amount":"15.62万","count":"170875","vol":"6811.41万","symbol":"ethusdt","base":"eth","quote":"usdt","rank":2,"rateStr":"0.98%","rate":4.300000000000011,"exchangeName":"火币Pro","stockValue":"2986.31亿","isSelect":0,"coinId":162,"exchangeAndSymbol":"火币Pro:ethusdt","ucrid":null,"chineseName":"以太坊","openCny":"2939.40","low":"425.000","high":"447.990","lowCny":"2834.75","highCny":"2988.09","volForSort":"68114133.48761709"},{"open":"3.12070","close":"3.20370","closeCny":"21.3686","amount":"1161.41万","count":"99369","vol":"3601.84万","symbol":"htusdt","base":"ht","quote":"usdt","rank":62,"rateStr":"2.66%","rate":0.08300000000000018,"exchangeName":"火币Pro","stockValue":"10.68亿","isSelect":0,"coinId":196,"exchangeAndSymbol":"火币Pro:htusdt","ucrid":null,"chineseName":"火币积分","openCny":"20.8150","low":"2.92990","high":"3.24600","lowCny":"19.5424","highCny":"21.6508","volForSort":"36018404.65226397"},{"open":"0.00288","close":"0.00253","closeCny":"7.51517","amount":"2600.54万","count":"66917","vol":"6.53万","symbol":"paieth","base":"pai","quote":"eth","rank":1372,"rateStr":"-12.27%","rate":-3.5400000000000015E-4,"exchangeName":"火币Pro","stockValue":"0","isSelect":0,"coinId":7197,"exchangeAndSymbol":"火币Pro:paieth","ucrid":null,"chineseName":"","openCny":"8.56587","low":"0.00210","high":"0.00289","lowCny":"6.25671","highCny":"8.57775","volForSort":"65316.11708588639"},{"open":"16.7979","close":"16.6280","closeCny":"110.908","amount":"141.23万","count":"40270","vol":"2298.68万","symbol":"etcusdt","base":"etc","quote":"usdt","rank":15,"rateStr":"-1.01%","rate":-0.16989999999999839,"exchangeName":"火币Pro","stockValue":"114.16亿","isSelect":0,"coinId":176,"exchangeAndSymbol":"火币Pro:etcusdt","ucrid":null,"chineseName":"以太经典","openCny":"112.041","low":"15.6514","high":"17.0000","lowCny":"104.394","highCny":"113.390","volForSort":"22986850.60906382"},{"open":"696.880","close":"708.770","closeCny":"4727.49","amount":"3.06万","count":"34384","vol":"2121.66万","symbol":"bchusdt","base":"bch","quote":"usdt","rank":4,"rateStr":"1.71%","rate":11.889999999999986,"exchangeName":"火币Pro","stockValue":"814.57亿","isSelect":0,"coinId":164,"exchangeAndSymbol":"火币Pro:bchusdt","ucrid":null,"chineseName":"比特现金","openCny":"4648.18","low":"681.380","high":"711.790","lowCny":"4544.80","highCny":"4747.63","volForSort":"21216605.885451235"},{"open":"0.00113","close":"0.00112","closeCny":"47.7450","amount":"251.74万","count":"32919","vol":"2835.5983535517657","symbol":"eosbtc","base":"eos","quote":"btc","rank":5,"rateStr":"-1.38%","rate":-1.5719999999999883E-5,"exchangeName":"火币Pro","stockValue":"427.86亿","isSelect":0,"coinId":169,"exchangeAndSymbol":"火币Pro:eosbtc","ucrid":null,"chineseName":"柚子","openCny":"48.4136","low":"0.00107","high":"0.00117","lowCny":"45.5075","highCny":"50.0659","volForSort":"2835.5983535517657"},{"open":"0.06905","close":"0.06973","closeCny":"2965.85","amount":"3.94万","count":"134995","vol":"2711.140839156","symbol":"ethbtc","base":"eth","quote":"btc","rank":2,"rateStr":"0.98%","rate":6.78999999999999E-4,"exchangeName":"火币Pro","stockValue":"2984.07亿","isSelect":0,"coinId":162,"exchangeAndSymbol":"火币Pro:ethbtc","ucrid":null,"chineseName":"以太坊","openCny":"2936.97","low":"0.06762","high":"0.06993","lowCny":"2876.16","highCny":"2974.32","volForSort":"2711.140839156"},{"open":"0.44800","close":"0.44940","closeCny":"2.99749","amount":"3508.84万","count":"27312","vol":"1565.28万","symbol":"xrpusdt","base":"xrp","quote":"usdt","rank":3,"rateStr":"0.31%","rate":0.0014000000000000123,"exchangeName":"火币Pro","stockValue":"1176.88亿","isSelect":0,"coinId":163,"exchangeAndSymbol":"火币Pro:xrpusdt","ucrid":null,"chineseName":"瑞波币","openCny":"2.98816","low":"0.43820","high":"0.45140","lowCny":"2.92279","highCny":"3.01083","volForSort":"15652824.315370826"},{"open":"0.00235","close":"0.00240","closeCny":"102.072","amount":"80.69万","count":"19566","vol":"2110.0432121209506","symbol":"bcdbtc","base":"bcd","quote":"btc","rank":38,"rateStr":"2.04%","rate":4.7999999999999866E-5,"exchangeName":"火币Pro","stockValue":"156.94亿","isSelect":0,"coinId":1307,"exchangeAndSymbol":"火币Pro:bcdbtc","ucrid":null,"chineseName":"比特币钻石","openCny":"100.031","low":"0.00186","high":"0.00306","lowCny":"79.1065","highCny":"130.142","volForSort":"2110.0432121209506"},{"open":"3.85050","close":"3.69120","closeCny":"24.6203","amount":"294.73万","count":"34272","vol":"1072.85万","symbol":"ontusdt","base":"ont","quote":"usdt","rank":25,"rateStr":"-4.14%","rate":-0.1593,"exchangeName":"火币Pro","stockValue":"37.24亿","isSelect":0,"coinId":1302,"exchangeAndSymbol":"火币Pro:ontusdt","ucrid":null,"chineseName":"本体","openCny":"25.6828","low":"3.31000","high":"3.88160","lowCny":"22.0777","highCny":"25.8902","volForSort":"10728531.074295752"},{"open":"76.0100","close":"80.5000","closeCny":"536.935","amount":"13.26万","count":"32698","vol":"1016.81万","symbol":"ltcusdt","base":"ltc","quote":"usdt","rank":6,"rateStr":"5.91%","rate":4.489999999999995,"exchangeName":"火币Pro","stockValue":"307.93亿","isSelect":0,"coinId":165,"exchangeAndSymbol":"火币Pro:ltcusdt","ucrid":null,"chineseName":"莱特币","openCny":"506.986","low":"74.5500","high":"81.0800","lowCny":"497.248","highCny":"540.803","volForSort":"10168170.72536716"},{"open":"0.01650","close":"0.01612","closeCny":"47.8455","amount":"131.22万","count":"21852","vol":"2.15万","symbol":"eoseth","base":"eos","quote":"eth","rank":5,"rateStr":"-2.31%","rate":-3.8136999999999893E-4,"exchangeName":"火币Pro","stockValue":"428.76亿","isSelect":0,"coinId":169,"exchangeAndSymbol":"火币Pro:eoseth","ucrid":null,"chineseName":"柚子","openCny":"48.9774","low":"0.01577","high":"0.01704","lowCny":"46.8200","highCny":"50.5904","volForSort":"21541.706767815565"},{"open":"0.000001","close":"0.000002","closeCny":"0.10845","amount":"5.81亿","count":"19872","vol":"1452.4787947763264","symbol":"bcxbtc","base":"bcx","quote":"btc","rank":1378,"rateStr":"62.42%","rate":9.800000000000001E-7,"exchangeName":"火币Pro","stockValue":"0","isSelect":0,"coinId":1408,"exchangeAndSymbol":"火币Pro:bcxbtc","ucrid":null,"chineseName":"比特无限","openCny":"0.06677","low":"0.000001","high":"0.000003","lowCny":"0.06677","highCny":"0.13567","volForSort":"1452.4787947763264"},{"open":"0.02143","close":"0.02093","closeCny":"0.13966","amount":"4.51亿","count":"30751","vol":"915.63万","symbol":"iostusdt","base":"iost","quote":"usdt","rank":59,"rateStr":"-2.30%","rate":-4.940000000000014E-4,"exchangeName":"火币Pro","stockValue":"11.73亿","isSelect":0,"coinId":1312,"exchangeAndSymbol":"火币Pro:iostusdt","ucrid":null,"chineseName":"－","openCny":"0.14295","low":"0.01880","high":"0.02154","lowCny":"0.12539","highCny":"0.14371","volForSort":"9156389.12505816"},{"open":"0.00061","close":"0.00056","closeCny":"23.8867","amount":"198.44万","count":"15957","vol":"1158.3574655060677","symbol":"nasbtc","base":"nas","quote":"btc","rank":58,"rateStr":"-8.55%","rate":-5.254000000000001E-5,"exchangeName":"火币Pro","stockValue":"10.86亿","isSelect":0,"coinId":237,"exchangeAndSymbol":"火币Pro:nasbtc","ucrid":null,"chineseName":"星云币","openCny":"26.1213","low":"0.00053","high":"0.00061","lowCny":"22.7065","highCny":"26.1442","volForSort":"1158.3574655060677"},{"open":"0.00979","close":"0.00941","closeCny":"400.593","amount":"10.17万","count":"12492","vol":"1073.492202602145","symbol":"sbtcbtc","base":"s","quote":"btc","rank":1008610010,"rateStr":"-3.88%","rate":-3.8000000000000013E-4,"exchangeName":"火币Pro","stockValue":null,"isSelect":0,"coinId":null,"exchangeAndSymbol":"火币Pro:sbtcbtc","ucrid":null,"chineseName":null,"openCny":"416.755","low":"0.00651","high":"0.01350","lowCny":"276.915","highCny":"574.160","volForSort":"1073.492202602145"},{"open":"0.00048","close":"0.00050","closeCny":"21.3783","amount":"194.97万","count":"38664","vol":"957.5639370304126","symbol":"htbtc","base":"ht","quote":"btc","rank":62,"rateStr":"2.79%","rate":1.3650000000000034E-5,"exchangeName":"火币Pro","stockValue":"10.68亿","isSelect":0,"coinId":196,"exchangeAndSymbol":"火币Pro:htbtc","ucrid":null,"chineseName":"火币积分","openCny":"20.7977","low":"0.00046","high":"0.00050","lowCny":"19.7409","highCny":"21.6632","volForSort":"957.5639370304126"}]
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
         * open : 7.26780
         * close : 7.17740
         * closeCny : 47.8732
         * amount : 1552.66万
         * count : 112648
         * vol : 1.10亿
         * symbol : eosusdt
         * base : eos
         * quote : usdt
         * rank : 5
         * rateStr : -1.24%
         * rate : -0.0904000000000007
         * exchangeName : 火币Pro
         * stockValue : 429.01亿
         * isSelect : 0
         * coinId : 169
         * exchangeAndSymbol : 火币Pro:eosusdt
         * ucrid : null
         * chineseName : 柚子
         * openCny : 48.4762
         * low : 6.69990
         * high : 7.53670
         * lowCny : 44.6883
         * highCny : 50.2697
         * volForSort : 110639327.36947675
         */

        private String open;
        private String close;
        private String closeShow;
        private String amount;
        private String amountShow;
        private String count;
        private String vol;
        private String symbol;
        private String base;
        private String quote;
        private long symbolId;
        private int rank;
        private String rateStr;
        private double rate;
        private String exchangeName;
        private String stockValue;
        private int isSelect;
        private long coinId;
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

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
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
