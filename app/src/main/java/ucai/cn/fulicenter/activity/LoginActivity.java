package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.Result;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.backAuto)
    LinearLayout backAuto;
    @Bind(R.id.et_user)
    EditText metUser;
    @Bind(R.id.et_password)
    EditText metPassword;
    @Bind(R.id.btn_Login)
    Button mbtnLogin;
    @Bind(R.id.btn_Register)
    Button mbtnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.backAuto, R.id.btn_Login, R.id.btn_Register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backAuto:
                MFGT.finish(this);
                break;
            case R.id.btn_Login:
                String username=metUser.getText().toString().trim();
                String password=metPassword.getText().toString().trim();
                if(username.isEmpty()){
                    CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
                    metUser.requestFocus();
                    return;
                }else if(password.isEmpty()){
                    CommonUtils.showLongToast(R.string.password_connot_be_empty);
                    metPassword.requestFocus();
                    return;
                }else {
                    login(username,password);
                }
                break;
            case R.id.btn_Register:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivityForResult(intent,3);
                break;
        }
    }

    private void login(String username, String password) {
        NetDao.login(this, username, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if(result==null){
                    CommonUtils.showLongToast("登录失败");
                   return;
                }
                if(result.isRetMsg()){
                    String json=result.getRetData().toString();
                    Gson gson=new Gson();
                    UserAvatar user=gson.fromJson(json,UserAvatar.class);
                    CommonUtils.showLongToast("登录成功");
                    L.e("user="+user.toString());
                }else {
                    CommonUtils.showLongToast("登录失败");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=2){
            return;
        }
        switch (requestCode){
            case 3:
                String UserName=data.getStringExtra("username");
               metUser.setText(UserName);
                break;

        }
    }
}
