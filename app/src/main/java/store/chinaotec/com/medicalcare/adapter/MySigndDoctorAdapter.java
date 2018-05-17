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
import store.chinaotec.com.medicalcare.view.MySetRatingBar;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class MySigndDoctorAdapter extends RecyclerView.Adapter<MySigndDoctorAdapter.ViewHolder> implements View.OnClickListener {
    private SignedDoctorItemClickListener mSignedDoctorItemClickListener = null;
    private String[] names = {"陈寿渊", "郭秀晶", "孔良超", "钱亚凤", "许永岚"};
    private int[] logos = {R.mipmap.doctor_one, R.mipmap.doctor_two, R.mipmap.doctor_three, R.mipmap.doctor_four, R.mipmap.doctor_five};
    private Context context;

    public MySigndDoctorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singed_doctor, parent, false);
        inflate.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(names[position]);
        holder.signDoctorName.setText(names[position]);
        holder.signDoctorPhoto.setImageResource(logos[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setOnItemClickListener(SignedDoctorItemClickListener signedDoctorItemClickListener) {
        this.mSignedDoctorItemClickListener = signedDoctorItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mSignedDoctorItemClickListener != null) {
            mSignedDoctorItemClickListener.onItemClick((String) v.getTag());
        }
    }

    public interface SignedDoctorItemClickListener {
        void onItemClick(String name);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sign_doctor_photo)
        ImageView signDoctorPhoto;
        @Bind(R.id.sign_doctor_name)
        TextView signDoctorName;
        @Bind(R.id.sign_doctor_distance)
        TextView signDoctorDistance;
        @Bind(R.id.sign_hospital_name)
        TextView signHospitalName;
        @Bind(R.id.sign_advisory_number)
        TextView signAdvisoryNumber;
        @Bind(R.id.sign_doctor_stars)
        MySetRatingBar signDoctorStars;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
