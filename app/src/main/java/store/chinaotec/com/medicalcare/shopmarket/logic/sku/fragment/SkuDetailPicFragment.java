package store.chinaotec.com.medicalcare.shopmarket.logic.sku.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestsImpl.ResultFormatImpl;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.SkuParameterAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.GraphicDetail;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Results;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * Created by wjc on 2016/9/5 0005.
 */
public class SkuDetailPicFragment extends Fragment {
    private ListView lv_sku_parameter;
    private Activity mActivity;
    private User user;
    private String sId;
    private Bundle mBundle;
    private String skuCode;
    private Map<String, String> mParam;
    private ResultFormatImpl mResFormat = null;
    private List<GraphicDetail> list;
    private String homeSkuCode;
    private DisplayMetrics dm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mActivity = getActivity();
        View view = View.inflate(mActivity, R.layout.activity_shop_market_sku_parameter, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        lv_sku_parameter = (ListView) view.findViewById(R.id.lv_sku_parameter);
    }


    protected void initData() {
        user = UserKeeper.readUser(mActivity);
        sId = user.sid;
        mParam = new HashMap<>();
        mResFormat = new ResultFormatImpl();
        dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mBundle = mActivity.getIntent().getExtras();
        skuCode = mBundle.getString(SourceConstant.SKU_CODE);
        getData(sId, skuCode);
    }

    private void getData(String sid, String skuCode) {
        mParam.clear();
        String url = RequestUrl.APP_HOME + RequestUrl.SKU_INFO_GRAPHIC_DETAILS;
        mParam.put("sid", sid);
        mParam.put("skuCode", skuCode);
        OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                Results result = JSON.parseObject(response, Results.class);
                if (result.data != null && !"".equals(result.data)) {
                    setData(result.data);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    private void setData(String data) {
        list = JSONArray.parseArray(data, GraphicDetail.class);
        if (list.size() > 0) {
            SkuParameterAdapter skuParameterAdapter = new SkuParameterAdapter(mActivity, list, dm.widthPixels);
            lv_sku_parameter.setAdapter(skuParameterAdapter);
        }
    }
}
