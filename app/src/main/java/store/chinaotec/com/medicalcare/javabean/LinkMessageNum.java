package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;

/**
 * Created by seven on 2018/3/23 0023.
 */

public class LinkMessageNum implements Serializable{
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

    public static class DataBean implements Serializable{
        private int  remindNum;
        private int noticeNum;
        private NoticeTop noticeTop;

        public int getRemindNum() {
            return remindNum;
        }

        public void setRemindNum(int remindNum) {
            this.remindNum = remindNum;
        }

        public int getNoticeNum() {
            return noticeNum;
        }

        public void setNoticeNum(int noticeNum) {
            this.noticeNum = noticeNum;
        }

        public NoticeTop getNoticeTop() {
            return noticeTop;
        }

        public void setNoticeTop(NoticeTop noticeTop) {
            this.noticeTop = noticeTop;
        }

        public static class NoticeTop implements Serializable{
            private int messageId;
            private String memberIcon;
            private String memberNickName;
            private String telePhone;
            private String body;
            private int memberRefId;
            private long createTime;

            public int getMessageId() {
                return messageId;
            }

            public void setMessageId(int messageId) {
                this.messageId = messageId;
            }

            public String getMemberIcon() {
                return memberIcon;
            }

            public void setMemberIcon(String memberIcon) {
                this.memberIcon = memberIcon;
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

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public int getMemberRefId() {
                return memberRefId;
            }

            public void setMemberRefId(int memberRefId) {
                this.memberRefId = memberRefId;
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
