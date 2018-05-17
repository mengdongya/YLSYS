package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by hxk on 2017/9/22 0022 14:34
 * 首页 健康咨询信息
 */

public class HealthInfoBean {
    /**
     * data : [{"contentUrl":"http://10.10.0.95:8887/aorun-medical-wap/knowledge/gestationList?id=16","iconUrl":"http://image.39.net/auth/t/7491.jpg","id":16,"lexiconName":"减肥","titleName":"怎么减都不瘦？竟然与你体内的细菌有关！"},{"contentUrl":"http://10.10.0.95:8887/aorun-medical-wap/knowledge/gestationList?id=17","iconUrl":"http://pimg.39.net/PictureLib/A/f76/20160926/org_762823.jpg","id":17,"lexiconName":"保养","titleName":"科学保养有诀窍 强健骨骼不放\u201c松\u201d"},{"contentUrl":"http://10.10.0.95:8887/aorun-medical-wap/knowledge/gestationList?id=18","iconUrl":"http://pimg.39.net/PictureLib/A/f76/20160616/org_722168.png","id":18,"lexiconName":"可乐","titleName":"这样开可乐，怎么乱晃都绝对不会喷"}]
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private String msg;
    private String responseCode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * contentUrl : http://10.10.0.95:8887/aorun-medical-wap/knowledge/gestationList?id=16
         * iconUrl : http://image.39.net/auth/t/7491.jpg
         * id : 16
         * lexiconName : 减肥
         * titleName : 怎么减都不瘦？竟然与你体内的细菌有关！
         */

        private String contentUrl;
        private String iconUrl;
        private int id;
        private String lexiconName;
        private String titleName;

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLexiconName() {
            return lexiconName;
        }

        public void setLexiconName(String lexiconName) {
            this.lexiconName = lexiconName;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
    }
}
