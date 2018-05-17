package store.chinaotec.com.medicalcare.shopmarket.common.constant;


import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;

public class SourceConstant {
    /**
     * android客户端
     */
    public static final String ANDROID_CODE = "1";
    public static final String APP_CODE = "sourceCode";
    /**
     * 商品 - 标签下的商品列表---- 精品推荐
     */
    public static final String SKU_LABLE_CODE_JPTJ = "JPTJ";
    /**
     * 商品 - 标签下的商品列表----特惠街列表
     */
    public static final String SKU_LABLE_CODE_THJ = "THJ";
    /**
     * 商品 - 列表的标题名；如：搜索结果，分类
     */
    public static final String TITLE_NAME = "title";
    /**
     * 商品 - 查询商品的关键字
     */
    public static final String SEARCH_KEY = "search_key";
    /**
     * 商品 - 查询商品分类的分类ID
     */
    public static final String TYPE_CODE = "type_code";
    /**
     * 商品 - 商品的skuCode
     */
    public static final String SKU_CODE = "skuCode";
    /**
     * 商品 - 商品Pcode
     */
    public static final String PCOD = "Pcod";
    /**
     * 存储visitKey
     */
    public static final String VisitKey = "visitKey";
    /**
     * 存储门店visitKey
     */
    public static final String StoreVisitKey = "storeVisitKey";
    /**
     * 存储用户的真实姓名
     */
    public static final String USER_TRUE_NAME = "trueName";
    /**
     * 存储第三方登录验证已绑定---保存的手机号
     */
    public static final String USER_RUYIBAO_IS_BIND_USERPHONE = "userPhone";
    /**
     * 存储用户登录
     */
    public static final String USER_SID = "sid";
    /**
     * 存储app用户
     */
    public static final String USER_APP_ACCOUNT = "appAccount";
    /**
     * 存储地址信息
     */
    public static final String ADDRESS_LIST_JSON = "addressListJson";
    /**
     * 存储首页信息
     */
    public static final String HOME_LIST_JSON = "homeListJson";
    /**
     * 存储分类信息
     */
    public static final String TYPE_LIST_JSON = "typeListJson";
    /**
     * 存储左侧一级分类名称
     */
    public static final String LEFT_MENU_LIST_JSON = "leftMenuListJson";
    /**
     * 存储文件的文件名
     */
    public static final String fileNameAli = "alisp";
    /**
     * 搜索历史
     */
    public static final String SEARCH_HISTORY = "search_history";
    /**
     * 菜单名称的集合
     */
    public static final String[] list_Menu_Name = {"全部", "代付款", "代发货", "代收货", "待评价"};
//	/** 存储文件的文件名 */
//	public static final String fileNameRuyiBao = "ruyibaosp";
    /**
     * 首页-无网络状态的广播
     */
    public static final String Net_State_Cast_NO = "com.yili.netStateCast.no";
    /**
     * 首页-有网络状态的广播
     */
    public static final String Net_State_Cast_OK = "com.yili.netStateCast.ok";
    /**
     * 首页-回到顶部状态的广播
     */
    public static final String SCROLL_TOP_OK = "com.yili.scrollTop.ok";
    /** 订单-订单详情 */
//    public static OrderUser orderUser;
    /**
     * 首页-取消回到顶部状态的广播
     */
    public static final String SCROLL_TOP_VISIBILITY_VISIBLE = "com.yili.scrollTop.visible";
    /**
     * 首页-取消回到顶部状态的广播
     */
    public static final String SCROLL_TOP_VISIBILITY_GONE = "com.yili.scrollTop.GONE";
    public static final String START_SHOP_SKU_INFO = "org.aorun.ym.jump.to.skuInfo.activity";
    public static final String START_SHOP_STORE = "org.aorun.ym.jump.to.shopDetails.activity";
    public static final String START_SHOP_TUAN_MIAO = "org.aorun.ym.jump.to.seckillAndGroupBuy.activity";
    public static final String START_SHOP_STORE_TUAN_MIAO = "org.aorun.ym.jump.to.store.seckillAndGroupBuy.activity";
    public static final String START_SHOP_STORE_TUAN_MIAO_TWO = "org.aorun.ym.jump.to.store.seckillAndGroupBuy2.activity";
    public static final String START_SHOP_STORE_CXZQ = "org.aorun.ym.jump.to.sku.list.discount.activity";
    public static final String START_SHOP_STORE_CXZQ_TWO = "org.aorun.ym.jump.to.sku.list.discount2.activity";
    public static final String HOME_SKU_CODE = "home_skucode";
    /**
     * 订单-去付款的广播
     */
    public static final String PAY = "com.alading.pay";
    /**
     * 我的收藏----取消收藏
     */
    public static final String UN_FAVORITES = "com.alading.unfavorites";
    /**
     * 订单-订单金额
     */
    public static final String TOTAL_ACTUAL = "totalActual";
    /**
     * 注册-登录名
     */
    public static final String USER_NAME = "userName";
    /**
     * 订单列表中首次显示哪一个
     */
    public static final String ALLORDERS_FIRST = "allOrderFirst";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    /**
     * 每页显示多少个数据
     */
    public static final int PAGE_SIZE = 10;
    /**
     * 存放登录方式----1:QQ登录 2：微信登录 3：新浪微博登录
     */
    public static final String OTHER_LOGIN = "other_login";
    /**
     * 存储是否首次启动
     */
    public static final String APP_IS_FIRST_OPEN = "appIsFirstOpen";
    /**
     * 门店编号
     */
    public static final String STORE_CODE = "storeCode";
    /**
     * 门店名称
     */
    public static final String STORE_NAME = "storeName";
    public static final String STORE_LOCATION_LONGITUDE = "Longitude";
    public static final String STORE_LOCATION_LATITUDE = "Latitude";
    /**  */
    public static final String STORE_CELL_NAME = "cellName";
    /**
     * 门店营业状态
     */
    public static final String STORE_STATUS = "storeStatus";
    public static final String STORE_PHONE = "";
    /**
     * 活动的地址
     */
    public static final String EVENT_TITLE = "eventTitle";
    public static final String EVENT_URL = "eventUrl";
    public static final String BLOCK_GROUP_BUY = "TOGEGHER";
    public static final String BLOCK_SECKILL = "TIMELIMIT";
    public static final String BLOCK_COLLECT = "collect";
    public static final String BLOCK_ORDER = "order";
    public static final String BLOCK_STORE_TOGETHER = "storeTogether";
    public static final String BLOCK_STORE_PROMOTION = "StorePromotion";
    public static final String BLOCK_STORE_COLLECT = "StoreCollect";
    public static final String BLOCK_STORE_TIMELIMIT = "StoreTimeLimit";
    /**
     * 意见反馈
     */
    public static final String FEEK_BACK = "2";
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeigh;
    /**
     * 订单-商品
     */
    public static SkuByOrderLine skuByOrder;
    /**
     * 订单-订单状态
     */
    public static String orderStatus;
    /**
     * 注册登录  注册码
     */
    public static String randomCode;
    /**
     * 订单-订单编码
     */
    public static String orderCode;
    /**
     * 从玉门跳到商超的商品详情
     */
    public static int YU_MEN_TO_SHOP_SKU_INFO = 0;
    public static int YU_MEN_TO_SHOP_STORE = 0;
    public static int YU_MEN_TO_SHOP_TUAN_MIAO = 0;
    public static int YU_MEN_TO_SHOP_STORE_TUAN_MIAO = 0;
    public static int YU_MEN_TO_SHOP_STORE_CXZQ = 0;
    /**
     * 订单-是否向服务器发送请求 查询订单
     */
    public static boolean ISGetDate = false;
    /**
     * 我的收藏----取消收藏
     */
    public static String FAVORITES_SKU_CODE;
    /**
     * 订单编号
     */
    public static String ORDER_CODE = "orderCode";
    /**
     * 订单-订单金额
     */
    public static String TOTAL_PRICE = "";
    /**
     * 订单-订单生成时的编号
     */
    public static String SO_CODE = "";
    /**
     * 从哪个界面跳转到购物车的
     */
    public static String FROM_ACTIVITY = "APPName";
    /**
     * 订单列表中点击的哪个
     */
    public static int POSTION_ORDER = -1;
    /**
     * 订单是否创建成功
     */
    public static boolean IS_ORDER_OK = false;
    /**
     * 支付是否成功
     */
    public static int IS_PAY_OK = 0;
    /**
     * 网络判断 是否有网络  true：有网络，false：无网络
     */
    public static boolean IS_NET_STATE = true;
    /**
     * 支付判断 是否是app支付 (1,2,3种状态) 1：普通商品请求支付，2：如意宝请求支付，3：卡请求支付
     */
    public static int IS_APP_PAY = 0;
    public static int IS_XXX = 0;
    public static int IS_PAY_SUCCESS = 1;
    public static int IS_BACK_CURRENT_PAGE = 0;
    public static int GO_TO_SHARE = 0;
    /**
     * 推送判断是否返回当钱的app
     */
    public static int IS_BACK_CURRENT_APP = 0;
    /**
     * 是否关闭选择支付页面
     */
    public static boolean PAY_SELECT_IS_CLOSE = false;
    public static String GOTO_SHOP_HOME_OR_TYPE = "goto";

    //各模块的投诉类型
    public static int TURN_TO_COMPLAINT_BY_TYPE = 0;
}

