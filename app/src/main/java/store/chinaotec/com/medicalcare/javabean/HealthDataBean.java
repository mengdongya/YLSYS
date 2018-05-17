package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by hxk on 2017/10/17 0017 14:12
 * 慢性病模块健康信息
 */

public class HealthDataBean {

    /**
     * data : {"patientDateViewDtos":[{"fromX":"2017-10-17","patientDateDto":{"bloodFat":"62.5","bloodSugar":"70.5","createTime":1508230616120,"diastolicPressure":"310.0","heartRate":"74.0","patientId":3,"systolicPressure":"77.5"}}]}
     * msg : 请求数据成功!
     * responseCode : 0
     */

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
        private List<PatientDateViewDtosBean> patientDateViewDtos;

        public List<PatientDateViewDtosBean> getPatientDateViewDtos() {
            return patientDateViewDtos;
        }

        public void setPatientDateViewDtos(List<PatientDateViewDtosBean> patientDateViewDtos) {
            this.patientDateViewDtos = patientDateViewDtos;
        }

        public static class PatientDateViewDtosBean {
            /**
             * fromX : 2017-10-17
             * patientDateDto : {"bloodFat":"62.5","bloodSugar":"70.5","createTime":1508230616120,"diastolicPressure":"310.0","heartRate":"74.0","patientId":3,"systolicPressure":"77.5"}
             */

            private String fromX;
            private PatientDateDtoBean patientDateDto;

            public String getFromX() {
                return fromX;
            }

            public void setFromX(String fromX) {
                this.fromX = fromX;
            }

            public PatientDateDtoBean getPatientDateDto() {
                return patientDateDto;
            }

            public void setPatientDateDto(PatientDateDtoBean patientDateDto) {
                this.patientDateDto = patientDateDto;
            }

            public static class PatientDateDtoBean {
                /**
                 * bloodFat : 62.5
                 * bloodSugar : 70.5
                 * createTime : 1508230616120
                 * diastolicPressure : 310.0
                 * heartRate : 74.0
                 * patientId : 3
                 * systolicPressure : 77.5
                 */

                private Float bloodFat;
                private Float bloodSugar;
                private long createTime;
                private Float diastolicPressure;
                private Float heartRate;
                private int patientId;
                private Float systolicPressure;

                public Float getBloodFat() {
                    return bloodFat;
                }

                public void setBloodFat(Float bloodFat) {
                    this.bloodFat = bloodFat;
                }

                public Float getBloodSugar() {
                    return bloodSugar;
                }

                public void setBloodSugar(Float bloodSugar) {
                    this.bloodSugar = bloodSugar;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public Float getDiastolicPressure() {
                    return diastolicPressure;
                }

                public void setDiastolicPressure(Float diastolicPressure) {
                    this.diastolicPressure = diastolicPressure;
                }

                public Float getHeartRate() {
                    return heartRate;
                }

                public void setHeartRate(Float heartRate) {
                    this.heartRate = heartRate;
                }

                public int getPatientId() {
                    return patientId;
                }

                public void setPatientId(int patientId) {
                    this.patientId = patientId;
                }

                public Float getSystolicPressure() {
                    return systolicPressure;
                }

                public void setSystolicPressure(Float systolicPressure) {
                    this.systolicPressure = systolicPressure;
                }
            }
        }
    }
}
