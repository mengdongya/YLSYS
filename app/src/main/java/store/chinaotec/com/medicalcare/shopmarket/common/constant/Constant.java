package store.chinaotec.com.medicalcare.shopmarket.common.constant;

import android.os.Environment;

import java.io.File;

public class Constant {
    /**
     * qq APPID 测试Appid为 : 222222,1103836404
     */
    public final static String QQ_APPID = "101225031";
    /**
     * 微信 APPID
     */
    public final static String WX_APPID = "wx58cd00ca05fec2cf";
    // public final static String SD_CARD_TEMP_DIR = path +
    // "/CloudHouse/Image/temp.jpg";
    /**
     * 微信 APP_SECRET
     */
    public final static String WX_APP_SECRET = "20f4e0f4da97474ae6be86e81a0c270c";
    /**
     * 支付宝 APPID
     */
    // public final static String ALI_APPID = "2014122600021948";
//	public final static String ALI_APPID = "1022751544";
    public final static String ALI_APPID = "2016082201785801";
    public final static String DEFAULT_PARTNER = "2088711896780205";
    public final static String ALI_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJcVEgxuQPwokbFIYj5PwAgn+MkvqYY76NOtN3yMwwqrk/g3UyJN4ngQCVpBwSMtVcyPSS4Lddp9ApaQeGv5jvmsU9ERwwTmMDaIVO+dXXSovPdvGf4BYjpxNjK9ZJKFLGhOj0KZ1vrz9TO4qHv3vNE6jE2yIGkIpYxGHz65Mz9dAgMBAAECgYBqn1Ekeq7cBhmD3syOoyOn3eXCviNKe3892/HbsU94eUwtEYZXIFYiW1buXpdaJwV1Hu4LU8Uxk6W+8mg5815FmhEkmyWGDw3FDPO03SETTGWXUH/nWAXksD7uZgA1+VzTRsH6sui9fC29YgiQwh+Fj2ZonM4bmsT7Y1Lweud3oQJBAMbRdIayId6j5nSj3gmQXhjd5U/uoU2Zc9cfhrRmUtEauQd+gsNHJJTpr99W21XWLxanZSG3++oWR53GPnJ7RkUCQQDCiO2GnAwL2Ylo/6lPRFOcUI/1t2jyNmBQZcC0CGfeKfrYDFdJsBDLNuxNKsGW8hCemwspi/bdTG6YQAGLbNI5AkEAxSu/5j0OfeAJkq9Yah+0UfDtk4HSkkSWr0dirdG0XA+mZBpA0CzjyJKgilt3Ff9dn5fPIct2l0YVLBEIqTw8CQJAF+XgYB/9bts7gTWfJAi9yL8w4Du23cKGVHobkUJTGDzJ/w6NDpVdisllgBlXjOaR2hor5d+25PrFv1hlmC43KQJAGwMjrX7juPiaE3lQAf7gkhzss6emKuFqUJJUdeyiWFBMTPHQMDAOIb0qzYDCPNw9rtymqlSF09d0iqHlkiXG1g==";
    public final static String ALI_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    /**
     * 新浪微博 APP_KEY
     */
    public static final String APP_KEY = "2446568897";
    /**
     * 新浪微博登录成功后的回调页
     */
    public static final String REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    /**
     * 新浪微博权限
     * <p/>
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
     * <p/>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。
     * <p/>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p/>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    /**
     * 极光推送 - 首页
     */
    public final static int JPUSH_HOME = 0;
    /**
     * 极光推送 - 具体商品-- 后台给到的商品code ,app链接到商品详情
     */
    public final static int JPUSH_SKU = 1;
    /**
     * 极光推送 - 相关活动 --给到title和连接Url,,app链接到Url
     */
    public final static int JPUSH_ACTIVITIES = 2;
    /**
     * 极光推送 - 下单成功,给到订单code,app不做处理
     */
    public final static int JPUSH_ORDER_LIST = 3;
    /**
     * 极光推送 - 订单发货之后,给到的订单code 跳转到 订单详情
     */
    public final static int JPUSH_ORDER_DETAIL = 4;
    /**
     * 极光推送 - registrationID
     */
    public final static String JPUSH_REGISTRATION_ID = "registrationID";
    /**
     * 登录
     */
    public final static byte REQUEST_CODE_LOGIN = 0x00;
    /**
     * 用户中心
     */
    public final static byte REQUEST_CODE_LOGIN_YM = 0x01;
    /**
     * 我的收藏
     */
    public final static byte REQUEST_CODE_FAVORITES = 0x02;
    /**
     * 亲子课程 - 评论
     */
    public final static byte REQUEST_CODE_SYSARTICLE_ADDEVALUATE = 0x03;
    /**
     * 相册取照
     */
    public static final byte REQUEST_CODE_CHOOSE_BIG_PICTURE = 0x04;
    /**
     * 拍照
     */
    public static final byte REQUEST_CODE_CHOOSE_BIG_CAMERA = 0x05;
    /**
     * 照片裁剪
     */
    public static final byte REQUEST_CODE_PHOTO_SHEAR = 0x06;
    /**
     * 选择 - 省
     */
    public static final byte REQUEST_CODE_ADDRESS_PROVINCE = 0x07;
    /**
     * 选择 - 银行卡列表
     */
    public static final byte REQUEST_CODE_BANK_LIST = 0x08;
    /**
     * 银行卡- 新建
     */
    public static final byte REQUEST_CODE_ADDRESS_BANK = 0x09;
    /**
     * 银行卡- 修改
     */
    public static final byte REQUEST_CODE_BANK_CHANGE = 0x16;
    /**
     * 支付 - 银联支付
     */
    public static final byte REQUEST_CODE_UNIONPAY = 0xA;
    /**
     * 地址 - 修改
     */
    public static final byte REQUEST_CODE_ADDRESS_CHANGE = 0xB;
    /**
     * 地址 - 列表
     */
    public static final byte REQUEST_CODE_ADDRESS_LIST = 0xC;
    /**
     * 支付 - 支付方式
     */
    public static final byte REQUEST_CODE_PAY_SELECT = 0xD;
    /**
     * 支付 - 发票类型
     */
    public static final byte REQUEST_CODE_PAY_INVOICE = 0xE;
    /**
     * 去结算 - 购物车
     */
    public static final byte REQUEST_CODE_CART_PAY = 0xF;
    /**
     * 购物车
     */
    public static final byte REQUEST_CODE_CART = 0x10;
    /**
     * 订单 - 详情
     */
    public static final byte REQUEST_CODE_ORDER_INFO = 0x11;
    /**
     * 订单 - 列表
     */
    public static final byte REQUEST_CODE_ORDER_LIST = 0x12;
    /**
     * 商品 - 详情
     */
    public static final byte REQUEST_CODE_SKU_INFO = 0x13;
    /**
     * 忘记密码 - 修改密码
     */
    public static final byte REQUEST_CODE_FORGET_PWD_CHANGE = 0x14;
    /**
     * 地址 - 添加
     */
    public static final byte REQUEST_CODE_ADDRESS_ADD = 0x15;
    /**
     * 可用优惠券
     */
    public static final byte REQUEST_CODE_CANUSE_COUPON = 0x17;
    /**
     * 选择优惠券使用方式
     */
    public static final byte REQUEST_CODE_COUPON_TYPE = 0x18;
    /**
     * 选择门店的小区
     */
    public static final byte STORE_CHOOSE_CELL = 0x19;
    /**
     * 转出到如意宝
     */
    public static final byte REQUEST_CODE_TURN_OUT_RYB = 0x21;
    /**
     * 转出到如意宝
     */
    public static final byte REQUEST_CODE_TURN_OUT_RYB_TO = 0x27;
    /**
     * 转出到如意宝
     */
    public static final byte REQUEST_CODE_TURN_OUT_BANK = 0x20;
    /**
     * 转出到如意宝选择联系人
     */
    public static final byte REQUEST_CODE_TURN_OUT_RYB_CHOOSE_PERSON = 0x22;
    /**
     * 转出到如意宝选择联系人-确认
     */
    public static final byte REQUEST_CODE_TURN_OUT_RYB_CHOOSE_PERSON_SURE = 0x23;
    /**
     * 转入到如意宝
     */
    public static final byte REQUEST_CODE_TRANSF_IN_RYB = 0x24;
    /**
     * 转入到如意宝
     */
    public static final byte REQUEST_CODE_TRANSF_IN_RYB_BY_STK = 0x25;
    /**
     * 转入到如意宝
     */
    public static final byte REQUEST_CODE_TRANSF_IN_RYB_BY_STKED = 0x26;
    public static final byte REQUEST_SCORE_SKU_INFO = 0x27;
    public static final byte REQUEST_SCORE_SKU_CHANGE = 0x28;
    public static final byte REQUEST_CODE_STORE_CATEGORY = 0x29;
    /**
     * 默认
     */
    public static final byte SKU_LIST_DEFAULT = 0x01;
    /**
     * 最新
     */
    public static final byte SKU_LIST_MOST_NEW = 0x02;
    /**
     * 销量
     */
    public static final byte SKU_LIST_SALES_VOLUME = 0x03;

    /** 商品列表,排序 */
    /**
     * 价格
     */
    public static final byte SKU_LIST_PRICE = 0x04;
    /**
     * 升序 - 只对"价格"有效
     */
    public static final byte SKU_LIST_PRICE_ASC = 0x05;
    /**
     * 降序 - 只对"价格"有效
     */
    public static final byte SKU_LIST_PRICE_DESC = 0x06;
    /**
     * 在线客服URL
     */
    public static final String URL_SERVICE_FOR_ONLINE = "http://chat16.live800.com/live800/chatClient/chatbox.jsp?companyID=568223&configID=91102&jid=8222921867";
    /**
     * 申请开店的URL
     */
    public static final String APPLY_OPEN_STORE = "http://m.aladingshop.com/store/toOpenShop";
    /**
     * 如意消费卡的介绍页
     */
    public static final String RUYIBAO_INTRODUCE = "http://m.aladingshop.com/app/toSpeRuyi";
    /**
     * 开通vip如意消费卡
     */
    public static final String RUYIBAO_OPEN_VIP = "http://m.aladingshop.com/app/toSpeRuyiApply";
    /**
     * 商品回购协议
     */
    public static final String SKU_BUY_BACK = "http://m.aladingshop.com/static/shop/buy-back.html";
    /**
     * 关于我们
     */
    public static final String ABOUT_US = "http://m.aladingshop.com/static/app-soft/about.html";
    /**
     * 会员专区
     */
    public static final String MEMBERS_AREA = "http://m.aladingshop.com/static/shop/special.html";
    /**
     * 积分商城
     */
    public static final String Integral_Shop = "http://m.aladingshop.com/integral/homeShow?sid=";
    /**
     * 母婴专区
     */
    public static final String FRANSNANA_AREA = "http://m.aladingshop.com/static/shop/special_baby.html";
    /**
     * 五一活动
     */
    public static final String WU_YI_EVENT = "http://m.aladingshop.com/static/shop/special_May.html";
    /**
     * 图片在手机SD卡的地址
     */
    public static String CACHE_PATH;
    /**
     * 分组
     */
    public static String[] data = {"洗涤用品组", "家庭日用品", "小食品组", "粮油调味组", "酒水饮料组"};
    public static String[] data1 = {};
    /**
     * 商品标签下的列表 是每日专场还是特惠街
     */
    public static String pageCategory = "pageCategory";
    /**
     * VIP专区的 分类ID
     */
    public static String CATEGORY_CODE = "categoryCode";
    /**
     * 如意宝转入消费 类型名称
     */
    public static String ruyibaoType = "ruyibaoType";
    /**
     * 网络图片 在本地的缓存路径
     */
    public static String cachePath = Environment.getExternalStorageDirectory()
            .getPath() + "/aladingshop/img/";
    private static String path = Environment.getExternalStorageDirectory()
            .getPath();
    public final static File SD_CARD_TEMP_DIR = new File(path,
            "/Fushionbaby/TEMP_IMG.jpg");

    public static final String REFRESH_FORUM = "refresh_forum";

}
