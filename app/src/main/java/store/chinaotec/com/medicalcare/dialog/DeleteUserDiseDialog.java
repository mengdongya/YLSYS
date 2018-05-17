package store.chinaotec.com.medicalcare.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.BaseActivity;
import store.chinaotec.com.medicalcare.utill.BaseUtill;

/**
 * Created by Administrator on 2017/11/22 0022.
 * 删除用户的疾病种类弹窗
 */

public class DeleteUserDiseDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.delete_dise_dialog, null);
        alertDialog.setContentView(inflate);

        RadioGroup deleteJudge = (RadioGroup) inflate.findViewById(R.id.delete_judge);
        deleteJudge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.delete_dise_cancle:
                        dismiss();
                        break;
                    case R.id.delete_dise_sure:
                        if (mSureDeleteDiseListener != null) {
                            mSureDeleteDiseListener.sureDelete();
                        }

                        dismiss();
                        break;
                }
            }
        });
        return alertDialog;
    }

    private SureDeleteDiseListener mSureDeleteDiseListener;

    /**
     * 确定"删除"监听
     */
    public interface SureDeleteDiseListener {
        /**
         * 确定"删除" 监听回调方法
         */
        void sureDelete();
    }

    /**
     * @param sureDeleteDiseListener 确定删除监听
     *                               设置确定删除监听回调
     */
    public void setDeleteDiseSure(SureDeleteDiseListener sureDeleteDiseListener) {
        this.mSureDeleteDiseListener = sureDeleteDiseListener;
    }
}
