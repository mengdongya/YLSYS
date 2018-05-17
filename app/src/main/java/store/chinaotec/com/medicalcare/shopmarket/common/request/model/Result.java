package store.chinaotec.com.medicalcare.shopmarket.common.request.model;

public class Result {
    /**
     * 服务器返回的状态码
     */
    public int responseCode;
    /**
     * 服务器返回的提示信息
     */
    public String msg;
    /**
     * 服务器返回的数据
     */
    public Object data;

    @Override
    public String toString() {
//		return "";
        return "responseCode = " + responseCode + " ,\nmsg = " + msg + " ,\ndata = " + data;
    }
}
