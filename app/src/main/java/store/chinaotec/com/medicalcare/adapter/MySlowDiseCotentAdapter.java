package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import store.chinaotec.com.medicalcare.javabean.SlowDisesBean;

/**
 * Created by Administrator on 2017/5/4 0004.
 * 慢性病分类详情适配器
 */

public class MySlowDiseCotentAdapter extends RecyclerView.Adapter<MySlowDiseCotentAdapter.ViewHolder> {
    private Context mContext;
    private List<SlowDisesBean.DataBean.ChronicListBean> chronicList;

    public MySlowDiseCotentAdapter(Context context, List<SlowDisesBean.DataBean.ChronicListBean> chronicList) {
        this.mContext = context;
        this.chronicList = chronicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_slow_disease_detail, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.slowDiseaseName.setText(chronicList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return chronicList.size();
    }

    /**
     * @param slowLisener 点击疾病分类条目的监听回调
     *                    设置疾病分类条目的监听回调
     */
    public void setOnClickSlowLisener(SlowDisesLisener slowLisener) {
        this.mSlowDisesLisener = slowLisener;
    }

    private SlowDisesLisener mSlowDisesLisener;

    /**
     * 疾病分类条目的监听回调
     */
    public interface SlowDisesLisener {
        /**
         * @param slowDiseName 当前条目疾病的名字
         * @param slowDiseId   当前条目疾病的id
         */
        void clickItem(String slowDiseName, String slowDiseId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.slow_disease_name)
        TextView slowDiseaseName;
        @Bind(R.id.add_disease_switch)
        ImageView addDiseaseSwitch;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSlowDisesLisener != null) {
                        mSlowDisesLisener.clickItem(chronicList.get(getAdapterPosition()).getName(), chronicList.get(getAdapterPosition()).getId());
                    }
                }
            });
        }
    }
}
