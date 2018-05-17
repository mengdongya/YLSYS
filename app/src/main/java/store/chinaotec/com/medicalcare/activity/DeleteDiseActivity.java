package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyDiseAdapter;
import store.chinaotec.com.medicalcare.dialog.DeleteUserDiseDialog;
import store.chinaotec.com.medicalcare.javabean.ChronicBean;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 疾病种类删除页面
 */
public class DeleteDiseActivity extends BaseActivity {

    @Bind(R.id.no_dise)
    RelativeLayout noDise;
    @Bind(R.id.user_dise_recycleviw)
    RecyclerView userDiseRecycleviw;
    @Bind(R.id.resh_help_doctor)
    SwipeRefreshLayout reshHelpDoctor;
    private MyDiseAdapter myDiseAdapter;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_dise);
        ButterKnife.bind(this);
        setDiseAdapter();
        getUserDisease();
        initListener();
    }

    /**
     * 删除疾病适配器展示数据
     */
    private void setDiseAdapter() {
        userDiseRecycleviw.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myDiseAdapter = new MyDiseAdapter(this);
        userDiseRecycleviw.setAdapter(myDiseAdapter);
    }

    private void initListener() {
        myDiseAdapter.setDeleteUserDise(new MyDiseAdapter.DeleteDiseListener() {
            @Override
            public void deleteDise(final int disePosition, final String diseName) {
                final DeleteUserDiseDialog deleteUserDiseDialog = new DeleteUserDiseDialog();
                deleteUserDiseDialog.show(getSupportFragmentManager(), "deleteDiease");

                deleteUserDiseDialog.setDeleteDiseSure(new DeleteUserDiseDialog.SureDeleteDiseListener() {
                    @Override
                    public void sureDelete() {
                        final String chronicMemberId = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, disePosition + "chronicMemberId");
                        NetWorkUtill.deleteUserDise(sid, chronicMemberId, new NetWorkUtill.DeleteUserDiseListener() {

                            private int diseKind = 0;

                            @Override
                            public void deleteUserDise() {
                                MyLog.d("删除用户疾病种类...成功..chronicMemberId.." + chronicMemberId + "..用户的sid.." + sid);
                                //删除用户疾病种类的标志
                                SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "deleteDise", true);
                                //保存删除疾病种类的名字
                                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "deleteDiseName", diseName);
                                //删除疾病的种类数
                                int deleteDiseKind = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, "deleteDiseKind");
                                if (deleteDiseKind != ResourseSum.default_value) {
                                    deleteDiseKind = deleteDiseKind+1;
                                    SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "deleteDiseKind", deleteDiseKind);
                                } else {
                                    diseKind = diseKind+1;
                                    SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "deleteDiseKind", diseKind);
                                }
                                SpUtill.putBoolen(MyApp.getContext(), ResourseSum.Medica_SP, "delete", true);
                                finish();
                                //重新获取用户的慢性病种类数据
//                                getUserDisease();
//                                myDiseAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
            }
        });
    }

    /**
     * 获取用户的慢性病种类数据
     */
    private void getUserDisease() {
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        NetWorkUtill.getUserChronicData(sid, new NetWorkUtill.GetChronicDataListener() {
            @Override
            public void getChronicData(List<String> patientIdList, List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> diseList, List<String> diseMemberIdList, boolean dataEmpty) {
                if (dataEmpty) {
                    myDiseAdapter.reshData(diseList);
                    noDise.setVisibility(View.GONE);
                } else {
                    //用户的疾病种类删除完之后,保存默认显示页面的编号
                    SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition", ResourseSum.default_value);
                    userDiseRecycleviw.setVisibility(View.GONE);
                    noDise.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void getCourseOfDiseaseContent(List<ChronicBean.DataBean.ChronicPatientDtosBean> chronicPatientDtos) {

            }

        });
    }
}
