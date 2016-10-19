package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/10/19.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        initData();
        setListener();
    }

    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void initView();

}
