package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.SlodiseAdapter;
import store.chinaotec.com.medicalcare.fragment.slowdise.SlowDiseFragment;
import store.chinaotec.com.medicalcare.javabean.ChronicBean;
import store.chinaotec.com.medicalcare.listener.SlowDiseListener;
import store.chinaotec.com.medicalcare.utill.ModueUtill;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 慢性病管理页面
 */
public class SlowDiseaseActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.slow_tablayout)
    TabLayout slowTablayout;
    @Bind(R.id.slow_viewpager)
    ViewPager slowViewpager;
    @Bind(R.id.add_slow_dise)
    Button addSlowDise;
    @Bind(R.id.delete_dise)
    FrameLayout deleteDise;

    //当前用户最初的慢性病信息集合
    private List<String> normalDiseList = new ArrayList();
    //当前用户最初的慢性病信息集合对应的fragment集合
    private List<Fragment> normalFragmentList = new ArrayList();
    //获取当前用户已添加的慢性病信息集合
    private List<String> addDiseList = new ArrayList();
    //获取当前用户已添加的慢性病信息对应的fragment集合
    private List<Fragment> addFragmentList = new ArrayList();
    private String sid;
    private SlodiseAdapter slodiseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slow_disease);
        ButterKnife.bind(this);
        initDiseData();
        setDataAdapter();
        getSlowDiseData();
        initListener();
    }

    private void initListener() {
        addSlowDise.setOnClickListener(this);
        deleteDise.setOnClickListener(this);
        //首页页面变化监听处理
        slowViewpager.addOnPageChangeListener(new SlowDiseListener(this, addFragmentList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //编辑病人信息的标志
        boolean editPatientInfo = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "editPatientInfo");
        //删除用户慢性病种类的标志
        boolean deleteDise = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "deleteDise");
        //修改用户慢性病种类的标志
        boolean editUserDise = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "editUserDise");
        //添加疾病信息的标志
        boolean addChronic = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "addChronic");
        //添加病情记录的标志
        boolean diseaseRecords = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "diseaseRecords");
        //当前页面fragment的编号
        int currentItem = slowViewpager.getCurrentItem();
        //刷新页面展示修改后的病人信息,病人信息的修改标志editPatientInfo,修改完信息后默认为false
        if (editPatientInfo) {
            ((SlowDiseFragment) addFragmentList.get(currentItem)).reshSlodiseData(this, currentItem);
            //修改病人信息回到首页数据展示后,修改标志默认为 false
            SpUtill.putBoolen(this, ResourseSum.Medica_SP, "editPatientInfo", false);
        } else if (editUserDise) {
            editDiseKind(currentItem);
        } else if (addChronic) {
            addDiseInfoShow(currentItem);
        } else if (deleteDise) {
            deleteDiseKind(currentItem);
        }else if (diseaseRecords){
            ((SlowDiseFragment) addFragmentList.get(currentItem)).reshSlodiseData(this, currentItem);
            //添加病程记录的标志
            SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP,"diseaseRecords",false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加用户慢性病
            case R.id.add_slow_dise:
                Intent intent = new Intent(SlowDiseaseActivity.this, AddSlowDiseActivity.class);
                int addPosition = getAddPosition();
                intent.putExtra("addPosition", addPosition);
                startActivity(intent);
                break;
            //删除用户的慢性病种类
            case R.id.delete_dise:
                startActivity(new Intent(SlowDiseaseActivity.this, DeleteDiseActivity.class));
                break;
        }
    }

    /**
     * @param currentItem 当前显示页面的编号
     *                    修改用户的疾病种类回到首页后页面 标题展示
     */
    private void editDiseKind(int currentItem) {
        String editDiseName = SpUtill.getString(this, ResourseSum.Medica_SP, "editDiseName");
        addDiseList.set(currentItem, editDiseName);
        slodiseAdapter.setData(addFragmentList, addDiseList);
        //刷新展示修改后的疾病种类
        ((SlowDiseFragment) addFragmentList.get(currentItem)).reshSlodiseData(this, currentItem);
        //修改用户慢性病种类的标志,在修改后慢性病首页数据刷新后为false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "editUserDise", false);
    }

    /**
     * @param currentItem 当前显示页面的编号
     *                    删除用户的疾病后首页数据展示
     */
    private void deleteDiseKind(int currentItem) {
        String deleteDiseName = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "deleteDiseName");
        addDiseList.remove(deleteDiseName);
        addFragmentList.remove(currentItem);
        slodiseAdapter.setData(addFragmentList, addDiseList);
        //删除用户慢性病种类的标志,在删除后慢性病首页数据刷新后为false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "deleteDise", false);
    }

    /**
     * 慢性病添加页面添加完慢性病之后,回到当前页面显示慢性病以及展示页面
     */
    private void addDiseInfoShow(int currentItem) {
        //添加疾病对应展示页面的编号
        int addPosition = getAddPosition();
        //添加的疾病种类的名字
        String addChronicName = SpUtill.getString(this, ResourseSum.Medica_SP, addPosition + "chronicName");
        //当前用户已经添加了慢性病种类,但是本次进入添加页面没有添加
        if (addPosition == ResourseSum.default_value) {
            slodiseAdapter.setData(addFragmentList, addDiseList);
        } else {  //当前用户已经添加了慢性病种类,本次进入添加页面添加
            addDiseList.add(addFragmentList.size(), addChronicName);
            addFragmentList.add(addFragmentList.size(), SlowDiseFragment.newInstance(addPosition));
            slodiseAdapter.setData(addFragmentList, addDiseList);
        }
        //添加用户慢性病种类的标志此时为false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "addChronic", false);
    }

    /**
     * @return 添加疾病的页面编号
     * 获取添加慢性病种类的页面编号
     */
    private int getAddPosition() {
        int addPosition;
        if (addFragmentList.size() == 0) {
            addPosition = 0;
        } else {
            addPosition = addFragmentList.size();
        }
        MyLog.d("onClick..添加页面编号.." + addPosition);
        return addPosition;
    }

    /**
     * 联网获取用户的慢性病数据并展示
     */
    private void getSlowDiseData() {
        NetWorkUtill.getUserChronicData(sid, new NetWorkUtill.GetChronicDataListener() {
            @Override
            public void getChronicData(List<String> patientIdList, List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> diseList, List<String> diseMemberIdList, boolean dataEmpty) {
                if (dataEmpty) {
                    ModueUtill.saveDiseInfo(patientIdList, diseList, diseMemberIdList);
                    ModueUtill.addSlowDisePage(addFragmentList, diseList, slowTablayout, addDiseList);
                    //适配器展示
                    slodiseAdapter.setData(addFragmentList, addDiseList);
                } else {
                    //适配器展示
                    slodiseAdapter.setData(normalFragmentList, normalDiseList);
                }
            }

            @Override
            public void getCourseOfDiseaseContent(List<ChronicBean.DataBean.ChronicPatientDtosBean> chronicPatientDtos) {

            }

        });
    }

    /**
     * 适配器展示页面慢性病页面
     */
    private void setDataAdapter() {
        //慢性病页面碎片展示
        slodiseAdapter = new SlodiseAdapter(getSupportFragmentManager());
        slowViewpager.setAdapter(slodiseAdapter);
        slowViewpager.setOffscreenPageLimit(0);
        slowTablayout.setupWithViewPager(slowViewpager);
    }

    private void initDiseData() {
        //用户慢性病种类的添加标志,打开慢性病管理页面时默认为false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "addChronic", false);
        //删除用户慢性病种类的标志,在删除后慢性病首页数据刷新后为false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "deleteDise", false);
        //修改用户慢性病种类的标志,在修改后慢性病首页数据刷新后为false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "editUserDise", false);
        //修改病人信息回到首页数据展示后,修改标志默认为 false
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "editPatientInfo", false);
        //修改病人信息成功,此时病人信息除了id还有其他信息
        SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "patientHasData", false);
        //添加病程记录的标志
        SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP,"diseaseRecords",false);
        //删除疾病的开关
        SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "delete", false);
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        //没有慢性病信息时标题,fragment数据源初始化
        normalDiseList.add("");
        normalFragmentList.add(SlowDiseFragment.newInstance(ResourseSum.default_value));
        slowTablayout.addTab(slowTablayout.newTab().setText(normalDiseList.get(0)));
    }
}
