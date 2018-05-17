package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.BankBean;

/**
 * Created by hxk on 2017/7/7 0007 15:09
 */

public class MyBlankAdapter extends RecyclerView.Adapter<MyBlankAdapter.ViewHolder> implements View.OnClickListener {
    private ItemClikListener mItemClikListener;
    private List<BankBean.DataBean> mData;
    private Context mContext;

    public MyBlankAdapter(List<BankBean.DataBean> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.item_blank, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(mData.get(position));

        holder.blankName.setText(mData.get(position).getBankName());
        Glide.with(mContext).load(mData.get(position).getBankIconUrl()).into(holder.blankLogo);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickItemListener(ItemClikListener itemListener) {
        this.mItemClikListener = itemListener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClikListener != null) {
            mItemClikListener.clickItem((BankBean.DataBean) v.getTag());
        }
    }

    public interface ItemClikListener {
        void clickItem(BankBean.DataBean dataBean);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.blank_logo)
        ImageView blankLogo;
        @Bind(R.id.blank_name)
        TextView blankName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
