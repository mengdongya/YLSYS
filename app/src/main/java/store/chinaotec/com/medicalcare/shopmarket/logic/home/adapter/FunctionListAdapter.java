package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.CustomUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.VerifyUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.MainActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.activity.ShopMarketSeckillAndGroupBuyActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.FunctionalBlock;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.AllOrderActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.activity.MyFavoritesActivity;

/**
 * 特惠商品 适配器
 */
public class FunctionListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FunctionalBlock> mListData;
    /**
     * 屏幕宽度
     */
    private int width;

    public FunctionListAdapter(Context context,
                               ArrayList<FunctionalBlock> mListData, int width) {
        this.context = context;
        this.mListData = mListData;
        // this.width = (width / 3) - 7;
        this.width = (width - CustomUtils.dip2px(context, 10)) / 4;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shop_market_function_list,
                    null);
            convertView.setLayoutParams(new AbsListView.LayoutParams(width,
                    width));
            holder.mImg = (ImageView) convertView
                    .findViewById(R.id.iv_function_icon);
            holder.ll_function = (LinearLayout) convertView
                    .findViewById(R.id.ll_function);
            holder.name = (TextView) convertView
                    .findViewById(R.id.tv_function_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FunctionalBlock block = mListData.get(position);
        holder.name.setText(block.getName());
        String imgUrl = block.getIcon();
        int errorImg = R.drawable.error_home_block;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.mImg.setImageResource(errorImg);
        } else {
            ImageView imageView = holder.mImg;
            MyImageLoader.displayImage(imgUrl, imageView, errorImg, errorImg);
        }
        final String key = block.getCode();
        holder.ll_function.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SourceConstant.IS_NET_STATE) {
                    Intent intent = new Intent();
                    switch (key) {
                        case SourceConstant.BLOCK_GROUP_BUY:
                            intent.setClass(context, ShopMarketSeckillAndGroupBuyActivity.class);
                            context.getSharedPreferences("alisp", Context.MODE_PRIVATE).edit().putString("BannerCode", "TOGEGHER").commit();
                            context.getSharedPreferences("alisp", Context.MODE_PRIVATE).edit().putString("BannerName", "团购").commit();
//							intent.putExtra("BannerCode", "TOGEGHER");
//							intent.putExtra("BannerName", "团购");
                            break;
                        case SourceConstant.BLOCK_SECKILL:
                            intent.setClass(context, ShopMarketSeckillAndGroupBuyActivity.class);
                            context.getSharedPreferences("alisp", Context.MODE_PRIVATE).edit().putString("BannerCode", "TIMELIMIT").commit();
                            context.getSharedPreferences("alisp", Context.MODE_PRIVATE).edit().putString("BannerName", "秒杀").commit();
//							intent.putExtra("BannerCode", "TIMELIMIT");
//							intent.putExtra("BannerName", "秒杀");
                            break;
                        case SourceConstant.BLOCK_COLLECT:
                            if (!VerifyUtil.isEmpyty(MainActivity.sid)) {
                                intent.setClass(context, MyFavoritesActivity.class);
                            } else {
//                                intent.setClass(context, LoginActivity.class);TODO
                            }

                            break;
                        case SourceConstant.BLOCK_ORDER:
                            if (!VerifyUtil.isEmpyty(MainActivity.sid)) {
                                intent.setClass(context, AllOrderActivity.class);
                            } else {
//                                intent.setClass(context, LoginActivity.class);TODO
                            }

                            break;
                    }
                    context.startActivity(intent);
                } else {
                    ToastUtil.MyToast(context, R.string.no_net_error);
                }
            }
        });
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
        TextView name;
        ImageView mImg;
        LinearLayout ll_function;
    }
}
