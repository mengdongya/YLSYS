package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.MyCollectList;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;
import store.chinaotec.com.medicalcare.view.MySetRatingBar;

/**
 * 我的收藏页面
 */
public class MineCollectionActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_collect_doctor)
    TextView tvDoctor;
    @Bind(R.id.tv_collect_hospital)
    TextView tvHospital;
    @Bind(R.id.rv_collect_list)
    LRecyclerView recyclerView;
    private HashMap<String, String> mParams;
    private String sid;
    private List<MyCollectList.CollectionBean> data;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int pageIndex = 1;
    private boolean isDoctor = true;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        mParams = new HashMap<>();
        data = new ArrayList<>();
        isDoctor = true;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyRecyclerAdapter());
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
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
        mParams.clear();
        mParams.put("sid", sid);
        mParams.put("pageIndex", pageIndex + "");
        if (isDoctor) {
            url = MyUrl.COLLECTION_DOCTOR_LIST;
        } else {
            url = MyUrl.COLLECTION_HOSPITAL_LIST;
        }

        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MyCollectList myCollectList = JSONObject.parseObject(response, MyCollectList.class);
                if ("0".equals(myCollectList.getResponseCode())) {
                    if (pageIndex == 1) {
                        data.clear();
                    }
                    data.addAll(myCollectList.getData());
                    lRecyclerViewAdapter.notifyDataSetChanged();
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

    private void initListener() {
        tvDoctor.setOnClickListener(this);
        tvHospital.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collect_doctor:
                isDoctor = true;
                tvDoctor.setTextColor(getResources().getColor(R.color.colorTooBar));
                tvHospital.setTextColor(getResources().getColor(R.color.goHospitalContTitle));
                initData();
                pageIndex = 1;
                break;
            case R.id.tv_collect_hospital:
                isDoctor = false;
                tvDoctor.setTextColor(getResources().getColor(R.color.goHospitalContTitle));
                tvHospital.setTextColor(getResources().getColor(R.color.colorTooBar));
                initData();
                pageIndex = 1;
                break;
        }
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MineCollectionActivity.this).inflate(R.layout.item_collect, parent, false));
        }

        @Override
        public void onBindViewHolder( MyViewHolder holder, int position) {
            final MyCollectList.CollectionBean collectionBean = data.get(position);
            Glide.with(MineCollectionActivity.this).load(collectionBean.getHeadImage()).fitCenter().transform(
                    new GlideRoundTransform(MineCollectionActivity.this, 5)).placeholder(R.mipmap.ic_launcher).into(holder.hospitalLogo);

            holder.appStars.setStar(Float.parseFloat(collectionBean.getStarLevel()));
            String[] split = null;
            if (isDoctor){
                holder.hospitalName.setText(collectionBean.getHospitalName());
                holder.tvDoctorName.setText(collectionBean.getName());
                holder.tvDoctorName.setVisibility(View.VISIBLE);
                holder.reservationNumber.setText(collectionBean.getAppointment()+"人预约");
                holder.reservationNumber.setVisibility(View.VISIBLE);
                split = collectionBean.getDoctorLabel().split(",");
            }else {
                holder.hospitalName.setText(collectionBean.getName());
                holder.tvDoctorName.setVisibility(View.GONE);
                holder.reservationNumber.setVisibility(View.GONE);
                split = collectionBean.getHospitalLabel().split(",");
            }
            holder.llHospitalLevel.removeAllViews();
            for (String aSplit : split) {
                View view = LayoutInflater.from(MineCollectionActivity.this).inflate(R.layout.item_hospital_level, null);
                ((TextView) view.findViewById(R.id.tv_hospital_level)).setText(aSplit);
                holder.llHospitalLevel.addView(view);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    if (isDoctor){
                        intent.setClass(MineCollectionActivity.this,DoctorDetailActivity.class);
                        intent.putExtra("doctor_code",collectionBean.getDoctorCode());
                    }else {
                        intent.setClass(MineCollectionActivity.this,HospitalDetailActivity.class);
                        intent.putExtra("hospitalCode", collectionBean.getHospitalCode());
                        intent.putExtra("hospitalLabel", collectionBean.getHospitalLabel());
                    }
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.hospital_logo)
            ImageView hospitalLogo;
            @Bind(R.id.tv_doctor_name)
            TextView tvDoctorName;
            @Bind(R.id.hospital_name)
            TextView hospitalName;
            @Bind(R.id.ll_hospital_doctor_level)
            LinearLayout llHospitalLevel;
            @Bind(R.id.app_stars)
            MySetRatingBar appStars;
            @Bind(R.id.reservation_number)
            TextView reservationNumber;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
