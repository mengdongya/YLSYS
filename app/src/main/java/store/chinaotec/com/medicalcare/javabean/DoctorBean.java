package store.chinaotec.com.medicalcare.javabean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 * 在线医生解析javabean
 */

public class DoctorBean {
    /**
     * data : {"doctorList":[{"appointment":100,"code":"ys00000008","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第八人民医院","name":"hh","starLevel":"5"},{"appointment":100,"code":"ys00000007","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第七人民医院","name":"gg","starLevel":"5"},{"appointment":100,"code":"ys00000006","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第六人民医院","name":"ff","starLevel":"5"},{"appointment":100,"code":"ys00000005","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第五人民医院","name":"ee","starLevel":"5"},{"appointment":100,"code":"ys00000004","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第四人民医院","name":"dd","starLevel":"5"},{"appointment":100,"code":"ys00000003","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第三人民医院","name":"cc","starLevel":"5"},{"appointment":100,"code":"ys00000002","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第二人民医院","name":"bb","starLevel":"5"},{"appointment":100,"code":"ys00000001","doctorLabel":"一甲,主治,专家","headImage":"http://10.10.0.95/img/userfiles//images/null","hospitalName":"北京市第一人民医院","name":"aa","starLevel":"5"}]}
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
        private List<DoctorListBean> doctorList;

        public List<DoctorListBean> getDoctorList() {
            return doctorList == null ? new ArrayList<DoctorListBean>() : doctorList;
        }

        public void setDoctorList(List<DoctorListBean> doctorList) {
            this.doctorList = doctorList;
        }

        public static class DoctorListBean {
            /**
             * appointment : 100
             * code : ys00000008
             * doctorLabel : 一甲,主治,专家
             * headImage : http://10.10.0.95/img/userfiles//images/null
             * hospitalName : 北京市第八人民医院
             * name : hh
             * starLevel : 5
             */

            private int appointment;
            private String code;
            private String doctorLabel;
            private String headImage;
            private String hospitalName;
            private String name;
            private float starLevel;

            public int getAppointment() {
                return appointment;
            }

            public void setAppointment(int appointment) {
                this.appointment = appointment;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDoctorLabel() {
                return doctorLabel;
            }

            public void setDoctorLabel(String doctorLabel) {
                this.doctorLabel = doctorLabel;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getStarLevel() {
                return starLevel;
            }

            public void setStarLevel(float starLevel) {
                this.starLevel = starLevel;
            }
        }
    }
}
