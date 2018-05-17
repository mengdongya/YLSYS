package store.chinaotec.com.medicalcare.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by hxk on 2017/10/20 0020 17:01
 * 删除求救医生弹窗
 */

public class DeleteDoctorDialog extends DialogFragment implements View.OnClickListener {

    private DeleteDoctorListener mDeleteDoctorListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        //设置自定义布局view
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_delete_doct, null);
        alertDialog.setContentView(inflate);
        //设置弹窗大小位置
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        //设置弹窗大小
        attributes.width = 300;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置弹窗位置
        attributes.x = Gravity.CENTER_HORIZONTAL;
        attributes.y = Gravity.CENTER_VERTICAL;
        window.setAttributes(attributes);
        //设置按钮点击事件
        Button doctorDelete = (Button) inflate.findViewById(R.id.doctor_delete);
        doctorDelete.setOnClickListener(this);
        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doctor_delete:
                if (mDeleteDoctorListener != null) {
                    mDeleteDoctorListener.deleteDoctor();
                }
                //点击"删除"按钮后关闭当前弹窗
                dismiss();
                break;
        }
    }

    /**
     * 点击弹窗上"删除"按钮监听回调
     */
    public interface DeleteDoctorListener {
        /**
         * 删除逻辑执行方法
         */
        void deleteDoctor();
    }

    public void setDeleteDoctor(DeleteDoctorListener deleteDoctorListener) {
        mDeleteDoctorListener = deleteDoctorListener;
    }
}
