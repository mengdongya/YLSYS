package store.chinaotec.com.medicalcare.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.utill.BaseUtill;

/**
 * Created by hxk on 2017/10/15 0015 17:14
 * 慢性病模块首页,"按年"选择日期弹窗
 */

public class PickYearDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AletDialogYear aletDialogYear = new AletDialogYear(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new AletDialogYear.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                String choseYear = String.format("%d", startYear);
                if (choseYearListener != null) {
                    choseYearListener.choseDate(choseYear);
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        aletDialogYear.show();
        return aletDialogYear;
    }

    private ChoseYearListener choseYearListener;

    public interface ChoseYearListener {
        /**
         * @param choseYear 选中年份
         */
        void choseDate(String choseYear);
    }

    public void setChoseDate(ChoseYearListener choseYearListener) {
        this.choseYearListener = choseYearListener;
    }
}
