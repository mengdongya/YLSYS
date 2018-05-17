package store.chinaotec.com.medicalcare.shopmarket.logic.user.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 个人中心 意见反馈
 * Created by wjc on 2016/8/4
 */
public class SuggestionFeedbackActivity extends BaseAoActivity {

    EditText editText, ed_phone_or_email;
    int num = 100;// 限制的最大字数
    private TextView mTvTitle, tv_feedbace;
    private ImageView mBtnBack;
    private Button mBtnSubmit;
    private String sid;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_complaint_feedback);
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        tv_feedbace = (TextView) findViewById(R.id.tv_feedbace);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        editText = (EditText) findViewById(R.id.ed_feedback);
        ed_phone_or_email = (EditText) findViewById(R.id.ed_phone_or_email);

    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText(R.string.user_btn_customer_complaint);
        editText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
                System.out.println("s=" + s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_feedbace.setText(Html.fromHtml(String.format(SuggestionFeedbackActivity.this
                        .getResources().getString(R.string.txt_freeback), s.length())));
            }
        });
    }

    private void submit() {

        if ("".equals(sid)) {
            startActivityForResult(new Intent(this, LoginActivity.class),ResourseSum.LOGIN_RESPONSE);
            return;
        }

        String content = editText.getText().toString().trim();
        if (content.equals("")) {
            ToastUtil.MyToast(this, "请填写反馈内容");
            return;
        }

        String phoneOrEmail = ed_phone_or_email.getText().toString().trim();
//        if ("".equals(phoneOrEmail)) {
//            ToastUtil.MyToast(this, "请填写手机号");
//            return;
//        }

        if (SourceConstant.TURN_TO_COMPLAINT_BY_TYPE == SourceConstant.SIX) {
            AorunApi.getFeedback(sid, content, phoneOrEmail, "2", mDataCallback);
        } else {
            AorunApi.getFeedback(sid, content, phoneOrEmail, "1", mDataCallback);
        }

    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        SourceConstant.TURN_TO_COMPLAINT_BY_TYPE = SourceConstant.ZERO;
        ToastUtil.MyToast(this, "提交成功");
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        }
    }
}
