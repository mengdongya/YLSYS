package store.chinaotec.com.medicalcare.shopmarket.logic.address.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.listener.ListenerLoadMore;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.adapter.AddressAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.AddressInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 确认订单时   获取地址
 */
public class OrderListAddressActivity extends BaseAoActivity implements
        SwipeMenuCreator, SwipeMenuListView.OnMenuItemClickListener, OnItemClickListener {

    public static int deviceWidth;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private ImageView mBtnAddressNew;
    private SwipeMenuListView mListView;
    private ArrayList<AddressInfo> mData;
    private AddressAdapter mAdapter;
    private SharedPreferences fileNameAli;
    private String sId;
    private User user;
    private LinearLayout ll_address_none;
    private Button btn_address_no_add;
    private boolean flagLoadMore = true;
    private int page_index = 1;
    private String payAddress = "";
    private int inedxDel;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                Intent intent = new Intent();
                intent.putExtra("flag", false);
                intent.putExtra("addressId", payAddress);
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.title_btn_right:
                //跳转到添加新地址页面
                startAddressInfo(true, -1);
                break;
            case R.id.btn_address_no_add:
                // 跳转到添加新地址页面
                startAddressInfo(true, -1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra("flag", true);
        bundle.putParcelable("address", mData.get(position));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_address_list);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mBtnAddressNew = (ImageView) findViewById(R.id.title_btn_right);
        mListView = (SwipeMenuListView) findViewById(R.id.lv_address);
        ll_address_none = (LinearLayout) findViewById(R.id.ll_address_none);
        btn_address_no_add = (Button) findViewById(R.id.btn_address_no_add);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        user = UserKeeper.readUser(this);
        sId = user.sid;

    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mBtnAddressNew.setOnClickListener(this);
        btn_address_no_add.setOnClickListener(this);
        mListView.setOnScrollListener(new ListenerLoadMore() {
            @Override
            protected void nextPage() {
                if (flagLoadMore) {
                    getAddressList(sId);
                }
            }
        });
        if (getIntent().getExtras() != null) {
            payAddress = getIntent().getExtras().getString("addressId");
            mListView.setOnItemClickListener(this);
        }
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText("地址管理");
        mBtnAddressNew.setBackgroundResource(R.drawable.btn_insert_address);
        initData();

        getAddressList(sId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sId = user.sid;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initData() {
        mData = new ArrayList<AddressInfo>();
        mAdapter = new AddressAdapter(this, mData);
        deviceWidth = getResources().getDisplayMetrics().widthPixels;
        mListView.setAdapter(mAdapter);
        // set creator
        mListView.setMenuCreator(this);

        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(this);

    }

    private void getAddressList(String sid) {
        AorunApi.getAddressList(sid, mDataCallback);
    }

    private void deleteAddress(String addressId) {
        AorunApi.getAddressDelete(sId, addressId, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.ADDRESS_LIST:
                logicList(data);
                break;
            case RequestUrl.ADDRESS_DELETE:
                logicDelete();
                break;
        }
    }

    private void logicList(String jsonStr) {
        page_index++;
        List<AddressInfo> list = JSONArray.parseArray(jsonStr, AddressInfo.class);
        flagLoadMore = false;
        if (list.size() != 0) {
            mData.clear();
            mData.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            flagLoadMore = false;
        }

        if (mData.size() < 1) {
            ll_address_none.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            ll_address_none.setVisibility(View.GONE);
        }

    }

    private void logicDelete() {
        mData.remove(inedxDel);
        mAdapter.notifyDataSetChanged();
        if (mData.size() < 1) {
            payAddress = "-1";
            ll_address_none.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            ll_address_none.setVisibility(View.GONE);
        }

    }

    /**
     * @param flag true: 添加地址<br>
     *             false : 修改地址
     */
    private void startAddressInfo(boolean flag, int position) {
        Intent intent = new Intent(this, AddressInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", flag);
        if (flag) {
            intent.putExtras(bundle);
            startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_ADD);
        } else {
            AddressInfo addressInfo = mData.get(position);
            bundle.putParcelable("addressInfo", addressInfo);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_CHANGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestCode 哪里回来的, resultCode怎么回来的
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.REQUEST_CODE_ADDRESS_ADD:
                mData.clear();
                mAdapter.notifyDataSetChanged();

                flagLoadMore = true;
                page_index = 1;
                getAddressList(sId);
                break;
            case Constant.REQUEST_CODE_ADDRESS_CHANGE:
                // mData.set(index, object)
                AddressInfo address = data.getParcelableExtra("addressInfo");
                int position = data.getExtras().getInt("position");
                getAddressList(sId);
                mData.set(position, address);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    // step 1. create a MenuCreator
    @Override
    public void create(SwipeMenu menu) {
        // create "open" item
        SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
        // set item background
        openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
        // set item width
        openItem.setWidth(dp2px(90));
        // set a icon
//		openItem.setIcon(R.drawable.addressedit);
        openItem.setTitle("修改");
        openItem.setTitleSize(18);
        openItem.setTitleColor(Color.WHITE);
        // add to menu
        menu.addMenuItem(openItem);

        // create "delete" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
        // set item background
        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
        // set item width
        deleteItem.setWidth(dp2px(90));
        // set a icon
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
                // edit
                startAddressInfo(false, position);
                break;
            case 1:
                // delete  delete(item);
                inedxDel = position;
                deleteAddress(mData.get(position).addressId);
                break;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        intent.putExtra("flag", false);
        intent.putExtra("addressId", payAddress);
        setResult(RESULT_CANCELED, intent);
        return super.onKeyDown(keyCode, event);
    }
}
