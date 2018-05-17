package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
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
import store.chinaotec.com.medicalcare.activity.MedicalBookDetailActivity;
import store.chinaotec.com.medicalcare.activity.MedicalBookIntroduceActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;

/**
 * Created by wjc on 2017/10/20 0020.
 */
public class MedicalTypeContentLevelAdapter extends RecyclerView.Adapter<MedicalTypeContentLevelAdapter.ViewHolder>{


    private Context mContext;
    private MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean mDataListBean;

    public MedicalTypeContentLevelAdapter(Context context, MedicalBookBean.DataBean.MedicalTypeListBean.DataListBean dataListBean) {
        mContext = context;
        mDataListBean = dataListBean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_medical_book, parent, false);
//        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bookName.setText(mDataListBean.getMedicalBookList().get(position).getName());
        Glide.with(mContext).load(mDataListBean.getMedicalBookList().get(position).getImage()).into(holder.bookImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(mContext,MedicalBookDetailActivity.class);
//                intent.putExtra("medical_book_name",mDataListBean.getMedicalBookList().get(position).getName());
//                intent.putExtra("medical_book_url",mDataListBean.getMedicalBookList().get(position).getUrl());
                Intent intent = new Intent(mContext,MedicalBookIntroduceActivity.class);
                intent.putExtra("bookId",mDataListBean.getMedicalBookList().get(position).getMemberSickDealId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataListBean.getMedicalBookList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_book_img)
        ImageView bookImg;
        @Bind(R.id.tv_book_name)
        TextView bookName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
