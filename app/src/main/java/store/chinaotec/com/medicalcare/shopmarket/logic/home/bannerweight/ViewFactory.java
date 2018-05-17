package store.chinaotec.com.medicalcare.shopmarket.logic.home.bannerweight;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param context
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.shop_market_view_banner, null);
        if (url == null && "".equals(url)) {
            imageView.setBackgroundResource(R.drawable.error_home_banner);
        } else {
            int error = R.drawable.error_home_banner;
            MyImageLoader.displayImage(url, imageView, 0, error);
        }

        return imageView;
    }
}
