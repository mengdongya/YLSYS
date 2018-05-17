package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/5/11 0011.
 * 医养知识展示适配器
 */

public class MyKnowlChargeAdapter extends RecyclerView.Adapter<MyKnowlChargeAdapter.ViewHolder> {

    private Context mContext = null;
    private String[] knowledgeName = {"准妈妈入院前需要做什么准备?", "女性肾功能异常为什么别勉强怀孕?",
            "什么是习惯性流产?如何预防习惯性流产?", "春天吃什么好,春天的养生之道?", "12个月的健康养生计划,健康养生计划?"};

    public MyKnowlChargeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_knowledge_charge, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.knowledgeName.setText(knowledgeName[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
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
