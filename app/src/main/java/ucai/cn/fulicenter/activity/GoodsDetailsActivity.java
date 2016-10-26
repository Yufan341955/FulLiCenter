package ucai.cn.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.AlbumsBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;
import ucai.cn.fulicenter.views.FlowIndicator;
import ucai.cn.fulicenter.views.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {


    @Bind(R.id.backAuto)
    LinearLayout backAuto;
    @Bind(R.id.tvEnglishName)
    TextView tvEnglishName;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvPericeCurrent)
    TextView tvPericeCurrent;
    @Bind(R.id.tvPericeShop)
    TextView tvPericeShop;
    @Bind(R.id.Sav)
    SlideAutoLoopView Sav;
    @Bind(R.id.flow)
    FlowIndicator flow;
    @Bind(R.id.wv_goods_brief)
    WebView wvGoodsBrief;
    int goodsId;
    @Bind(R.id.collect_in)
    ImageView collectIn;
    @Bind(R.id.share)
    ImageView share;

    UserAvatar user;
    boolean isCollect=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra("goodsId", 0);
        L.e("details", goodsId + "");
        if (goodsId == 0) {
            finish();
        }
        user= FuLiCenterApplication.getUser();
        initView();
        initData();
        setListener();
    }

    private void setListener() {

    }

    private void initData() {
        downloadGoodsDetails();
        if(user!=null) {
            isCollect(goodsId,user.getMuserName());
        }
       if(isCollect){
           collectIn.setImageResource(R.mipmap.bg_collect_out);
       }else{
           collectIn.setImageResource(R.mipmap.bg_collect_in);
       }
    }

    private void isCollect(int goodsId,String username) {
        NetDao.isCollect(this, goodsId, username, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null){
                    isCollect=result.isSuccess();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadGoodsDetails() {
        NetDao.downlodaGoodsDetails(this, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details.result=", result.toString());
                if (result == null) {
                    finish();
                } else {
                    showGoodsDetails(result);
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details.error=", error);
                Toast.makeText(GoodsDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showGoodsDetails(GoodsDetailsBean details) {
        tvEnglishName.setText(details.getGoodsEnglishName());
        tvName.setText(details.getGoodsName());
        tvPericeCurrent.setText(details.getCurrencyPrice());
        tvPericeShop.setText(details.getShopPrice());
        Sav.startPlayLoop(flow, getAlbumsImgUrl(details), getAlbumsImgCount(details));
        wvGoodsBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumsImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumsImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[0].getImgUrl();
            }

        }
        return urls;
    }

    private void initView() {

    }

    @OnClick(R.id.backAuto)
    public void onClick() {
        MFGT.finish(this);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }

    @OnClick({R.id.collect_in, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_in:

                break;
            case R.id.share:
                break;
        }
    }
}
