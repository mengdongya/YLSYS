package store.chinaotec.com.medicalcare.shopmarket.logic.user.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.MainTabActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.listener.ListenerLoadMore;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.adapter.MyFavoritesAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.MyFavorites;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 我的收藏
 */
public class MyFavoritesActivity extends BaseAoActivity implements
        SwipeMenuCreator, SwipeMenuListView.OnMenuItemClickListener, OnItemClickListener {

    public static boolean ffinsh = true;
    private final int page_size = 10;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private Button btn_favorites_no_add;
    private SwipeMenuListView mLv;
    private MyFavoritesAdapter mAdapter;
    private ArrayList<MyFavorites> mDataList;
    private int position = 0;
    private String sid;
    private String TAG = "MyFavoritesActivity";
    private FavoritesBroadCastReceiver OrderReceiver;
    /**
     * 没有收藏时显示的界面
     */
    private LinearLayout ll_no_favorites;
    private int page_index = 1;
    private boolean flagList = true;
    private ListenerLoadMore mListenerLoadMore = new ListenerLoadMore() {

        @Override
        protected void nextPage() {
            getData(page_index, page_size);
        }

    };

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.btn_favorites_no_add:
                this.finish();
                ((RadioButton) MainTabActivity.main_radio.getChildAt(0)).setChecked(true);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(this, SkuInfoActivity.class);
        intent.putExtra(SourceConstant.SKU_CODE, String.valueOf(mDataList.get(position).skuCode) + "");
        startActivityForResult(intent, Constant.REQUEST_CODE_SKU_INFO);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_my_favorites);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mLv = (SwipeMenuListView) findViewById(R.id.lv_my_favorites);
        ll_no_favorites = (LinearLayout) findViewById(R.id.ll_no_favorites);
        btn_favorites_no_add = (Button) findViewById(R.id.btn_favorites_no_add);

        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        btn_favorites_no_add.setOnClickListener(this);

        mLv.setOnItemClickListener(this);
        mLv.setOnScrollListener(mListenerLoadMore);
        // set creator
        mLv.setMenuCreator(this);
        // step 2. listener item click event
        mLv.setOnMenuItemClickListener(this);
        // 注册收藏相关的广播
        OrderReceiver = new FavoritesBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SourceConstant.UN_FAVORITES);
        this.registerReceiver(OrderReceiver, filter);
    }

    @Override
    protected void processLogic() {
        initData();
        if (!"".equals(sid)) {
            getData(page_index, page_size);
        } else {
            this.finish();
//            startActivity(new Intent(this, LoginActivity.class));TODO
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
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

    private void initData() {
        mTvTitle.setText(R.string.my_favorite_title);
        mDataList = new ArrayList<MyFavorites>();
    }

    private void getData(int page_index, int page_size) {
        if (flagList) {
            AorunApi.getFavoritesList(sid, page_index, page_size, mDataCallback);
        }
    }

    private void delSku() {
        AorunApi.getFavoritesDelete(sid, mDataList.get(position).skuCode, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.FAVORITES_LIST:
                List<MyFavorites> list = JSONArray.parseArray(data, MyFavorites.class);
                LogUtil.e(TAG, "LSIT==============" + list.size());
                if (list.size() != 0) {
                    mDataList.addAll(list);
                    page_index++;
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();

                    }
                } else {
                    flagList = false;
                }
                if (mDataList.size() < 1) {
                    LogUtil.e(TAG, "没有更多内容了。。。。");
                    ll_no_favorites.setVisibility(View.VISIBLE);
                    mLv.setVisibility(View.GONE);
                } else {
                    mLv.setVisibility(View.VISIBLE);
                    ll_no_favorites.setVisibility(View.GONE);
                }
                if (mDataList != null) {
                    mAdapter = new MyFavoritesAdapter(this, mDataList);
                    mLv.setAdapter(mAdapter);
                }
                break;
            case RequestUrl.FAVORITES_DELETE:
                mDataList.remove(position);
                mAdapter.notifyDataSetChanged();
                if (mDataList.size() < 1) {
                    LogUtil.e(TAG, "没有更多内容了。。。。");
                    ll_no_favorites.setVisibility(View.VISIBLE);
                    mLv.setVisibility(View.GONE);
                } else {
                    mLv.setVisibility(View.VISIBLE);
                    ll_no_favorites.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    public void create(SwipeMenu menu) {
        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
        // set item width
        deleteItem.setWidth(dp2px(90));
//		// set a icon
//		deleteItem.setIcon(R.drawable.addressdel);
        deleteItem.setTitle("删除");
        deleteItem.setTitleSize(18);
        deleteItem.setTitleColor(Color.WHITE);
        // add to menu
        menu.addMenuItem(deleteItem);
    }

    @Override
    public void onMenuItemClick(int position, SwipeMenu menu, int index) {
        switch (index) {
            case 0:
                this.position = position;
                delSku();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            page_index = 1;
            flagList = true;
            mDataList.clear();
            mAdapter.notifyDataSetChanged();
            getData(page_index, page_size);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void unFavorites() {
        AorunApi.getFavoritesDelete(sid, SourceConstant.FAVORITES_SKU_CODE, mDataCallback);
    }

    /**
     * 取消 收藏
     */
    class FavoritesBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 取消收藏
            if (action.equals(SourceConstant.UN_FAVORITES)) {
                unFavorites();
            }
        }
    }
}
