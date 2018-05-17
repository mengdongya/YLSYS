package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;

/**
 * Created by hxk on 2017/7/17 0017 18:13
 * 普通用户登录后个人信息对象的解析
 */

public class NormalUserInfo implements Serializable{
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
         * birthday : 2010-6-24
         * domicile : 上海浦东新区
         * sex : 0
         * occupation : 工人
         * weight : 120斤
         * identityId : wit152030
         * identityCard : 410329199009263516
         * nation : 汉族
         * socialInsuranceCard : 9632884258
         * height : 1.72米
         * contactInfo :
         * nickName : 病人
         * imgPath :
         * name : 张三
         * age : 7
         * contactType : 3
         * telephone : 13526749730
         */

        private String birthday;
        private String domicile;
        private int sex;
        private String occupation;
        private String weight;
        private String identityId;
        private String identityCard;
        private String nation;
        private String socialInsuranceCard;
        private String height;
        private String contactInfo;
        private String nickName;
        private String imgPath;
        private String name;
        private String age;
        private String contactType;
        private String telephone;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getDomicile() {
            return domicile;
        }

        public void setDomicile(String domicile) {
            this.domicile = domicile;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getIdentityId() {
            return identityId;
        }

        public void setIdentityId(String identityId) {
            this.identityId = identityId;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getSocialInsuranceCard() {
            return socialInsuranceCard;
        }

        public void setSocialInsuranceCard(String socialInsuranceCard) {
            this.socialInsuranceCard = socialInsuranceCard;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getContactInfo() {
            return contactInfo;
        }

        public void setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getContactType() {
            return contactType;
        }

        public void setContactType(String contactType) {
            this.contactType = contactType;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }
}
