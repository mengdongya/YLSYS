package store.chinaotec.com.medicalcare.shopmarket.logic.pay.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.GotoOrderShops;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.PaySku;

public class PayShopSkuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GotoOrderShops> orderShopslist;
    private OnClickListener clickListener;
    private ViewHolder viewHolder;
    private GotoOrderShops orderShops;

    public PayShopSkuAdapter(Context context, ArrayList<GotoOrderShops> orderShopslist) {
        this.context = context;
        this.orderShopslist = orderShopslist;
        if (context instanceof OnClickListener) {
            clickListener = (OnClickListener) context;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop_market_order_shops, null);

            viewHolder.tv_order_shop_name = (TextView) convertView.findViewById(R.id.tv_order_shop_name);
            viewHolder.tv_order_line_free = (TextView) convertView.findViewById(R.id.tv_order_line_free);
            viewHolder.tv_order_line_totalnum = (TextView) convertView.findViewById(R.id.tv_order_line_totalnum);
            viewHolder.tv_order_line_totalprice = (TextView) convertView.findViewById(R.id.tv_order_line_totalprice);
            viewHolder.tv_free_freight = (TextView) convertView.findViewById(R.id.tv_free_freight);
            viewHolder.tv_coupon_count = (TextView) convertView.findViewById(R.id.tv_coupon_count);
            viewHolder.iv_shop_icon = (ImageView) convertView.findViewById(R.id.iv_shop_icon);
            viewHolder.tv_use_coupon_money = (TextView) convertView.findViewById(R.id.tv_use_coupon_money);
            viewHolder.rl_use_coupon = (RelativeLayout) convertView.findViewById(R.id.rl_use_coupon);
            viewHolder.lv_item_line = (MyScrollListView) convertView.findViewById(R.id.lv_order_shops_list_item);
            viewHolder.ed_memo = (EditText) convertView.findViewById(R.id.ed_input_shop_goto_order_memo);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        orderShops = orderShopslist.get(position);

        viewHolder.tv_order_shop_name.setText(orderShops.getGotoOrderStoreSummaryDto().getStoreName());
        viewHolder.tv_order_line_free.setText(orderShops.getGotoOrderStoreSummaryDto().getFreight());
        viewHolder.tv_order_line_totalprice.setText("￥" + orderShops.getGotoOrderStoreSummaryDto().getSkuTotal());
        viewHolder.tv_order_line_totalnum.setText("共" + orderShops.getGotoOrderStoreSummaryDto().getQuantityTotal() + "件商品");

        if ("999".equals(orderShops.getGotoOrderStoreSummaryDto().getStoreCode())) {
            viewHolder.iv_shop_icon.setImageResource(R.drawable.alading_sku_icon);
        } else {
            viewHolder.iv_shop_icon.setImageResource(R.drawable.icon_qjd_logo);
        }

        viewHolder.ed_memo.setTag(orderShops);
//        viewHolder.ed_memo.setOnClickListener(clickListener);

        //清除焦点
        viewHolder.ed_memo.clearFocus();

        viewHolder.ed_memo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获得Edittext所在position里面的Bean，并设置数据
                GotoOrderShops bean = (GotoOrderShops) viewHolder.ed_memo.getTag();
                bean.setMemo(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //大部分情况下，Adapter里面有if必须有else
        if (!TextUtils.isEmpty(orderShops.getMemo())) {
            viewHolder.ed_memo.setText(orderShops.getMemo());
        } else {
            viewHolder.ed_memo.setText("");
        }

        ArrayList<PaySku> orderLineItems = orderShops.getOrderLineItems();
        PaySkuAdapter paySkuAdapter = new PaySkuAdapter(context, orderLineItems);
        viewHolder.lv_item_line.setAdapter(paySkuAdapter);

        return convertView;
    }

    @Override
    public int getCount() {
        return orderShopslist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderShopslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView tv_order_shop_name;
        TextView tv_order_line_free;
        TextView tv_order_line_totalnum, tv_free_freight;
        TextView tv_order_line_totalprice, tv_coupon_count, tv_use_coupon_money;
        MyScrollListView lv_item_line;
        RelativeLayout rl_use_coupon;
        ImageView iv_shop_icon;
        EditText ed_memo;
    }

}
