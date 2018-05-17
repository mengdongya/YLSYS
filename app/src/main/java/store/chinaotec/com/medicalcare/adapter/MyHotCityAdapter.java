package store.chinaotec.com.medicalcare.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/3/29 0029.
 * 定位城市选中页面,热门城市展示适配器
 */

public class MyHotCityAdapter extends RecyclerView.Adapter<MyHotCityAdapter.ViewHolder> implements View.OnClickListener {
    private String[] mHotCityArray;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MyHotCityAdapter(String[] hotCityArray) {
        this.mHotCityArray = hotCityArray;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick((String) v.getTag());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_city, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.hotCityName.setText(mHotCityArray[position]);
        holder.itemView.setTag(mHotCityArray[position]);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mHotCityArray.length;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(String data);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.hot_city_name)
        TextView hotCityName;
        @Bind(R.id.hot_city_relative)
        RelativeLayout hotCityRelative;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
