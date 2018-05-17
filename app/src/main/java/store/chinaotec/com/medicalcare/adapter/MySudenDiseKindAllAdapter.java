package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.SudDiseDetActivity;
import store.chinaotec.com.medicalcare.javabean.SuddenDiseBean;

/**
 * Created by Administrator on 2017/3/30 0030.
 * 所有种类突发伤病展示详情适配器
 */

public class MySudenDiseKindAllAdapter extends RecyclerView.Adapter<MySudenDiseKindAllAdapter.ViewHolder> {
    private Context mContext;
    private List<SuddenDiseBean.DataBean.MedicalTypeListBean> mTypeList;
    private SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean dataListBean;

    public MySudenDiseKindAllAdapter(Context context, List<SuddenDiseBean.DataBean.MedicalTypeListBean> typeList) {
        this.mContext = context;
        this.mTypeList = typeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_catory, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        if (position + 1 == mTypeList.size()) {
//            dataListBean = mTypeList.get(position).getDataList().get(0);
//            holder.diseaseKindName.setText(mTypeList.get(position).getTypeName());
//        } else {
        dataListBean = mTypeList.get(position + 1).getDataList().get(0);
        holder.diseaseKindName.setText(dataListBean.getTypeLabelName());
//            holder.diseaseKindName.setText(mTypeList.get(position + 1).getTypeName());
//        }
        holder.suddenDiseaseKind.setLayoutManager(new GridLayoutManager(mContext, 3));
        final MySubClassDetAdapter mySubClassDetAdapter = new MySubClassDetAdapter(mContext, dataListBean);
        holder.suddenDiseaseKind.setAdapter(mySubClassDetAdapter);
        mySubClassDetAdapter.setOnClickSuddenTreatListener(new MySubClassDetAdapter.SuddenTreatLisener() {
            @Override
            public void suddenTreatClick(int disease) {
                Intent intent = new Intent(mContext, SudDiseDetActivity.class);
                dataListBean = mTypeList.get(position + 1).getDataList().get(0);
                Integer memberSickDealId = dataListBean.getMemberSickDealList().get(disease).getMemberSickDealId();
                String diseName = dataListBean.getMemberSickDealList().get(disease).getName();
                intent.putExtra("memberSickDealId", memberSickDealId);
                intent.putExtra("diseName", diseName);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypeList.size() - 1;
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
