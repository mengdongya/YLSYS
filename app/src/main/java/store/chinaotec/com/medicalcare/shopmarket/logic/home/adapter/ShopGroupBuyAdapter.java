package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.ShopGroupBuyAndSeckill;

/**
 * Created by wjc on 2016/8/20 0020.
 */
public class ShopGroupBuyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShopGroupBuyAndSeckill> mDataList;

    public ShopGroupBuyAdapter(Context context, ArrayList<ShopGroupBuyAndSeckill> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_groupbuy_sku, null);
            holder.skuIcon = (ImageView) convertView.findViewById(R.id.iv_shop_group_sku_icon);
            holder.skuName = (TextView) convertView.findViewById(R.id.tv_shop_group_sku_name);
            holder.skuCurrPrice = (TextView) convertView.findViewById(R.id.tv_shop_group_sku_current_price);
            holder.skuOldPrice = (TextView) convertView.findViewById(R.id.tv_shop_group_sku_old_price);
            holder.skuNum = (TextView) convertView.findViewById(R.id.tv_shop_group_sku_num);
            holder.skuLimit = (TextView) convertView.findViewById(R.id.tv_shop_group_sku_limit);
            holder.skuLimitPer = (TextView) convertView.findViewById(R.id.tv_shop_group_sku_limit_per);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.skuName.setText(mDataList.get(position).getSkuName());
        holder.skuCurrPrice.setText("￥" + mDataList.get(position).getSkuTogetherPrice());
        holder.skuOldPrice.setText("参考价:" + mDataList.get(position).getSkuPrice());
        holder.skuNum.setText("已售" + mDataList.get(position).getActualOrderNumber() + "件");
        holder.skuLimit.setText("限量" + mDataList.get(position).getSkuTotalNumber() + "件，每人限购");
        holder.skuLimitPer.setText(mDataList.get(position).getLimitNum() + "");
        String imgUrl = mDataList.get(position).getSkuImgPath();

        if (null != imgUrl && !"".equals(imgUrl)) {
            ImageView imageView = holder.skuIcon;
            MyImageLoader.displayImage(imgUrl, imageView);
        } else {
            holder.skuIcon.setBackgroundResource(R.drawable.error_type);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Holder {
        ImageView skuIcon;
        TextView skuName, skuCurrPrice, skuOldPrice, skuNum, skuLimit, skuLimitPer;
    }
}
