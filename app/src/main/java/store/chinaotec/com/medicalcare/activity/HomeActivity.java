package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyhomeFragmentAdapter;
import store.chinaotec.com.medicalcare.fragment.HeaManaFragment;
import store.chinaotec.com.medicalcare.fragment.HomeFragment;
import store.chinaotec.com.medicalcare.fragment.MineFragment;
import store.chinaotec.com.medicalcare.fragment.NoticeFragment;
import store.chinaotec.com.medicalcare.javabean.NickPhone;
import store.chinaotec.com.medicalcare.listener.HomeChangListener;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.Colector;
import store.chinaotec.com.medicalcare.utill.ModueUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.UpdateVersionUtil;

/**
 * Created by hxk on 2017/7/5 0005 11:32
 */

public class HomeActivity extends BaseActivity {
    @Bind(R.id.viewpager_main)
    ViewPager viewpagerMain;
    @Bind(R.id.main_home)
    RadioButton mainHome;
    @Bind(R.id.main_notice)
    RadioButton mainNotice;
    @Bind(R.id.main_health_manage)
    RadioButton mainHealthManage;
    @Bind(R.id.main_mine)
    RadioButton mainMine;

    private List<Fragment> fragments;
    boolean isPressedBackOnce = false;
    private List<RadioButton> radioButtons;
    private int USER_TYPE = 00;
    private long firstTime = 0;
    private long secondTime = 0;
    private SharedPreferences sharedPreferences;
    private MyhomeFragmentAdapter myhomeFragmentAdapter;
    public static final String TAG = "HomeActivity";
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openLocationService();
//        initFragment();
        UpdateVersionUtil.checkUpdate(this);
    }

    private void initBasicData() {
        mainHome.setChecked(true);
        //初始化碎片数据源
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.instance(city));
//        fragments.add(HomeFragment.instance("上海"));
        fragments.add(NoticeFragment.instance());
        fragments.add(HeaManaFragment.instance());
        fragments.add(MineFragment.instance());
        //初始化按钮集合
        radioButtons = new ArrayList<>();
        radioButtons.add(mainHome);
        radioButtons.add(mainNotice);
        radioButtons.add(mainHealthManage);
        radioButtons.add(mainMine);
        //初始化sp对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("clickLogin", false).apply();
        sharedPreferences.edit().putBoolean("signOut", false).apply();
        sharedPreferences.edit().putBoolean("changeLogo", false).apply();

    }

    private void openLocationService() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        initLocationOption(mLocationClient);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    private void iniListener() {
//        viewpagerMain.setScanScroll(false);
        //展示fragment
        myhomeFragmentAdapter = new MyhomeFragmentAdapter(getSupportFragmentManager(), fragments);
        viewpagerMain.setAdapter(myhomeFragmentAdapter);
        viewpagerMain.setOffscreenPageLimit(1);

        Intent intent = getIntent();
        if (intent != null) {
            int normalUser = intent.getIntExtra("normalUser", 000);
            if (normalUser == 111) {
                mainMine.setChecked(true);
                viewpagerMain.setCurrentItem(3);
            }
        }
        //随着滑动对应标题按钮显示选中效果
        viewpagerMain.addOnPageChangeListener(new HomeChangListener(radioButtons, fragments, this));
        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.main_home:
                            viewpagerMain.setCurrentItem(0);
                            setButtonUncheck(mainHome.getId());
                            break;
                        case R.id.main_notice:
                            viewpagerMain.setCurrentItem(1);
                            setButtonUncheck(mainNotice.getId());
                            break;
                        case R.id.main_health_manage:
                            viewpagerMain.setCurrentItem(2);
                            setButtonUncheck(mainHealthManage.getId());
                            break;
                        case R.id.main_mine:
                            viewpagerMain.setCurrentItem(3);
                            setButtonUncheck(mainMine.getId());
                            break;
                    }
                }
            });
        }
    }

    /**
     * @param id 当前RadioButton按钮的id
     *           设置其他按钮为非选中状态
     */
    private void setButtonUncheck(int id) {
        for (int j = 0; j < radioButtons.size(); j++) {
            if (id != radioButtons.get(j).getId()) {
                radioButtons.get(j).setChecked(false);
            }
        }
    }

    /**
     * 首页点击返回键点击两次退出
     */
    @Override
    public void onBackPressed() {
        if (isPressedBackOnce) {
            secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                // 说明第一次点击作废 时间重新算
                BaseUtill.toastUtil("再点一次退出");
                firstTime = System.currentTimeMillis();
                isPressedBackOnce = true;
            } else {
                ModueUtill.loginOutClean(this);
                Colector.clearActivity();
                isPressedBackOnce = false;
                firstTime = 0;
                secondTime = 0;
                sharedPreferences.edit().putString("caseCode", "").apply();
            }
        } else {
            // 说明是第一次点击
            BaseUtill.toastUtil("再点一次退出");
            firstTime = System.currentTimeMillis();
            isPressedBackOnce = true;
        }
    }

    /**
     * @return 获取登录用户的信息名字 电话 用户种类
     */
    public NickPhone getLoginInfo() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        }
        NickPhone nickPhone = null;
        try {
            nickPhone = null;
            String nickName = sharedPreferences.getString("username", "");
            String phone = sharedPreferences.getString("userphone", "");
            int type = sharedPreferences.getInt("usertype", USER_TYPE);
            if ((!(TextUtils.isEmpty(nickName))) && (!(TextUtils.isEmpty(phone)))) {
                nickPhone = new NickPhone(type, nickName, phone);
                return nickPhone;
            }
        } catch (Exception e) {
            return nickPhone;
        }
        return nickPhone;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        //判断是否进行登陆操作并刷新
//        boolean clickLogin = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "clickLogin");
//        if (clickLogin) {
//            ReshUtill.reshMineFragment(viewpagerMain.getCurrentItem(), fragments, this);
//        }
        //判断用户是否退出登录并刷新
//        boolean signOut = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "signOut");
//        if (signOut) {
//            ReshUtill.reshMineFragment(viewpagerMain.getCurrentItem(), fragments, this);
//        }
        //用户logo是否变更并刷新显示
//        boolean changeLogo = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "changeLogo");
//        if (changeLogo) {
//            ReshUtill.reshMineFragment(viewpagerMain.getCurrentItem(), fragments, this);
//        }
    }

    private void initLocationOption(AMapLocationClient mLocationClient) {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        LogUtil.e("高德地图====", "启动了定位...");

    }

    private String city;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度

                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    city = amapLocation.getCity();//城市信息
                    SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "saveLocationCity", amapLocation.getCity());
                    amapLocation.getDistrict();//城区信息
                    SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "saveLocationAddress", amapLocation.getLatitude() + "," + amapLocation.getLongitude());
                    if (isFirst) {
                        initFragment();
                        isFirst = false;
                    }

                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
//	            amapLocation.getAOIName();//获取当前定位点的AOI信息
                } else {
                    mLocationClient.stopLocation();//停止定位
                    initFragment();
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    LogUtil.e("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
                }
            }
        }
    };

    private void initFragment() {
        initBasicData();
        iniListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();//停止定位
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
        isFirst = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                openLocationService();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
