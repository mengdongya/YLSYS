package store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.SkuByOrderLine;

/**
 * 此类描述的是:商品列表中的商品数量大于1个的适配器
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年12月14日 上午11:32:00
 */
public class OrserHorizontalListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SkuByOrderLine> list;
    private OnClickListener clickListener;

    public OrserHorizontalListViewAdapter(Context context, ArrayList<SkuByOrderLine> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SkuByOrderLine sku = list.get(position);
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_order_hor_listview, null);
            holder.iv_order_sku_img = (ImageView) convertView.findViewById(R.id.img_like);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

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
    }
}
