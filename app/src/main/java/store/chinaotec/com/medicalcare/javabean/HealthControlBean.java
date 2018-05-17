package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by seven on 2017/12/26 0026.
 */
public class HealthControlBean implements Serializable{
    private String msg;
    private String responseCode;
    private DataBean data;

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

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Serializable{
        private List<MedicalADV> medicalADV;
        private List<PatientDtosBean> patientDtos;

        public List<MedicalADV> getMedicalADV() {
            return medicalADV;
        }

        public void setMedicalADV(List<MedicalADV> medicalADV) {
            this.medicalADV = medicalADV;
        }

        public List<PatientDtosBean> getPatientDtos() {
            return patientDtos;
        }

        public void setPatientDtos(List<PatientDtosBean> patientDtos) {
            this.patientDtos = patientDtos;
        }

        public static class MedicalADV implements Serializable{
            private int id;
            private int medicalTypeId;
            private String name;
            private String img;
            private String url;
            private String body;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMedicalTypeId() {
                return medicalTypeId;
            }

            public void setMedicalTypeId(int medicalTypeId) {
                this.medicalTypeId = medicalTypeId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }
        }
        public static class PatientDtosBean implements Serializable{
            /**
             * age : 13
             * diseaseRecordDtos : [{"bloodFatHdl":"434","bloodFatLdl":"131","bloodFatTg":"12345","createTime":"2017-12-06","diseaseDesc":"1343","diseaseRecordId":44,"fundoscopy":"Ⅱ期"}]
             * medicine1 : 1234566666666686
             * name : 123556
             * patientId : 241
             * service : 333333333333333
             * sex : 0
             * starTime : 2017-12-06
             * suggestion : 22222222222
             * therapeuticRehabilitaitn : 11111111111111
             */

            private int age;
            private String medicine1;
            private String name;
            private String patientId;
            private String service;
            private int sex;
            private String starTime;
            private String suggestion;
            private String therapeuticRehabilitaitn;
            private String height;
            private String weight;
            private List<DiseaseRecordDtosBean> diseaseRecordDtos;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getMedicine1() {
                return medicine1;
            }

            public void setMedicine1(String medicine1) {
                this.medicine1 = medicine1;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPatientId() {
                return patientId;
            }

            public void setPatientId(String patientId) {
                this.patientId = patientId;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getStarTime() {
                return starTime;
            }

            public void setStarTime(String starTime) {
                this.starTime = starTime;
            }

            public String getSuggestion() {
                return suggestion;
            }

            public void setSuggestion(String suggestion) {
                this.suggestion = suggestion;
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

            public String getTherapeuticRehabilitaitn() {
                return therapeuticRehabilitaitn;
            }

            public void setTherapeuticRehabilitaitn(String therapeuticRehabilitaitn) {
                this.therapeuticRehabilitaitn = therapeuticRehabilitaitn;
            }

            public List<DiseaseRecordDtosBean> getDiseaseRecordDtos() {
                return diseaseRecordDtos;
            }

            public void setDiseaseRecordDtos(List<DiseaseRecordDtosBean> diseaseRecordDtos) {
                this.diseaseRecordDtos = diseaseRecordDtos;
            }

            public static class DiseaseRecordDtosBean implements Serializable{
                /**
                 * bloodFatHdl : 434
                 * bloodFatLdl : 131
                 * bloodFatTg : 12345
                 * createTime : 2017-12-06
                 * diseaseDesc : 1343
                 * diseaseRecordId : 44
                 * fundoscopy : Ⅱ期
                 */

                private String bloodFatHdl;
                private String bloodFatLdl;
                private String bloodFatTg;
                private String createTime;
                private String diseaseDesc;
                private int diseaseRecordId;
                private String fundoscopy;
                private String inoculate;
                private String contraception;

                public String getBloodFatHdl() {
                    return bloodFatHdl;
                }

                public void setBloodFatHdl(String bloodFatHdl) {
                    this.bloodFatHdl = bloodFatHdl;
                }

                public String getBloodFatLdl() {
                    return bloodFatLdl;
                }

                public void setBloodFatLdl(String bloodFatLdl) {
                    this.bloodFatLdl = bloodFatLdl;
                }

                public String getBloodFatTg() {
                    return bloodFatTg;
                }

                public void setBloodFatTg(String bloodFatTg) {
                    this.bloodFatTg = bloodFatTg;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getDiseaseDesc() {
                    return diseaseDesc;
                }

                public void setDiseaseDesc(String diseaseDesc) {
                    this.diseaseDesc = diseaseDesc;
                }

                public int getDiseaseRecordId() {
                    return diseaseRecordId;
                }

                public void setDiseaseRecordId(int diseaseRecordId) {
                    this.diseaseRecordId = diseaseRecordId;
                }

                public String getFundoscopy() {
                    return fundoscopy;
                }

                public void setFundoscopy(String fundoscopy) {
                    this.fundoscopy = fundoscopy;
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
