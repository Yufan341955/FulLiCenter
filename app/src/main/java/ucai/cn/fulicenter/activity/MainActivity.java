package ucai.cn.fulicenter.activity;


import android.content.Intent;
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
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.fragment.BoutiqoeFragment;
import ucai.cn.fulicenter.fragment.CartFragment;
import ucai.cn.fulicenter.fragment.CategoryFragment;
import ucai.cn.fulicenter.fragment.NewGoodsFragment;
import ucai.cn.fulicenter.fragment.PersonFragment;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;

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
    int index=0;
    RadioButton[] rbs;
    Fragment[] mFragments;




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
        NewGoodsFragment mNewGoodsFragment=new NewGoodsFragment();
        BoutiqoeFragment mboutiqoeFragment=new BoutiqoeFragment();
        CategoryFragment mCategoryFragment=new CategoryFragment();
        CartFragment mCartFragment=new CartFragment();
        PersonFragment mPersonFragment=new PersonFragment();
        mFragments[0]=mNewGoodsFragment;
        mFragments[1]=mboutiqoeFragment;
        mFragments[2]=mCategoryFragment;
        mFragments[3]=mCartFragment;
        mFragments[4]=mPersonFragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(R.id.frame,mFragments[0]);
        transaction.commit();
    }

    public void onCheckedChange(View v) {
               index=0;
        switch (v.getId()) {
            case R.id.rbtn_newGoods:
                index=0;

                break;
            case R.id.rbtn_boutique:
                index=1;

                break;
            case R.id.rbtn_category:
                index=2;

                break;
            case R.id.rbtn_cart:
                if(FuLiCenterApplication.getUser()==null){
                    L.e("carttoLoginActivity");
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivityForResult(intent,9);
                }else {
                    index=3;
                }
                break;
            case R.id.rbtn_prosen:
                if(FuLiCenterApplication.getUser()==null){
                    L.e("metoLoginActivity");
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivityForResult(intent,4);
                    //MFGT.startActivity(this,LoginActivity.class);
                }else {
                    index=4;
                }
                break;
        }
        singleChecked(index);
        changeFragment(index);
    }

    private void changeFragment(int index) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.frame,mFragments[index]);
        transaction.commitAllowingStateLoss();
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

    @Override
    protected void onResume() {
        super.onResume();
        if(FuLiCenterApplication.getUser()==null){
            changeFragment(0);
            singleChecked(0);
        }else {
            changeFragment(index);
            singleChecked(index);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=5){
            return;
        }
        switch (requestCode){
            case 4:
                index=4;
                break;
            case 9:
                index=3;
                break;
        }
    }
}