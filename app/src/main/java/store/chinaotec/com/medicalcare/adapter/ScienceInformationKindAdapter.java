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
import store.chinaotec.com.medicalcare.activity.ScienceInformationActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by seven
 * on 2018/2/2 0002.
 */
public class ScienceInformationKindAdapter extends RecyclerView.Adapter<ScienceInformationKindAdapter.ViewHolder> implements View.OnClickListener {
    private final Context context;
    private List<MedicalBookBean.DataBean.MedicalTypeListBean> typeList;
    private int mPosition = 1;
    private ScienceInformationKind typeClickListener;

    public ScienceInformationKindAdapter(Context context, List<MedicalBookBean.DataBean.MedicalTypeListBean> medicalBookBean) {
        this.context = context;
        this.typeList = medicalBookBean;
    }

    public void setIndex(int position){
        mPosition = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_result_kind,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position + 1);
        MedicalBookBean.DataBean.MedicalTypeListBean listBean = typeList.get(position + 1);
        holder.textResultKindName.setText(listBean.getTypeName());
        Glide.with(context).load(listBean.getTypeImage()).into(holder.textResultKindLogo);

        if (mPosition == position + 1){
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_selected);
        }else {
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_normal);
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size() - 1;
    }

    public void setOnItemClickListener(ScienceInformationKind listener){
        typeClickListener = listener;
    }

    public interface ScienceInformationKind{
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
