package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by seven on 2018/3/23 0023.
 */

public class LinkMessageInfo {
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
        private List<RefMemberInfoList> dataList;

        public List<RefMemberInfoList> getDataList() {
            return dataList;
        }

        public void setDataList(List<RefMemberInfoList> dataList) {
            this.dataList = dataList;
        }

        public static class RefMemberInfoList{
            private int messageId;
            private int memberRefId;
            private String memberIcon;
            private String body;
            private int isReade;
            private String memberNickName;
            private String telePhone;
            private long createTime;
            private int checkUp;

            public int getCheckUp() {
                return checkUp;
            }

            public void setCheckUp(int checkUp) {
                this.checkUp = checkUp;
            }

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public int getMemberRefId() {
                return memberRefId;
            }

            public void setMemberRefId(int memberRefId) {
                this.memberRefId = memberRefId;
            }

            public String getMemberIcon() {
                return memberIcon;
            }

            public void setMemberIcon(String memberIcon) {
                this.memberIcon = memberIcon;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public int getIsReade() {
                return isReade;
            }

            public void setIsReade(int isReade) {
                this.isReade = isReade;
            }

            public String getMemberNickName() {
                return memberNickName;
            }

            public void setMemberNickName(String memberNickName) {
                this.memberNickName = memberNickName;
            }

            public String getTelePhone() {
                return telePhone;
            }

            public void setTelePhone(String telePhone) {
                this.telePhone = telePhone;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
