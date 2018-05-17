package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

/**
 * 商品列表适配器
 */
public class SearchSkuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Sku> dataList;
    private int width;
    private SharedPreferences fileNameAli;
    private String storeCode;

    public SearchSkuAdapter(Context context, ArrayList<Sku> dataList, int width) {
        this.context = context;
        this.dataList = dataList;
        this.width = width;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            // convertView = LayoutInflater.from(context).inflate(R.layout.item_commodity, null);
            convertView = View.inflate(context, R.layout.item_shop_market_sku_list, null);
//			convertView.setLayoutParams(new AbsListView.LayoutParams(width / 2, width / 2));
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_sku_selfSell = (TextView) convertView.findViewById(R.id.tv_sku_selfSell);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Sku sku = dataList.get(position);
        holder.name.setText(sku.skuName);
        holder.price.setText("价格：￥ " + sku.skuPrice);

        String imgUrl = dataList.get(position).skuImg;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.img.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.img;
            MyImageLoader.displayImage(imgUrl, imageView);
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

    class Holder {
        TextView name, price, tv_old_price, tv_sku_selfSell;
        ImageView img;
    }
}
