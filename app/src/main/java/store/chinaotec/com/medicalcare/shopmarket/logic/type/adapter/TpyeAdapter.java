package store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.TypeEntity;

/**
 * 分类 适配器
 */
public class TpyeAdapter extends BaseAdapter {

    private Context context;
    // private String packageName;
    private ArrayList<TypeEntity> dataList;

    public TpyeAdapter(Context context, ArrayList<TypeEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
        // packageName = context.getPackageName();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop_market_type, null);
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = getItem(position).getNickName();
        holder.mTvName.setText(name);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public TypeEntity getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    class ViewHolder {
        TextView mTvName;
    }

}
