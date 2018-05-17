package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MySlowDiseCotentAdapter;
import store.chinaotec.com.medicalcare.adapter.MySlowDiseKindAdapter;
import store.chinaotec.com.medicalcare.javabean.AddDiseBean;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ModueUtill;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.javabean.SlowDiseKind;
import store.chinaotec.com.medicalcare.javabean.SlowDisesBean;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 慢性病添加页面
 */
public class AddSlowDiseActivity extends BaseActivity {
    @Bind(R.id.slow_disease_kind)
    RecyclerView slowDiseaseKind;
    @Bind(R.id.slow_disease_detail)
    RecyclerView slowDiseaseDetail;

    private MySlowDiseKindAdapter mySlowDiseKindAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);
        ButterKnife.bind(this);
        getDataDise();
    }

    private void getDataDise() {
        //获取慢性病种类信息并展示
        getSlowDiseKind();
        //默认显示慢性病信息
        defauShowDise();
    }

    private void defauShowDise() {
        //获取当前点击条目的编号
        int position = SpUtill.getInt(this, ResourseSum.Medica_SP, "slow_dise");
        if (position != ResourseSum.default_value) {
            //获取当前被点击慢性病的种类的id
            int knidId = SpUtill.getInt(this, ResourseSum.Medica_SP, position + "kindId");
            if (knidId != ResourseSum.default_value) {
                getSubClassSlowDise(knidId);
            }
        }
    }

    /**
     * 获取慢性病的种类数据,并根据慢性病的种类id获取该种慢性病的数据并展示
     */
    private void getSlowDiseKind() {
        NetWorkUtill.getSlowDiseKnidData(new NetWorkUtill.SlowDiseKinidListener() {
            @Override
            public void getSlowDiseKnidData(List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes) {
                mySlowDiseKindAdapter = new MySlowDiseKindAdapter(AddSlowDiseActivity.this, medicalTypes);
                slowDiseaseKind.setLayoutManager(new LinearLayoutManager(AddSlowDiseActivity.this, LinearLayoutManager.VERTICAL, false));
                slowDiseaseKind.setAdapter(mySlowDiseKindAdapter);

                mySlowDiseKindAdapter.setOnClickListener(new MySlowDiseKindAdapter.SlowDiseLisener() {
                    @Override
                    public void itemClick(int positon) {
                        //保存当前点击慢性病种类在种类列表中的编号
                        SpUtill.putInt(AddSlowDiseActivity.this, ResourseSum.Medica_SP, "slow_dise", positon);
                        mySlowDiseKindAdapter.notifyDataSetChanged();
                        //获取当前选中慢性病的种类的id,并获取该类慢性病信息
                        int diseKindId = SpUtill.getInt(AddSlowDiseActivity.this, ResourseSum.Medica_SP, positon + "kindId");
                        if (diseKindId != ResourseSum.default_value) {
                            //根据分慢性病分类id获取该类慢性病信息
                            getSubClassSlowDise(diseKindId);
                        }
                    }
                });
            }
        });
    }

    /**
     * @param diseKindId 慢性病的种类id
     *                   获取该类慢性病的信息
     */
    private void getSubClassSlowDise(final int diseKindId) {
        NetWorkUtill.getSlowDiseDataByKinid(diseKindId, new NetWorkUtill.GetSlowDiseDataListener() {
            @Override
            public void getSlowDiseData(List<SlowDisesBean.DataBean.ChronicListBean> chronicDataList) {
                slowDiseaseDetail.setLayoutManager(new LinearLayoutManager(AddSlowDiseActivity.this, LinearLayoutManager.VERTICAL, false));
                MySlowDiseCotentAdapter mySlowDiseCotentAdapter = new MySlowDiseCotentAdapter(AddSlowDiseActivity.this, chronicDataList);
                slowDiseaseDetail.setAdapter(mySlowDiseCotentAdapter);
                //回传当前选择的慢性病信息
                mySlowDiseCotentAdapter.setOnClickSlowLisener(new MySlowDiseCotentAdapter.SlowDisesLisener() {
                    @Override
                    public void clickItem(final String slowDiseName, final String slowDiseId) {
                        Intent intent = getIntent();
                        //慢性病首页添加疾病种类的页面编号
                        final int addPosition = intent.getIntExtra("addPosition", ResourseSum.default_value);
                        //获取当前用户登录后返回的sid
                        String sid = SpUtill.getString(AddSlowDiseActivity.this, ResourseSum.Medica_SP, "saveSid");
                        //添加用户慢性病种类
                        NetWorkUtill.addSlowDiseData(slowDiseId, sid, new NetWorkUtill.AddChoronicDiseaseListener() {
                            @Override
                            public void addChronicDisease(boolean add, String json) {
                                if (add) {
                                    BaseUtill.toastUtil("该种疾病已添加");
                                } else {
                                    ModueUtill.addDiseKindSaveData(json, addPosition, slowDiseName, slowDiseId);
                                    //用户添加慢性病信息的成功的标志
                                    SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "addChronic", true);
                                    //添加的慢性病的展示页面编号
                                    BaseUtill.toastUtil("添加成功");
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
