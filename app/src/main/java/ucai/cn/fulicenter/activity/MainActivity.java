package ucai.cn.fulicenter.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import ucai.cn.fulicenter.R;

public class MainActivity extends AppCompatActivity {
   RadioButton rbtnNewGoods,rbtnBoutique,rbtnCategory,rbtnCart,rbtnProsen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rbtnNewGoods= (RadioButton) findViewById(R.id.rbtn_newGoods);
        rbtnBoutique= (RadioButton) findViewById(R.id.rbtn_boutique);
        rbtnCategory= (RadioButton) findViewById(R.id.rbtn_category);
        rbtnCart= (RadioButton) findViewById(R.id.rbtn_cart);
        rbtnProsen= (RadioButton) findViewById(R.id.rbtn_prosen);
    }

    public void onCheckedChange(View v){
        switch (v.getId()){
            case R.id.rbtn_newGoods:
                singleChecked((RadioButton) v);
            break;
            case R.id.rbtn_boutique:
                singleChecked((RadioButton) v);
            break;
            case R.id.rbtn_category:
                singleChecked((RadioButton) v);
            break;
            case R.id.rbtn_cart:
                singleChecked((RadioButton) v);
            break;
            case R.id.rbtn_prosen:
                singleChecked((RadioButton) v);
            break;
        }
    }
    public void singleChecked(RadioButton radio){
        if(radio!=rbtnNewGoods){
            rbtnNewGoods.setChecked(false);
        }
        if(radio!=rbtnBoutique){
            rbtnBoutique.setChecked(false);
        }
        if(radio!=rbtnCategory){
            rbtnCategory.setChecked(false);
        }
        if(radio!=rbtnCart){
            rbtnCart.setChecked(false);
        }
        if(radio!=rbtnProsen){
            rbtnProsen.setChecked(false);
        }

    }


}
