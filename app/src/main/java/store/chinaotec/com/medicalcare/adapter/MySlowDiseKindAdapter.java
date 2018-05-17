package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.SlowDiseKind;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by Administrator on 2017/5/3 0003.
 * 慢性病种类适配器
 */

public class MySlowDiseKindAdapter extends RecyclerView.Adapter<MySlowDiseKindAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext = null;
    private SlowDiseLisener mSlowDisLisener = null;
    List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes;
    //慢性病种类列表条目编号,从sp文件获取没有值时的默认值
    private int CLICK_KIND_ITEM = 200;

    public MySlowDiseKindAdapter(Context context, List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes) {
        this.mContext = context;
        this.medicalTypes = medicalTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_slow_disease_kind, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        //保存慢性病的种类id到sp文件
        SpUtill.putInt(mContext, ResourseSum.Medica_SP, position + "kindId", medicalTypes.get(position).getId());
        //判断当前慢性病分类条目是否被选中
        int clickItem = SpUtill.getInt(mContext, ResourseSum.Medica_SP, "slow_dise");
        if ((clickItem != ResourseSum.default_value) && clickItem == position) {
            holder.itemSlowDisease.setBackgroundResource(R.mipmap.dise_kind_item_selected);
        } else {
            holder.itemSlowDisease.setBackgroundResource(R.mipmap.dise_kind_item_normal);
        }
        holder.slowDiseKindName.setText(medicalTypes.get(position).getTypeName());
    }

    @Override
    public int getItemCount() {
        return medicalTypes.size();
    }

    public void setOnClickListener(SlowDiseLisener slowDiseLisener) {
        this.mSlowDisLisener = slowDiseLisener;
    }

    @Override
    public void onClick(View v) {
        if (mSlowDisLisener != null) {
            mSlowDisLisener.itemClick((int) v.getTag());
        }
    }

    public interface SlowDiseLisener {
        void itemClick(int positon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.slow_dise_kind_logo)
        ImageView slowDiseKindLogo;
        @Bind(R.id.slow_dise_kind_name)
        TextView slowDiseKindName;
        @Bind(R.id.item_slow_disease)
        LinearLayout itemSlowDisease;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
