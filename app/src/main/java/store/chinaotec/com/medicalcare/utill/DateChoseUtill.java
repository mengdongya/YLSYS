package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by hxk on 2017/10/10 0010 15:28
 * 日期弹窗工具
 */
public class DateChoseUtill {
    private static TimePickerView timePickerView;

    public interface GetChoseDateListener {
        void getChoseDate(String date);
    }

    /**
     * @param context  上下文对象
     * @param getChoseDateListener   选中日期监听回调
     *                               慢性病模块,病人信息输入页面,年龄,起病时间通过弹窗获取时间
     */
    public static void choseDate(Context context, final GetChoseDateListener getChoseDateListener, final String dateStyle) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //当前选中的时间  "yyyy-MM-dd"
                String format = new SimpleDateFormat(dateStyle).format(date);
                if (getChoseDateListener != null) {
                    getChoseDateListener.getChoseDate(format);
                }
            }
        })
                .isDialog(true).
                        setDate(selectedDate).
                        setLayoutRes(R.layout.date_chose_dialog, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                RadioGroup dateChoseGroup = (RadioGroup) v.findViewById(R.id.date_chose_group);
                                dateChoseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                        switch (checkedId) {
                                            case R.id.date_chose_cancle:
                                                timePickerView.dismiss();
                                                break;
                                            case R.id.date_chose_sure:
                                                timePickerView.returnData();
                                                timePickerView.dismiss();
                                                break;
                                        }
                                    }
                                });
                            }
                        }).setType(new boolean[]{true, true, true, false, false, false}).
                        isCenterLabel(false).
                        build();
        timePickerView.show();
    }
}
