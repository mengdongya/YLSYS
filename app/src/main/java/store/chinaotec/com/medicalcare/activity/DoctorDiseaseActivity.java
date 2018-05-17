package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.SpecialHallBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;

/**
 * Created by wjc on 2017/11/14 0014.
 * 疾病群
 */
public class DoctorDiseaseActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_medical_title_name)
    TextView tvMedicalTitleName;
    @Bind(R.id.tv_doctor_setup)
    TextView tvDoctorSetup;
    @Bind(R.id.rv_forum_list)
    LRecyclerView recyclerView;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int pageIndex = 1;
    private SpecialHallBean specialHallBean;
    private List<SpecialHallBean.DataBean> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_forum);
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
        tvMedicalTitleName.setText("疾病群");
        tvDoctorSetup.setText("");
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyRecyclerAdapter());
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                initData();
            }
        });
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.FIND_TOPIC_CLASS).addParams("baseClassId", "5").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                specialHallBean = JSON.parseObject(response, SpecialHallBean.class);
                if ("0".equals(specialHallBean.getResponseCode())) {
                    List<SpecialHallBean.DataBean> beanList = specialHallBean.getData();
                    if (pageIndex == 1) {
                        data.clear();
                    }
                    data.addAll(beanList);
                    lRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.MyToast(DoctorDiseaseActivity.this, specialHallBean.getMsg());
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(DoctorDiseaseActivity.this).inflate(R.layout.item_disease_name, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final SpecialHallBean.DataBean dataBean = data.get(position);
            holder.tvMedicalName.setText(dataBean.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DoctorDiseaseActivity.this,DoctorDiseaseTypeActivity.class);
                    intent.putExtra("title",dataBean.getName());
                    intent.putExtra("classId",dataBean.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_disease_name)
            TextView tvMedicalName;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
