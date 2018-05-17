package store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;

public class OrderInfoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SkuByOrderLine> list;
    private OnClickListener clickListener;
    private int status;

    public OrderInfoAdapter(Context context, ArrayList<SkuByOrderLine> list) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
        this.status = status;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SkuByOrderLine sku = list.get(position);
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_order, null);
            holder.iv_order_sku_img = (ImageView) convertView
                    .findViewById(R.id.iv_order_sku_img);
            holder.tv_order_sku_name = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_name);
            holder.tv_order_sku_price = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_price);
            holder.tv_order_sku_quantity = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_quantity);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_order_sku_name.setText(sku.getSkuName());
        holder.tv_order_sku_price.setText("￥:" + sku.getSkuPrice() + "");
        holder.tv_order_sku_quantity.setText(sku.getQuantity() + "");
        String imgUrl = sku.getSkuImg();

        if (imgUrl == null || imgUrl.equals("")) {
            holder.iv_order_sku_img.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.iv_order_sku_img;
            MyImageLoader.displayImage(imgUrl, imageView);
        }
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
        /**
         * 订单中的商品图片
         */
        ImageView iv_order_sku_img;
        /**
         * 订单中的商品名称
         */
        TextView tv_order_sku_name;
        /**
         * 订单中的商品价格
         */
        TextView tv_order_sku_price;
        /**
         * 订单中的商品数量
         */
        TextView tv_order_sku_quantity;
    }

}
