package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;


/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class MyGoHospScreConAdapter extends RecyclerView.Adapter<MyGoHospScreConAdapter.ViewHolder> {

    private Context mContext;

    public MyGoHospScreConAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_screen_contion, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.screenContionsRecycleview.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.screenContionsRecycleview.setAdapter(new MyHospitalFilterAdapter(mContext));
        if (position == 0) {
            holder.linearClearContion.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.screen_contion_title)
        TextView screenContionTitle;
        @Bind(R.id.linear_clear_contion)
        LinearLayout linearClearContion;
        @Bind(R.id.screen_contions_recycleview)
        RecyclerView screenContionsRecycleview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
