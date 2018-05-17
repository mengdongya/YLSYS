package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.view.MySetRatingBar;
import store.chinaotec.com.medicalcare.javabean.HospitalBean;


/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class MyGoHospitalsAdapter extends RecyclerView.Adapter<MyGoHospitalsAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private OnlineHospitalRecycleViewItemClickListener mOnItemClickListener = null;
    private String[] names = {"上海东方医院", "上海虹桥医院", "上海江城医院", "上海曙光医院", "上海杨思医院"};
    private int[] logos = {R.mipmap.hospital_one, R.mipmap.hospital_two, R.mipmap.hospital_three,
            R.mipmap.hospital_four, R.mipmap.hospital_five};

    private List<HospitalBean.DataBean.HospitalListBean> mHospitalList;

    public MyGoHospitalsAdapter(Context context, List<HospitalBean.DataBean.HospitalListBean> hospitalList) {
        this.mContext = context;
        this.mHospitalList = hospitalList;
    }

    public void setOnItemClickListener(OnlineHospitalRecycleViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_go_hospitals, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(mHospitalList.get(position).getName());
        holder.hospitalName.setText(mHospitalList.get(position).getName());
//        holder.hospitalLogo.setImageResource(mHospitalList.get(position).getHeadImage());
        Glide.with(mContext).load(mHospitalList.get(position).getHeadImage()).into(holder.hospitalLogo);
    }

    @Override
    public int getItemCount() {
        return mHospitalList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick((String) v.getTag());
        }
    }

    public interface OnlineHospitalRecycleViewItemClickListener {
        void onItemClick(String name);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.hospital_logo)
        ImageView hospitalLogo;
        @Bind(R.id.app_stars)
        MySetRatingBar appStars;
        @Bind(R.id.distace_data)
        TextView distaceData;
        @Bind(R.id.hospital_name)
        TextView hospitalName;
        @Bind(R.id.reservation_number)
        TextView reservationNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
