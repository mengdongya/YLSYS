package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

/**
 * 此类描述的是:返回跟踪记录的数组
 *
 * @author: wjc
 * @version:1.0
 * @date:2017年1月9日 下午12:38:27
 */
public class ExpressResArrayDto {
    /**
     * 每条跟踪信息的时间
     */
    private String time;
    /**
     * 每条跟综信息的描述
     */
    private String context;
    /**
     * 备用字段
     */
    private String ftime;

    public ExpressResArrayDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ExpressResArrayDto(String time, String context, String ftime) {
        super();
        this.time = time;
        this.context = context;
        this.ftime = ftime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

}
