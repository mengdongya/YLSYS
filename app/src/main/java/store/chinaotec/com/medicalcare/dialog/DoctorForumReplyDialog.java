package store.chinaotec.com.medicalcare.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.MedicalForumBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.IntentUtils;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * Created by wjc on 2017/10/30 0030.
 * 评论弹框
 */
@SuppressLint("ValidFragment")
public class DoctorForumReplyDialog extends DialogFragment {

    private MedicalForumBean.ForumBean data;
    private Activity activity;
    @Bind(R.id.et_comment_body)
    EditText edit_message;
    @Bind(R.id.tv_comment_send)
    TextView tvCommentSend;
    private String sid;


    public DoctorForumReplyDialog(Activity activity, MedicalForumBean.ForumBean forumBean) {
        this.activity = activity;
        this.data = forumBean;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这句代码的意思是让dialogFragment弹出时沾满全屏
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_doctor_forum_comment, container);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        sid = activity.getSharedPreferences(ResourseSum.Medica_SP, Context.MODE_PRIVATE).getString("sid", "");
        tvCommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
        tvCommentSend.setEnabled(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_dialog)));

        edit_message.requestFocus();
        edit_message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //处理事件
                    ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    request();
                }
                return false;
            }
        });
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        edit_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(edit_message.getText().toString())) {
                    tvCommentSend.setTextColor(getResources().getColor(R.color.writerName));
                    tvCommentSend.setEnabled(false);
                } else {
                    tvCommentSend.setTextColor(getResources().getColor(R.color.colorTooBar));
                    tvCommentSend.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int top = view.findViewById(R.id.ll_reply_comment).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < top) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        edit_message.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
//        proDialog = new ProgressDialog(activity);
//        proDialog.setMessage("发送中......");

    }

    private void request() {
        String body = edit_message.getText().toString().trim();
        if ("".equals(body)) {
            ToastUtil.MyToast(activity, "请先输入内容");
            return;
        }
        if (IntentUtils.toLoginActivity(sid, activity)) {
            return;
        }
        OkHttpUtils.post().url(MyUrl.FORUM_ADD_REPLY).addParams("classId", "13").addParams("topicId", data.getTopicId()).
                addParams("sid", sid).addParams("body", body).addParams("memberName", "").addParams("source", "1").build().
                execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
                        ResultBean result = JSON.parseObject(response, ResultBean.class);
                        if ("0".equals(result.responseCode)) {
                            ToastUtil.MyToast(activity, "评论成功");
                        } else {
                            ToastUtil.MyToast(activity, result.msg);
                        }
                        Intent intent = new Intent();
                        intent.setAction(Constant.REFRESH_FORUM);
                        activity.sendBroadcast(intent);
                        dismiss();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
