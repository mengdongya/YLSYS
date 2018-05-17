package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by seven on 2018/1/23 0023.
 */
public class MedicalKnowledgeBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private String msg;
    private String responseCode;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MedicalBookListBean> medicalBookList;

        public List<MedicalBookListBean> getMedicalBookList() {
            return medicalBookList;
        }

        public void setMedicalBookList(List<MedicalBookListBean> medicalBookList) {
            this.medicalBookList = medicalBookList;
        }

        public static class MedicalBookListBean{
            private String name;
            private String image;
            private int memberSickDealId;
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getMemberSickDealId() {
                return memberSickDealId;
            }

            public void setMemberSickDealId(int memberSickDealId) {
                this.memberSickDealId = memberSickDealId;
            }
        }
    }
}
