package store.chinaotec.com.medicalcare.javabean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HYY on 2017/12/4.
 */

public class DiseaseRecordDtos {

    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"chronicPatientDtos":[{"chronicMemberId":131,"chronicDto":{"chronicId":58,"name":"高血压","iconUrl":null,"createTime":1510889723000},"patientDtos":[{"patientId":198,"iconUrl":null,"name":"123456","sex":1,"age":13,"medicine1":"123456","starTime":"","therapeuticRehabilitaitn":null,"suggestion":null,"service":null,"diseaseRecordDtos":[{"diseaseRecordId":4,"bloodFatTg":"23","bloodFatLdl":"55","bloodFatHdl":"56","fundoscopy":"Ⅱ期","diseaseDesc":"123589966669vv回家姐姐呵呵哈哈哈就瓜兮兮私发个vVB别扭扭捏捏刚才不不不不会","createTime":"20171204"}]}]},{"chronicMemberId":102,"chronicDto":{"chronicId":59,"name":"冠心病","iconUrl":null,"createTime":1510889723000},"patientDtos":[{"patientId":169,"iconUrl":null,"name":"楼","sex":0,"age":14,"medicine1":"头目66666","starTime":"","therapeuticRehabilitaitn":null,"suggestion":null,"service":null,"diseaseRecordDtos":[]}]},{"chronicMemberId":132,"chronicDto":{"chronicId":61,"name":"慢性肾炎","iconUrl":null,"createTime":1510889723000},"patientDtos":[{"patientId":199,"iconUrl":null,"name":null,"sex":null,"age":null,"medicine1":null,"starTime":"","therapeuticRehabilitaitn":null,"suggestion":null,"service":null,"diseaseRecordDtos":[]}]}]}
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
             * chronicMemberId : 131
             * chronicDto : {"chronicId":58,"name":"高血压","iconUrl":null,"createTime":1510889723000}
             * patientDtos : [{"patientId":198,"iconUrl":null,"name":"123456","sex":1,"age":13,"medicine1":"123456","starTime":"","therapeuticRehabilitaitn":null,"suggestion":null,"service":null,"diseaseRecordDtos":[{"diseaseRecordId":4,"bloodFatTg":"23","bloodFatLdl":"55","bloodFatHdl":"56","fundoscopy":"Ⅱ期","diseaseDesc":"123589966669vv回家姐姐呵呵哈哈哈就瓜兮兮私发个vVB别扭扭捏捏刚才不不不不会","createTime":"20171204"}]}]
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
                 * chronicId : 58
                 * name : 高血压
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
                 * patientId : 198
                 * iconUrl : null
                 * name : 123456
                 * sex : 1
                 * age : 13
                 * medicine1 : 123456
                 * starTime :
                 * therapeuticRehabilitaitn : null
                 * suggestion : null
                 * service : null
                 * diseaseRecordDtos : [{"diseaseRecordId":4,"bloodFatTg":"23","bloodFatLdl":"55","bloodFatHdl":"56","fundoscopy":"Ⅱ期","diseaseDesc":"123589966669vv回家姐姐呵呵哈哈哈就瓜兮兮私发个vVB别扭扭捏捏刚才不不不不会","createTime":"20171204"}]
                 */

                private int patientId;
                private Object iconUrl;
                private String name;
                private int sex;
                private int age;
                private String medicine1;
                private String starTime;
                private Object therapeuticRehabilitaitn;
                private Object suggestion;
                private Object service;
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

                public List<DiseaseRecordDtosBean> getDiseaseRecordDtos() {
                    return diseaseRecordDtos;
                }

                public void setDiseaseRecordDtos(List<DiseaseRecordDtosBean> diseaseRecordDtos) {
                    this.diseaseRecordDtos = diseaseRecordDtos;
                }

                public static class DiseaseRecordDtosBean {
                    /**
                     * diseaseRecordId : 4
                     * bloodFatTg : 23
                     * bloodFatLdl : 55
                     * bloodFatHdl : 56
                     * fundoscopy : Ⅱ期
                     * diseaseDesc : 123589966669vv回家姐姐呵呵哈哈哈就瓜兮兮私发个vVB别扭扭捏捏刚才不不不不会
                     * createTime : 20171204
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
