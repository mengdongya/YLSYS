package store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl;

/**
 * 请求链接
 */
public class RequestUrl {

    // 预生产 测试---玉门商超
//    public static final String APP_HOME = "https://appymclient.91catv.com:8444/fushionbaby-app";

    //	public static final String APP_HOME = "http://10.10.0.95:8080/fushionbaby-app";
    // 开发 环境 ---- 玉门商超
    public static final String APP_HOME = "http://219.144.68.15:8081/fushionbaby-app";
//	public static final String APP_HOME = "http://222.23.86.31:8085/fushionbaby-app";
//	public static final String APP_HOME = "http://app1.91catv.com:8080/fushionbaby-app";

//    public static final String APP_HOME = "http://app1.91catv.com:8021/fushionbaby-app";
//    public static final String APP_HOME = "http://60.205.0.226:8021/fushionbaby-app";

    //	public static final String PAY_HOST = "pay.91catv.com:8083/fushionbaby-pay";
    public static final String PAY_HOST = "http://60.205.0.192:8083/fushionbaby-pay";
    // /** 支付HOST -微信 银联支付,支付宝 -----------测试*/

    /**
     * 微信 - 获取 支付信息
     */
    public static final String WX_PAY = "/appwx/getprepayid.do?";
    /**
     * 银联支付 - 获取的tn
     */
    public static final String YL_GET_TN = "/appunion/tn.do?";
    /**
     * 支付宝支付
     */
    public static final String ALI_PAY = "/appzfb/alipaymodel.do?";
    /**  */
    public static final String GET_UUID = "/cart/getVisitKey.do?";
    /**
     * 地址 - 收货地址列表
     */
    public static final String ADDRESS_LIST = "/memberAddress/addressList?";
    /**
     * 地址 - 添加地址
     */
    public static final String ADDRESS_ADD = "/memberAddress/addAddress?";
    /**
     * 地址 - 删除地址
     */
    public static final String ADDRESS_DELETE = "/memberAddress/deleteAddress?";
    /**
     * 地址 - 修改地址
     */
    public static final String ADDRESS_CHANGE = "/memberAddress/changeAddress?";
    /**
     * 省级/市级/区级 - 列表
     */
    public static final String ADDRESS_AREA_LIST = "/memberAddress/getAllAddress?";
    /**
     * 首页 - 展示信息
     */
    public static final String HOME_LIST = "/shopHome/homeList?";
    /**
     * 首页 - 分类列表
     */
    public static final String HOME_TYPE_LIST = "/category/categoryList?";
    public static final String HOME_STORE_TYPE_LIST = "/store/getCategoryList?";

    /**
     * 我的足迹
     */
    public static final String SKU_BROWSE_HISTORIES = "/browesHistory/skuBrowseHistories?";
    /**
     * 商品 - 标签下的商品列表
     */
    public static final String SKU_LIST_LABEL = "/shopHome/findSkuByLable?";
    /**
     * 商品 - 详情
     */
    public static final String SKU_INFO = "/sku/skuDetail?";
    /**
     * 商品 - 图文详情
     */
    public static final String SKU_INFO_GRAPHIC_DETAILS = "/sku/graphicDetails?";
    /**
     * 首页 - 分类列表下的子分类
     */
    public static final String TYPE_CATEGORY_SKU_LIST = "/category/categorySkuList?";
    /**
     * 门店分类下分类查询
     */
    public static final String STORE_GET_CATEGORY_LIST = "/store/categorySkuList?";

    /**
     * 商品- 是否被收藏
     */
    public static final String SKU_IS_COLLECT = "/skuCollect/isCollect?";
    /**
     * 商品收藏 --添加与删除
     */
    public static final String SKU_COLLECT_ADD_OR_DEL = "/skuCollect/handleSkuCollect?";

    /**
     * 商品 - 评价列表
     */
    public static final String SKU_EVALUATE_LIST = "/sku/skuEvaluate?";
    /**
     * 收藏 - 列表
     */
    public static final String FAVORITES_LIST = "/skuCollect/skuCollectList?";
    /**
     * 收藏 - 添加
     */
    public static final String FAVORITES_ADD = "/favorites/addFavorites?";
    /**
     * 收藏 - 删除
     */
    public static final String FAVORITES_DELETE = "/skuCollect/deleteSkuCollect?";

    /**
     * 商品 - 详情-类型code
     */
    public static final String SKU_INFO_CODE = "/sku/getSkuCodeByProductCodeColorSize?";

    /**
     * 购物车 - 列表
     */
    public static final String CART_LIST = "/cart/cartList?";
    /**
     * 购物车 - 商品数量
     */
    public static final String CART_COUNT = "/cart/cartSkuCount?";
    /**
     * 购物车 - 添加
     */
    public static final String CART_ADD = "/cart/addToCart?";
    /**
     * 购物车 - 删除
     */
    public static final String CART_DEl = "/cart/cartDeleteCommodity?";
    /**
     * 购物车 - 商品选择 --单选
     */
    public static final String CART_SKU_CHECK = "/cart/modifyCartItemSelected?";
    /**
     * 购物车 - 商品选择 --全选
     */
    public static final String CART_SKU_CHECK_ALL = "/cart/modifyAllSelected?";
    /**
     * 购物车 - 商品修改数量
     */
    public static final String CART_SKU_CHANGE_NUM = "/cart/modifyItemQuantity?";
    /**
     * 提交订单前的校验
     */
    public static final String ORDER_CHECK_GOTO_ORDER = "/order/checkGotoOrder?";
    /**
     * 商铺列表
     */
    public static final String SHOP_LIST = "/store/storeList?";
    public static final String SHOP_LOACTION_LIST = "/storePosition/position";
    /**
     * 进入商铺首页
     */
    public static final String STORE_ENTER_STORE_HOME = "/store/storeHomePage?";
    /**
     * 商铺介绍
     */
    public static final String STORE_INTRODUCE = "/store/storeInfo?";
    /**
     * 设置 - 意见反馈
     */
    public static final String SETTING_FEEDBACK = "/setting/feedback.do?";
    /**
     * 申请开店
     */
    public static final String STORE_COMMIT_STORE_APPIY = "/store/commitStoreApply?";
    /**
     * 团购
     */
    public static final String SKU_TOGETHER_LIST = "/sku/skuTogetherList?";
    /**
     * 秒杀
     */
    public static final String SKU_TIME_LIMIT_LIST = "/sku/skuTimeLimitList?";
    /**
     * 搜索
     */
    public static final String SEARCH = "/sku/searchKeyword?";
//------------------------------------------------------------------------------------

    /**
     * 潮流搭配
     */
    public static final String HOME_TREND_SKU_LIST = "/home/trendSkuList.do?";
    /**
     * 首页 - 品牌列表
     */
    public static final String HOME_BRAND_LIST = "/home/brandList.do?";

    /**
     * vip商品 - 查看更多的商品列表
     */
    public static final String VIP_SKU_LIST = "/vip/sku/more?";
    /**
     * 商品 - 特惠列表
     */
    public static final String SKU_LIST_DISCOUNT = "/home/discountSkuList?";
    /**
     * 商品 - 品牌的商品列表
     */
    public static final String SKU_LIST_BRAND = "/sku/brandSkuList?";
    /**
     * 商品 - 类别 - 商品列表
     */
    public static final String SKU_LIST_TYPE = "/sku/skuListByCategory?";

    /**
     * 商品 - 猜你喜欢列表
     */
    public static final String SKU_LIKE_LIST = "/sku/likeList?";


    /**
     * 去结算 - 购物车
     */
    public static final String PAY_CART = "/order/gotoOrder?";
    /**
     * 去结算 - 购物车
     */
    public static final String PAY_SCORE = "/epoints/exchangeSku?";
    /**
     * 去结算 - 立即支付
     */
    public static final String PAY_IMMEDIATE = "/immediate/immediate_payment?";

    //-------------------------------------------------订单
    /**
     * 订单 - 创建订单
     */
    public static final String ORDER_CREAT = "/order/createOrder?";
    /**
     * 订单 - 列表
     */
    public static final String ORDER_LIST = "/order/orderList?";
    /**
     * 订单 - 各种订单数量
     */
    public static final String ORDER_QUANTITY = "/order/orderNum?";
    /**
     * 订单 - 待评价列表
     */
    public static final String ORDER_EVALUATE_LIST = "/order/evaluateList?";

    /**
     * 订单 - 取消订单
     */
    public static final String ORDER_CANCEL = "/order/handleOrder?";
    /**
     * 订单 - 删除
     */
    public static final String ORDER_DELETE = "/order/deleteOrder?";
    /**
     * 订单 - 提醒卖家发货
     */
    public static final String ORDER_REMINDDELIVERY = "/order/remindDelivery?";
    /**
     * 订单 - 会员申请退款
     */
    public static final String ORDER_APPLY_REFUND = "/order/applyRefund?";
    /**
     * 订单 - 确认收货
     */
    public static final String ORDER_CONFIRM_RECEIPT = "/order/confirmReceipt?";

    /**
     * 订单 - 评价商品
     */
    public static final String ORDER_ADD_EVALUATE = "/order/commitComment?";
    /**
     * 订单 - 详情
     */
    public static final String ORDER_INFO = "/order/orderDetail?";

    /**
     * 订单 - 使用优惠卷
     */
    public static final String ORDER_DISCOUNT = "/order/get_sku_discount.do?";

    /**
     * 订单 - 计算运费 , 使用更改地址
     */
    public static final String ORDER_FREIGHT = "/order/getFreight?";
    /**
     * 订单 - 查看物流信息
     */
    public static final String ORDER_GET_ORDER_EXPRESS = "/express/getOrderExpress?";

    /**
     * 个人中心 - 首页头部登陆之后显示（优惠券，红包，积分）
     */
    public static final String USERCENTER_GET_MYHEAD_INFO = "/index/getMyHeadInfo?";
    /**
     * 查询运费
     */
    public static final String FREE_SHIPPING = "/cart/freeShipping?";

    public static final String FREIGHT_INFO = "/order/changeMemberAddress?";

}
