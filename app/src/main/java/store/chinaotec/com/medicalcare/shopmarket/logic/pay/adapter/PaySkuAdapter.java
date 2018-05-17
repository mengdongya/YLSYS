package store.chinaotec.com.medicalcare.shopmarket.logic.pay.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.pay.model.PaySku;

public class PaySkuAdapter extends BaseAdapter {
    public ViewHolder holder = null;
    protected boolean isAgreeProtocol = false;
    private Context context;
    private ArrayList<PaySku> dataList;
    private OnClickListener clickListener;

    public PaySkuAdapter(Context context, ArrayList<PaySku> dataList) {
        this.context = context;
        this.dataList = dataList;
        if (context instanceof OnClickListener) {
            clickListener = (OnClickListener) context;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_market_pay_order_sure, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img_pay);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name_pay);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price_pay);
            holder.tvColor = (TextView) convertView.findViewById(R.id.tv_color_pay);
            holder.tvSize = (TextView) convertView.findViewById(R.id.tv_size_pay);
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_sku_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PaySku paySku = dataList.get(position);
        // -------------------------------
        holder.tvName.setText(paySku.skuName);
        holder.tvPrice.setText(String.valueOf("￥: " + paySku.currentPrice));
        holder.tvSize.setText(paySku.size);
        holder.tvColor.setText(paySku.color);
        holder.tvNum.setText("数量: " + paySku.quantity);
        LogUtil.e("PaySkuAdapter", "===========" + holder.tvPrice.getText());

        String size = paySku.size;
        String color = paySku.color;
        if (!"".equals(color) && null != color) {
            holder.tvColor.setText(Html.fromHtml(String.format(
                    context.getResources().getString(
                            R.string.evaluate_title_color), color)));
        } else {
            holder.tvColor.setVisibility(View.GONE);
        }
        if (!"".equals(size) && null != size) {
            holder.tvSize.setText(Html.fromHtml(String.format(
                    context.getResources().getString(
                            R.string.evaluate_title_size), size)));
        } else {
            holder.tvSize.setVisibility(View.GONE);
        }

        String imgUrl = paySku.skuImgPath;

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

    public class ViewHolder {
        public TextView tvName, tvType, tvPrice, tvSize, tvColor, tvNum;
        ImageView img;

    }
}