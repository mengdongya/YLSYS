package store.chinaotec.com.medicalcare.shopmarket.logic.cart.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyChronometer;
import store.chinaotec.com.medicalcare.shopmarket.logic.cart.model.CartItem;

/**
 * 此类描述的是:购物车的适配器
 */
public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CartItem> cartItemDataList;
    private OnClickListener clickListener;
    private SharedPreferences fileNameAli;
    private String alabaoSid;

    public CartAdapter(Context context, ArrayList<CartItem> cartItemDataList) {
        this.context = context;
        this.cartItemDataList = cartItemDataList;
        if (context instanceof OnClickListener) {
            clickListener = (OnClickListener) context;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_cart, null);
            holder.btnCheck = (ImageView) convertView.findViewById(R.id.btn_check);
            holder.btnAdd = (MyChronometer) convertView.findViewById(R.id.btn_add);
            holder.btnMinus = (MyChronometer) convertView.findViewById(R.id.btn_minus);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tvVipPrice = (TextView) convertView.findViewById(R.id.tv_cart_vip_price);
            holder.tvColor = (TextView) convertView.findViewById(R.id.tv_color);
            holder.tvSize = (TextView) convertView.findViewById(R.id.tv_size);
            holder.ll_vip_price = (LinearLayout) convertView.findViewById(R.id.ll_vip_price);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        CartItem cart = (CartItem) getItem(position);
        holder.tvName.setText(cart.skuName);
//		holder.tvType.setText(cart.type);
        holder.tvNum.setText(String.valueOf(cart.quantity));
        holder.tvShopName.setText(cart.storeName);

        holder.tvPrice.setText("￥:" + cart.currentPrice);
//		holder.tvVipPrice.setText("￥:"+cart.priceVip);
//		String vipPrice = cart.priceVip;

//		if ("y".equals(cart.isMemberSku) && !"".equals(alabaoSid)) {
//			holder.tvVipPrice.setText("￥:"+cart.aladingPrice);
//			//holder.tvPrice.setText("￥:"+cart.price);
//			holder.ll_vip_price.setVisibility(View.VISIBLE);
//			holder.tvPrice.setVisibility(View.GONE);
//		}else{
//			holder.tvPrice.setText("￥:"+cart.price);
//			holder.tvPrice.setVisibility(View.VISIBLE);
//			holder.ll_vip_price.setVisibility(View.GONE);
//		}

//		if (!"".equals(vipPrice)) {
//			holder.ll_vip_price.setVisibility(View.VISIBLE);
//			holder.tvPrice.setText("￥:"+vipPrice);
//		}else {
//			holder.ll_vip_price.setVisibility(View.GONE);
//		}

//		holder.tvSize.setText(cart.size);
        String size = cart.size;
        String color = cart.color;
        if (!"".equals(color) && null != color) {
            holder.tvColor.setText(Html.fromHtml(String.format(context.getResources().getString(
                    R.string.evaluate_title_color), color)));
        } else {
            holder.tvColor.setVisibility(View.GONE);
        }
        if (!"".equals(size) && null != size) {
            holder.tvSize.setText(Html.fromHtml(String.format(context.getResources().getString(
                    R.string.evaluate_title_size), size)));
        } else {
            holder.tvSize.setVisibility(View.GONE);
        }

        LogUtil.e("CartActivity", "position：" + position + "cartItem的商品ID：" + cart.skuCode);
        holder.btnCheck.setTag(position);
        holder.btnCheck.setOnClickListener(clickListener);// TODO 这里

        if (cart.isSelected.equals("y")) {
            holder.btnCheck.setImageResource(R.drawable.btn_select_down);
        } else {
            holder.btnCheck.setImageResource(R.drawable.btn_select_up);
        }
        holder.btnAdd.setTag(position);
        holder.btnAdd.setOnClickListener(clickListener);// TODO 这里
        holder.btnAdd.setOnChronometerTickListener((MyChronometer.OnChronometerTickListener) context);
        holder.tvName.setTag(position);
        holder.tvName.setOnClickListener(clickListener);// TODO 这里
        holder.img.setTag(position);
        holder.img.setOnClickListener(clickListener);// TODO 这里

        holder.btnMinus.setTag(position);
        holder.btnMinus.setOnClickListener(clickListener);// TODO 这里
        holder.btnMinus.setOnChronometerTickListener((MyChronometer.OnChronometerTickListener) context);

        String imgUrl = cart.skuImgPath;

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
        return cartItemDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItemDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        public ImageView btnCheck;
        MyChronometer btnAdd, btnMinus;

        ImageView img;
        TextView tvName, tvNum, tvPrice, tvVipPrice, tvColor, tvSize, tvShopName;
        LinearLayout ll_vip_price;
    }


}
