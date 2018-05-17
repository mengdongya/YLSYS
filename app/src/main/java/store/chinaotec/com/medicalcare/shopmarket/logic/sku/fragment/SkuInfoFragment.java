package store.chinaotec.com.medicalcare.shopmarket.logic.sku.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.Result;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.DialUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.VerifyUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.HorizontalListView;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollListView;
import store.chinaotec.com.medicalcare.shopmarket.common.view.SlideShowView;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.ShopDetailsActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuEvaluateListActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.HorizontalListViewAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.SelectAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.SkuParameterAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.GraphicDetail;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Results;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.SelectModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.SkuInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * Created by wjc on 2016/9/5 0005.
 */
public class SkuInfoFragment extends Fragment implements View.OnClickListener {
    private HorizontalListView gv_tv_may_like;
    private TextView mTvSkuName;
    private TextView mTvSkuPrice;
    private TextView tv_sku_old_vip;
    private TextView tv_comment;
    private TextView tv_store_name;
    private Button tv_check_store_type;
    private Button tv_enter_store;
    private Button tv_contact_shop_boss;
    private TextView tv_favorites_txt;
    private TextView tv_title_select;
    private TextView tv_price_select_old;
    private TextView mTvNum;
    private ImageView iv_exit_select_sku;
    private RelativeLayout rl_details;
    private RelativeLayout rll_sku_evaluate;
    private LinearLayout ll_may_like;
    private MyScrollListView lv_sku_parameter;
    private LinearLayout ll_sku_details_store;
    private LinearLayout ll_favorites_bottom;
    private LinearLayout ll_bottom;
    private View view_night;
    private ImageView iv_store_icon;
    private FrameLayout llSelectSku;
    private ImageButton btn_favorites_bottom;
    private Button mBtnAddCart;
    private TextView tvFreight;
    private Button mBtnSkuSubmit;
    private Button btn_minus;
    private Button btn_add;
    /**
     * 加入购物车时弹出的窗体中商品的价格
     */
    private TextView tv_price_select;
    /**
     * 加入购物车时弹出的窗体中商品的图片
     */
    private ImageView iv_img_select;

    private Activity activity;
    private View view;
    /**
     * 购物车key(同步使用)，用户未登录传此参数
     */
    private String visitKey;
    private String sId;
    private Bundle mBundle;
    private String skuCode;
    private Map<String, String> mParam;

    private String isCollect;
    private String TAG = "SkuInfoFragment=====";
    /**
     * 值 ----- 状态 ---- 动作 <br>
     * ture -- 未收藏 -- 收藏商品<br>
     * false - 已收藏 --- 取消收藏<br>
     */
    private boolean flagFavorites = true;
    private SkuInfo entity;
    private JsonParser jsonParser;
    private JsonElement json;
    private String productCode;
    private String commentContent;
    private String[] imageUrls;
    private ArrayList<String> sizeList;
    private ArrayList<GraphicDetail> parameterList;
    private ArrayList<Sku> linkSkusList;
    private String skuName;
    private String skuprice;
    private HorizontalListViewAdapter likeAdapter;
    private String skuimgPath;

    private String homeSkuCode;
    private String storeCode;
    private String telePhone;
    private SharedPreferences fileNameAli;
    private View mSizeView;
    private PopupWindow popupWindow;
    private LinearLayout llSize;
    private GridView mGvSize;
    private ArrayList<SelectModel> sizeLists;
    private SelectAdapter sizeSelectAdapter;
    private String mSize;
    private String quantity;
    private int quotaNum;
    /**
     * 添加购物车弹窗的布局
     */
    private View popView;
    private SlideShowView mTopViewPager;

    private void saveStoreCode() {
        SharedPreferences.Editor editor = fileNameAli.edit();
        editor.putString(SourceConstant.STORE_CODE, entity.storeCode);
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.activity = getActivity();
        view = LayoutInflater.from(activity).inflate(R.layout.activity_shop_market_sku_tab, null);
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initView(View view) {
        gv_tv_may_like = (HorizontalListView) view.findViewById(R.id.gv_tv_may_like);
        mTopViewPager = (SlideShowView) view.findViewById(R.id.sku_viewpager);
        mTvSkuName = (TextView) view.findViewById(R.id.tv_sku_name);
        mTvSkuPrice = (TextView) view.findViewById(R.id.tv_sku_price);
        tv_sku_old_vip = (TextView) view.findViewById(R.id.tv_sku_old_vip);
        tv_comment = (TextView) view.findViewById(R.id.tv_comment);
        tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
        tv_check_store_type = (Button) view.findViewById(R.id.tv_check_store_type);
        tv_enter_store = (Button) view.findViewById(R.id.tv_enter_store);
        tv_contact_shop_boss = (Button) view.findViewById(R.id.tv_contact_shop_boss);
        tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
        tv_favorites_txt = (TextView) view.findViewById(R.id.tv_favorites_txt);
        tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
        rl_details = (RelativeLayout) view.findViewById(R.id.rl_details);
        rll_sku_evaluate = (RelativeLayout) view.findViewById(R.id.rll_sku_evaluate);
        ll_may_like = (LinearLayout) view.findViewById(R.id.ll_may_like);
        lv_sku_parameter = (MyScrollListView) view.findViewById(R.id.lv_sku_parameter);
        ll_sku_details_store = (LinearLayout) view.findViewById(R.id.ll_sku_details_store);
        ll_favorites_bottom = (LinearLayout) view.findViewById(R.id.ll_favorites_bottom);
        ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        view_night = view.findViewById(R.id.view_night);
        iv_store_icon = (ImageView) view.findViewById(R.id.iv_store_icon);
        llSelectSku = (FrameLayout) view.findViewById(R.id.ll);
        btn_favorites_bottom = (ImageButton) view.findViewById(R.id.btn_favorites_bottom);
        mBtnAddCart = (Button) view.findViewById(R.id.btn_add_cart);
        tvFreight = (TextView) view.findViewById(R.id.tv_sku_freight);

    }

    private void initListener() {
        rll_sku_evaluate.setOnClickListener(this);
        ll_sku_details_store.setOnClickListener(this);
        ll_favorites_bottom.setOnClickListener(this);
        mBtnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skuprice != null) {
                    showPopupWindow(ll_bottom);
                    view_night.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_enter_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("999".equals(entity.storeCode)) {
                    activity.finish();
                    ((RadioButton) MainTabActivity.main_radio.getChildAt(0)).setChecked(true);
                } else {
                    saveStoreCode();
                    startActivity(new Intent(activity, ShopDetailsActivity.class));
                }
            }
        });
        tv_check_store_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("999".equals(entity.storeCode)) {
                    activity.finish();
                    ((RadioButton) MainTabActivity.main_radio.getChildAt(1)).setChecked(true);
                } else {
                    saveStoreCode();
                    SourceConstant.GOTO_SHOP_HOME_OR_TYPE = "TYPE";
                    startActivity(new Intent(activity, ShopDetailsActivity.class));
                }
            }
        });
        tv_contact_shop_boss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIsOpenCall();
            }
        });

    }

    protected void initData() {
        sId = SpUtill.getString(activity, ResourseSum.Medica_SP, "saveSid");
        mParam = new HashMap<>();
        fileNameAli = activity.getSharedPreferences(SourceConstant.fileNameAli, 0);
        visitKey = activity.getSharedPreferences("cart", Context.MODE_PRIVATE).getString("visitkey", "");
        mBundle = activity.getIntent().getExtras();
        skuCode = mBundle.getString(SourceConstant.SKU_CODE);

        getData(sId, skuCode);
        getSkuIsCollect();
    }


    private void checkIsOpenCall() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {

                    ToastUtil.showMessageOKCancel("您需要获取打电话的权限\n设置方法:权限管理->电话->允许", activity,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    intent.setData(Uri.parse("package:" + "store.chinaotec.com.medicalcare"));
                                    startActivity(intent);
                                }
                            });
                } else {
                    openCallService();
                }
            } else {
                openCallService();
            }
        } else {
            openCallService();
        }
    }

    private void openCallService() {
        DialUtil iu = new DialUtil(activity);
        if (!"".equals(telePhone) && telePhone != null) {
//            startActivity(iu.getTel(telePhone));
            ToastUtil.MyToast(getActivity(),"暂无商家电话");
        }
    }

    /**
     * 设置gridView选中条目，使其选中的Item 背景色变色
     */
    private void selectData(GridView view, int resId, SelectAdapter adapter,
                            ArrayList<String> strlist, ArrayList<SelectModel> list, String content) {
        view = (GridView) activity.findViewById(resId);
        view.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

        int listSize = strlist.size();
        int flag = -1;
        flag = list.indexOf(new SelectModel("", content, ""));
        if (flag != -1) {
            list.get(flag).flag = "1";
        }
        selectAdapter(view, adapter, list, flag);
    }

    private void selectAdapter(GridView view, SelectAdapter adapter,
                               ArrayList<SelectModel> list, int flag) {
        adapter = new SelectAdapter(activity, list, R.layout.item_shop_market_select_sku);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });//TODO
        adapter.setSelectIndex(flag);
    }

    /**
     * 是否向服务器发送请求数据
     */
    private void isGetDat() {
        int index;
        if (entity.sizeList.size() > 0) { //&& entity.colorList.size() <= 0
            LogUtil.e(TAG, "===-=============只有尺寸，没有颜色");

            index = sizeSelectAdapter.getSelectIndex();
            LogUtil.e(TAG, "===-=============已经选中了一个尺寸：" + mSize + "index:" + index);
            getDataCode(storeCode, productCode, "", mSize);
        }
    }

    /**
     * 切换商品属性的时候请求的商品code
     */
    private void getDataCode(String storeCode, String productCode2, String mColor2, String mSize2) {
        mParam.clear();
        String url = RequestUrl.APP_HOME + RequestUrl.SKU_INFO_CODE;
        mParam.put("storeCode", storeCode);
        mParam.put("productCode", productCode2);
        try {
            mParam.put("size", URLEncoder.encode(mSize2, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                Result result = JSON.parseObject(response, Result.class);
                if (result.responseCode == 0) {
                    String typeSkuCode = result.data.toString();
                    LogUtil.e(TAG, "切换后的skuCode" + typeSkuCode);
                    getData(sId, typeSkuCode);
                } else {
                    skuCode = "";
                    ToastUtil.MyToast(activity, "该商品库存不足,可选其他商品");
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    private String setSelectGv(String content, View view, ArrayList<String> list, SelectAdapter adapter, int position) {
        content = list.size() == 0 ? "" : list.get(position);
        // // 判断本次点击是否与上次点击为同一控件
        // if (adapter.getSelectIndex() == position) {
        // view.setBackgroundColor(Color.TRANSPARENT);
        // content = "";
        // adapter.setSelectIndex(-1);
        // } else {
        content = list.get(position);
        // }
        return content;
    }

    /**
     * 查询商品是否收藏
     */
    private void getSkuIsCollect() {
        if (!"".equals(sId)) {
            mParam.clear();
            String url = RequestUrl.APP_HOME + RequestUrl.SKU_IS_COLLECT;
            mParam.put("sid", sId);
            mParam.put("skuCode", skuCode);

            OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

                @Override
                public void onResponse(String response, int id) {
                    Results result = JSON.parseObject(response, Results.class);

                    isCollect = result.data.toString();
                    if (isCollect != null && "y".equals(isCollect)) {
                        LogUtil.e(TAG, "收藏状态=====" + isCollect);
                        flagFavorites = false;
                        btn_favorites_bottom.setImageResource(R.drawable.check_favorite_down);
                        tv_favorites_txt.setText("已收藏");
                    } else {
                        flagFavorites = true;
                        btn_favorites_bottom.setImageResource(R.drawable.check_favorite_up);
                        tv_favorites_txt.setText("收藏");
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {

                }

            });

        }
    }

    /**
     * 请求获取不同类型的商品详情
     */
    private void getData(String sId, String skuCode) {
        mParam.clear();
        String url = RequestUrl.APP_HOME + RequestUrl.SKU_INFO;
        mParam.put("sid", sId);
        mParam.put("skuCode", skuCode);
        mParam.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);

        OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                Results result = JSON.parseObject(response, Results.class);
                if (!"".equals(result.data) && null != result.data) {
                    setData(result.data);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    private void addCart(String quantity) {
        mParam.clear();
        String url = RequestUrl.APP_HOME + RequestUrl.CART_ADD;
        mParam.put("skuCode", skuCode);
        mParam.put("quantity", quantity);
        mParam.put(SourceConstant.APP_CODE, SourceConstant.ANDROID_CODE);
        if ("".equals(sId)) {
            mParam.put("sid", "");
            mParam.put("visitKey", visitKey);
        } else {
            mParam.put("sid", sId);
            mParam.put("visitKey", "");
        }
        OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                Results result = JSON.parseObject(response, Results.class);
                if (result.responseCode.equals("0")) {
                    ToastUtil.MyToast(activity, "已成功加入购物车");
                } else {
                    ToastUtil.MyToast(activity, result.msg);
                }
                popupWindow.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    /***
     * 商品 收藏
     */
    public void getSkuADDorDelCollect() {
        LogUtil.e(TAG, "进了商品收藏方法===============");
        if (skuCode != null && !"".equals(skuCode)) {
            LogUtil.e(TAG, "if...favorites====skuCode有值了===============" + skuCode);
            mParam.clear();
            mParam.put("sid", sId);
            mParam.put("skuCode", skuCode);
            String url = RequestUrl.APP_HOME + RequestUrl.SKU_COLLECT_ADD_OR_DEL;
            if (!"".equals(sId)) {
                if (flagFavorites) {
                    mParam.put("isAttention", "y");
                } else {
                    mParam.put("isAttention", "n");
                }
            } else {
                // 未登录跳转到登录
//                startActivity(new Intent(activity, LoginActivity.class));
                return;
            }

            OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

                @Override
                public void onResponse(String response, int id) {
                    Results result = JSON.parseObject(response, Results.class);
                    if (flagFavorites) {
                        flagFavorites = false;
                        ToastUtil.MyToast(activity, "收藏成功");
                        btn_favorites_bottom.setImageResource(R.drawable.check_favorite_down);
                        tv_favorites_txt.setText("已收藏");
                    } else {
                        flagFavorites = true;
                        ToastUtil.MyToast(activity, "取消收藏");
                        btn_favorites_bottom.setImageResource(R.drawable.check_favorite_up);
                        tv_favorites_txt.setText("收藏");
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {

                }

            });
        }

    }

    public void showPopupWindow(View parent) {
        LogUtil.e(TAG, "进了pup开启的代码");
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = layoutInflater.inflate(R.layout.include_shop_market_select_sku, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置可以获得焦点
        popupWindow.setTouchable(true);
        // 设置弹窗内可点击
        popupWindow.setFocusable(true);
        // 设置弹窗背景
        Drawable win_bg = this.getResources().getDrawable(R.color.transparent);
        popupWindow.setBackgroundDrawable(win_bg);
        // 设置弹窗外可点击
        popupWindow.setOutsideTouchable(true);
        // popupWindow关闭时执行
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                view_night.setVisibility(View.GONE);
            }
        });

        iv_img_select = (ImageView) popView.findViewById(R.id.iv_img_select);
        btn_minus = (Button) popView.findViewById(R.id.btn_minus);
        btn_add = (Button) popView.findViewById(R.id.btn_add);
        tv_price_select = (TextView) popView.findViewById(R.id.tv_price_select);
        tv_title_select = (TextView) popView.findViewById(R.id.tv_title_select);
        tv_price_select_old = (TextView) popView.findViewById(R.id.tv_price_select_old);
        iv_exit_select_sku = (ImageView) popView.findViewById(R.id.iv_exit_select_sku);
        mBtnSkuSubmit = (Button) popView.findViewById(R.id.btn_sku_submit);
        mTvNum = (TextView) popView.findViewById(R.id.tv_num);

        if ("y".equals(entity.isTogetherSku)) {
            tv_price_select.setText("价格：￥ " + entity.togetherPrice);
        } else {
            if ("y".equals(entity.isTimelimitSku)) {
                tv_price_select.setText("价格：￥ " + entity.timelimitPrice);
            } else {
                tv_price_select.setText("价格：￥ " + skuprice);
            }
        }
        tv_title_select.setText(skuName);
        if (!VerifyUtil.isEmpyty(skuimgPath.trim())) {
            MyImageLoader.loadImage(skuimgPath, iv_img_select);
        } else {
            iv_img_select.setImageResource(R.drawable.error_img);
        }

        btn_minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(mTvNum.getText().toString().trim());
                if (num <= 2) {
                    mTvNum.setText("1");
                } else {
                    num--;
                    mTvNum.setText(num + "");
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int num1 = Integer.valueOf(mTvNum.getText().toString().trim());
                num1++;
                mTvNum.setText(num1 + "");
            }
        });

        iv_exit_select_sku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 如果类型集合有没有数据就隐藏布局,否则显示出来
        llSize = (LinearLayout) popView.findViewById(R.id.ll_size);
        if (VerifyUtil.isEmpty(entity.sizeList)) {
            llSize.setVisibility(View.GONE);
        } else {
            mGvSize = (GridView) popView.findViewById(R.id.gv_size);
            mGvSize.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

            int listSize = entity.sizeList.size();
            if (sizeLists == null) {
                sizeLists = new ArrayList<SelectModel>();
            }

            if (sizeLists.size() <= 0) {
                for (int i = 0; i < listSize; i++) {
                    if (entity.size.equals(entity.sizeList.get(i))) {
                        sizeLists.add(new SelectModel("" + i, entity.sizeList.get(i), "1"));
                    } else {
                        sizeLists.add(new SelectModel("" + i, entity.sizeList.get(i), "2"));
                    }
                }
            }
            int flag = -1;
            flag = sizeList.indexOf(new SelectModel("", mSize, ""));
            if (flag != -1) {
                sizeLists.get(flag).flag = "1";
            }

            sizeSelectAdapter = new SelectAdapter(activity, sizeLists, R.layout.item_shop_market_select_sku);
            mGvSize.setAdapter(sizeSelectAdapter);
            mGvSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSizeView = view;
                    mSize = setSelectGv(mSize, view, entity.sizeList, sizeSelectAdapter, position);
                    if (sizeSelectAdapter.getSelectIndex() != -1) {
                        mSizeView.setBackgroundResource(R.drawable.shop_market_rect_gray);
                    }
                    for (int i = 0; i < sizeSelectAdapter.getCount(); i++) {
                        if (i == position) {
                            sizeLists.get(i).flag = "1";
                        } else {
                            sizeLists.get(i).flag = "2";
                        }
                        LogUtil.e(TAG, "进了 尺寸的======for循环 更改了属性,// 1:选中 , 2:未选中 , 3: 不能选择======="
                                + sizeLists.get(i).flag);
                    }
                    LogUtil.e(TAG, "点了   mSize=======" + mSize);

                    sizeSelectAdapter.setSelectIndex(position);
                    sizeSelectAdapter.setDataList(sizeLists);
                    sizeSelectAdapter.notifyDataSetChanged();
                    sizeSelectAdapter.setSelectIndex(position);
                    selectData(mGvSize, R.id.gv_size, sizeSelectAdapter, entity.sizeList, sizeLists, mSize);
                    isGetDat();
                }
            });

            mSize = entity.size;
            LogUtil.e(TAG, "=============默认尺寸是：" + entity.size + "=============默认商品CODE是：" + entity.skuCode);
        }

        mBtnSkuSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(TAG, "进了加入购物车==========");
                quantity = mTvNum.getText().toString().trim();
                int quantityInt = Integer.parseInt(quantity);
                if (!"".equals(skuCode)) {
                    if (quotaNum == 0) {
                        addCart(quantity);
                        return;
                    }
                    if (quantityInt <= quotaNum) {
                        addCart(quantity);
                    } else {
                        ToastUtil.MyToast(activity, "该商品的限购" + quotaNum + "件");
                    }

                } else {
                    ToastUtil.MyToast(activity, "请重新选择其他规格商品");
                }
            }
        });
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    private void setData(String data) {
        entity = new SkuInfo();
        jsonParser = new JsonParser();
        json = jsonParser.parse(data.toString());
        entity.productCode = json.getAsJsonObject().get("productCode").getAsString();
        entity.name = json.getAsJsonObject().get("name").getAsString();
        entity.skuCode = json.getAsJsonObject().get("skuCode").getAsString();
        skuCode = entity.skuCode;
        entity.storeCode = json.getAsJsonObject().get("storeCode").getAsString();
        storeCode = entity.storeCode;
        entity.storeName = json.getAsJsonObject().get("storeName").getAsString();
        entity.storeImgPath = json.getAsJsonObject().get("storeImgPath").getAsString();
        entity.size = json.getAsJsonObject().get("size").getAsString();
        productCode = entity.productCode;
        entity.currentPrice = json.getAsJsonObject().get("currentPrice").getAsString();

        entity.isTimelimitSku = json.getAsJsonObject().get("isTimelimitSku").getAsString();
        entity.timelimitPrice = json.getAsJsonObject().get("timelimitPrice").getAsString();
        entity.isTogetherSku = json.getAsJsonObject().get("isTogetherSku").getAsString();
        entity.togetherPrice = json.getAsJsonObject().get("togetherPrice").getAsString();
        entity.telephone = json.getAsJsonObject().get("telephone").getAsString();
        telePhone = entity.telephone;
        entity.freightInfo = json.getAsJsonObject().get("freightInfo").getAsString();
        entity.quotaNum = json.getAsJsonObject().get("quotaNum").getAsInt();
        quotaNum = entity.quotaNum;
        JsonObject jsonMemberComment = json.getAsJsonObject().get("memberComment").getAsJsonObject();
        entity.memberComment.commentContent = jsonMemberComment.get("commentContent").getAsString();
        commentContent = entity.memberComment.commentContent;

        JsonArray jsonSkuImages = json.getAsJsonObject().get("skuImages").getAsJsonArray();
        ArrayList<String> skuImages = new ArrayList<String>();
        for (int i = 0; i < jsonSkuImages.size(); i++) {// 遍历JSONArray
            JsonElement oj = jsonSkuImages.get(i);
            skuImages.add(oj.getAsString());
        }
        entity.skuImages = skuImages;
        imageUrls = new String[entity.skuImages.size()];
        for (int i = 0; i < entity.skuImages.size(); i++) {
            String url = entity.skuImages.get(i);
            imageUrls[i] = url;
        }
        mTopViewPager.setImageUrls(imageUrls);

        JsonArray jsonSizeList = json.getAsJsonObject().get("sizeList").getAsJsonArray();
        sizeList = new ArrayList<String>();
        for (int i = 0; i < jsonSizeList.size(); i++) {// 遍历JSONArray
            JsonElement oj = jsonSizeList.get(i);
            sizeList.add(oj.getAsString());
        }
        entity.sizeList = sizeList;
        // ------------------------------图文详情------------------------------------------

        Log.e(TAG, "graphicDetails.toString()===" + json.getAsJsonObject().get("graphicDetails").toString());
        if (!json.getAsJsonObject().get("graphicDetails").toString().equals("null")
                && !json.getAsJsonObject().get("graphicDetails").toString().equals("")) {
            JsonArray JsongraphicDetails = json.getAsJsonObject().get("graphicDetails").getAsJsonArray();
            parameterList = new ArrayList<GraphicDetail>();
            for (int i = 0; i < JsongraphicDetails.size(); i++) {// 遍历JSONArray
                JsonObject oj = JsongraphicDetails.get(i).getAsJsonObject();
                GraphicDetail skugd = new GraphicDetail();
                skugd.url = oj.get("url").getAsString();
                skugd.height = oj.get("height").getAsInt();
                parameterList.add(skugd);
            }
        }
        // ------------------------------猜你喜欢------------------------------------------

        if (!json.getAsJsonObject().get("linkSkus").toString().equals("null")
                && !json.getAsJsonObject().get("linkSkus").toString().equals("")) {
            LogUtil.e(TAG, "linkSkus不为空========");
            JsonArray JsonLikeList = json.getAsJsonObject().get("linkSkus").getAsJsonArray();
            linkSkusList = new ArrayList<Sku>();// 猜你喜欢(商品列表)
            for (int i = 0; i < JsonLikeList.size(); i++) {// 遍历JSONArray
                JsonObject oj = JsonLikeList.get(i).getAsJsonObject();
                Sku skuAttr = new Sku();
                skuAttr.name = oj.get("name").getAsString();
                skuAttr.skuCode = oj.get("skuCode").getAsString();
                skuAttr.imgPath = oj.get("imgPath").getAsString();
                skuAttr.currentPrice = oj.get("currentPrice").getAsString();
                skuAttr.productCode = oj.get("productCode").getAsString();
                skuAttr.color = oj.get("color").getAsString();
                skuAttr.size = oj.get("size").getAsString();
                linkSkusList.add(skuAttr);
            }
        } else {
            LogUtil.e(TAG, "linkSkus是空========");
        }
        entity.linkSkus = linkSkusList;
        productCode = entity.productCode;
        skuName = entity.name;
        skuprice = entity.currentPrice;
        mTvSkuName.setText(skuName);

        if ("y".equals(entity.isTogetherSku)) {
            tv_sku_old_vip.setText("团购价：" + entity.togetherPrice);
            mTvSkuPrice.setText("价格：￥ " + skuprice);
        } else {
            if ("y".equals(entity.isTimelimitSku)) {
                tv_sku_old_vip.setText("秒杀价：" + entity.timelimitPrice);
                mTvSkuPrice.setText("价格：￥ " + skuprice);
            } else {
                mTvSkuPrice.setText("价格：￥ " + skuprice);
            }
        }
        tvFreight.setText(entity.freightInfo);
        tv_store_name.setText(entity.storeName);
        String imgUrl = entity.storeImgPath;
        if (!"".equals(imgUrl)) {
            MyImageLoader.displayImage(imgUrl, iv_store_icon);
        } else {
            iv_store_icon.setBackgroundResource(R.drawable.icon_bg_store);
        }

        // mTvPriceOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线

        // 配置猜你喜欢的参数---------------------------------
        if (VerifyUtil.isEmpty(entity.linkSkus)) {
            ll_may_like.setVisibility(View.GONE);
        } else {
            ll_may_like.setVisibility(View.VISIBLE);
            if (linkSkusList == null) {
                linkSkusList = new ArrayList<Sku>();
            }
            int linkSkusSize = entity.linkSkus.size();
            if (linkSkusList.size() <= 0) {
                for (int i = 0; i < linkSkusSize; i++) {
                    linkSkusList.addAll(entity.linkSkus);
                }
            }
            // LogUtil.e(TAG, "猜你喜欢=========linkSkusList.size:" + linkSkusList.size());
            likeAdapter = new HorizontalListViewAdapter(this.activity, linkSkusList);
            for (int i = 0; i < linkSkusList.size(); i++) {
                LogUtil.e(TAG, "linkSkusList" + linkSkusList.get(i).toString());
            }
            likeAdapter.notifyDataSetChanged();
            gv_tv_may_like.setAdapter(likeAdapter);
            gv_tv_may_like.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(activity, SkuInfoActivity.class);
                    mIntent.putExtra(SourceConstant.SKU_CODE, linkSkusList.get(position).skuCode + "");
                    startActivity(mIntent);
                }
            });
        }
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 配置 图文详情
        if (parameterList != null) {
            SkuParameterAdapter skuParameterAdapter = new SkuParameterAdapter(activity, parameterList, dm.widthPixels);
            lv_sku_parameter.setAdapter(skuParameterAdapter);
        }

        if (entity.skuImages.size() > 0) {
            skuimgPath = entity.skuImages.get(0);
            LogUtil.e(TAG, "==================图片集合不为空：" + skuimgPath);
        } else {
            skuimgPath = "";
        }
        if (popView != null) {
            LogUtil.e(TAG, "弹窗不是空的了");
            if ("y".equals(entity.isTogetherSku)) {
                tv_price_select.setText("价格：￥ " + entity.togetherPrice);
            } else {
                if ("y".equals(entity.isTimelimitSku)) {
                    tv_price_select.setText("价格：￥ " + entity.timelimitPrice);
                } else {
                    tv_price_select.setText("价格：￥ " + skuprice);
                }
            }
            tv_title_select.setText(skuName);
            if (!"".equals(skuimgPath.trim())) {
                MyImageLoader.loadImage(skuimgPath, iv_img_select);
            } else {
                iv_img_select.setImageResource(R.drawable.error_img);
            }
        }

        if (!VerifyUtil.isEmpyty(entity.memberComment.commentContent)) {
            tv_comment.setText(entity.memberComment.commentContent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rll_sku_evaluate:
                if (!VerifyUtil.isEmpyty(commentContent)) {
                    Intent intent = new Intent(activity, SkuEvaluateListActivity.class);
                    intent.putExtra(SourceConstant.SKU_CODE, skuCode);
                    startActivity(intent);
                } else {
                    ToastUtil.MyToast(activity, "该商品尚未评论");
                }
                break;
            case R.id.ll_sku_details_store:
                if ("999".equals(entity.storeCode)) {
                    activity.finish();
                    ((RadioButton) MainTabActivity.main_radio.getChildAt(0)).setChecked(true);
                } else {
                    saveStoreCode();
                    startActivity(new Intent(activity, ShopDetailsActivity.class));
                }
                break;
            case R.id.ll_favorites_bottom:
                getSkuADDorDelCollect();
                break;
        }
    }
}
