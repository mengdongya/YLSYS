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

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by Administrator on 2017/5/3 0003.
 * 检查结果类别适配器
 */

public class MyTextResultKindAdapter extends RecyclerView.Adapter<MyTextResultKindAdapter.ViewHolder> implements View.OnClickListener {
    private SharedPreferences mSharedPreferences = null;
    private Context mContext = null;
    private TextResultLisener mTextResultLisener = null;
    private List<TextResultBean.DataBean.MedicalTypeListBean> medicalTypeList;

    public MyTextResultKindAdapter(Context context, SharedPreferences sharedPreferences, List<TextResultBean.DataBean.MedicalTypeListBean> medicalTypeList) {
        this.mContext = context;
        this.mSharedPreferences = sharedPreferences;
        this.medicalTypeList = medicalTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_text_result_kind, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        int position1 = mSharedPreferences.getInt("text_dise", 101);
        if (position1 != 101 && position1 == position) {
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_selected);
        } else {
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_normal);
        }
        //显示种类logo
//        if (position != 0) {
            Glide.with(mContext).load(medicalTypeList.get(position).getTypeImage()).placeholder(R.mipmap.slow_disease_kind_one).into(holder.textResultKindLogo);
//        }
        holder.textResultKindName.setText(medicalTypeList.get(position).getTypeName());
    }

    @Override
    public int getItemCount() {
        return medicalTypeList.size();
    }

    public void setOnClickLisenerSlowDisease(TextResultLisener textResultLisener) {
        this.mTextResultLisener = textResultLisener;
    }

    @Override
    public void onClick(View v) {
        if (mTextResultLisener != null) {
            mTextResultLisener.itemClick((int) v.getTag());
        }
    }

    public interface TextResultLisener {
        void itemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_result_kind_logo)
        ImageView textResultKindLogo;
        @Bind(R.id.text_result_kind_name)
        TextView textResultKindName;
        @Bind(R.id.item_text_result)
        LinearLayout itemTextResult;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
