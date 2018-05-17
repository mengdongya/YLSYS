package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;

/**
 * Created by hxk on 2017/7/20 0020 16:25
 * 医生用户登陆后详细信息解析
 */

public class DoctUserInfo implements Serializable{

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

    public static class DataBean implements Serializable{
        /**
         * juniorStrong : 擅长内科外科
         * bankInfo : 17316334998
         * identityId : wit291141
         * nickName : 医生一
         * facilitatingAgency : 中科院
         * imgPath :
         * professionalTitle : 特级专家
         * workingExperience : 就职于解放军853医院
         * identityCard : 41032919800926
         * doctorName : 终南山
         * telephone : 13526749730
         */

        private String juniorStrong;
        private String bankInfo;
        private String identityId;
        private String nickName;
        private String facilitatingAgency;
        private String imgPath;
        private String professionalTitle;
        private String workingExperience;
        private String identityCard;
        private String doctorName;
        private String telephone;

        public String getJuniorStrong() {
            return juniorStrong;
        }

        public void setJuniorStrong(String juniorStrong) {
            this.juniorStrong = juniorStrong;
        }

        public String getBankInfo() {
            return bankInfo;
        }

        public void setBankInfo(String bankInfo) {
            this.bankInfo = bankInfo;
        }

        public String getIdentityId() {
            return identityId;
        }

        public void setIdentityId(String identityId) {
            this.identityId = identityId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getFacilitatingAgency() {
            return facilitatingAgency;
        }

        public void setFacilitatingAgency(String facilitatingAgency) {
            this.facilitatingAgency = facilitatingAgency;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getProfessionalTitle() {
            return professionalTitle;
        }

        public void setProfessionalTitle(String professionalTitle) {
            this.professionalTitle = professionalTitle;
        }

        public String getWorkingExperience() {
            return workingExperience;
        }

        public void setWorkingExperience(String workingExperience) {
            this.workingExperience = workingExperience;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
