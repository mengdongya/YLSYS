package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * Created by seven on 2017/12/1 0001.
 */
public class MedicalFoodContentAdapter extends RecyclerView.Adapter<MedicalFoodContentAdapter.ViewHolder> {

    private final Context context;
    private final MedicalBookBean.DataBean.MedicalTypeListBean medicalTypeListBean;


    public MedicalFoodContentAdapter(Context context, MedicalBookBean.DataBean.MedicalTypeListBean medicalTypeList) {
        this.context = context;
        this.medicalTypeListBean = medicalTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_check_result, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rvTwoLevelData.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.rvTwoLevelData.setAdapter(new MedicalFoodTwoLevelDataAdapter(context,medicalTypeListBean.getChildTypeList().get(position).getDataList()));
        if (medicalTypeListBean.getChildTypeList().size() > 0){
            holder.tvTwoLevelName.setVisibility(View.VISIBLE);
            holder.tvTwoLevelName.setText(medicalTypeListBean.getChildTypeList().get(position).getTypeName());
            holder.rvThreeLevel.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            holder.rvThreeLevel.setAdapter(new MedicalFoodThreeLevelAdapter(context,medicalTypeListBean.getChildTypeList().get(position)));

        }else {
            holder.tvTwoLevelName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return medicalTypeListBean.getChildTypeList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_two_level_name)
        TextView tvTwoLevelName;
        @Bind(R.id.rv_two_level_data)
        RecyclerView rvTwoLevelData;
        @Bind(R.id.rv_three_level)
        RecyclerView rvThreeLevel;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
