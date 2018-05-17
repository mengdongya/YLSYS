package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.fragment.book.MedicalBookCatalogueFragment;
import store.chinaotec.com.medicalcare.fragment.book.MedicalBookDetailFragment;
import store.chinaotec.com.medicalcare.javabean.MedicalBookIntro;

/**
 * Created by wjc on 2018/3/1 0001.
 * 医疗图书介绍
 */

public class MedicalBookIntroduceActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_bar)
    TextView tvTitleBar;
    @Bind(R.id.iv_medical_book)
    ImageView ivMedicalBook;
    @Bind(R.id.tv_medical_name)
    TextView tvMedicalName;
    @Bind(R.id.tv_medical_author)
    TextView tvMedicalAuthor;
    @Bind(R.id.tl_medical_intro)
    TabLayout tlMedicalIntro;
    @Bind(R.id.viewpager_medical_intro)
    ViewPager viewpagerMedicalIntro;
    private int bookId;
    private MedicalBookIntro.Data.MedicalBookDetailDto detailDto;
    private List<String> titleList;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_book_intro);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        bookId = getIntent().getIntExtra("bookId", 0);
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleList = new ArrayList();
        titleList.add("详情");
        titleList.add("目录");
        fragmentList = new ArrayList();
    }

    private void initData() {
        String url = MyUrl.MEDICAL_BOOK_INTRO;
        OkHttpUtils.post().url(url).addParams("bookId",bookId+"").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalBookIntro medicalBookIntro = JSON.parseObject(response, MedicalBookIntro.class);
                detailDto = medicalBookIntro.getData().getMedicalBookDetailDto();
                setData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {
        tvTitleBar.setText(detailDto.getName());
        Glide.with(this).load(detailDto.getImg()).into(ivMedicalBook);
        tvMedicalName.setText(detailDto.getName());
        tvMedicalAuthor.setText("作者："+detailDto.getAuthor());
        fragmentList.add(MedicalBookDetailFragment.newInstance(detailDto.getDetail()));
        fragmentList.add(MedicalBookCatalogueFragment.newInstance(detailDto.getMedicalBookLists()));

        viewpagerMedicalIntro.setAdapter(new MyBookAdapter(getSupportFragmentManager()));
        tlMedicalIntro.setupWithViewPager(viewpagerMedicalIntro);
    }

    class MyBookAdapter extends FragmentPagerAdapter{

        public MyBookAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
