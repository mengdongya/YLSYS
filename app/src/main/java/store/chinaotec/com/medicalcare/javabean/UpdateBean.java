package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/8/9 0009 14:52
 */

public class UpdateBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"name":"五台山","version":10,"content":"有新版本更新了。。。","updateTime":"2017-05-10 14:06:56","updateUrl":"","isForce":2}
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
        return data == null ? new DataBean() : data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 五台山
         * version : 10
         * content : 有新版本更新了。。。
         * updateTime : 2017-05-10 14:06:56
         * updateUrl :
         * isForce : 2
         */

        private String name;
        private int version;
        private String content;
        private String updateTime;
        private String updateUrl;
        private int isForce;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }
    }
}
