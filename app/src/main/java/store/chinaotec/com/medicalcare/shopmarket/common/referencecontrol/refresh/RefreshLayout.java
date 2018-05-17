package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.refresh;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;

public class RefreshLayout extends LinearLayout {
    //下拉头部回滚的速度
    public static final int SCROLL_SPACED = -5;
    // 刷新状态
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;
    // 是否刷新
    private static final int PULL_UP_STATE = 0;
    private static final int PULL_DOWN_STATE = 1;
    private static final String TAG = "RefreshLayout";
    public LayoutParams layoutparams;
    /**
     * last y
     */
    private int mLastMotionY;
    /**
     * lock
     */
    private boolean mLock;
    /**
     * headerview
     */
    private View mHeaderView;
    /**
     * footerview
     */
    private View mFooterView;
    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;
    /**
     * scrollview
     */
    private ScrollView mScrollView;
    /**
     * header view height
     */
    private int mHeaderViewHeight;
    /**
     * footerview 的高度
     */
    private int mFooterViewHeight;
    /**
     * headerview 图片
     */
    private ImageView mHeaderImageView;
    /**
     * header提示文字
     */
    private TextView mHeaderTextView;
    /**
     * footer提示文字
     */
    private TextView mFooterTextView;
    /**
     * header刷新时间
     */
    private TextView mHeaderUpdateTextView;
    /**
     * header progressbar
     */
    private ProgressBar mHeaderProgressBar;
    /**
     * footer progressbar
     */
    private ProgressBar mFooterProgressBar;
    /**
     * layout inflater
     */
    private LayoutInflater mInflater;
    /**
     * headerview的当前状态
     */
    private int mHeaderState;
    /**
     * footerview的当前状态
     */
    private int mFooterState;
    /**
     * pull 的状态
     */
    private int mPullState;
    /**
     * 变为向下的箭头,改变箭头方向
     */
    private RotateAnimation mFlipAnimation;
    /**
     * 变为逆向的箭头,旋转
     */
    private RotateAnimation mReverseFlipAnimation;
    /**
     * header刷新监听
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;
    /**
     * 最近更新时间
     */
    private String mLastUpdateTime;

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshLayout(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        addHeaderView();
    }

    /**
     * 添加header
     */
    private void addHeaderView() {
        mInflater.inflate(R.layout.shop_market_new_refresh_header, this, false);
        mHeaderImageView = (ImageView) mHeaderView
                .findViewById(R.id.pull_to_refresh_image);
        mHeaderTextView = (TextView) mHeaderView
                .findViewById(R.id.pull_to_refresh_text);
        mHeaderUpdateTextView = (TextView) mHeaderView
                .findViewById(R.id.pull_to_refresh_updated_at);
        mHeaderProgressBar = (ProgressBar) mHeaderView
                .findViewById(R.id.pull_to_refresh_progress);
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();


        layoutparams = new LayoutParams(LayoutParams.MATCH_PARENT,
                mHeaderViewHeight);
        // 设置topMargin的值为负的header View高度,即将其隐藏在最上方
        layoutparams.topMargin = -(mHeaderViewHeight);
        addView(mHeaderView, layoutparams);


    }


    /**
     * 调用完inflate方法后执行
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    /**
     * 计算View的大小
     *
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 用于拦截手势事件的，每个手势事件都会先调用onInterceptTouchEvent
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                if (isRefreshViewScroll(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /**
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLock) {
            return true;
        }
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE) {
                    // PullToRefreshView执行下拉
                    LogUtil.e(TAG, "PullToRefreshView执行下拉");
                    headerPrepareToRefresh(deltaY);
                } else if (mPullState == PULL_UP_STATE) {
                    // PullToRefreshView执行上拉
//				footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0) {
                        // 开始刷新
                        headerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
//		//对于ListView和GridView
//		if (mAdapterView != null) {
//			// 子view(ListView or GridView)滑动到最顶端
//			if (deltaY > 0) {
//
//				View child = mAdapterView.getChildAt(0);
//				if (child == null) {
//					// 如果mAdapterView中没有数据,不拦截
//					return false;
//				}
//				if (mAdapterView.getFirstVisiblePosition() == 0
//						&& child.getTop() == 0) {
//					mPullState = PULL_DOWN_STATE;
//					return true;
//				}
//				int top = child.getTop();
//				int padding = mAdapterView.getPaddingTop();
//				if (mAdapterView.getFirstVisiblePosition() == 0
//						&& Math.abs(top - padding) <= 8) {//这里之前用3可以判断,但现在不行,还没找到原因
//					mPullState = PULL_DOWN_STATE;
//					return true;
//				}
//
//			} else if (deltaY < 0) {
//				View lastChild = mAdapterView.getChildAt(mAdapterView
//						.getChildCount() - 1);
//				if (lastChild == null) {
//					// 如果mAdapterView中没有数据,不拦截
//					return false;
//				}
//				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
//				// 等于父View的高度说明mAdapterView已经滑动到最后
//				if (lastChild.getBottom() <= getHeight()
//						&& mAdapterView.getLastVisiblePosition() == mAdapterView
//						.getCount() - 1) {
//					mPullState = PULL_UP_STATE;
//					return true;
//				}
//			}
//		}
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }

    public void setmScrollView(ScrollView mScrollView) {
        this.mScrollView = mScrollView;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
            mHeaderImageView.setVisibility(View.VISIBLE);
            mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
            mHeaderImageView.setVisibility(View.VISIBLE);
            mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
            mHeaderState = PULL_TO_REFRESH;

        }
    }


    /**
     * 修改Header view top margin的值
     *
     * @param deltaY
     * @description
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        //这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
        //表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        //同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    /**
     * header刷新
     */
    private void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setImageDrawable(null);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }

    /**
     * header view 完成更新后恢复初始状态
     */
    public void onHeaderRefreshComplete() {
        new HideHeaderTask().execute();
        mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
        mHeaderProgressBar.setVisibility(View.GONE);

    }

    /**
     * 下拉刷新的完成时的状态
     */
    public void onHeaderRefreshComplete(CharSequence lastUpdated) {
        setLastUpdated(lastUpdated);
        onHeaderRefreshComplete();
    }

    /**
     * 设置最近更新时的状态以及文字提示
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText(lastUpdated);
        } else {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText("暂无更新！");
        }
    }

    /**
     * 获取当前header view 的topMargin，即距离屏幕最上方的距离
     *
     * @description
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @param topMargin ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     * @description
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * 事件上锁
     */
    private void lock() {
        mLock = true;
    }

    /**
     * 事件解锁
     */
    private void unlock() {
        mLock = false;
    }

    /**
     * 设置下拉的监听事件，要实现下拉的功能必须调用此方法
     *
     * @param headerRefreshListener
     * @description
     */
    public void setOnHeaderRefreshListener(
            OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    /**
     * 下拉刷新的接口，在触摸事件中会调用到此接口的方法
     */
    public interface OnFooterRefreshListener {
        public void onFooterRefresh(RefreshLayout view);
    }

    /**
     * 上拉加载更多的接口，在触摸事件中会调用到此接口的方法
     * .
     */
    public interface OnHeaderRefreshListener {
        public void onHeaderRefresh(RefreshLayout view);
    }

    //隐藏下拉头的任务
    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = layoutparams.topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPACED;
                if (topMargin <= -mHeaderViewHeight) {
                    topMargin = -mHeaderViewHeight;
                    break;
                }
                publishProgress(topMargin);//触发onProgressUpdate()方法的执行
                new Thread() {
                    @Override
                    public void run() {
//						sleep(20);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
            return topMargin;
        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            layoutparams.topMargin = topMargin;
            mHeaderView.setLayoutParams(layoutparams);
            mHeaderState = PULL_TO_REFRESH;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            LogUtil.e(TAG, "RefreshLayout.HideHeaderTask.onProgressUpdate()");
            layoutparams.topMargin = topMargin[0];
            mHeaderView.setLayoutParams(layoutparams);
        }

    }

}
