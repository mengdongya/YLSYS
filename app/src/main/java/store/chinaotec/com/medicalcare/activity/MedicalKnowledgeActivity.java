package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MedicalTypeContentAdapter;
import store.chinaotec.com.medicalcare.adapter.MedicalTypeContentLevelAdapter;
import store.chinaotec.com.medicalcare.adapter.MedicalTypeNameAdapter;
import store.chinaotec.com.medicalcare.fragment.medicalcare.MedicalKnowledgeFragment;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;
import store.chinaotec.com.medicalcare.javabean.SpecialHallBean;


/**
 * Created by wjc on 2017/10/20 0020.
 * 大众医学知识
 */
public class MedicalKnowledgeActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_bar)
    TextView tvTitle;
    @Bind(R.id.rv_medical_kinds)
    RecyclerView rvMedicalKinds;
    @Bind(R.id.rv_medical_content)
    RecyclerView rvMedicalContent;
    @Bind(R.id.tab_medical_knowledge)
    TabLayout tabLayout;
    @Bind(R.id.tab_medical_knowledge_view_pager)
    ViewPager viewPager;
    private MedicalBookBean medicalBookBean;
    private String title;
    private List<Fragment> fragment;
    private List<String> titles;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_knowledge);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(title);
        fragment = new ArrayList<>();
        titles = new ArrayList<>();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
//        viewPager.setOffscreenPageLimit(10);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.medical_book_list).addParams("type", "10").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
//                setData();
                if ("0".equals(medicalBookBean.getResponseCode())){
                    if (medicalBookBean.getData().getMedicalTypeList().size() > 1){
                        for (int i = 1;i < medicalBookBean.getData().getMedicalTypeList().size();i++){
                            fragment.add(MedicalKnowledgeFragment.newInstance(medicalBookBean.getData().getMedicalTypeList().get(i).getMedicalTypeId()));
                            titles.add(medicalBookBean.getData().getMedicalTypeList().get(i).getTypeName());
                        }

                        myViewPagerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void setData() {
        rvMedicalKinds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final MedicalTypeNameAdapter medicalTypeNameAdapter = new MedicalTypeNameAdapter(this, medicalBookBean.getData().getMedicalTypeList());
        rvMedicalKinds.setAdapter(medicalTypeNameAdapter);
        medicalTypeNameAdapter.notifyDataSetChanged();

        rvMedicalContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MedicalTypeContentAdapter medicalTypeContentAdapter = new MedicalTypeContentAdapter(this, medicalBookBean.getData().getMedicalTypeList());
        rvMedicalContent.setAdapter(medicalTypeContentAdapter);

        medicalTypeNameAdapter.setOnItemClick(new MedicalTypeNameAdapter.MedicalTypeClickListener() {
            @Override
            public void click(int position) {
                medicalTypeNameAdapter.setIndex(position);
                medicalTypeNameAdapter.notifyDataSetChanged();
                if (position == 0) {
                    rvMedicalContent.setLayoutManager(new LinearLayoutManager(MedicalKnowledgeActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvMedicalContent.setAdapter(new MedicalTypeContentAdapter(MedicalKnowledgeActivity.this,
                            medicalBookBean.getData().getMedicalTypeList()));
                } else {
                    rvMedicalContent.setLayoutManager(new GridLayoutManager(MedicalKnowledgeActivity.this, 2));
                    MedicalTypeContentLevelAdapter contentLevelAdapter = new MedicalTypeContentLevelAdapter(MedicalKnowledgeActivity.this,
                            medicalBookBean.getData().getMedicalTypeList().get(position).getDataList().get(0));
                    rvMedicalContent.setAdapter(contentLevelAdapter);
                }
            }
        });
    }
}
