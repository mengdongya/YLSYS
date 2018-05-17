package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by hxk on 2017/8/2 0002 10:57
 * 检查结果解读解析
 */

public class TextResultBean {
    /**
     * data : {"medicalTypeList":[{"dataList":[{"memberSickDealList":[],"typeLabelName":"泌尿系统"},{"memberSickDealList":[{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","memberSickDealId":12,"name":"yyy"}],"typeLabelName":"消化系统"},{"memberSickDealList":[{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","memberSickDealId":11,"name":"xxx"},{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","memberSickDealId":13,"name":"zzz"}],"typeLabelName":"呼吸系统"}],"medicalTypeId":0,"typeImage":"","typeName":"全部"},{"dataList":[{"memberSickDealList":[{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","memberSickDealId":11,"name":"xxx"},{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","memberSickDealId":13,"name":"zzz"}],"typeLabelName":"呼吸系统"}],"medicalTypeId":4,"typeImage":"http://219.144.68.15/img/userfiles//images//sickdeal/16.png","typeName":"呼吸系统"},{"dataList":[{"memberSickDealList":[{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","memberSickDealId":12,"name":"yyy"}],"typeLabelName":"消化系统"}],"medicalTypeId":5,"typeImage":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","typeName":"消化系统"},{"dataList":[{"memberSickDealList":[],"typeLabelName":"泌尿系统"}],"medicalTypeId":6,"typeImage":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","typeName":"泌尿系统"}]}
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
        private List<MedicalTypeListBean> medicalTypeList;

        public List<MedicalTypeListBean> getMedicalTypeList() {
            return medicalTypeList;
        }

        public void setMedicalTypeList(List<MedicalTypeListBean> medicalTypeList) {
            this.medicalTypeList = medicalTypeList;
        }

        public static class MedicalTypeListBean {
            /**
             * dataList : [{"memberSickDealList":[],"typeLabelName":"泌尿系统"},{"memberSickDealList":[{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/18.png","memberSickDealId":12,"name":"yyy"}],"typeLabelName":"消化系统"},{"memberSickDealList":[{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/17.png","memberSickDealId":11,"name":"xxx"},{"image":"http://219.144.68.15/img/userfiles//images//sickdeal/04.png","memberSickDealId":13,"name":"zzz"}],"typeLabelName":"呼吸系统"}]
             * medicalTypeId : 0
             * typeImage :
             * typeName : 全部
             */

            private int medicalTypeId;
            private String typeImage;
            private String typeName;
            private List<DataListBean> dataList;
            private int level;
            private List<DataChildTypeList> childTypeList;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public List<DataChildTypeList> getChildTypeList() {
                return childTypeList;
            }

            public void setChildTypeList(List<DataChildTypeList> childTypeList) {
                this.childTypeList = childTypeList;
            }

            public int getMedicalTypeId() {
                return medicalTypeId;
            }

            public void setMedicalTypeId(int medicalTypeId) {
                this.medicalTypeId = medicalTypeId;
            }

            public String getTypeImage() {
                return typeImage;
            }

            public void setTypeImage(String typeImage) {
                this.typeImage = typeImage;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public List<DataListBean> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBean> dataList) {
                this.dataList = dataList;
            }

            public static class DataChildTypeList{
                private int medicalTypeId;
                private String typeImage;
                private String typeName;
                private List<DataListBean> dataList;
                private int level;
                private List<DataChildTypeThreeList> childTypeList;

                public int getMedicalTypeId() {
                    return medicalTypeId;
                }

                public void setMedicalTypeId(int medicalTypeId) {
                    this.medicalTypeId = medicalTypeId;
                }

                public String getTypeImage() {
                    return typeImage;
                }

                public void setTypeImage(String typeImage) {
                    this.typeImage = typeImage;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public List<DataListBean> getDataList() {
                    return dataList;
                }

                public void setDataList(List<DataListBean> dataList) {
                    this.dataList = dataList;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public List<DataChildTypeThreeList> getChildTypeList() {
                    return childTypeList;
                }

                public void setChildTypeList(List<DataChildTypeThreeList> childTypeList) {
                    this.childTypeList = childTypeList;
                }

                public static class DataChildTypeThreeList{
                    private int medicalTypeId;
                    private String typeImage;
                    private String typeName;
                    private List<ChildTypeThree> dataList;
                    private int level;

                    public int getMedicalTypeId() {
                        return medicalTypeId;
                    }

                    public void setMedicalTypeId(int medicalTypeId) {
                        this.medicalTypeId = medicalTypeId;
                    }

                    public String getTypeImage() {
                        return typeImage;
                    }

                    public void setTypeImage(String typeImage) {
                        this.typeImage = typeImage;
                    }

                    public String getTypeName() {
                        return typeName;
                    }

                    public void setTypeName(String typeName) {
                        this.typeName = typeName;
                    }

                    public List<ChildTypeThree> getDataList() {
                        return dataList;
                    }

                    public void setDataList(List<ChildTypeThree> dataList) {
                        this.dataList = dataList;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public static class ChildTypeThree {
                        /**
                         * memberSickDealList : []
                         * typeLabelName : 泌尿系统
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
                            private String image;
                            private String name;
                            private int memberSickDealId;

                            public String getImage() {
                                return image;
                            }

                            public void setImage(String image) {
                                this.image = image;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public int getMemberSickDealId() {
                                return memberSickDealId;
                            }

                            public void setMemberSickDealId(int memberSickDealId) {
                                this.memberSickDealId = memberSickDealId;
                            }
                        }
                    }
                }

            }

            public static class DataListBean {
                /**
                 * memberSickDealList : []
                 * typeLabelName : 泌尿系统
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
                    private String image;
                    private String name;
                    private int memberSickDealId;

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getMemberSickDealId() {
                        return memberSickDealId;
                    }

                    public void setMemberSickDealId(int memberSickDealId) {
                        this.memberSickDealId = memberSickDealId;
                    }
                }
            }
        }
    }
}
