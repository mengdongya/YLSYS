package store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuTypeListSearchActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.ChildTypeEntity;

/**
 * 此类描述的是: 门店的二三级分类数据配置
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年4月22日 上午10:11:55
 */
public class ProductMenuStoreAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<ChildTypeEntity> data;

    public ProductMenuStoreAdapter(Context mcontext, ArrayList<ChildTypeEntity> data) {
        this.mcontext = mcontext;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
//			view = LayoutInflater.from(mcontext).inflate(R.layout.item_product_grid, null);
            convertView = View.inflate(mcontext, R.layout.item_shop_market_product_grid, null);
            holder.item_tv = (TextView) convertView.findViewById(R.id.tv);
            holder.iv_two_type = (ImageView) convertView.findViewById(R.id.iv);

            // 封装成tag
            convertView.setTag(holder);
        } else {
            // 重复利用
//			view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        String twoTypeName = data.get(position).getName();
        if (twoTypeName != null && !"".equals(twoTypeName)) {
            holder.item_tv.setText(twoTypeName);
            holder.item_tv.setVisibility(View.VISIBLE);
        } else {
            holder.item_tv.setVisibility(View.GONE);
        }

        String imgUrl = data.get(position).getLogoUrl();
        if (imgUrl != null && !"".equals(imgUrl)) {
            ImageView imageView = holder.iv_two_type;
            MyImageLoader.displayImage(imgUrl, imageView, R.drawable.error_type, R.drawable.error_type);
        } else {
            holder.iv_two_type.setImageResource(R.drawable.error_type);
        }
        holder.iv_two_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SourceConstant.IS_NET_STATE) {

                    Intent intent = new Intent(mcontext,
                            SkuTypeListSearchActivity.class);
                    String code = data.get(position).getCode();
                    Bundle bundle = new Bundle();
                    bundle.putString(SourceConstant.TITLE_NAME, data.get(position).getName());
                    bundle.putString(SourceConstant.TYPE_CODE, code);
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                } else {
                    ToastUtil.MyToast(mcontext, R.string.no_net_error);
                }
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView item_tv;
        ImageView iv_two_type;
    }

}
