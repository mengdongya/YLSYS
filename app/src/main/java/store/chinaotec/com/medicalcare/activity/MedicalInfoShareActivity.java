package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * 医疗信息共享页面
 */
public class MedicalInfoShareActivity extends BaseActivity{

    @Bind(R.id.share_infokind_recycleview)
    RecyclerView recyclerView;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean.DataChildTypeList> medicalTypeList;
    private MyShareInfoKindAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info_share);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.medical_book_list).addParams("type", "14").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalBookBean medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
                medicalTypeList = medicalBookBean.getData().getMedicalTypeList().get(0).getChildTypeList();
                setData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new MyShareInfoKindAdapter();
        recyclerView.setAdapter(adapter);
    }

    class MyShareInfoKindAdapter extends RecyclerView.Adapter<MyShareInfoKindAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(MedicalInfoShareActivity.this).inflate(R.layout.item_shareinfo_kind, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.title.setText(medicalTypeList.get(position).getTypeName());
            holder.shareInfoKindDetail.setLayoutManager(new LinearLayoutManager(MedicalInfoShareActivity.this, LinearLayoutManager.VERTICAL, false));
            holder.shareInfoKindDetail.setAdapter(new MyInfoKindDetailAdapter(medicalTypeList.get(position).getDataList().get(0).getMedicalBookList()));
        }

        @Override
        public int getItemCount() {
            return medicalTypeList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.shareinfo_kind_detail)
            RecyclerView shareInfoKindDetail;
            @Bind(R.id.share_info_kind_title)
            TextView title;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    class MyInfoKindDetailAdapter extends RecyclerView.Adapter<MyInfoKindDetailAdapter.ViewHolder> {


        private final List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> list;

        public MyInfoKindDetailAdapter(List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> listBean) {
            this.list = listBean;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(MedicalInfoShareActivity.this).inflate(R.layout.item_infor_detail, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.shareInfoTitle.setText(list.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MedicalInfoShareActivity.this,HealthInfoDetailActivity.class);
                    intent.putExtra("title", list.get(position).getName());
                    intent.putExtra("url",list.get(position).getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.share_info_title)
            TextView shareInfoTitle;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
