package ucai.cn.fulicenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.Result;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.dao.UserDao;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class UpdateNickActivity extends AppCompatActivity {


    Context mContext;
    @Bind(R.id.et_nick)
    EditText etNick;
    @Bind(R.id.btn_Update_Nick)
    Button btnUpdateNick;
    UserAvatar user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        mContext=this;
       user= FuLiCenterApplication.getUser();
        etNick.setText(user.getMuserNick());
    }

    @OnClick(R.id.btn_Update_Nick)
    public void onClick() {
        if(user!=null){
            String username=user.getMuserName();
            String nick=etNick.getText().toString().trim();
            updateNick(username,nick);
            L.e("Nick"+nick);
        }

    }

    private void updateNick(String username, String nick) {
        NetDao.updateNick(mContext, username, nick, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String str) {
                L.e("onSuccess");
                if(str!=null){
                    Gson gson=new Gson();
                    Result result=gson.fromJson(str,Result.class);
                    if(result.getRetCode()== I.MSG_USER_UPDATE_NICK_SUCCESS){
                            CommonUtils.showLongToast("昵称修改成功");
                           String json=result.getRetData().toString();
                           UserAvatar user=gson.fromJson(json,UserAvatar.class);
                            L.e("user="+user.toString());
                           UserDao dao=new UserDao(mContext);
                           dao.savaUser(user);
                            FuLiCenterApplication.setUser(user);
                        MFGT.finish(UpdateNickActivity.this);
                    }else if(result.getRetCode()==I.MSG_USER_UPDATE_NICK_FAIL){
                        CommonUtils.showLongToast("昵称修改失败");
                    }else {
                        CommonUtils.showLongToast("修改昵称失败");
                    }
                }else {
                    CommonUtils.showLongToast("昵称修改失败");
                }
            }

            @Override
            public void onError(String error) {
                  CommonUtils.showLongToast(error);
            }
        });
    }
}
