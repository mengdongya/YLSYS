package store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.Category;

/**
 * @ClassName: ProductMenuAdapter
 * @Description: 商品二级菜单栏的适配器
 * @author: wyk
 * @date:2015年7月8日 下午5:41:12
 */
public class ProductMenuAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Category.ChildCategoryEntity> data;

    /**
     * @param mcontext
     * @param list
     */
    public ProductMenuAdapter(Context mcontext, List<Category.ChildCategoryEntity> list) {
        this.mcontext = mcontext;
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
            view = LayoutInflater.from(mcontext).inflate(
                    R.layout.item_shop_market_pro_list, null);
            holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
            holder.gv = (GridView) view.findViewById(R.id.gv);

            // 封装成tag
            view.setTag(holder);
        } else {
            // 重复利用
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        String twoTypeName = data.get(position).getName();
        if (twoTypeName != null && !"".equals(twoTypeName)) {
            holder.item_tv.setText(twoTypeName);
            holder.item_tv.setVisibility(View.VISIBLE);
        } else {
            holder.item_tv.setVisibility(View.GONE);
        }
        List<Category.ChildCategoryEntity> childCategory = data.get(position).getChildCategory();
        if (childCategory != null && !"".equals(childCategory)) {
            holder.gv.setAdapter(new ProductClassifyGridAdapter(mcontext, childCategory));
            holder.gv.setVisibility(View.VISIBLE);
        } else {
            holder.gv.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHolder {
        TextView item_tv;
        GridView gv;
    }
}