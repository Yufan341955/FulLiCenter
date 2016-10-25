package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.dao.SharePrefrenceUtils;
import ucai.cn.fulicenter.dao.UserDao;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserAvatar user=FuLiCenterApplication.getUser();
                String username=SharePrefrenceUtils.getInstance(SplashActivity.this).getUser();
                if(user==null&&username!=null) {
                    UserDao dao = new UserDao(SplashActivity.this);
                    user = dao.getUser(username);
                    if(user!=null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                    MFGT.gotoMainActivity(SplashActivity.this);
                    finish();


            }
        },SPLASH_TIME);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long startTime = SystemClock.currentThreadTimeMillis();
//                //BD耗时操作
//                long costTime = SystemClock.currentThreadTimeMillis()-startTime;
//                if(SPLASH_TIME-costTime>0){
//                    try {
//                        Thread.sleep(SPLASH_TIME-costTime);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                MFGT.gotoMainActivity(SplashActivity.this);
//                //startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                finish();
//                //MFGT.finish(SplashActivity.this);
//            }
//        }).start();

    }
}
