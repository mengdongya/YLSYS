package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.ContaAddActivity;
import store.chinaotec.com.medicalcare.javabean.OneKeyPersBean;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class MyOneKeyCallAdapter extends RecyclerView.Adapter<MyOneKeyCallAdapter.ViewHolder>{
    private Context mContext = null;
    private List<OneKeyPersBean.DataBean.MemberCalloutListBean> calloutList = null;

    public MyOneKeyCallAdapter(Context context, List<OneKeyPersBean.DataBean.MemberCalloutListBean> memberCalloutList) {
        this.mContext = context;
        this.calloutList = memberCalloutList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_contacts, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.contactName.setText(calloutList.get(position).getCalloutName());
        holder.contactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ContaAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact",calloutList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.contactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return calloutList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.contact_name)
        TextView contactName;
        @Bind(R.id.tv_contact_edit)
        TextView contactEdit;
        @Bind(R.id.tv_contact_delete)
        TextView contactDelete;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
