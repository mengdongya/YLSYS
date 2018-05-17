package store.chinaotec.com.medicalcare.utill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by hxk on 2017/9/14 0014 18:54
 * 初始化类汇总
 */

public class InitToolUtill {
    /**
     * 初始化OCR对象
     */
    public static void initAccessTokenWithAkSk(Context context) {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("error", error.getMessage());
            }
        }, context.getApplicationContext(), "GbLTYsnVWGg9YKAgSkuMoiPB", "jpTOpPaiMWWycf9HZncM0ZRH3ZxCTHmx");
    }
    /**
     * 听写UI监听器
     */
    public static RecognizerDialogListener getUIlistener(final HashMap<String, String> mIatResults, final String sendMess,
                                                         final EditText intoText) {
        RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
            public void onResult(RecognizerResult results, boolean isLast) {
                printResult(results, mIatResults, sendMess, intoText);
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        };
        return mRecognizerDialogListener;
    }

    public static void printResult(RecognizerResult results, HashMap<String, String> mIatResults, String sendMess, EditText intoText) {
        String text = JsonParserUtill.parseIatResult(results.getResultString());
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
        //获取语音转换的文字信息
        sendMess = resultBuffer.toString();
        intoText.setText(resultBuffer.toString());
        intoText.setSelection(intoText.length());
    }

    /**
     * @param activity  上下文对象
     * @param REQUEST_CODE_GENERAL 百度ocr自带打开照相机方法
     */
    public static void ocrOpenCamera(Activity activity, int REQUEST_CODE_GENERAL,String mFilePath) {
        Intent intent = new Intent(activity, CameraActivity.class);
//        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,FileUtil.getSaveFile(activity.getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,mFilePath);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_GENERAL);
        activity.startActivityForResult(intent, REQUEST_CODE_GENERAL);
    }
}
