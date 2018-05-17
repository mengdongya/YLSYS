package store.chinaotec.com.medicalcare.localalbum;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import java.util.List;
import store.chinaotec.com.medicalcare.R;


/**
 * 此类描述的是: 大图的viewpager页面
 * 作者：fengdi on 2016/4/29 16:23
 */
public class AlbumViewPager extends ViewPager implements MatrixImageView.OnMovingListener {
    public final static String TAG = "AlbumViewPager";


    /**
     * 当前子控件是否处理拖动状态
     */
    private boolean mChildIsBeingDragged = false;

    /**
     * 界面单击事件 用以显示和隐藏菜单栏
     */
    private MatrixImageView.OnSingleTapListener onSingleTapListener;

    /**
     * 播放按钮点击事件
     */
    public AlbumViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mChildIsBeingDragged)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void startDrag() {
        // TODO Auto-generated method stub
        mChildIsBeingDragged = true;
    }


    @Override
    public void stopDrag() {
        // TODO Auto-generated method stub
        mChildIsBeingDragged = false;
    }

    public void setOnSingleTapListener(MatrixImageView.OnSingleTapListener onSingleTapListener) {
        this.onSingleTapListener = onSingleTapListener;
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<ImgUrlItem> paths;//大图地址 如果为网络图片 则为大图url

        public ViewPagerAdapter(List<ImgUrlItem> paths) {
            this.paths = paths;
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            //注意，这里不可以加inflate的时候直接添加到viewGroup下，而需要用addView重新添加
            //因为直接加到viewGroup下会导致返回的view为viewGroup
            View imageLayout = inflate(getContext(), R.layout.item_album_pager, null);
            viewGroup.addView(imageLayout);
            assert imageLayout != null;
            MatrixImageView imageView = (MatrixImageView) imageLayout.findViewById(R.id.image);
            imageView.setOnMovingListener(AlbumViewPager.this);
            imageView.setOnSingleTapListener(onSingleTapListener);
            String path = paths.get(position).url;
            Glide.with(getContext()).load(path).into(imageView);
            return imageLayout;
        }


        @Override
        public int getItemPosition(Object object) {
            //在notifyDataSetChanged时返回None，重新绘制
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int arg1, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }


    }

    public class LocalViewPagerAdapter extends PagerAdapter {
        private List<LocalImageHelper.LocalFile> paths;//大图地址 如果为网络图片 则为大图url

        public LocalViewPagerAdapter(List<LocalImageHelper.LocalFile> paths) {
            this.paths = paths;
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            //注意，这里不可以加inflate的时候直接添加到viewGroup下，而需要用addView重新添加
            //因为直接加到viewGroup下会导致返回的view为viewGroup
            View imageLayout = inflate(getContext(), R.layout.item_album_pager, null);
            viewGroup.addView(imageLayout);
            assert imageLayout != null;
            MatrixImageView imageView = (MatrixImageView) imageLayout.findViewById(R.id.image);
            imageView.setOnMovingListener(AlbumViewPager.this);
            imageView.setOnSingleTapListener(onSingleTapListener);
            LocalImageHelper.LocalFile path = paths.get(position);
            Glide.with(getContext()).load(path.getOriginalUri()).into(imageView);
            return imageLayout;
        }


        @Override
        public int getItemPosition(Object object) {
            //在notifyDataSetChanged时返回None，重新绘制
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int arg1, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

}