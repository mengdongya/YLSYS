package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by Administrator on 2017/12/5 0005.
 * 删除用户疾病种类后返回的数据java对象
 */

public class DeleteDiseBean {
    /**
     * msg : 请求数据成功!
     * responseCode : 0
     */

    private String msg;
    private String responseCode;

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
}
