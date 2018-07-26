package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/10.
 */
public class QuickNewsResponse {

    /**
     * code : 200
     * data : {"content":[{"id":2824,"title":"SBI年内或将建立第三个海外矿场 目标是BCH挖矿比例达到30%","content":"<body>   \n <p>据知情人士消息，日本SBI&nbsp;1月份开设第一个海外矿场，年内将开设第二个海外矿场，第三个也已经在考虑中。通过扩大矿场规模，SBI试图达到BCH挖矿总量的30%。<br><\/p> \n <p><br><\/p>  \n<\/body>","publishTime":1531190718},{"id":2823,"title":"比特币或将成为未来的主流支付手段","content":"<body>   7月10日讯，据帝国学院和英国交易平台埃托罗的一项新研究报告指出，比特币（BTC）等数字货币已经满足了货币的三个主要标准之一，极有潜力发展成为未来的主流支付手段。 \n <p><br><\/p>  \n<\/body>","publishTime":1531190407},{"id":2822,"title":"火币区块链研究院周报：比特币为数字火币领域的热度最高词汇","content":"<body>   \n <p>火币区块链研究院周报显示：本周github代码中，ZRX代码提交数达190commits。社群活跃度方面，facebook中Bitcoin&nbsp;、Ethereum和Ripple&nbsp;(XRP)的公共主页关注数分列前三；Twitter中，粉丝数前三的项目为Ripple、Bitcoin和Ethereum；Mainframe为telegram社区关注度最高的项目，过去24小时粉丝增长最多的项目FxPay。根据火币区块链大数周度洞察报告显示，Reddit论坛关键词云图中，比特币、Bot（机器人）和Coinbase分别为数字货币、热点事件和交易所及币圈KOL领域的热度最高词汇。三大搜索引擎中，热门虚拟货币及区块链相关词汇相比上周升降不一。<\/p> \n <p><br><\/p>  \n<\/body>","publishTime":1531190350}],"pageable":{"sort":{"sorted":true,"unsorted":false},"pageSize":3,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"last":false,"totalPages":941,"totalElements":2823,"first":true,"sort":{"sorted":true,"unsorted":false},"numberOfElements":3,"size":3,"number":0}
     * message : success
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * content : [{"id":2824,"title":"SBI年内或将建立第三个海外矿场 目标是BCH挖矿比例达到30%","content":"<body>   \n <p>据知情人士消息，日本SBI&nbsp;1月份开设第一个海外矿场，年内将开设第二个海外矿场，第三个也已经在考虑中。通过扩大矿场规模，SBI试图达到BCH挖矿总量的30%。<br><\/p> \n <p><br><\/p>  \n<\/body>","publishTime":1531190718},{"id":2823,"title":"比特币或将成为未来的主流支付手段","content":"<body>   7月10日讯，据帝国学院和英国交易平台埃托罗的一项新研究报告指出，比特币（BTC）等数字货币已经满足了货币的三个主要标准之一，极有潜力发展成为未来的主流支付手段。 \n <p><br><\/p>  \n<\/body>","publishTime":1531190407},{"id":2822,"title":"火币区块链研究院周报：比特币为数字火币领域的热度最高词汇","content":"<body>   \n <p>火币区块链研究院周报显示：本周github代码中，ZRX代码提交数达190commits。社群活跃度方面，facebook中Bitcoin&nbsp;、Ethereum和Ripple&nbsp;(XRP)的公共主页关注数分列前三；Twitter中，粉丝数前三的项目为Ripple、Bitcoin和Ethereum；Mainframe为telegram社区关注度最高的项目，过去24小时粉丝增长最多的项目FxPay。根据火币区块链大数周度洞察报告显示，Reddit论坛关键词云图中，比特币、Bot（机器人）和Coinbase分别为数字货币、热点事件和交易所及币圈KOL领域的热度最高词汇。三大搜索引擎中，热门虚拟货币及区块链相关词汇相比上周升降不一。<\/p> \n <p><br><\/p>  \n<\/body>","publishTime":1531190350}]
         * pageable : {"sort":{"sorted":true,"unsorted":false},"pageSize":3,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * last : false
         * totalPages : 941
         * totalElements : 2823
         * first : true
         * sort : {"sorted":true,"unsorted":false}
         * numberOfElements : 3
         * size : 3
         * number : 0
         */

        private PageableBean pageable;
        private boolean last;
        private int totalPages;
        private int totalElements;
        private boolean first;
        private SortBeanX sort;
        private int numberOfElements;
        private int size;
        private int number;
        private List<ContentBean> content;

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public SortBeanX getSort() {
            return sort;
        }

        public void setSort(SortBeanX sort) {
            this.sort = sort;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class PageableBean {
            /**
             * sort : {"sorted":true,"unsorted":false}
             * pageSize : 3
             * pageNumber : 0
             * offset : 0
             * paged : true
             * unpaged : false
             */

            private SortBean sort;
            private int pageSize;
            private int pageNumber;
            private int offset;
            private boolean paged;
            private boolean unpaged;

            public SortBean getSort() {
                return sort;
            }

            public void setSort(SortBean sort) {
                this.sort = sort;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public boolean isPaged() {
                return paged;
            }

            public void setPaged(boolean paged) {
                this.paged = paged;
            }

            public boolean isUnpaged() {
                return unpaged;
            }

            public void setUnpaged(boolean unpaged) {
                this.unpaged = unpaged;
            }

            public static class SortBean {
                /**
                 * sorted : true
                 * unsorted : false
                 */

                private boolean sorted;
                private boolean unsorted;

                public boolean isSorted() {
                    return sorted;
                }

                public void setSorted(boolean sorted) {
                    this.sorted = sorted;
                }

                public boolean isUnsorted() {
                    return unsorted;
                }

                public void setUnsorted(boolean unsorted) {
                    this.unsorted = unsorted;
                }
            }
        }

        public static class SortBeanX {
            /**
             * sorted : true
             * unsorted : false
             */

            private boolean sorted;
            private boolean unsorted;

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }
        }

        public static class ContentBean {
            /**
             * id : 2824
             * title : SBI年内或将建立第三个海外矿场 目标是BCH挖矿比例达到30%
             * content : <body>
             <p>据知情人士消息，日本SBI&nbsp;1月份开设第一个海外矿场，年内将开设第二个海外矿场，第三个也已经在考虑中。通过扩大矿场规模，SBI试图达到BCH挖矿总量的30%。<br></p>
             <p><br></p>
             </body>
             * publishTime : 1531190718
             */

            private int id;
            private String title;
            private String content;
            private long publishTime;
            private String contentWithoutTag;
            private String showPubTime;

            public String getShowPubTime() {
                return showPubTime;
            }

            public void setShowPubTime(String showPubTime) {
                this.showPubTime = showPubTime;
            }

            public String getContentWithoutTag() {
                return contentWithoutTag;
            }

            public void setContentWithoutTag(String contentWithoutTag) {
                this.contentWithoutTag = contentWithoutTag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }
        }
    }
}
