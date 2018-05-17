package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MySlowDiseKindAdapter;
import store.chinaotec.com.medicalcare.javabean.SlowDiseKind;

/**
 * Created by wjc on 2018/2/1 0001.
 * 慢性病添加
 */
public class ChronicDiseaseAddActivity extends BaseActivity {
    @Bind(R.id.slow_disease_kind)
    RecyclerView slowDiseaseKind;
    @Bind(R.id.slow_disease_detail)
    RecyclerView slowDiseaseDetail;
    private List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.slow_dise_kind).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                SlowDiseKind slowDiseKind = JSONObject.parseObject(response, SlowDiseKind.class);
                if ("0".equals(slowDiseKind.getResponseCode())){
                    medicalTypes = slowDiseKind.getData().getMedicalTypes();
                    setKindData();
                }
            }
            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setKindData() {
        MySlowDiseKindAdapter mySlowDiseKindAdapter = new MySlowDiseKindAdapter(this, medicalTypes);
        slowDiseaseKind.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        slowDiseaseKind.setAdapter(mySlowDiseKindAdapter);
        mySlowDiseKindAdapter.notifyDataSetChanged();
        mySlowDiseKindAdapter.setOnClickListener(new MySlowDiseKindAdapter.SlowDiseLisener() {
            @Override
            public void itemClick(int position) {

            }
        });
    }
}
