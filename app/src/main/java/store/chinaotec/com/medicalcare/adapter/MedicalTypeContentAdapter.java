package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
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
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * Created by seven on 2017/10/20 0020.
 * 图书内容
 */
public class MedicalTypeContentAdapter extends RecyclerView.Adapter<MedicalTypeContentAdapter.ViewHolder> {
    private Context mContext;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean> mTypeList;
    private MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean dataListBean;

    public MedicalTypeContentAdapter(Context context, List<MedicalBookBean.DataBean.MedicalTypeListBean> typeList) {
        this.mContext = context;
        this.mTypeList = typeList;
    }

    @Override
    public MedicalTypeContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_all_catory, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MedicalTypeContentAdapter.ViewHolder holder, int position) {
        dataListBean = mTypeList.get(position + 1).getDataList().get(0);
        holder.oneTypeName.setText(dataListBean.getTypeLabelName());
        holder.rvKindContent.setLayoutManager(new GridLayoutManager(mContext, 2));
        holder.rvKindContent.setAdapter(new MedicalTypeContentLevelAdapter(mContext, dataListBean));
    }

    @Override
    public int getItemCount() {
        return mTypeList.size() - 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_one_kind_name)
        TextView oneTypeName;
        @Bind(R.id.rv_medical_kind_content)
        RecyclerView rvKindContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
