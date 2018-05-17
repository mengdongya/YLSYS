package store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.HorizontalListView;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.OrderInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.OrderUser;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;

/**
 * 订单列表的适配器
 */
public class OrderAdapter extends BaseAdapter {
    public static Holder holder;
    private Context context;
    private ArrayList<OrderUser> mOrderUser;
    private OnClickListener clickListener;
    private String TAG = "OrderAdapter";
    private boolean skuIsComment = true;

    public OrderAdapter(Context context, ArrayList<OrderUser> mOrderUser) {
        this.context = context;
        this.mOrderUser = mOrderUser;
        if (context instanceof OnClickListener) {
            clickListener = (OnClickListener) context;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final OrderUser order = (OrderUser) getItem(position);
        holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_market_order_info, null);

            holder.tv_bt1 = (Button) convertView.findViewById(R.id.tv_bt1);
            holder.tv_bt2 = (Button) convertView.findViewById(R.id.tv_bt2);
            holder.tv_sku_price = (TextView) convertView.findViewById(R.id.tv_sku_price);
            holder.tv_creat_order_time = (TextView) convertView.findViewById(R.id.tv_creat_order_time);
//			holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            holder.iv_shop_logo = (ImageView) convertView.findViewById(R.id.iv_shop_logo);
            holder.lv_order_item = (MyScrollListView) convertView.findViewById(R.id.lv_order_item);
            holder.hor_lv_order_item = (HorizontalListView) convertView.findViewById(R.id.gv_order_item);
            holder.ll_gv_order_item = (LinearLayout) convertView.findViewById(R.id.ll_gv_order_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        for (int i = 0; i < order.getSkuByOrderLines().size(); i++) {
            SkuByOrderLine skuByOrderLine = order.getSkuByOrderLines().get(i);
            if ("n".equals(skuByOrderLine.getIsComment())) {
                skuIsComment = false;
            } else {
                skuIsComment = true;
            }
        }
        int orderStatus = Integer.parseInt(order.getOrderStatus());

        switch (orderStatus) {
            // 1代表待付款
            case 1:
                holder.tv_bt1.setText("取消订单");
                holder.tv_bt2.setText("付款");
                holder.tv_bt1.setVisibility(View.VISIBLE);
                holder.tv_bt1.setTag(position);
                holder.tv_bt1.setOnClickListener(clickListener);
                holder.tv_bt2.setTag(position);
                holder.tv_bt2.setOnClickListener(clickListener);

                break;
            // 2代表审核中
            case 2:
                break;
            // 3代表待发货
            case 3:
                holder.tv_bt1.setVisibility(View.GONE);
                holder.tv_bt2.setText(String.valueOf("提醒发货"));
                holder.tv_bt2.setTag(position);
                holder.tv_bt2.setOnClickListener(clickListener);
                break;
            // 4 审核失败，请联系客服 暂不能删除
            case 4:
                break;
            // 5，7待收货
            case 5:
            case 7:
                holder.tv_bt1.setText(String.valueOf("查看物流"));
                holder.tv_bt1.setTag(position);
                holder.tv_bt1.setOnClickListener(clickListener);
                holder.tv_bt2.setText(String.valueOf("确认收货"));
                holder.tv_bt2.setTag(position);
                holder.tv_bt2.setOnClickListener(clickListener);
                break;
            // 6 交易完成 能评价 能删除
            case 6:
            case 8:
                if (skuIsComment) {
                    holder.tv_bt1.setVisibility(View.GONE);
                } else {
                    holder.tv_bt1.setVisibility(View.VISIBLE);
                    holder.tv_bt1.setText(String.valueOf("去评价"));
                    holder.tv_bt1.setTag(position);
                    holder.tv_bt1.setOnClickListener(clickListener);
                }
                holder.tv_bt2.setText(String.valueOf("删除订单"));
                holder.tv_bt2.setTag(position);
                holder.tv_bt2.setOnClickListener(clickListener);
                break;
            // 9 会员取消订单 订单已取消 能删除
            case 9:
                // 10 系统取消订单 订单已取消 能删除
            case 11:
            case 10:
                // 12 已退款 订单已取消 能删除
            case 12:
                holder.tv_bt1.setVisibility(View.GONE);
                holder.tv_bt2.setText(String.valueOf("删除订单"));
                holder.tv_bt2.setTag(position);
                holder.tv_bt2.setOnClickListener(clickListener);
                break;
        }
        ArrayList<SkuByOrderLine> skuByOrderLines = mOrderUser.get(position)
                .getSkuByOrderLines();

        holder.tv_sku_price.setText(order.getPaymentTotalActual() + "");

        if ("999".equals(mOrderUser.get(position).getStoreCode())) {
            holder.iv_shop_logo.setImageResource(R.drawable.alading_sku_icon);
        } else {
            holder.iv_shop_logo.setImageResource(R.drawable.icon_qjd_logo);
        }
        holder.tv_creat_order_time.setText(order.getStoreName());

//		holder.tv_creat_order_time.setText("下单时间: "+order.getCreateOrderTime());
//		int quantity = 0;
//		for (int i = 0; i < skuByOrderLines.size(); i++) {
//			quantity += skuByOrderLines.get(i).getQuantity();
//		}
//		holder.tv_quantity.setText(Html.fromHtml(String.format(context
//				.getResources().getString(R.string.order_tv_order_max),
//				quantity)));

        if (skuByOrderLines.size() > 1) {
            holder.lv_order_item.setVisibility(View.GONE);
            holder.ll_gv_order_item.setVisibility(View.VISIBLE);
            holder.hor_lv_order_item.setAdapter(new OrserHorizontalListViewAdapter(context, skuByOrderLines));
            holder.hor_lv_order_item.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // SourceConstant.orderUser = order;
                    LogUtil.e(TAG, "adapter============" + order.toString());
                    Intent intent = new Intent(context, OrderInfoActivity.class);
                    intent.putExtra("order_code", order.getOrderCode());
                    context.startActivity(intent);
                }
            });
        } else {
            holder.ll_gv_order_item.setVisibility(View.GONE);
            holder.lv_order_item.setVisibility(View.VISIBLE);
            holder.lv_order_item.setAdapter(new OrderInfoAdapter(context,
                    skuByOrderLines));
            holder.lv_order_item.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // SourceConstant.orderUser = order;
                    LogUtil.e(TAG, "adapter============" + order.toString());
                    Intent intent = new Intent(context, OrderInfoActivity.class);
                    intent.putExtra("order_code", order.getOrderCode());
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mOrderUser.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        /**
         * 按钮1
         */
        Button tv_bt1;
        /**
         * 按钮2
         */
        Button tv_bt2;
        /**
         * 下单时间
         */
        TextView tv_creat_order_time;
        /**
         * 商品价格
         */
        TextView tv_sku_price;
        /**
         * 商品数量
         */
        TextView tv_quantity;
        /**
         * 该订单下面的商品列表的listView
         */
        MyScrollListView lv_order_item;
        /**
         * 该订单下面的商品列表的listView
         */
        HorizontalListView hor_lv_order_item;
        LinearLayout ll_gv_order_item;
        ImageView iv_shop_logo;
    }

}
