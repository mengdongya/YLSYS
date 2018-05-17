package store.chinaotec.com.medicalcare.shopmarket.logic.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.listener.ListenerLoadMore;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.adapter.SkuHistoryAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.SkuBrowseHistoriesDto;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 此类描述的是:商品浏览历史
 *
 * @author: wjc
 * @version:1.0
 */
public class SkuMyHistoryListActivity extends BaseAoActivity implements OnItemClickListener {
    public static boolean ffinsh = true;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private SwipeMenuListView lv_my_history_sku_list;
    /**
     * 没有足迹时
     */
    private LinearLayout ll_no_history;
    private String sId;
    private boolean mFlagLoadMore = true;
    private boolean mFlagHttp = true;
    private boolean flagClear = false;
    private int page_index = 1;
    private String TAG = " SkuMyHistoryList == ";

    private SkuHistoryAdapter mAdapter;
    private ArrayList<SkuBrowseHistoriesDto> arrayList;


    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, SkuInfoActivity.class);
        intent.putExtra(SourceConstant.SKU_CODE,
                String.valueOf(arrayList.get(position).getSkuDto().skuCode));

        startActivity(intent);

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_sku_my_history_list);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        lv_my_history_sku_list = (SwipeMenuListView) findViewById(R.id.lv_my_history_sku_list);
        ll_no_history = (LinearLayout) findViewById(R.id.ll_no_history);
        sId = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        LogUtil.e("sid====", "sid=======" + sId);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        lv_my_history_sku_list.setOnItemClickListener(this);
        lv_my_history_sku_list.setOnScrollListener(new ListenerLoadMore() {
            @Override
            protected void nextPage() {
                if (mFlagHttp)
                    getData();
            }
        });
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText("我的足迹");
        getData();
        initData();
    }

    private void initData() {
        arrayList = new ArrayList<SkuBrowseHistoriesDto>();
        mAdapter = new SkuHistoryAdapter(this, arrayList, mWindowWidth);
        lv_my_history_sku_list.setAdapter(mAdapter);
    }

    /**
     * 请求网络获取数据
     */
    private void getData() {
        if (mFlagHttp) {
            if (mFlagLoadMore) {
                mFlagHttp = false;
                AorunApi.getSkuListMyfoot(sId, page_index, SourceConstant.PAGE_SIZE, mDataCallback);
            }
        }
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        if (flagClear) {
            flagClear = false;
            arrayList.clear();
            mAdapter.notifyDataSetChanged();
        }
        List<SkuBrowseHistoriesDto> formatSkuBrowseHistoriesDto = JSONArray.parseArray(data, SkuBrowseHistoriesDto.class);

        if (formatSkuBrowseHistoriesDto.size() != 0) {
            arrayList.addAll(formatSkuBrowseHistoriesDto);
            mAdapter.notifyDataSetChanged();
            page_index++;
        } else {
            mFlagLoadMore = false;
            mFlagHttp = false;
        }
        if (arrayList.size() < 1) {
            LogUtil.e(TAG, "没有浏览历史。。。。");
            ll_no_history.setVisibility(View.VISIBLE);
            lv_my_history_sku_list.setVisibility(View.GONE);
        } else {
            lv_my_history_sku_list.setVisibility(View.VISIBLE);
            ll_no_history.setVisibility(View.GONE);
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
