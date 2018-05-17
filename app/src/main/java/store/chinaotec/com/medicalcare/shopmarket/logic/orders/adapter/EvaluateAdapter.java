package store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.OrderEvaluateActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;

public class EvaluateAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SkuByOrderLine> list;
    private OnClickListener clickListener;
    private int status;

    public EvaluateAdapter(Context context, ArrayList<SkuByOrderLine> list) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
        this.status = status;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SkuByOrderLine sku = list.get(position);
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_order_evaluate,
                    null);
            holder.iv_order_sku_img = (ImageView) convertView
                    .findViewById(R.id.iv_order_sku_img_evaluate);
            holder.tv_order_sku_name = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_name_evaluate);
            holder.tv_order_sku_color = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_color_evaluate);
            holder.tv_order_sku_size = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_size_evaluate);
            holder.tv_order_sku_price = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_price_evaluate);
            holder.tv_order_sku_quantity = (TextView) convertView
                    .findViewById(R.id.tv_order_sku_quantity_evaluate);
            holder.tv_bt1_evaluate = (Button) convertView
                    .findViewById(R.id.tv_bt1_evaluate);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_order_sku_name.setText(sku.getSkuName());
        if (null != sku.getSkuColor() && !"".equals(sku.getSkuColor())) {
            holder.tv_order_sku_color.setText(Html.fromHtml(String.format(
                    context.getResources().getString(R.string.evaluate_title_color), sku.getSkuColor())));
        } else {
            holder.tv_order_sku_color.setVisibility(View.GONE);
        }
        if (null != sku.getSkuSize() && !"".equals(sku.getSkuSize())) {
            holder.tv_order_sku_size.setText(Html.fromHtml(String.format(
                    context.getResources().getString(
                            R.string.evaluate_title_size), sku.getSkuSize())));
        } else {
            holder.tv_order_sku_size.setVisibility(View.GONE);
        }

        holder.tv_order_sku_price.setText(Html.fromHtml(String.format(
                context.getResources().getString(R.string.evaluate_title_price), sku.getSkuPrice() + "")));
        holder.tv_order_sku_quantity.setText(sku.getQuantity() + "");
        String imgUrl = sku.getSkuImg();

        if (imgUrl == null || imgUrl.equals("")) {
            holder.iv_order_sku_img.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.iv_order_sku_img;
            MyImageLoader.displayImage(imgUrl, imageView);
        }
        // 点击了去评价的按钮
        holder.tv_bt1_evaluate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SourceConstant.skuByOrder = sku;
                context.startActivity(new Intent(context, OrderEvaluateActivity.class));
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
        ImageView iv_order_sku_img;
        /**
         * 订单中的商品名称
         */
        TextView tv_order_sku_name;
        /**
         * 订单中的商品颜色
         */
        TextView tv_order_sku_color;
        /**
         * 订单中的商品尺码
         */
        TextView tv_order_sku_size;
        /**
         * 订单中的商品价格
         */
        TextView tv_order_sku_price;
        /**
         * 订单中的商品数量
         */
        TextView tv_order_sku_quantity;
        /**
         * 去评价
         */
        Button tv_bt1_evaluate;

    }

}
