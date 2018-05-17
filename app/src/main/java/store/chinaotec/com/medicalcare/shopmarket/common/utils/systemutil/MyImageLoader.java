package store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

import store.chinaotec.com.medicalcare.R;

/**
 * 图片缓存
 *
 * @author wyk
 * @desc 需引用ImageLoader框架包, universal-image-loader-1.9.4.jar以上
 * @permission android.permission.INTERNET
 * @permission android.permission.WRITE_EXTERNAL_STORAGE
 * @crate 2015-6-22 下午6:38:27
 */
public class MyImageLoader {
    public static final String TAG = MyImageLoader.class.getSimpleName();
    /**
     * ImageLoader实例(单例)
     */
    public static ImageLoader imageLoader;
    private static MyImageLoader instance;
    private static Context context;

    private MyImageLoader() {

    }

    /**
     * 初始化ImageLoader实例
     *
     * @return
     * @desc 需在Application的onCreate调用一次
     */
    public static ImageLoader initImageLoader(Context mContext, String cachePath) {
        context = mContext;
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                context);
        builder.threadPoolSize(3);
        builder.memoryCache(new WeakMemoryCache());
        // 如果路径不为空,就设置缓存路径
        if (!TextUtils.isEmpty(cachePath)) {
            File dir = new File(cachePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            builder = builder.diskCache(new UnlimitedDiskCache(dir));
        }
        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        instance = getInstance();
        return imageLoader;
    }


    protected static MyImageLoader getInstance() {
        if (instance == null) {
            instance = new MyImageLoader();
        }
        return instance;
    }


    /**
     * 默认的加载图片设置的Builder
     *
     * @param loading 图片下载期间显示的图片
     * @param error   图片Uri为空或是错误的时候显示的图片
     * @desc
     */
    private static Builder getDefaultDisplayOptionsBuilder(int loading,
                                                           int error) {
        Builder builder = new Builder()
                .showImageOnLoading(loading) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(error) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片的缩放类型，该方法可以有效减少内存的占用
                .displayer(new FadeInBitmapDisplayer(100)); // 设置图片渐显的时间
        // .build(); // 创建配置过得DisplayImageOption对象
        return builder;
    }

    private static DisplayImageOptions getDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.drawable.error_img)
                .showImageOnFail(R.drawable.error_img)
                .showImageOnLoading(R.drawable.error_img)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new SimpleBitmapDisplayer()).build();
        return options;
    }

    /**
     * 默认的加载图片设置
     *
     * @param loading 图片下载期间显示的图片
     * @param error   图片Uri为空或是错误的时候显示的图片
     * @desc
     */
    public static DisplayImageOptions getDefaultDisplayOptions(int loading,
                                                               int error) {
        return getDefaultDisplayOptionsBuilder(loading, error).build(); // 创建配置过得DisplayImageOption对象;
    }

    /**
     * 加载网络或者本地图片
     *
     * @param url 网络图片的url或者本地图片的绝对路径
     * @param iv  要设置图片的ImageView
     * @desc 不设置loading图和error图
     * @overload loadImage(String url, final ImageView iv, final int loading,
     *final int error)
     */
    public static void loadImage(String url, ImageView iv) {
        loadImage(url, iv, R.drawable.error_type, R.drawable.error_type);
    }

    /**
     * 加载网络或者本地图片
     *
     * @param url 网络图片的url或者本地图片的绝对路径
     * @param iv  要设置图片的ImageView
     * @desc 不设置loading图和error图
     * @overload loadImage(String url, final ImageView iv, final int loading,
     *final int error)
     */
    public static void loadImage1(String url, ImageView iv) {
        loadImage(url, iv, 0, 0);
    }

    /**
     * 加载网络图片或者本地图片
     *
     * @param url     网络图片的url或者本地图片的绝对路径
     * @param iv      要设置图片的ImageView
     * @param loading 加载中的图
     * @param error   加载失败的图
     * @desc
     * @overload loadImage(String url, ImageView iv)
     */
    public static void loadImage(String url, final ImageView iv, final int loading, final int error) {
        loadImage(url, iv, -1, loading, error);
    }

    /**
     * 加载网络图片或者本地图片并填充整个屏幕的宽度
     *
     * @param url      网络图片的url或者本地图片的绝对路径
     * @param iv       要设置图片的ImageView
     * @param activity Activity
     * @desc 填充屏幕宽度
     */
    public static void loadImage(String url, ImageView iv, Activity activity,
                                 int loading, int error) {
        loadImage(url, iv, activity.getWindowManager().getDefaultDisplay().getWidth(), loading, error);
    }

    /**
     * 指定显示的宽度来加载网络图片或者本地图片
     *
     * @param url       网络图片的url或者本地图片的绝对路径
     * @param iv        要设置图片的ImageView
     * @param showWidth 要显示的宽度大小
     * @param loading   加载中的图
     * @param error     加载失败的图
     * @desc 指定宽度
     */
    public static void loadImage(String url, ImageView iv, int showWidth, int loading, int error) {
        imageLoader.loadImage(formatUri(url), getDefaultDisplayOptions(loading, error), instance.new DyhImageLoadingListener(iv, showWidth, loading, error));
    }

    /**
     * 加载网络图或者本地图
     *
     * @param url
     * @param iv
     * @desc 适合ListAdapter
     */
    public static void displayImage(String url, ImageView iv) {
        displayImage(url, iv, R.drawable.error_type, R.drawable.error_type);
    }

    /**
     * 加载网络图或者本地图
     *
     * @param url
     * @param iv
     * @param loading
     * @param error
     * @desc 适合ListAdapter
     */
    public static void displayImage(String url, final ImageView iv, final int loading, final int error) {
        displayImage(url, iv, -1, loading, error);
    }

    /**
     * 加载网络图或者本地图(填充屏幕宽)
     *
     * @param url
     * @param iv
     * @param activity
     * @param loading
     * @param error
     * @desc 适合ListAdapter
     */
    public static void displayImage(String url, ImageView iv, Activity activity, int loading, int error) {
        displayImage(url, iv, activity.getWindowManager().getDefaultDisplay().getWidth(), loading, error);
    }

    /**
     * 加载网络图或者本地图(指定宽度)
     *
     * @param url
     * @param iv
     * @param showWidth
     * @param loading
     * @param error
     * @desc 适合ListAdapter
     */
    public static void displayImage(String url, ImageView iv, int showWidth, int loading, int error) {
        imageLoader.displayImage(formatUri(url), iv, getDefaultDisplayOptions(loading, error), instance.new DyhImageLoadingListener(iv, showWidth, loading, error));
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @param iv
     * @desc 适合ListAdapter
     */
    public static void displayImage2(String url, ImageView iv) {
        imageLoader.displayImage(url, new ImageViewAware(iv), getDisplayImageOptions());
    }

    /**
     * 将ImageView的设置为指定宽度,并根据宽度等比设置高度
     *
     * @param iv
     * @param showWidth
     * @param bitmap
     * @return 设置完成的ImageView
     * @desc
     */
    public static ImageView setImageView(ImageView iv, int showWidth,
                                         Bitmap bitmap) {
        LayoutParams params = iv.getLayoutParams();
        // 比例
        double proportion = (double) showWidth / (double) bitmap.getWidth();
        // 改变高度
        int picHeight = (int) (bitmap.getHeight() * proportion);
        params.width = showWidth;
        params.height = picHeight;
        iv.setLayoutParams(params);
        // 打印
        // Log.w("图片", "宽:"+imageWidth + " 高:" + imageHeight);
        // Log.w("屏幕", "宽:"+screenWidth + " 高:" + screenHeight);
        // Log.w("比例", proportion+"");
        // Log.w("改后图片", "宽:"+params.width + " 高:" + params.height);
        // Log.w("-----------", "--------------------------------------------");
        return iv;
    }

    /**
     * 判断路径类型
     *
     * @param
     * @return
     * @desc
     */
    private static String formatUri(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String imgUrl;
        if (url.trim().startsWith("http://")) {
            imgUrl = url;
        } else {
            imgUrl = "file://" + url;
        }
        return imgUrl.trim();
    }

    public class DyhImageLoadingListener extends SimpleImageLoadingListener {
        private ImageView iv;
        private int showWidth;
        private int loading;
        private int error;

        public DyhImageLoadingListener(ImageView iv, int showWidth,
                                       int loading, int error) {
            this.iv = iv;
            this.showWidth = showWidth;
            this.loading = loading;
            this.error = error;
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (showWidth == -1) {
                iv.setImageBitmap(loadedImage);
            } else {
                ImageView adaptView = setImageView(iv, showWidth,
                        loadedImage);
                adaptView.setImageBitmap(loadedImage);
            }
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            if (loading != 0) {
                iv.setImageResource(loading);
            }
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason) {
            if (error != 0) {
                iv.setImageResource(error);
            }
        }
    }
}
