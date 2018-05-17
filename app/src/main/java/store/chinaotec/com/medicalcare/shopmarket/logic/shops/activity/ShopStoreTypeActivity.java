package store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity;

import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.adapter.TypeNameAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter.ProductMenuStoreAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.Category;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.ChildTypeEntity;

/**
 * Created by wjc on 2016/12/18 0007.
 * 门店分类
 */
public class ShopStoreTypeActivity extends BaseAoActivity implements AdapterView.OnItemClickListener {
    private List<Category> typeList;
    private TextView mTvTitle;
    private SharedPreferences fileNameAli;
    private String storeCode;
    private ListView lv_name;
    private GridView lv_body;
    private ArrayList<ChildTypeEntity> options1Items;
    private TypeNameAdapter typeNameAdapter;
    private ProductMenuStoreAdapter typeBodyAdapter;
    private LinearLayout ll_shop_market_type;
    private TextView tv_shop_no_type;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_shop_type);
    }

    @Override
    protected void onClickEvent(View paramView) {

    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        lv_name = (ListView) findViewById(R.id.lv_shop_store_type_name);
        lv_body = (GridView) findViewById(R.id.lv_shop_store_type_body);
        ll_shop_market_type = (LinearLayout) findViewById(R.id.ll_shop_market_type);
        tv_shop_no_type = (TextView) findViewById(R.id.tv_shop_no_type);
        options1Items = new ArrayList<>();
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
    }

    @Override
    protected void initListener() {
        lv_name.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText(R.string.main_tab_type);
        getStoreTypeList();
    }

    private void getStoreTypeList() {
        AorunApi.getStoreTypeList(storeCode, mDataCallback);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.HOME_STORE_TYPE_LIST:
                callbackHomeList(data);
                break;
        }
    }

    private void callbackHomeList(String obj) {
        typeList = JSONArray.parseArray(obj, Category.class);

        if (typeList.size() > 0) {
            ll_shop_market_type.setVisibility(View.VISIBLE);
            tv_shop_no_type.setVisibility(View.GONE);
            typeNameAdapter = new TypeNameAdapter(this, typeList);
            lv_name.setAdapter(typeNameAdapter);

            options1Items.clear();
            getTypeList(0);
            typeBodyAdapter = new ProductMenuStoreAdapter(this, options1Items);
            lv_body.setAdapter(typeBodyAdapter);
            typeBodyAdapter.notifyDataSetChanged();
        } else {
            ll_shop_market_type.setVisibility(View.GONE);
            tv_shop_no_type.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        typeNameAdapter.setIndex(position);
        typeNameAdapter.notifyDataSetChanged();
        options1Items.clear();
        getTypeList(position);
        typeBodyAdapter.notifyDataSetChanged();
    }

    private void getTypeList(int position) {

        List<Category.ChildCategoryEntity> twoType = typeList.get(position).getChildCategory();

        if (twoType.size() > 0) {

            for (int i = 0; i < twoType.size(); i++) {

                List<Category.ChildCategoryEntity> threeType = twoType.get(i).getChildCategory();

                if (threeType.size() > 0) {

                    for (int j = 0; j < threeType.size(); j++) {

                        options1Items.add(new ChildTypeEntity(threeType.get(j).getName(), threeType.get(j).getLogoUrl(),
                                threeType.get(j).getCode()));

                    }
                } else {
                    options1Items.add(new ChildTypeEntity(twoType.get(i).getName(), twoType.get(i).getLogoUrl(),
                            twoType.get(i).getCode()));
                }
            }
        } else {
            options1Items.add(new ChildTypeEntity(typeList.get(position).getName(),
                    typeList.get(position).getLogoUrl(), typeList.get(position).getCode()));
        }
        LogUtil.e("把分类转换成一个统一的集合===", "data==" + JsonUtil.entityToJson(options1Items));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!"".equals(storeCode)) {
                SharedPreferences.Editor editor = fileNameAli.edit();
                editor.putString(SourceConstant.STORE_CODE, "");
                editor.commit();
                this.finish();
            } else {
                AppManager.getInstance().AppExit(ShopStoreTypeActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
