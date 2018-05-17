package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

import java.util.ArrayList;

public class OrderUserRes {
    /**
     * 订单数量
     */
    private int total;
    /**
     * 订单对象集合
     */
    private ArrayList<OrderUser> orderList;

    public OrderUserRes() {
        // TODO Auto-generated constructor stub
    }

    public OrderUserRes(int total, ArrayList<OrderUser> orderList) {
        super();
        this.total = total;
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "OrderUserRes [total=" + total + ", orderList=" + orderList
                + "]";
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<OrderUser> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderUser> orderList) {
        this.orderList = orderList;
    }


}
