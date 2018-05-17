package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

public class HorizontalListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sku> dataList;
    private int width;
    private int total_number_word = 0;
    private int pieceNumbers = 0;
    private String TAG = "HorizontalListViewAdapter";

    public HorizontalListViewAdapter(Context context, ArrayList<Sku> dataList) {
        this.context = context;
        this.dataList = dataList;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            // convertView =
            // LayoutInflater.from(context).inflate(R.layout.item_commodity,
            // null);
            convertView = View.inflate(context, R.layout.item_shop_market_guess_like_sku, null);
            // convertView.setLayoutParams(new AbsListView.LayoutParams(width /
            // 2,
            // width / 2));
            holder.img = (ImageView) convertView.findViewById(R.id.img_like);
            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_title_like);
            holder.price = (TextView) convertView
                    .findViewById(R.id.tv_price_like);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String imgUrl = dataList.get(position).imgPath;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.img.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.img;
            MyImageLoader.displayImage(imgUrl, imageView);
        }
        holder.name.setText(dataList.get(position).skuName);
        holder.price.setText("￥ " + dataList.get(position).currentPrice);
        return convertView;
    }

    private static class Holder {
        ImageView img;
        TextView name, price;
    }

}