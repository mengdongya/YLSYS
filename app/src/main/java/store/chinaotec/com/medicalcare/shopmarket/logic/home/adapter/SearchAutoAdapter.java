package store.chinaotec.com.medicalcare.shopmarket.logic.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;

public class SearchAutoAdapter extends BaseAdapter {
    //	private ArrayList<SearchAutoData> mOriginalValues;// 所有的Item
//	private final Object mLock = new Object();
//	private int mMaxMatch = 10;// 最多显示多少个选项,负数表示全部
    List<String> data;
    private Context mContext;
    private OnClickListener mOnClickListener;

    public SearchAutoAdapter(Context context, List<String> data,
                             OnClickListener onClickListener) {
        this.mContext = context;
        this.data = data;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        Log.i("cyl", "getCount======" + data.size());
        return null == data ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return null == data ? 0 : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_shop_market_seach_history_list, parent, false);
            holder = new AutoHolder();
            holder.content = (TextView) convertView.findViewById(R.id.auto_content);
            convertView.setTag(holder);
        } else {
            holder = (AutoHolder) convertView.getTag();
        }

        String str = data.get(position);
        holder.content.setText(str);
//		holder.addButton.setTag(data);
//		holder.addButton.setOnClickListener(mOnClickListener);
//		holder.content.setOnClickListener(mOnClickListener);
        return convertView;
    }


    /**
     * 匹配过滤搜索内容
     */
//	public void performFiltering(CharSequence prefix) {
//		if (prefix == null || prefix.length() == 0) {//搜索框内容为空的时候显示所有历史记录
////			synchronized (mLock) {
////				mObjects = mOriginalValues;
////			}
//		} else {
//			String prefixString = prefix.toString().toLowerCase();
////			int count = mOriginalValues.size();
//			ArrayList<SearchAutoData> newValues = new ArrayList<SearchAutoData>(
//					count);
//			for (int i = 0; i < count; i++) {
//				final String value = mOriginalValues.get(i).getContent();
//				final String valueText = value.toLowerCase();
//				if (valueText.contains(prefixString)) {
//
//				}
//				if (valueText.startsWith(prefixString)) {
//					newValues.add(new SearchAutoData().setContent(valueText));
//				} else {
//					final String[] words = valueText.split(" ");
//					final int wordCount = words.length;
//					for (int k = 0; k < wordCount; k++) {
//						if (words[k].startsWith(prefixString)) {
//							newValues.add(new SearchAutoData().setContent(value));
//							break;
//						}
//					}
//				}
//				if (mMaxMatch > 0) {
//					if (newValues.size() > mMaxMatch - 1) {
//						break;
//					}
//				}
//			}
////			mObjects = newValues;
//		}
//		notifyDataSetChanged();
//	}

    private class AutoHolder {
        TextView content;
//		TextView addButton;
//		TextView autoImage;
    }
}
