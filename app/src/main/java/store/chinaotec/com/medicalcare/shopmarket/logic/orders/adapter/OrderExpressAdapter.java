package store.chinaotec.com.medicalcare.shopmarket.logic.orders.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.model.ExpressResArrayDto;

/**
 * 此类描述的是:物流信息列表的适配器
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年11月11日 下午5:47:08
 */
public class OrderExpressAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ExpressResArrayDto> mDataList;

    public OrderExpressAdapter(Context context, ArrayList<ExpressResArrayDto> dataList) {
        this.context = context;
        this.mDataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_order_express_info, null);
            holder.view_up_line = (View) convertView.findViewById(R.id.view_up_line);
            holder.iv_order_express_tracking = (ImageView) convertView.findViewById(R.id.iv_order_express_tracking);
            holder.tv_order_express_info_desc = (TextView) convertView.findViewById(R.id.tv_order_express_info_desc);
            holder.tv_order_express_info_time = (TextView) convertView.findViewById(R.id.tv_order_express_info_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ExpressResArrayDto arrayDto = mDataList.get(position);
//		int currPosition = 0;
        holder.tv_order_express_info_desc.setText(arrayDto.getContext());
        holder.tv_order_express_info_time.setText(arrayDto.getTime());
        if (position == 0) {
            holder.view_up_line.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.iv_order_express_tracking.setBackgroundResource(R.drawable.order_express_tracking);
            holder.tv_order_express_info_desc.setTextColor(context.getResources().getColor(R.color.text_color_orange));
            holder.tv_order_express_info_time.setTextColor(context.getResources().getColor(R.color.text_color_orange));
        } else {
            holder.view_up_line.setBackgroundColor(context.getResources().getColor(R.color.line_gary));
            holder.iv_order_express_tracking.setBackgroundResource(R.drawable.order_express_tracking_other);
            holder.tv_order_express_info_desc.setTextColor(context.getResources().getColor(R.color.line_gary));
            holder.tv_order_express_info_time.setTextColor(context.getResources().getColor(R.color.line_gary));
        }
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

    class Holder {
        ImageView iv_order_express_tracking;
        /**
         * 跟综信息的描述
         */
        TextView tv_order_express_info_desc;
        /**
         * 跟踪信息的时间
         */
        TextView tv_order_express_info_time;
        View view_up_line;
    }

}
