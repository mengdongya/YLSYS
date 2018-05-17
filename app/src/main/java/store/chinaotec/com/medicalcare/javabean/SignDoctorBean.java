package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by seven on 2018/1/16 0016.
 */
public class SignDoctorBean {
    private String msg;
    private String responseCode;
    private List<SignDoctor> data;

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

    public List<SignDoctor> getData() {
        return data;
    }

    public void setData(List<SignDoctor> data) {
        this.data = data;
    }

    public static class SignDoctor{
        private String summary;
        private String starLevel;
        private String hospitalLevelName;
        private String headImage;
        private String doctorLabel;
        private String name;
        private String hospitalName;
        private String code;
        private int appointment;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(String starLevel) {
            this.starLevel = starLevel;
        }

        public String getHospitalLevelName() {
            return hospitalLevelName;
        }

        public void setHospitalLevelName(String hospitalLevelName) {
            this.hospitalLevelName = hospitalLevelName;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getDoctorLabel() {
            return doctorLabel;
        }

        public void setDoctorLabel(String doctorLabel) {
            this.doctorLabel = doctorLabel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getAppointment() {
            return appointment;
        }

        public void setAppointment(int appointment) {
            this.appointment = appointment;
        }
    }
}
