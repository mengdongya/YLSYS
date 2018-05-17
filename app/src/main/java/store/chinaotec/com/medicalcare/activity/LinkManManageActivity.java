package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.SimpleFragmentPagerAdapter;
import store.chinaotec.com.medicalcare.fragment.LinkManagerFragment;
import store.chinaotec.com.medicalcare.fragment.MessageManageMentFragment;
import store.chinaotec.com.medicalcare.javabean.LinkMessageNum;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.BadgeView;

/**
 * Created by HYY on 2018/3/9.
 * <p>
 * 联系人管理
 */

public class LinkManManageActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_include_title_view;
    private TextView tv_include_title_right;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private List<String> mPageTitleList = new ArrayList<String>();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private List<Integer> mBadgeCountList = new ArrayList<Integer>();
    private List<BadgeView> mBadgeViews;
    private SimpleFragmentPagerAdapter mPagerAdapter;
    private String sid;
    private LinkMessageNum.DataBean messageNumData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkman_manage);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        messageNumData = (LinkMessageNum.DataBean) bundle.getSerializable("messageNumData");
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_right = (TextView) findViewById(R.id.tv_include_title_right);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tv_include_title_right.setOnClickListener(this);
        tv_include_title_view.setText("联系人管理");
        initFragments();
        initView();

    }

    private void initFragments() {
        int num = messageNumData.getNoticeNum() + messageNumData.getRemindNum();
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        mPageTitleList.add("联系人");
        mPageTitleList.add("消息");
        mBadgeCountList.add(0);
        mBadgeCountList.add(num);
        mFragmentList.add(LinkManagerFragment.getInstance(mPageTitleList.get(0)));
        mFragmentList.add(MessageManageMentFragment.getInstance(messageNumData));
    }

    private void initView() {
        mPagerAdapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager(), mFragmentList, mPageTitleList, mBadgeCountList);
        mViewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        initBadgeViews();
        setUpTabBadge();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                switch (tabPosition) {
                    case 0:
                        //点击联系人
                        break;
                    case 1:
                        //点击消息
                        mBadgeCountList.set(1, 0);
                        setUpTabBadge();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initBadgeViews() {
        if (mBadgeViews == null) {
            mBadgeViews = new ArrayList<>();
            for (int i = 0; i < mFragmentList.size(); i++) {
                BadgeView tmp = new BadgeView(this);
                tmp.setBadgeMargin(0, 6, 10, 0);
                tmp.setTextSize(10);
                mBadgeViews.add(tmp);
            }
        }
    }

    /**
     * 设置Tablayout上的标题的角标
     */
    private void setUpTabBadge() {
        // 2. 最实用
        for (int i = 0; i < mFragmentList.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            // 更新Badge前,先remove原来的customView,否则Badge无法更新
            View customView = tab.getCustomView();
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(customView);
                }
            }
            // 更新CustomView
            tab.setCustomView(mPagerAdapter.getTabItemView(i));
        }
        // 需加上以下代码,不然会出现更新Tab角标后,选中的Tab字体颜色不是选中状态的颜色
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().setSelected(true);
    }
//    设置消息条数
//    findViewById(R.id.btn_add_badge).setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            mBadgeCountList.set(1, count++);
//            setUpTabBadge();
//        }
//    });


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_include_title_right:
                //点击添加联系人
                startActivityForResult(new Intent(this, AddLinkManManageActivity.class),1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK){
//            getResponseData();
//        }
    }
}
