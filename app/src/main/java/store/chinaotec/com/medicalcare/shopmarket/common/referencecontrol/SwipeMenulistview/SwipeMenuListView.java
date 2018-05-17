package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.SwipeMenulistview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SwipeMenuListView extends ListView {
    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;
    private int MAX_Y = 5;
    private int MAX_X = 3;
    private float mDownX;
    private float mDownY;
    private int mTouchState;
    private int mTouchPosition;
    private SwipeMenuLayout mTouchView;
    private OnSwipeListener mOnSwipeListener;
    private SwipeMenuCreator mMenuCreator;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;

    public SwipeMenuListView(Context context) {
        super(context);
        this.init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.MAX_X = this.dp2px(this.MAX_X);
        this.MAX_Y = this.dp2px(this.MAX_Y);
        this.mTouchState = 0;
    }

    public void setAdapter(final ListAdapter adapter) {
        super.setAdapter(new SwipeMenuAdapter(this.getContext(), adapter) {
            public void createMenu(SwipeMenu menu) {
                if (SwipeMenuListView.this.mMenuCreator != null) {
                    SwipeMenuListView.this.mMenuCreator.create(menu);
                }

            }

            public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
                if (SwipeMenuListView.this.mOnMenuItemClickListener != null) {
                    SwipeMenuListView.this.mOnMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
                }

                if (SwipeMenuListView.this.mTouchView != null) {
                    SwipeMenuListView.this.mTouchView.smoothCloseMenu();
                }

            }
        });
    }

    public Interpolator getOpenInterpolator() {
        return this.mOpenInterpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        this.mOpenInterpolator = interpolator;
    }

    public Interpolator getCloseInterpolator() {
        return this.mCloseInterpolator;
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        this.mCloseInterpolator = interpolator;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() != 0 && this.mTouchView == null) {
            return super.onTouchEvent(ev);
        } else {
            int action = MotionEventCompat.getActionMasked(ev);
            action = ev.getAction();
            switch (action) {
                case 0:
                    int oldPos = this.mTouchPosition;
                    this.mDownX = ev.getX();
                    this.mDownY = ev.getY();
                    this.mTouchState = 0;
                    this.mTouchPosition = this.pointToPosition((int) ev.getX(), (int) ev.getY());
                    if (this.mTouchPosition == oldPos && this.mTouchView != null && this.mTouchView.isOpen()) {
                        this.mTouchState = 1;
                        this.mTouchView.onSwipe(ev);
                        return true;
                    }

                    View view = this.getChildAt(this.mTouchPosition - this.getFirstVisiblePosition());
                    if (this.mTouchView != null && this.mTouchView.isOpen()) {
                        this.mTouchView.smoothCloseMenu();
                        this.mTouchView = null;
                        return super.onTouchEvent(ev);
                    }

                    if (view instanceof SwipeMenuLayout) {
                        this.mTouchView = (SwipeMenuLayout) view;
                    }

                    if (this.mTouchView != null) {
                        this.mTouchView.onSwipe(ev);
                    }
                    break;
                case 1:
                    if (this.mTouchState == 1) {
                        if (this.mTouchView != null) {
                            this.mTouchView.onSwipe(ev);
                            if (!this.mTouchView.isOpen()) {
                                this.mTouchPosition = -1;
                                this.mTouchView = null;
                            }
                        }

                        if (this.mOnSwipeListener != null) {
                            this.mOnSwipeListener.onSwipeEnd(this.mTouchPosition);
                        }

                        ev.setAction(3);
                        super.onTouchEvent(ev);
                        return true;
                    }
                    break;
                case 2:
                    float dy = Math.abs(ev.getY() - this.mDownY);
                    float dx = Math.abs(ev.getX() - this.mDownX);
                    if (this.mTouchState == 1) {
                        if (this.mTouchView != null) {
                            this.mTouchView.onSwipe(ev);
                        }

                        this.getSelector().setState(new int[1]);
                        ev.setAction(3);
                        super.onTouchEvent(ev);
                        return true;
                    }

                    if (this.mTouchState == 0) {
                        if (Math.abs(dy) > (float) this.MAX_Y) {
                            this.mTouchState = 2;
                        } else if (dx > (float) this.MAX_X) {
                            this.mTouchState = 1;
                            if (this.mOnSwipeListener != null) {
                                this.mOnSwipeListener.onSwipeStart(this.mTouchPosition);
                            }
                        }
                    }
            }

            return super.onTouchEvent(ev);
        }
    }

    public void smoothOpenMenu(int position) {
        if (position >= this.getFirstVisiblePosition() && position <= this.getLastVisiblePosition()) {
            View view = this.getChildAt(position - this.getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                this.mTouchPosition = position;
                if (this.mTouchView != null && this.mTouchView.isOpen()) {
                    this.mTouchView.smoothCloseMenu();
                }

                this.mTouchView = (SwipeMenuLayout) view;
                this.mTouchView.smoothOpenMenu();
            }
        }

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, this.getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int var1, SwipeMenu var2, int var3);
    }

    public interface OnSwipeListener {
        void onSwipeStart(int var1);

        void onSwipeEnd(int var1);
    }
}
