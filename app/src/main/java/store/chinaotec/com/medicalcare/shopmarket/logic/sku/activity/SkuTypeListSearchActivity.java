package store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.StoreSkuItemAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreSkuList;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * @ClassName: SkuTypeListSearchActivity 商品分类下二三级分类对应的商品列表
 * @author: wjc
 * @date:2016年8月2日 下午3:15:20
 */
public class SkuTypeListSearchActivity extends BaseAoActivity implements
        OnItemClickListener {

    public static boolean ffinsh = true;
    public static String storeCode;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private GridView mGv;
    /**
     * 分类ID
     */
    private String typeCode;
    private boolean flagClear = false;
    private boolean mFalghttp = true;
    private boolean mFalgLoadMore = true;
    private String titleName;
    private Bundle bundle;
    private int pageIndex = 1;
    private int pageSize = 10;
    private StoreSkuItemAdapter skuItemAdapter;
    private String TAG = "SkuTypeListSearchActivity====";
    private SharedPreferences fileNameAli;
    private ArrayList<StoreSkuList> storeSkuList;
    private String sid;
    private User user;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                SourceConstant.IS_BACK_CURRENT_PAGE = 1;
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, SkuInfoActivity.class);
        intent.putExtra(SourceConstant.SKU_CODE, String.valueOf(storeSkuList.get(position).skuCode));
        startActivity(intent);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_sku_grid);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mGv = (GridView) findViewById(R.id.gv_commodity);
        user = UserKeeper.readUser(this);
        sid = user.sid;
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        Intent mIntent = this.getIntent();
        bundle = mIntent.getExtras();
        titleName = bundle.getString(SourceConstant.TITLE_NAME);
        typeCode = bundle.getString(SourceConstant.TYPE_CODE);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mGv.setOnItemClickListener(this);
        mGv.setOnScrollListener(new ListenerLoadMore() {
            @Override
            protected void nextPage() {
                getData();
            }
        });
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText(titleName);
        initdata();
        getData();
    }

    private void initdata() {
        storeSkuList = new ArrayList<StoreSkuList>();
        skuItemAdapter = new StoreSkuItemAdapter(this, storeSkuList, mWindowWidth);
        mGv.setAdapter(skuItemAdapter);
    }

    private void getData() {
        if (mFalghttp) {
            if (mFalgLoadMore) {
                mFalghttp = false;
                Log.e(titleName, "categoryCode======" + typeCode);

                if (typeCode == null) {
                    typeCode = "";
                }
                if ("".equals(storeCode)) {
                    AorunApi.getStoreTypeCategorySkuList("999", typeCode, pageIndex, pageSize, mDataCallback);
                } else {
                    AorunApi.getStoreTypeCategorySkuList(storeCode, typeCode, pageIndex, pageSize, mDataCallback);
                }
            }
        }

    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        mFalghttp = true;
        switch (reqVo.requestUrl) {
            case RequestUrl.STORE_GET_CATEGORY_LIST:
                if (flagClear) {
                    flagClear = false;
                    storeSkuList.clear();
                    skuItemAdapter.notifyDataSetChanged();
                }
                List<StoreSkuList> storeSkuLists = JSONArray.parseArray(data, StoreSkuList.class);

                LogUtil.e(TAG, "=====解析门店分类下的商品====" + JsonUtil.entityToJson(storeSkuLists));
                if (storeSkuLists.size() != 0) {
                    storeSkuList.addAll(storeSkuLists);
                    skuItemAdapter.notifyDataSetChanged();
                    pageIndex++;
                } else {
                    mFalgLoadMore = false;
//                    ToastUtil.MyToast(this, "没有更多商品了");
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

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SourceConstant.IS_BACK_CURRENT_PAGE = 1;
        return super.onKeyDown(keyCode, event);
    }
}
