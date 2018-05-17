package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.CustomUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.GraphicDetail;

/**
 * 此类描述的是: 商品详情适配器
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年8月29日 下午1:05:25
 */
public class SkuParameterAdapter extends BaseAdapter {

    private Context context;
    private List<GraphicDetail> dataList;

    private String TAG = "SkuParameterAdapter";
    private int width;

    public SkuParameterAdapter(Context context, List<GraphicDetail> dataList, int width) {
        this.context = context;
        this.dataList = dataList;
        this.width = width;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        GraphicDetail detail = dataList.get(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_sku_parameter,
                    null);
            holder.iv_sku_parameter = (ImageView) convertView.findViewById(R.id.iv_sku_parameter);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        int height = CustomUtils.px2dip(context, detail.height);
        holder.iv_sku_parameter.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
        LogUtil.e(TAG, "屏幕宽度：" + SourceConstant.screenWidth + "========图片高度：" + detail.height + "========px2dip：" + height);
        String imgUrl = detail.url;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.iv_sku_parameter.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.iv_sku_parameter;
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
        ImageView iv_sku_parameter;
    }
}
