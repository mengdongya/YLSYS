package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;

/**
 * Created by seven on 2018/3/21 0021.
 */

public class LinkManBean {
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

    public static class DataBean{
        private List<RefMemberInfos> refMemberInfos;

        public List<RefMemberInfos> getRefMemberInfos() {
            return refMemberInfos;
        }

        public void setRefMemberInfos(List<RefMemberInfos> refMemberInfos) {
            this.refMemberInfos = refMemberInfos;
        }

        public static class RefMemberInfos{
            private int chronicMemberRefId;
            private int memberId;
            private String img;
            private String nickName;
            private String phone;

            public int getChronicMemberRefId() {
                return chronicMemberRefId;
            }

            public void setChronicMemberRefId(int chronicMemberRefId) {
                this.chronicMemberRefId = chronicMemberRefId;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }


}
