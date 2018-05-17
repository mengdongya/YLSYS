package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
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
import store.chinaotec.com.medicalcare.db.UpdateRecordTable;
import store.chinaotec.com.medicalcare.dialog.LoadingView;
import store.chinaotec.com.medicalcare.javabean.DiseaseRecord;
import store.chinaotec.com.medicalcare.javabean.SuddenDiseBean;

/**
 * Created by wjc on 2018/3/2 0002.
 * 突发伤病处置
 */

public class TreatmentOfSuddenInjuryActivity extends AppCompatActivity {
    @Bind(R.id.tv_title_back)
    ImageView tvTitleBack;
    @Bind(R.id.tv_title_view)
    TextView tvTitleView;
    @Bind(R.id.iv_disease_search)
    ImageView ivDiseaseSearch;
    @Bind(R.id.rv_disease_sudden)
    LRecyclerView rvDiseaseSudden;
    RecyclerView rvDiseaseCheck;
    LinearLayout llDiseaseCheck;

    private List<SuddenDiseBean.DataBean.MedicalTypeListBean> dataList;
    private UpdateRecordTable updataTable;
    private ArrayList<DiseaseRecord> updateRecords;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private View headerView;
    private MyCheckAdapter checkAdapter;
    private LoadingView loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudden_injury);
        ButterKnife.bind(this);
        initView();
        getResponseData();
    }

    private void initView() {
        loadingDialog = new LoadingView(this, R.style.ProgressDialog);
        tvTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivDiseaseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TreatmentOfSuddenInjuryActivity.this,SuddenDiseaseSearchActivity.class));
            }
        });

        updataTable = new UpdateRecordTable(this);
        updateRecords = updataTable.queryAllRecords();
        headerView = getHeaderView();
        rvDiseaseCheck.setLayoutManager(new GridLayoutManager(this,4));
        checkAdapter = new MyCheckAdapter();
        rvDiseaseCheck.setAdapter(checkAdapter);

        if (updateRecords.size() > 0){
            llDiseaseCheck.setVisibility(View.VISIBLE);
        }else {
            llDiseaseCheck.setVisibility(View.GONE);
        }

        dataList = new ArrayList<>();
//        rvDiseaseSudden.setLayoutManager(new GridLayoutManager(this,4));
        rvDiseaseSudden.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MySuddenListAdapter());
        lRecyclerViewAdapter.addHeaderView(headerView);
        rvDiseaseSudden.setAdapter(lRecyclerViewAdapter);
        rvDiseaseSudden.setLoadMoreEnabled(false);
        rvDiseaseSudden.setPullRefreshEnabled(false);
    }

    private View getHeaderView() {
        View view = View.inflate(this,R.layout.header_sudden_disease,null);
        llDiseaseCheck = (LinearLayout) view.findViewById(R.id.ll_disease_check);
        rvDiseaseCheck = (RecyclerView) view.findViewById(R.id.rv_disease_check);
        return view;
    }

    private void getResponseData() {
        loadingDialog.show();
        OkHttpUtils.post().url(MyUrl.SUDEN_DISE_RESULT).addParams("type","1").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                loadingDialog.dismiss();
                SuddenDiseBean suddenDiseBean = JSON.parseObject(response, SuddenDiseBean.class);
                dataList.clear();
//                for (int i = 0;i < suddenDiseBean.getData().getMedicalTypeList().size();i++){
//                    dataList.addAll(suddenDiseBean.getData().getMedicalTypeList().get(i).getDataList().get(0).getMemberSickDealList());
//                }
                dataList.addAll(suddenDiseBean.getData().getMedicalTypeList());
                lRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                loadingDialog.dismiss();
            }

        });
    }

    public class MySuddenListAdapter extends RecyclerView.Adapter<MySuddenListAdapter.ViewHolder> {

        private SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean dataListBean;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_catory, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            dataListBean = dataList.get(position + 1).getDataList().get(0);
            holder.diseaseKindName.setText(dataListBean.getTypeLabelName());
            holder.suddenDiseaseKind.setLayoutManager(new GridLayoutManager(TreatmentOfSuddenInjuryActivity.this, 4));
            MySuddenDiseAdapter adapter = new MySuddenDiseAdapter(dataListBean);
            holder.suddenDiseaseKind.setAdapter(adapter);
        }

        @Override
        public int getItemCount() {
            return dataList.size() - 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.disease_kind_name)
            TextView diseaseKindName;
            @Bind(R.id.sudden_disease_kind)
            RecyclerView suddenDiseaseKind;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    class MySuddenDiseAdapter extends RecyclerView.Adapter<MySuddenDiseAdapter.ViewHolder> {

        private final SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean mDataListBean;

        public MySuddenDiseAdapter(SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean dataListBean) {
            this.mDataListBean = dataListBean;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(TreatmentOfSuddenInjuryActivity.this).inflate(R.layout.item_sudden_diease_kind, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.suddenDiaeaseName.setText(mDataListBean.getMemberSickDealList().get(position).getName());
            Glide.with(TreatmentOfSuddenInjuryActivity.this).load(mDataListBean.getMemberSickDealList().get(position)
                    .getImage()).into(holder.suddenDiaeaseLogo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordCheck(mDataListBean.getMemberSickDealList().get(position));

//                    Intent intent = new Intent(TreatmentOfSuddenInjuryActivity.this,SudDiseDetActivity.class);
                    Intent intent = new Intent(TreatmentOfSuddenInjuryActivity.this,TreatmentOfSuddenInjuryDetailActivity.class);
                    intent.putExtra("title",mDataListBean.getMemberSickDealList().get(position).getName());
                    intent.putExtra("url",mDataListBean.getMemberSickDealList().get(position).getUrl());
                    startActivityForResult(intent,1);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataListBean.getMemberSickDealList().size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.sudden_diaease_logo)
            ImageView suddenDiaeaseLogo;
            @Bind(R.id.sudden_diaease_name)
            TextView suddenDiaeaseName;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    private void recordCheck(SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean memberSickDealListBean) {
        DiseaseRecord mRecord = new DiseaseRecord(-1,memberSickDealListBean.getName(),memberSickDealListBean.getImage(),
                memberSickDealListBean.getMemberSickDealId(),System.currentTimeMillis(),memberSickDealListBean.getUrl());

        if (updataTable.checkSickDealId(memberSickDealListBean.getMemberSickDealId())){
            updataTable.insertRecordObject(mRecord);
        }else {
            updataTable.updateRecordObject(mRecord);
        }

        updateRecords = updataTable.queryAllRecords();
        System.out.print("updateRecords ===" +updateRecords.toString());
        Log.d("",updateRecords.toString());
    }

    class MyCheckAdapter extends RecyclerView.Adapter<MyCheckAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(TreatmentOfSuddenInjuryActivity.this).inflate(R.layout.item_sudden_diease_kind, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.suddenDiaeaseName.setText(updateRecords.get(position).getName());
            Glide.with(TreatmentOfSuddenInjuryActivity.this).load(updateRecords.get(position).getImage()).into(holder.suddenDiaeaseLogo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TreatmentOfSuddenInjuryActivity.this,TreatmentOfSuddenInjuryDetailActivity.class);
                    intent.putExtra("title",updateRecords.get(position).getName());
                    intent.putExtra("url",updateRecords.get(position).getUrl());
                    startActivityForResult(intent,1);
                }
            });
        }

        @Override
        public int getItemCount() {
            return updateRecords.size() > 8 ? 8 :updateRecords.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.sudden_diaease_logo)
            ImageView suddenDiaeaseLogo;
            @Bind(R.id.sudden_diaease_name)
            TextView suddenDiaeaseName;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateRecords = updataTable.queryAllRecords();
        if (updateRecords.size() > 0){
            llDiseaseCheck.setVisibility(View.VISIBLE);
            if (lRecyclerViewAdapter != null && checkAdapter != null){
                lRecyclerViewAdapter.notifyDataSetChanged();
                checkAdapter.notifyDataSetChanged();
            }
        }else {
            llDiseaseCheck.setVisibility(View.GONE);
        }

    }
}
