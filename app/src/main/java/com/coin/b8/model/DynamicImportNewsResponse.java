package com.coin.b8.model;

import java.util.List;

/**
 * Created by zhangyi on 2018/7/9.
 */
public class DynamicImportNewsResponse {

    /**
     * code : 200
     * data : {"content":[{"id":964,"title":"韩国将遵循G20要求制定加密货币法规","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/144e01cd-42cb-4ae3-9ba9-b254392cda30.jpg","publishTime":1531130449},{"id":963,"title":"Facebook任命新的区块链工程总监","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/8ea69891-4425-4fd8-8c5d-f9d977f6a06e.jpg","publishTime":1531129830},{"id":962,"title":"不知不觉，韩国商业银行已持有近20亿美元的加密货币","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/b7c0ef5d-41ce-43fc-bedc-58bc202bc328.jpg","publishTime":1531129728},{"id":961,"title":"德国solaris银行开始向欧盟加密货币公司提供企业账户","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/dddccd84-592c-42b0-be4b-1bfd2f89097e.jpg","publishTime":1531129584},{"id":960,"title":"香港持续采取监管行动，并希望成为国际区块链中心","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/92e13209-581a-46b8-96b5-f8048b4e39cd.jpg","publishTime":1531129483},{"id":959,"title":"探秘加密货币\u201c淘金热\u201d：挖矿成了这些地方的唯一出路","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/a5ce9390-a739-44c1-8a86-16fb1d4d9c9a.jpg","publishTime":1531129199},{"id":958,"title":"沃尔玛新专利：区块链实现包裹的\u201c智能交付\u201d","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/65b3d654-5085-4247-bc1b-229d2eb7347c.jpg","publishTime":1531129102},{"id":957,"title":"联邦快递研究所同制药公司合作使用区块链技术进行癌症药物分销","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/f6e4af8e-70ae-40c0-b744-51df548c7e09.jpg","publishTime":1531128983},{"id":956,"title":"科学实验的可重复性危机\u2014\u2014加密技术带来解题良药","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/721da47c-3591-4655-9831-6af767e1e37d.jpg","publishTime":1531128460},{"id":955,"title":"印度执政党被指控参与\u201c超级比特币诈骗\u201d","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/03d1de30-b644-4e4a-8f8a-7e6e3a01e5f6.jpg","publishTime":1531127935}],"pageable":{"sort":{"sorted":true,"unsorted":false},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"last":false,"totalPages":96,"totalElements":960,"first":true,"sort":{"sorted":true,"unsorted":false},"numberOfElements":10,"size":10,"number":0}
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
         * content : [{"id":964,"title":"韩国将遵循G20要求制定加密货币法规","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/144e01cd-42cb-4ae3-9ba9-b254392cda30.jpg","publishTime":1531130449},{"id":963,"title":"Facebook任命新的区块链工程总监","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/8ea69891-4425-4fd8-8c5d-f9d977f6a06e.jpg","publishTime":1531129830},{"id":962,"title":"不知不觉，韩国商业银行已持有近20亿美元的加密货币","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/b7c0ef5d-41ce-43fc-bedc-58bc202bc328.jpg","publishTime":1531129728},{"id":961,"title":"德国solaris银行开始向欧盟加密货币公司提供企业账户","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/dddccd84-592c-42b0-be4b-1bfd2f89097e.jpg","publishTime":1531129584},{"id":960,"title":"香港持续采取监管行动，并希望成为国际区块链中心","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/92e13209-581a-46b8-96b5-f8048b4e39cd.jpg","publishTime":1531129483},{"id":959,"title":"探秘加密货币\u201c淘金热\u201d：挖矿成了这些地方的唯一出路","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/a5ce9390-a739-44c1-8a86-16fb1d4d9c9a.jpg","publishTime":1531129199},{"id":958,"title":"沃尔玛新专利：区块链实现包裹的\u201c智能交付\u201d","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/65b3d654-5085-4247-bc1b-229d2eb7347c.jpg","publishTime":1531129102},{"id":957,"title":"联邦快递研究所同制药公司合作使用区块链技术进行癌症药物分销","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/f6e4af8e-70ae-40c0-b744-51df548c7e09.jpg","publishTime":1531128983},{"id":956,"title":"科学实验的可重复性危机\u2014\u2014加密技术带来解题良药","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/721da47c-3591-4655-9831-6af767e1e37d.jpg","publishTime":1531128460},{"id":955,"title":"印度执政党被指控参与\u201c超级比特币诈骗\u201d","pic":"https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/03d1de30-b644-4e4a-8f8a-7e6e3a01e5f6.jpg","publishTime":1531127935}]
         * pageable : {"sort":{"sorted":true,"unsorted":false},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * last : false
         * totalPages : 96
         * totalElements : 960
         * first : true
         * sort : {"sorted":true,"unsorted":false}
         * numberOfElements : 10
         * size : 10
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
             * pageSize : 10
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
             * id : 964
             * title : 韩国将遵循G20要求制定加密货币法规
             * pic : https://huobizixun-prd-1253283450.cos.ap-beijing.myqcloud.com/144e01cd-42cb-4ae3-9ba9-b254392cda30.jpg
             * publishTime : 1531130449
             */
            private int viewType;

            private long id;
            private String title;
            private String pic;
            private long publishTime;
            private String abs;
            private String showPubTime;

            public int getViewType() {
                return viewType;
            }

            public void setViewType(int viewType) {
                this.viewType = viewType;
            }

            public String getShowPubTime() {
                return showPubTime;
            }

            public void setShowPubTime(String showPubTime) {
                this.showPubTime = showPubTime;
            }

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

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public String getAbs() {
                return abs;
            }

            public void setAbs(String abs) {
                this.abs = abs;
            }
        }
    }
}
