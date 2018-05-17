package store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;

/********************************************************************
 * [Summary] TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要 [Remarks]
 * TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/

public class CustomProgressDialog extends Dialog {

    private boolean falg = true;

    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.customprogressdialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        TextView textView = (TextView) findViewById(R.id.id_tv_loadingmsg);
        AnimationDrawable animationDrawable = (AnimationDrawable) textView.getCompoundDrawables()[0];

        animationDrawable.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (this.isShowing()) {
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        falg = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        falg = true;

    }

}