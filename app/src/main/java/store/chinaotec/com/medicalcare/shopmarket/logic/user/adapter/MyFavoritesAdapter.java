package store.chinaotec.com.medicalcare.shopmarket.logic.user.adapter;

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
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.MyFavorites;

public class MyFavoritesAdapter extends BaseAdapter {
    // private AsyncImageLoader mImageLoader;
    private Context context;
    private ArrayList<MyFavorites> list;
    private OnClickListener clickListener;
    private int status;

    public MyFavoritesAdapter(Context context, ArrayList<MyFavorites> list) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
        this.status = status;

        // mImageLoader = new AsyncImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyFavorites sku = list.get(position);
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_my_favorites,
                    null);
            holder.favorite_img = (ImageView) convertView
                    .findViewById(R.id.favorite_img);
            holder.tv_favorite_name = (TextView) convertView
                    .findViewById(R.id.tv_favorite_name);
//			holder.tv_favorite_color = (TextView) convertView
//					.findViewById(R.id.tv_favorite_color);
//			holder.tv_favorite_size = (TextView) convertView
//					.findViewById(R.id.tv_favorite_size);
            holder.tv_favorite_sku_price = (TextView) convertView
                    .findViewById(R.id.tv_favorite_sku_price);
//			holder.tv_order_sku_quantity = (TextView) convertView.findViewById(R.id.tv_order_sku_quantity);
            holder.tv_collect_time = (TextView) convertView
                    .findViewById(R.id.tv_collect_time);
            holder.btn_check = (ImageView) convertView.findViewById(R.id.btn_check);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_favorite_name.setText(sku.name);
//		holder.tv_favorite_color.setText(sku.color);
//		holder.tv_favorite_size.setText(sku.size);
        holder.tv_favorite_sku_price.setText("￥∶" + sku.currentPrice + "");
        // holder.tv_order_sku_quantity.setText(sku. + "");
        if (sku.addTime != null && !"".equals(sku.addTime)) {
            holder.tv_collect_time.setText("收藏时间:" + sku.addTime);
        }
        String imgUrl = sku.imgPath;

        if (imgUrl == null || imgUrl.equals("")) {
            holder.favorite_img.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.favorite_img;
            MyImageLoader.displayImage(imgUrl, imageView);
        }
        // 点击取消收藏的按钮
        holder.btn_check.setOnClickListener(new OnClickListener() {

            // 取消收藏
            @Override
            public void onClick(View v) {
                // SourceConstant.FAVORITES_SKU_CODE=sku.skuCode;
                // // 发送取消收藏的广播
                // context.sendBroadcast(new Intent(
                // SourceConstant.UN_FAVORITES));
            }
        });
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
        ImageView favorite_img;
        /**
         * 订单中的商品名称
         */
        TextView tv_favorite_name;
        /**
         * 订单中的商品颜色
         */
        TextView tv_favorite_color;
        /**
         * 订单中的商品尺码
         */
        TextView tv_favorite_size;
        /**
         * 订单中的商品价格
         */
        TextView tv_favorite_sku_price;
        /**
         * 订单中的商品数量
         */
        TextView tv_order_sku_quantity;
        /**
         * 订单中的商品数量
         */
        TextView tv_collect_time;
        /**
         * 去评价
         */
        ImageView btn_check;

    }
}
