package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxk on 2017/10/16 0016 15:11
 * 慢性病模块返回的病人信息数据
 */

public class PatientBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"chronicPatientDtos":[{"chronicMemberId":147,"chronicDto":{"chronicId":56,"name":"糖尿病I型","iconUrl":null,"createTime":1510889723000},"patientDtos":[{"patientId":214,"iconUrl":null,"name":null,"sex":null,"age":null,"medicine1":null,"starTime":"","therapeuticRehabilitaitn":"1111111111111111","suggestion":"2222222222222","service":"3333333333333333333","diseaseRecordDtos":[{"diseaseRecordId":27,"bloodFatTg":"28","bloodFatLdl":"58","bloodFatHdl":"58","fundoscopy":"Ⅱ期","diseaseDesc":"酷兔兔","createTime":"2017-12-05"}]}]}]}
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
             * chronicMemberId : 147
             * chronicDto : {"chronicId":56,"name":"糖尿病I型","iconUrl":null,"createTime":1510889723000}
             * patientDtos : [{"patientId":214,"iconUrl":null,"name":null,"sex":null,"age":null,"medicine1":null,"starTime":"","therapeuticRehabilitaitn":"1111111111111111","suggestion":"2222222222222","service":"3333333333333333333","diseaseRecordDtos":[{"diseaseRecordId":27,"bloodFatTg":"28","bloodFatLdl":"58","bloodFatHdl":"58","fundoscopy":"Ⅱ期","diseaseDesc":"酷兔兔","createTime":"2017-12-05"}]}]
             */

            private int chronicMemberId;
            private ChronicDtoBean chronicDto;
            private List<PatientDtosBean> patientDtos;

            public int getChronicMemberId() {
                return chronicMemberId;
            }

            public void setChronicMemberId(int chronicMemberId) {
                this.chronicMemberId = chronicMemberId;
            }

            public ChronicDtoBean getChronicDto() {
                return chronicDto;
            }

            public void setChronicDto(ChronicDtoBean chronicDto) {
                this.chronicDto = chronicDto;
            }

            public List<PatientDtosBean> getPatientDtos() {
                return patientDtos;
            }

            public void setPatientDtos(List<PatientDtosBean> patientDtos) {
                this.patientDtos = patientDtos;
            }

            public static class ChronicDtoBean {
                /**
                 * chronicId : 56
                 * name : 糖尿病I型
                 * iconUrl : null
                 * createTime : 1510889723000
                 */

                private int chronicId;
                private String name;
                private Object iconUrl;
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

                public Object getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(Object iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }
            }

            public static class PatientDtosBean {
                /**
                 * patientId : 214
                 * iconUrl : null
                 * name : null
                 * sex : null
                 * age : null
                 * medicine1 : null
                 * starTime :
                 * therapeuticRehabilitaitn : 1111111111111111
                 * suggestion : 2222222222222
                 * service : 3333333333333333333
                 * diseaseRecordDtos : [{"diseaseRecordId":27,"bloodFatTg":"28","bloodFatLdl":"58","bloodFatHdl":"58","fundoscopy":"Ⅱ期","diseaseDesc":"酷兔兔","createTime":"2017-12-05"}]
                 */

                private int patientId;
                private String iconUrl;
                private String name;
                private String sex;
                private String age;
                private String medicine1;
                private String starTime;
                private String therapeuticRehabilitaitn;
                private String suggestion;
                private String service;
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

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
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

                public String getStarTime() {
                    return starTime;
                }

                public void setStarTime(String starTime) {
                    this.starTime = starTime;
                }

                public String getTherapeuticRehabilitaitn() {
                    return therapeuticRehabilitaitn;
                }

                public void setTherapeuticRehabilitaitn(String therapeuticRehabilitaitn) {
                    this.therapeuticRehabilitaitn = therapeuticRehabilitaitn;
                }

                public String getSuggestion() {
                    return suggestion;
                }

                public void setSuggestion(String suggestion) {
                    this.suggestion = suggestion;
                }

                public String getService() {
                    return service;
                }

                public void setService(String service) {
                    this.service = service;
                }

                public List<DiseaseRecordDtosBean> getDiseaseRecordDtos() {
                    return diseaseRecordDtos;
                }

                public void setDiseaseRecordDtos(List<DiseaseRecordDtosBean> diseaseRecordDtos) {
                    this.diseaseRecordDtos = diseaseRecordDtos;
                }

                public static class DiseaseRecordDtosBean implements Serializable {
                    /**
                     * diseaseRecordId : 27
                     * bloodFatTg : 28
                     * bloodFatLdl : 58
                     * bloodFatHdl : 58
                     * fundoscopy : Ⅱ期
                     * diseaseDesc : 酷兔兔
                     * createTime : 2017-12-05
                     */

                    private int diseaseRecordId;
                    private String bloodFatTg;
                    private String bloodFatLdl;
                    private String bloodFatHdl;
                    private String fundoscopy;
                    private String diseaseDesc;
                    private String createTime;

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
                }
            }
        }
    }
}
