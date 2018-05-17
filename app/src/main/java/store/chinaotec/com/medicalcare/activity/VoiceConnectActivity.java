package store.chinaotec.com.medicalcare.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyVoiceAdapter;
import store.chinaotec.com.medicalcare.dialog.PropUploadDialog;
import store.chinaotec.com.medicalcare.javabean.MesageBean;
import store.chinaotec.com.medicalcare.javabean.QuestionBean;
import store.chinaotec.com.medicalcare.javabean.TextReconizeBean;
import store.chinaotec.com.medicalcare.service.RecogService;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.FileUtil;
import store.chinaotec.com.medicalcare.utill.InitToolUtill;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.RandomNumUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.SysUtill;
import store.chinaotec.com.medicalcare.utill.ToastUtil;
import store.chinaotec.com.medicalcare.utill.VoiceAnyUtill;

public class VoiceConnectActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_GENERAL = 105;
    @Bind(R.id.iv_title_back)
    ImageView titleBack;
    @Bind(R.id.comunicat_recycleview)
    RecyclerView comunicatRecycleview;
    @Bind(R.id.self_voice)
    ImageView selfVoice;
    @Bind(R.id.into_text)
    EditText intoText;
    @Bind(R.id.linear_self_one)
    LinearLayout linearSelfOne;
    @Bind(R.id.self_send)
    TextView selfSend;
    @Bind(R.id.main_back)
    View mainBack;
    @Bind(R.id.linear_down)
    LinearLayout linearDown;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private RecognizerDialog mIatDialog;
    private List<MesageBean> list;
    private SharedPreferences sharedPreferences;
    private Uri imageUri;
    private String memberid, questionPre, maa, sendMess, logo, sid;
    private MyVoiceAdapter myVoiceAdapter;
    private Context context = this;
    private AlertDialog alertDialog;
    private String caseCode;
    private String inputAge;
    private int width;
    private int height;
    private RadioGroup choseSex;
    //是否选择了性别
    private boolean choseSexSwitch = false;
    private boolean login;
    private PropUploadDialog propUploadDialog;
    private String mEngineType;
    private SpeechRecognizer mIat;
    public static final String TAG = "VoiceConnectActivity";
    @Bind(R.id.tv_activity_voice_connect_clear)
    TextView tvActivityVoiceConnectClear;
    private int getQuestion = 0;  //计算次数最多8次
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_voice_connect);
        ButterKnife.bind(this);
        initBaseData();
        initListener();
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(VoiceConnectActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(VoiceConnectActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance().release();
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

    private void initBaseData() {
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(VoiceConnectActivity.this, mInitListener);
        //设置引擎类型  语音听写只支持在线听写
        mEngineType = SpeechConstant.TYPE_CLOUD;
        //初始化语音识别对话框对象
        mIatDialog = new RecognizerDialog(VoiceConnectActivity.this, mInitListener);
        if (mIat == null) {
            BaseUtill.toastUtil("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
        }
        list = new ArrayList();
        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, "<font color='#33B5E5'> 诊断疾病大约需要5-10分钟, </font>现在请您描述病情", imageUri));
        //初始化sp对象
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        //获用户登录后返回的sid
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "sid");
        //用户的在线logo路径
        logo = SpUtill.getString(this, ResourseSum.Medica_SP, "logo");
        //获取当前病例号
        caseCode = SpUtill.getString(this, ResourseSum.Medica_SP, "caseCode");
        //获取用户的memberId就是UID
        memberid = SpUtill.getString(this, ResourseSum.Medica_SP, "memberId");
        //每次进入沟通页面需要发起一次问诊调用LaunchCommunication接口
        SpUtill.putString(this, ResourseSum.Medica_SP, "tag", "yes");
        //清空上次输入的代述年龄
        SpUtill.putString(this, ResourseSum.Medica_SP, "daishuage", "");
    }

    private void initListener() {
        tvActivityVoiceConnectClear.setOnClickListener(this);
        selfSend.setOnClickListener(this);
        selfVoice.setOnClickListener(this);
        titleBack.setOnClickListener(this);
        comunicatRecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myVoiceAdapter = new MyVoiceAdapter(context, list, sharedPreferences);
        comunicatRecycleview.setAdapter(myVoiceAdapter);
        //添加输入框的内容改变监听
        intoText.addTextChangedListener(new MyText());
        //是否选择"带述"
        boolean daishu = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "daishu");
        String sid = SpUtill.getString(this, ResourseSum.Medica_SP, "sid");
        MyLog.d("..是否选择代述.." + daishu + "..用户登陆后返回的sid.." + sid);
        boolean login = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "login");
        if (login) {//用户登录时
            loginTrue(daishu);
        } else { //用户未登录时
            loginFalse(daishu);
        }
        //初始化OCR事例
        InitToolUtill.initAccessTokenWithAkSk(this);
    }

    /**
     * @param daishu 选择"代述"的标志
     *               用户登陆时根据事都选择"代述"进行判断操作
     */
    private void loginTrue(boolean daishu) {
        if (daishu) { //选择"代述"时
            //清空本地病例号 临时 sessionId和uid
            SpUtill.putString(this, ResourseSum.Medica_SP, "caseCode", "");
            //生成临时 sessionId和uid
            sid = autoRateSid();
            //保存用户的临时sessionId
            SpUtill.putString(this, ResourseSum.Medica_SP, "sid", sid);
            //生成临时uid
            memberid = sid;
            SpUtill.putString(this, ResourseSum.Medica_SP, "memberId", memberid);
            //弹窗输入性别
            showChoseSexDialog();
            //创建病例号
            connectInter();
        } else { //选择"自己"时
            //重新赋值用户登录后返回的sid,和用户id
            String saveSid = sharedPreferences.getString("saveSid", "");
            String saveMemberId = sharedPreferences.getString("saveMemberId", "");
            String sid = sharedPreferences.getString("sid", "");

            //如果登录失败了返回sid为空话给sid添加值
            if (MyCommonUtil.isEmpty(sid)) {
                sharedPreferences.edit().putString("sid", autoRateSid()).apply();
            }
            sharedPreferences.edit().putString("sid", saveSid).apply();
            sharedPreferences.edit().putString("memberId", saveMemberId).apply();
            memberid = saveMemberId;
            //判断性别 年龄是否存在
            int sex = sharedPreferences.getInt("saveSex", 222);
            String age = sharedPreferences.getString("saveAge", "");
            if (TextUtils.isEmpty(age) || sex == 222) {
                BaseUtill.toastUtil("注册信息不完整,请完善信息");
                showChoseSexDialog();
            }
            if (TextUtils.isEmpty(caseCode) || (!saveSid.equals(sid))) {
                //病例号不存在,创建病例号
                connectInter();
            } else {
                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "tag", "no");
                //本地病例号存在时,调用获取问题接口
                getQuesCaseCode(caseCode);
            }
        }
    }

    /**
     * @param daishu 选择"代述"的标志
     *               用户未登陆时根据事都选择"代述"进行判断操作
     */
    private void loginFalse(boolean daishu) {
        if (daishu) { //未登录选择"代述时"清空本地病例号,清空临时 sessionId和uid，生成临时sessionId和uid
            //清空本地病例号  临时sessionId   临时uid
            sharedPreferences.edit().putString("caseCode", "").apply();
            //生成临时 sessionId和uid
            sid = autoRateSid();
            //保存用户的临时sessionId
            sharedPreferences.edit().putString("sid", sid).apply();
            //生成临时uid
            memberid = sid;
            sharedPreferences.edit().putString("memberId", memberid).apply();
            //根据病例号判断是否存在
            judgeCasecode();
        } else {  //选择"自己"
            //用户的sid是否存在,不存在随机生成一个临时sessionId并赋值(sessionId就是sid)
            if (TextUtils.isEmpty(sid)) {
                //生成临时sessionId
                sid = autoRateSid();
                //清空病例号
                sharedPreferences.edit().putString("caseCode", "").apply();
                //保存用户的临时sessionId
                sharedPreferences.edit().putString("sid", sid).apply();
                //生成临时uid
                memberid = sid;
                sharedPreferences.edit().putString("memberId", memberid).apply();

            }
            //根据病例号判断是否存在
            judgeCasecode();
        }
    }

    /**
     * 根据病例号是否存在判断进行操作
     */
    private void judgeCasecode() {
        String nowCaseCode = sharedPreferences.getString("caseCode", "");
        MyLog.d("judgeCasecode.....病例号..." + nowCaseCode);
        if (!(TextUtils.isEmpty(nowCaseCode))) {
            //第一次问诊结束标志
            sharedPreferences.edit().putString("tag", "no").apply();
            //本地病例号存在时,调用获取问题接口
            getQuesCaseCode(nowCaseCode);
        } else {
            //本地病例号不存在时,弹出弹窗提示输入性别 年龄
            showChoseSexDialog();
        }
    }

    /**
     * sid重新赋值
     */
    private String autoRateSid() {
        String sidValue = null;
        String sixId = BaseUtill.getSessionId(10);
        sidValue = "test_" + sixId;
        MyLog.d("...随机生成sessionid为.." + sidValue);
        SpUtill.putString(this, ResourseSum.Medica_SP, "sid", sidValue);
        return sidValue;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            Uri uri = Uri.fromFile(FileUtil.getSaveFile(getApplicationContext()));
            //初始化获取调用ocr接口图片的宽 高
            VoiceAnyUtill.getOCRphotoSize(width, height, uri);
            //调用ocr接口识别图片
            String realPathFromURI = BaseUtill.getRealPathFromURI(uri, this);
            //解析图片识别图片
            RecogService.recGeneral(realPathFromURI, new RecogService.ServiceListener() {
                @Override
                public void onResult(String result) {
                    //解析到含坐标位置的数据
                    TextReconizeBean textReconizeBean = new TextReconizeBean(width, height, result);
                    String synJson = MyApp.getGson().toJson(textReconizeBean);
                    //调用预解析接口,图片宽 高 图片解析后的json封装的json作为用户的回复
                    sendMess = synJson;
                    startPreAny(questionPre, caseCode);
                    //去掉提示上传图片的弹窗
                    propUploadDialog.dismiss();
                }
            });
            //显示图片到聊天记录
            list.add(new MesageBean(MesageBean.PHOTO_TYPE, "", uri));
            myVoiceAdapter.notifyItemInserted(list.size() - 1);
            comunicatRecycleview.scrollToPosition(list.size() - 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //信息发送按钮
            case R.id.self_send:
                //获取输入框的信息并判断是否为空,不为空调用逻辑接口进行一系列操作,为空弹toast提示
                String inputText = intoText.getText().toString();
                if (!(TextUtils.isEmpty(inputText))) {
                    sendMess = inputText;
                    //获取当前病例号并判断,进入当前页面后没有病例号时,重新创建病例号后需要再次获取病例号
                    if (TextUtils.isEmpty(caseCode)) {
                        caseCode = sharedPreferences.getString("caseCode", "");
                    }
                    //当前推送问题进行判断,如果为空设置初始推送问题"现在请您描述病情?"
                    if (TextUtils.isEmpty(questionPre)) {
                        questionPre = "现在请您描述病情?";
                        //调用预解析接口
                        startPreAny(questionPre, caseCode);
                    } else {
                        //调用预解析接口
                        startPreAny(questionPre, caseCode);
                    }
                    //添加聊天信息到聊天记录
                    list.add(new MesageBean(MesageBean.SEND_TYPE, sendMess, imageUri));
                    myVoiceAdapter.notifyItemInserted(list.size() - 1);
                    comunicatRecycleview.scrollToPosition(list.size() - 1);
                    //初始化聊天输入框
                    intoText.setText("");
                    selfSend.setEnabled(false);
                    selfSend.setBackgroundResource(R.drawable.bg_gray);
                    selfSend.setTextColor(getResources().getColor(R.color.colorTooBar));
                    selfSend.setText("诊疗中...");
                } else {
                    BaseUtill.toastUtil("请输入聊天信息");
                }
                break;
            //开始语音听写
            case R.id.self_voice:
                VoiceToWrite();
                break;
            //取消选择
            case R.id.cancle_chose:
                alertDialog.dismiss();
                mainBack.setVisibility(View.GONE);
                finish();
                break;
            //确定选择
            case R.id.sure_chose:
                boolean login = sharedPreferences.getBoolean("login", false);
                boolean daishu = sharedPreferences.getBoolean("daishu", false);
                String daishuage = sharedPreferences.getString("daishuage", "");
                if (login && !daishu) {
                    //判断性别 年龄是否存在
                    int sex = sharedPreferences.getInt("saveSex", 222);
                    String age = sharedPreferences.getString("saveAge", "");
                    if (TextUtils.isEmpty(age) || sex == 222) {
                        //获取当前录入的年龄  性别信息
                        int sex1 = sharedPreferences.getInt("sex", 222);
                        //弹窗输入的性别 年龄覆盖用户登陆后的性别 年龄信息
                        sharedPreferences.edit().putString("saveAge", daishuage).apply();
                        sharedPreferences.edit().putInt("saveSex", sex1).apply();
                    }
                }
                if (choseSexSwitch && (!(TextUtils.isEmpty(daishuage)))) {
                    alertDialog.dismiss();
                    linearDown.setVisibility(View.VISIBLE);
                    mainBack.setVisibility(View.GONE);
                    //点击确定之后在请求病例号

                    //创建病例号
                    connectInter();
                } else {
                    BaseUtill.toastUtil("请输入个人性别,年龄信息");
                }
                break;
            case R.id.tv_activity_voice_connect_clear:
                //取消诊疗
//                SpUtill.putString(this, ResourseSum.Medica_SP, "caseCode", "");
//                ToastUtil.showToast(VoiceConnectActivity.this, "关闭成功");
//                selfSend.setEnabled(false);
//                selfSend.setBackgroundResource(R.drawable.bg_gray);
//                selfSend.setTextColor(getResources().getColor(R.color.colorTooBar));
//                selfSend.setText("初始化中...");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                initBaseData();
//                                initListener();
//                                selfSend.setEnabled(true);
//                                selfSend.setBackgroundResource(R.drawable.round_shape_send);
//                                selfSend.setTextColor(getResources().getColor(R.color.white));
//                                selfSend.setText("发送");
//                            }
//                        });
//                    }
//                }, 2000);
                break;
            case R.id.iv_title_back:
                finish();
                break;
        }
    }

    /**
     * 科大讯飞语音输入文字听写
     */
    private void VoiceToWrite() {
        // 移动数据分析，收集开始听写事件
        try {
            FlowerCollector.onEvent(VoiceConnectActivity.this, "iat_recognize");
            mIatResults.clear();
            intoText.setText(null);
            setVoiceParm();
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            BaseUtill.toastUtil("请开始说话…");
        } catch (Exception e) {
            //当前应用版本号
            String version = SysUtill.getVersion(MyApp.getContext());
            //手机型号
            String systemModel = SysUtill.getSystemModel();
            //获取系统版本
            String systemVersion = SysUtill.getSystemVersion();
            String saveSid = SpUtill.getString(VoiceConnectActivity.this, ResourseSum.Medica_SP, "saveSid");
            NetWorkUtill.submitErrorLog(saveSid, e.toString(), version, systemModel, systemVersion);
        }
    }

    /**
     * 科大讯飞听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            String text = BaseUtill.parseIatResult(results.getResultString());
            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mIatResults.put(sn, text);
            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            intoText.setText(resultBuffer.toString());
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            BaseUtill.toastUtil(error.getPlainDescription(true));
        }
    };
    /**
     * 科大讯飞初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                BaseUtill.toastUtil("初始化失败...错误提示码" + code);
            }
        }
    };

    /**
     * 科大讯飞在线语音听写设置部分参数
     */
    private void setVoiceParm() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    /**
     * 监听拦截弹窗的返回键事件
     */
    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    private void showChoseSexDialog() {
        //添加黑色半透明背景
        mainBack.setVisibility(View.VISIBLE);
        mainBack.setAlpha(0.70f);
        //初始化弹窗对象并显示
        alertDialog = new AlertDialog.Builder(VoiceConnectActivity.this).create();
        alertDialog.show();
        //隐藏下半部功能按钮
        linearDown.setVisibility(View.GONE);
        //设置自定义弹窗布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_input_perinfor, null);
        alertDialog.setContentView(inflate);
        //设置点击外部弹窗不消失
        alertDialog.setCanceledOnTouchOutside(false);
        //监听弹窗返回键点击事件并处理
        alertDialog.setOnKeyListener(keylistener);
        //初始化view并设置监听事件
        choseSex = (RadioGroup) inflate.findViewById(R.id.radio_chose_sex);
        Button cancleChose = (Button) inflate.findViewById(R.id.cancle_chose);
        Button sureChose = (Button) inflate.findViewById(R.id.sure_chose);
        EditText daishuage = (EditText) inflate.findViewById(R.id.dai_shu_age);
        cancleChose.setOnClickListener(this);
        sureChose.setOnClickListener(this);
        //设置弹窗大小
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
        //处理EditText在弹窗里不能输入文字
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        daishuage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputAge = s.toString();
                //带述人年龄
                sharedPreferences.edit().putString("daishuage", inputAge).apply();
            }
        });
        choseSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                choseSexSwitch = true;
                switch (checkedId) {
                    case R.id.chose_sex_men:
                        sharedPreferences.edit().putInt("sex", 0).apply();
                        break;
                    case R.id.chose_sex_women:
                        sharedPreferences.edit().putInt("sex", 1).apply();
                        break;
                }
            }
        });
    }

    /**
     * 创建病例号并保存在SP
     */
    private void connectInter() {
        //获取f_UID的值
//        String f_UID = getfUIDvalue();
        String f_UID = sharedPreferences.getString("memberId", "");
        //创建病历获取病历号
        OkGo.post(MyUrl.CREATE_CASE).params("f_UID", f_UID).params("CaseContent", "现在请描述病情").execute(new StringCallback() {
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                //解析返回的xml字符串并处理
                Document documentone = null;
                try {
                    documentone = DocumentHelper.parseText(s);
                    //获取根节点
                    final Element rootElementone = documentone.getRootElement();
                    //获取病例号
                    caseCode = rootElementone.element("caseCode").getTextTrim();
                    if (MyCommonUtil.isEmpty(caseCode)) {
                        ToastUtil.showToast(VoiceConnectActivity.this, "创建病例号失败");
                    }
                    MyLog.d("anysConnectInter...创建新的病例号.." + caseCode);
                    //保存病历号
                    sharedPreferences.edit().putString("caseCode", caseCode).apply();
                    //是否是第一次发起问诊沟通
                    sharedPreferences.edit().putString("tag", "yes").apply();
                } catch (DocumentException e) {
                    e.printStackTrace();
                    ToastUtil.showToast(VoiceConnectActivity.this, "创建病例号异常");
                }
            }
        });
    }

    /**
     * @return f_UID的实际值
     * 根据用户的登录情况给f_UID参数赋值
     */
    private String getfUIDvalue() {
        String f_UID = null;
        memberid = sharedPreferences.getString("memberId", "");
        sid = sharedPreferences.getString("sid", "");
        login = sharedPreferences.getBoolean("login", false);
        if (login) { //用户登陆f_UID值为memberid
            f_UID = memberid;
        } else {//用户登陆f_UID值为sid
            f_UID = sid;
        }
        MyLog.d("connectInter..f_UID...." + f_UID);
        return f_UID;
    }

    /**
     * @param question 病人提交的问题
     * @param caseCode 病例号
     *                 调用预解析接口
     */
    private void startPreAny(String question, final String caseCode) {
        //获取当前的sid  memberid的值
        String nowSid = sharedPreferences.getString("sid", "");
        String nowMemberid = sharedPreferences.getString("memberId", "");
        OkGo.post(MyUrl.PRE_ANYS).params("userid", nowMemberid).params("sessionid", nowSid).params("question", question).params("response", sendMess).execute(new StringCallback() {

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..预解析成功.." + s);
                //保存调用预解析接口后返回的xml字符串
                sharedPreferences.edit().putString("preXml", s).apply();
                try {
                    Document documenttwo = DocumentHelper.parseText(s);
                    //获取根节点
                    Element rootElementtwo = documenttwo.getRootElement();
                    //获取finished节点的值
                    String finished = rootElementtwo.element("finished").getText();
                    //finished字符串转为int值
                    int finished_int = Integer.parseInt(finished);
                    MyLog.d("预解析..finished值为." + finished_int);
                    //获取病例号
                    String caseCode = sharedPreferences.getString("caseCode", null);
                    //判断病例号是否存在,不存在创建病例号
                    judgCaseExist(caseCode);
                    if (finished_int == 1) {
                        //预解析完成  根据tag值判断是否调用问诊 LaunchCommunication接口 或者 提交问诊 SubmitUFeedback接口
                        judgeInquiry(s);
                    } else if (finished_int == 0) {//预解析未完成,解析并显示下一个问题
                        questionPre = anysNextQuetion(rootElementtwo);
                        //问题输入框可以输入文字
                        intoText.setFocusable(true);
                        //显示推送问题
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, questionPre, imageUri));
                        myVoiceAdapter.notifyDataSetChanged();
                        comunicatRecycleview.scrollToPosition(list.size() - 1);

                        selfSend.setEnabled(true);
                        selfSend.setBackgroundResource(R.drawable.round_shape_send);
                        selfSend.setTextColor(getResources().getColor(R.color.white));
                        selfSend.setText("发送");

                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param rootElementtwo Element对象
     *                       获取nextquestion的值并解析推送问题
     * @return 推送显示的问题
     */
    private String anysNextQuetion(Element rootElementtwo) {
        String qustion = null;
        //获取nextquestion的值json字符串
        String nextquestion = rootElementtwo.element("nextquestion").getText();
        MyLog.d("anysNextQuetion.....nextquestion....." + nextquestion);
        QuestionBean questionBean = MyApp.getGson().fromJson(nextquestion, QuestionBean.class);
        if (questionBean != null) {
            String action = questionBean.getAction();
            if (action.equals("question")) {
                qustion = questionBean.getQuestion();
            } else if (action.equals("upload_image")) {
                //后台推送的下一个问题
                qustion = questionBean.getQuestion();
                //调用预解析接口,finished为0时,action值为upload_image时,弹窗提示上传单据
                showPropUpload(qustion);
            }
        } else {
            qustion = "我还不明白您的意思，预处理XML解析异常";
        }
        return qustion;
    }


    /**
     * @param s 调用预解析接口返回的json字符串
     *          根据tag值判断调问诊用接口,或者提交问诊接口
     */
    private void judgeInquiry(String s) {
        String tag = sharedPreferences.getString("tag", "");
        MyLog.d("judgeInquiry...tag.." + tag);
        if (tag.equals("yes")) { //调用开始问诊接口,创建病例号
            beginConsult(s);
        } else if (tag.equals("no")) { //调用提交问诊接口
            submitConsult(caseCode, s);
        }
    }

    /**
     * 判断病例号是否存在,为空创建新的病例号并保存
     */
    private void judgCaseExist(String caseCode) {
        MyLog.d("judgCaseExist..病例号.." + caseCode);
        if (TextUtils.isEmpty(caseCode)) {
            connectInter();
        }
    }

    /**
     * @param s 调用开始问诊接口返回的json字符串
     */
    private void beginConsult(String s) {
        //获取当前病例号
        caseCode = sharedPreferences.getString("caseCode", "");
        maa = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><conversation><CaseCode>" + caseCode + "</CaseCode>" + s.substring(s.indexOf("<conversation>") + "<conversation>".length());
        //获取用户的登录状态
        boolean login = sharedPreferences.getBoolean("login", false);
        //获取当前用户的登陆状态
        String loginStaue = getLoginStaue(login);
        //获取当前用户的性别信息
        final String userSexInfo = getUserSexInfo();
        //获取当前用户年龄
        String ageInfo = getUserAgeShow();
        //根据用户的登录状态拼接传入的登录状态,性别,年龄等信息
        String spicValue = splicParmer(loginStaue, ageInfo, login, userSexInfo);
        MyLog.d("beginConsult...用户性别..userSexInfo..年龄..ageInfo..登录标签..loginStaue..病例号..caseCode.." + userSexInfo + ".." + ageInfo + ".." + loginStaue + ".." + caseCode);
        //截取当前参数maa并以"<conversation>"截取,截取后在拼接得到最终传入的参数maa
        String splicResult = spicResult(spicValue);
        MyLog.d("beginConsult..调用问诊接口拼接完性别等信息后传入的参数.." + splicResult);


        OkGo.post(MyUrl.WEN_ZHEN).params("f_xml", splicResult).execute(new StringCallback() {
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("..开始问诊成功.." + s);
                //每次沟通只能发起一次问诊调用LaunchCommunication接口
                sharedPreferences.edit().putString("tag", "no").apply();
                Document documentThree = null;
                try {
                    documentThree = DocumentHelper.parseText(s);
                    Element rootElementThree = documentThree.getRootElement();
                    //获取标签Launch的值
                    String Launch = rootElementThree.element("Launch").getText();
                    MyLog.d("..开始问诊成功..Launch.." + Launch);
                    if ((Launch.equals("T"))) {
                        //调用获取问题接口
                        getQuestion(caseCode);
                    } else {
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, "大脑初始化Lunch失败", imageUri));
                        myVoiceAdapter.notifyItemInserted(list.size() - 1);
                        comunicatRecycleview.scrollToPosition(list.size() - 1);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param apicValue 包含有年龄,性别,登录标签的拼接后的字符串
     * @return 最终调用LaunchCommunication接口传入的参数f_xml的值拼接后的字符串
     */
    private String spicResult(String apicValue) {
        String splic = null;
        String[] split = maa.split("<conversation>");
        splic = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<conversation>" + apicValue + split[1];
        MyLog.d("beginConsult.....拼接后参数为.." + maa);
        return splic;
    }

    /**
     * @return 获取用户的登录状态  已经录返回y   未登录返回n
     */
    public String getLoginStaue(boolean login) {
        String loginInfo = null;
        if (login) { //登录显示"y"
            loginInfo = "y";
        } else { //未登录显示为"n"
            loginInfo = "n";
        }
        return loginInfo;
    }

    /**
     * @return 获取用户的性别信息  sex为0 性别为"男"  sex为1 性别为"女"
     */
    public String getUserSexInfo() {
        String sexInfo = null;
        boolean login = sharedPreferences.getBoolean("login", false);
        boolean daishu = sharedPreferences.getBoolean("daishu", false);
        int sex = sharedPreferences.getInt("sex", 100);
        if (login && !daishu) {
            sex = sharedPreferences.getInt("saveSex", 300);
        }
        if (sex != 100 && sex == 0) {
            sexInfo = "男";
        } else if (sex != 100 && sex == 1) {
            sexInfo = "女";
        }
        return sexInfo;
    }

    /**
     * @param loginStaue  用户的登录状态标签
     * @param ageInfo     用户的年龄
     * @param login       用户的登录状态
     * @param userSexInfo 用户的性别信息
     * @return 根据用户的登录状态拼接相应的参数值到字符串里
     */
    public String splicParmer(String loginStaue, String ageInfo, Boolean login, String userSexInfo) {
        String pinjie = "";
        if (login) {//登录
            //拼接中间添加参数串
            pinjie = "<U_info register=\"" + loginStaue + "\" uid=\"" + memberid + "\" ><sid>" + sid + "</sid><sex>" + userSexInfo + "</sex><age>" + ageInfo + "</age></U_info>";
        } else {//没登录
            pinjie = "<U_info register=\"" + loginStaue + "\" uid=\"" + sid + "\" ><sid>" + sid + "</sid><sex>" + userSexInfo + "</sex><age>" + ageInfo + "</age></U_info>";
        }
        return pinjie;
    }

    /**
     * @return 当前用户的年龄
     * 获取当前沟通用户的年龄数据
     */
    private String getUserAgeShow() {
        String ageInfo = null;
        boolean login = sharedPreferences.getBoolean("login", false);
        boolean daishu = sharedPreferences.getBoolean("daishu", false);
        String userAge = sharedPreferences.getString("age", null);
        if (login && !daishu) {
            userAge = sharedPreferences.getString("saveAge", "");
        }
        MyLog.d("beginConsult...userAge.." + userAge);
        if (TextUtils.isEmpty(userAge)) { //用户未登录时显示输入的带述人年龄
            String daishuage = sharedPreferences.getString("daishuage", "");
            if (!(TextUtils.isEmpty(daishuage))) {
                ageInfo = daishuage;
            }
        } else {
            ageInfo = userAge;
        }
        return ageInfo;
    }

    /**
     * @param caseCode 病例号
     * @param s        调用提交问诊接口
     */
    private void submitConsult(final String caseCode, String s) {
        maa = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><conversation><CaseCode>" + caseCode + "</CaseCode>" + s.substring(s.indexOf("<conversation>") + "<conversation>".length());
        //调用提交问诊接口
        OkGo.post(MyUrl.SUBMIT_ZHEN).params("f_xml", maa).execute(new StringCallback() {
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.xml("onSuccess...提交问诊成功.." + s);
                MyLog.d("...submitConsult..病例号." + caseCode);
                Document documentFour = null;
                try {
                    documentFour = DocumentHelper.parseText(s);
                    Element rootElementFour = documentFour.getRootElement();
                    String getch = rootElementFour.element("getch").getText();
                    if ((getch.equals("T"))) {
                        //调用获取问题接口
                        getQuestion(caseCode);
                    } else {
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, "提交问诊失败getch为F", imageUri));
                        myVoiceAdapter.notifyItemInserted(list.size() - 1);
                        comunicatRecycleview.scrollToPosition(list.size() - 1);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param caseCode 病例号
     *                 提交问诊成功后,<fv>T</fv><allMatch>T</allMatch><Launch>T</Launch> 三个参数都为T时开始获取问题
     */
    private void getQuestion(final String caseCode) {
        MyLog.d("getQuestion..病例号.caseCode..." + caseCode);
        String uuid = RandomNumUtil.getUUIDString();
        //调用获取问题接口
        OkGo.post(MyUrl.GET_QUSTION).params("f_CaseCode", caseCode).params("UUID",uuid).
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (getQuestion > 8) {
                            ToastUtil.showToast(VoiceConnectActivity.this, e.getMessage());
                        } else {
                            getQuestion++;
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getQuestion(caseCode);
                                }
                            }, 2 * 1000);
                        }
                    }

                    @Override
                    public void onCacheError(Call call, Exception e) {
                        super.onCacheError(call, e);
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MyLog.d("..获取问题成功." + s);
                        Document documentFive = null;
                        try {
                            getQuestion = 0;
                            documentFive = DocumentHelper.parseText(s);
                            Element rootElementFive = documentFive.getRootElement();
                            //获取标签Complete,question,conclusion的值
                            String complete = rootElementFive.element("Complete").getText();
                            String conclusion = rootElementFive.element("conclusion").asXML();
                            MyLog.d("..Complete.." + complete + "..conclusion.." + conclusion);
                            if (complete.equals("F")) {  //把question值给用户展示出来
                                //截取返回xml字符串获取hint参数
                                String[] fvAry = s.split("</fv>");
                                String[] completeAry = fvAry[1].split("<Complete>");
                                String hintValue = completeAry[0];
                                MyLog.d("合成问题接口传入hint参数.." + hintValue);
                                //连接合成问题接口
                                beginSysncQuestion(hintValue);
                            } else if (complete.equals("T")) {
                                //问题回复输入框可以输入文字
                                list.add(new MesageBean(MesageBean.RECEIVE_TYPE, conclusion, imageUri));
                                sharedPreferences.edit().putString("caseCode", "").apply();
                                myVoiceAdapter.notifyItemInserted(list.size() - 1);
                                comunicatRecycleview.scrollToPosition(list.size() - 1);
                                intoText.setFocusable(false);

                                selfSend.setEnabled(true);
                                selfSend.setBackgroundResource(R.drawable.round_shape_send);
                                selfSend.setTextColor(getResources().getColor(R.color.white));
                                selfSend.setText("发送");

                                //保存推送的结论
                                //sharedPreferences.edit().putString("conclusion", conclusion).commit();
                            }
                        } catch (DocumentException e) {
                            e.printStackTrace();
                            String message = e.getMessage();
                            list.add(new MesageBean(MesageBean.RECEIVE_TYPE, "解析XML异常,message: " + message, imageUri));
                            myVoiceAdapter.notifyItemInserted(list.size() - 1);
                            comunicatRecycleview.scrollToPosition(list.size() - 1);
                        }
                    }
                });

    }

    /**
     * @param hintValue 传入的hint参数值
     *                  获取问题接口返回数据里Complete值为F时连接合成问题接口
     */
    private void beginSysncQuestion(final String hintValue) {
        //获取当前的  sessionid
        sid = sharedPreferences.getString("sid", "");
        //获取用户的memberId就是UID
        memberid = sharedPreferences.getString("memberId", "");
        OkGo.post(MyUrl.SYSNC_QUSTION).params("userid", memberid).params("sessionid", sid).params("hint", hintValue).
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MyLog.d("..合成问题成功.." + s);
                        QuestionBean questionBean = MyApp.getGson().fromJson(s, QuestionBean.class);
                        String action = questionBean.getAction();
                        if (action.equals("question")) {
                            questionPre = questionBean.getQuestion();
                        } else if (action.equals("upload_image")) {
                            questionPre = questionBean.getQuestion();
                            //加弹窗提示上传单据
                            showPropUpload(questionPre);
                        }
                        //问题回复输入框可以输入文字
                        intoText.setFocusable(true);
                        //展示合成的问题
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, questionPre, imageUri));
                        myVoiceAdapter.notifyItemInserted(list.size() - 1);
                        comunicatRecycleview.scrollToPosition(list.size() - 1);

                        selfSend.setEnabled(true);
                        selfSend.setBackgroundResource(R.drawable.round_shape_send);
                        selfSend.setTextColor(getResources().getColor(R.color.white));
                        selfSend.setText("发送");
                    }
                });
    }

    /**
     * @param questionPre 后台推送的问题
     *                    弹窗提示用户上传单据
     */
    private void showPropUpload(final String questionPre) {
        propUploadDialog = new PropUploadDialog();
        propUploadDialog.show(getSupportFragmentManager(), "uploaddialog");
        propUploadDialog.setCancelable(false);
        propUploadDialog.setPropTitle(questionPre);
        propUploadDialog.setOnClickCancleListener(new PropUploadDialog.CancleListener() {
            @Override
            public void cancle() {
                //问题回复输入框可以输入文字
                intoText.setFocusable(true);
                //点击取消上传后,显示"取消上传"在会话列表
                list.add(new MesageBean(MesageBean.SEND_TYPE, "取消上传(没有)", imageUri));
                myVoiceAdapter.notifyItemInserted(list.size() - 1);
                comunicatRecycleview.scrollToPosition(list.size() - 1);
                //调用预解析接口response传入ocr_result为null,width为o,height为0合成的json字符串
                initOCRdata(questionPre);
            }
        });
        propUploadDialog.setOnClickUploadListener(new PropUploadDialog.UploadListener() {
            @Override
            public void upload() {
                //打开相机
                InitToolUtill.ocrOpenCamera(VoiceConnectActivity.this, REQUEST_CODE_GENERAL,"");
            }
        });
    }

    /**
     * @param questionPre 后台推送的问题
     *                    弹窗提示用户上传单据,并调用预解析接口,传入参数response值为TextReconizeBean对象转化的json字符串
     */
    private void initOCRdata(String questionPre) {
        TextReconizeBean textReconizeBean = new TextReconizeBean(0, 0, "");
        String synJson = MyApp.getGson().toJson(textReconizeBean);
        sendMess = synJson;
        startPreAny(questionPre, caseCode);
    }

    /**
     * @param caseCode 本地已保存的病例号
     *                 调用获取问题接口,根据返回的complete的值判断为T时创建新的病例号保存在本地
     */
    private void getQuesCaseCode(final String caseCode) {
        selfSend.setEnabled(false);
        selfSend.setBackgroundResource(R.drawable.bg_gray);
        selfSend.setTextColor(getResources().getColor(R.color.colorTooBar));
        selfSend.setText("诊疗中...");

        //调用获取问题接口
        OkGo.post(MyUrl.GET_QUSTION).params("f_CaseCode", caseCode).execute(new StringCallback() {
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                Document documentFive = null;
                try {
                    documentFive = DocumentHelper.parseText(s);
                    Element rootElementFive = documentFive.getRootElement();
                    //获取标签Complete的值   question的值
                    String complete = rootElementFive.element("Complete").getText();
                    if (complete.equals("T")) { //覆盖当前的病例号创建新的病例号
                        String conclusion = rootElementFive.element("conclusion").asXML();
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, conclusion, imageUri));
                        sharedPreferences.edit().putString("caseCode", "").apply();
                        myVoiceAdapter.notifyItemInserted(list.size() - 1);
                        comunicatRecycleview.scrollToPosition(list.size() - 1);

                        selfSend.setEnabled(true);
                        selfSend.setBackgroundResource(R.drawable.round_shape_send);
                        selfSend.setTextColor(getResources().getColor(R.color.white));
                        selfSend.setText("发送");

                    } else {
                        //截取返回的xml字符串得到hint参数
                        String[] fvAry = s.split("</fv>");
                        String[] completeAry = fvAry[1].split("<Complete>");
                        String hintValue = completeAry[0];
                        //开始合成问题展示
                        beginSysncQuestion(hintValue);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                    String message = e.getMessage();
                    list.add(new MesageBean(MesageBean.RECEIVE_TYPE, "解析XML异常,message: " + message, imageUri));
                    myVoiceAdapter.notifyItemInserted(list.size() - 1);
                    comunicatRecycleview.scrollToPosition(list.size() - 1);
                }
            }
        });
    }


    class MyText implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            sendMess = s.toString();
           /* if (TextUtils.isEmpty(sendMess)) {
                selfSend.setVisibility(View.GONE);
                selfAdd.setVisibility(View.VISIBLE);
            } else {
                selfSend.setVisibility(View.VISIBLE);
                selfAdd.setVisibility(View.GONE);
            }*/
        }
    }

}
