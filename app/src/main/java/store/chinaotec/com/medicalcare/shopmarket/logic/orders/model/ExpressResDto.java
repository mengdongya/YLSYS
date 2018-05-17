package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

import java.util.ArrayList;

/**
 * 此类描述的是:查询物流返回的信息对象
 *
 * @author: wjc
 * @version:1.0
 * @date:2017年1月9日 上午11:57:34
 */
public class ExpressResDto {
    /**
     * 为‘ok’时代表成功
     */
    private String message;
    /**
     * 物流状态（0：在途 1：揽件2：疑难3签收4退签 5：派件6：退回）
     */
    private String state;
    /**
     * 查询状态 ’200’ 表示成功
     */
    private String status;
    /**
     * 物流公司编号
     */
    private String com;
    /**
     * 物流单号
     */
    private String nu;
    /**
     * 返回跟踪记录的数组
     */
    private ArrayList<ExpressResArrayDto> data;
    /**
     * 物流公司的中文名
     */
    private String name;

    public ExpressResDto(String message, String state, String status, String com, String nu,
                         ArrayList<ExpressResArrayDto> data, String name) {
        super();
        this.message = message;
        this.state = state;
        this.status = status;
        this.com = com;
        this.nu = nu;
        this.data = data;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public ArrayList<ExpressResArrayDto> getData() {
        return data;
    }

    public void setData(ArrayList<ExpressResArrayDto> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ExpressResDto [message=" + message + ", state=" + state + ", status=" + status + ", com=" + com
                + ", nu=" + nu + ", data=" + data + ", name=" + name + "]";
    }


}
