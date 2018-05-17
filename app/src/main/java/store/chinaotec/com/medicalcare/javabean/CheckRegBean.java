package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/7/12 0012 15:19
 * 用户注册第一步校验短信验证码等信息后解析返回的数据
 */

public class CheckRegBean {
    /**
     * msg : 请求参数传递错误!!
     * responseCode : 300
     */

    private String msg;
    private int responseCode;

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
