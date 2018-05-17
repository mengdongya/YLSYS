package store.chinaotec.com.medicalcare.shopmarket.logic.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.SkuBrowseHistoriesDto;

/**
 * 此类描述的是:浏览足迹的适配器
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年11月13日 下午2:37:31
 */

public class SkuHistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SkuBrowseHistoriesDto> dataList;
    private int width;

    public SkuHistoryAdapter(Context context, ArrayList<SkuBrowseHistoriesDto> skuBrowseHistoriesDto, int width) {
        this.context = context;
        this.dataList = skuBrowseHistoriesDto;
        this.width = width;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            // convertView =
            // LayoutInflater.from(context).inflate(R.layout.item_commodity,
            // null);
            convertView = View.inflate(context, R.layout.item_shop_market_sku_list, null);
//			convertView.setLayoutParams(new AbsListView.LayoutParams(width / 2, width / 2));
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_browse_time = (TextView) convertView.findViewById(R.id.tv_browse_time);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Sku sku = dataList.get(position).getSkuDto();
//		if ("y".equals(sku.isMemberSku)) {
//			holder.price.setText("VIP价：￥ " + sku.priceVip);
//			holder.tv_old_price .setText("原价：￥ " + sku.priceNew);
//			holder.tv_old_price .setVisibility(View.VISIBLE);
//		}else{
//			holder.tv_old_price .setVisibility(View.GONE);
//			holder.price.setText("价格：￥ " + sku.priceNew);
//		}
        holder.price.setText("价格：￥ " + sku.currentPrice);
        holder.name.setText(sku.name);

        holder.tv_browse_time.setText("浏览时间:" + dataList.get(position).getBrowsTime());

        String imgUrl = sku.imgPath;
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

    class Holder {
        TextView name, price, tv_old_price, tv_browse_time;
        ImageView img;
    }
}
