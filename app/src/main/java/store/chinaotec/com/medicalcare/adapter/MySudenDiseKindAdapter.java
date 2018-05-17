package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.SuddenDiseBean;

/**
 * Created by hxk on 2017/7/31 0031 13:31
 * 突发伤病分类展示适配器
 */

public class MySudenDiseKindAdapter extends RecyclerView.Adapter<MySudenDiseKindAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext = null;
    private AllClassListener mAllClassListener;
    private SharedPreferences mSharedPreferences;
    private List<SuddenDiseBean.DataBean.MedicalTypeListBean> mTypeList;

    public MySudenDiseKindAdapter(Context context, List<SuddenDiseBean.DataBean.MedicalTypeListBean> typeList, SharedPreferences sharedPreferences) {
        this.mContext = context;
        this.mTypeList = typeList;
        this.mSharedPreferences = sharedPreferences;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_dise_class, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.diseKind.setText(mTypeList.get(position).getTypeName());
        holder.itemView.setTag(position);
        //根据点击的突发病种类改变选中效果
        int dise_kind = mSharedPreferences.getInt("suden_dise", 105);
        if (dise_kind != 105 && dise_kind == position) {
            holder.diseKind.setBackgroundResource(R.mipmap.dis_kind_select);
        } else {
            holder.diseKind.setBackgroundResource(R.color.kind_back);
        }
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public void setOnItemClick(AllClassListener allClassListener) {
        this.mAllClassListener = allClassListener;
    }

    @Override
    public void onClick(View v) {
        if (mAllClassListener != null) {
            mAllClassListener.click((int) v.getTag());
        }
    }

    public interface AllClassListener {
        void click(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.dise_kind)
        TextView diseKind;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
