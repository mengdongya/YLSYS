package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;

/**
 * <p>
 * Title: TopViewPagerAdapter.java
 * </p>
 * <p>
 * E-Mail: 176291935@qq.com
 * </p>
 * <p>
 * QQ: 176291935
 * </p>
 * <p>
 * Http: iaiai.iteye.com
 * </p>
 * <p>
 * Create time: 2011-6-26
 * </p>
 *
 * @author 丸子
 * @version 0.0.1
 */
public class TopViewPagerAdapter extends PagerAdapter {

    private ArrayList<View> mPageViewList;
    private ArrayList<String> mImageList;
    private LayoutInflater mInflater;
    private int mChildCount = 0;

    public TopViewPagerAdapter(Context context, ArrayList<String> imageList,
                               int itemLayoutId) {
        mImageList = imageList;
        mPageViewList = new ArrayList<View>();

        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (!(imageList == null || imageList.isEmpty())) {
            for (int i = 0; i < imageList.size(); i++) {
                View view = mInflater.inflate(itemLayoutId, null);
                mPageViewList.add(view);
            }
        }

    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPageViewList != null ? mPageViewList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mPageViewList.get(arg1));
    }

    @Override
    public Object instantiateItem(View view, final int postion) {
        final String imgPath = mImageList.get(postion);
        ((ViewPager) view).addView(mPageViewList.get(postion));
        final ImageView imageView = (ImageView) mPageViewList.get(postion)
                .findViewById(R.id.image_main);

        // =========================================

        if (imgPath != null && !imgPath.equals("")) {
            imageView.setTag(imgPath);
            MyImageLoader.displayImage(imgPath, imageView);
        }

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, "this index is " + postion,
                // Toast.LENGTH_SHORT).show();
            }
        });

        return mPageViewList.get(postion);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        //
    }

    @Override
    public Parcelable saveState() {
        //
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        //
    }

    @Override
    public void finishUpdate(View arg0) {
        //
    }

    class Holder {
        ImageView img;
    }
}
