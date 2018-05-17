package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.localalbum.LocalImageHelper;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;

/**
 * Created by wjc on 2016/11/9 0009.
 */
public class GridPictureAdapter extends BaseAdapter {
    /**
     * 删除图片的id
     */
    private static List<String> delImgId;
    private final List<LocalImageHelper.LocalFile> checkedItems;
    private final Context context;
    private LayoutInflater inflater;
    private Bitmap bitmap;

    public GridPictureAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        checkedItems = LocalImageHelper.getInstance().getCheckedItems();
        delImgId = new ArrayList<String>();
    }

    public static List<String> getDelImgId() {
        return delImgId;
    }

    public static void setDelImgId(List<String> delImgId) {
        delImgId = delImgId;
    }

    public int getCount() {
        if (checkedItems.size() == 6) {
            return 6;
        }
        return (checkedItems.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_sku_icon, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.iv_item_sku_icon);
            holder.iv_close = (ImageView) convertView.findViewById(R.id.iv_item_sku_close);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == checkedItems.size()) {
            holder.image.setImageBitmap(BitmapFactory.decodeResource(convertView.getContext().getResources(), R.mipmap.icon_forum_add_pic));
            holder.iv_close.setVisibility(View.GONE);
            holder.iv_close.setClickable(false);
            if (position == 10) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
//            LogUtil.e("url的position------------------", position + "");
            holder.iv_close.setVisibility(View.VISIBLE);
            if (checkedItems.get(position).isNetUrl) {
                Glide.with(context).load(checkedItems.get(position).getOriginalUri()).placeholder(R.drawable.error_img).into(holder.image);
            } else {
                holder.image.setImageBitmap(checkedItems.get(position).getBitmap());
            }
        }
        holder.image.setDrawingCacheEnabled(true);
        holder.image.buildDrawingCache();
        bitmap = holder.image.getDrawingCache();
        holder.iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkedItems.get(position).isNetUrl) {
                    delImgId.add(checkedItems.get(position).id);
                }
                checkedItems.remove(position);
                GridPictureAdapter.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public ImageView image, iv_close;
    }
}
