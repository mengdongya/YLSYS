package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

import java.util.ArrayList;

/**
 * 此类描述的是:服务器返回的订单待评价的实体
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年7月24日 下午1:39:47
 */
public class EvaluateOrderUserRes {
    /**
     * 待评价数量
     */
    private OrderTypeNum orderTypeNumDto;
    /**
     * 商品对象集合
     */
    private ArrayList<SkuByOrderLine> orderLineDtoList;

    public OrderTypeNum getOrderTypeNumDto() {
        return orderTypeNumDto;
    }

    public void setOrderTypeNumDto(OrderTypeNum orderTypeNumDto) {
        this.orderTypeNumDto = orderTypeNumDto;
    }

    public ArrayList<SkuByOrderLine> getOrderLineDtoList() {
        return orderLineDtoList;
    }

    public void setOrderLineDtoList(ArrayList<SkuByOrderLine> orderLineDtoList) {
        this.orderLineDtoList = orderLineDtoList;
    }
}
