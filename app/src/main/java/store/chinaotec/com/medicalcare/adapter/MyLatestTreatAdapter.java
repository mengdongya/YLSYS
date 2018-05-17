package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.HealthInfoDetailActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class MyLatestTreatAdapter extends RecyclerView.Adapter<MyLatestTreatAdapter.ViewHolder> {

    private final List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> list;
    private Context mContext;

    public MyLatestTreatAdapter(Context context, List<MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> medicalTypeList) {
        this.mContext = context;
        this.list = medicalTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_knowledge_charge, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.knowledgeName.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(list.get(position).getUrl())){
                    Intent intent = new Intent(mContext,HealthInfoDetailActivity.class);
                    intent.putExtra("title", list.get(position).getName());
                    intent.putExtra("url",list.get(position).getUrl());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_info_arrow)
        ImageView userInfoArrow;
        @Bind(R.id.knowledge_name)
        TextView knowledgeName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
