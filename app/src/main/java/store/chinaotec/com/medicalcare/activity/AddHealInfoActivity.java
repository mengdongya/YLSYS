package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.http.Result;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 添加健康管理信息页面,可以添加自己的或者他人的
 */
public class AddHealInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.main_health_me)
    Button mainHealthMe;
    @Bind(R.id.main_health_others)
    Button mainHealthOthers;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_health_add);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        mainHealthMe.setOnClickListener(this);
        mainHealthOthers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_health_me:
                addSelfResponse();
                break;
            case R.id.main_health_others:
//                startActivity(new Intent(getApplicationContext(), AddDeviceActivity.class));
                startActivityForResult(new Intent(this,HealthAddPersonActivity.class),ResourseSum.ADD_HEALTH_PERSON);
                break;
        }
    }

    private void addSelfResponse() {
        OkHttpUtils.post().url(MyUrl.HEALTH_ADD_PERSON).addParams("sid",sid).addParams("isOwn","1").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean result = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(result.responseCode)){
                    finish();
                }else {
                    ToastUtil.MyToast(AddHealInfoActivity.this,result.msg);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
