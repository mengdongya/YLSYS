package store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;

/**
 * Created by seven on 2016/9/25 0025.
 */
public class AmapActivity extends Activity implements LocationSource, OnGeocodeSearchListener,
        AMapLocationListener, RouteSearch.OnRouteSearchListener {
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private MapView mMapView;
    private TextView tvHomeTitle;
    private ImageView btnBack;
    private AMap aMap;
    private TextView title_btn_right;
    private GeocodeSearch geocoderSearch;
    private RouteSearch routeSearch;
    private int drivingMode = RouteSearch.DrivingDefault;// 驾车默认模式
    private LatLonPoint startPoint;
    private LatLonPoint endPoint;
    private RouteSearch.FromAndTo fromAndTo;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private DriveRouteResult driveRouteResult;
    private OnLocationChangedListener mListener;
    private Marker mLocMarker;
    private boolean mFirstFix = false;
    private Circle mCircle;
    private AlertDialog dialog;
    private String startInto;
    private Bundle mBundle;
    private String address;
    private String location;//从店铺列表传过来的经纬度
    private LatLonPoint latLonPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_market_map_gao_de);
        initView(savedInstanceState);
//        searchAddress();
        searchStringAddress();
    }

    private void initView(Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        tvHomeTitle = (TextView) findViewById(R.id.title_textview);
        btnBack = (ImageView) findViewById(R.id.title_btn_left);
        title_btn_right = (TextView) findViewById(R.id.title_btn_right);
        tvHomeTitle.setText("地图");
        title_btn_right.setVisibility(View.VISIBLE);
        title_btn_right.setText("导航");
        title_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDia();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBundle = getIntent().getExtras();
        endLongitude = Double.valueOf(mBundle.getString(SourceConstant.STORE_LOCATION_LONGITUDE));
        endLatitude = Double.valueOf(mBundle.getString(SourceConstant.STORE_LOCATION_LATITUDE));
        if (!"".equals(endLongitude) && !"".equals(endLatitude)) {
            latLonPoint = new LatLonPoint(endLatitude, endLongitude);
            endPoint = AMapUtil.convertToLatLonPoint(new LatLng(endLatitude, endLongitude));
        }
//        address = "玉门市市政广场";
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }

    }

    private void showDia() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        dialog = adb.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_choose_navi, null);

        TextView dialog_navi = (TextView) view.findViewById(R.id.dialog_navi);
        TextView dialog_baidu_map = (TextView) view.findViewById(R.id.dialog_baidu_map);
        TextView dialog_gaode_map = (TextView) view.findViewById(R.id.dialog_gaode_map);
        TextView dialog_map_cancel = (TextView) view.findViewById(R.id.dialog_map_cancel);

        dialog_baidu_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = null;
                try {
//                it = Intent.getIntent("intent://map/geocoder?address=" + getString(R.string.shanghai) + address +
//                        "&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    it = Intent.getIntent("intent://map/direction?origin=latlng:" + startInto + "|name:" + "" +
                            "&destination=" + address + "&mode=driving" + "&region=" + "上海" + "&src=thirdapp.navi.yourCompanyName.yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    startActivity(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog_gaode_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = Intent.getIntent("androidamap://route?sourceApplication=aiyumen&slat=" + startLatitude + "&slon=" + startLongitude +
                            "&sname=" + "" + "&dlat=" + endLatitude + "&dlon=" + endLongitude + "&dname=" + address + "&dev=0&m=0&t=2");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (!isAvilible(this, "com.baidu.BaiduMap")) {
            if (!isAvilible(this, "com.autonavi.minimap")) {
                Toast.makeText(this, getString(R.string.no_map_app), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        dialog.setView(view, 0, 0, 0, 1);
        if (isAvilible(this, "com.baidu.BaiduMap") && isAvilible(this, "com.autonavi.minimap")) {
            dialog_baidu_map.setVisibility(View.VISIBLE);
            dialog_gaode_map.setVisibility(View.VISIBLE);
            dialog.show();
            return;
        }

        if (isAvilible(this, "com.baidu.BaiduMap")) {
            dialog_baidu_map.setVisibility(View.VISIBLE);
            dialog.show();
        }

        if (isAvilible(this, "com.autonavi.minimap")) {
            dialog_gaode_map.setVisibility(View.VISIBLE);
            dialog.show();
        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        initLocation();
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
            mLocationOption.setInterval(1000);
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
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
//                mLocationErrText.setVisibility(View.GONE);
                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                startInto = amapLocation.getLatitude() + "," + amapLocation.getLongitude();
                startPoint = AMapUtil.convertToLatLonPoint(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                startLatitude = amapLocation.getLatitude();
                startLongitude = amapLocation.getLongitude();
                LogUtil.e("", "startPoint=======" + amapLocation.getLatitude() + "," + amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
                    addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                    addMarker(location);//添加定位图标
//                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                //回调中规划驾车路线
                driveLine();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
//                mLocationErrText.setVisibility(View.VISIBLE);
//                mLocationErrText.setText(errText);
            }
        }
    }

    private void searchStringAddress() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    private void searchAddress() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        GeocodeQuery query = new GeocodeQuery("临平路333弄", "上海");
        geocoderSearch.getFromLocationNameAsyn(query);
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                address = result.getRegeocodeAddress().getFormatAddress();

            } else {
                ToastUtil.MyToast(AmapActivity.this, R.string.no_result);
            }
        } else {
            ToastUtil.MyToast(this, rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
//                endLatitude = address.getLatLonPoint().getLatitude();
//                endLongitude = address.getLatLonPoint().getLongitude();
//                endPoint =  AMapUtil.convertToLatLonPoint(new LatLng(endLatitude,endLongitude));
                LogUtil.e("", "endPoint=======" + endLatitude + "," + endLongitude);

            } else {
                ToastUtil.MyToast(AmapActivity.this, R.string.no_result);
            }
        }
    }

    private void driveLine() {

        if (startPoint == null) {
            ToastUtil.MyToast(this, "定位中，稍后再试...");
            return;
        }
        if (endPoint != null) {
            fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
        }
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
        // fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
        // 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, drivingMode, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    driveRouteResult = result;
                    final DrivePath drivePath = driveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(this,
                            aMap, drivePath,
                            driveRouteResult.getStartPos(),
                            driveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    if (mLocationClient != null) {
                        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                    }
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.MyToast(this, R.string.no_result);
                }

            } else {
                ToastUtil.MyToast(this, R.string.no_result);
            }
        } else {
            ToastUtil.MyToast(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
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

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
        deactivate();
        mFirstFix = false;
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

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
    }

    private boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

}
