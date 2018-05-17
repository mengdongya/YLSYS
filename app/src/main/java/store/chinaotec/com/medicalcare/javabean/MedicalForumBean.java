package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by seven on 2017/10/26 0026.
 */
public class MedicalForumBean {
    private List<ForumBean> data;
    private String msg;
    private String responseCode;

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

    public List<ForumBean> getData() {
        return data;
    }

    public void setData(List<ForumBean> data) {
        this.data = data;
    }

    public static class ForumBean {
        private String topicId;
        private int classId;
        private int memberId;
        private String memberName;
        private String memberIcon;
        private long createTime;
        private String imageUrl;
        private String body;
        private String bodyImageUrls;
        private String nickName;
        private String likeCount;
        private String replyCount;
        private String isReply;
        private String isLikes;
        private List<MedicalDto> bbsReplyMedicalDtos;

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public int getClassId() {
            return classId;
        }

        public void setClassId(int classId) {
            this.classId = classId;
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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getBodyImageUrls() {
            return bodyImageUrls;
        }

        public void setBodyImageUrls(String bodyImageUrls) {
            this.bodyImageUrls = bodyImageUrls;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(String replyCount) {
            this.replyCount = replyCount;
        }

        public String getIsReply() {
            return isReply;
        }

        public void setIsReply(String isReply) {
            this.isReply = isReply;
        }

        public String getIsLikes() {
            return isLikes;
        }

        public void setIsLikes(String isLikes) {
            this.isLikes = isLikes;
        }

        public List<MedicalDto> getBbsReplyMedicalDtos() {
            return bbsReplyMedicalDtos;
        }

        public void setBbsReplyMedicalDtos(List<MedicalDto> bbsReplyMedicalDtos) {
            this.bbsReplyMedicalDtos = bbsReplyMedicalDtos;
        }

        public static class MedicalDto {
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
}
