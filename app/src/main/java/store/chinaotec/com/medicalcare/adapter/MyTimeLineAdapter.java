package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.PatientBean;

/**
 * Created by Administrator on 2017/12/1 0001.
 * 慢性病模块病程记录时间轴适配器
 */

public class MyTimeLineAdapter extends RecyclerView.Adapter<MyTimeLineAdapter.ViewHolder> {

    private Context context;
    List<PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean.DiseaseRecordDtosBean> diseaseRecordDtos;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public MyTimeLineAdapter(Context context, List<PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean.DiseaseRecordDtosBean> diseaseRecordDtos) {
        this.context = context;
        this.diseaseRecordDtos = diseaseRecordDtos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_time_line, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.line.getLayoutParams();
            layoutParams.setMargins(0, 2, 0, 0);
        }
        holder.checkDate.setText(diseaseRecordDtos.get(position).getCreateTime());
        holder.triglYcerides.setText(diseaseRecordDtos.get(position).getBloodFatTg());
        holder.lowDensityLipoprotein.setText(diseaseRecordDtos.get(position).getBloodFatLdl());
        holder.highDensityLipoprotein.setText(diseaseRecordDtos.get(position).getBloodFatHdl());
        holder.checkPeriod.setText(diseaseRecordDtos.get(position).getFundoscopy());
        holder.conditionDescription.setText(diseaseRecordDtos.get(position).getDiseaseDesc());

        if (mOnItemClickListener != null) {
            holder.llItemTimeLineView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return diseaseRecordDtos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_item_time_line_view)
        LinearLayout llItemTimeLineView;
        @Bind(R.id.check_date)
        TextView checkDate;
        @Bind(R.id.dots)
        View dots;
        @Bind(R.id.line)
        View line;
        @Bind(R.id.trigl_ycerides)
        TextView triglYcerides;
        @Bind(R.id.low_density_lipoprotein)
        TextView lowDensityLipoprotein;
        @Bind(R.id.high_density_lipoprotein)
        TextView highDensityLipoprotein;
        @Bind(R.id.check_period)
        TextView checkPeriod;
        @Bind(R.id.condition_description)
        TextView conditionDescription;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
