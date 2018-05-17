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
import store.chinaotec.com.medicalcare.activity.CheckResultInterpretActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by seven on 2017/12/1 0001.
 */
public class CheckResultKindAdapter extends RecyclerView.Adapter<CheckResultKindAdapter.ViewHolder> implements View.OnClickListener {

    private final Context context;
    private final List<TextResultBean.DataBean.MedicalTypeListBean> typeList;
    private int mPosition;
    private CheckResultKind typeClickListener;

    public CheckResultKindAdapter(Context context, List<TextResultBean.DataBean.MedicalTypeListBean> typeList) {
        this.context = context;
        this.typeList = typeList;
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
        holder.itemView.setTag(position);
        TextResultBean.DataBean.MedicalTypeListBean listBean = typeList.get(position);
        holder.textResultKindName.setText(listBean.getTypeName());
        Glide.with(context).load(listBean.getTypeImage()).into(holder.textResultKindLogo);
        if (mPosition == position){
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_selected);
        }else {
            holder.itemTextResult.setBackgroundResource(R.mipmap.dise_kind_item_normal);
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
    public void setOnItemClickListener(CheckResultKind listener){
        typeClickListener = listener;
    }

    public interface CheckResultKind{
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
