package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/7/12 0012 13:25
 * 获取短信验证码时后台返回的数据解析
 */

public class CodeKeyBean {
    /**
     * data : APP_201606_KRjN6V9Ij4OVPHIo9ko0pQu26BA50Kts
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private String data;
    private String msg;
    private int responseCode;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
