package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import store.chinaotec.com.medicalcare.adapter.MySudenDiseDetalAdapter;
import store.chinaotec.com.medicalcare.javabean.BaseContentBean;
import store.chinaotec.com.medicalcare.javabean.ContentBean;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * 突发伤病详情页面
 */
public class SudDiseDetActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.online_doctor)
    Button onlineDoctor;
    @Bind(R.id.intellg_treatment)
    Button intellgTreatment;
    @Bind(R.id.one_call_help)
    Button oneCallHelp;

    @Bind(R.id.sudden_dise_content)
    RecyclerView suddenDiseContent;
    private TextView tv_include_title_view;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudden_detail);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        ButterKnife.bind(this);
        getDiseDetail();
        initListener();
    }

    private void getDiseDetail() {
        //初始化SP对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, Context.MODE_PRIVATE);
//        int memberSickDealId = sharedPreferences.getInt("memberSickDealId", 0);

        int memberSickDealId = getIntent().getIntExtra("memberSickDealId", 0);

        if (memberSickDealId != 202) {
            OkGo.post(MyUrl.CONTENT).params("memberSickDealId", memberSickDealId).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    ContentBean contentBean = MyApp.getGson().fromJson(s, ContentBean.class);
                    if (contentBean.getResponseCode() == 500) {//TODO

                    } else {
                        ContentBean.DataBean data = contentBean.getData();
                        List<BaseContentBean> list = new ArrayList();
                        //获取标题  内容并截取得到对应的数组
                        String name = data.getName();
                        String sickDetailInfo = data.getSickDetailInfo();
                        String[] nameAry = name.split("\\|\\|\\|\\|\\|");
                        String[] infoAry = sickDetailInfo.split("\\|\\|\\|\\|\\|");
                        BaseContentBean baseContentBean = null;
                        //初始化list集合
                        for (int i = 0; i < nameAry.length; i++) {
                            baseContentBean = new BaseContentBean(nameAry[i], infoAry[i]);
                            list.add(baseContentBean);
                        }
                        suddenDiseContent.setLayoutManager(new LinearLayoutManager(MyApp.getContext(), LinearLayoutManager.VERTICAL, false));
                        suddenDiseContent.setAdapter(new MySudenDiseDetalAdapter(MyApp.getContext(), list));
                    }
                }
            });
        }
    }

    private void initListener() {
        onlineDoctor.setOnClickListener(this);
        intellgTreatment.setOnClickListener(this);
        oneCallHelp.setOnClickListener(this);
        //获取当前疾病的名字展示
//        String chronicname = sharedPreferences.getString("diseName", "");
        String chronicname = getIntent().getStringExtra("diseName");
        if (!(TextUtils.isEmpty(chronicname))) {
            tv_include_title_view.setText(chronicname);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.online_doctor:
                boolean login = sharedPreferences.getBoolean("login", false);
                if (login) {
                    startActivity(new Intent(this, HelpDoctorShowActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.intellg_treatment:
                startActivity(new Intent(this, SeeDoctorActivity.class));
                break;
            case R.id.one_call_help:
                startActivity(new Intent(this, OneKeyCallActivity.class));
                break;
        }
    }
}
