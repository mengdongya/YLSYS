package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by hxk on 2017/6/27 0027
 * 分类慢性病详情javabean
 */

public class SlowDisesBean {
    /**
     * data : {"chronicList":[{"createTime":1497582072000,"id":3,"medicalTypeId":10,"name":"胃胀"},{"createTime":1497582099000,"id":4,"medicalTypeId":10,"name":"胃酸分泌混乱"},{"createTime":1497582172000,"id":5,"medicalTypeId":10,"name":"胃溃疡"},{"createTime":1497582255000,"id":6,"medicalTypeId":10,"name":"慢性胃炎"},{"createTime":1497582275000,"id":7,"medicalTypeId":10,"name":"十二指肠溃疡"},{"createTime":1497582935000,"id":8,"medicalTypeId":10,"name":"肠道炎"},{"createTime":1497582938000,"id":9,"medicalTypeId":10,"name":"痔疮"},{"createTime":1497583283000,"id":10,"medicalTypeId":10,"name":"易感冒"}]}
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private DataBean data;
    private String msg;
    private String responseCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public static class DataBean {
        private List<ChronicListBean> chronicList;

        public List<ChronicListBean> getChronicList() {
            return chronicList;
        }

        public void setChronicList(List<ChronicListBean> chronicList) {
            this.chronicList = chronicList;
        }

        public static class ChronicListBean {
            /**
             * createTime : 1497582072000
             * id : 3
             * medicalTypeId : 10
             * name : 胃胀
             */

            private long createTime;
            private String id;
            private int medicalTypeId;
            private String name;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getMedicalTypeId() {
                return medicalTypeId;
            }

            public void setMedicalTypeId(int medicalTypeId) {
                this.medicalTypeId = medicalTypeId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
