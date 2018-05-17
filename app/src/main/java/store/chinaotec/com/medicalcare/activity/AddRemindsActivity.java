package store.chinaotec.com.medicalcare.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 添加体检提醒页面并提交
 */
public class AddRemindsActivity extends BaseActivity implements View.OnClickListener {
    private final int DATE_DIALOG = 1;
    private final int TIME_DIALOG = 2;
    @Bind(R.id.linear_examination_date)
    LinearLayout linearExaminationDate;
    @Bind(R.id.examination_time)
    TextView examinationTime;
    @Bind(R.id.linear_examination_time)
    LinearLayout linearExaminationTime;
    @Bind(R.id.submit_add_medica)
    Button submitAddMedica;
    @Bind(R.id.examination_date)
    TextView examinationDate;
    @Bind(R.id.examination_remark)
    EditText examinationRemark;
    @Bind(R.id.addMedicaHospRecycleview)
    RecyclerView addMedicaHospRecycleview;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            display();
        }
    };
    private TimePickerDialog.OnTimeSetListener mtimeListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            display();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medic_remind);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        linearExaminationDate.setOnClickListener(this);
        linearExaminationTime.setOnClickListener(this);
        submitAddMedica.setOnClickListener(this);
        //初始化获取当前的年 月 日 小时 分钟
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
            case R.id.linear_examination_date:
                showDialog(DATE_DIALOG);
                break;
            case R.id.linear_examination_time:
                showDialog(TIME_DIALOG);
                break;
            case R.id.submit_add_medica:
                Toast.makeText(getApplicationContext(), "体检提醒添加成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
            case TIME_DIALOG:
                return new TimePickerDialog(this, mtimeListener, mHour, mMinute, true);
        }
        return null;
    }

    /**
     * 设置选中的日期和时间
     */
    public void display() {
        examinationDate.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
        examinationTime.setText(new StringBuffer().append(mHour).append("-").append(mMinute));
    }
}
