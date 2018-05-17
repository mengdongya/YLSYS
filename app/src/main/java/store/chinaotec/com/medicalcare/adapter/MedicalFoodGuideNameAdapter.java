package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * Created by wjc on 2017/11/22 0022.
 * 膳食指南
 */
public class MedicalFoodGuideNameAdapter extends RecyclerView.Adapter<MedicalFoodGuideNameAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean> mTypeList;
    private int mPosition;
    private MedicalFoodGuideType typeClickListener;
    private MedicalBookBean.DataBean.MedicalTypeListBean dataListBean;

    public MedicalFoodGuideNameAdapter(Context context, List<MedicalBookBean.DataBean.MedicalTypeListBean> typeList){
        this.mContext = context;
        this.mTypeList = typeList;
    }

    public void setIndex(int position){
        mPosition = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text_result_kind, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
        dataListBean = mTypeList.get(position);
        holder.textResultKindName.setText(dataListBean.getTypeName());
        if (mPosition == position){
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_selected);
        }else {
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_normal);
        }
        Glide.with(mContext).load(dataListBean.getTypeImage()).into(holder.textResultKindLogo);
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    public void setOnItemClickListener(MedicalFoodGuideType listener){
        typeClickListener = listener;
    }

    public interface MedicalFoodGuideType{
        void click(int position);
    }

    @Override
    public void onClick(View view) {
        if (typeClickListener != null){
            typeClickListener.click((int) view.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.text_result_kind_logo)
        ImageView textResultKindLogo;
        @Bind(R.id.text_result_kind_name)
        TextView textResultKindName;
        @Bind(R.id.item_text_result)
        LinearLayout itemTextResult;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
