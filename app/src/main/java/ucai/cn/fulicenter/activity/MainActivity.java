package ucai.cn.fulicenter.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
    //RadioButton rbtnNewGoods, rbtnBoutique, rbtnCategory, rbtnCart, rbtnProsen;
    @Bind(R.id.rbtn_newGoods)
    RadioButton rbtnNewGoods;
    @Bind(R.id.rbtn_boutique)
    RadioButton rbtnBoutique;
    @Bind(R.id.rbtn_category)
    RadioButton rbtnCategory;
    @Bind(R.id.rbtn_cart)
    RadioButton rbtnCart;
    @Bind(R.id.tvCarthint)
    TextView tvCarthint;
    @Bind(R.id.rbtn_prosen)
    RadioButton rbtnProsen;
    int index;
    RadioButton[] rbs;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rbs=new RadioButton[5];
        mFragments=new Fragment[5];
        rbs[0]=rbtnNewGoods;
        rbs[1]=rbtnBoutique;
        rbs[2]=rbtnCategory;
        rbs[3]=rbtnCart;
        rbs[4]=rbtnProsen;
        mNewGoodsFragment=new NewGoodsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
                transaction.add(R.id.frame,mNewGoodsFragment);
        transaction.commit();
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.rbtn_newGoods:
                index=0;
                break;
            case R.id.rbtn_boutique:
                index=0;
                break;
            case R.id.rbtn_category:
                index=0;
                break;
            case R.id.rbtn_cart:
                index=0;
                break;
            case R.id.rbtn_prosen:
                index=0;
                break;
        }
    }

    public void singleChecked(int index) {
        for(int i=0;i<rbs.length;i++){
            if(i==index){
                rbs[i].setChecked(true);
            }else {
                rbs[i].setChecked(false);
            }
        }

    }


}
