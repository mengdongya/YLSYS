package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyTestResulAllClassAdapter;
import store.chinaotec.com.medicalcare.adapter.MyTextResultKindAdapter;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.javabean.TextResultBaseBean;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * 检查结果解读页面
 */
public class TestResultActivity extends BaseActivity{

    private static final String TAG = "TestResultActivity";
    @Bind(R.id.test_result_kind)
    RecyclerView testResultKind;
    @Bind(R.id.test_result_content)
    LRecyclerView testResultContent;
    private SharedPreferences sharedPreferences;
    private Context context = this;
    private List<TextResultBean.DataBean.MedicalTypeListBean> medicalTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_check);
        ButterKnife.bind(this);
        getDataDise();
    }
    private void getDataDise() {
        //初始化SP文件对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP,MODE_PRIVATE);
        //默认显示""
        sharedPreferences.edit().putInt("text_dise", 0).commit();
        //获取慢性病种类信息
        getSlowDiseKind();
    }

    private void getSlowDiseKind() {
        OkGo.get(MyUrl.SUDEN_DISE_RESULT).params("type", 3).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                medicalTypeList = MyApp.getGson().fromJson(s, TextResultBean.class).getData().getMedicalTypeList();
                //种类设置适配器
                final MyTextResultKindAdapter myTextResultKindAdapter = new MyTextResultKindAdapter(context, sharedPreferences, medicalTypeList);
                testResultKind.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                //打印疾病种类的数据源
                MyLog.object(medicalTypeList);
                testResultKind.setAdapter(myTextResultKindAdapter);
                //打开页面默认显示
                int textDise = sharedPreferences.getInt("text_dise", 505);
                if (textDise != 505) {
                    if (textDise == 0) {
                        List<TextResultBaseBean> diseListZero = getPositonZeroData(textDise);
                        MyTestResulAllClassAdapter myTestResulAllClassAdapter = new MyTestResulAllClassAdapter(context, diseListZero);
                        testResultContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        testResultContent.setAdapter(myTestResulAllClassAdapter);
                        //点击跳转详情页面
                        kindClickJump(diseListZero, myTestResulAllClassAdapter);
                    } else {
                        List<TextResultBaseBean> diseList = getPositonOtherData(textDise);
                        MyTestResulAllClassAdapter myTestResulAllClassAdapter = new MyTestResulAllClassAdapter(context, diseList);
                        testResultContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        testResultContent.setAdapter(myTestResulAllClassAdapter);
                        //点击跳转详情页面
                        kindClickJump(diseList, myTestResulAllClassAdapter);
                    }
                }
                //sp保存选中的慢性病种类的position
                myTextResultKindAdapter.setOnClickLisenerSlowDisease(new MyTextResultKindAdapter.TextResultLisener() {
                    @Override
                    public void itemClick(int position) {
                        //保存当前点击的慢性病种类的坐标,并刷新适配器
                        sharedPreferences.edit().putInt("text_dise", position).commit();
                        String typeImage = medicalTypeList.get(position).getTypeImage();
                        myTextResultKindAdapter.notifyDataSetChanged();
                        //根据当前点击位置展示数据
                        if (position == 0) {
                            final List<TextResultBaseBean> diseListZero = getPositonZeroData(position);
                            MyTestResulAllClassAdapter myTestResulAllClassAdapter = new MyTestResulAllClassAdapter(context, diseListZero);
                            testResultContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            testResultContent.setAdapter(myTestResulAllClassAdapter);
                            //点击跳转详情页面
                            kindClickJump(diseListZero, myTestResulAllClassAdapter);
                        } else {
                            List<TextResultBaseBean> diseList = getPositonOtherData(position);
                            MyTestResulAllClassAdapter myTestResulAllClassAdapter = new MyTestResulAllClassAdapter(context, diseList);
                            testResultContent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            testResultContent.setAdapter(myTestResulAllClassAdapter);
                            //点击跳转详情页面
                            kindClickJump(diseList, myTestResulAllClassAdapter);
                        }
                    }
                });
            }
        });
    }

    /**
     * @param diseListZero               种类病内容数据源
     * @param myTestResulAllClassAdapter 疾病内容展示适配器
     *                                   内容条目点击跳转详情页面
     */
    private void kindClickJump(final List<TextResultBaseBean> diseListZero, MyTestResulAllClassAdapter myTestResulAllClassAdapter) {
        myTestResulAllClassAdapter.setOnTextClickLisener(new MyTestResulAllClassAdapter.TestResultContentLisener() {
            @Override
            public void itemClick(int position) {
                //保存突发伤病的memberSickDealId
                sharedPreferences.edit().putInt("memberSickDealId", diseListZero.get(position).getMemberSickDealId()).commit();
                sharedPreferences.edit().putString("diseName", diseListZero.get(position).getName()).commit();
                startActivity(new Intent(MyApp.getContext(), SudDiseDetActivity.class));
            }
        });
    }

    @NonNull
    private List<TextResultBaseBean> getPositonOtherData(int position) {
        List<TextResultBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> memberSickDealList = medicalTypeList.get(position).getDataList().get(0).getMemberSickDealList();
        List<TextResultBaseBean> diseList = new ArrayList();
        for (int i = 0; i < memberSickDealList.size(); i++) {
            String name = memberSickDealList.get(i).getName();
            int memberSickDealId = memberSickDealList.get(i).getMemberSickDealId();
            diseList.add(new TextResultBaseBean(name, memberSickDealId));
        }
        return diseList;
    }

    @NonNull
    private List<TextResultBaseBean> getPositonZeroData(int position) {
        List<TextResultBean.DataBean.MedicalTypeListBean.DataListBean> dataList = medicalTypeList.get(position).getDataList();
        List<TextResultBaseBean> diseListZero = new ArrayList();
        List<TextResultBean.DataBean.MedicalTypeListBean.DataListBean.MemberSickDealListBean> memberSickDealListZero;
        for (int i = 0; i < dataList.size(); i++) {
            memberSickDealListZero = dataList.get(i).getMemberSickDealList();
            for (int j = 0; j < memberSickDealListZero.size(); j++) {
                String name = memberSickDealListZero.get(j).getName();
                int memberSickDealId = memberSickDealListZero.get(j).getMemberSickDealId();
                diseListZero.add(new TextResultBaseBean(name, memberSickDealId));
            }
        }
        return diseListZero;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putInt("text_dise", 0).commit();
    }
}
