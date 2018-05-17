package store.chinaotec.com.medicalcare.shopmarket.logic.home.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.ShopGroupBuyAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.ShopSeckillAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.ShopGroupBuyAndSeckill;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;

/**
 * Created by wjc on 2016/8/12 0020.
 */
public class ShopMarketSeckillAndGroupBuyActivity extends BaseAoActivity implements AdapterView.OnItemClickListener {
    public static boolean ffinsh = true;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private int page_index = 1;
    private String TAG = "ShopMarketSeckillAndGroupBuyActivity";
    private SharedPreferences fileNameAli;
    private boolean flagClear = false;
    private boolean mFalghttp = true;
    private boolean mFalgLoadMore = true;
    private ListView mSeckillAndGroupbuyList;
    private LinearLayout mSeckillAndGroupbuyNo;
    private Intent mIntent;
    private String bannerCode;
    private String bannerName;
    private String storeCode;
    private ArrayList<ShopGroupBuyAndSeckill> mDataList;
    private ShopSeckillAdapter mSeckAdapter;
    private ShopGroupBuyAdapter mGroupAdapter;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                this.finish();
                break;
        }
    }

    @Override
    protected void initLayout() {
//        mIntent = getIntent();
//        bannerCode = mIntent.getStringExtra("BannerCode");
//        bannerName = mIntent.getStringExtra("BannerName");
        setContentView(R.layout.activity_shop_market_seckill_and_groupbuy);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mSeckillAndGroupbuyList = (ListView) findViewById(R.id.lv_seckill_and_groupbuy_list);
        mSeckillAndGroupbuyNo = (LinearLayout) findViewById(R.id.ll_no_seckill_and_groupbuy);

        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        bannerCode = fileNameAli.getString("BannerCode", "");
        bannerName = fileNameAli.getString("BannerName", "");
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mSeckillAndGroupbuyList.setOnItemClickListener(this);
        mSeckillAndGroupbuyList.setOnScrollListener(new ListenerLoadMore() {
            @Override
            protected void nextPage() {
                getData();
            }
        });
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText(bannerName);
        mDataList = new ArrayList<ShopGroupBuyAndSeckill>();
        switch (bannerCode) {
            case SourceConstant.BLOCK_GROUP_BUY:
            case SourceConstant.BLOCK_STORE_TOGETHER:
                mGroupAdapter = new ShopGroupBuyAdapter(this, mDataList);
                mSeckillAndGroupbuyList.setAdapter(mGroupAdapter);
                break;
            case SourceConstant.BLOCK_SECKILL:
            case SourceConstant.BLOCK_STORE_TIMELIMIT:
                mSeckAdapter = new ShopSeckillAdapter(this, mDataList);
                mSeckillAndGroupbuyList.setAdapter(mSeckAdapter);
                break;
        }
        getData();
    }

    private void getData() {
        if (mFalghttp) {
            if (mFalgLoadMore) {
                mFalghttp = false;
                switch (bannerCode) {
                    case SourceConstant.BLOCK_GROUP_BUY:
                    case SourceConstant.BLOCK_STORE_TOGETHER:
                        AorunApi.getShopGroupBuyList(storeCode, page_index, mDataCallback);
                        break;
                    case SourceConstant.BLOCK_SECKILL:
                    case SourceConstant.BLOCK_STORE_TIMELIMIT:
                        AorunApi.getShopTimeLimitList(storeCode, page_index, mDataCallback);
                        break;
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, SkuInfoActivity.class);
        intent.putExtra(SourceConstant.SKU_CODE, mDataList.get(position).getSkuCode());
        startActivity(intent);
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        switch (requestVo.requestUrl) {
            case RequestUrl.SKU_TOGETHER_LIST:
                if (flagClear) {
                    flagClear = false;
                    LogUtil.e(TAG, "清空了集合");
                    mDataList.clear();
                    mGroupAdapter.notifyDataSetChanged();
                }
                List<ShopGroupBuyAndSeckill> list = JSONArray.parseArray(data, ShopGroupBuyAndSeckill.class);
                if (list.size() != 0) {
                    mDataList.addAll(list);
                    mGroupAdapter.notifyDataSetChanged();
                    LogUtil.e(TAG, "当前第几页: " + page_index);
                    page_index++;
                } else {
                    ToastUtil.MyToast(this, "暂无商品");
                    mFalgLoadMore = false;
                }
                break;
            case RequestUrl.SKU_TIME_LIMIT_LIST:
                if (flagClear) {
                    flagClear = false;
                    LogUtil.e(TAG, "清空了集合");
                    mDataList.clear();
                    mSeckAdapter.notifyDataSetChanged();
                }
                List<ShopGroupBuyAndSeckill> seckList = JSONArray.parseArray(data, ShopGroupBuyAndSeckill.class);
                if (seckList.size() != 0) {
                    mDataList.addAll(seckList);
                    mSeckAdapter.notifyDataSetChanged();
                    LogUtil.e(TAG, "当前第几页: " + page_index);
                    page_index++;
                } else {
                    mFalgLoadMore = false;
                    ToastUtil.MyToast(this, "暂无商品");
                }

                break;
        }
    }

    @Override
    protected void onResume() {
        if (!ffinsh) {
            this.finish();
        }
        super.onResume();
    }
}
