package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MySubClassDetAdapter;
import store.chinaotec.com.medicalcare.adapter.MySudenDiseKindAdapter;
import store.chinaotec.com.medicalcare.adapter.MySudenDiseKindAllAdapter;
import store.chinaotec.com.medicalcare.javabean.SuddenDiseBean;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 突发伤病管理页面
 */
public class SudDiseActivity extends BaseActivity {
    @Bind(R.id.sudden_diaease_kinds)
    RecyclerView suddenDiaeaseKinds;
    @Bind(R.id.sudden_diaease_content)
    RecyclerView suddenDiaeaseContent;
    private Context mContext = this;
    private SharedPreferences sharedPreferences;
    private MySubClassDetAdapter mySubClassDetAdapter;
    private SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean dataListBean;
    private TextView tv_include_title_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudden_treat);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("突发伤病处置");
        ButterKnife.bind(this);
        initBaseData();
        getIntenData();
    }

    private void initBaseData() {
        //初始化SP对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, mContext.MODE_PRIVATE);
    }

    private void getIntenData() {
        MyLog.d("突发伤病获取成功........getIntenData.........");
        OkGo.get(MyUrl.SUDEN_DISE_RESULT).params("type", 1).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MyLog.d("突发伤病获取成功" + s);
                        //获取突发伤病的种类
                        final List<SuddenDiseBean.DataBean.MedicalTypeListBean> medicalTypeList = MyApp.getGson().fromJson(s, SuddenDiseBean.class).getData().getMedicalTypeList();
                        //突发伤病种类适配器展示
                        suddenDiaeaseKinds.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        final MySudenDiseKindAdapter mySudenDiseKindAdapter = new MySudenDiseKindAdapter(mContext, medicalTypeList, sharedPreferences);
                        suddenDiaeaseKinds.setAdapter(mySudenDiseKindAdapter);
                        //打开当前页面默认显示"全部"类疾病
                        SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "suden_dise", 0);
                        mySudenDiseKindAdapter.notifyDataSetChanged();
                        suddenDiaeaseContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        suddenDiaeaseContent.setAdapter(new MySudenDiseKindAllAdapter(mContext, medicalTypeList));
                        //根据当前选中的疾病种类,显示当前种类的疾病信息
                        int suden_dise = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, "suden_dise");
                        if (suden_dise != ResourseSum.default_value) {
                            if (suden_dise == 0) {  //突发伤病第一项分类展示所有的突发伤病信息
                                suddenDiaeaseContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                                suddenDiaeaseContent.setAdapter(new MySudenDiseKindAllAdapter(mContext, medicalTypeList));
                            } else { //展示其他分类的突发伤病的信息
                                suddenDiaeaseContent.setLayoutManager(new GridLayoutManager(mContext, 3));
                                dataListBean = medicalTypeList.get(suden_dise).getDataList().get(0);
                                mySubClassDetAdapter = new MySubClassDetAdapter(mContext, dataListBean);
                                suddenDiaeaseContent.setAdapter(mySubClassDetAdapter);
//                                openDetailPage(dataListBean);
                            }
                        }
                        //展示当前选中分类慢性病数据
                        showSubClassDiseData(medicalTypeList, mySudenDiseKindAdapter);
                    }
                });
    }

    /**
     * @param medicalTypeList        后台获取数据源
     * @param mySudenDiseKindAdapter 分类适配器
     */
    private void showSubClassDiseData(final List<SuddenDiseBean.DataBean.MedicalTypeListBean> medicalTypeList, final MySudenDiseKindAdapter mySudenDiseKindAdapter) {
        //点击选中当前突发伤病分类并展示数据
        mySudenDiseKindAdapter.setOnItemClick(new MySudenDiseKindAdapter.AllClassListener() {
            @Override
            public void click(int position) {
                //保存当前突发伤病种类的编号
                SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "suden_dise", position);
                mySudenDiseKindAdapter.notifyDataSetChanged();
                if (position == 0) {  //突发伤病第一项分类展示所有的突发伤病信息
                    suddenDiaeaseContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                    suddenDiaeaseContent.setAdapter(new MySudenDiseKindAllAdapter(mContext, medicalTypeList));
                } else { //展示其他分类的突发伤病的信息
                    suddenDiaeaseContent.setLayoutManager(new GridLayoutManager(mContext, 3));
                    dataListBean = medicalTypeList.get(position).getDataList().get(0);
                    mySubClassDetAdapter = new MySubClassDetAdapter(mContext, dataListBean);
                    suddenDiaeaseContent.setAdapter(mySubClassDetAdapter);
                    openDetailPage(dataListBean);
                }
            }
        });
    }

    //
    private void openDetailPage(final SuddenDiseBean.DataBean.MedicalTypeListBean.DataListBean dataListBean) {
        mySubClassDetAdapter.setOnClickSuddenTreatListener(new MySubClassDetAdapter.SuddenTreatLisener() {
            @Override
            public void suddenTreatClick(int disease) {
                //保存突发伤病的memberSickDealId
//                SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "memberSickDealId", dataListBean.getMemberSickDealList().get(disease).getMemberSickDealId());
//                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "diseName", dataListBean.getMemberSickDealList().get(disease).getName());
//                mContext.startActivity(new Intent(mContext, SudDiseDetActivity.class));
                Intent intent = new Intent(mContext, SudDiseDetActivity.class);
                intent.putExtra("memberSickDealId", dataListBean.getMemberSickDealList().get(disease).getMemberSickDealId());
                intent.putExtra("diseName", dataListBean.getMemberSickDealList().get(disease).getName());
                mContext.startActivity(intent);
            }
        });
    }
}
