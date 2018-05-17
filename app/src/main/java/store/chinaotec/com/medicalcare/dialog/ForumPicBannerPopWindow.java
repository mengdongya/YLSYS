package store.chinaotec.com.medicalcare.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.ForumPicBannerAdapter;
import store.chinaotec.com.medicalcare.localalbum.AlbumViewPager;
import store.chinaotec.com.medicalcare.view.PinchImageView;

/**
 * Created by seven on 2017/10/31 0031.
 */
public class ForumPicBannerPopWindow extends PopupWindow {
    private final ForumPicBannerAdapter adapter;
    private Activity activity;
    private AlbumViewPager viewPager;
    private TextView tv_page, tv_cancel;
    private List<View> pagers;

    public ForumPicBannerPopWindow(Activity activity,String[] urls,int id){
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwin_banner, null);
        setContentView(view);
        viewPager = (AlbumViewPager) view.findViewById(R.id.viewpager);
        tv_page = (TextView) view.findViewById(R.id.page);
        tv_cancel = (TextView) view.findViewById(R.id.cancel);

        pagers = new ArrayList<>();
        adapter = new ForumPicBannerAdapter(pagers);

        setViewPager(urls, id);

        setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setViewPager(final String[] urls, int id) {
        if (urls.length > 0) {
            pagers.clear();
            for (int i = 0; i < urls.length; i++) {
                View view = LayoutInflater.from(activity).inflate(R.layout.forum_pic_banner, null);
                PinchImageView imageView = (PinchImageView) view.findViewById(R.id.news_banner);
                Glide.with(activity).load(urls[i]).fitCenter().diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);
                pagers.add(view);
            }
            adapter.notifyDataSetChanged();
        }
        tv_page.setText((id + 1) + "/" + urls.length);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                tv_page.setText((arg0 + 1) + "/" + urls.length);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        viewPager.setCurrentItem(id);

    }


    public void show(View view) {
        showAtLocation(view, Gravity.CENTER, 0, 0);
        backgroundAlpha(0f);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
