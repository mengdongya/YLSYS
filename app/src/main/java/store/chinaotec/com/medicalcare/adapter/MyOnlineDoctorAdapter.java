package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
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
import store.chinaotec.com.medicalcare.view.MySetRatingBar;
import store.chinaotec.com.medicalcare.javabean.DoctorBean;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class MyOnlineDoctorAdapter extends RecyclerView.Adapter<MyOnlineDoctorAdapter.ViewHolder> implements View.OnClickListener {
    private OnlineDoctorRecycleViewItemClickListener mOnItemClickListener = null;
    private Context mContext = null;
    private String[] names = {"陈寿渊", "郭秀晶", "孔良超", "钱亚凤", "许永岚"};
    private int[] logos = {R.mipmap.doctor_one, R.mipmap.doctor_two, R.mipmap.doctor_three,
            R.mipmap.doctor_four, R.mipmap.doctor_five};
    private String[] name = {"上海东方医院", "上海虹桥医院", "上海江城医院", "上海曙光医院", "上海杨思医院"};
    private List<DoctorBean.DataBean.DoctorListBean> mDoctorList = null;

    public MyOnlineDoctorAdapter(Context context, List<DoctorBean.DataBean.DoctorListBean> doctorList) {
        this.mContext = context;
        this.mDoctorList = doctorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_online_doctor, parent, false);
        inflate.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(mDoctorList.get(position).getName());
        holder.doctorName.setText(mDoctorList.get(position).getName());
//        holder.doctorPhoto.setImageResource(logos[position]);
        holder.appStars.setStar(mDoctorList.get(position).getStarLevel());
        holder.hospitalName.setText(mDoctorList.get(position).getHospitalName());
    }

    @Override
    public int getItemCount() {
        return mDoctorList.size();
    }

    public void setOnItemClickListener(OnlineDoctorRecycleViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick((String) v.getTag());
        }
    }

    public interface OnlineDoctorRecycleViewItemClickListener {
        void onItemClick(String name);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.doctor_photo)
        ImageView doctorPhoto;
        @Bind(R.id.doctor_name)
        TextView doctorName;
        @Bind(R.id.hospital_name)
        TextView hospitalName;
        @Bind(R.id.app_stars)
        MySetRatingBar appStars;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
