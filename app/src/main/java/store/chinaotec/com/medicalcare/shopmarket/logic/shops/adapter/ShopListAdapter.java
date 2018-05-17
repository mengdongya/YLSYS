package store.chinaotec.com.medicalcare.shopmarket.logic.shops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.shops.entity.Shops;

/**
 * Created by seven on 2016/8/11 0011.
 */
public class ShopListAdapter extends BaseAdapter {
    private Context context;
    private List<Shops> mDataList;

    public ShopListAdapter(Context context, List<Shops> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_shop, null);
            holder.shopIcon = (ImageView) convertView.findViewById(R.id.iv_store_shop_list_icon);
            holder.shopName = (TextView) convertView.findViewById(R.id.tv_store_shop_list_name);
            holder.shopType = (TextView) convertView.findViewById(R.id.tv_store_shop_list_type);
            holder.shopSales = (TextView) convertView.findViewById(R.id.tv_store_shop_list_sales);
            holder.shopNum = (TextView) convertView.findViewById(R.id.tv_store_shop_list_sku_num);
            holder.shopDistance = (TextView) convertView.findViewById(R.id.tv_store_shop_list_distance);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.shopName.setText(mDataList.get(position).getName());
        holder.shopType.setText(mDataList.get(position).getStoreCategoryName());
        holder.shopSales.setText("销量 " + mDataList.get(position).getActualOrderNumber());
        holder.shopNum.setText("共" + mDataList.get(position).getSkuNum() + " 件商品");
//        holder.shopDistance.setText(mDataList.get(position).getDistance());
        String imgUrl = mDataList.get(position).getStoreImage();

        if (null != imgUrl && !"".equals(imgUrl)) {
            ImageView imageView = holder.shopIcon;
            MyImageLoader.displayImage(imgUrl, imageView);
        } else {
            holder.shopIcon.setBackgroundResource(R.drawable.error_type);
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
        ImageView shopIcon;
        TextView shopName, shopType, shopSales, shopNum, shopDistance;
    }

}
