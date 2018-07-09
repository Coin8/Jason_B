package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class SelectCoinListResponse {

    /**
     * code : 200
     * data : [{"items":[{"id":1,"name":"基础链","introduction":"去中心化的底层系统","coinCount":21},{"id":3,"name":"分叉币","introduction":"分叉比特币区块链生成的货币","coinCount":6},{"id":4,"name":"平台币","introduction":"数字货币交易所发行的代币","coinCount":5}],"parentType":1,"icon":null,"typeName":"主题选币"},{"items":[{"id":8,"name":"放量上攻","introduction":"量价齐升上升趋势","coinCount":6},{"id":2,"name":"止跌回升","introduction":"下跌多日再放量上涨","coinCount":20},{"id":9,"name":"近期新高","introduction":"价格创30日新高","coinCount":14}],"parentType":2,"icon":null,"typeName":"技术选币"},{"items":[{"id":6,"name":"技术活跃的竞争币","introduction":"通过跟踪社区活跃度","coinCount":13},{"id":7,"name":"社区活跃的代币","introduction":"通过代码提交追踪活跃度","coinCount":4},{"id":5,"name":"有潜力的次新币","introduction":"上市时间不长没有经过炒作","coinCount":9}],"parentType":3,"icon":null,"typeName":"特色选币"}]
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
         * items : [{"id":1,"name":"基础链","introduction":"去中心化的底层系统","coinCount":21},{"id":3,"name":"分叉币","introduction":"分叉比特币区块链生成的货币","coinCount":6},{"id":4,"name":"平台币","introduction":"数字货币交易所发行的代币","coinCount":5}]
         * parentType : 1
         * icon : null
         * typeName : 主题选币
         */

        private int parentType;
        private Object icon;
        private String typeName;
        private List<ItemsBean> items;

        public int getParentType() {
            return parentType;
        }

        public void setParentType(int parentType) {
            this.parentType = parentType;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 1
             * name : 基础链
             * introduction : 去中心化的底层系统
             * coinCount : 21
             */

            private int id;
            private String name;
            private String introduction;
            private int coinCount;

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

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }
        }
    }
}
