package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.R;

public class CategoryChildActivity extends AppCompatActivity {


    @Bind(R.id.btn_Price)
    Button btnPrice;
    @Bind(R.id.btn_Time)
    Button btnTime;
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.Srl)
    SwipeRefreshLayout Srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        int id = getIntent().getIntExtra("id", 0);

    }

    @OnClick({R.id.backAuto, R.id.btn_Price, R.id.btn_Time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backAuto:
                break;
            case R.id.btn_Price:
                break;
            case R.id.btn_Time:
                break;
        }
    }
}
