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

public class PickMonthDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AletDialogMonth aletDialogMonth = new AletDialogMonth(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new AletDialogMonth.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                String choseYearMonth = String.format("%d-%d", startYear, startMonthOfYear + 1);
                String showMonth = String.format("%d", startMonthOfYear + 1);
                if (choseMonthListener != null) {
                    choseMonthListener.choseDate(choseYearMonth,showMonth);
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        aletDialogMonth.show();
        return aletDialogMonth;
    }

    private ChoseMonthListener choseMonthListener;

    public interface ChoseMonthListener {
        /**
         * @param choseYearMonth 选中年 月份
         */
        void choseDate(String choseYearMonth,String showMonth);
    }

    public void setChoseDate(ChoseMonthListener choseMonthListener) {
        this.choseMonthListener = choseMonthListener;
    }
}
