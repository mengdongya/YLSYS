package store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;


/**
 * @ClassName: LeftMenuAdapter
 * @Description: 左侧菜单栏的适配器
 * @author: wyk
 * @date:2015年7月8日 下午5:40:18
 */
public class LeftMenuAdapter extends BaseAdapter {
    private Context context;
    private String[] data;
    private int indx = 0;
    private Resources resource;

    public LeftMenuAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    public void setIndx(int indx) {
        this.indx = indx;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public String getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = null;
        if (convertView == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_shop_market_type,
                    null);
            holder.item_tv = (TextView) view.findViewById(R.id.tv_type_name);

            // 封装成tag
            view.setTag(holder);
        } else {
            // 重复利用
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        String product = data[position];
        if (position != indx) {
            resource = (Resources) context.getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.text_color_gray);
            if (csl != null) {
                holder.item_tv.setTextColor(csl);
            }
            holder.item_tv.setBackgroundColor(Color.parseColor("#F8F8F8"));
        } else {
            LogUtil.e("TitleFragment", "adapter的indx：" + indx);
            resource = (Resources) context.getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.black1);
            if (csl != null) {
                holder.item_tv.setTextColor(csl);
            }
            holder.item_tv.setBackgroundColor(Color.WHITE);
        }
        holder.item_tv.setText(product);
        return view;
    }

    class ViewHolder {
        TextView item_tv;
    }
}