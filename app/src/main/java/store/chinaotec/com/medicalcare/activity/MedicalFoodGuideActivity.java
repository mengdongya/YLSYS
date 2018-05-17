package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MedicalFoodContentAdapter;
import store.chinaotec.com.medicalcare.adapter.MedicalFoodGuideNameAdapter;
import store.chinaotec.com.medicalcare.adapter.MedicalFoodOneLevelDataAdapter;
import store.chinaotec.com.medicalcare.adapter.MyTestResulAllClassAdapter;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;
import store.chinaotec.com.medicalcare.javabean.TextResultBaseBean;

/**
 * Created by wjc on 2017/11/22 0022.
 * 膳食指南
 */
public class MedicalFoodGuideActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.rv_food_result_kind)
    RecyclerView rvMedicalKinds;
    @Bind(R.id.rv_food_result_content)
    RecyclerView rvMedicalContent;
    @Bind(R.id.rv_guide_one_level_data)
    RecyclerView rvGuideOneLevelData;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean> medicalTypeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_guide);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.medical_book_list).addParams("type", "7").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalBookBean medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
                medicalTypeList = medicalBookBean.getData().getMedicalTypeList();
                setData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {
        rvMedicalKinds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final MedicalFoodGuideNameAdapter nameAdapter = new MedicalFoodGuideNameAdapter(this, medicalTypeList);
        rvMedicalKinds.setAdapter(nameAdapter);

        rvMedicalContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MedicalFoodContentAdapter contentAdapter = new MedicalFoodContentAdapter(this, medicalTypeList.get(0));
        rvMedicalContent.setAdapter(contentAdapter);

        rvGuideOneLevelData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvGuideOneLevelData.setAdapter(new MedicalFoodOneLevelDataAdapter(this,medicalTypeList.get(0).getDataList()));

//        MyTestResulAllClassAdapter adapter = new MyTestResulAllClassAdapter(this, getPositionZeroData(0));
//        rvMedicalContent.setAdapter(adapter);
//        adapter.setOnTextClickLisener(new MyTestResulAllClassAdapter.TestResultContentLisener() {
//            @Override
//            public void itemClick(int position) {
//                Intent intent = new Intent(MedicalFoodGuideActivity.this, MedicalBookDetailActivity.class);
//                intent.putExtra("medical_book_name", getPositionZeroData(0).get(position).getName());
//                intent.putExtra("medical_book_url", getPositionZeroData(0).get(position).getUrl());
//                startActivity(intent);
//            }
//        });
        nameAdapter.setOnItemClickListener(new MedicalFoodGuideNameAdapter.MedicalFoodGuideType() {
//            public MyTestResulAllClassAdapter contentAdapter;
//            public List<TextResultBaseBean> guideList;

            @Override
            public void click(int position) {
                nameAdapter.setIndex(position);
                nameAdapter.notifyDataSetChanged();
                rvMedicalContent.setLayoutManager(new LinearLayoutManager(MedicalFoodGuideActivity.this, LinearLayoutManager.VERTICAL, false));
                MedicalFoodContentAdapter contentAdapter = new MedicalFoodContentAdapter(MedicalFoodGuideActivity.this, medicalTypeList.get(position));
                rvMedicalContent.setAdapter(contentAdapter);
                rvGuideOneLevelData.setAdapter(new MedicalFoodOneLevelDataAdapter(MedicalFoodGuideActivity.this,medicalTypeList.get(position).getDataList()));

//                if (position == 0) {
//                    guideList = getPositionZeroData(position);
//                    contentAdapter = new MyTestResulAllClassAdapter(MedicalFoodGuideActivity.this, guideList);
//                } else {
//                    guideList = getPositionOtherData(position);
//                    contentAdapter = new MyTestResulAllClassAdapter(MedicalFoodGuideActivity.this, guideList);
//                }
//                rvMedicalContent.setAdapter(contentAdapter);
//                contentAdapter.setOnTextClickLisener(new MyTestResulAllClassAdapter.TestResultContentLisener() {
//                    @Override
//                    public void itemClick(int position) {
//                        Intent intent = new Intent(MedicalFoodGuideActivity.this, MedicalBookDetailActivity.class);
//                        intent.putExtra("medical_book_name", guideList.get(position).getName());
//                        intent.putExtra("medical_book_url", guideList.get(position).getUrl());
//                        startActivity(intent);
//                    }
//                });
            }
        });

    }


    @NonNull
    private List<TextResultBaseBean> getPositionOtherData(int position) {
        List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> medicalBookList = medicalTypeList.get(position)
                .getDataList().get(0).getMedicalBookList();
        List<TextResultBaseBean> guideList = new ArrayList();
        for (int i = 0; i < medicalBookList.size(); i++) {
            String name = medicalBookList.get(i).getName();
            int memberSickDealId = medicalBookList.get(i).getMemberSickDealId();
            String url = medicalBookList.get(i).getUrl();
            guideList.add(new TextResultBaseBean(name, memberSickDealId, url));
        }
        return guideList;
    }

    @NonNull
    private List<TextResultBaseBean> getPositionZeroData(int position) {
        List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean> dataList = medicalTypeList.get(position).getDataList();
        List<TextResultBaseBean> guideListZero = new ArrayList();
        List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> memberSickDealListZero;
        for (int i = 0; i < dataList.size(); i++) {
            memberSickDealListZero = dataList.get(i).getMedicalBookList();
            for (int j = 0; j < memberSickDealListZero.size(); j++) {
                String name = memberSickDealListZero.get(j).getName();
                int memberSickDealId = memberSickDealListZero.get(j).getMemberSickDealId();
                String url = memberSickDealListZero.get(j).getUrl();
                guideListZero.add(new TextResultBaseBean(name, memberSickDealId, url));
            }
        }
        return guideListZero;
    }
}
