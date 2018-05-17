package store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
 * 此类描述的是: 首页2+8个专题下对应的三级分类的商品
 *
 * @author: wjc
 * @version:2.0
 * @date:2016年8月26日 下午3:35:10
 */
public class SkuListTypeActivity extends BaseAoActivity implements OnItemClickListener {

    public static boolean ffinsh = true;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private ArrayList<Sku> mDataList;
    private SharedPreferences fileNameAli;
    private String storeCode;
    private int page_index = 1;
    private int pageSize = 10;
    private boolean flagClear = false;
    private boolean mFalghttp = true;
    private boolean mFalgLoadMore = true;
    /**
     * 分类条目       1:洗涤用品   2： 家庭日用品   3:小食品   4：粮油调味品  5：酒水饮料   6：速食品
     */
    private String type;
    /**
     * labelCode分类编号
     */
    private int labelCode;
    private GridView mGv;
    private CommodityAdapter mAdapter;
    private String TAG = "SkuListTypeActivity=====";
    private Intent mIntent;
    private String lableName;

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
        setContentView(R.layout.activity_shop_market_sku_grid);
        mIntent = getIntent();
        type = mIntent.getStringExtra("type");
        lableName = mIntent.getStringExtra("lableName");
        LogUtil.e(TAG, "接收到了 用户点击的是=========" + type);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mGv = (GridView) findViewById(R.id.gv_commodity);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mGv.setOnItemClickListener(this);
        mGv.setOnScrollListener(new ListenerLoadMore() {
            @Override
            protected void nextPage() {
                if (mFalghttp)
                    getData();
            }
        });
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText(lableName);
        initdata();
        getData();
    }

    private void getData() {
        if (mFalghttp) {
            if (mFalgLoadMore) {
                mFalghttp = false;
                LogUtil.e(TAG, "请求了服务器");
                AorunApi.getStoreTypeCategorySkuList(storeCode, type, page_index, pageSize, mDataCallback);
            }
        }
    }

    private void initdata() {
        mDataList = new ArrayList<Sku>();
        mAdapter = new CommodityAdapter(this, mDataList, mWindowWidth);
        mGv.setAdapter(mAdapter);
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
            mDataList.addAll(list);
            mAdapter.notifyDataSetChanged();
            LogUtil.e(TAG, "当前第几页: " + page_index);
            page_index++;
        } else {
            mFalgLoadMore = false;
            mFalghttp = false;
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
