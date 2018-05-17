package store.chinaotec.com.medicalcare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import Decoder.BASE64Encoder;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyVoiceAdapter;
import store.chinaotec.com.medicalcare.dialog.PropUploadDialog;
import store.chinaotec.com.medicalcare.javabean.AskQuestion;
import store.chinaotec.com.medicalcare.javabean.BindDevice;
import store.chinaotec.com.medicalcare.javabean.InquiryAddCase;
import store.chinaotec.com.medicalcare.javabean.MesageBean;
import store.chinaotec.com.medicalcare.javabean.TextReconizeBean;
import store.chinaotec.com.medicalcare.service.RecogService;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.GetPathFromUri4kitkat;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.FileUtil;
import store.chinaotec.com.medicalcare.utill.FileUtils;
import store.chinaotec.com.medicalcare.utill.InitToolUtill;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.SysUtill;
import store.chinaotec.com.medicalcare.utill.ToastUtil;
import store.chinaotec.com.medicalcare.utill.VoiceAnyUtill;

/**
 * Created by wjc on 2018/3/8 0008.
 * 智能问诊
 */

public class IntelligentInquiryActivity extends AppCompatActivity implements View.OnClickListener {
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
    private SpeechRecognizer mIat;
    private String mEngineType;
    private RecognizerDialog mIatDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();// 用HashMap存储听写结果
    private List<MesageBean> list;
    private SharedPreferences sharedPreferences;
    private String memberid, questionPre, maa, sendMess, logo, sid;
    private String caseCode;
    private Uri imageUri;
    private MyVoiceAdapter myVoiceAdapter;
    private String sessionId;
    private TelephonyManager tm;
    private String base64Datas;
    private String action;
    private String bindStatus;
    private PropUploadDialog propUploadDialog;
    private boolean daiShu;
    public static final String TAG = "IntelligentInquiryActivity";
    private String mFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_connect);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        //设置引擎类型  语音听写只支持在线听写
        mEngineType = SpeechConstant.TYPE_CLOUD;
        //初始化语音识别对话框对象
        mIatDialog = new RecognizerDialog(this, mInitListener);
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
        sessionId = SpUtill.getString(this, ResourseSum.Medica_SP, "sessionId");
        //每次进入沟通页面需要发起一次问诊调用LaunchCommunication接口
        SpUtill.putString(this, ResourseSum.Medica_SP, "tag", "yes");

    }

    private void initData() {
        titleBack.setOnClickListener(this);
        selfSend.setOnClickListener(this);
        selfVoice.setOnClickListener(this);

        selfSend.setEnabled(true);
        selfSend.setBackgroundResource(R.drawable.round_shape_send);
        selfSend.setTextColor(getResources().getColor(R.color.white));
        selfSend.setText("发送");

        comunicatRecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myVoiceAdapter = new MyVoiceAdapter(this, list, sharedPreferences);
        comunicatRecycleview.setAdapter(myVoiceAdapter);
        //初始化OCR事例
        InitToolUtill.initAccessTokenWithAkSk(this);
        //是否选择"带述"
        daiShu = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "daishu");
        boolean login = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "login");
//        caseCode = sharedPreferences.getString("caseCode", "");
        boolean isScan = SpUtill.getBoolen(this, ResourseSum.Medica_SP, "is_scan");
        if (!isScan){
            createCaseCode(daiShu);
        }else {
            caseCode = sharedPreferences.getString("caseCode", "");
            sessionId = sharedPreferences.getString("sessionId", "");
            bindDevice(caseCode,sessionId);
        }
    }

    private void createCaseCode(boolean daiShu) {
        int sex = sharedPreferences.getInt("sex", 22);
        String daiShuAge = sharedPreferences.getString("daishuage", "");
        getCaseCode(daiShu,sex,daiShuAge);
    }

    private void getCaseCode(boolean daiShu, int sex, String daiShuAge) {
        String type;
        if (daiShu){
            type = "1";
        }else {
            type = "0";
        }
        OkHttpUtils.post().url(MyUrl.INQUIRY_ADD_CASE).addParams("sid",sid).addParams("sex",sex+"").addParams("birthdate",daiShuAge)
                .addParams("type",type).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                InquiryAddCase inquiryAddCase = JSON.parseObject(response, InquiryAddCase.class);
                if ("0".equals(inquiryAddCase.getResponseCode())){
                    caseCode = inquiryAddCase.getData().getCasecode();
                    sessionId = inquiryAddCase.getData().getSessionid();
                    if (MyCommonUtil.isEmpty(caseCode)){
                        ToastUtil.showToast(IntelligentInquiryActivity.this, "创建病例号失败");
                        return;
                    }

                    //保存病历号
                    sharedPreferences.edit().putString("caseCode", caseCode).apply();
                    sharedPreferences.edit().putString("sessionId", sessionId).apply();
                    MyLog.d("caseCode为.." + caseCode);


                    bindDevice(caseCode,sessionId);
//                    beginQuestion(caseCode);
                }else {
                    ToastUtil.showToast(IntelligentInquiryActivity.this, "创建病例号异常");
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void bindDevice(String caseCode, String sessionId) {
        OkHttpUtils.post().url(MyUrl.INQUIRY_BIND_DEVICE).addParams("casecode",caseCode).addParams("sessionid",sessionId)
                .addParams("mac",MyApp.getContext().getImei()).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                BindDevice bindDevice = JSON.parseObject(response, BindDevice.class);
                if ("0".equals(bindDevice.getResponseCode())){
                    bindStatus = bindDevice.getData().getFinish();
                }else {
                    ToastUtil.showToast(IntelligentInquiryActivity.this,bindDevice.getMsg());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                    if ("0".equals(bindStatus)){
                        beginQuestion(caseCode);
                    }

                } else {
                    BaseUtill.toastUtil("请输入聊天信息");
                }
                break;
            //开始语音听写
            case R.id.self_voice:
                VoiceToWrite();
                break;
            case R.id.iv_title_back:
//                if (daiShu){
                    sharedPreferences.edit().putString("caseCode", "").apply();
//                }
                finish();
                break;
        }
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
                //点击取消上传后,显示"取消上传"在会话列表
                list.add(new MesageBean(MesageBean.SEND_TYPE, "取消上传(没有)", imageUri));
                myVoiceAdapter.notifyItemInserted(list.size() - 1);
                comunicatRecycleview.scrollToPosition(list.size() - 1);
                propUploadDialog.dismiss();
                //调用预解析接口response传入ocr_result为null,width为o,height为0合成的json字符串
                initOCRdata();
            }
        });
        propUploadDialog.setOnClickUploadListener(new PropUploadDialog.UploadListener() {
            @Override
            public void upload() {
                //打开相机

                File appDir = new File(Environment.getExternalStorageDirectory(),"/zhyl/");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String timeStamp = String.valueOf(System.currentTimeMillis());
                String fileName = timeStamp + ".jpg";
                File outputImage = new File(appDir, fileName);
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mFilePath = outputImage.getAbsolutePath();

                InitToolUtill.ocrOpenCamera(IntelligentInquiryActivity.this, REQUEST_CODE_GENERAL,mFilePath);

//                Uri imgUri = FileUtil.getUriForFile(IntelligentInquiryActivity.this,outputImage);// 传递路径
//                //  启动系统相机 意图 相机
//                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);// 更改系统默认存储路径
//                // 如果有相机
//                if (openCameraIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(openCameraIntent, REQUEST_CODE_GENERAL);// 启动系统相机
//                }
                // 去掉提示上传图片的弹窗
                if (propUploadDialog != null){
                    propUploadDialog.dismiss();
                }
            }
        });
    }

    /**
     * 弹窗提示用户上传单据,并调用预解析接口,传入参数response值为TextReconizeBean对象转化的json字符串
     */
    private void initOCRdata() {
        TextReconizeBean textReconizeBean = new TextReconizeBean(0, 0, "");
        String synJson = MyApp.getGson().toJson(textReconizeBean);
        sendMess = synJson;
        beginQuestion(caseCode);
    }

    private int width;
    private int height;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            Uri uri = Uri.fromFile(new File(mFilePath));
//            Uri uri = Uri.fromFile(FileUtil.getSaveFile(getApplicationContext()));
            //把Uri转换成base64
            String path = GetPathFromUri4kitkat.getPath(this, uri);
            BASE64Encoder baseEncode = new BASE64Encoder();
            base64Datas  = baseEncode.encode(FileUtil.getBitmap(path));

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
                    beginQuestion(caseCode);

                }
            });
            //显示图片到聊天记录
            list.add(new MesageBean(MesageBean.PHOTO_TYPE, "", uri));
            myVoiceAdapter.notifyItemInserted(list.size() - 1);
            comunicatRecycleview.scrollToPosition(list.size() - 1);
        }
    }

    private void beginQuestion(String caseCode) {
        if (TextUtils.isEmpty(questionPre)) {
             questionPre = "现在请您描述病情?";
        }
        if (TextUtils.isEmpty(action)) {
            action = "";
        }
        if (TextUtils.isEmpty(base64Datas)){
            base64Datas = "";
        }
        OkHttpUtils.post().url(MyUrl.INQUIRY_PREANALYSIS).addParams("sessionid",sessionId).addParams("question",questionPre).
                addParams("response",sendMess).addParams("casecode",caseCode).addParams("action",action)
                .addParams("mac",MyApp.getContext().getImei()).addParams("base64Datas",base64Datas).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                AskQuestion askQuestion = JSON.parseObject(response, AskQuestion.class);
                MyLog.d("AskQuestion.." + askQuestion.toString());
                if ("0".equals(askQuestion.getResponseCode())){
                    action = askQuestion.getData().getAction();

                    if ("question".equals(action)){
                        questionPre = askQuestion.getData().getQuestion();
                    }else if ("upload_image".equals(action)){
                        questionPre = askQuestion.getData().getQuestion();
                        //加弹窗提示上传单据
                        showPropUpload(questionPre);
                    }

                    if ("0".equals(askQuestion.getData().getFinish())){
                        //展示合成的问题
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE, questionPre, imageUri));
                        myVoiceAdapter.notifyItemInserted(list.size() - 1);
                        comunicatRecycleview.scrollToPosition(list.size() - 1);

                        selfSend.setEnabled(true);
                        selfSend.setBackgroundResource(R.drawable.round_shape_send);
                        selfSend.setTextColor(getResources().getColor(R.color.white));
                        selfSend.setText("发送");

                    }else if("1".equals(askQuestion.getData().getFinish())){
                        list.add(new MesageBean(MesageBean.RECEIVE_TYPE,askQuestion.getData().getConclusion(), imageUri));
                        sharedPreferences.edit().putString("caseCode", "").apply();
                        myVoiceAdapter.notifyItemInserted(list.size() - 1);
                        comunicatRecycleview.scrollToPosition(list.size() - 1);

                        selfSend.setEnabled(false);
                        selfSend.setBackgroundResource(R.drawable.bg_gray);
                        selfSend.setTextColor(getResources().getColor(R.color.colorTooBar));
                        selfSend.setText("问诊结束");

                    }else if("2".equals(askQuestion.getData().getFinish())){
                        BaseUtill.toastUtil(askQuestion.getMsg());
                    }

                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(this);
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
        sharedPreferences.edit().putString("caseCode", "").apply();
    }

    /**
     * 科大讯飞语音输入文字听写
     */
    private void VoiceToWrite() {
        // 移动数据分析，收集开始听写事件
        try {
            FlowerCollector.onEvent(this, "iat_recognize");
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
            String saveSid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (daiShu){
            sharedPreferences.edit().putString("caseCode", "").apply();
//        }
    }
}
