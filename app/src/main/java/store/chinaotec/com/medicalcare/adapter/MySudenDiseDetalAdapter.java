package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.BaseContentBean;

/**
 * Created by hxk on 2017/8/1 0001 13:37
 * 突发伤病 详情页面标题 内容展示适配器
 */

public class MySudenDiseDetalAdapter extends RecyclerView.Adapter<MySudenDiseDetalAdapter.ViewHolder> {
    List<BaseContentBean> mlist;
    private Context mContext;

    public MySudenDiseDetalAdapter(Context context, List<BaseContentBean> list) {
        this.mContext = context;
        this.mlist = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_sudden_dise_detail, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.diseDetailName.setText(mlist.get(position).getContentName());
        holder.diseDetailContent.setText(mlist.get(position).getContentInfo());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.dise_detail_name)
        TextView diseDetailName;
        @Bind(R.id.dise_detail_content)
        TextView diseDetailContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
