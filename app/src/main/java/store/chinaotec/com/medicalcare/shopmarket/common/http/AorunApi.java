package store.chinaotec.com.medicalcare.shopmarket.common.http;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.CommitComment;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderCreat;

/**
 * Created by wjc on 2016/10/9 0009.
 */
public class AorunApi {

    public static void getHomeList(DataCallBack callback) {
        RequestVo requestVo = new RequestVo();
        requestVo.apphoust = RequestUrl.APP_HOME;
        requestVo.requestUrl = RequestUrl.HOME_LIST;
        Map<String, String> params = new LinkedHashMap<>();
        params.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        requestVo.params = params;
        callback.setRequestVo(requestVo);
        AorunYuMenApiClient.post(requestVo, callback);
    }

    public static void getShopList(int pageIndex, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SHOP_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getShopLocationList(String longitude, String latitude, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SHOP_LOACTION_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void enterStoreHome(String sid, String storeCode, int pageIndex, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_ENTER_STORE_HOME;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("storeCode", storeCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getStoreIntro(String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_INTRODUCE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", String.valueOf(storeCode));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getSkuListSearch(String storeCode, String searchKey, int pageIndex, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SEARCH;
        reqVo.hasDialog = true;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        map.put("keyword", searchKey);
        map.put("pageIndex", String.valueOf(pageIndex));
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getShopGroupBuyList(String storeCode, int pageIndex, DataCallBack callback) {
        RequestVo requestVo = new RequestVo();
        requestVo.apphoust = RequestUrl.APP_HOME;
        requestVo.requestUrl = RequestUrl.SKU_TOGETHER_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        requestVo.params = map;
        callback.setRequestVo(requestVo);
        AorunYuMenApiClient.post(requestVo, callback);
    }

    public static void getShopTimeLimitList(String storeCode, int pageIndex, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_TIME_LIMIT_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getHomeTypeList(String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.HOME_TYPE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getStoreTypeList(String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.HOME_STORE_TYPE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getStoreTypeCategorySkuList(String storeCode, String categoryCode, int pageIndex, int pageSize, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_GET_CATEGORY_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("categoryCode", categoryCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getSkuCommentList(String skuCode, String level, int page_index, int page_size, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_EVALUATE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("skuCode", skuCode);
        map.put("commentLevel", level);
        map.put("pageIndex", String.valueOf(page_index));
        map.put("pageSize", String.valueOf(page_size));
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getEachOrderQuantity(String sid, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_QUANTITY;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getSkuListMyfoot(String sid, int pageIndex, int pageSize, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_BROWSE_HISTORIES;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getAreaList(String areaId, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_AREA_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("areaId", areaId);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getApplyOpenShop(String storeName, String storePhone, String storeCity, String storeAddress,
                                        String imgUrl, String imgCardUrl, String beverageLicense, String storeType, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_COMMIT_STORE_APPIY;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", storeName);
        map.put("phone", storePhone);
        map.put("area", storeCity);
        map.put("addressDetail", storeAddress);
        map.put("base64DataBusinessLicense", imgUrl);
        map.put("base64DataIdentityCard", imgCardUrl);
        map.put("base64DataFoodAndBeverageLicense", beverageLicense);
        map.put("storeType", storeType);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getFavoritesList(String sid, int pageIndex, int pageSize, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FAVORITES_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getFavoritesDelete(String sid, String skuCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FAVORITES_DELETE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getFeedback(String sid, String content, String phoneOrEmail, String type, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SETTING_FEEDBACK;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, "3");
        map.put("sid", sid);
        map.put("content", content);
        map.put("contactInformation", phoneOrEmail);
        map.put("type", type);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getStoreCartList(String visitKey, String sid, int page_index, int page_size, String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("pageIndex", String.valueOf(page_index));
        map.put("pageSize", String.valueOf(page_size));
        map.put("storeCode", storeCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCartSkuSelect(String visitKey, String sid,
                                        String skuCodes, String isSelected, String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_SKU_CHECK;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("skuCode", skuCodes);
        map.put("isSelected", isSelected);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCartSkuSelectAll(String visitKey, String sid, String isSelected, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_SKU_CHECK_ALL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("isSelected", isSelected);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCartSkuChangeNum(String visitKey, String sid, String skuId, String quantity, String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_SKU_CHANGE_NUM;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("skuCode", skuId);
        map.put("quantity", quantity);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCartDel(String visitKey, String sid, String skuCode, String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_DEl;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCheckGotoOrder(String sid, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CHECK_GOTO_ORDER;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    /**
     * 首页2+8个专题下对应的三级分类的商品
     */
    public static void getTypeCategorySkuList(String storeCode, String categoryCode,
                                              String pageIndex, String pageSize, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.TYPE_CATEGORY_SKU_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put("categoryCode", categoryCode);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    /**
     * 热门推荐下的商品
     */
    public static void getSkuListLabel(String labelCode, String pageIndex, String storeCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_LIST_LABEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("labelCode", labelCode);
        map.put("pageIndex", pageIndex);
        map.put("storeCode", storeCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getAddressList(String sid, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("sid", sid);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getAddressDelete(String sid, String addressId, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_DELETE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("addressId", addressId);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getAddressAdd(String sid, String name, String phone,
                                     String provinceId, String cityId, String districtId,
                                     String address, String isDefault, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_ADD;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("name", name);
        map.put("phone", phone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("address", address);
        map.put("isDefault", isDefault);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getAddressChange(String sid, String addressId,
                                        String name, String phone, String provinceId, String cityId,
                                        String districtId, String address, String isDefault, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_CHANGE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("addressId", addressId);
        map.put("name", name);
        map.put("phone", phone);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("address", address);
        map.put("isDefault", isDefault);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getPayCart(String sid, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.PAY_CART;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCreatOrder(OrderCreat entity, DataCallBack callback) {
        OrderCreat orderCreat = (OrderCreat) entity;
        RequestVo reqVo = new RequestVo();
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CREAT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("sid", orderCreat.sid);
        map.put("payoffId", orderCreat.payoff_id);
        map.put("addressId", orderCreat.address_id);
        map.put("shopContent", orderCreat.shopContent);
        map.put("isInvoice", orderCreat.isInvoice);
        map.put("invoiceType", orderCreat.invoiceType);
        map.put("invoiceTitle", orderCreat.invoiceTitle);
//        map.put("buyBackCodes", orderCreat.buyBackCodes);
        map.put("sendDate", orderCreat.send_date);
        map.put("pushSignAddr", orderCreat.macAddr);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getAliPayInfo(String orderCode, String appId, String sId, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.PAY_HOST;
        reqVo.requestUrl = RequestUrl.ALI_PAY;
        HashMap<String, String> map = null;
        map = new HashMap<String, String>();
        map.put("orderCodes", orderCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("appId", appId);
        map.put("sid", sId);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getOrderAll(String sid, int pageIndex, String orderType, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("orderType", orderType);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getOrderEvaluateList(String sid, int pageIndex, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_EVALUATE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getCancelOrder(String sid, String orderCode, String orderHandleType, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getOrderDel(String sid, String orderCode, String orderHandleType, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getRemindDelivery(String sid, String orderCode, String orderHandleType, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getOrderConfirmReceipt(String sid, String orderCode, String orderHandleType, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void applyRefund(String data, String mac, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_APPLY_REFUND;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("data", data);
        map.put("mac", mac);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getSkuEvaluateAdd(CommitComment mComment, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_ADD_EVALUATE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", mComment.getSid());
        map.put("skuCode", mComment.getSkuCode());
        map.put("orderCode", mComment.getOrderCode());
        map.put("content", mComment.getContent());
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("skuColor", mComment.getSkuColor());
        map.put("skuSize", mComment.getSkuSize());
//        map.put("orderLineId", mComment.getOrderLineId());
        map.put("score", mComment.getScore());
        map.put("isAnonymous", mComment.getIsAnonymous());
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getOrderInfo(String sid, String orderCode, DataCallBack callback) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_INFO;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        reqVo.params = map;
        callback.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callback);
    }

    public static void getFreight(String sid, String addressId, String payOffId, DataCallBack callBack) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FREIGHT_INFO;
        HashMap<String, String> map = new HashMap<>();
        map.put("sid", sid);
        map.put("addressId", addressId);
        map.put("payOffId", payOffId);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callBack.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callBack);
    }

    public static void getOrderExpress(String sid, String orderCode, DataCallBack callBack) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_GET_ORDER_EXPRESS;
        HashMap<String, String> map = new HashMap<>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        reqVo.params = map;
        callBack.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callBack);
    }

    public static void getShopStoreList(int pageIndex, DataCallBack callBack) {
        RequestVo reqVo = new RequestVo();
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SHOP_LIST;
        HashMap<String, String> map = new HashMap<>();
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.params = map;
        callBack.setRequestVo(reqVo);
        AorunYuMenApiClient.post(reqVo, callBack);
    }

}
