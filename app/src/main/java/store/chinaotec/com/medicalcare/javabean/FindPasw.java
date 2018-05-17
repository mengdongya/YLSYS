package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/7/18 0018 15:33
 * 设置新密码页面最后校验返回的数据解析
 */

public class FindPasw {
    /**
     * msg : 验证码输入有误!
     * responseCode : 500
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
