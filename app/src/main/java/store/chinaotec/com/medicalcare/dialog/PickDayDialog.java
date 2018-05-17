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
 * 慢性病模块首页,"按日"选择日期弹窗
 */

public class PickDayDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AletDialogStartEnd aletDialogStartEnd = new AletDialogStartEnd(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new AletDialogStartEnd.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                  int endDayOfMonth) {
                String startDate = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                String endDate = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                String startShowDate = String.format("%d/%d/%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                String endShowDate = String.format("%d/%d/%d", endYear, endMonthOfYear + 1, endDayOfMonth);
                if (choseDateListener != null) {
                    choseDateListener.choseDate(startDate, endDate, startShowDate, endShowDate);
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
        aletDialogStartEnd.show();
        return aletDialogStartEnd;
    }

    private ChoseDateListener choseDateListener;

    public interface ChoseDateListener {
        /**
         * @param startDate 选中的开始日期
         * @param endDate   选中的结束日期
         */
        void choseDate(String startDate, String endDate, String startShowDate, String endShowDate);
    }

    public void setChoseDate(ChoseDateListener choseDateListener) {
        this.choseDateListener = choseDateListener;
    }
}
