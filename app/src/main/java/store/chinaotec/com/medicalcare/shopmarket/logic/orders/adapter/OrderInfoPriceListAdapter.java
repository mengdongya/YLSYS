package store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.AppDetailStr;

public class OrderInfoPriceListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AppDetailStr> list;

    public OrderInfoPriceListAdapter(Context context, ArrayList<AppDetailStr> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_order_info_price_list, null);
            holder.tv_orderinfo_price_name = (TextView) convertView.findViewById(R.id.tv_orderinfo_price_name);
            holder.tv_orderinfo_price_value = (TextView) convertView.findViewById(R.id.tv_orderinfo_price_value);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv_orderinfo_price_name.setText(list.get(position).getKey());
        holder.tv_orderinfo_price_value.setText(list.get(position).getValues());

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        TextView tv_orderinfo_price_name;
        TextView tv_orderinfo_price_value;
    }
}
