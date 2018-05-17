package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.ChronicBean;

/**
 * Created by Administrator on 2017/11/22 0022.
 * 用户疾病种类删除页面疾病种类显示适配器
 */

public class MyDiseAdapter extends RecyclerView.Adapter<MyDiseAdapter.ViewHolder> {
    List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> mDiseList = new ArrayList<>();
    private Context mContext;

    public MyDiseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_delete_dise, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.deleteDiseNumber.setText(position + 1 + ".");
        holder.diseName.setText(mDiseList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDiseList.size();
    }

    public void reshData(List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> list) {
        if (mDiseList.size() != 0) {
            mDiseList.clear();
        }
        mDiseList.addAll(list);
        notifyDataSetChanged();
    }

    DeleteDiseListener mDeleteDiseListener;

    /**
     * 点击删除疾病种类监听
     */
    public interface DeleteDiseListener {
        /**
         * @param position  删除疾病的列表编号
         * @param diseName  删除疾病的名字
         */
        void deleteDise(int position,String diseName);
    }

    public void setDeleteUserDise(DeleteDiseListener deleteDiseListener) {
        this.mDeleteDiseListener = deleteDiseListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.delete_dise_number)
        TextView deleteDiseNumber;
        @Bind(R.id.dise_number)
        RelativeLayout diseNumber;
        @Bind(R.id.dise_name)
        TextView diseName;
        @Bind(R.id.delete_switch)
        ImageView deleteSwitch;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteDiseListener != null) {
                        mDeleteDiseListener.deleteDise(getAdapterPosition(),mDiseList.get(getAdapterPosition()).getName());
                    }
                }
            });
        }
    }
}
