package store.chinaotec.com.medicalcare.shopmarket.logic.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.app.AppManager;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.refresh.RefreshLayout;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil.NetUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.HomeScrollView;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollGridView;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.FunctionListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.GvDiscounAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.LableListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.bannerweight.CycleViewPager;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.bannerweight.ViewFactory;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.ADInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.CategoryAndPre;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.FunctionalBlock;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.Home;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.ViewPagerImage;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.AllOrderActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.ShopDetailsActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuListDiscountActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.activity.TypeActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.MyFavoritesActivity;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 首页
 */
public class MainActivity extends BaseAoActivity implements ListView.OnItemClickListener,
        RefreshLayout.OnHeaderRefreshListener, HomeScrollView.OnScrollViewListener, android.widget.ViewSwitcher.ViewFactory {
    public static String sid;
    int id = 0; //switcherNews 数组的Id;
    private boolean isSendVisible = false;
    private boolean isSendGone = false;
    private boolean tip = true;
    private View view;
    private RefreshLayout swipeLayout;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    // /** 轮播控件 */
    private CycleViewPager cycleViewPager;
    // 自定义轮播图的资源
    private String[] imageUrls;
    private String[] imageUrlLables;
    private ArrayList<ViewPagerImage> mbanner;
    private MyScrollGridView gvDiscoun;
    private GvDiscounAdapter gvDiscounAdapter;
    private Home homeList;
    /**
     * 回到顶部
     */
    private ScrollTopReceiver mReceiver;
    private String TAG = "MainActivity";
    private MyScrollGridView gv_home_function;
    private ScrollView sv;
    private String homeData;//首页缓存数据
    private SharedPreferences fileNameAli;
    private String visitKey;
    private String[] storeImageUrls;
    private GridView gv_two_lable;
    private MyScrollGridView gv_six_lable;
    private ImageView iv_special_0;
    private ImageView iv_special_1;
    private ImageView iv_special_2;
    private ImageView iv_special_3;
    private TextSwitcher wel_Txt_Switcher;
    private LinearLayout ll_alading_shop_home;
    private LinearLayout ll_hot_recommend;
    private String specialLinkUrl;
    private TextView tvHomeTitle;
    private ImageView btnBack;
    private ImageView btnSearch;
    private LinearLayout ll_no_net;
    private String skuCode;
    private String[] switcherNews;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// 覆盖handleMessage方法
            switch (msg.what) {// 根据收到的消息的what类型处理
                // 无网络
                case SourceConstant.ONE:
                    id = next(); //更新Id值
                    updateText();//更新TextSwitcherd显示内容;
                    break;
                // 回到顶部
                case SourceConstant.TWO:
                    ScaleTop();
                    // 发送隐藏的广播
                    MainActivity.this.sendBroadcast(new Intent(SourceConstant.SCROLL_TOP_VISIBILITY_GONE));
                    LogUtil.e(TAG, "发送隐藏的广播============");
                    break;
                case SourceConstant.THREE:
                    startActivity(new Intent(MainActivity.this, ShopDetailsActivity.class));
                    break;
                case SourceConstant.FOUR:
                    startActivity(new Intent(MainActivity.this, ShopMarketSeckillAndGroupBuyActivity.class));
                    break;
                case SourceConstant.FIVE:
                    Intent intent = new Intent(MainActivity.this, SkuInfoActivity.class);
                    intent.putExtra(SourceConstant.SKU_CODE, skuCode);
                    startActivity(intent);
                    LogUtil.e(TAG, "=====开启了商品详情");
                    break;
                case SourceConstant.SIX:
                    startActivity(new Intent(MainActivity.this, ShopDetailsActivity.class));
                    break;
                case SourceConstant.EIGHT:
                    startActivity(new Intent(MainActivity.this, ShopDetailsActivity.class));
                    break;
                default:
                    super.handleMessage(msg);// 这里最好对不需要或者不关心的消息抛给父类，避免丢失消息
                    break;
            }
        }
    };
    private List<ImageView> mAdCycleViews = new ArrayList<ImageView>();
    private List<ADInfo> mAdCycleInfos = new ArrayList<ADInfo>();
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                ViewPagerImage viewPagerImage = null;
                if (mbanner != null) {
                    viewPagerImage = mbanner.get(position);
                }
                if (viewPagerImage != null) {
                    if (viewPagerImage.skipRuleCode != null && !"".equals(viewPagerImage.skipRuleCode)
                            && viewPagerImage.skipRuleValue != null && !"".equals(viewPagerImage.skipRuleValue)) {
                        jumpToWhere(viewPagerImage.skipRuleCode, viewPagerImage.skipRuleValue);
                    }

                    if (viewPagerImage.url != null && !"".equals(viewPagerImage.url)) {
                        // 启动活动弹框
                        Intent intent = new Intent(MainActivity.this, IntroduceActivity.class);
                        intent.putExtra(SourceConstant.EVENT_URL, viewPagerImage.url);
                        intent.putExtra("title_shop", viewPagerImage.title);
                        startActivity(intent);
                    }

                }
            }
        }

    };
    private LinearLayout main_lyt_type;
    private LinearLayout main_lyt_order;
    private LinearLayout main_lyt_collect;


    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        // 将Activity添加到堆栈区。
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void initLayout() {
        LogUtil.e(TAG, "=======initLayout=======");
        setContentView(R.layout.activity_shop_market_main);

        // 注册接收网络更改的广播
        mReceiver = new ScrollTopReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SourceConstant.SCROLL_TOP_OK);
        filter.addAction(SourceConstant.START_SHOP_SKU_INFO);
        filter.addAction(SourceConstant.START_SHOP_STORE);
        filter.addAction(SourceConstant.START_SHOP_TUAN_MIAO);
        filter.addAction(SourceConstant.START_SHOP_STORE_TUAN_MIAO);
        filter.addAction(SourceConstant.START_SHOP_STORE_CXZQ);
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initView() {
        LogUtil.e(TAG, "=======initView=======");
        tvHomeTitle = (TextView) findViewById(R.id.title_textview);
        btnBack = (ImageView) findViewById(R.id.title_btn_left);
        btnSearch = (ImageView) findViewById(R.id.title_btn_right);
        ll_alading_shop_home = (LinearLayout) findViewById(R.id.ll_alading_shop_home);
        ll_hot_recommend = (LinearLayout) findViewById(R.id.ll_hot_recommend);

        main_lyt_type = (LinearLayout) findViewById(R.id.main_lyt_type);
        main_lyt_order = (LinearLayout) findViewById(R.id.main_lyt_order);
        main_lyt_collect = (LinearLayout) findViewById(R.id.main_lyt_collect);

        gvDiscoun = (MyScrollGridView) findViewById(R.id.gv_discount);
        gv_home_function = (MyScrollGridView) findViewById(R.id.gv_home_function);
        gv_six_lable = (MyScrollGridView) findViewById(R.id.gv_six_lable);
        gv_two_lable = (GridView) findViewById(R.id.gv_two_lable);
        iv_special_0 = (ImageView) findViewById(R.id.iv_special_0);
        iv_special_1 = (ImageView) findViewById(R.id.iv_special_1);
        iv_special_2 = (ImageView) findViewById(R.id.iv_special_2);
        iv_special_3 = (ImageView) findViewById(R.id.iv_special_3);
        wel_Txt_Switcher = (TextSwitcher) findViewById(R.id.wel_Txt_Switcher);
        sv = (ScrollView) findViewById(R.id.scv_main);
        ll_no_net = (LinearLayout) findViewById(R.id.ll_no_net);

        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        visitKey = fileNameAli.getString(SourceConstant.VisitKey, "");
        skuCode = fileNameAli.getString(SourceConstant.SKU_CODE, "");

    }

    private void initTextSwitcher() {
        wel_Txt_Switcher.setFactory(this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 1, 3000);//每3秒更新
    }

    @Override
    protected void initListener() {
        LogUtil.e(TAG, "=======initListener=======");
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        iv_special_0.setOnClickListener(this);
        iv_special_1.setOnClickListener(this);
        iv_special_2.setOnClickListener(this);
        iv_special_3.setOnClickListener(this);
        ll_hot_recommend.setOnClickListener(this);
        gvDiscoun.setOnItemClickListener(this);
        main_lyt_type.setOnClickListener(this);
        main_lyt_order.setOnClickListener(this);
        main_lyt_collect.setOnClickListener(this);
        sv.setOnTouchListener(new TouchListenerImpl());
//		swipeLayout.setOnHeaderRefreshListener(this);
    }

    @Override
    protected void processLogic() {
        tvHomeTitle.setText("在线药店");
        btnSearch.setVisibility(View.VISIBLE);
        int isNet = NetUtil.getAPNType(this);

        if (isNet == -1) {
            sv.setVisibility(View.GONE);
            ll_no_net.setVisibility(View.VISIBLE);
        } else {
            getHomeList();
        }

    }

    @Override
    protected void onClickEvent(View paramView) {
        if (SourceConstant.IS_NET_STATE) {
            Intent intent = new Intent();
            switch (paramView.getId()) {
                case R.id.title_btn_right:
                    startActivity(new Intent(this, ShopMarketSearchActivity.class));
                    LogUtil.e("", "点击了搜索。。。");
                    break;
                case R.id.title_btn_left:
                    this.finish();
                    break;
                case R.id.ll_hot_recommend:
                    Intent mIntent1 = new Intent(this, SkuListDiscountActivity.class);
                    mIntent1.putExtra("BannerCode", "JPTJ");
                    mIntent1.putExtra("BannerName", "热门推荐");
                    startActivity(mIntent1);
                    break;
                case R.id.iv_special_0:
                    specialLinkUrl = homeList.special_area.get(0).getLinkUrl();
                    if (specialLinkUrl != null && !"".equals(specialLinkUrl)) {
                        intent.setClass(this, IntroduceActivity.class);
                        intent.putExtra(SourceConstant.EVENT_URL, specialLinkUrl);
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_special_1:
                    specialLinkUrl = homeList.special_area.get(1).getLinkUrl();
                    if (specialLinkUrl != null && !"".equals(specialLinkUrl)) {
                        intent.setClass(this, IntroduceActivity.class);
                        intent.putExtra(SourceConstant.EVENT_URL, specialLinkUrl);
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_special_2:
                    specialLinkUrl = homeList.special_area.get(2).getLinkUrl();
                    if (specialLinkUrl != null && !"".equals(specialLinkUrl)) {
                        intent.setClass(this, IntroduceActivity.class);
                        intent.putExtra(SourceConstant.EVENT_URL, specialLinkUrl);
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_special_3:
                    specialLinkUrl = homeList.special_area.get(3).getLinkUrl();
                    if (specialLinkUrl != null && !"".equals(specialLinkUrl)) {
                        intent.setClass(this, IntroduceActivity.class);
                        intent.putExtra(SourceConstant.EVENT_URL, specialLinkUrl);
                        startActivity(intent);
                    }
                    LogUtil.e(TAG, "specialLinkUrl ==" + specialLinkUrl);
                    break;

                case R.id.main_lyt_type:
                    startActivity(new Intent(this, TypeActivity.class));
                    break;
                case R.id.main_lyt_order:
                    if (!"".equals(sid)) {
                        startActivity(new Intent(this,AllOrderActivity.class));
                    } else {
                        startActivityForResult(new Intent(this, LoginActivity.class),SourceConstant.IS_XXX);
                    }
                    break;
                case R.id.main_lyt_collect:
                    if (!"".equals(sid)) {
                        startActivity(new Intent(this,MyFavoritesActivity.class));
                    } else {
                        startActivityForResult(new Intent(this, LoginActivity.class),SourceConstant.IS_XXX);
                    }
                    break;
            }
        } else {
            ToastUtil.MyToast(this, R.string.no_net_error);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SourceConstant.IS_NET_STATE) {
            Intent intent = new Intent();
            switch (parent.getId()) {
                case R.id.gv_discount:
                    intent.setClass(this, SkuInfoActivity.class);
                    intent.putExtra(SourceConstant.SKU_CODE, homeList.JPTJ.get(position).skuCode + "");
                    startActivity(intent);
                    break;
            }
        } else {
            ToastUtil.MyToast(this, R.string.no_net_error);
        }
    }


    /**
     * 请求首页数据
     */
    private void getHomeList() {
        AorunApi.getHomeList(mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        Editor editorHomeList = fileNameAli.edit();
        editorHomeList.putString(SourceConstant.HOME_LIST_JSON, data);
//		editorHomeList.commit();
        if (!"".equals(data) && null != data) {
            callbackHomeList(data);
            LogUtil.e("请求的首页数据===", data);
        }
    }

    @Override
    protected void onResume() {
        LogUtil.e(TAG, "----onResume=============");
        super.onResume();
        sid = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE).getString("sid","");
        if (isSendVisible) {
            // 发送显示的广播
            MainActivity.this.sendBroadcast(new Intent(SourceConstant.SCROLL_TOP_VISIBILITY_VISIBLE));
        }
        if (SourceConstant.YU_MEN_TO_SHOP_SKU_INFO == SourceConstant.FIVE) {
            SourceConstant.YU_MEN_TO_SHOP_SKU_INFO = SourceConstant.ZERO;
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_SKU_INFO));
        }
        if (SourceConstant.YU_MEN_TO_SHOP_STORE == SourceConstant.SIX) {
            SourceConstant.YU_MEN_TO_SHOP_STORE = SourceConstant.ZERO;
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_STORE));
        }

        if (SourceConstant.YU_MEN_TO_SHOP_TUAN_MIAO == SourceConstant.ONE) {
            SourceConstant.YU_MEN_TO_SHOP_TUAN_MIAO = SourceConstant.ZERO;
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_TUAN_MIAO));
        }
        if (SourceConstant.YU_MEN_TO_SHOP_STORE_TUAN_MIAO == SourceConstant.THREE) {
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_STORE_TUAN_MIAO));
        }

        if (SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ == SourceConstant.FOUR) {
            SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ = SourceConstant.FIVE;
            this.sendBroadcast(new Intent(SourceConstant.START_SHOP_STORE_CXZQ));
        }
    }

    @Override
    protected void onPause() {
        LogUtil.e(TAG, "=========onPause()==========");
        if (isSendVisible) {
            // 发送隐藏的广播
            MainActivity.this.sendBroadcast(new Intent(SourceConstant.SCROLL_TOP_VISIBILITY_GONE));
            LogUtil.e(TAG, "发送隐藏的广播============");
        }
        super.onPause();
//		MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        LogUtil.e(TAG, "=========onStop()==========");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtil.e(TAG, "=========onDestroy()==========");
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void callbackHomeList(String obj) {
        if (!tip) {
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(view);
        }
        Gson gson = new Gson();
        homeList = gson.fromJson(obj, Home.class);

//        homeList = JSON.parseObject(obj,Home.class);
        LogUtil.e("解析后首页数据===", "");

        mbanner = new ArrayList<ViewPagerImage>();
        int size1 = homeList.banner.size();
//		if (size1 == 0) {
//			ll_hot_recommend.setVisibility(View.GONE);
//			LogUtil.e(TAG, "homeList.banner是空的");
//			return;
//		}
        mbanner.addAll(homeList.banner);
        imageUrls = new String[mbanner.size()];
        for (int i = 0; i < mbanner.size(); i++) {
            String url = homeList.banner.get(i).picturePath.toString();
            imageUrls[i] = url;
        }
        if (imageUrls.length > 0) {
            SetBannerImage();
        }

//        //textswitcher上下滚动
//        ArrayList<NotifyNews> news = homeList.news;
//        if (news.size() > 0) {
//            switcherNews = new String[news.size()];
//            for (int i = 0; i < news.size(); i++) {
//                switcherNews[i] = news.get(i).getName().toString();
//            }
//            if (tip) {
//                initTextSwitcher();
//                tip = false;
//            }
//            LogUtil.e(TAG, "switcherNews======" + switcherNews.toString());
//        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        LogUtil.e(TAG, "=============屏幕大小：dm.widthPixels:" + dm.widthPixels + ",dm.heightPixels:" + dm.heightPixels);

        try {
            // 配置 特惠SKU
            setDiscounSku(dm.widthPixels);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.scrollTo(0, 0);
            }
        });
        sv.setVisibility(View.VISIBLE);

        ArrayList<FunctionalBlock> bankDtos = homeList.block;
        FunctionListAdapter functionAdapter = new FunctionListAdapter(this, bankDtos, dm.widthPixels);
        gv_home_function.setAdapter(functionAdapter);

        ArrayList<CategoryAndPre> categorys = homeList.category;
        LableListAdapter lableListAdapter = new LableListAdapter(this, categorys, dm.widthPixels);
        gv_six_lable.setAdapter(lableListAdapter);
    }

    private void setDiscounSku(int width) {
        int size = homeList.JPTJ.size();
        if (size == 0) {
            ll_hot_recommend.setVisibility(View.GONE);
            return;
        }
        gvDiscounAdapter = new GvDiscounAdapter(this, homeList.JPTJ, width);
        gvDiscoun.setAdapter(gvDiscounAdapter);
    }

    private int next() {

        int flag = id + 1;
        if (flag > switcherNews.length - 1) {
            flag = flag - switcherNews.length;
        }
        return flag;
    }

    private void updateText() {
        wel_Txt_Switcher.setText(switcherNews[id]);
        wel_Txt_Switcher.setOnClickListener(new OnClickListener() {
            private String linkUrl0;
            private String linkUrl1;
            private String linkUrl2;
            private String name0;
            private String name1;
            private String name2;

            @Override
            public void onClick(View v) {

                if (homeList.news.size() > 0) {
                    linkUrl0 = homeList.news.get(0).getLinkUrl();
                    linkUrl1 = homeList.news.get(1).getLinkUrl();
                    linkUrl2 = homeList.news.get(2).getLinkUrl();

                    name0 = homeList.news.get(0).getName();
                    name1 = homeList.news.get(1).getName();
                    name2 = homeList.news.get(2).getName();

                    Intent intent = new Intent(MainActivity.this, IntroduceActivity.class);
                    if (switcherNews[id].equals(name0)) {
                        intent.putExtra(SourceConstant.EVENT_URL, linkUrl0);
                    } else if (switcherNews[id].equals(name1)) {
                        intent.putExtra(SourceConstant.EVENT_URL, linkUrl1);
                    } else if (switcherNews[id].equals(name2)) {
                        intent.putExtra(SourceConstant.EVENT_URL, linkUrl2);
                    }
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(this);
        tv.setTextSize(16);
        tv.setTextColor(getResources().getColor(R.color.text_color_sku_name));
        tv.setLines(1);
        tv.setText(switcherNews[id]);
        return tv;
    }

    public void ScaleTop() {
        sv.fullScroll(View.FOCUS_UP);
    }

    /**
     * 设置首页的广告banner
     */
    public void SetBannerImage() {
        cycleViewPager = (CycleViewPager) getFragmentManager().findFragmentById(R.id.viewpager_main);
        initialize();
    }

    private void initialize() {
        // 先清空集合
        mAdCycleInfos.clear();
        mAdCycleViews.clear();
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            // if (imageUrls.length>1) {
            mAdCycleInfos.add(info);
            // }
        }
        // 将最后一个ImageView添加进来
        mAdCycleViews.add(ViewFactory.getImageView(this, mAdCycleInfos.get(mAdCycleInfos.size() - 1).getUrl()));
        for (int i = 0; i < mAdCycleInfos.size(); i++) {
            mAdCycleViews.add(ViewFactory.getImageView(this, mAdCycleInfos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        mAdCycleViews.add(ViewFactory.getImageView(this, mAdCycleInfos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(mAdCycleViews, mAdCycleInfos, mAdCycleViewListener);
        // 设置轮播
        if (mAdCycleInfos.size() > 1) {
            cycleViewPager.setWheel(true);
        }

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(3000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private void jumpToWhere(String skipRuleCode, String skipRuleValue) {
        Intent intent = new Intent();
        final Editor editor = fileNameAli.edit();
        switch (skipRuleCode) {

            case "YM-06-01"://商超首页
                break;
            case "YM-06-02"://团购（平台）
                intent.setClass(this, ShopMarketSeckillAndGroupBuyActivity.class);
                editor.putString("BannerCode", "TOGEGHER");
                editor.putString("BannerName", "团购");
                break;
            case "YM-06-03"://秒杀（平台）
                intent.setClass(this, ShopMarketSeckillAndGroupBuyActivity.class);
                editor.putString("BannerCode", "TIMELIMIT");
                editor.putString("BannerName", "秒杀");
                break;
            case "YM-06-04"://商品详情
                intent.setClass(this, SkuInfoActivity.class);
                intent.putExtra(SourceConstant.SKU_CODE, skipRuleValue);
                break;
            case "YM-06-05"://店铺首页
                editor.putString(SourceConstant.STORE_CODE, skipRuleValue);
                intent.setClass(this, ShopDetailsActivity.class);
                break;
            case "YM-06-06"://团购（店铺）
                editor.putString(SourceConstant.STORE_CODE, skipRuleValue);
                intent.setClass(this, ShopDetailsActivity.class);
                SourceConstant.YU_MEN_TO_SHOP_STORE_TUAN_MIAO = SourceConstant.THREE;
                editor.putString("BannerCode", "storeTogether");
                editor.putString("BannerName", "团购");
                break;
            case "YM-06-07"://秒杀（店铺）
                editor.putString(SourceConstant.STORE_CODE, skipRuleValue);
                intent.setClass(this, ShopDetailsActivity.class);
                SourceConstant.YU_MEN_TO_SHOP_STORE_TUAN_MIAO = SourceConstant.THREE;
                editor.putString("BannerCode", "StoreTimeLimit");
                editor.putString("BannerName", "秒杀");
                break;
            case "YM-06-08"://促销（店铺）
                editor.putString(SourceConstant.STORE_CODE, skipRuleValue);
                intent.setClass(this, ShopDetailsActivity.class);
                SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ = SourceConstant.FIVE;
                editor.putString("BannerCode", "STORE_CXZQ");
                editor.putString("BannerName", "促销专区");
                break;
        }
        editor.commit();
        startActivity(intent);
    }

    @Override
    public void onHeaderRefresh(RefreshLayout view) {
//		swipeLayout.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				// 更新数据
//				// 更新完后调用该方法结束刷新
//				if (SourceConstant.IS_NET_STATE) {
//					LogUtil.e(TAG, "下拉刷新            有网络现在去请求===================");
//					storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
//					if ("".equals(storeCode)) {
//						// 请求网络
//						getHomeList();
//					}else {
//						getStoreHomeList();
//					}
//				}
//				swipeLayout.onHeaderRefreshComplete();
//			}
//		}, 3000);
        LogUtil.e(TAG, "下拉刷新刷新完成===================");
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < 480) {
//			rl_home_tilte.setAlpha(y/500f);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            AppManager.getInstance().AppExit(MainActivity.this);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 接收回到顶部的广播
     */
    class ScrollTopReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 回到顶部
            if (action.equals(SourceConstant.SCROLL_TOP_OK)) {
                Message message = new Message();
                message.what = SourceConstant.TWO;
                mHandler.sendMessage(message);
            }
            //跳到商品详情
            if (action.equals(SourceConstant.START_SHOP_SKU_INFO)) {
                Message message = new Message();
                message.what = SourceConstant.FIVE;
                mHandler.sendMessage(message);
            }
            //跳到店铺详情
            if (action.equals(SourceConstant.START_SHOP_STORE)) {
                Message message = new Message();
                message.what = SourceConstant.SIX;
                mHandler.sendMessage(message);
            }
            //跳到平台的团购，秒杀
            if (action.equals(SourceConstant.START_SHOP_TUAN_MIAO)) {
                Message message = new Message();
                message.what = SourceConstant.FOUR;
                mHandler.sendMessage(message);
            }

            //跳到店铺的团购，秒杀
            if (action.equals(SourceConstant.START_SHOP_STORE_TUAN_MIAO)) {
                Message message = new Message();
                message.what = SourceConstant.THREE;
                mHandler.sendMessage(message);
            }

            //跳到店铺的促销
            if (action.equals(SourceConstant.START_SHOP_STORE_CXZQ)) {
                Message message = new Message();
                message.what = SourceConstant.EIGHT;
                mHandler.sendMessage(message);
            }
        }
    }

    /**
     * 描述: 监听ScrollView滑动到顶端和底部 注意事项: 1
     * mScrollView.getChildAt(0).getMeasuredHeight()表示:
     * ScrollView所占的高度.即ScrollView内容的高度.常常有一 部分内容要滑动后才可见,这部分的高度也包含在了
     * mScrollView.getChildAt(0).getMeasuredHeight()中 2 view.getScrollY表示:
     * ScrollView顶端已经滑出去的高度 3 view.getHeight()表示: ScrollView的可见高度
     */
    private class TouchListenerImpl implements OnTouchListener {

        private int lastY = 0;
        private int touchEventId = -9983761;

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                View scroller = (View) msg.obj;
                if (msg.what == touchEventId) {
                    if (lastY == scroller.getScrollY()) {
                        handleStop(scroller);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 5);
                        lastY = scroller.getScrollY();
                    }
                }
            }
        };

        // 这里写真正的事件
        private void handleStop(Object view) {
            ScrollView scroller = (ScrollView) view;

            if (scroller.getScrollY() <= 500) {
                if (!isSendGone) {
                    // 发送隐藏的广播
                    MainActivity.this.sendBroadcast(new Intent(SourceConstant.SCROLL_TOP_VISIBILITY_GONE));
                    LogUtil.e(TAG, "发送隐藏的广播============");
                    isSendVisible = false;
                    isSendGone = true;
                }
            }
            if (scroller.getScrollY() >= 500) {
                if (!isSendVisible) {
                    // 发送显示回到顶部的广播
                    LogUtil.e(TAG, "发送显示的广播============");
                    MainActivity.this.sendBroadcast(new Intent(SourceConstant.SCROLL_TOP_VISIBILITY_VISIBLE));
                    isSendVisible = true;
                    isSendGone = false;
                }
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, view), 0);
                    break;
                default:
                    break;
            }
            return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sid = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE).getString("sid","");
    }
}