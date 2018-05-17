package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

import java.util.ArrayList;

/**
 * 商品详情
 */
public class SkuInfo {
    public String unCode;
    public String productCode;// 产品Code

    public String name;// 商品名称
    public String isCollect;// 商品收藏状态 n/y
    public String priceOld;// 商品单价(原价)
    public String priceNew;// 商品单价(现价)
    public String brandName;// 品牌名称
    public String videoUrl;// 暂时未用到
    /**
     * 是否是会员商品
     */
    public String isMemberSku;// y 是会员商品 n 不是会员商品
    /**
     * VIP会员商品价格
     */
//	public String priceVip;// 评价个数
    public int quotaNum;// 限购量
    public String inventory;// 商品的库存量
    public ArrayList<String> skuImages = new ArrayList<String>();// 商品图片
    public ArrayList<GraphicDetail> graphicDetails = new ArrayList<GraphicDetail>();// 图文详情
    public ArrayList<SkuAttr> attr = new ArrayList<SkuAttr>();// 商品属性
    public ArrayList<Sku> linkSkus = new ArrayList<Sku>();// 猜你喜欢-商品//

    public ArrayList<String> sizeList = new ArrayList<String>();// 款式
    public ArrayList<String> colorList = new ArrayList<String>();// 颜色
    public MemberSkuComment memberComment = new MemberSkuComment();// 用户对SKU的评价
    public SelectedSku selectedSku;
    // public ArrayList<SelectedSku> selectedSku = new
    // ArrayList<SelectedSku>();//选择商品颜色尺寸

    public String skuCode;
    public String skuNo;
    public String currentPrice;
    public String storeCode;
    public String storeName;
    public String size;
    public String storeImgPath;
    public String availableQty;

    public String isTimelimitSku;
    public String timelimitPrice;
    public String isTogetherSku;
    public String togetherPrice;
    public String telephone;
    public String freightInfo;

}
