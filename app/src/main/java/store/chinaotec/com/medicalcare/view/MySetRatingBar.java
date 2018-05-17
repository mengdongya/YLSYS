package store.chinaotec.com.medicalcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/2/20 0020.
 * 自定义星级控件可以设置星星的间距,大小等
 */
public class MySetRatingBar extends LinearLayout {
    /**
     * 星星总数
     */
    private int starCount;
    /**
     * 每个星星的大小
     */
    private float starImageSize;
    /**
     * 每个星星的间距
     */
    private float starPadding;
    /**
     * 星星的显示数量，支持小数点
     */
    private float starStep;
    /**
     * 空白的默认星星图片
     */
    private Drawable starEmptyDrawable;
    /**
     * 选中后的星星填充图片
     */
    private Drawable starFillDrawable;
    /**
     * 半颗星的图片
     */
    private Drawable starHalfDrawable;

    public MySetRatingBar(Context context) {
        super(context);

    }

    public MySetRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        starImageSize = mTypedArray.getDimension(R.styleable.RatingBar_starImageSize, 30);
        starPadding = mTypedArray.getDimension(R.styleable.RatingBar_starPadding, 10);
        starStep = mTypedArray.getFloat(R.styleable.RatingBar_starStep, 1.0f);
        starCount = mTypedArray.getInteger(R.styleable.RatingBar_starCount, 5);
        starEmptyDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starEmpty);
        starFillDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starFill);
        starHalfDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starHalf);
        mTypedArray.recycle();
        for (int i = 0; i < starCount; ++i) {
            final ImageView imageView = getStarImageView();
            imageView.setImageDrawable(starEmptyDrawable);
            addView(imageView);
        }
        setStar(starStep);
    }

    public MySetRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置半星的图片资源文件
     *
     * @param starHalfDrawable
     */
    public void setStarHalfDrawable(Drawable starHalfDrawable) {
        this.starHalfDrawable = starHalfDrawable;
    }

    /**
     * 设置满星的图片资源文件
     *
     * @param starFillDrawable
     */
    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    /**
     * 设置空白和默认的图片资源文件
     *
     * @param starEmptyDrawable
     */
    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    /**
     * 设置星星的大小
     *
     * @param starImageSize
     */
    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    /**
     * 设置星星的总数
     *
     * @param starCount
     */
    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    /**
     * 设置每颗星星的参数
     *
     * @return
     */
    private ImageView getStarImageView() {
        ImageView imageView = new ImageView(getContext());
        LayoutParams layout = new LayoutParams(
                Math.round(starImageSize), Math.round(starImageSize));//设置每颗星星在线性布局的大小
        layout.setMargins(0, 0, Math.round(starPadding), 0);//设置每颗星星在线性布局的间距
        imageView.setLayoutParams(layout);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(starEmptyDrawable);
        imageView.setMinimumWidth(20);
        imageView.setMaxHeight(20);
        return imageView;
    }

    /**
     * 设置星星的个数
     *
     * @param rating
     */
    public void setStar(float rating) {
        this.starStep = rating;
        //浮点数的整数部分
        int fint = (int) rating;
        BigDecimal b1 = new BigDecimal(Float.toString(rating));
        BigDecimal b2 = new BigDecimal(Integer.toString(fint));
        //浮点数的小数部分
        float fPoint = b1.subtract(b2).floatValue();
        //设置选中的星星
        for (int i = 0; i < fint; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
        }
        //设置没有选中的星星
        for (int i = fint; i < starCount; i++) {
            ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
        }
        //小数点默认增加半颗星
        if (fPoint > 0) {
            ((ImageView) getChildAt(fint)).setImageDrawable(starHalfDrawable);
        }
    }
}
