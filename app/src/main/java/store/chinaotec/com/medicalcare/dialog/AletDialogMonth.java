package store.chinaotec.com.medicalcare.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.lang.reflect.Field;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by hxk on 2017/10/20 0020 13:44
 * 只显示月份的日期选择弹窗
 */

public class AletDialogMonth extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener {
    private final DatePicker mDatePickerStart;
    private final OnDateSetListener mCallBack;

    public interface OnDateSetListener {
        void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth);
    }

    public AletDialogMonth(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth);
    }


    public AletDialogMonth(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
                           int dayOfMonth) {
        super(context, theme);
        mCallBack = callBack;
        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "确 定", this);
        setButton(BUTTON_NEGATIVE, "取 消", this);
        setIcon(0);
        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_date_picker, null);
        setView(view);
        mDatePickerStart = (DatePicker) view.findViewById(R.id.datePickerStart);
        mDatePickerStart.init(year, monthOfYear, dayOfMonth, this);
        hideYearDay(mDatePickerStart);
    }

    private void hideYearDay(DatePicker mDatePicker) {
        try {
            /* 处理android5.0以上的特殊情况 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
                if ((daySpinnerId != 0) && (yearSpinnerId != 0)) {
                    View daySpinner = mDatePicker.findViewById(daySpinnerId);
                    View yearSpinner = mDatePicker.findViewById(yearSpinnerId);
                    if ((daySpinner != null) && yearSpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                        yearSpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
                for (Field datePickerField : datePickerfFields) {
                    if (("mYearSpinner".equals(datePickerField.getName()) || ("mYearPicker").equals(datePickerField.getName())) &&
                            ("mDaySpinner".equals(datePickerField.getName()) || ("mDayPicker").equals(datePickerField.getName()))) {
                        datePickerField.setAccessible(true);
                        Object yearPicker = new Object();
                        Object dayPicker = new Object();
                        try {
                            yearPicker = datePickerField.get(mDatePicker);
                            dayPicker = datePickerField.get(mDatePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                        ((View) yearPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_POSITIVE)
            tryNotifyDateSet();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.datePickerStart)
            mDatePickerStart.init(year, month, day, this);
    }

    private void tryNotifyDateSet() {
        if (mCallBack != null) {
            mDatePickerStart.clearFocus();
            mCallBack.onDateSet(mDatePickerStart, mDatePickerStart.getYear(), mDatePickerStart.getMonth(),
                    mDatePickerStart.getDayOfMonth());
        }
    }
}
