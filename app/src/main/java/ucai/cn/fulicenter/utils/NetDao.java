package ucai.cn.fulicenter.utils;

import android.content.Context;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.bean.Result;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {
    public static void downlodaNewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
          OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<NewGoodsBean[]>(context);
          utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                  .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
                  .addParam(I.PAGE_ID,String.valueOf(pageId))
                  .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                  .targetClass(NewGoodsBean[].class)
                  .execute(listener);
    }
    public static void downlodaGoods(Context context,int boutiqueId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
          OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<NewGoodsBean[]>(context);
          utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                  .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(boutiqueId))
                  .addParam(I.PAGE_ID,String.valueOf(pageId))
                  .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                  .targetClass(NewGoodsBean[].class)
                  .execute(listener);
    }
    public static void downlodaGoodsDetails(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils<GoodsDetailsBean> utils=new OkHttpUtils<GoodsDetailsBean>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodsId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);

    }
    public static void downloadBoutidue(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils<BoutiqueBean[]> utils=new OkHttpUtils<BoutiqueBean[]>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
    public static void downloadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
           OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<CategoryGroupBean[]>(context);
          utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                  .targetClass(CategoryGroupBean[].class)
                  .execute(listener);
    }
    public static void downloadCategoryChild(Context context,int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils<CategoryChildBean[]> utils=new OkHttpUtils<CategoryChildBean[]>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,parentId+"")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
    public static void register(Context context, String username, String nick, String password, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils=new OkHttpUtils<Result>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .post()
                .targetClass(Result.class)
                .execute(listener);

    }
    public static void login(Context context, String username, String password, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils=new OkHttpUtils<Result>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .execute(listener);

    }

}
