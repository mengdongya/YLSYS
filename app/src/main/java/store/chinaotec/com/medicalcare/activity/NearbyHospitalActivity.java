package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.ViewPoiOverlay;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.AmapActivity;

/**
 * Created by wjc on 2017/9/23 0023.
 */
public class NearbyHospitalActivity extends BaseActivity implements LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener, PoiSearch.OnPoiSearchListener {
    @Bind(R.id.mapView)
    MapView mMapView;
    @Bind(R.id.title_btn_left)
    ImageView ivBack;
    @Bind(R.id.ll_near_by_hospital)
    LinearLayout llNearByHospital;
    private AMap aMap;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private String city;
    private LatLonPoint latLonPoint;
    private PoiSearch.SearchBound searchBound;
    private PoiResult poiResults;
    private boolean hasSearch = true;
    private MyLocationStyle myLocationStyle;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_hospital);
        ButterKnife.bind(this);
        initView(savedInstanceState);
//        initPopWindow();
    }

    private void initView(Bundle savedInstanceState) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);// 此方法必须重写

        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示

//         设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        aMap.setOnMarkerClickListener(this);
//        aMap.setInfoWindowAdapter(this);

        myLocationStyle = new MyLocationStyle();
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_hospital_loaction);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        myLocationStyle.myLocationIcon(des);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        initLocation();
    }

    private void initSearch() {
        //第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query("医院", "", city);
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);//设置回调数据的监听器
        //点附近2000米内的搜索结果
        if (latLonPoint != null) {
            searchBound = new PoiSearch.SearchBound(latLonPoint, 4000);
            poiSearch.setBound(searchBound);
        }
        poiSearch.searchPOIAsyn();//开始搜索

        LogUtil.e("poiSearch====", "开始搜索");
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    private void initLocation() {
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
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
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {

                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                city = amapLocation.getCity();
                //获得小点
                if (latLonPoint == null) {
                    latLonPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
                } else {
                    latLonPoint.setLatitude(amapLocation.getLatitude());
                    latLonPoint.setLongitude(amapLocation.getLongitude());
                }
                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());


                LogUtil.e("", "startPoint=======" + amapLocation.getLatitude() + "," + amapLocation.getLongitude());

                if (hasSearch) {
                    initSearch();
                    hasSearch = false;
                }

//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            Log.i("---", "查询结果:" + i);
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    poiResults = poiResult;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        ViewPoiOverlay poiOverlay = new ViewPoiOverlay(aMap, poiItems, getApplicationContext());
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
//                        showSuggestCity(suggestionCities);
                    } else {
                        Toast.makeText(NearbyHospitalActivity.this, "未找到结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(NearbyHospitalActivity.this, "该距离内没有找到结果", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("---", "查询结果:" + i);
            Toast.makeText(NearbyHospitalActivity.this, "异常代码---" + i, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        LatLng position = marker.getPosition();
//        popupWindow.showAtLocation(llNearByHospital, Gravity.BOTTOM, 0, 0);
        initPopWindow(marker);
        return false;
    }


    private void initPopWindow(final Marker marker) {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.popwindow_near_by_hospital, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 设置可以获得焦点
        popupWindow.setTouchable(true);
        // 设置弹窗内可点击
        popupWindow.setFocusable(true);
        // 设置弹窗背景
        Drawable win_bg = this.getResources().getDrawable(R.color.transparent);
        popupWindow.setBackgroundDrawable(win_bg);
        // 设置弹窗外可点击
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(llNearByHospital, Gravity.BOTTOM, 0, 0);

        ((TextView)view.findViewById(R.id.tv_hospital_info)).setText(marker.getTitle());
        view.findViewById(R.id.tv_hospital_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearbyHospitalActivity.this, AmapActivity.class);
                intent.putExtra(SourceConstant.STORE_LOCATION_LONGITUDE, String.valueOf(marker.getPosition().longitude));
                intent.putExtra(SourceConstant.STORE_LOCATION_LATITUDE, String.valueOf(marker.getPosition().latitude));
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
    }

}
