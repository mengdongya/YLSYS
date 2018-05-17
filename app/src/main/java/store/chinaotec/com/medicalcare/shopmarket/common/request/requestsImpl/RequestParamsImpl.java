package store.chinaotec.com.medicalcare.shopmarket.common.request.requestsImpl;

import android.content.Context;

import java.util.HashMap;

import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requests.RequestParams;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.CommitComment;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderCreat;

/**
 * 此类描述的是:请求服务器数据的实现方法
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年9月16日 下午6:49:53
 */
public class RequestParamsImpl implements RequestParams<RequestVo> {
    private Context context;

    public RequestParamsImpl(Context context) {
        this.context = context;
    }

    @Override
    public RequestVo getUUID() {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.hasDialog = false;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.GET_UUID;
        HashMap<String, String> map = new HashMap<String, String>();
        reqVo.requestDataMap = map;
        return reqVo;

    }

    @Override
    public RequestVo getAddressList(String sid) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("sid", sid);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getAddressDelete(String sid, String addressId) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_DELETE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("addressId", addressId);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getAddressAdd(String sid, String name, String phone,
                                   String provinceId, String cityId, String districtId,
                                   String address, String isDefault) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
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
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getAddressChange(String sid, String addressId,
                                      String name, String phone, String provinceId, String cityId,
                                      String districtId, String address, String isDefault) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
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
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getAreaList(String areaId) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ADDRESS_AREA_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("areaId", areaId);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getHomeList() {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
//        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.apphoust = "http://222.23.86.31:8085/fushionbaby-app";
        reqVo.requestUrl = RequestUrl.HOME_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getHomeTypeList(String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.HOME_TYPE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getTypeCategorySkuList(String categoryCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.TYPE_CATEGORY_SKU_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("categoryCode", categoryCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getHomeBrandList() {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = false;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.HOME_BRAND_LIST;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListSearch(String storeCode, String searchKey, int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SEARCH;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        map.put("keyword", searchKey);
        map.put("pageIndex", String.valueOf(pageIndex));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListMyfoot(String sid, int pageIndex, int pageSize) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_BROWSE_HISTORIES;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);// 系统来源,androiud1
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListDiscount(int pageIndex, int pageSize,
                                        int sortParam, int sortType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_LIST_DISCOUNT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("sortParam", String.valueOf(sortParam));
        map.put("sortType", String.valueOf(sortType));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListBrand(String brandId, int pageIndex,
                                     int pageSize, int sortParam, int sortType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_LIST_BRAND;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("brandId", brandId);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("sortParam", String.valueOf(sortParam));
        map.put("sortType", String.valueOf(sortType));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListType(String categoryCode, int pageIndex,
                                    int pageSize, int sortParam, int sortType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_LIST_TYPE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("categoryCode", categoryCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("sortParam", String.valueOf(sortParam));
        map.put("sortType", String.valueOf(sortType));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListTrend(int zoneIndex, int pageIndex,
                                     int pageSize, int sortParam, int sortType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.HOME_TREND_SKU_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("zoneIndex", String.valueOf(zoneIndex));
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("sortParam", String.valueOf(sortParam));
        map.put("sortType", String.valueOf(sortType));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuInfo(String sid, String skuCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_INFO;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuInfoCode(String pcode, String color, String size) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = true;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_INFO_CODE;
        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("visitKey", visitKey);
//        map.put("skuCode", skuCode);// 商品唯一code
        map.put("productCode", pcode);// 产品code
        map.put("color", color);
        map.put("size", size);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuIsCollect(String sid, String skuCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = false;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_IS_COLLECT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuAddOrDel(String sid, String skuCode, String isAttention) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = false;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_COLLECT_ADD_OR_DEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        map.put("isAttention", isAttention);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuLikeList(String id, int pageIndex, int pageSize) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_LIKE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuEvaluateList(String sid, int pageIndex, int pageSize) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_EVALUATE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuEvaluateAdd(CommitComment mComment) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_ADD_EVALUATE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", mComment.getSid());
        map.put("skuCode", mComment.getSkuCode());
        map.put("orderCode", mComment.getOrderCode());
        map.put("content", mComment.getContent());
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);// 系统来源,androiud
        // : 1
        // ,IOS
        // : 2
        map.put("skuColor", mComment.getSkuColor());
        map.put("skuSize", mComment.getSkuSize());
        map.put("orderLineId", mComment.getOrderLineId());
        map.put("score", mComment.getScore());
        map.put("isAnonymous", mComment.getIsAnonymous());
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getFavoritesList(String sid, int pageIndex, int pageSize) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FAVORITES_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getFavoritesAdd(String sid, String skuCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FAVORITES_ADD;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getFavoritesDelete(String sid, String skuCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FAVORITES_DELETE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartList(String visitKey, String sid) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
//		map.put("pageIndex", String.valueOf(pageIndex));
//		map.put("pageSize", String.valueOf(pageSize));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartAdd(String visitKey, String sid, String skuCode,
                                String quantity) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_ADD;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("visitKey", visitKey);
        map.put("skuCode", skuCode);
        map.put("quantity", quantity);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartDel(String visitKey, String sid, String skuCode, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_DEl;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartSkuSelect(String visitKey, String sid,
                                      String skuCodes, String isSelected, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_SKU_CHECK;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("skuCode", skuCodes);
        map.put("isSelected", isSelected);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartSkuSelectAll(String visitKey, String sid, String isSelected) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_SKU_CHECK_ALL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("isSelected", isSelected);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartSkuChangeNum(String visitKey, String sid, String skuId, String quantity, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_SKU_CHANGE_NUM;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put("skuCode", skuId);
        map.put("quantity", quantity);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCheckGotoOrder(String sid) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CHECK_GOTO_ORDER;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getPayCart(String sid) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.PAY_CART;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCreatOrder(OrderCreat entity) {
        OrderCreat orderCreat = (OrderCreat) entity;
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CREAT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("sid", orderCreat.sid);
        map.put("payoffId", orderCreat.payoff_id);
        map.put("addressId", orderCreat.address_id);
        map.put("shopContent", orderCreat.shopContent);
//		map.put("isPoint", orderCreat.is_point);
        map.put("isInvoice", orderCreat.isInvoice);
        map.put("invoiceType", orderCreat.invoiceType);
        map.put("invoiceTitle", orderCreat.invoiceTitle);
        map.put("buyBackCodes", orderCreat.buyBackCodes);

        map.put("sendDate", orderCreat.send_date);
//		map.put("memo", orderCreat.memo);
//		map.put("isCardNo", orderCreat.is_cardno);
//		map.put("cardNo", orderCreat.cardno);
//		map.put("isRedUse", orderCreat.isRedUse);
//		map.put("isPickUp", orderCreat.isPickUp);
        map.put("macAddr", orderCreat.macAddr);

        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getOrderAll(String sid, int pageIndex, String orderType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("orderType", orderType);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getOrderInfo(String sid, String orderCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_INFO;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getFeedback(String sid, String content, String phoneOrEmail) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SETTING_FEEDBACK;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("sid", sid);
        map.put("content", content);
        map.put("contactInformation", phoneOrEmail);
        map.put("type", SourceConstant.FEEK_BACK);
        reqVo.requestDataMap = map;
        return reqVo;
    }


    @Override
    public RequestVo getOrderSkuDiscount(String sid, String payOffId,
                                         String skuId, String isUseSkuDiscount, String area_code,
                                         String is_use_tax) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_DISCOUNT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("payoff_id", payOffId);
        map.put("skuId", skuId);
        map.put("is_use_sku_discount", isUseSkuDiscount);
        map.put("area_code", area_code);
        map.put("is_use_tax", is_use_tax);
        reqVo.requestDataMap = map;
        return reqVo;
    }


    @Override
    public RequestVo getOrderFreight(String sid, String payoff_id,
                                     String area_code, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_FREIGHT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("payoffId", payoff_id);
        map.put("areaCode", area_code);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getOrderDel(String sid, String orderCode, String orderHandleType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo applyRefund(String data, String mac) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_APPLY_REFUND;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("data", data);
        map.put("mac", mac);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getOrderConfirmReceipt(String toJson) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CONFIRM_RECEIPT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("data", toJson);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getWXPayInfo(String code, String sId) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.PAY_HOST;
        reqVo.requestUrl = RequestUrl.WX_PAY;
        HashMap<String, String> map = null;
        map = new HashMap<String, String>();
        map.put("orderCode", code);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("sid", sId);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getAliPayInfo(String orderCode, String sourceCode,
                                   String appId, String sId) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.PAY_HOST;
        reqVo.requestUrl = RequestUrl.ALI_PAY;
        HashMap<String, String> map = null;
        map = new HashMap<String, String>();
        map.put("orderCodes", orderCode);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("appId", appId);
        map.put("sid", sId);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getUnionpayPayInfo(String soCode, String sId) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.PAY_HOST;
        reqVo.requestUrl = RequestUrl.YL_GET_TN;
        HashMap<String, String> map = null;
        map = new HashMap<String, String>();
        map.put("orderCodes", soCode);
        map.put("sid", sId);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);// 系统来源,androiud
        // : 1
        // ,IOS
        // : 2
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getPayImmediate(String sid, String skuCode, String quantity) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.PAY_IMMEDIATE;
        HashMap<String, String> map = null;
        map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("skuCode", skuCode);
        map.put("quantity", quantity);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuListLabel(String labelCode, String pageIndex,
                                     String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_LIST_LABEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("labelCode", labelCode);
        map.put("pageIndex", pageIndex);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getVipSkuListLabel(String categoryCode, String pageIndex,
                                        String pageSize) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = false;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.VIP_SKU_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("categoryCode", categoryCode);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getOrderEvaluateList(String sid, int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_EVALUATE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("pageIndex", String.valueOf(pageIndex));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCancelOrder(String sid, String orderCode, String orderHandleType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getRemindDelivery(String sid, String orderCode, String orderHandleType) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_CANCEL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("orderCode", orderCode);
        map.put("orderHandleType", orderHandleType);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getTypeCategorySkuList(String storeCode, String categoryCode,
                                            String pageIndex, String pageSize) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.TYPE_CATEGORY_SKU_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put("categoryCode", categoryCode);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCartSkuCount(String visitKey, String sid, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = false;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_COUNT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("visitKey", visitKey);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }


    @Override
    public RequestVo getEachOrderQuantity(String sid) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_QUANTITY;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getOrderExpress(String data, String mac) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.ORDER_GET_ORDER_EXPRESS;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("data", data);
        map.put("mac", mac);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getMyHeadInfo(String data, String mac) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.USERCENTER_GET_MYHEAD_INFO;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("data", data);
        map.put("mac", mac);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo enterStoreHome(String sid, String storeCode, int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.hasDialog = false;
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_ENTER_STORE_HOME;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("storeCode", storeCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getStoreCartAdd(String visitKey, String sid, String skuCode, String quantity, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_ADD;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sid", sid);
        map.put("visitKey", visitKey);
        map.put("skuCode", skuCode);
        map.put("quantity", quantity);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getStoreCartList(String visitKey, String sid, int page_index, int page_size, String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.CART_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("visitKey", visitKey);
        map.put("sid", sid);
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("pageIndex", String.valueOf(page_index));
        map.put("pageSize", String.valueOf(page_size));
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getStoreTypeList(String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.HOME_STORE_TYPE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getStoreTypeCategorySkuList(String storeCode, String categoryCode, int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_GET_CATEGORY_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        map.put("storeCode", storeCode);
        map.put("categoryCode", categoryCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getSkuCommentList(String skuCode, String level, int page_index, int page_size) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_EVALUATE_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("skuCode", skuCode);
        map.put("commentLevel", level);
        map.put("pageIndex", String.valueOf(page_index));
        map.put("pageSize", String.valueOf(page_size));
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getCheckFree() {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.FREE_SHIPPING;
        return reqVo;
    }

    @Override
    public RequestVo getShopList(int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SHOP_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getStoreIntro(String storeCode) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.STORE_INTRODUCE;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", String.valueOf(storeCode));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getApplyOpenShop(String storeName, String storePhone, String storeCity, String storeAddress, String imgUrl, String imgCardUrl) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
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
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getShopGroupBuyList(String storeCode, int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_TOGETHER_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

    @Override
    public RequestVo getShopTimeLimitList(String storeCode, int pageIndex) {
        RequestVo reqVo = new RequestVo();
        reqVo.context = context;
        reqVo.hasDialog = false;
        reqVo.type = 1;
        reqVo.apphoust = RequestUrl.APP_HOME;
        reqVo.requestUrl = RequestUrl.SKU_TIME_LIMIT_LIST;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("storeCode", storeCode);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        reqVo.requestDataMap = map;
        return reqVo;
    }

}