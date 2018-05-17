package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HelpDoctorBeean;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by hxk on 2017/9/25 0025 16:18
 * 求救医生列表适配器
 */

public class MyHelpDoctorAdapter extends RecyclerView.Adapter<MyHelpDoctorAdapter.ViewHolder> {

    private Context mContext;
    List<HelpDoctorBeean.DataBean.MemberCalloutListBean> memberList = new ArrayList();

    public MyHelpDoctorAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_add_help_doctor, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.addDoctorName.setText(memberList.get(position).getCalloutName());
        holder.addDoctorPhone.setText(memberList.get(position).getTelephone());
        holder.addDoctorNumber.setText(position + 1 + ".");

        holder.callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + memberList.get(position).getTelephone()));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void setData(List<HelpDoctorBeean.DataBean.MemberCalloutListBean> dataList) {
        if (memberList.size() != 0) {
            memberList.clear();
        }
        memberList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    /**
     * 长按事件监听接口
     */
    public interface OnLongItemClickListener {
        void onLongItemClick(String memberCalloutId, int adapterPosition);
    }

    /**
     * 点击事件监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(HelpDoctorBeean.DataBean.MemberCalloutListBean emberCalloutListBean);
    }

    public OnItemClickListener mOnItemClickListener = null;//点击
    public OnLongItemClickListener mOnLongItemClickListener = null;//长按

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener nLongItemClickListener) {
        this.mOnLongItemClickListener = nLongItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.add_doctor_number)
        TextView addDoctorNumber;
        @Bind(R.id.add_doctor_name)
        TextView addDoctorName;
        @Bind(R.id.add_doctor_phone)
        TextView addDoctorPhone;
        @Bind(R.id.call_phone)
        ImageView callPhone;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(memberList.get(getAdapterPosition()));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnLongItemClickListener != null) {
                        mOnLongItemClickListener.onLongItemClick(memberList.get(getAdapterPosition()).getMemberCalloutId(), getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
