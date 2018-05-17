package store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.SearchSkuAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

/**
 * 商品列表 - 搜索
 */
public class SkuListSearchActivity extends BaseAoActivity implements OnItemClickListener {

    public static boolean ffinsh = true;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private Intent mIntent;
    /**
     * 显示搜索结果的listview
     */
    private ListView mLv;
    /**
     * 显示没有搜索结果的布局
     */
    private LinearLayout ll_no_sku;
    private SearchSkuAdapter mAdapter;
    private ArrayList<Sku> mDataList;
    private boolean flagClear = false;

    private int page_index = 1;
    private int sortParam = Constant.SKU_LIST_MOST_NEW;
    private int sortType = Constant.SKU_LIST_PRICE_DESC;
    /**
     * 搜索关键字
     */
    private String search_key;
    /**
     * 商品分类名称
     */
    private String TypeCodeName;
    private boolean mFlagLoadMore = true;
    private boolean mFlagHttp = true;
    private String TAG = "SkuListSearchActivity";
    private SharedPreferences fileNameAli;
    private String storeCode;
    private String[] storeImageUrls;
    private TextView tv_store_sku_num;
    private String sid;
    private String visitKey;
    private String storeVisitKey;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            default:
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
        setContentView(R.layout.activity_shop_market_search_sku_list);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mLv = (ListView) findViewById(R.id.lv_commodity);
        ll_no_sku = (LinearLayout) findViewById(R.id.ll_no_sku);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        sid = fileNameAli.getString(SourceConstant.USER_SID, "");
        visitKey = fileNameAli.getString(SourceConstant.VisitKey, "");
        storeVisitKey = fileNameAli.getString(SourceConstant.StoreVisitKey, "");
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mLv.setOnItemClickListener(this);
        mLv.setOnScrollListener(new ListenerLoadMore() {
            @Override
            protected void nextPage() {
                if (mFlagHttp)
                    getData();
            }
        });
    }

    @Override
    protected void processLogic() {
        mIntent = getIntent();
        mIntent.getStringExtra("");
        mTvTitle.setText(R.string.sku_list_title_search);
        initdata();
        getData();

    }

    private void initdata() {
        Intent mIntent = getIntent();
        search_key = mIntent.getStringExtra(SourceConstant.SEARCH_KEY);
        mDataList = new ArrayList<Sku>();
        mAdapter = new SearchSkuAdapter(this, mDataList, mWindowWidth);
        mLv.setAdapter(mAdapter);
    }

    private void getData() {
        if (mFlagHttp) {
            if (mFlagLoadMore) {
                mFlagHttp = false;
                AorunApi.getSkuListSearch(storeCode, search_key, page_index, mDataCallback);
            }
        }
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        if (flagClear) {
            flagClear = false;
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
        }

        List<Sku> list = JSONArray.parseArray(data, Sku.class);

        if (list.size() != 0) {
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
            page_index++;
        } else {
            mFlagLoadMore = false;
            mFlagHttp = false;
        }
        if (mDataList.size() < 1) {
            LogUtil.e(TAG, "没搜出来东西。。。。");
            ll_no_sku.setVisibility(View.VISIBLE);
        } else {
            ll_no_sku.setVisibility(View.GONE);
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
}
