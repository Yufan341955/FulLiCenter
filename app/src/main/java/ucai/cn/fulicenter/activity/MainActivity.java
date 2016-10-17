package ucai.cn.fulicenter.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
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

    public void singleChecked(RadioButton radio) {
        if (radio != rbtnNewGoods) {
            rbtnNewGoods.setChecked(false);
        }
        if (radio != rbtnBoutique) {
            rbtnBoutique.setChecked(false);
        }
        if (radio != rbtnCategory) {
            rbtnCategory.setChecked(false);
        }
        if (radio != rbtnCart) {
            rbtnCart.setChecked(false);
        }
        if (radio != rbtnProsen) {
            rbtnProsen.setChecked(false);
        }

    }


}
