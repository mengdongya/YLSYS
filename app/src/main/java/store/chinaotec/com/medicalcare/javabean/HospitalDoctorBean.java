package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by seven on 2017/12/20 0020.
 */
public class HospitalDoctorBean {
    private DataBean data;
    private String msg;
    private String responseCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        private List<DoctorBeans> doctorList;

        public List<DoctorBeans> getDoctorList() {
            return doctorList;
        }

        public void setDoctorList(List<DoctorBeans> doctorList) {
            this.doctorList = doctorList;
        }

        public static class DoctorBeans{
            private String starLevel;
            private String name;
            private String doctorLabel;
            private String headImage;
            private String hospitalName;
            private String code;
            private int appointment;
            private String summary;

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getDoctorLabel() {
                return doctorLabel;
            }

            public void setDoctorLabel(String doctorLabel) {
                this.doctorLabel = doctorLabel;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }


            public int getAppointment() {
                return appointment;
            }

            public void setAppointment(int appointment) {
                this.appointment = appointment;
            }

            public String getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(String starLevel) {
                this.starLevel = starLevel;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

        }
    }
}
