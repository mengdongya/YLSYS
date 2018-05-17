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

/**
 * Created by Administrator on 2017/5/11 0011.
 * 医养都会页面标题适配器
 */

public class MyMedicKindAdapter extends RecyclerView.Adapter<MyMedicKindAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext = null;
    private String[] mMedicalKindName;
    private int[] mMedicalKindLogo;
    private MedicalKindLisener mMedicalKindLisener = null;

    public MyMedicKindAdapter(Context context, String[] medicalKindName, int[] medicalKindLogo) {
        this.mContext = context;
        this.mMedicalKindName = medicalKindName;
        this.mMedicalKindLogo = medicalKindLogo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_medical_kind, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.medicalKindLogo.setImageResource(mMedicalKindLogo[position]);
        holder.medicalKindName.setText(mMedicalKindName[position]);
    }

    @Override
    public int getItemCount() {
        return mMedicalKindLogo.length;
    }

    public void setOnClickLisenerMedicalKind(MedicalKindLisener medicalKindLisener) {
        this.mMedicalKindLisener = medicalKindLisener;
    }

    @Override
    public void onClick(View v) {
        if (mMedicalKindLisener != null) {
            mMedicalKindLisener.itemClick((int) v.getTag());
        }
    }

    public interface MedicalKindLisener {
        void itemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.medical_kind_logo)
        ImageView medicalKindLogo;
        @Bind(R.id.medical_kind_name)
        TextView medicalKindName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
