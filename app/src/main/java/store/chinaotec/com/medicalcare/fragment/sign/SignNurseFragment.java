package store.chinaotec.com.medicalcare.fragment.sign;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.DoctorDetailActivity;
import store.chinaotec.com.medicalcare.javabean.SignDoctorBean;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;
import store.chinaotec.com.medicalcare.view.MySetRatingBar;

/**
 * A simple {@link Fragment} subclass.
 * 签约护士页面
 */
public class SignNurseFragment extends Fragment {

    @Bind(R.id.rv_sign_doctor)
    LRecyclerView recyclerView;
    private HashMap<String, String> mParams;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int pageIndex = 1;
    private List<SignDoctorBean.SignDoctor> data;
    private String[] split;
    private String cityArr;

    public SignNurseFragment() {
    }

    public static SignNurseFragment instance(String address) {
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        SignNurseFragment signNurseFragment = new SignNurseFragment();
        signNurseFragment.setArguments(bundle);
        return signNurseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cityArr = getArguments().getString("address");
        View view = inflater.inflate(R.layout.fragment_sign_nurse, container, false);
        ButterKnife.bind(this, view);
        initView();
        getDoctorData();
        return view;
    }

    private void initView() {

        if (!MyCommonUtil.isEmpty(cityArr)) {
            split = cityArr.split(",");
        } else {
            split = new String[]{"0.0", "0.0"};
        }
        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new DoctorRecyclerAdapter());
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getDoctorData();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                getDoctorData();
            }
        });
    }

    private void getDoctorData() {
        mParams.clear();
        mParams.put("latitude",split[0]);
        mParams.put("longitude",split[1]);
        mParams.put("pageIndex",pageIndex+"");
        OkHttpUtils.post().url(MyUrl.SIGN_NURSE_LIST).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                SignDoctorBean doctorBean = JSONObject.parseObject(response, SignDoctorBean.class);
                if ("0".equals(doctorBean.getResponseCode())){
                    if (pageIndex == 1){
                        data.clear();
                    }
                    data.addAll(doctorBean.getData());
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

    class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_singed_doctor, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final SignDoctorBean.SignDoctor doctor = data.get(position);
            Glide.with(getActivity()).load(doctor.getHeadImage()).fitCenter().transform(
                    new GlideRoundTransform(getActivity(), 5)).placeholder(R.mipmap.ic_launcher).into(holder.signDoctorPhoto);
            holder.signHospitalName.setText(doctor.getHospitalName());
            holder.signDoctorStars.setStar(Float.parseFloat(doctor.getStarLevel()));
            holder.signDoctorName.setText(doctor.getName());
            holder.signAdvisoryNumber.setText(doctor.getAppointment()+"");
            String[] split = doctor.getDoctorLabel().split(",");
            holder.ll_doctor_level.removeAllViews();
            for (int i = 0; i < split.length; i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_hospital_level, null);
                ((TextView) view.findViewById(R.id.tv_hospital_level)).setText(split[i]);
                holder.ll_doctor_level.addView(view);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("doctor_code", doctor.getCode());
                    intent.setClass(getContext(), DoctorDetailActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.sign_doctor_photo)
            ImageView signDoctorPhoto;
            @Bind(R.id.sign_doctor_name)
            TextView signDoctorName;
            @Bind(R.id.ll_doctor_level)
            LinearLayout ll_doctor_level;
            @Bind(R.id.sign_hospital_name)
            TextView signHospitalName;
            @Bind(R.id.sign_advisory_number)
            TextView signAdvisoryNumber;
            @Bind(R.id.sign_doctor_stars)
            MySetRatingBar signDoctorStars;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
