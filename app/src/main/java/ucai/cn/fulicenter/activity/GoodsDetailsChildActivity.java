package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;

public class GoodsDetailsChildActivity extends BaseActivity {


    @Bind(R.id.backAuto)
    LinearLayout backAuto;
    @Bind(R.id.title_Name)
    TextView titleName;
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.Srl)
    SwipeRefreshLayout Srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details_child);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String name=intent.getStringExtra("name");
        int id=intent.getIntExtra("id",0);
        titleName.setText(name);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {

    }
}
