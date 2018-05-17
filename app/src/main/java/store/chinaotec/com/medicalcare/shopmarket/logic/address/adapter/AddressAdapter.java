package store.chinaotec.com.medicalcare.shopmarket.logic.address.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.AddressInfo;

public class AddressAdapter extends BaseAdapter {
    private Context context;
    private List<AddressInfo> mListData;
    private OnClickListener clickLisetener;

    public AddressAdapter(Context context, List<AddressInfo> mListData) {
        this.context = context;
        this.mListData = mListData;
        if (context instanceof OnClickListener) {
            clickLisetener = (OnClickListener) context;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shop_market_address, null);
            holder = new ViewHolder();
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.mTvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            holder.img_arrow = (ImageView) convertView.findViewById(R.id.img_arrow);
            holder.mBtnCheck = (ImageView) convertView.findViewById(R.id.btn_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvName.setText(mListData.get(position).name);
        holder.mTvPhone.setText(mListData.get(position).phone);
        String address = mListData.get(position).provinceName + mListData.get(position).cityName + mListData.get(position).districtName + mListData.get(position).addressInfo;
        holder.mTvAddress.setText(address);
        holder.mBtnCheck.setTag(position);
        holder.mBtnCheck.setOnClickListener(clickLisetener);
        if (mListData.get(position).isDefault.equals("y")) {
            holder.mBtnCheck.setImageResource(R.drawable.btn_select_down);
        } else {
            holder.mBtnCheck.setImageResource(R.drawable.btn_select_up);
        }
        holder.img_arrow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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

    static class ViewHolder {
        TextView mTvAddress, mTvName, mTvPhone;
        ImageView img_arrow;
        ImageView mBtnCheck;
    }

}
