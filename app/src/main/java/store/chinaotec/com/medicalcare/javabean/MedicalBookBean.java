package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by wjc on 2017/10/20 0010.
 * 医学电子图书
 */

public class MedicalBookBean{
    /**
     * msg : 请求数据成功!
     * responseCode : 0
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

    public static class DataBean{
        private List<MedicalTypeListBean> medicalTypeList;

        public List<MedicalTypeListBean> getMedicalTypeList() {
            return medicalTypeList;
        }

        public void setMedicalTypeList(List<MedicalTypeListBean> medicalTypeList) {
            this.medicalTypeList = medicalTypeList;
        }

        public static class MedicalTypeListBean{
            /**
             * typeImage :
             * medicalTypeId : 0
             */

            private String typeName;
            private String typeImage;
            private int medicalTypeId;
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

                    public static class ChildTypeThree{
                        /**
                         * memberSickDealList : []
                         * typeLabelName : 泌尿系统
                         */

                        private String typeLabelName;
                        private List<MemberSickDealListBean> medicalBookList;

                        public String getTypeLabelName() {
                            return typeLabelName;
                        }

                        public void setTypeLabelName(String typeLabelName) {
                            this.typeLabelName = typeLabelName;
                        }

                        public List<MemberSickDealListBean> getMedicalBookList() {
                            return medicalBookList;
                        }

                        public void setMedicalBookList(List<MemberSickDealListBean> medicalBookList) {
                            this.medicalBookList = medicalBookList;
                        }

                        public static class MemberSickDealListBean{
                            private String image;
                            private String name;
                            private int memberSickDealId;
                            private String url;

                            public String getUrl() {
                                return url;
                            }

                            public void setUrl(String url) {
                                this.url = url;
                            }

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

            public static class DataListBean{

                private String typeLabelName;
                private List<MemberSickDealListBean> medicalBookList;

                public String getTypeLabelName() {
                    return typeLabelName;
                }

                public void setTypeLabelName(String typeLabelName) {
                    this.typeLabelName = typeLabelName;
                }

                public List<MemberSickDealListBean> getMedicalBookList() {
                    return medicalBookList;
                }

                public void setMedicalBookList(List<MemberSickDealListBean> medicalBookList) {
                    this.medicalBookList = medicalBookList;
                }

                public static class MemberSickDealListBean{

                    private String name;
                    private String image;
                    private int memberSickDealId;
                    private String url;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

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
                }
            }
        }
    }
}
