package store.chinaotec.com.medicalcare.shopmarket.logic.shops.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.Category;

/**
 * Created by wjc on 2017/3/16 0016.
 */
public class TypeNameAdapter extends BaseAdapter {
    private Context context;
    private List<Category> data;
    private int index = 0;
    private Resources resource;

    public TypeNameAdapter(Context context, List<Category> skuTypeBean) {
        this.context = context;
        this.data = skuTypeBean;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop_market_type, null);
            holder.item_tv = (TextView) convertView.findViewById(R.id.tv_type_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String name = data.get(position).getName();
        if (position != index) {
            resource = (Resources) context.getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.line_gary);
            if (csl != null) {
                holder.item_tv.setTextColor(csl);
            }
            holder.item_tv.setBackgroundColor(Color.parseColor("#F8F8F8"));
        } else {
            resource = (Resources) context.getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.black1);
            if (csl != null) {
                holder.item_tv.setTextColor(csl);
            }
            holder.item_tv.setBackgroundColor(Color.WHITE);
        }
        holder.item_tv.setText(name);
        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView item_tv;
    }
}
