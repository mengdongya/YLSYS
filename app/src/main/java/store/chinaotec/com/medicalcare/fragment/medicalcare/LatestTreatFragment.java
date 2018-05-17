package store.chinaotec.com.medicalcare.fragment.medicalcare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyLatestTreatAdapter;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * A simple {@link Fragment} subclass.
 * 最新诊疗成果页面
 */
public class LatestTreatFragment extends Fragment {

    @Bind(R.id.latest_Treat_recycleview)
    RecyclerView latestTreatRecycleview;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> medicalTypeList;

    public LatestTreatFragment() {
        // Required empty public constructor
    }

    public static LatestTreatFragment instance() {
        Bundle bundle = new Bundle();
        LatestTreatFragment latestTreatFragment = new LatestTreatFragment();
        latestTreatFragment.setArguments(bundle);
        return latestTreatFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_treat, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.medical_book_list).addParams("type", "15").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalBookBean medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
                medicalTypeList = medicalBookBean.getData().getMedicalTypeList().get(0).getDataList().get(0).getMedicalBookList();
                setData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    private void setData() {
        latestTreatRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        latestTreatRecycleview.setAdapter(new MyLatestTreatAdapter(getActivity(),medicalTypeList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
