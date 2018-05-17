package store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.listener.ListenerLoadMore;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.CommodityAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

/**
 * 分类条目       ------------查看更多--热门推荐
 * 0：猜你喜欢(暂时使用精品推荐的接口)
 * 1:每日专场 2： 精品推荐 3: 特惠街 4: 促销专区
 */
public class SkuListDiscountActivity extends BaseAoActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "SkuListDiscountActivity";
    public static boolean ffinsh = true;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private GridView mGv;
    private CommodityAdapter mAdapter;
    private ArrayList<Sku> mDataList;
    private int page_index = 1;
    private int pageSize = 10;
    private boolean flagClear = false;
    private boolean mFalghttp = true;
    private boolean mFalgLoadMore = true;
    /**
     * labelCode分类编号
     */
    private String labelCode;
    private Intent mIntent;
    private String bannerCode;
    private String bannerName;
    private SharedPreferences fileNameAli;
    private String storeCode;
    private String homeBannerCode;
    private String homeBannerName;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ = SourceConstant.ZERO;
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(this, SkuInfoActivity.class);
        intent.putExtra(SourceConstant.SKU_CODE, String.valueOf(mDataList.get(position).skuCode));
        startActivity(intent);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_sku_grid);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");

        if (SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ == SourceConstant.FIVE) {
            homeBannerCode = fileNameAli.getString("BannerCode", "");
            homeBannerName = fileNameAli.getString("BannerName", "");
        } else {
            mIntent = getIntent();
            bannerCode = mIntent.getStringExtra("BannerCode");
            bannerName = mIntent.getStringExtra("BannerName");
        }
        LogUtil.e(TAG, "接收到了 用户点击的是=========" + bannerCode);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mGv = (GridView) findViewById(R.id.gv_commodity);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mGv.setOnItemClickListener(this);
        mGv.setOnScrollListener(new ListenerLoadMore() {

            @Override
            protected void nextPage() {
                LogUtil.e(TAG, "下一页 =========请求了服务器");
//                getData();
                if (SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ == SourceConstant.FIVE) {
                    getData(homeBannerCode);
                } else {
                    getData(bannerCode);
                }
            }
        });
    }

    @Override
    protected void processLogic() {
        initdata();
        if (SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ == SourceConstant.FIVE) {
            mTvTitle.setText(homeBannerName);
            getData(homeBannerCode);
        } else {
            mTvTitle.setText(bannerName);
            getData(bannerCode);
        }

    }

    private void initdata() {
        mDataList = new ArrayList<Sku>();
        mAdapter = new CommodityAdapter(this, mDataList, mWindowWidth);
        mGv.setAdapter(mAdapter);
    }

    private void getData(String bannerCode) {

        if (mFalghttp) {
            if (mFalgLoadMore) {
                mFalghttp = false;
                LogUtil.e(TAG, "请求了服务器");
                AorunApi.getSkuListLabel(bannerCode, page_index + "", storeCode, mDataCallback);
            }
        }
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        mFalghttp = true;
        if (flagClear) {
            flagClear = false;
            LogUtil.e(TAG, "清空了集合");
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
        }
        List<Sku> list = JSONArray.parseArray(data, Sku.class);
        if (list.size() != 0) {
            // mDataList.clear();
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
            LogUtil.e(TAG, "当前第几页: " + page_index);
            page_index++;
        } else {
            mFalgLoadMore = false;
        }
    }

    @Override
    protected void onResume() {
        if (!ffinsh) {
            this.finish();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ = SourceConstant.ZERO;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SourceConstant.YU_MEN_TO_SHOP_STORE_CXZQ = SourceConstant.ZERO;
        return super.onKeyDown(keyCode, event);
    }
}

