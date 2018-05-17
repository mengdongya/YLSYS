package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.dialog.LoadingView;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by HYY on 2018/3/9.
 * 添加联系人
 */

public class AddLinkManManageActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_add_linkman_data)
    EditText etAddLinkmanData;
    @Bind(R.id.et_add_linkman_text)
    EditText etAddLinkmanText;
    @Bind(R.id.b_song_info)
    Button bSongInfo;
    private TextView tv_include_title_view;
    private String sid;
    private LoadingView loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_linkman_manage);
        ButterKnife.bind(this);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("添加联系人");
        initData();
    }

    private void initData() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        bSongInfo.setOnClickListener(this);
        loadingDialog = new LoadingView(this, R.style.ProgressDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_song_info:
                submit();
                break;
        }
    }

    private void submit() {
        String data = etAddLinkmanData.getText().toString().trim();
        String text = etAddLinkmanText.getText().toString().trim();

        if (StringUtils.isEmpty(data)){
            ToastUtil.MyToast(this,"请输入电话或者用户名");
            return;
        }

        if (StringUtils.isEmpty(text)){
            ToastUtil.MyToast(this,"请输入验证信息");
            return;
        }
        loadingDialog.show();
        OkHttpUtils.post().url(MyUrl.ADD_CHRONIC_MEMBER).addParams("sid",sid).addParams("refphone",data).addParams("reftext",text).build().
                execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                loadingDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                loadingDialog.dismiss();
                ResultBean resultBean = JSON.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    ToastUtil.MyToast(AddLinkManManageActivity.this,"已发送请求");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }
}
