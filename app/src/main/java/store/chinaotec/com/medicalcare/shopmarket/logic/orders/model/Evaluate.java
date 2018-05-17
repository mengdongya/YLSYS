package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 此类描述的是:向服务器请求待评价商品的实体类
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年7月24日 下午1:38:49
 */
public class Evaluate {
    private String sid;
    private String evaluateStatus;
    private String pageIndex;
    private String pageSize;

    public Evaluate() {
        // TODO Auto-generated constructor stub
    }

    public Evaluate(String sid, String evaluateStatus, String pageIndex,
                    String pageSize) {
        super();
        this.sid = sid;
        this.evaluateStatus = evaluateStatus;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
