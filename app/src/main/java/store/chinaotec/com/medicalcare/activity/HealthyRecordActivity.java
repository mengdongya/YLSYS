package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyHealthyTimeLineAdapterV2;
import store.chinaotec.com.medicalcare.javabean.HealthyRecordBean;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;

/**
 * Created by HYY on 2018/1/5.
 * 健康记录列表
 */

public class HealthyRecordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.add_course_record)
    LinearLayout addCourseRecord;
    @Bind(R.id.time_line)
    LRecyclerView timeLine;
    private String patientId;
    private List<HealthyRecordBean.DataBean.PatientDtoBean.DiseaseRecordDtosBean> mDiseaseRecordDtosBeans = new HealthyRecordBean().getData().getPatientDto().getDiseaseRecordDtos();
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private Integer pageIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_record);
        ButterKnife.bind(this);
        addCourseRecord.setOnClickListener(this);
        patientId = getIntent().getStringExtra("patientId");
        timeLine.setLayoutManager(new LinearLayoutManager(this));

        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyHealthyTimeLineAdapterV2(this, mDiseaseRecordDtosBeans));
        timeLine.setAdapter(lRecyclerViewAdapter);
        timeLine.setLoadMoreEnabled(true);
        timeLine.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);//可以自定义下拉刷新的样式
        timeLine.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//可以自定义上拉加载的样式
//        timeLine.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);//可以自定义上拉加载的样式
//        timeLine.setLoadingMoreProgressStyle();
//        timeLine.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);

        timeLine.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                selectHealthyList();
            }
        });
        timeLine.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                selectHealthyList();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        selectHealthyList();
    }


    private void selectHealthyList() {
        NetWorkUtill.getGetHalthyRecord(patientId, pageIndex, new NetWorkUtill.GetHalthyRecordListener() {
            @Override
            public void getHalthyRecord(HealthyRecordBean fromJson) {
                if (fromJson.getResponseCode().equals("0")) {
                    HealthyRecordBean.DataBean.PatientDtoBean patientDto = fromJson.getData().getPatientDto();
                    if (patientDto.getDiseaseRecordDtos().size() > 0) {
                        if (pageIndex == 1) {
                            mDiseaseRecordDtosBeans.clear();
                        }
                        mDiseaseRecordDtosBeans.addAll(patientDto.getDiseaseRecordDtos());
                    }
                }
                lRecyclerViewAdapter.notifyDataSetChanged();
                timeLine.refreshComplete(pageIndex);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_course_record:
                Intent intent = new Intent(this, HealthAddRecordActivity.class);
                intent.putExtra("patientId", patientId);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
