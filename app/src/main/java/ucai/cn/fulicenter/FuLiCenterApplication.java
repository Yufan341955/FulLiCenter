package ucai.cn.fulicenter;

import android.app.Application;

import ucai.cn.fulicenter.bean.UserAvatar;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;
    private static UserAvatar user;

    public static UserAvatar getUser() {
        return user;
    }

    public static void setUser(UserAvatar user) {
        FuLiCenterApplication.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        instance=this;
    }

    public static FuLiCenterApplication getInstance(){
     if(instance==null){
         instance=new FuLiCenterApplication();
     }
        return instance;
    }

}
