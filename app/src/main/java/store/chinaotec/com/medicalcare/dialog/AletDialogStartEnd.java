package store.chinaotec.com.medicalcare.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by hxk on 2017/10/15 0015 17:51
 * 包含开始时间  结束时间选择器弹窗
 */

public class AletDialogStartEnd extends AlertDialog implements AlertDialog.OnClickListener, DatePicker.OnDateChangedListener {
    private final DatePicker picker_Start;
    private final DatePicker picker_End;
    private final OnDateSetListener mCallBack;

    public interface OnDateSetListener {
        void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,
                       DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth);
    }

    public AletDialogStartEnd(Context context, int theme, final OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme);
        mCallBack = callBack;
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_chose_day, null);
        setView(view);
        picker_Start = (DatePicker) view.findViewById(R.id.start_picker);
        picker_End = (DatePicker) view.findViewById(R.id.end_picker);
        picker_Start.init(year, monthOfYear, dayOfMonth, this);
        picker_End.init(year, monthOfYear, dayOfMonth, this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_POSITIVE)
            tryNotifyDateSet();
    }

    /**
     * 点击"确定"按钮后
     */
    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            picker_Start.clearFocus();
            picker_End.clearFocus();
            mCallBack.onDateSet(picker_Start, picker_Start.getYear(), picker_Start.getMonth(),
                    picker_Start.getDayOfMonth(), picker_End, picker_End.getYear(),
                    picker_End.getMonth(), picker_End.getDayOfMonth());
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.start_picker)
            picker_Start.init(year, monthOfYear, dayOfMonth, this);
        if (view.getId() == R.id.end_picker)
            picker_End.init(year, monthOfYear, dayOfMonth, this);
    }
}
