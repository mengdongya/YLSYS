package store.chinaotec.com.medicalcare.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 挂号操作页面
 */
public class RegistDoctorActivity extends BaseActivity implements View.OnClickListener {
    final int DATE_DIALOG = 1;
    @Bind(R.id.linear_appoint_time)
    LinearLayout linearAppointTime;
    @Bind(R.id.appoint_time)
    TextView appointTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatePicker dateRegist;
    //    private TimePicker timeRegist;
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            appointTime.setText(new StringBuffer().append(mYear).append("年").append("-")
                    .append(mMonth + 1).append("月").append("-").append(mDay).append("日"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_doctor);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        linearAppointTime.setOnClickListener(this);
        //初始化获取当前的年 月 日 小时  分钟
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_appoint_time:
//                chouseDateTime();
                showDialog(DATE_DIALOG);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private void chouseDateTime() {
       /* final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_date_time, null);
        dateRegist = (DatePicker) inflate.findViewById(R.id.date_regist);
//        timeRegist = (TimePicker) inflate.findViewById(R.id.time_regist);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateRegist.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                });
              *//*  timeRegist.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        minute = minute;
                    }
                });*//*
                appointTime.setText(new StringBuffer().append(mYear).append("-")
                        .append(mMonth + 1).append("-").append(mDay).append("-").append(mHour).
                                append("-").append(mMinute));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(inflate).show();*/
    }
}
