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
import store.chinaotec.com.medicalcare.utill.BaseUtill;

/**
 * Created by Administrator on 2017/11/22 0022.
 * 修改用户的疾病种类弹窗
 */

public class EditUserDiseDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.edit_dise_dialog, null);
        alertDialog.setContentView(inflate);

        RadioGroup deleteJudge = (RadioGroup) inflate.findViewById(R.id.edit_judge);
        deleteJudge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.edit_dise_cancle:
                        dismiss();
                        break;
                    case R.id.edit_dise_sure:
                        if (mSureEditDiseListener != null) {
                            mSureEditDiseListener.sureEdit();
                        }
                        BaseUtill.toastUtil("修改成功");
                        dismiss();
                        break;
                }
            }
        });
        return alertDialog;
    }

    private SureEditDiseListener mSureEditDiseListener;

    /**
     * 确定"修改"监听
     */
    public interface SureEditDiseListener {
        /**
         * 确定"修改" 监听回调方法
         */
        void sureEdit();
    }

    /**
     * @param sureEditDiseListener 确定修改监听
     *                             设置确定修改监听回调
     */
    public void setEditDiseSure(SureEditDiseListener sureEditDiseListener) {
        this.mSureEditDiseListener = sureEditDiseListener;
    }
}
