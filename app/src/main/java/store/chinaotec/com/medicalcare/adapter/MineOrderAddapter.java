package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class MineOrderAddapter extends RecyclerView.Adapter<MineOrderAddapter.ViewHolder> {
    private Context mContext;

    public MineOrderAddapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_mine_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.delivey_statue)
        TextView deliveyStatue;
        @Bind(R.id.medicine_logo)
        ImageView medicineLogo;
        @Bind(R.id.medicine_name)
        TextView medicineName;
        @Bind(R.id.medicine_price)
        TextView medicinePrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
