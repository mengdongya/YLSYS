package store.chinaotec.com.medicalcare.shopmarket.logic.pay.model;

public class PaySku {
    // 商品唯一编码
    public String skuCode;
    // 数量,订购数量
    public long requestedQty;
    // 单价,销售单价
    public String currentPrice;
    // 购买行小计
    public String currentPriceTotal;
    // 购买行折扣后小计
    public String dicountPriceTotal;
    public String imgPath;

    public String skuName;
    public String memberId;
    public String storeCode;
    public String storeName;
    public String skuImgPath;
    public String quantity;
    public String lineTotalPrice;

    // 颜色
    public String color;
    // 尺寸
    public String size;
    // 商品数量
    public int pnum;
    // 零售价
    public String retailPrice;
    // 是否是回购商品
    public String isBuyBack;

    public PaySku() {
    }

    public PaySku(String skuCode, long requestedQty, String currentPrice, String currentPriceTotal,
                  String dicountPriceTotal, String skuImgPath, String skuName, String color, String size, int pnum,
                  String retailPrice, String isBuyBack) {
        super();
        this.skuCode = skuCode;
        this.requestedQty = requestedQty;
        this.currentPrice = currentPrice;
        this.currentPriceTotal = currentPriceTotal;
        this.dicountPriceTotal = dicountPriceTotal;
        this.skuImgPath = skuImgPath;
        this.skuName = skuName;
        this.color = color;
        this.size = size;
        this.pnum = pnum;
        this.retailPrice = retailPrice;
        this.isBuyBack = isBuyBack;
    }

    @Override
    public String toString() {
        return "PaySku [skuCode=" + skuCode + ", requestedQty=" + requestedQty + ", currentPrice=" + currentPrice
                + ", currentPriceTotal=" + currentPriceTotal + ", dicountPriceTotal=" + dicountPriceTotal
                + ", skuImgPath=" + skuImgPath + ", skuName=" + skuName + ", color=" + color + ", size=" + size + ", pnum="
                + pnum + ", retailPrice=" + retailPrice + "]";
    }

}
