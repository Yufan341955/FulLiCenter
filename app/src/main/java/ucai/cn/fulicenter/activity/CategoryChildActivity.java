package ucai.cn.fulicenter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.utils.L;

public class CategoryChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        int id=getIntent().getIntExtra("id",0);
        L.e("Id="+id+"");
    }
}
