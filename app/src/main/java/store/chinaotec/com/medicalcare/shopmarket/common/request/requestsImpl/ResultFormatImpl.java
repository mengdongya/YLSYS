package store.chinaotec.com.medicalcare.shopmarket.common.request.requestsImpl;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.shopmarket.common.request.model.Result;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requests.ResultFormat;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
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
 * Created by seven on 2016/8/1 0001.
 */
public class ResultFormatImpl implements ResultFormat {
    private Gson gson = new Gson();

    /**
     * @param jsonString json字符串
     * @param clazz      对象的实体bean
     * @return 集合对象
     * @Title: getJsonToList
     * @Description: 通过json字符串 解析得到集合对象
     */
    public static <T, clazz> List<T> getJsonToList(String jsonString,
                                                   Class<T> clazz) {
        List<T> list = null;

        // 判断是否是空的
        if (jsonString != null && clazz != null) {
            Gson gson = new Gson();
            List<T> listJson = gson.fromJson(jsonString,
                    new TypeToken<List<clazz>>() {
                    }.getType());

            list = new ArrayList<T>();

            for (int i = 0; i < listJson.size(); i++) {
                T bean = getJsonToBean(gson.toJson(listJson.get(i)), clazz);
                list.add(bean);
            }
        }

        return list;
    }

    /**
     * @param jsonString json字符串
     * @param clazz      要被解析的bean对象
     * @return 单个对象
     * @Title: getJsonToBean
     * @Description: json格式的字符串 返回对象类型
     */
    public static <T> T getJsonToBean(String jsonString, Class<T> clazz) {

        if (jsonString != null && clazz != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, clazz);
        }

        return null;
    }

    @Override
    public Result verfiyResponseCode(String jsonStr) {
        Result result = new Result();

        try {
            JSONObject jo = new JSONObject(jsonStr);
            result.responseCode = jo.getInt("responseCode");
            result.msg = jo.getString("msg");
            result.data = jo.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
            result.responseCode = -1;
            result.msg = "数据解析失败";
            result.data = "";
        }
        LogUtil.d("", result.toString());
        return result;

    }

    @Override
    public Home formatHomeList(String jsonStr) {
        Home entity = new Home();
        try {
            entity = gson.fromJson(jsonStr, Home.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public StoreHome formatStoreHomeList(String jsonStr) {
        StoreHome entity = new StoreHome();
        try {
            entity = gson.fromJson(jsonStr, StoreHome.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public Cart formatCartList(String jsonStr) {
        Cart cart = null;
        try {
            cart = gson.fromJson(jsonStr, Cart.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return cart;
    }

    @Override
    public ArrayList<Sku> formatSkuList(String jsonStr) {
        ArrayList<Sku> list = null;

        try {
            list = gson.fromJson(jsonStr, new TypeToken<ArrayList<Sku>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return ((list != null) ? list : new ArrayList<Sku>());
    }

    @Override
    public ArrayList<StoreSkuList> formatStoreSkuLists(String jsonStr) {
        ArrayList<StoreSkuList> list = null;

        try {
            list = gson.fromJson(jsonStr, new TypeToken<ArrayList<StoreSkuList>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return ((list != null) ? list : new ArrayList<StoreSkuList>());
    }

    @Override
    public ArrayList<AddressInfo> formatAddressList(String jsonStr) {
        ArrayList<AddressInfo> list = null;
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<AddressInfo>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return ((list != null) ? list : new ArrayList<AddressInfo>());

    }

    @Override
    public ArrayList<MyFavorites> formatFavoritesList(String jsonStr) {
        ArrayList<MyFavorites> list = null;
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<MyFavorites>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return ((list != null) ? list : new ArrayList<MyFavorites>());
    }

    @Override
    public ArrayList<SkuBrowseHistoriesDto> formatSkuBrowseHistoriesDto(String jsonStr) {
        ArrayList<SkuBrowseHistoriesDto> list = null;
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<SkuBrowseHistoriesDto>>() {
                    }.getType());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return ((list != null) ? list : new ArrayList<SkuBrowseHistoriesDto>());
    }

    @Override
    public SkuCommentList formatSkuCommentList(String jsonStr) {
        SkuCommentList entity = null;
        try {
            entity = gson.fromJson(jsonStr, SkuCommentList.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public ArrayList<Shops> formatShopList(String jsonStr) {
        ArrayList<Shops> list = null;
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<Shops>>() {
                    }.getType());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<ShopGroupBuyAndSeckill> formatShopGroupBuyOrTimeLimitList(String jsonStr) {
        ArrayList<ShopGroupBuyAndSeckill> list = null;
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<ShopGroupBuyAndSeckill>>() {
                    }.getType());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<GraphicDetail> formatGraphicDetail(String jsonStr) {
        ArrayList<GraphicDetail> list = null;
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<ArrayList<GraphicDetail>>() {
                    }.getType());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public AlipayModel formatAliPayInfo(String jsonStr) {
        AlipayModel entity = null;
        try {
            entity = gson.fromJson(jsonStr, AlipayModel.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }

        return entity;
    }

}
