package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.ScienceInformationContentAdapter;
import store.chinaotec.com.medicalcare.adapter.ScienceInformationKindAdapter;
import store.chinaotec.com.medicalcare.adapter.ScienceInformationOneLevelDataAdapter;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * Created by wjc on 2018/2/2 0002.
 * 科普资料
 */
public class ScienceInformationActivity extends BaseActivity{
    @Bind(R.id.test_result_kind)
    RecyclerView testResultKind;
    @Bind(R.id.test_result_content)
    RecyclerView testResultContent;
    @Bind(R.id.rv_one_level_data)
    RecyclerView rvOneLevelData;
    private TextView titleView;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean> medicalBookList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_check);
        ButterKnife.bind(this);
        titleView = (TextView) findViewById(R.id.tv_include_title_view);
        titleView.setText("科普资料");
        initData();
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.medical_book_list).addParams("type","11").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalBookBean medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
                medicalBookList = medicalBookBean.getData().getMedicalTypeList();
                setData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {
        testResultKind.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final ScienceInformationKindAdapter kindAdapter = new ScienceInformationKindAdapter(this, medicalBookList);
        testResultKind.setAdapter(kindAdapter);

        testResultContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ScienceInformationContentAdapter contentAdapter = new ScienceInformationContentAdapter(this, medicalBookList.get(1));
        testResultContent.setAdapter(contentAdapter);

        rvOneLevelData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOneLevelData.setAdapter(new ScienceInformationOneLevelDataAdapter(this,medicalBookList.get(1).getDataList()));

        kindAdapter.setOnItemClickListener(new ScienceInformationKindAdapter.ScienceInformationKind() {
            @Override
            public void click(int position) {
                kindAdapter.setIndex(position);
                kindAdapter.notifyDataSetChanged();
                ScienceInformationContentAdapter contentAdapter = new ScienceInformationContentAdapter(ScienceInformationActivity.this,
                        medicalBookList.get(position));
                testResultContent.setAdapter(contentAdapter);
                rvOneLevelData.setAdapter(new ScienceInformationOneLevelDataAdapter(ScienceInformationActivity.this,
                        medicalBookList.get(position).getDataList()));
            }
        });
    }
}
