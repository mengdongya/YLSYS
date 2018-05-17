package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 * 突发伤病后台数据获取
 */

public class SuddenDiseBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"medicalTypeList":[{"dataList":[{"memberSickDealList":[{"name":"肿胀","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":8},{"name":"坏死","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":9},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":10}],"typeLabelName":"咬蛰伤"},{"memberSickDealList":[{"name":"烧伤","image":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","memberSickDealId":5},{"name":"车祸","image":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","memberSickDealId":6},{"name":"坠落","image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png\r\n/sickdeal/06.png\r\n/sickdeal/08.p","memberSickDealId":7}],"typeLabelName":"创伤"},{"memberSickDealList":[{"name":"脑中风","image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","memberSickDealId":1},{"name":"心肌梗塞","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":2},{"name":"休克","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":3},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":4}],"typeLabelName":"突发疾病"}],"typeName":"全部","typeImage":"","medicalTypeId":0},{"dataList":[{"memberSickDealList":[{"name":"脑中风","image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","memberSickDealId":1},{"name":"心肌梗塞","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":2},{"name":"休克","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":3},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":4}],"typeLabelName":"突发疾病"}],"typeName":"突发疾病","typeImage":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","medicalTypeId":1},{"dataList":[{"memberSickDealList":[{"name":"烧伤","image":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","memberSickDealId":5},{"name":"车祸","image":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","memberSickDealId":6},{"name":"坠落","image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png\r\n/sickdeal/06.png\r\n/sickdeal/08.p","memberSickDealId":7}],"typeLabelName":"创伤"}],"typeName":"创伤","typeImage":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","medicalTypeId":2},{"dataList":[{"memberSickDealList":[{"name":"肿胀","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":8},{"name":"坏死","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":9},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":10}],"typeLabelName":"咬蛰伤"}],"typeName":"咬蛰伤","typeImage":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","medicalTypeId":3}]}
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
        private List<MedicalTypeListBean> medicalTypeList;

        public List<MedicalTypeListBean> getMedicalTypeList() {
            return medicalTypeList;
        }

        public void setMedicalTypeList(List<MedicalTypeListBean> medicalTypeList) {
            this.medicalTypeList = medicalTypeList;
        }

        public static class MedicalTypeListBean {
            /**
             * dataList : [{"memberSickDealList":[{"name":"肿胀","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":8},{"name":"坏死","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":9},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":10}],"typeLabelName":"咬蛰伤"},{"memberSickDealList":[{"name":"烧伤","image":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","memberSickDealId":5},{"name":"车祸","image":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","memberSickDealId":6},{"name":"坠落","image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png\r\n/sickdeal/06.png\r\n/sickdeal/08.p","memberSickDealId":7}],"typeLabelName":"创伤"},{"memberSickDealList":[{"name":"脑中风","image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","memberSickDealId":1},{"name":"心肌梗塞","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":2},{"name":"休克","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":3},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":4}],"typeLabelName":"突发疾病"}]
             * typeName : 全部
             * typeImage :
             * medicalTypeId : 0
             */

            private String typeName;
            private String typeImage;
            private int medicalTypeId;
            private List<DataListBean> dataList;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getTypeImage() {
                return typeImage;
            }

            public void setTypeImage(String typeImage) {
                this.typeImage = typeImage;
            }

            public int getMedicalTypeId() {
                return medicalTypeId;
            }

            public void setMedicalTypeId(int medicalTypeId) {
                this.medicalTypeId = medicalTypeId;
            }

            public List<DataListBean> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBean> dataList) {
                this.dataList = dataList;
            }

            public static class DataListBean {
                /**
                 * memberSickDealList : [{"name":"肿胀","image":"http://219.144.68.15/img/userfiles//images//sickdeal/06.png","memberSickDealId":8},{"name":"坏死","image":"http://219.144.68.15/img/userfiles//images//sickdeal/08.png","memberSickDealId":9},{"name":"中毒","image":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","memberSickDealId":10}]
                 * typeLabelName : 咬蛰伤
                 */

                private String typeLabelName;
                private List<MemberSickDealListBean> memberSickDealList;

                public String getTypeLabelName() {
                    return typeLabelName;
                }

                public void setTypeLabelName(String typeLabelName) {
                    this.typeLabelName = typeLabelName;
                }

                public List<MemberSickDealListBean> getMemberSickDealList() {
                    return memberSickDealList;
                }

                public void setMemberSickDealList(List<MemberSickDealListBean> memberSickDealList) {
                    this.memberSickDealList = memberSickDealList;
                }

                public static class MemberSickDealListBean {
                    /**
                     * name : 肿胀
                     * image : http://219.144.68.15/img/userfiles//images//sickdeal/06.png
                     * memberSickDealId : 8
                     */

                    private String name;
                    private String image;
                    private int memberSickDealId;
                    private String url;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public int getMemberSickDealId() {
                        return memberSickDealId;
                    }

                    public void setMemberSickDealId(int memberSickDealId) {
                        this.memberSickDealId = memberSickDealId;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }


                @Override
                public String toString() {
                    return "DataListBean{" +
                            "typeLabelName='" + typeLabelName + '\'' +
                            ", memberSickDealList=" + memberSickDealList +
                            '}';
                }
            }
        }
    }
}
