package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.SlowDisesBean;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by Administrator on 2017/5/4 0004.
 * 修改用户疾病种类,分类详情适配器
 */

public class MyEditDiseAdapter extends RecyclerView.Adapter<MyEditDiseAdapter.ViewHolder> {

    private Context mContext;
    private List<SlowDisesBean.DataBean.ChronicListBean> chronicList;


    public MyEditDiseAdapter(Context context, List<SlowDisesBean.DataBean.ChronicListBean> chronicList) {
        this.mContext = context;
        this.chronicList = chronicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_edit_dise, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.deleteDiseName.setText(chronicList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return chronicList.size();
    }


    private SlowDisesEditLisener mSlowDisesEditLisener;

    public interface SlowDisesEditLisener {
        void editDise(String chronicId,String diseName);
    }

    public void setOnClickSlowLisener(SlowDisesEditLisener slowDisesEditLisener) {
        this.mSlowDisesEditLisener = slowDisesEditLisener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.delete_dise_name)
        TextView deleteDiseName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSlowDisesEditLisener != null) {
                        mSlowDisesEditLisener.editDise(chronicList.get(getAdapterPosition()).getId(),chronicList.get(getAdapterPosition()).getName());
                    }
                }
            });
        }
    }
}
