package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.CustomUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreSkuList;

/**
 * 此类描述的是:门店首页商品的商品lable下的商品
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年12月19日 下午4:00:08
 */
public class StoreSkuItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StoreSkuList> storeSkuLists;
    /**
     * 屏幕宽度
     */
    private int width;
    private OnClickListener clickListener;
    private String[] storeImageUrls;

    public StoreSkuItemAdapter(Context context, ArrayList<StoreSkuList> storeSkuLists, int with) {
        this.context = context;
        this.storeSkuLists = storeSkuLists;
        this.width = (with - CustomUtils.dip2px(context, 10)) / 2;
        if (context instanceof OnClickListener) {
            clickListener = (OnClickListener) context;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop_store_main_sku, null);
            holder.mImg = (ImageView) convertView.findViewById(R.id.store_home_sku);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.mImg.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StoreSkuList storeSku = storeSkuLists.get(position);
        holder.name.setText(storeSku.getName());
        holder.price.setText("￥ " + storeSku.currentPrice);
        storeImageUrls = new String[storeSku.imgUrls.size()];
        ArrayList<String> imgUrl = storeSku.imgUrls;
        if (imgUrl.size() != 0) {
            String storeImg = imgUrl.get(0).toString();
            storeImageUrls[0] = storeImg;
            if (storeImg == null || storeImg.equals("")) {
                holder.mImg.setBackgroundResource(R.drawable.error_type);
            } else {
                ImageView imageView = holder.mImg;
                MyImageLoader.displayImage(storeImg, imageView, R.drawable.error_type, R.drawable.error_type);
            }
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return storeSkuLists.size();
    }

    @Override
    public Object getItem(int position) {
        return storeSkuLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView name, price;
        //		SlideShowView mImg;
        ImageView mImg;
    }
}
