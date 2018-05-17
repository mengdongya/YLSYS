package store.chinaotec.com.medicalcare.activity;

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
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HospitalDoctorBean;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;

/**
 * Created by wjc on 2017/12/20 0020.
 * 专家列表
 */
public class HospitalDoctorListActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.rv_expert_doctor)
    LRecyclerView recyclerView;
    private String hospitalCode;
    private int pageIndex = 1;
    private HashMap<String, String> mParams;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<HospitalDoctorBean.DataBean.DoctorBeans> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hospitalCode = getIntent().getStringExtra("hospitalCode");
        setContentView(R.layout.activity_hospital_doctor);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyRecyclerAdapter());
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getDoctorListResponse();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                getDoctorListResponse();
            }
        });
        getDoctorListResponse();
    }

    private void getDoctorListResponse() {
        mParams.clear();
        String url = MyUrl.DOCTOR_FORUM_LIST;
        mParams.put("hospitalCode", hospitalCode);
        mParams.put("pageIndex", pageIndex + "");
//        mParams.put("pageSize", "10");
        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                HospitalDoctorBean doctorBean = JSONObject.parseObject(response, HospitalDoctorBean.class);
                if ("0".equals(doctorBean.getResponseCode())) {
                    if (pageIndex == 1) {
                        data.clear();
                    }
                    data.addAll(doctorBean.getData().getDoctorList());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HospitalDoctorListActivity.this, doctorBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(HospitalDoctorListActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(HospitalDoctorListActivity.this).
                    inflate(R.layout.item_hospital_doctor, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HospitalDoctorBean.DataBean.DoctorBeans doctor = data.get(position);
            Glide.with(HospitalDoctorListActivity.this).load(doctor.getHeadImage()).transform(new
                    GlideRoundTransform(HospitalDoctorListActivity.this, 5)).into(holder.ivDoctorHeadImg);

            holder.tvDoctorName.setText(doctor.getName());
            holder.tvDoctorLable.setText(doctor.getDoctorLabel());
            holder.tvDoctorSummary.setText(doctor.getSummary());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.iv_doctor_head_img)
            ImageView ivDoctorHeadImg;
            @Bind(R.id.tv_doctor_name)
            TextView tvDoctorName;
            @Bind(R.id.tv_doctor_lable)
            TextView tvDoctorLable;
            @Bind(R.id.tv_doctor_summary)
            TextView tvDoctorSummary;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
