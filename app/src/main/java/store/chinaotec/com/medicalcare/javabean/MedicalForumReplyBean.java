package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by wjc on 2017/10/27 0027.
 * 回复列表
 */
public class MedicalForumReplyBean {
    private List<ForumReplyBean> data;
    private String msg;
    private String responseCode;

    public List<ForumReplyBean> getData() {
        return data;
    }

    public void setData(List<ForumReplyBean> data) {
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

    public static class ForumReplyBean{
        private int replyId;
        private int memberId;
        private String memberName;
        private String memberIcon;
        private int topicId;
        private String title;
        private long createTime;
        private String body;

        public int getReplyId() {
            return replyId;
        }

        public void setReplyId(int replyId) {
            this.replyId = replyId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberIcon() {
            return memberIcon;
        }

        public void setMemberIcon(String memberIcon) {
            this.memberIcon = memberIcon;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}
