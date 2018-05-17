package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjc on 2018/2/1 0001.
 */
public class ChronicDiseaseBean implements Serializable{
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
        private List<ChronicPatientDto> chronicPatientDtos;

        public List<ChronicPatientDto> getChronicPatientDtos() {
            return chronicPatientDtos;
        }

        public void setChronicPatientDtos(List<ChronicPatientDto> chronicPatientDtos) {
            this.chronicPatientDtos = chronicPatientDtos;
        }

        public static class ChronicPatientDto implements Serializable{
            private int chronicMemberId;
            private ChronicDto chronicDto;
            private List<PatientDto> patientDtos;

            public int getChronicMemberId() {
                return chronicMemberId;
            }

            public void setChronicMemberId(int chronicMemberId) {
                this.chronicMemberId = chronicMemberId;
            }

            public ChronicDto getChronicDto() {
                return chronicDto;
            }

            public void setChronicDto(ChronicDto chronicDto) {
                this.chronicDto = chronicDto;
            }

            public List<PatientDto> getPatientDtos() {
                return patientDtos;
            }

            public void setPatientDtos(List<PatientDto> patientDtos) {
                this.patientDtos = patientDtos;
            }

            public static class ChronicDto implements Serializable{
                private int chronicId;
                private String name;
                private String iconUrl;
                private long createTime;

                public int getChronicId() {
                    return chronicId;
                }

                public void setChronicId(int chronicId) {
                    this.chronicId = chronicId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }
            }

            public static class PatientDto implements Serializable{
                private String age;
                private String medicine1;
                private String name;
                private String patientId;
                private String iconUrl;
                private String service;
                private int sex;
                private String starTime;
                private String suggestion;
                private String therapeuticRehabilitaitn;
                private String height;
                private String weight;
                private List<DiseaseRecordDtosBean> diseaseRecordDtos;

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getAge() {
                    return age;
                }

                public void setAge(String age) {
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
}
