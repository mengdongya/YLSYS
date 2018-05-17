package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * 此类描述的是:自定义BUTTON
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年10月22日 上午9:26:17
 */
public class BGABadgeRadioButton extends RadioButton implements BGABadgeable {
    private BGABadgeViewHelper mBadgeViewHeler;

    public BGABadgeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBadgeViewHeler = new BGABadgeViewHelper(this, context, attrs, BGABadgeViewHelper.BadgeGravity.RightTop);
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