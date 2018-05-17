package store.chinaotec.com.medicalcare.shopmarket.logic.home.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter.SearchAutoAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuListSearchActivity;

/**
 * 搜索商品
 */
public class ShopMarketSearchActivity extends BaseAoActivity {

    public static final String SEARCH_HISTORY = "search_history";
    public static boolean ffinsh = true;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private ImageView mIvDeleteText;
    private EditText mEdSearch;
    private Intent intent;
    /**
     * 删除搜索历史
     */
    private Button btn_delete_history;
    /**
     * 搜索历史列表
     */
    private ListView mAutoListView;
    private SearchAutoAdapter mSearchAutoAdapter;
    private SharedPreferences sp;
    private String longhistory;
    private ArrayList<String> mOriginalValues;
    private String[] hisArrays;
    private SharedPreferences.Editor editor1;
    private boolean isreturn = true;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_search:
                String search_key = mEdSearch.getText().toString().trim();
                if (search_key.equals("")) {
                    ToastUtil.MyToast(this, "请输入要搜索的内容");
                    return;
                }
                saveSearchHistory();
                intent = new Intent(this, SkuListSearchActivity.class);
                intent.putExtra("search_key", search_key);
                startActivity(intent);
                LogUtil.e("顶部搜索按钮触发的事件=====", "intent" + intent);
                // finish();
                break;
            case R.id.btn_delete_history:
                if (!"".equals(longhistory)) {
                    cleanHistory();
                }
                break;
        }
    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_search);
    }

    @Override
    protected void initView() {
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mEdSearch = (EditText) findViewById(R.id.ed_search);
        mIvDeleteText = (ImageView) findViewById(R.id.iv_delete_text);
        btn_delete_history = (Button) findViewById(R.id.btn_delete_history);
        mAutoListView = (ListView) findViewById(R.id.lv_search_history);
        sp = getSharedPreferences(SEARCH_HISTORY, 0);

    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        longhistory = sp.getString(SEARCH_HISTORY, "");
        btn_delete_history.setOnClickListener(this);
        mOriginalValues = new ArrayList<String>();
        if (!"".equals(longhistory)) {
            initSearchHistory();
        }

        mIvDeleteText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEdSearch.setText("");
            }

        });

        mEdSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mIvDeleteText.setVisibility(View.GONE);
                } else {
                    mIvDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });

        mAutoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("点击历史条目===", "进了点击条目的方法");
//				TextView tv = (TextView) parent.getItemAtPosition(position);
                String tv = (String) mSearchAutoAdapter.getItem(position);
                LogUtil.e("点击历史条目===", "tv==========" + tv.toString());
//				SearchAutoData data = (SearchAutoData) mSearchAutoAdapter.getItem(position);
//				mEdSearch.setText(data.getContent());
                mEdSearch.setText(tv);
                intent = new Intent(ShopMarketSearchActivity.this, SkuListSearchActivity.class);
                intent.putExtra("search_key", tv);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void processLogic() {

        mEdSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            ShopMarketSearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 跳转到搜索结果界面
                    String search_key = mEdSearch.getText().toString().trim();
                    if (!search_key.equals("")) {
                        saveSearchHistory();
                        Intent intent1 = new Intent(ShopMarketSearchActivity.this, SkuListSearchActivity.class);
                        intent1.putExtra("search_key", search_key);
                        startActivity(intent1);
                        LogUtil.e("底部搜索键盘按钮触发的事件=====", "intent1" + intent1);
                    } else {
                        ToastUtil.MyToast(ShopMarketSearchActivity.this, "请输入要搜索的内容");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /*
     * 保存搜索记录
     */
    private void saveSearchHistory() {
        String text = mEdSearch.getText().toString().trim();
        if (text.length() < 1) {
            return;
        }
        longhistory = sp.getString(SEARCH_HISTORY, "");
        LogUtil.e(longhistory, "longhistory读取:" + longhistory);
        hisArrays = longhistory.split(",");
        for (int i = 0; i < hisArrays.length; i++) {
            LogUtil.e(longhistory, "text======" + text);
            LogUtil.e(longhistory, "hisArrays[i]======" + hisArrays[i]);
            if (text.equals(hisArrays[i])) {
                isreturn = false;
            }
        }
        if (isreturn) {
            clearSp();
            editor1 = sp.edit();
            editor1.putString(SEARCH_HISTORY, longhistory + (text + ","));
            editor1.commit();
            LogUtil.e(longhistory, "保存了:======" + text);
            LogUtil.e(longhistory, "longhistory:======" + sp.getString(SEARCH_HISTORY, ""));
        }
    }

    private void clearSp() {
        editor1 = sp.edit();
        editor1.putString(SEARCH_HISTORY, "");
        editor1.commit();
    }

    /*
     * 清除搜索记录
     */
    public void cleanHistory() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        mOriginalValues.clear();
        // ToastUtil.MyToast(this, "清除成功");
        mSearchAutoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        if (!ffinsh) {
            this.finish();
        }
        longhistory = sp.getString(SEARCH_HISTORY, "");
        if (!"".equals(longhistory)) {
            LogUtil.e(longhistory, "===========longhistory" + longhistory);
            initSearchHistory();
            mSearchAutoAdapter = new SearchAutoAdapter(this, mOriginalValues, this);
            mAutoListView.setAdapter(mSearchAutoAdapter);
            mSearchAutoAdapter.notifyDataSetChanged();

        }
        isreturn = true;
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 读取历史搜索记录
     */
    public void initSearchHistory() {
        longhistory = sp.getString(ShopMarketSearchActivity.SEARCH_HISTORY, "");
        hisArrays = longhistory.split(",");
        if (hisArrays.length < 1) {
            return;
        }
        mOriginalValues.clear();
        for (int i = 0; i < hisArrays.length; i++) {
            LogUtil.e(longhistory, "字符:======" + hisArrays[i]);
            mOriginalValues.add(hisArrays[i]);
        }
    }

}

