package store.chinaotec.com.medicalcare.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjc on 2016/7/1.
 */
public class ForumPicBannerAdapter extends PagerAdapter {
    private List<View> mPagers;
    private Activity context;

    public ForumPicBannerAdapter(List<View> pagers) {
        this.mPagers = pagers;
    }

    public ForumPicBannerAdapter(Activity context, ArrayList<View> pagers) {
        this.mPagers = pagers;
        this.context = context;
    }

    @Override
    public int getCount() {
        // return mPagers.isEmpty() ? 0 : Integer.MAX_VALUE;
        return mPagers.size();
//        return mPagers.size() <= 1 ? mPagers.size() : Integer.MAX_VALUE;
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mPagers.get(position % mPagers.size()));
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要
    // 显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View v = mPagers.get(position
                % mPagers.size());
        if (view.getParent() != null) {
            view.removeView(v);
        }
        view.addView(v);
        return mPagers.get(position % mPagers.size());
    }

}

