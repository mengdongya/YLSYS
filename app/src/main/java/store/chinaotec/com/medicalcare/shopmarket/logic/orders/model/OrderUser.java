package store.chinaotec.com.medicalcare.shopmarket.logic.orders.model;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单详情
 */
public class OrderUser implements Serializable {
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 商品列表
     */
    private ArrayList<SkuByOrderLine> skuByOrderLines;
    /**
     * 实付运费
     */
    private double actualTransferFee;
    /**
     * 代金券金额
     */
    private double cardAmount;
    /**
     * 卖家是否发货(y/n)
     */
    private String transStatus;
    /**
     * 卖家发货时间
     */
    private String deliveryTime;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 收货人手机号
     */
    private String receiverMobile;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 使用红包金额
     */
    private String redAmount;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 实际付款金额
     */
    private double paymentTotalActual;
    /**
     * 客服电话
     */
    private String customerServicePhone;
    /**
     * 下单时间
     */
    private String createOrderTime;
    /**
     * 成功支付时间
     */
    private String paymentCompleteTime;
    /**
     * 失败的原因
     */
    private String failReason;
    /**
     * 积分
     */
    private String epointsMoney;

    private String totalPrice;
    private String storeName;
    private String storeCode;

    private ArrayList<String> canHandleOperate;

    /**
     * app详情
     */
    private ArrayList<AppDetailList> listAppDetail;

    private ArrayList<String> addressStr;
    private ArrayList<String> orderStr;
    private ArrayList<String> priceStr;
    private int skuNum;

    public OrderUser() {
    }

    public OrderUser(Parcel in) {
    }

    public OrderUser(String orderStatus, String orderCode,
                     ArrayList<SkuByOrderLine> skuByOrderLines,
                     double actualTransferFee, double cardAmount, String transStatus,
                     String deliveryTime, String receiver, String receiverMobile,
                     String province, String city, String district, String address,
                     double paymentTotalActual, String customerServicePhone,
                     String createOrderTime, String paymentCompleteTime, String redAmount,
                     String failReason, String epointsMoney, ArrayList<AppDetailList> listAppDetail, String storeCode, String storeName) {
        super();
        this.orderStatus = orderStatus;
        this.orderCode = orderCode;
        this.skuByOrderLines = skuByOrderLines;
        this.actualTransferFee = actualTransferFee;
        this.cardAmount = cardAmount;
        this.transStatus = transStatus;
        this.deliveryTime = deliveryTime;
        this.receiver = receiver;
        this.receiverMobile = receiverMobile;
        this.province = province;
        this.city = city;
        this.redAmount = redAmount;
        this.district = district;
        this.address = address;
        this.paymentTotalActual = paymentTotalActual;
        this.customerServicePhone = customerServicePhone;
        this.createOrderTime = createOrderTime;
        this.paymentCompleteTime = paymentCompleteTime;
        this.failReason = failReason;
        this.epointsMoney = epointsMoney;
        this.listAppDetail = listAppDetail;
        this.storeCode = storeCode;
        this.storeName = storeName;
    }

    public OrderUser(String orderStatus, String orderCode,
                     ArrayList<SkuByOrderLine> skuByOrderLines,
                     double actualTransferFee, double cardAmount, String transStatus,
                     String deliveryTime, String receiver, String receiverMobile,
                     String province, String city, String district, String redAmount,
                     String address, double paymentTotalActual,
                     String customerServicePhone, String createOrderTime,
                     String paymentCompleteTime, String epointsMoney) {
        super();
        this.orderStatus = orderStatus;
        this.orderCode = orderCode;
        this.skuByOrderLines = skuByOrderLines;
        this.actualTransferFee = actualTransferFee;
        this.cardAmount = cardAmount;
        this.transStatus = transStatus;
        this.deliveryTime = deliveryTime;
        this.receiver = receiver;
        this.receiverMobile = receiverMobile;
        this.province = province;
        this.city = city;
        this.district = district;
        this.redAmount = redAmount;
        this.address = address;
        this.paymentTotalActual = paymentTotalActual;
        this.customerServicePhone = customerServicePhone;
        this.createOrderTime = createOrderTime;
        this.paymentCompleteTime = paymentCompleteTime;
        this.epointsMoney = epointsMoney;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public ArrayList<String> getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(ArrayList<String> addressStr) {
        this.addressStr = addressStr;
    }

    public ArrayList<String> getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(ArrayList<String> orderStr) {
        this.orderStr = orderStr;
    }

    public ArrayList<String> getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(ArrayList<String> priceStr) {
        this.priceStr = priceStr;
    }

    public ArrayList<String> getCanHandleOperate() {
        return canHandleOperate;
    }

    public void setCanHandleOperate(ArrayList<String> canHandleOperate) {
        this.canHandleOperate = canHandleOperate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public ArrayList<AppDetailList> getListAppDetail() {
        return listAppDetail;
    }

    public void setListAppDetail(ArrayList<AppDetailList> listAppDetail) {
        this.listAppDetail = listAppDetail;
    }

    public String getEpointsMoney() {
        return epointsMoney;
    }

    public void setEpointsMoney(String epointsMoney) {
        this.epointsMoney = epointsMoney;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public ArrayList<SkuByOrderLine> getSkuByOrderLines() {
        return skuByOrderLines;
    }

    public void setSkuByOrderLines(ArrayList<SkuByOrderLine> skuByOrderLines) {
        this.skuByOrderLines = skuByOrderLines;
    }

    public double getActualTransferFee() {
        return actualTransferFee;
    }

    public void setActualTransferFee(double actualTransferFee) {
        this.actualTransferFee = actualTransferFee;
    }

    public double getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(double cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(String redAmount) {
        this.redAmount = redAmount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderUser [orderStatus=" + orderStatus + ", orderCode="
                + orderCode + ", skuByOrderLines=" + skuByOrderLines
                + ", actualTransferFee=" + actualTransferFee + ", cardAmount="
                + cardAmount + ", transStatus=" + transStatus
                + ", deliveryTime=" + deliveryTime + ", receiver=" + receiver
                + ", receiverMobile=" + receiverMobile + ", province="
                + province + ", city=" + city + ", district=" + district
                + ", address=" + address + ", paymentTotalActual="
                + paymentTotalActual + ", customerServicePhone="
                + customerServicePhone + ", createOrderTime=" + createOrderTime
                + ", paymentCompleteTime=" + paymentCompleteTime
                + ", redAmount=" + redAmount + ", failReason= " + failReason + ", epointsMoney= " +
                epointsMoney + ",listAppDetail" + listAppDetail + "]";
    }

    public double getPaymentTotalActual() {
        return paymentTotalActual;
    }

    public void setPaymentTotalActual(double paymentTotalActual) {
        this.paymentTotalActual = paymentTotalActual;
    }

    public String getCustomerServicePhone() {
        return customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
        this.customerServicePhone = customerServicePhone;
    }

    public String getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(String createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public String getPaymentCompleteTime() {
        return paymentCompleteTime;
    }

//	@Override
//	public int describeContents() {
//		return 0;
//	}

    public void setPaymentCompleteTime(String paymentCompleteTime) {
        this.paymentCompleteTime = paymentCompleteTime;
    }

}
