package store.chinaotec.com.medicalcare.javabean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxk on 2017/10/11 0011 11:26
 * 慢性病模块首页数据获取接口javabean
 */
public class ChronicBean {
    /**
     * data : {"chronicPatientDtos":[{"chronicDto":{"chronicId":56,"createTime":1510889723000,"name":"糖尿病I型"},"chronicMemberId":174,"patientDtos":[{"age":13,"diseaseRecordDtos":[{"bloodFatHdl":"434","bloodFatLdl":"131","bloodFatTg":"12345","createTime":"2017-12-06","diseaseDesc":"1343","diseaseRecordId":44,"fundoscopy":"Ⅱ期"}],"medicine1":"1234566666666686","name":"123556","patientId":241,"service":"333333333333333","sex":0,"starTime":"2017-12-06","suggestion":"22222222222","therapeuticRehabilitaitn":"11111111111111"}]},{"chronicDto":{"chronicId":57,"createTime":1510889723000,"name":"糖尿病II型"},"chronicMemberId":197,"patientDtos":[{"age":11,"diseaseRecordDtos":[],"medicine1":"13434","name":"131","patientId":264,"sex":0,"starTime":"2006-12-06"}]},{"chronicDto":{"chronicId":58,"createTime":1510889723000,"name":"高血压"},"chronicMemberId":202,"patientDtos":[{"diseaseRecordDtos":[],"patientId":269,"starTime":""}]},{"chronicDto":{"chronicId":62,"createTime":1510889723000,"name":"甲亢"},"chronicMemberId":203,"patientDtos":[{"diseaseRecordDtos":[],"patientId":270,"starTime":""}]}]}
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private DataBean data;
    private String msg;
    private String responseCode;

    public DataBean getData() {
        return data == null ? new DataBean() : data;
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
        private List<ChronicPatientDtosBean> chronicPatientDtos;

        public List<ChronicPatientDtosBean> getChronicPatientDtos() {
            return chronicPatientDtos == null ? new ArrayList<ChronicPatientDtosBean>() : chronicPatientDtos;
        }

        public void setChronicPatientDtos(List<ChronicPatientDtosBean> chronicPatientDtos) {
            this.chronicPatientDtos = chronicPatientDtos;
        }

        public static class ChronicPatientDtosBean {
            /**
             * chronicDto : {"chronicId":56,"createTime":1510889723000,"name":"糖尿病I型"}
             * chronicMemberId : 174
             * patientDtos : [{"age":13,"diseaseRecordDtos":[{"bloodFatHdl":"434","bloodFatLdl":"131","bloodFatTg":"12345","createTime":"2017-12-06","diseaseDesc":"1343","diseaseRecordId":44,"fundoscopy":"Ⅱ期"}],"medicine1":"1234566666666686","name":"123556","patientId":241,"service":"333333333333333","sex":0,"starTime":"2017-12-06","suggestion":"22222222222","therapeuticRehabilitaitn":"11111111111111"}]
             */

            private ChronicDtoBean chronicDto;
            private String chronicMemberId;
            private List<PatientDtosBean> patientDtos;

            public ChronicDtoBean getChronicDto() {
                return chronicDto;
            }

            public void setChronicDto(ChronicDtoBean chronicDto) {
                this.chronicDto = chronicDto;
            }

            public String getChronicMemberId() {
                return chronicMemberId;
            }

            public void setChronicMemberId(String chronicMemberId) {
                this.chronicMemberId = chronicMemberId;
            }

            public List<PatientDtosBean> getPatientDtos() {
                return patientDtos == null ? new ArrayList<PatientDtosBean>() : patientDtos;
            }

            public void setPatientDtos(List<PatientDtosBean> patientDtos) {
                this.patientDtos = patientDtos;
            }

            public static class ChronicDtoBean {
                /**
                 * chronicId : 56
                 * createTime : 1510889723000
                 * name : 糖尿病I型
                 */

                private String chronicId;
                private long createTime;
                private String name;

                public String getChronicId() {
                    return chronicId;
                }

                public void setChronicId(String chronicId) {
                    this.chronicId = chronicId;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class PatientDtosBean {
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

                public static class DiseaseRecordDtosBean {
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
                }
            }
        }
    }
}
