package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
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
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by seven on 2017/12/1 0001.
 */
public class CheckResultOneLevelDataAdapter extends RecyclerView.Adapter<CheckResultOneLevelDataAdapter.ViewHolder> {
    private final Context context;
    private final List<TextResultBean.DataBean.MedicalTypeListBean.DataListBean> dataList;

    public CheckResultOneLevelDataAdapter(Context context, List<TextResultBean.DataBean.MedicalTypeListBean.DataListBean> dataList){
        this.context = context;
        this.dataList = dataList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_check_result_three, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final List<TextResultBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> sickDealList = dataList.get(0).getMemberSickDealList();
        holder.textResultName.setText(sickDealList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SudDiseDetActivity.class);
                intent.putExtra("memberSickDealId",sickDealList.get(position).getMemberSickDealId());
                intent.putExtra("diseName",sickDealList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.get(0).getMemberSickDealList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_check_three_level_name)
        TextView textResultName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
