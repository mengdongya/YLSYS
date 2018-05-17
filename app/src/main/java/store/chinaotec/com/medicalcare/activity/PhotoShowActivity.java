package store.chinaotec.com.medicalcare.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * 用户交流图片展示
 */
public class PhotoShowActivity extends BaseActivity {

    @Bind(R.id.send_photo)
    ImageView sendPhoto;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);
        ButterKnife.bind(this);
        //初始化sp对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);

        String photo_show = sharedPreferences.getString("photo_show", "");
        Glide.with(this).load(photo_show).into(sendPhoto);
    }
}
