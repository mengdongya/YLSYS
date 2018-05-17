package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by on 2018/3/6 0006.
 * 启动页
 */

public class WelcomeActivity extends AppCompatActivity{
    private static Handler msHandler ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        msHandler = new Handler();
        msHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                turnTo();
            }
        },3000);
    }

    private void turnTo() {
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}
