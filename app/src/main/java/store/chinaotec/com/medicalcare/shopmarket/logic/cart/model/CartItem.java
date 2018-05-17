package store.chinaotec.com.medicalcare.shopmarket.logic.cart.model;


/**
 * 购物车列表--item
 */
public class CartItem {
    // 商品ID
    public String skuCode;
    // 行Id
    public String cartRowsId;
    // 图片路径
    public String skuImgPath;
    // 商品名字
    public String skuName;

    //商品类型
    public String type;
    // 颜色
    public String color;
    // 尺寸
    public String size;

    // 商品数量
    public int pnum;

    // 商品价格
    public String price;
    // 商品价格 - 总价
    public String rowPriceTotal;
    // 商品的选择状态
    public String isSelected;

    //vip商品价格
    public String priceVip;

    public int availableQty;
    public int quotaNum;
    public String isLoseEffc;
    public String isMemberSku;
    public int quantity;
    public String storeCode;
    public String storeName;
    public String aladingPrice;
    public String currentPrice;

    public boolean childbox;// item 中BOX全选的标记

}