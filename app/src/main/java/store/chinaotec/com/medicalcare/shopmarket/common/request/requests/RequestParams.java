package store.chinaotec.com.medicalcare.shopmarket.common.request.requests;


import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.CommitComment;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderCreat;

/**
 * 请求参数
 */
public interface RequestParams<T> {

    /**
     * 获取UUId
     */
    public T getUUID();

    /**
     * 个人中心 - 获取各种订单数量
     *
     * @param sid session id
     */
    public T getEachOrderQuantity(String sid);

    /**
     * 地址 - 列表
     *
     * @param sid 用户id
     */
    public T getAddressList(String sid);

    /**
     * 地址 - 删除
     *
     * @param sid       用户id
     * @param addressId 地址id
     */
    public T getAddressDelete(String sid, String addressId);

    /**
     * 地址 - 添加
     *
     * @param sid        用户id
     * @param name       收货人姓名
     * @param phone      收货人电话
     * @param provinceId 省Id
     * @param cityId     市Id
     * @param districtId 区Id
     * @param address    详细地址
     * @param isDefault  是否默认地址
     */

    public T getAddressAdd(String sid, String name, String phone,
                           String provinceId, String cityId, String districtId,
                           String address, String isDefault);

    /**
     * 地址 - 修改
     *
     * @param sid        用户id
     * @param addressId  地址id
     * @param name       收货人姓名
     * @param phone      收货人电话
     * @param provinceId 省Id
     * @param cityId     市Id
     * @param districtId 区Id
     * @param address    详细地址
     * @param isDefault  是否默认地址
     */
    public T getAddressChange(String sid, String addressId, String name,
                              String phone, String provinceId, String cityId, String districtId,
                              String address, String isDefault);

    /**
     * 地址 - 修改
     *
     * @param sid
     *            用户id
     * @param address_id
     *            地址id
     * @param name
     *            收货人姓名
     * @param phone
     *            收货人电话
     * @param province_id
     *            省Id
     * @param city_id
     *            市Id
     * @param district_id
     *            区Id
     * @param address
     *            详细地址
     */
    // public T getAddressChange(String sid, String address_id, String name,String phone, String province_id,
    // String city_id, String district_id, String address);

    /**
     * 省级/市级/区级 - 列表
     */
    public T getAreaList(String area_id);

    /**
     * 首页 - 展示信息
     */
    public T getHomeList();

    /**
     * 分类 - 分类列表
     */
    public T getHomeTypeList(String storeCode);

    /**
     * 分类 - 品牌列表
     */
    public T getHomeBrandList();

    /**
     * 分类 - 分类列表下的子分类列表
     */
    public T getTypeCategorySkuList(String categoryCode);

    /**
     * 分类 - 分类列表下的子分类商品列表
     */

    public T getTypeCategorySkuList(String storeCode, String categoryCode, String pageIndex,
                                    String pageSize);

    /**
     * 搜索
     *
     * @param searchKey 搜索关键字
     * @param pageIndex 第几页(从1开始)
     */
    public T getSkuListSearch(String storeCode, String searchKey, int pageIndex);

    /**
     * 我的足迹
     *
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     */
    public T getSkuListMyfoot(String sid, int pageIndex, int pageSize);

    /**
     * 商品 - 标签下的商品列表
     *
     * @param pageIndex 第几页(从1开始)
     * @param storeCode 每页多少条
     *                  //* @param labelCode分类编号
     */
    public T getSkuListLabel(String labelCode, String pageIndex, String storeCode);

    /**
     * vip商品 - 标签下的商品列表
     *
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     *                  // * @param labelCode分类编号
     */
    public T getVipSkuListLabel(String categoryCode, String pageIndex, String pageSize);

    /**
     * 商品 - 特惠列表
     *
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     * @param sortParam 最新 or 价格 or 销量
     * @param sortType  升序 or 降序 (只对价格有效 , 2015年3月10日)
     */
    public T getSkuListDiscount(int pageIndex, int pageSize, int sortParam,
                                int sortType);

    /**
     * 商品 - 品牌 - 商品列表
     *
     * @param brandId   品牌id
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     * @param sortParam 最新 or 价格 or 销量
     * @param sortType  升序 or 降序 (只对价格有效 , 2015年3月10日)
     */
    public T getSkuListBrand(String brandId, int pageIndex, int pageSize,
                             int sortParam, int sortType);

    /**
     * 商品 - 类别 - 商品列表
     *
     * @param categoryCode 类别id
     * @param pageIndex    第几页(从1开始)
     * @param pageSize     每页多少条
     * @param sortParam    最新 or 价格 or 销量
     * @param sortType     升序 or 降序 (只对价格有效 , 2015年3月10日)
     */
    public T getSkuListType(String categoryCode, int pageIndex, int pageSize,
                            int sortParam, int sortType);

    /**
     * * 商品 - 类别 - 商品列表
     *
     * @param zoneIndex 类别id 1:男宝,2:女宝 ,3 : 亲子
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     * @param sortParam 最新 or 价格 or 销量
     * @param sortType  升序 or 降序 (只对价格有效 , 2015年3月10日)
     */
    public T getSkuListTrend(int zoneIndex, int pageIndex, int pageSize,
                             int sortParam, int sortType);

    /**
     * 商品 - 详情
     * <p/>
     * //	 * @param sid
     * sessionId
     *
     * @param skuCode 商品唯一编号
     */
    public T getSkuInfo(String sid, String skuCode);

    /**
     * 商品 - 详情
     * <p/>
     * // * @param sid
     * sessionId
     *
     * @param pcode 商品编号
     * @param color 颜色
     * @param size  尺寸
     */
    public T getSkuInfoCode(String pcode, String color, String size);

    /**
     * 判断商品是否被收藏
     *
     * @param sid
     * @return
     */
    public T getSkuIsCollect(String sid, String skuCode);

    /**
     * 商品收藏--添加与删除
     *
     * @param sId
     * @param skuCode
     * @param isAttention
     * @return
     */

    public T getSkuAddOrDel(String sId, String skuCode, String isAttention);


    /**
     * 商品 - 猜你喜欢列表
     *
     * @param id        商品id
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     */
    public T getSkuLikeList(String id, int pageIndex, int pageSize);

    /**
     * 商品 - 评价
     * <p/>
     * //	 * @param id
     * 商品id
     *
     * @param page_index 第几页(从1开始)
     * @param page_size  每页多少条
     */
    public T getSkuEvaluateList(String sid, int page_index, int page_size);

    /***
     * 商品 - 评价列表
     *
     * @param skuCode
     * @param level
     * @param page_index
     * @param page_size
     * @return
     */
    public T getSkuCommentList(String skuCode, String level, int page_index, int page_size);

    /**
     * 订单 - 评价列表
     *
     * @param Jsondata json串
     *                 // * @param sid
     *                 用户登录标识
     *                 // * @param page_index
     *                 第几页(从1开始)
     *                 // * @param page_size
     *                 每页多少条
     */
    public T getOrderEvaluateList(String Jsondata, int pageIndex);

    /**
     * 商品 - 评价添加
     * <p/>
     * // * @param sid
     * 用户session
     * // * @param sku_id
     * 商品id
     * // * @param content
     * 评价内容
     * // * @param source
     * 来源 1:andorid , 2:Ios
     * // * @param sku_color
     * 颜色
     * // * @param sku_size
     * 尺寸
     * // * @param so_code
     * 订单号
     * // * @param so_line_id
     * 订单行
     * // * @param sku_code
     * 商品编码
     * // * @param sku_score
     * 评分
     */

    public T getSkuEvaluateAdd(CommitComment mComment);

    // String sid,String skuCode,String orderCode,String content,String
    // sourceCode,String skuColor,String skuSize,String orderLineId,String
    // score,String isAnonymous,String commentLevel

    /**
     * 购物车 - 列表
     *
     * @param visitKey 类UUID
     * @param sid      用户session
     *                 //	 * @param page_index
     *                 第几页(从1开始)
     *                 // * @param page_size
     *                 每页多少条
     */

//	public T getCartList(String visitKey, String sid, int page_index,
//			int page_size);
    public T getCartList(String visitKey, String sid);

    /**
     * 门店--购物车 - 列表
     *
     * @param visitKey   类UUID
     * @param sid        用户session
     * @param page_index 第几页(从1开始)
     * @param page_size  每页多少条
     */

    public T getStoreCartList(String visitKey, String sid, int page_index,
                              int page_size, String storeCode);

    /**
     * 购物车 - 数量
     *
     * @param visitKey 类UUID
     * @param sid      用户session
     */

    public T getCartSkuCount(String visitKey, String sid, String storeCode);

    /**
     * 购物车 - 添加
     *
     * @param visitKey 类UUID
     *                 <p/>
     *                 //	 * @param skuId
     *                 商品id
     * @param quantity 数量
     */
    public T getCartAdd(String visitKey, String sid, String skuCode,
                        String quantity);

    /**
     * 购物车 - 添加
     *
     * @param visitKey 类UUID
     *                 <p/>
     *                 // * @param skuId
     *                 商品id
     * @param quantity 数量
     */
    public T getStoreCartAdd(String visitKey, String sid, String skuCode,
                             String quantity, String storeCode);

    /**
     * 购物车 - 删除
     *
     * @param visitKey 类UUID
     * @param sid      用户session
     *                 // * @param sku_id
     *                 购物车商品标识
     */

    public T getCartDel(String visitKey, String sid, String skuId, String storeCode);

    /**
     * 购物车 - 商品选择状态
     *
     * @param visitKey
     * @param sid        用户登录时的 sessionID
     *                   // * @param skuIds
     *                   商品Id 多个时 用 " , " 逗号分割(英文状态的符号)
     * @param isSelected y :　选中　；　n : 未选中
     */

    public T getCartSkuSelect(String visitKey, String sid, String skuCode,
                              String isSelected, String storeCode);

    /**
     * 购物车 - 商品全部选择状态
     *
     * @param visitKey
     * @param sid        用户登录时的 sessionID
     * @param isSelected y :　选中　；　n : 未选中
     */

    public T getCartSkuSelectAll(String visitKey, String sid, String isSelected);

    /**
     * 购物车 - 商品改变数量
     *
     * @param visitKey
     * @param sid      //* @param sku_id
     * @param quantity 商品总数量
     */

    // public T getCartSkuChangeNum(String visitKey, String sid, String sku_id,
    // String quantity);
    public T getCartSkuChangeNum(String visitKey, String sid, String skuId,
                                 String quantity, String storeCode);

    /***
     * 检测能不能-去结算
     *
     * @param sid
     * @return
     */

    public T getCheckGotoOrder(String sid);

    /**
     * 去结算 - 购物车
     *
     * @param sid
     */

    public T getPayCart(String sid);

    /**
     * 去结算 - 立即支付
     *
     * @param sid
     */

    public T getPayImmediate(String sid, String sku_id, String quantity);


    /**
     * 收藏 - 列表
     *
     * @param sid       用户session
     * @param pageIndex 第几页(从1开始)
     * @param pageSize  每页多少条
     */

    public T getFavoritesList(String sid, int pageIndex, int pageSize);

    /**
     * 收藏 - 添加
     *
     * @param sid     用户session
     * @param skuCode 商品编号
     */

    public T getFavoritesAdd(String sid, String skuCode);

    /**
     * 收藏 - 删除
     *
     * @param sid     用户session
     * @param skuCode 商品编号
     */

    public T getFavoritesDelete(String sid, String skuCode);

    /**
     * 订单 - 商品优惠券获取接口 -- 折扣
     *
     * @param sid
     * @param payoff_id           结算序列
     * @param sku_id              商品ID
     * @param is_use_sku_discount 是否使用(y是n否)
     * @param area_code           区域code
     * @param is_use_tax          是否使用税费
     * @return
     */
    public T getOrderSkuDiscount(String sid, String payoff_id, String sku_id,
                                 String is_use_sku_discount, String area_code, String is_use_tax);

    /**
     * 订单 - 计算运费接口
     *
     * @param sid
     * @param payoff_id 结算序列ID
     * @param area_code 省ID
     * @return
     */
    public T getOrderFreight(String sid, String payoff_id, String area_code, String storeCode);

    /**
     * 订单 - 去支付
     *
     * @return
     */
    public T getCreatOrder(OrderCreat entity);

    /**
     * 订单 - 列表
     *
     * @param sid       sessionId
     * @param pageIndex 第几页
     *                  //	 * @param page_size
     *                  分页数据大小(每页多少条)
     * @param orderType 传空代表全部订单，传1代表待付款，传3代表待发货，传5待收货
     */
    public T getOrderAll(String sid, int pageIndex, String orderType);

    /**
     * 订单 - 详情
     * <p/>
     * <p/>
     * 订单 code
     */
    public T getOrderInfo(String sid, String orderCode);

    /**
     * 订单 - 确认收货
     */
    public T getOrderConfirmReceipt(String toJson);

    /**
     * 订单 - 提醒收货
     *
     * @param sid
     * @param
     */
    public T getRemindDelivery(String sid, String orderCode, String orderHandleType);

    /**
     * 订单-----会员申请退款
     *
     * @param
     */
    public T applyRefund(String data, String mac);

    /**
     * 订单 - 取消订单
     *
     * @param
     */
    public T getCancelOrder(String sid, String orderCode, String orderHandleType);

    /**
     * 订单 - 删除
     *
     * @param sid json格式的参数
     * @param
     */
    public T getOrderDel(String sid, String orderCode, String orderHandleType);

    /**
     * 设置 - 反馈
     *
     * @param sid          sessionId
     * @param content      反馈内容
     * @param phoneOrEmail 手机号或邮箱
     */
    public T getFeedback(String sid, String content, String phoneOrEmail);


    /**
     * 银联 - 获取支付信息
     *
     * @param sId
     * @param soCode
     */
    public T getUnionpayPayInfo(String soCode, String sId);

    /**
     * 微信 - 获取支付信息
     *
     * @param
     * @param sId user.sId
     */
    public T getWXPayInfo(String code, String sId);

    /**
     * 支付宝 - 获取支付信息
     * <p/>
     * <p/>
     * /// sourceCode 当前系统 版本
     *
     * @param
     * @param appId 支付宝 appid
     * @param sId   user.sId
     */
    public T getAliPayInfo(String orderCode, String sourceCode, String appId,
                           String sId);

    /**
     * 获取物流信息
     *
     * @param
     */
    public T getOrderExpress(String data, String mac);

    /**
     * 获取个人中心头部信息
     *
     * @param
     */
    public T getMyHeadInfo(String data, String mac);


    /**
     * 进入小区门店
     *
     * @param
     */
    public T enterStoreHome(String sid, String storeCode, int pageSize);


    /**
     * 分类 - 门店下的分类列表
     */
    public T getStoreTypeList(String storeCode);

    /**
     * 分类 - 分类列表下的子分类商品列表
     */
    public T getStoreTypeCategorySkuList(String storeCode, String categoryCode, int pageIndex);

    /**
     * 查询运费
     *
     * @return
     */
    public T getCheckFree();

    /***
     * 店铺列表
     *
     * @param pageIndex
     * @return
     */
    public T getShopList(int pageIndex);

    /***
     * 店铺介绍
     *
     * @param storeCode
     * @return
     */
    public T getStoreIntro(String storeCode);

    /***
     * 申请开店
     *
     * @param storeName
     * @param storePhone
     * @param storeCity
     * @param storeAddress
     * @return
     */
    public T getApplyOpenShop(String storeName, String storePhone, String storeCity, String storeAddress, String imgUrl, String imgCardUrl);

    /***
     * 获取团购的商品列表
     *
     * @param storeCode
     * @param pageIndex
     * @return
     */
    public T getShopGroupBuyList(String storeCode, int pageIndex);

    /***
     * 获取秒杀的商品列表
     *
     * @param storeCode
     * @param pageIndex
     * @return
     */
    public T getShopTimeLimitList(String storeCode, int pageIndex);

}
