package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.Result;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.backAuto)
    LinearLayout backAuto;
    @Bind(R.id.et_user)
    EditText etUser;
    @Bind(R.id.et_nick)
    EditText etNick;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password_ok)
    EditText etPasswordOk;
    @Bind(R.id.btn_Register)
    Button btnRegister;
    String UserName;
    String Nick;
    String Password;
    String Passwordcomfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setListener();
    }

    private void setListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserName = etUser.getText().toString().trim();
                Nick = etNick.getText().toString().trim();
                Password = etPassword.getText().toString().trim();
                Passwordcomfirm = etPasswordOk.getText().toString().trim();
                if (UserName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    etUser.requestFocus();
                    return;
                } else if (!UserName.matches("[a-zA-Z]\\w{4,15}")) {
                    Toast.makeText(RegisterActivity.this, "用户名无效,以字母开头,长度为5—16", Toast.LENGTH_SHORT).show();
                    etUser.requestFocus();
                    return;
                } else if (Nick.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    etNick.requestFocus();
                    return;
                } else if (Password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                } else if (Passwordcomfirm.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "第二次输入密码不能为空", Toast.LENGTH_SHORT).show();
                    etPasswordOk.requestFocus();
                    return;
                } else if (!Password.equals(Passwordcomfirm)) {
                    Toast.makeText(RegisterActivity.this, "第二次输入密码有误", Toast.LENGTH_SHORT).show();
                    etPasswordOk.requestFocus();
                    return;
                } else {
                    register(UserName, Nick, Password);
                }
            }
        });
    }


    private void register(String userName, String nick, String password) {
        NetDao.register(this, userName, nick, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
             if(result==null){
                 CommonUtils.showLongToast(R.string.register_fail_exists);
             }else{
                 if(result.isRetMsg()){
                     CommonUtils.showLongToast(R.string.register_success);
                     Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                     intent.putExtra("username",UserName);
                     setResult(2,intent);
                     finish();
                 }else {
                     CommonUtils.showLongToast(R.string.register_fail_exists);
                 }
             }
            }

            @Override
            public void onError(String error) {
            CommonUtils.showLongToast(R.string.register_fail);
            }
        });
    }

    @OnClick(R.id.backAuto)
    public void onClick() {
        MFGT.finish(this);
    }

}
