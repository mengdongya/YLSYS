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

public class MySignedNurseAdapter extends RecyclerView.Adapter<MySignedNurseAdapter.ViewHolder> implements View.OnClickListener {

    private OnSignedNurseClickListener mOnSignedNurseClickListener = null;
    private Context mContext = null;
    private String[] names = {"许永岚", "钱亚凤", "孔良超", "郭秀晶", "陈寿渊"};
    private int[] logos = {R.mipmap.doctor_five, R.mipmap.doctor_four, R.mipmap.doctor_three, R.mipmap.doctor_two, R.mipmap.doctor_one};

    public MySignedNurseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_signed_nurse, parent, false);
        inflate.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.signNurseName.setText(names[position]);
        holder.signNursePhoto.setImageResource(logos[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setOnClickSignNurseListener(OnSignedNurseClickListener onSignedNurseClickListener) {
        this.mOnSignedNurseClickListener = onSignedNurseClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mOnSignedNurseClickListener != null) {
            mOnSignedNurseClickListener.onItemClick();
        }
    }

    public interface OnSignedNurseClickListener {
        void onItemClick();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sign_nurse_photo)
        ImageView signNursePhoto;
        @Bind(R.id.sign_nurse_name)
        TextView signNurseName;
        @Bind(R.id.sign_nurse_hospital_name)
        TextView signNurseHospitalName;
        @Bind(R.id.sign_nurse_hospital_stars)
        MySetRatingBar signNurseHospitalStars;
        @Bind(R.id.sign_nurse_advisory_number)
        TextView signNurseAdvisoryNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
