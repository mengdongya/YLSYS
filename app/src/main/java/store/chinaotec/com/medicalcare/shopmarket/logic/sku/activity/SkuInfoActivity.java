package store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.view.viewpagerindicator.TabPageIndicator;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.activity.CartActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.IntroduceActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.ShopMarketSearchActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.ShopMarketSeckillAndGroupBuyActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.AllOrderActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.OrderInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity.ShopDetailsActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.fragment.SkuDetailPicFragment;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.fragment.SkuInfoFragment;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.MyFavoritesActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.SkuMyHistoryListActivity;

/**
 * Created by wjc on 2016/9/5 0005.
 * 商品详情
 */
public class SkuInfoActivity extends BaseAoActivity {
    private ViewPager mViewPager;
    private TabPageIndicator mTabPageIndicator;
    private List<Fragment> fragments;
    private TabAdapter adapter;
    private String[] titles = new String[]{"商品", "详情"};
    private ImageView titlebar_img_back;
    private ImageView titlebar_img_menu;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_sku_info);
    }

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.titlebar_img_back:
                finish();
                break;
            case R.id.titlebar_img_menu:
                clickTo();
                break;
        }

    }

    @Override
    protected void initView() {
        titlebar_img_back = (ImageView) findViewById(R.id.titlebar_img_back);
        titlebar_img_menu = (ImageView) findViewById(R.id.titlebar_img_menu);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
    }

    @Override
    protected void initListener() {
        titlebar_img_back.setOnClickListener(this);
        titlebar_img_menu.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        fragments = new ArrayList<>();
        fragments.add(new SkuInfoFragment());
        fragments.add(new SkuDetailPicFragment());
        adapter = new TabAdapter(getSupportFragmentManager());
        setUi();
    }

    private void setUi() {
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();
        mTabPageIndicator.setViewPager(mViewPager);
        mTabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {

    }

    private void clickTo() {
        this.finish();
        SkuListDiscountActivity.ffinsh = false;
        SkuTypeListSearchActivity.ffinsh = false;
        SkuListSearchActivity.ffinsh = false;
        SkuListTypeActivity.ffinsh = false;
        ShopMarketSearchActivity.ffinsh = false;
        SkuMyHistoryListActivity.ffinsh = false;
        MyFavoritesActivity.ffinsh = false;
        IntroduceActivity.ffinsh = false;
        OrderInfoActivity.ffinsh = false;
        AllOrderActivity.ffinsh = false;
        ShopMarketSeckillAndGroupBuyActivity.ffinsh = false;
        ShopDetailsActivity.ffinsh = false;
        // 跳转到购物车
        if (SourceConstant.YU_MEN_TO_SHOP_SKU_INFO == SourceConstant.SIX) {
            startActivity(new Intent(this, CartActivity.class));
        } else {
            ((RadioButton) MainTabActivity.main_radio.getChildAt(3)).setChecked(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class TabAdapter extends FragmentStatePagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
        }
    }

}
