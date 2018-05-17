package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.CustomUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

/**
 * 商品列表适配器
 */
public class CommodityAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sku> dataList;
    private int width;

    public CommodityAdapter(Context context, ArrayList<Sku> dataList, int width) {
        this.context = context;
        this.dataList = dataList;
        this.width = (width - CustomUtils.dip2px(context, 10)) / 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            // convertView =
            // LayoutInflater.from(context).inflate(R.layout.item_commodity,
            // null);
            convertView = View.inflate(context, R.layout.item_shop_market_main_like, null);
            holder.img = (ImageView) convertView.findViewById(R.id.image);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.img.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.name.setText(dataList.get(position).name);
        holder.price.setText("￥ " + dataList.get(position).currentPrice);

        String imgUrl = dataList.get(position).imgPath;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.img.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.img;
            MyImageLoader.displayImage(imgUrl, imageView, R.drawable.error_type, R.drawable.error_type);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Holder {
        TextView name, price;
        ImageView img;
    }
}
