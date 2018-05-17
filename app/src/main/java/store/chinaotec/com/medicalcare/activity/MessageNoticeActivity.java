package store.chinaotec.com.medicalcare.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * 消息通知页面
 */
public class MessageNoticeActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.news_push)
    ImageButton newsPush;
    @Bind(R.id.doctor_reply_reminder)
    ImageButton doctorReplyReminder;
    @Bind(R.id.health_plan_push)
    ImageButton healthPlanPush;
    @Bind(R.id.product_news_push)
    ImageButton productNewsPush;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notice);
        ButterKnife.bind(this);
        initlistener();
    }

    private void initlistener() {
        newsPush.setOnClickListener(this);
        doctorReplyReminder.setOnClickListener(this);
        healthPlanPush.setOnClickListener(this);
        productNewsPush.setOnClickListener(this);

        //存储配置信息
        sharedPreferences = getSharedPreferences("mesage_notice_config", getApplicationContext().MODE_APPEND);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_push:
                boolean aBoolean1 = sharedPreferences.getBoolean(ResourseSum.NEWS_PUSH_SWITCH, false);
                if (aBoolean1) {
                    newsPush.setBackgroundResource(R.drawable.message_notice_close);
                    sharedPreferences.edit().putBoolean(ResourseSum.NEWS_PUSH_SWITCH, false).commit();

                    Toast.makeText(getApplicationContext(), "新闻推送开关已经关闭", Toast.LENGTH_SHORT).show();
                } else {
                    newsPush.setBackgroundResource(R.drawable.message_notice_open);
                    sharedPreferences.edit().putBoolean(ResourseSum.NEWS_PUSH_SWITCH, true).commit();

                    Toast.makeText(getApplicationContext(), "新闻推送开关已经打开", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.doctor_reply_reminder:
                boolean aBoolean2 = sharedPreferences.getBoolean(ResourseSum.DOCTOR_REPLY_REMINDER, false);
                if (aBoolean2) {
                    doctorReplyReminder.setBackgroundResource(R.drawable.message_notice_close);
                    sharedPreferences.edit().putBoolean(ResourseSum.DOCTOR_REPLY_REMINDER, false).commit();
                } else {
                    doctorReplyReminder.setBackgroundResource(R.drawable.message_notice_open);
                    sharedPreferences.edit().putBoolean(ResourseSum.DOCTOR_REPLY_REMINDER, true).commit();
                }
                break;
            case R.id.health_plan_push:
                boolean aBoolean3 = sharedPreferences.getBoolean(ResourseSum.HEALTH_PLAN_PUSH, false);
                if (aBoolean3) {
                    healthPlanPush.setBackgroundResource(R.drawable.message_notice_close);
                    sharedPreferences.edit().putBoolean(ResourseSum.HEALTH_PLAN_PUSH, false).commit();
                } else {
                    healthPlanPush.setBackgroundResource(R.drawable.message_notice_open);
                    sharedPreferences.edit().putBoolean(ResourseSum.HEALTH_PLAN_PUSH, true).commit();
                }
                break;
            case R.id.product_news_push:
                boolean aBoolean4 = sharedPreferences.getBoolean(ResourseSum.PRODUCT_NEWS_PUSH, false);
                if (aBoolean4) {
                    productNewsPush.setBackgroundResource(R.drawable.message_notice_close);
                    sharedPreferences.edit().putBoolean(ResourseSum.PRODUCT_NEWS_PUSH, false).commit();
                } else {
                    productNewsPush.setBackgroundResource(R.drawable.message_notice_open);
                    sharedPreferences.edit().putBoolean(ResourseSum.PRODUCT_NEWS_PUSH, true).commit();
                }
                break;
        }
    }
}
