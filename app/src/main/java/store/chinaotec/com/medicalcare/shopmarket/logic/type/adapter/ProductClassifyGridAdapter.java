package store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuTypeListSearchActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.Category;

/**
 * @ClassName: ProductClassifyGridAdapter
 * @Description: 商品三级菜单GridView的适配器
 * @author: wyk
 * @date:2015年7月8日 下午5:38:43
 */
public class ProductClassifyGridAdapter extends BaseAdapter {
    /**
     * 友盟埋点 分类三级分类标题的点击事件
     */
    private final String UMENG_TYPE_SKU_FOR_THREE = "UMENG_TYPE_SKU_FOR_THREE";
    private Context context;
    private List<Category.ChildCategoryEntity> data;
    private HashMap<String, String> typeSkuForThreeMap = new HashMap<String, String>();

    public ProductClassifyGridAdapter(Context context,
                                      List<Category.ChildCategoryEntity> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Category.ChildCategoryEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = null;
        if (convertView == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(
                    R.layout.item_shop_market_product_grid, null);
            holder.iv = (ImageView) view.findViewById(R.id.iv);
            holder.tv = (TextView) view.findViewById(R.id.tv);
            // 封装成tag
            view.setTag(holder);
        } else {
            // 重复利用
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        final String imgUrl = data.get(position).getLogoUrl();
        if (imgUrl == null || imgUrl.equals("")) {
            holder.iv.setImageResource(R.drawable.error_type);
        } else {
            ImageView imageView = holder.iv;
            MyImageLoader.displayImage(imgUrl, imageView);
        }
        holder.iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SourceConstant.IS_NET_STATE) {
                    // 友盟 一级标题的点击次数统计
                    typeSkuForThreeMap.put("typeSkuForThreeName", "三级分类名称：" + data.get(position)
                            .getName());
//					MobclickAgent.onEvent(context, UMENG_TYPE_SKU_FOR_THREE,typeSkuForThreeMap);
                    Intent intent = new Intent(context, SkuTypeListSearchActivity.class);
                    String code = data.get(position).getCode();
                    Bundle bundle = new Bundle();
                    bundle.putString(SourceConstant.TITLE_NAME, data.get(position).getName());
                    bundle.putString(SourceConstant.TYPE_CODE, code);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    ToastUtil.MyToast(context, R.string.no_net_error);
                }
            }
        });
        holder.tv.setText(data.get(position).getName());
        return view;
    }

    class ViewHolder {
        TextView tv;
        ImageView iv;
    }
}