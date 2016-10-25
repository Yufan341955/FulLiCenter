package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.dao.SharePrefrenceUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);
        ButterKnife.bind(this);
        user= FuLiCenterApplication.getUser();
        initData();

    }

    private void initData() {
        UserName.setText(user.getMuserName());
        Nick.setText(user.getMuserNick());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),this,ivAvatar);
    }

    @OnClick({R.id.ivAvatar, R.id.UserName, R.id.iv_erw, R.id.btn_exists})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAvatar:
                break;
            case R.id.UserName:
                break;
            case R.id.iv_erw:
                break;
            case R.id.btn_exists:
                SharePrefrenceUtils.getInstance(this).removeUser();
                FuLiCenterApplication.setUser(null);
                MFGT.startActivity(UpdatePersonActivity.this,LoginActivity.class);
                this.finish();
                break;
        }
    }
}
