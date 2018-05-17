package store.chinaotec.com.medicalcare.shopmarket.common.request.requests;


import java.util.ArrayList;

import store.chinaotec.com.medicalcare.shopmarket.common.request.model.Result;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.AddressInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.model.Cart;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.Home;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.ShopGroupBuyAndSeckill;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreHome;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreSkuList;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.alipay.AlipayModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.entity.Shops;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.GraphicDetail;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.SkuCommentList;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.MyFavorites;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.SkuBrowseHistoriesDto;

/**
 * Created by wjc on 2016/8/1 0001.
 */
public interface ResultFormat {
    /**
     * 请求结果验证
     */
    public Result verfiyResponseCode(String jsonStr);

    /**
     * 解析首页信息
     */
    public Home formatHomeList(String jsonStr);

    /**
     * 解析门店首页信息
     */
    public StoreHome formatStoreHomeList(String jsonStr);

    /**
     * 购物车 - 列表
     */
    public Cart formatCartList(String jsonStr);

    /**
     * 解析商品列表
     */
    public ArrayList<Sku> formatSkuList(String jsonStr);

    /**
     * 门店三级分类下的商品
     */
    public ArrayList<StoreSkuList> formatStoreSkuLists(String jsonStr);

    /**
     * 解析地址列表
     */
    public ArrayList<AddressInfo> formatAddressList(String jsonStr);

    /**
     * 解析收藏列表
     */
    public ArrayList<MyFavorites> formatFavoritesList(String jsonStr);

    /**
     * 解析 我的浏览历史
     */
    public ArrayList<SkuBrowseHistoriesDto> formatSkuBrowseHistoriesDto(String jsonStr);

    /**
     * 商品 - 评论列表
     */
    public SkuCommentList formatSkuCommentList(String jsonStr);

    /**
     * 店铺列表
     */
    public ArrayList<Shops> formatShopList(String jsonStr);

    /**
     * 团购，秒杀列表
     */
    public ArrayList<ShopGroupBuyAndSeckill> formatShopGroupBuyOrTimeLimitList(String jsonStr);

    /**
     * 商品的图文详情
     */
    public ArrayList<GraphicDetail> formatGraphicDetail(String jsonStr);

    /**
     * 支付宝 - get支付信息
     */
    public AlipayModel formatAliPayInfo(String jsonStr);

}
