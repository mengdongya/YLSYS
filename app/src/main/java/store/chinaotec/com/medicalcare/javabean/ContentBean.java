package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/8/1 0001 15:25
 * 突发伤病 慢性病详情信息解析获取
 */

public class ContentBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"sickDetailInfo":"bbb|||||cccccdfds|||||","name":"aaaa|||||ccc|||||"}
     */

    private String msg;
    private int responseCode;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sickDetailInfo : bbb|||||cccccdfds|||||
         * name : aaaa|||||ccc|||||
         */

        private String sickDetailInfo;
        private String name;

        public String getSickDetailInfo() {
            return sickDetailInfo;
        }

        public void setSickDetailInfo(String sickDetailInfo) {
            this.sickDetailInfo = sickDetailInfo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
