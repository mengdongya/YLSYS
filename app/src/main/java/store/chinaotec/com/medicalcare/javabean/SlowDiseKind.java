package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by hxk on 2017/6/21 0021.
 * 慢性病种类解析javabean
 */
public class SlowDiseKind {

    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : {"medicalTypes":[{"id":10,"typeName":"消化系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":11,"typeName":"免疫系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":12,"typeName":"呼吸系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":13,"typeName":"神经系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":14,"typeName":"循环系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":15,"typeName":"内分泌系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":16,"typeName":"泌尿生殖系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null},{"id":17,"typeName":"骨骼系统","typeImage":null,"type":"5","createTime":1506043901000,"updateTime":null}]}
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
        private List<MedicalTypesBean> medicalTypes;

        public List<MedicalTypesBean> getMedicalTypes() {
            return medicalTypes;
        }

        public void setMedicalTypes(List<MedicalTypesBean> medicalTypes) {
            this.medicalTypes = medicalTypes;
        }

        public static class MedicalTypesBean {
            /**
             * id : 10
             * typeName : 消化系统
             * typeImage : null
             * type : 5
             * createTime : 1506043901000
             * updateTime : null
             */

            private int id;
            private String typeName;
            private Object typeImage;
            private String type;
            private long createTime;
            private Object updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public Object getTypeImage() {
                return typeImage;
            }

            public void setTypeImage(Object typeImage) {
                this.typeImage = typeImage;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
