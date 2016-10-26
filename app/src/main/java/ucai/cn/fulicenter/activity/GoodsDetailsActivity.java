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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.AlbumsBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.CommonUtils;
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

    }

    private void isCollect(int goodsId,String username) {
        NetDao.isCollect(this, goodsId, username, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null){
                    isCollect=result.isSuccess();
                    setCollectImage();
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
            if(user!=null){
                if(isCollect){
                    delteCollect();
                }else {
                    addCollect(goodsId, user.getMuserName());
                }
            }else {
                CommonUtils.showLongToast("请先登录");
            }
                break;
            case R.id.share:
                showShare();
                break;
        }
    }

    private void delteCollect() {
        NetDao.deleteCollect(this, goodsId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null){
                    isCollect=!result.isSuccess();
                    setCollectImage();
                    //CommonUtils.showLongToast(result.getMsg());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void addCollect(int goodsId, String muserName) {

        NetDao.addCollect(this, goodsId, muserName, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null){
                    isCollect=result.isSuccess();
                    setCollectImage();

                    CommonUtils.showLongToast(result.getMsg());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setCollectImage() {
        if(isCollect){
            collectIn.setImageResource(R.mipmap.bg_collect_out);
        }else{
            collectIn.setImageResource(R.mipmap.bg_collect_in);
        }
    }
    public void showShare(){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


}
