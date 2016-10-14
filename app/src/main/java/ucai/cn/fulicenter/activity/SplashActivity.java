package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ucai.cn.fulicenter.R;

public class SplashActivity extends AppCompatActivity {
    final long SPLASH_TIME=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = SystemClock.currentThreadTimeMillis();
                //BD耗时操作
                long costTime = SystemClock.currentThreadTimeMillis()-startTime;
                if(SPLASH_TIME-costTime>0){
                    try {
                        Thread.sleep(SPLASH_TIME-costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }).start();

    }
}
