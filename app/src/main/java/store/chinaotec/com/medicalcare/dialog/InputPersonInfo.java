package store.chinaotec.com.medicalcare.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hxk on 2017/10/23 0023 15:12
 * 自主诊疗页面弹窗提示输入代述人性别,年龄个人信息
 */

public class InputPersonInfo extends DialogFragment implements View.OnClickListener {
    private AlertDialog alertDialog;
    private EditText daiShuText;
    private SharedPreferences sharedPreferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        //初始化sp对象
        sharedPreferences = getActivity().getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        //设置自定义弹窗布局
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_input_perinfor, null);
        alertDialog.setContentView(inflate);
        //监听屏蔽弹窗返回键点击事件
        alertDialog.setOnKeyListener(keylistener);
        //设置"确定","取消"按钮点击事件
        Button cancle = (Button) inflate.findViewById(R.id.cancle_chose);
        Button sure = (Button) inflate.findViewById(R.id.sure_chose);
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
        daiShuText = (EditText) inflate.findViewById(R.id.dai_shu_age);
        return alertDialog;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle_chose:
                if (choseCancleListener != null) {
                    choseCancleListener.choseCancle();
                }
                break;
            case R.id.sure_chose:
                String daiShuAge = daiShuText.getText().toString();
                if (choseSureListener != null) {
                    choseSureListener.choseSure(daiShuAge);
                }
                break;
        }
    }

    //"确定"按钮点击监听回调
    ChoseSureListener choseSureListener = null;

    public interface ChoseSureListener {
        void choseSure(String daiShuAge);
    }

    public void setChoseSure(ChoseSureListener choseSureListener) {
        this.choseSureListener = choseSureListener;
    }

    //"取消"按钮点击监听回调
    ChoseCancleListener choseCancleListener = null;

    public interface ChoseCancleListener {
        void choseCancle();
    }

    public void setChoseCancle(ChoseCancleListener choseCancleListener) {
        this.choseCancleListener = choseCancleListener;
    }
}
