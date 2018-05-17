package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.SwipeMenulistview;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

public class SwipeMenuLayout extends FrameLayout {
    private static final int CONTENT_VIEW_ID = 1;
    private static final int MENU_VIEW_ID = 2;
    private static final int STATE_CLOSE = 0;
    private static final int STATE_OPEN = 1;
    private View mContentView;
    private SwipeMenuView mMenuView;
    private int mDownX;
    private int state;
    private GestureDetectorCompat mGestureDetector;
    private GestureDetector.OnGestureListener mGestureListener;
    private boolean isFling;
    private int MIN_FLING;
    private int MAX_VELOCITYX;
    private ScrollerCompat mOpenScroller;
    private ScrollerCompat mCloseScroller;
    private int mBaseX;
    private int position;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView) {
        this(contentView, menuView, (Interpolator) null, (Interpolator) null);
    }

    public SwipeMenuLayout(View contentView, SwipeMenuView menuView, Interpolator closeInterpolator, Interpolator openInterpolator) {
        super(contentView.getContext());
        this.state = 0;
        this.MIN_FLING = this.dp2px(15);
        this.MAX_VELOCITYX = -this.dp2px(500);
        this.mCloseInterpolator = closeInterpolator;
        this.mOpenInterpolator = openInterpolator;
        this.mContentView = contentView;
        this.mMenuView = menuView;
        this.mMenuView.setLayout(this);
        this.init();
    }

    private SwipeMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.state = 0;
        this.MIN_FLING = this.dp2px(15);
        this.MAX_VELOCITYX = -this.dp2px(500);
    }

    private SwipeMenuLayout(Context context) {
        super(context);
        this.state = 0;
        this.MIN_FLING = this.dp2px(15);
        this.MAX_VELOCITYX = -this.dp2px(500);
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
        this.mMenuView.setPosition(position);
    }

    @SuppressWarnings("ResourceType")
    private void init() {
//        this.setLayoutParams(new LayoutParams(-1, -2));
        LayoutParams lp = new LayoutParams(-1, -2);
        this.setLayoutParams(lp);

        this.mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                SwipeMenuLayout.this.isFling = false;
                return true;
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > (float) SwipeMenuLayout.this.MIN_FLING && velocityX < (float) SwipeMenuLayout.this.MAX_VELOCITYX) {
                    SwipeMenuLayout.this.isFling = true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        };
        this.mGestureDetector = new GestureDetectorCompat(this.getContext(), this.mGestureListener);
        if (this.mCloseInterpolator != null) {
            this.mCloseScroller = ScrollerCompat.create(this.getContext(), this.mCloseInterpolator);
        } else {
            this.mCloseScroller = ScrollerCompat.create(this.getContext());
        }

        if (this.mOpenInterpolator != null) {
            this.mOpenScroller = ScrollerCompat.create(this.getContext(), this.mOpenInterpolator);
        } else {
            this.mOpenScroller = ScrollerCompat.create(this.getContext());
        }

        LayoutParams contentParams = new LayoutParams(-1, -2);
        this.mContentView.setLayoutParams(contentParams);
        if (this.mContentView.getId() < 1) {
            this.mContentView.setId(1);
        }

        this.mMenuView.setId(2);
        this.mMenuView.setLayoutParams(new LayoutParams(-2, -2));
        this.addView(this.mContentView);
        this.addView(this.mMenuView);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public boolean onSwipe(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                this.mDownX = (int) event.getX();
                this.isFling = false;
                break;
            case 1:
                if (!this.isFling && (float) this.mDownX - event.getX() <= (float) (this.mMenuView.getWidth() / 2)) {
                    this.smoothCloseMenu();
                    return false;
                }

                this.smoothOpenMenu();
                break;
            case 2:
                int dis = (int) ((float) this.mDownX - event.getX());
                if (this.state == 1) {
                    dis += this.mMenuView.getWidth();
                }

                this.swipe(dis);
        }

        return true;
    }

    public boolean isOpen() {
        return this.state == 1;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void swipe(int dis) {
        if (dis > this.mMenuView.getWidth()) {
            dis = this.mMenuView.getWidth();
        }

        if (dis < 0) {
            dis = 0;
        }

        this.mContentView.layout(-dis, this.mContentView.getTop(), this.mContentView.getWidth() - dis, this.getMeasuredHeight());
        this.mMenuView.layout(this.mContentView.getWidth() - dis, this.mMenuView.getTop(), this.mContentView.getWidth() + this.mMenuView.getWidth() - dis, this.mMenuView.getBottom());
    }

    public void computeScroll() {
        if (this.state == 1) {
            if (this.mOpenScroller.computeScrollOffset()) {
                this.swipe(this.mOpenScroller.getCurrX());
                this.postInvalidate();
            }
        } else if (this.mCloseScroller.computeScrollOffset()) {
            this.swipe(this.mBaseX - this.mCloseScroller.getCurrX());
            this.postInvalidate();
        }

    }

    public void smoothCloseMenu() {
        this.state = 0;
        this.mBaseX = -this.mContentView.getLeft();
        this.mCloseScroller.startScroll(0, 0, this.mBaseX, 0, 350);
        this.postInvalidate();
    }

    public void smoothOpenMenu() {
        this.state = 1;
        this.mOpenScroller.startScroll(-this.mContentView.getLeft(), 0, this.mMenuView.getWidth(), 0, 350);
        this.postInvalidate();
    }

    public void closeMenu() {
        if (this.mCloseScroller.computeScrollOffset()) {
            this.mCloseScroller.abortAnimation();
        }

        if (this.state == 1) {
            this.state = 0;
            this.swipe(0);
        }

    }

    public void openMenu() {
        if (this.state == 0) {
            this.state = 1;
            this.swipe(this.mMenuView.getWidth());
        }

    }

    public View getContentView() {
        return this.mContentView;
    }

    public SwipeMenuView getMenuView() {
        return this.mMenuView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, this.getContext().getResources().getDisplayMetrics());
    }

    @SuppressWarnings("ResourceType")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mMenuView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 1073741824));
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mContentView.layout(0, 0, this.getMeasuredWidth(), this.mContentView.getMeasuredHeight());
        this.mMenuView.layout(this.getMeasuredWidth(), 0, this.getMeasuredWidth() + this.mMenuView.getMeasuredWidth(), this.mContentView.getMeasuredHeight());
    }

    public void setMenuHeight(int measuredHeight) {
        LayoutParams params = (LayoutParams) this.mMenuView.getLayoutParams();
        if (params.height != measuredHeight) {
            params.height = measuredHeight;
            this.mMenuView.setLayoutParams(this.mMenuView.getLayoutParams());
        }

    }
}

