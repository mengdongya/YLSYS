package store.chinaotec.com.medicalcare.fragment.medicalcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
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
import store.chinaotec.com.medicalcare.activity.HealthInfoDetailActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalKnowledgeBean;

/**
 * Created by wjc on 2018/1/22 0022.
 * 大众医学知识
 */
public class MedicalKnowledgeFragment extends Fragment {

    @Bind(R.id.rv_medical_list)
    LRecyclerView recyclerView;
    private int id;
    private int pageIndex = 1;
    private HashMap<String, String> mParams;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<MedicalKnowledgeBean.DataBean.MedicalBookListBean> data;

    public static MedicalKnowledgeFragment newInstance(int medicalTypeId) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", medicalTypeId);
        MedicalKnowledgeFragment fragment = new MedicalKnowledgeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        View inflate = inflater.inflate(R.layout.fragment_medical_knowledge, container, false);
        ButterKnife.bind(this, inflate);
        initView();
        initData();
        return inflate;
    }

    private void initView() {
        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        mParams.put("medicalTypeId",id + "");
        mParams.put("pageIndex",pageIndex + "");
        OkHttpUtils.post().url(MyUrl.MEDICAL_KNOWLEDGE_LIST).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalKnowledgeBean medicalKnowledgeBean = JSONObject.parseObject(response, MedicalKnowledgeBean.class);
                if ("0".equals(medicalKnowledgeBean.getResponseCode())){
                    if (pageIndex == 1){
                        data.clear();
                    }
                    data.addAll(medicalKnowledgeBean.getData().getMedicalBookList());
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
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_disease_name, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final MedicalKnowledgeBean.DataBean.MedicalBookListBean dataBean = data.get(position);
            holder.tvMedicalName.setText(dataBean.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),HealthInfoDetailActivity.class);
                    intent.putExtra("title",dataBean.getName());
                    intent.putExtra("url",dataBean.getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.tv_disease_name)
            TextView tvMedicalName;
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
