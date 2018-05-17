package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.SelectModel;

public class SelectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SelectModel> dataList;
    private int layoutId;

    private int selectIndex = -1;

    public SelectAdapter(Context context, ArrayList<SelectModel> dataList,
                         int layoutId) {
        this.context = context;
        this.dataList = dataList;
        this.layoutId = layoutId;

    }


    public void setDataList(ArrayList<SelectModel> dataList) {
        this.dataList = dataList;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_market_select_sku, null);
            holder.content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        int flag = Integer.valueOf(dataList.get(position).flag);
        switch (flag) {
            case 1:

                holder.content.setBackgroundResource(R.drawable.shop_market_rect_red);
                holder.content.setTextColor(context.getResources().getColor(
                        R.color.white));
                break;
            case 2:
                holder.content.setBackgroundResource(R.drawable.shop_market_rect_gray);
                holder.content.setTextColor(context.getResources().getColor(
                        R.color.edit_hint_color));
                break;

            default:
                break;
        }
        holder.content.setText(dataList.get(position).content);

        final TextView textView = holder.content;

        return convertView;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    class Holder {
        TextView content;
    }

}
