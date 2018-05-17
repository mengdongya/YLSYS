package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import store.chinaotec.com.medicalcare.R;

/**
 * 此类描述的是: 为控件添加数量提醒
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年10月15日 下午1:22:32
 */
public class BGABadgeViewHelper1 {
    /**
     * 徽章背景与宿主控件上下边缘间距离
     */
    int mBadgeVerticalMargin;
    private Bitmap mBitmap;
    private BGABadgeable mBadgeable;
    private Paint mBadgePaint;
    /**
     * 徽章背景色
     */
    private int mBadgeBgColor;
    /**
     * 徽章文本的颜色
     */
    private int mBadgeTextColor;
    /**
     * 徽章文本字体大小
     */
    private int mBadgeTextSize;
    /**
     * 徽章背景与宿主控件左右边缘间距离
     */
    private int mBadgeHorizontalMargin;
    /***
     * 徽章文本边缘与徽章背景边缘间的距离
     */
    private int mBadgePadding;
    /**
     * 徽章文本
     */
    private String mBadgeText;
    /**
     * 徽章文本所占区域大小
     */
    private Rect mBadgeNumberRect;
    /**
     * 是否显示Badge
     */
    private boolean mIsShowBadge;
    /**
     * 徽章在宿主控件中的位置
     */
    private BadgeGravity mBadgeGravity;

    public BGABadgeViewHelper1(BGABadgeable badgeable, Context context,
                               AttributeSet attrs, BadgeGravity defaultBadgeGravity) {
        mBadgeable = badgeable;
        initDefaultAttrs(context, defaultBadgeGravity);
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
    }

    private static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    private static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }

    private void initDefaultAttrs(Context context,
                                  BadgeGravity defaultBadgeGravity) {
        mBadgeNumberRect = new Rect();
        mBadgeBgColor = Color.RED;
        mBadgeTextColor = Color.WHITE;
        mBadgeTextSize = sp2px(context, 10);

        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);

        mBadgePadding = dp2px(context, 3);
        mBadgeVerticalMargin = dp2px(context, 10);
        mBadgeHorizontalMargin = dp2px(context, 15);

        mBadgeGravity = defaultBadgeGravity;
        mIsShowBadge = false;

        mBadgeText = null;

        mBitmap = null;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BGABadgeView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BGABadgeView_badge_bgColor) {
            mBadgeBgColor = typedArray.getColor(attr, mBadgeBgColor);
        } else if (attr == R.styleable.BGABadgeView_badge_textColor) {
            mBadgeTextColor = typedArray.getColor(attr, mBadgeTextColor);
        } else if (attr == R.styleable.BGABadgeView_badge_textSize) {
            mBadgeTextSize = typedArray.getDimensionPixelSize(attr,
                    mBadgeTextSize);
        } else if (attr == R.styleable.BGABadgeView_badge_verticalMargin) {
            mBadgeVerticalMargin = typedArray.getDimensionPixelSize(attr,
                    mBadgeVerticalMargin);
        } else if (attr == R.styleable.BGABadgeView_badge_horizontalMargin) {
            mBadgeHorizontalMargin = typedArray.getDimensionPixelSize(attr,
                    mBadgeHorizontalMargin);
        } else if (attr == R.styleable.BGABadgeView_badge_padding) {
            mBadgePadding = typedArray.getDimensionPixelSize(attr,
                    mBadgePadding);
        } else if (attr == R.styleable.BGABadgeView_badge_gravity) {
            int ordinal = typedArray.getInt(attr, mBadgeGravity.ordinal());
            mBadgeGravity = BadgeGravity.values()[ordinal];
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        mBadgePaint.setTextSize(mBadgeTextSize);
    }

    public void drawBadge(Canvas canvas) {
        if (mIsShowBadge) {
            if (mBitmap != null) {
                drawDrawableBadge(canvas);
            } else {
                drawTextBadge(canvas);
            }
        }
    }

    public void setmBadgeVerticalMargin(int mBadgeVerticalMargin) {
        this.mBadgeVerticalMargin = mBadgeVerticalMargin;
    }

    public void setmBadgeHorizontalMargin(int mBadgeHorizontalMargin) {
        this.mBadgeHorizontalMargin = mBadgeHorizontalMargin;
    }

    /**
     * 绘制图像徽章
     *
     * @param canvas
     */
    private void drawDrawableBadge(Canvas canvas) {
        float badgeLeft = mBadgeable.getWidth() - mBadgeHorizontalMargin
                - mBitmap.getWidth();
        float badgeTop = mBadgeVerticalMargin;
        switch (mBadgeGravity) {
            case RightTop:
                badgeTop = mBadgeVerticalMargin;
                break;
            case RightCenter:
                badgeTop = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
                break;
            case RightBottom:
                badgeTop = mBadgeable.getHeight() - mBitmap.getHeight()
                        - mBadgeVerticalMargin;
                break;
            case Center:
                badgeLeft = (mBadgeable.getWidth() - mBitmap.getWidth()) / 2;
                badgeTop = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
                break;
            default:
                break;
        }
        canvas.drawBitmap(mBitmap, badgeLeft, badgeTop, mBadgePaint);
    }

    /**
     * 绘制文字徽章
     *
     * @param canvas
     */
    private void drawTextBadge(Canvas canvas) {
        String badgeText = "";
        if (!TextUtils.isEmpty(mBadgeText)) {
            badgeText = String.valueOf(mBadgeText);

        }
        // 获取文本宽所占宽高
        mBadgePaint.getTextBounds(badgeText, 0, badgeText.length(),
                mBadgeNumberRect);
        // 计算徽章背景的宽高
        float badgeHeight = mBadgeNumberRect.height() + mBadgePadding * 2;
        float badgeWidth;
        // 计算徽章背景左右的值
        float badgeRight = mBadgeable.getWidth() - mBadgeHorizontalMargin;
        // 当mBadgeText的长度为1时，计算出来的高度会比宽度大，此时设置宽度等于高度
        if (badgeText.length() == 1) {
            badgeWidth = badgeHeight;
        } else {
            badgeWidth = mBadgeNumberRect.width() + mBadgePadding * 2;
        }

        // 计算徽章背景上下的值
        float badgeTop = mBadgeVerticalMargin;
        float badgeBottom = mBadgeable.getHeight() - mBadgeVerticalMargin;
        switch (mBadgeGravity) {
            case RightTop:
                badgeBottom = badgeTop + badgeHeight;
                break;
            case Center:
                badgeRight = (badgeRight + badgeWidth + mBadgeHorizontalMargin) / 2;
            case RightCenter:
                badgeTop = (mBadgeable.getHeight() - badgeHeight) / 2;
                badgeBottom = badgeTop + badgeHeight;
                break;
            case RightBottom:
                badgeTop = badgeBottom - badgeHeight;
                break;
            default:
                break;
        }

        // 计算徽章背景左右的值
        float badgeLeft = badgeRight - badgeWidth;
        // 设置徽章背景色
        mBadgePaint.setColor(mBadgeBgColor);
        // 绘制徽章背景
        canvas.drawRoundRect(new RectF(badgeLeft, badgeTop, badgeRight,
                badgeBottom), badgeHeight / 2, badgeHeight / 2, mBadgePaint);

        if (!TextUtils.isEmpty(mBadgeText)) {
            // 设置徽章文本颜色
            mBadgePaint.setColor(mBadgeTextColor);
            // initDefaultAttrs方法中设置了mBadgeText居中，此处的x为徽章背景的中心点y
            float x = badgeLeft + badgeWidth / 2;
            // 注意：绘制文本时的y是指文本底部，而不是文本的中间
            float y = badgeBottom - mBadgePadding;
            // 绘制徽章文本
            canvas.drawText(badgeText, x, y, mBadgePaint);
        }
    }

    public void showCirclePointBadge() {
        showTextBadge(null);
    }

    public void showTextBadge(String badgeText) {
        mBitmap = null;
        mBadgeText = badgeText;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public void hiddenBadge() {
        mIsShowBadge = false;
        mBitmap = null;
        mBadgeable.postInvalidate();
    }

    public void showDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public enum BadgeGravity {
        RightTop, RightCenter, RightBottom, Center
    }
}