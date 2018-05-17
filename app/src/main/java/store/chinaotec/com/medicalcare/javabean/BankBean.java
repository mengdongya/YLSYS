package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by hxk on 2017/7/12 0012 09:45
 * 银行java对象
 */

public class BankBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private String msg;
    private String responseCode;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bankCode : JTYH
         * bankIconUrl : http://10.10.0.95/img/userfiles//images//bank/201601/jtyh.png
         * bankName : 交通银行
         */

        private String bankCode;
        private String bankIconUrl;
        private String bankName;

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankIconUrl() {
            return bankIconUrl;
        }

        public void setBankIconUrl(String bankIconUrl) {
            this.bankIconUrl = bankIconUrl;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }
}
