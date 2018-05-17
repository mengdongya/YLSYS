package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/4/24 0024.
 * 在线医院详情图片适配器
 */

public class MyHospitalRealAdapter extends RecyclerView.Adapter<MyHospitalRealAdapter.ViewHolder> {
    private Context mContext;
    private int[] mHospitalReal;

    public MyHospitalRealAdapter(int[] hospitalReal, Context context) {
        this.mContext = context;
        this.mHospitalReal = hospitalReal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_hospital_real, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.hospitalRealItem.setImageResource(mHospitalReal[position]);
    }

    @Override
    public int getItemCount() {
        return mHospitalReal.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.hospital_real_item)
        ImageView hospitalRealItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
