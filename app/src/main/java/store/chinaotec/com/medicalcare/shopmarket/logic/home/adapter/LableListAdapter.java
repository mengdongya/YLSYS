package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.CustomUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.logic.home.model.CategoryAndPre;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity.SkuTypeListSearchActivity;

/**
 * 此类描述的是: lable的是数据适配器
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年1月13日 下午5:23:45
 */
public class LableListAdapter extends BaseAdapter {

    private ArrayList<CategoryAndPre> lableNums;
    private Context context;
    private int width;

    public LableListAdapter(Context context, ArrayList<CategoryAndPre> lableNums, int width) {
        this.context = context;
        this.lableNums = lableNums;
        this.width = (width - CustomUtils.dip2px(context, 10)) / 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_home_lable, null);
            holder.iv_lable = (ImageView) convertView.findViewById(R.id.iv_lable_icon);

//			holder.tv_lable_name = (TextView) convertView.findViewById(R.id.tv_lable_name);
            holder.ll_lable_list = (LinearLayout) convertView.findViewById(R.id.ll_lable_list);
//			convertView.setLayoutParams(new AbsListView.LayoutParams(width,width)); 
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        CategoryAndPre lableNum = lableNums.get(position);
        final String name = lableNum.getName();
        String imgUrl = lableNum.getImgUrl();
        int errorImg = R.drawable.error_home_pre_category;
        if (imgUrl == null || imgUrl.equals("")) {
            holder.iv_lable.setImageResource(errorImg);
        } else {
            ImageView imageView = holder.iv_lable;
            MyImageLoader.displayImage(imgUrl, imageView, errorImg, errorImg);
        }
        final String code = lableNum.getCode();

        holder.iv_lable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SkuTypeListSearchActivity.class);
                intent.putExtra(SourceConstant.TYPE_CODE, code);
                intent.putExtra(SourceConstant.TITLE_NAME, name);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return lableNums.size();
    }

    @Override
    public Object getItem(int position) {
        return lableNums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        ImageView iv_lable;
        //		TextView tv_lable_name;
        LinearLayout ll_lable_list;
    }
}
