package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * 此类描述的是:自定义RadioButton
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年10月15日 下午5:53:26
 */
public class BGABadgeRadioButton1 extends RadioButton implements BGABadgeable {
    private BGABadgeViewHelper1 mBadgeViewHeler;

    public BGABadgeRadioButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBadgeViewHeler = new BGABadgeViewHelper1(this, context, attrs, BGABadgeViewHelper1.BadgeGravity.RightTop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeViewHeler.drawBadge(canvas);
    }

    @Override
    public void showCriclePointBadge() {
        mBadgeViewHeler.showCirclePointBadge();
    }

    @Override
    public void showTextBadge(String badgeText) {
        mBadgeViewHeler.showTextBadge(badgeText);
    }

    @Override
    public void hiddenBadge() {
        mBadgeViewHeler.hiddenBadge();
    }

    @Override
    public void showDrawableBadge(Bitmap bitmap) {
        mBadgeViewHeler.showDrawable(bitmap);
    }

}