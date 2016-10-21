package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.utils.MFGT;

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
                break;
            case R.id.btn_Register:
                MFGT.startActivity(this,RegisterActivity.class);
                break;
        }
    }
}
