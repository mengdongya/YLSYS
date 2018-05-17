package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyEditDiseAdapter;
import store.chinaotec.com.medicalcare.adapter.MySlowDiseKindAdapter;
import store.chinaotec.com.medicalcare.dialog.EditUserDiseDialog;
import store.chinaotec.com.medicalcare.javabean.EditDiseBean;
import store.chinaotec.com.medicalcare.javabean.SlowDiseKind;
import store.chinaotec.com.medicalcare.javabean.SlowDisesBean;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 用户慢性病种类修改页面
 */
public class EditDiseActivity extends BaseActivity {

    @Bind(R.id.edit_dise_kind)
    RecyclerView editDiseKind;
    @Bind(R.id.edit_dise_detail)
    RecyclerView editDiseDetail;
    private MySlowDiseKindAdapter mySlowDiseKindAdapter;
    private int disePosition;
    private String chronicMemberId;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dise);
        ButterKnife.bind(this);
        getDataDise();
    }

    private void getDataDise() {
        //获取当前页面的编码,获取疾病的chronicMemberId
        Intent intent = getIntent();
        disePosition = intent.getIntExtra("disePosition", ResourseSum.default_value);
        if (disePosition != ResourseSum.default_value) {
            chronicMemberId = SpUtill.getString(this, ResourseSum.Medica_SP, disePosition + "chronicMemberId");
        }
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
        //获取用户登陆后的sid
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
    }

    /**
     * 获取慢性病的种类数据,并根据慢性病的种类id获取该种慢性病的数据并展示
     */
    private void getSlowDiseKind() {
        NetWorkUtill.getSlowDiseKnidData(new NetWorkUtill.SlowDiseKinidListener() {
            @Override
            public void getSlowDiseKnidData(List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes) {
                mySlowDiseKindAdapter = new MySlowDiseKindAdapter(EditDiseActivity.this, medicalTypes);
                editDiseKind.setLayoutManager(new LinearLayoutManager(EditDiseActivity.this, LinearLayoutManager.VERTICAL, false));
                editDiseKind.setAdapter(mySlowDiseKindAdapter);

                mySlowDiseKindAdapter.setOnClickListener(new MySlowDiseKindAdapter.SlowDiseLisener() {
                    @Override
                    public void itemClick(int positon) {
                        //保存当前点击慢性病种类在种类列表中的编号
                        SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "slow_dise", positon);
                        mySlowDiseKindAdapter.notifyDataSetChanged();
                        //获取当前选中慢性病的种类的id,并获取该类慢性病信息
                        int diseKindId = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, positon + "kindId");
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

            private MyEditDiseAdapter myEditDiseAdapter;

            @Override
            public void getSlowDiseData(List<SlowDisesBean.DataBean.ChronicListBean> chronicDataList) {
                editDiseDetail.setLayoutManager(new LinearLayoutManager(EditDiseActivity.this, LinearLayoutManager.VERTICAL, false));
                myEditDiseAdapter = new MyEditDiseAdapter(EditDiseActivity.this, chronicDataList);
                editDiseDetail.setAdapter(myEditDiseAdapter);

                //回传当前选择的慢性病信息
                myEditDiseAdapter.setOnClickSlowLisener(new MyEditDiseAdapter.SlowDisesEditLisener() {
                    @Override
                    public void editDise(final String chronicId, final String diseName) {
                        if (!(TextUtils.isEmpty(chronicMemberId))) {
                            EditUserDiseDialog editUserDiseDialog = new EditUserDiseDialog();
                            editUserDiseDialog.show(getSupportFragmentManager(), "editUserDise");

                            editUserDiseDialog.setEditDiseSure(new EditUserDiseDialog.SureEditDiseListener() {
                                @Override
                                public void sureEdit() {
                                    MyLog.d("开始修改用户的慢性病..sid.." + sid + "..疾病的id.." + chronicId + "..疾病的memberid.." + chronicMemberId);
                                    //修改用户的慢性病种类
                                    NetWorkUtill.editUserDise(sid, chronicMemberId, chronicId, new NetWorkUtill.EditUserDiseListener() {
                                        @Override
                                        public void editDise(EditDiseBean.DataBean data) {
                                            //修改用户慢性病种类的标志
                                            SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "editUserDise", true);
                                            //保存修改后的疾病名字,用于修改首页tablaout的标题对应的疾病名字的修改
                                            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "editDiseName", diseName);
                                            //保存修改后的疾病名字,用于修改首页疾病各项详情页上的疾病名字的修改
                                            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, disePosition + "chronicName", diseName);
                                            //修改成功后保存,修改后的疾病种类的id,已经疾病生成的对应的病人id
                                            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, disePosition + "patientId", data.getPatientId());
                                            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, disePosition + "chronicId", data.getChronicId());
                                            MyLog.d("修改后疾病的id.."+data.getChronicId()+"..修改后疾病对应病人的id.."+data.getPatientId());
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
