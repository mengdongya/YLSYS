package store.chinaotec.com.medicalcare.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by hxk on 2017/7/13 0013 11:32
 */

public class TimeView extends android.support.v7.widget.AppCompatTextView implements Runnable {
    private int msecond;//秒
    private boolean run = false; //是否启动了开始倒计时

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTimes(int second) {
        msecond = second;
    }

    public boolean isRun() {
        return run;
    }

    public void beginRun() {
        this.run = true;
        this.setClickable(false);
        run();
    }

    public void stopRun() {
        this.run = false;
        this.setClickable(true);
    }

    @Override
    public void run() {
        //标示已经启动
        if (run) {
            msecond--;
            if (msecond < 0) {
                stopRun();
                this.setText("获取验证码");
            } else {
                String strTime = msecond + "s";
                this.setText(strTime);
                postDelayed(this, 1000);
            }
        } else {
            removeCallbacks(this);
        }
    }
}
