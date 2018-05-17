package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollGridView;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreSku;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.StoreSkuList;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;

/**
 * 此类描述的是: 首页门店的商品列表的适配器
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年12月19日 下午3:44:55
 */
public class StoreSkuListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StoreSku> storeSkus;
    private int mWindowWidth = 0;

    public StoreSkuListAdapter(Context context, ArrayList<StoreSku> storeSkus) {
        this.context = context;
        this.storeSkus = storeSkus;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_store_home_sku_list, null);
            holder.tv_store_lable = (TextView) convertView.findViewById(R.id.tv_store_lable);
            holder.gv_store_lable_sku = (MyScrollGridView) convertView.findViewById(R.id.gv_store_lable_sku);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        StoreSku storeSku = storeSkus.get(position);
        holder.tv_store_lable.setText(storeSku.getLabelName());

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWindowWidth = dm.widthPixels;
        final ArrayList<StoreSkuList> storeSkuList = storeSku.getStoreSkuList();
        StoreSkuItemAdapter skuItemAdapter = new StoreSkuItemAdapter(context, storeSkuList, mWindowWidth);
        holder.gv_store_lable_sku.setAdapter(skuItemAdapter);
        holder.gv_store_lable_sku.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SkuInfoActivity.class);
                intent.putExtra(SourceConstant.SKU_CODE, storeSkuList.get(position).skuCode);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return storeSkus.size();
    }

    @Override
    public Object getItem(int position) {
        return storeSkus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        TextView tv_store_lable;
        MyScrollGridView gv_store_lable_sku;
    }
}
