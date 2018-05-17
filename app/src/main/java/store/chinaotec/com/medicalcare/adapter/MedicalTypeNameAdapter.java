package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * Created by wjc on 2017/10/20 0020.
 * 图书分类名称
 */
public class MedicalTypeNameAdapter extends RecyclerView.Adapter<MedicalTypeNameAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean> mTypeList;
    private int mPosition;
    private MedicalTypeClickListener typeClickListener;

    public MedicalTypeNameAdapter(Context context, List<MedicalBookBean.DataBean.MedicalTypeListBean> typeList){
        this.mContext = context;
        this.mTypeList = typeList;
    }

    public void setIndex(int position){
        mPosition = position;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_medical_name, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.typeName.setText(mTypeList.get(position).getTypeName());
        holder.itemView.setTag(position);
        if (mPosition == position){
            holder.typeName.setBackgroundResource(R.color.white);
            holder.typeName.setTextColor(mContext.getResources().getColor(R.color.colorTooBar));
        }else {
            holder.typeName.setBackgroundResource(R.color.kind_back);
            holder.typeName.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public void setOnItemClick(MedicalTypeClickListener listener){
        typeClickListener = listener;
    }

    public interface MedicalTypeClickListener{
        void click(int position);
    }

    @Override
    public void onClick(View v) {
        if (typeClickListener != null){
            typeClickListener.click((int) v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_medical_name)
        TextView typeName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
