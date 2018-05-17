package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.SuddenDiseBean;

/**
 * Created by Administrator on 2017/3/30 0030.
 * 突发伤病分类展示适配器
 */

public class MySubClassDetAdapter extends RecyclerView.Adapter<MySubClassDetAdapter.ViewHolder> implements View.OnClickListener {
    private SuddenTreatLisener mSuddenTreatLisener = null;
    private SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean mDataListBean;
    private Context mContext;

    public MySubClassDetAdapter(Context context, SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean dataListBean) {
        this.mContext = context;
        this.mDataListBean = dataListBean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sudden_diease_kind, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.suddenDiaeaseName.setText(mDataListBean.getMemberSickDealList().get(position).getName());
        Glide.with(mContext).load(mDataListBean.getMemberSickDealList().get(position).getImage()).into(holder.suddenDiaeaseLogo);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataListBean.getMemberSickDealList().size();
    }

    public void setOnClickSuddenTreatListener(SuddenTreatLisener suddenTreatListener) {
        this.mSuddenTreatLisener = suddenTreatListener;
    }

    @Override
    public void onClick(View v) {
        if (mSuddenTreatLisener != null) {
            mSuddenTreatLisener.suddenTreatClick((int) v.getTag());
        }
    }

    public interface SuddenTreatLisener {
        void suddenTreatClick(int disease);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.sudden_diaease_logo)
        ImageView suddenDiaeaseLogo;
        @Bind(R.id.sudden_diaease_name)
        TextView suddenDiaeaseName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
