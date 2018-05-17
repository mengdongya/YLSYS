package store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.http.DataCallBack;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestsImpl.RequestParamsImpl;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestsImpl.ResultFormatImpl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.XListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.adapter.ShopListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.entity.Shops;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;


/**
 * 店铺--列表
 * Created by wjc on 2016/8/3 0003.
 */
public class ShopListActivity extends BaseAoActivity implements AdapterView.OnItemClickListener {
    private XListView lv_store_list;
    private LinearLayout ll_no_stores;
    private TextView mTvTitle;
    private User user;
    private String sId;
    private RequestParamsImpl mReqParams = null;
    private ResultFormatImpl mResFormat = null;
    private int page_index = 1;
    private String TAG = "ShopListActivity";
    private ShopListAdapter mAdapter;
    private SharedPreferences fileNameAli;
    private ArrayList<Shops> shopList;
    private List<Shops> shopLists;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private double latitude;
    private double longitude;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    latitude = amapLocation.getLatitude();//获取纬度
                    longitude = amapLocation.getLongitude();//获取经度
                    LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    LogUtil.e("定位到的经度======", "getLongitude()" + amapLocation.getLongitude());
                    LogUtil.e("定位到的纬度======", "getLatitude()" + amapLocation.getLatitude());
                    getLocationData();
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    LogUtil.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }

    };

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == 100) {

            } else if (requestCode == 101) {
                openLocationService();
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == 100) {
            } else if (requestCode == 101) {
            }
        }
    };
    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(ShopListActivity.this)
                    .setTitle("友好提醒！")
                    .setMessage("没有定位权限将不能获取周围的店铺，请允许获取该权限")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();// 用户同意继续申请。
                        }
                    })
                    .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel(); // 用户拒绝申请。
                        }
                    }).show();
        }
    };
    private boolean flagClear = false;
    private boolean isRefresh = true;
    private Handler handler;
    private boolean isFirstComeIn = true;
    private ImageView mBtnBack;

    @Override
    protected void onClickEvent(View paramView) {
    }


    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_shop_list);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        lv_store_list = (XListView) findViewById(R.id.lv_store_list);
        ll_no_stores = (LinearLayout) findViewById(R.id.ll_no_stores);
        user = UserKeeper.readUser(this);
        sId = user.sid;
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        handler = new Handler();
    }

    @Override
    protected void initListener() {
        lv_store_list.setOnItemClickListener(this);
        lv_store_list.setOnItemClickListener(this);
        lv_store_list.setPullLoadEnable(true);
        lv_store_list.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page_index = 1;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getShopData();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                page_index++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getShopData();
                    }
                }, 1000);
            }
        });

    }

    @Override
    protected void processLogic() {
        mTvTitle.setText("店铺");
        mBtnBack.setVisibility(View.INVISIBLE);
        mReqParams = new RequestParamsImpl(this);
        mResFormat = new ResultFormatImpl();
        shopLists = new ArrayList<>();
        mAdapter = new ShopListAdapter(this, shopLists);
        lv_store_list.setAdapter(mAdapter);
//        CheckPermiss();
        getShopData();
    }

    private void getShopData() {
        AorunApi.getShopStoreList(page_index, new DataCallBack() {
            @Override
            protected void parseData(String s, RequestVo requestVo) {
                List<Shops> shopses = JSONArray.parseArray(s, Shops.class);

                if (page_index == 1) {
                    shopLists.clear();
                }
                shopLists.addAll(shopses);
                mAdapter.notifyDataSetChanged();
                lv_store_list.stopLoadMore();
                lv_store_list.stopRefresh();
            }
        });
    }

    private void CheckPermiss() {

        AndPermission.with(this)
                .requestCode(101)
                .permission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .rationale(mRationaleListener)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    @Override
    protected void onResume() {
        LogUtil.e(TAG, "=======onResume=======");
        super.onResume();
    }

    private void openLocationService() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        initLocation();
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.SHOP_LIST:
                if (flagClear) {
                    flagClear = false;
                    LogUtil.e(TAG, "清空了集合");
                    shopLists.clear();
                    mAdapter.notifyDataSetChanged();
                }
                shopList = mResFormat.formatShopList(data);
                if (shopList.size() > 0) {
                    isRefresh = true;
                    isFirstComeIn = false;
                    shopLists.addAll(shopList);
                    mAdapter.notifyDataSetChanged();
                    LogUtil.e(TAG, "当前第几页: " + page_index);
                    page_index++;

                    lv_store_list.setVisibility(View.VISIBLE);
                    ll_no_stores.setVisibility(View.GONE);
                } else {
                    if (isFirstComeIn) {
                        lv_store_list.setVisibility(View.GONE);
                        ll_no_stores.setVisibility(View.VISIBLE);
                    } else {
                        isRefresh = false;
                        isFirstComeIn = false;
                    }
                }


//                if (mLocationClient != null) {
//                    mLocationClient.stopLocation();
//                    mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
//                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor = fileNameAli.edit();
        editor.putString(SourceConstant.STORE_CODE, shopLists.get(position - 1).getStoreCode());
//        editor.putString(SourceConstant.STORE_LOCATION,shopList.get(position).getLocation());
        editor.commit();

        Intent intent = new Intent(this, ShopDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


//    @Override
//    public void onRefresh() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                flagClear = true;
//                page_index = 1;
//                getShopData();
//                load();
//            }
//        }, 1000);
//    }
//
//    @Override
//    public void onLoadMore() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isRefresh) {
//                    getShopData();
//                    mAdapter.notifyDataSetChanged();
//                    load();
//                } else {
//                    lv_store_list.stopLoadMore();
//                }
//            }
//        }, 1000);
//    }
//
//    private void load() {
//        lv_store_list.stopRefresh();
//        lv_store_list.stopLoadMore();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
//        String str = formatter.format(curDate);
//        lv_store_list.setRefreshTime(str);
//    }

    private void initLocation() {
        //初始化定位
//            mLocationClient = new AMapLocationClient(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
//            //设置定位回调监听
//            mLocationClient.setLocationListener(this);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(2000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
//        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        LogUtil.e("", "开启了定位=======");
    }

    private void getLocationData() {
        AorunApi.getShopLocationList(String.valueOf(longitude), String.valueOf(latitude), mDataCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
        mLocationClient = null;
    }
}
