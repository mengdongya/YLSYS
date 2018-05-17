package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.CustomUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

/**
 * 特惠商品 适配器
 */
public class GvDiscounAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Sku> mListData;
    /**
     * 屏幕宽度
     */
    private int width;

    public GvDiscounAdapter(Context context, ArrayList<Sku> mListData, int width) {
        this.context = context;
        this.mListData = mListData;
        // this.width = (width / 3) - 7;
        this.width = (width - CustomUtils.dip2px(context, 10)) / 2;
        // mInflater = (LayoutInflater)
        // context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // mImageLoader = new AsyncImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop_market_main_like, null);
            holder.mImg = (ImageView) convertView.findViewById(R.id.image);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_belong_to_shop = (TextView) convertView.findViewById(R.id.tv_belong_to_shop);
            holder.mImg.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            // holder.priceOld = (TextView)
            // convertView.findViewById(R.id.tv_price_old);
            // holder.commentCount = (TextView)
            // convertView.findViewById(R.id.tv_comment_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(mListData.get(position).name);
        holder.price.setText("￥ " + mListData.get(position).currentPrice);
        // holder.priceOld.setText(mListData.get(position).priceOld);

        // String comentCountStr;
        // comentCountStr =
        // String.valueOf(mListData.get(position).commentCount);
        // comentCountStr =
        // context.getResources().getString(string.sku_comment_count,
        // comentCountStr);
        //
        // holder.commentCount.setText(comentCountStr);
        String storeCode = mListData.get(position).storeCode;
//		if ("999".equals(storeCode)) {
//			holder.tv_belong_to_shop.setText("自营");
//		}else {
//			holder.tv_belong_to_shop.setText("旗舰店");
//		}
        int errorImg = R.drawable.error_sku;
        String imgUrl = mListData.get(position).imgPath;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.mImg.setImageResource(errorImg);
        } else {
            ImageView imageView = holder.mImg;
            MyImageLoader.displayImage(imgUrl, imageView, errorImg, errorImg);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    class ViewHolder {
        TextView name, price, tv_belong_to_shop;
        // TextView priceOld, commentCount;
        ImageView mImg;
    }
}
