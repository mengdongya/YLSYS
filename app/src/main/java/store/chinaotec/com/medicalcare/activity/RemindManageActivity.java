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
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by HYY on 2018/3/9.
 * 提醒
 */

public class RemindManageActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_add_linkman_data)
    EditText etAddLinkmanData;
    @Bind(R.id.b_song_info)
    Button bSongInfo;
    @Bind(R.id.tv_include_title_view)
    TextView tvIncludeTitleView;
    private int refMemberId;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_manage);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        refMemberId = getIntent().getIntExtra("refMemberId", 0);
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        tvIncludeTitleView.setText("提醒");
        bSongInfo.setOnClickListener(this);
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
        String text = etAddLinkmanData.getText().toString().trim();
        if (StringUtils.isEmpty(text)){
            ToastUtil.MyToast(this,"请输入提醒信息");
            return;
        }

        OkHttpUtils.post().url(MyUrl.REMIND_MEMBER).addParams("sid",sid).addParams("refMemberId",refMemberId+"").
                addParams("remindText",text).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSON.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    ToastUtil.MyToast(RemindManageActivity.this,"成功提醒！");
                    finish();
                }
            }
            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }
}
