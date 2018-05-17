package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/7/20 0020 14:09
 * 上传logo成功后解析返回数据
 */

public class UpLogoBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     * data : http://219.144.68.15/img/userfiles//images//member/image//1500530612679.jpg
     */

    private String msg;
    private int responseCode;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
