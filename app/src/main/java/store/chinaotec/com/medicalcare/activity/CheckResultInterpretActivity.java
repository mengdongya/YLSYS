package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.CheckResultContentAdapter;
import store.chinaotec.com.medicalcare.adapter.CheckResultKindAdapter;
import store.chinaotec.com.medicalcare.adapter.CheckResultOneLevelDataAdapter;
import store.chinaotec.com.medicalcare.dialog.LoadingView;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by wjc on 2017/12/1 0001.
 * 检查结果解读
 */
public class CheckResultInterpretActivity extends BaseActivity {
    @Bind(R.id.test_result_kind)
    RecyclerView testResultKind;
    @Bind(R.id.test_result_content)
    RecyclerView testResultContent;
    @Bind(R.id.rv_one_level_data)
    RecyclerView rvOneLevelData;
    private List<TextResultBean.DataBean.MedicalTypeListBean> medicalTypeList;
    private CheckResultKindAdapter checkResultKindAdapter;
    private TextView tv_include_title_view;
    private LoadingView loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_check);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("检查结果解读");
        ButterKnife.bind(this);
        loadingDialog = new LoadingView(this, R.style.ProgressDialog);
        initData();
    }


    private void initData() {
        loadingDialog.show();
        OkGo.post(MyUrl.SUDEN_DISE_RESULT).params("type", 3).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                TextResultBean textResultBean = JSONObject.parseObject(s, TextResultBean.class);
                if ("0".equals(textResultBean.getResponseCode())) {
                    loadingDialog.dismiss();
                    medicalTypeList = textResultBean.getData().getMedicalTypeList();
                    setData();
                }
            }
        });
    }

    private void setData() {
        testResultKind.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        checkResultKindAdapter = new CheckResultKindAdapter(this, medicalTypeList);
        testResultKind.setAdapter(checkResultKindAdapter);

        testResultContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        CheckResultContentAdapter contentAdapter = new CheckResultContentAdapter(this, medicalTypeList.get(0));
        testResultContent.setAdapter(contentAdapter);
        rvOneLevelData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOneLevelData.setAdapter(new CheckResultOneLevelDataAdapter(this, medicalTypeList.get(0).getDataList()));

        checkResultKindAdapter.setOnItemClickListener(new CheckResultKindAdapter.CheckResultKind() {
            @Override
            public void click(int position) {
                checkResultKindAdapter.setIndex(position);
                checkResultKindAdapter.notifyDataSetChanged();
                CheckResultContentAdapter contentAdapter = new CheckResultContentAdapter(CheckResultInterpretActivity.this, medicalTypeList.get(position));
                testResultContent.setAdapter(contentAdapter);
                rvOneLevelData.setAdapter(new CheckResultOneLevelDataAdapter(CheckResultInterpretActivity.this, medicalTypeList.get(position).getDataList()));

            }
        });
    }
}
