package store.chinaotec.com.medicalcare.shopmarket.logic.type.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.app.AppManager;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.fragment.ContentFragment;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.fragment.TitleFragment;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.Category;

/**
 * 分类
 */
public class TypeActivity extends BaseAoActivity implements ProductTypeOnItemClickListener {
    public static List<Category> TypeList;
    private TextView mTvTitle;
    /**
     * 右侧内容
     */
    private ContentFragment cf;
    /**
     * 左侧标题
     */
    private TitleFragment tf;
    private List<String> listName;
    private String[] arries;
    private String[] arrie = null;
    private String TAG = "TypeActivity==";
    private SharedPreferences fileNameAli;
    private String typeData;
    private String storeCode;
    private int position;
    private ImageView mBtnBack;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()){
            case R.id.title_btn_left:
                finish();
                break;
        }
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        // 将Activity添加到堆栈区。我是这样理解的。
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_type_product_classify);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        // 找到两个Fragment
        FragmentManager manager = getSupportFragmentManager();
        tf = (TitleFragment) manager.findFragmentById(R.id.fg_title);
        cf = (ContentFragment) manager.findFragmentById(R.id.fg_content);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
    }

    @Override
    protected void initListener() {
        // 把接口给TitleFragment
        tf.setListener(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        LogUtil.e(TAG, "=====processLogic()======");
        mTvTitle.setText(R.string.main_tab_type);

        LogUtil.e("TypeActivity=====", " 分类下的storeCode" + storeCode);

        if (SourceConstant.IS_NET_STATE) {
            if ("".equals(storeCode) || null == storeCode) {
                getHomeTypeList();
            } else {
                getStoreTypeList();
            }
        } else {
            //设置缓存数据
            if ("".equals(storeCode)) {
                typeData = fileNameAli.getString(SourceConstant.TYPE_LIST_JSON, "");
                if (!"".equals(typeData)) {
                    LogUtil.e(TAG, "data有数据 没有请求网络===================");
                    callbackHomeList(typeData);
                }
            }
        }
    }

    /**
     * 门店分类的请求
     */
    private void getStoreTypeList() {
        AorunApi.getStoreTypeList(storeCode, mDataCallback);
    }

    /**
     * 商城分类的数据请求
     */
    private void getHomeTypeList() {
        AorunApi.getStoreTypeList("999", mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.HOME_TYPE_LIST:
                SharedPreferences.Editor editorTypelist = fileNameAli.edit();
                editorTypelist.putString(SourceConstant.TYPE_LIST_JSON, data);
                editorTypelist.commit();
                callbackHomeList(data);
                break;
            case RequestUrl.HOME_STORE_TYPE_LIST:
                callbackHomeList(data);
                break;
        }
    }

    /**
     * 解析商城的分类
     */
    private void callbackHomeList(String obj) {
        TypeList = JSONArray.parseArray(obj, Category.class);

        listName = new ArrayList<String>();
        listName.clear();
        if (TypeList != null && !"".equals(TypeList)) {
            for (int i = 0; i < TypeList.size(); i++) {
                String name = TypeList.get(i).getName();
                listName.add(name);
            }
            setRecommendationType();
        }
    }

    private void setRecommendationType() {
        arries = new String[listName.size()];
        for (int i = 0; i < arries.length; i++) {
            arries[i] = listName.get(i);
        }
        if (arries.length > 0) {
            tf.setData(arries);
            LogUtil.e(TAG, "设置了左边的数据");
            cf.updateUI(0);
        }
    }

    @Override
    public void getPostion(int position) {
        // 当点击左侧一级分类菜单时更新右侧显示内容
        cf.updateUI(position);
    }

    @Override
    protected void onResume() {
        LogUtil.e("TypeActivity", "======onResume()===");
        super.onResume();
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        LogUtil.e("TypeActivity=====", " onResume()分类下的storeCode===" + storeCode);

        if (SourceConstant.IS_NET_STATE) {
            if ("".equals(storeCode)) {
                typeData = fileNameAli.getString(SourceConstant.TYPE_LIST_JSON, "");
                if (SourceConstant.IS_BACK_CURRENT_PAGE == 1) {
                    SourceConstant.IS_BACK_CURRENT_PAGE = 0;
                } else {
                    getHomeTypeList();
                }
            }
        } else {
            //设置缓存数据
            if ("".equals(storeCode)) {
                typeData = fileNameAli.getString(SourceConstant.TYPE_LIST_JSON, "");
                if (!"".equals(typeData)) {
                    LogUtil.e(TAG, "data有数据 没有请求网络===================");
                    callbackHomeList(typeData);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!"".equals(storeCode)) {
                SharedPreferences.Editor editor = fileNameAli.edit();
                editor.putString(SourceConstant.STORE_CODE, "");
                editor.commit();
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

