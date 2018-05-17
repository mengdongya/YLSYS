package store.chinaotec.com.medicalcare.javabean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HYY on 2018/1/5.
 */

public class HealthyRecordBean {

    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"patientDto":{"patientId":407,"iconUrl":null,"name":"2333","sex":1,"age":0,"medicine1":null,"starTime":"","therapeuticRehabilitaitn":null,"suggestion":null,"service":null,"height":"25","weight":"23","diseaseRecordDtos":[{"diseaseRecordId":73,"bloodFatTg":"1234","bloodFatLdl":"12345","bloodFatHdl":"4545","fundoscopy":"Ⅱ期","diseaseDesc":"1316","createTime":"2018-01-05","inoculate":"2018-01-05","contraception":"2018-01-05"}]}}
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
        return data == null ? new DataBean() : data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * patientDto : {"patientId":407,"iconUrl":null,"name":"2333","sex":1,"age":0,"medicine1":null,"starTime":"","therapeuticRehabilitaitn":null,"suggestion":null,"service":null,"height":"25","weight":"23","diseaseRecordDtos":[{"diseaseRecordId":73,"bloodFatTg":"1234","bloodFatLdl":"12345","bloodFatHdl":"4545","fundoscopy":"Ⅱ期","diseaseDesc":"1316","createTime":"2018-01-05","inoculate":"2018-01-05","contraception":"2018-01-05"}]}
         */

        private PatientDtoBean patientDto;

        public PatientDtoBean getPatientDto() {
            return patientDto == null ? new PatientDtoBean() : patientDto;
        }

        public void setPatientDto(PatientDtoBean patientDto) {
            this.patientDto = patientDto;
        }

        public static class PatientDtoBean {
            /**
             * patientId : 407
             * iconUrl : null
             * name : 2333
             * sex : 1
             * age : 0
             * medicine1 : null
             * starTime :
             * therapeuticRehabilitaitn : null
             * suggestion : null
             * service : null
             * height : 25
             * weight : 23
             * diseaseRecordDtos : [{"diseaseRecordId":73,"bloodFatTg":"1234","bloodFatLdl":"12345","bloodFatHdl":"4545","fundoscopy":"Ⅱ期","diseaseDesc":"1316","createTime":"2018-01-05","inoculate":"2018-01-05","contraception":"2018-01-05"}]
             */

            private int patientId;
            private Object iconUrl;
            private String name;
            private int sex;
            private int age;
            private Object medicine1;
            private String starTime;
            private Object therapeuticRehabilitaitn;
            private Object suggestion;
            private Object service;
            private String height;
            private String weight;
            private List<DiseaseRecordDtosBean> diseaseRecordDtos;

            public int getPatientId() {
                return patientId;
            }

            public void setPatientId(int patientId) {
                this.patientId = patientId;
            }

            public Object getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(Object iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public Object getMedicine1() {
                return medicine1;
            }

            public void setMedicine1(Object medicine1) {
                this.medicine1 = medicine1;
            }

            public String getStarTime() {
                return starTime;
            }

            public void setStarTime(String starTime) {
                this.starTime = starTime;
            }

            public Object getTherapeuticRehabilitaitn() {
                return therapeuticRehabilitaitn;
            }

            public void setTherapeuticRehabilitaitn(Object therapeuticRehabilitaitn) {
                this.therapeuticRehabilitaitn = therapeuticRehabilitaitn;
            }

            public Object getSuggestion() {
                return suggestion;
            }

            public void setSuggestion(Object suggestion) {
                this.suggestion = suggestion;
            }

            public Object getService() {
                return service;
            }

            public void setService(Object service) {
                this.service = service;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public List<DiseaseRecordDtosBean> getDiseaseRecordDtos() {
                return diseaseRecordDtos == null ? new ArrayList<DiseaseRecordDtosBean>() : diseaseRecordDtos;
            }

            public void setDiseaseRecordDtos(List<DiseaseRecordDtosBean> diseaseRecordDtos) {
                this.diseaseRecordDtos = diseaseRecordDtos;
            }

            public static class DiseaseRecordDtosBean {
                /**
                 * diseaseRecordId : 73
                 * bloodFatTg : 1234
                 * bloodFatLdl : 12345
                 * bloodFatHdl : 4545
                 * fundoscopy : Ⅱ期
                 * diseaseDesc : 1316
                 * createTime : 2018-01-05
                 * inoculate : 2018-01-05
                 * contraception : 2018-01-05
                 */

                private int diseaseRecordId;
                private String bloodFatTg;
                private String bloodFatLdl;
                private String bloodFatHdl;
                private String fundoscopy;
                private String diseaseDesc;
                private String createTime;
                private String inoculate;
                private String contraception;

                public int getDiseaseRecordId() {
                    return diseaseRecordId;
                }

                public void setDiseaseRecordId(int diseaseRecordId) {
                    this.diseaseRecordId = diseaseRecordId;
                }

                public String getBloodFatTg() {
                    return bloodFatTg;
                }

                public void setBloodFatTg(String bloodFatTg) {
                    this.bloodFatTg = bloodFatTg;
                }

                public String getBloodFatLdl() {
                    return bloodFatLdl;
                }

                public void setBloodFatLdl(String bloodFatLdl) {
                    this.bloodFatLdl = bloodFatLdl;
                }

                public String getBloodFatHdl() {
                    return bloodFatHdl;
                }

                public void setBloodFatHdl(String bloodFatHdl) {
                    this.bloodFatHdl = bloodFatHdl;
                }

                public String getFundoscopy() {
                    return fundoscopy;
                }

                public void setFundoscopy(String fundoscopy) {
                    this.fundoscopy = fundoscopy;
                }

                public String getDiseaseDesc() {
                    return diseaseDesc;
                }

                public void setDiseaseDesc(String diseaseDesc) {
                    this.diseaseDesc = diseaseDesc;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getInoculate() {
                    return inoculate;
                }

                public void setInoculate(String inoculate) {
                    this.inoculate = inoculate;
                }

                public String getContraception() {
                    return contraception;
                }

                public void setContraception(String contraception) {
                    this.contraception = contraception;
                }
            }
        }
    }
}
