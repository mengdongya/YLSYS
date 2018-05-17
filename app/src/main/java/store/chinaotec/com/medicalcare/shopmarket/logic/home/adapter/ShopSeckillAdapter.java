package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.ShopGroupBuyAndSeckill;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuInfoActivity;

/**
 * Created by wjc on 2016/8/20 0020.
 */
public class ShopSeckillAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShopGroupBuyAndSeckill> mDataList;

    public ShopSeckillAdapter(Context context, ArrayList<ShopGroupBuyAndSeckill> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_seckill_sku, null);
            holder.skuIcon = (ImageView) convertView.findViewById(R.id.iv_shop_limit_sku_icon);
            holder.skuName = (TextView) convertView.findViewById(R.id.tv_shop_limit_sku_name);
            holder.skuCurrPrice = (TextView) convertView.findViewById(R.id.tv_shop_limit_sku_current_price);
            holder.skuOldPrice = (TextView) convertView.findViewById(R.id.tv_shop_limit_sku_old_price);
            holder.skuGo = (TextView) convertView.findViewById(R.id.tv_shop_limit_go);
            holder.skuNum = (TextView) convertView.findViewById(R.id.tv_shop_limit_sku_num);
            holder.progress_limit = (ProgressBar) convertView.findViewById(R.id.progress_limit);
            holder.cv_countdownViewTest = (CountdownView) convertView.findViewById(R.id.cv_countdownViewTest);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.skuName.setText(mDataList.get(position).getSkuName());
        holder.skuCurrPrice.setText("￥" + mDataList.get(position).getSkuTogetherPrice());
        holder.skuOldPrice.setText("￥" + mDataList.get(position).getSkuPrice());
        holder.skuOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中划线
        holder.skuNum.setText("已售" + mDataList.get(position).getSaleRate() + "%");
        int pro = Integer.parseInt(mDataList.get(position).getSaleRate());
        holder.progress_limit.setProgress(pro);
        long endTime = Long.valueOf(mDataList.get(position).getDistanceEndSecond());
        LogUtil.e("", "===========秒杀时间：" + endTime);
//        long endTime = 360000;
        holder.cv_countdownViewTest.start(endTime * 1000);

        String imgUrl = mDataList.get(position).getSkuImgPath();
        if (null != imgUrl && !"".equals(imgUrl)) {
            ImageView imageView = holder.skuIcon;
            MyImageLoader.displayImage(imgUrl, imageView);
        } else {
            holder.skuIcon.setBackgroundResource(R.drawable.error_type);
        }
        holder.skuGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, SkuInfoActivity.class);
                intent.putExtra(SourceConstant.SKU_CODE, mDataList.get(position).getSkuCode());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Holder {
        ImageView skuIcon;
        TextView skuName, skuCurrPrice, skuOldPrice, skuNum, skuGo;
        ProgressBar progress_limit;
        CountdownView cv_countdownViewTest;
    }
}
