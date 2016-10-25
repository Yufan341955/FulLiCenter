package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.Result;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.dao.SharePrefrenceUtils;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;
import ucai.cn.fulicenter.utils.OnSetAvatarListener;

public class UpdatePersonActivity extends AppCompatActivity {

    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.UserName)
    TextView UserName;
    @Bind(R.id.Nick)
    TextView Nick;
    @Bind(R.id.iv_erw)
    ImageView ivErw;
    @Bind(R.id.btn_exists)
    Button btnExists;
    UserAvatar user;
   OnSetAvatarListener mOnSetAvatarListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);
        ButterKnife.bind(this);
        user = FuLiCenterApplication.getUser();
        initData();

    }

    private void initData() {
        UserName.setText(user.getMuserName());
        Nick.setText(user.getMuserNick());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, ivAvatar);
    }

    @OnClick({R.id.ivAvatar, R.id.UserName, R.id.iv_erw, R.id.btn_exists,R.id.Nick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAvatar:
             mOnSetAvatarListener=new OnSetAvatarListener(this,R.id.activity_update_person,user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                L.e("ivAvatar.onClick()");
                break;
            case R.id.UserName:
                CommonUtils.showLongToast("用户名不可修改");
                break;
            case R.id.Nick:
                MFGT.startActivity(this,UpdateNickActivity.class);
                break;
            case R.id.iv_erw:
                break;
            case R.id.btn_exists:
                SharePrefrenceUtils.getInstance(this).removeUser();
                FuLiCenterApplication.setUser(null);
                MFGT.startActivity(UpdatePersonActivity.this, LoginActivity.class);
                this.finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        initData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            L.e("resultCode!=RESULT_OK");
                return;
        }
        mOnSetAvatarListener.setAvatar(requestCode,data,ivAvatar);
        L.e("requestCode="+requestCode);
        if(requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
            L.e("onActivityResult");
            updateAvatar();
        }

    }

    private void updateAvatar() {
        File file=new File(OnSetAvatarListener.getAvatarPath(this,user.getMavatarPath()+"/"+user.getMuserName()+".jpg"));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        NetDao.updateAvatar(this, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
               L.e("result="+result.toString());
            }

            @Override
            public void onError(String error) {

            }
        });

    }
}
