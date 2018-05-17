package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.AbsCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyHelpDoctorAdapter;
import store.chinaotec.com.medicalcare.dialog.DeleteDoctorDialog;
import store.chinaotec.com.medicalcare.javabean.HelpDoctorBeean;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 添加求救医生页面
 */
public class HelpDoctorShowActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.add_help_doctor)
    TextView addHelpDoctor;
    @Bind(R.id.help_doctor_recycleviw)
    RecyclerView helpDoctorRecycleviw;
    @Bind(R.id.no_data)
    RelativeLayout noData;
    @Bind(R.id.resh_help_doctor)
    SwipeRefreshLayout reshHelpDoctor;

    private String sid;
    //求救医生信息数据默认加载的页面编号
    private int dex = 1;
    //开始上拉翻页的标志
    private boolean preshLoad;
    //添加第一页加载的数据
    private boolean addFirstPageData = false;
    private List<HelpDoctorBeean.DataBean.MemberCalloutListBean> getCalloutList = new ArrayList();
    private MyHelpDoctorAdapter myHelpDoctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help_doctr);
        ButterKnife.bind(this);
        initBasicData();
        initListener();
    }

    private void initListener() {
        //下拉加载显示第一页
        reshHelpDoctor.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyLog.d("onRefresh..下拉翻页显示第一页...");
                getHelpDoctors(1);
                dex = 1;
            }
        });
    }

    private void initBasicData() {
        addHelpDoctor.setOnClickListener(this);
        //用户登陆后返回的sid
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        //添加求救医生的标志
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "addDoctor", false);
        //修改求救医生的标志
        SpUtill.putBoolen(this, ResourseSum.Medica_SP, "editDoctor", false);
        setHelpDoctAdapter();
        //默认加载展示第一页数据
        addFirstPageData = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHelpDoctors(1);
    }

    private void getHelpDoctors(int pageIndex) {
        NetWorkUtill.getHelpDoctors(sid, pageIndex, new NetWorkUtill.GetHelpDoctorsListener() {
            @Override
            public void getHelpDoctorList(List<HelpDoctorBeean.DataBean.MemberCalloutListBean> memberCalloutList, boolean getData) {
                if (getData) {
                    if (preshLoad) {
                        //获取到数据时,上拉翻页
                        MyLog.d("开始上拉翻页获取数据条数.." + memberCalloutList.size());
                        //保存当前获取到的翻页加载的数据
                        getCalloutList.addAll(memberCalloutList);
                        MyLog.d("上拉翻页后获取到的数据总条数.." + getCalloutList.size());
                        myHelpDoctorAdapter.setData(getCalloutList);
                    } else {
                        //获取到数据时,普通加载
                        noData.setVisibility(View.GONE);
                        helpDoctorRecycleviw.setVisibility(View.VISIBLE);
                        //取消下拉加载刷新动画
                        reshHelpDoctor.setRefreshing(false);
                        //是否修改过求救医生信息的标志
                        boolean editDoctor = SpUtill.getBoolen(HelpDoctorShowActivity.this, ResourseSum.Medica_SP, "editDoctor");
                        if (editDoctor) {
                            //获取到数据时,修改完求救医生信息后,重新加载第一页求救医生信息
                            if (getCalloutList.size() != 0) {
                                getCalloutList.clear();
                                getCalloutList.addAll(memberCalloutList);
                                myHelpDoctorAdapter.setData(getCalloutList);
                            }
                        }
                        if (addFirstPageData) {
                            //获取到数据时,保存第一页求救医生信息数据
                            if (getCalloutList.size() != 0) {
                                getCalloutList.clear();
                            }
                            getCalloutList.addAll(memberCalloutList);
                            myHelpDoctorAdapter.setData(getCalloutList);
                        }
                    }
                } else {
                    if (preshLoad) {
                        //没有获取到数据时,上拉翻页
                        if (getCalloutList.size() != 0) {
                            MyLog.d("本次上拉翻页没有获取到数据..目前数据条数..." + getCalloutList.size());
                            myHelpDoctorAdapter.setData(getCalloutList);
                        }
                        preshLoad = false;
                    } else {
                        //没有获取到数据时,普通加载操作
                        reshHelpDoctor.setRefreshing(false);
                        helpDoctorRecycleviw.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void setHelpDoctAdapter() {
        //设置适配器展示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApp.getContext(), LinearLayoutManager.VERTICAL, false);
        helpDoctorRecycleviw.setLayoutManager(linearLayoutManager);
        myHelpDoctorAdapter = new MyHelpDoctorAdapter(this);
        helpDoctorRecycleviw.setAdapter(myHelpDoctorAdapter);
        //添加上拉翻页
        helpDoctorRecycleviw.addOnScrollListener(new MyScrollListener(linearLayoutManager, myHelpDoctorAdapter));
        //点击条目跳转到修改信息页面
        myHelpDoctorAdapter.setOnItemClickListener(new MyHelpDoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HelpDoctorBeean.DataBean.MemberCalloutListBean emberCalloutListBean) {
                Intent intent = new Intent(HelpDoctorShowActivity.this, EditHelpDoctorActivity.class);
                intent.putExtra("memberCalBean", emberCalloutListBean);
                startActivity(intent);
            }
        });
        //长按删除该条信息
        myHelpDoctorAdapter.setOnLongItemClickListener(new MyHelpDoctorAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(final String memberCalloutId, final int adapterPosition) {
                DeleteDoctorDialog deleteDoctorDialog = new DeleteDoctorDialog();
                deleteDoctorDialog.show(getSupportFragmentManager(), "deleteDoctor");
                deleteDoctorDialog.setDeleteDoctor(new DeleteDoctorDialog.DeleteDoctorListener() {
                    @Override
                    public void deleteDoctor() {
                        //滑动删除当前求救医生信息
                        String saveSid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
                        MyLog.d("删除求救医生..memberCalloutId.." + memberCalloutId + "..当前用户的sid.." + saveSid);
                        NetWorkUtill.deleteDoctor(saveSid, memberCalloutId, new AbsCallback() {
                            @Override
                            public void onSuccess(Object o, Call call, Response response) {
                                getHelpDoctors(1);
                            }

                            @Override
                            public Object convertSuccess(Response response) throws Exception {
                                return null;
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_help_doctor:
                startActivity(new Intent(this, AddHelpDoctorActivity.class));
                break;
        }
    }

    public class MyScrollListener extends RecyclerView.OnScrollListener {
        private LinearLayoutManager linearLayoutManager;
        private MyHelpDoctorAdapter myHelpDoctorAdapter;
        private int lastVisibleItemPosition;

        /**
         * @param linearLayoutManager 列表布局管理器
         * @param myHelpDoctorAdapter 列表适配器
         *                            recycleview添加上拉加载更多
         */
        public MyScrollListener(LinearLayoutManager linearLayoutManager, MyHelpDoctorAdapter myHelpDoctorAdapter) {
            this.linearLayoutManager = linearLayoutManager;
            this.myHelpDoctorAdapter = myHelpDoctorAdapter;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == myHelpDoctorAdapter.getItemCount()) {
                //上拉开始加载下一页
                preshLoad = true;
                dex++;
                MyLog.d("onScrollStateChanged..上拉翻页开始..翻页编码.." + dex);
                getHelpDoctors(dex);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //列表最后一个可见的条目编号
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        }
    }
}
