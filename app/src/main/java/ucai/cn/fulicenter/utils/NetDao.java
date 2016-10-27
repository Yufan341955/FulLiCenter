package ucai.cn.fulicenter.utils;

import android.content.Context;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import java.io.File;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.bean.CollectBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.MessageBean;
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
    public static void updateNick(Context context, String username, String nick, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils=new OkHttpUtils<String>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);

    }
    public static void updateAvatar(Context context, String username, File file, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils=new OkHttpUtils<Result>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,username)
                .addParam(I.AVATAR_TYPE,"user_avatar")
                .addFile2(file)
                .post()
                .targetClass(Result.class)
                .execute(listener);

    }
    public static void findUserByUserName(Context context, String username, OkHttpUtils.OnCompleteListener<Result> listener){
        OkHttpUtils<Result> utils=new OkHttpUtils<Result>(context);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME,username)
                .targetClass(Result.class)
                .execute(listener);
    }
    public static void getCollectCount(Context context, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<MessageBean>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    public static void findCollects(Context context,String username, int pageId, OkHttpUtils.OnCompleteListener<CollectBean[]> listener){
        OkHttpUtils<CollectBean[]> utils=new OkHttpUtils<CollectBean[]>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.USER_NAME,username)
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }
    public static void deleteCollect(Context context, int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<MessageBean>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,goodsId+"")
                .addParam(I.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void isCollect(Context context, int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<MessageBean>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,goodsId+"")
                .addParam(I.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);

    }
    public static void addCollect(Context context,int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<MessageBean>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID,goodsId+"")
                .addParam(I.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void findCarts(Context context, String username, OkHttpUtils.OnCompleteListener<CartBean[]> listener){
        OkHttpUtils<CartBean[]> utils=new OkHttpUtils<CartBean[]>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.USER_NAME,username)
                .targetClass(CartBean[].class)
                .execute(listener);

    }


    public static void updateCart(Context context, int CartId, int count,boolean ischecked, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<MessageBean>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,CartId+"")
                .addParam(I.Cart.COUNT,count+"")
                .addParam(I.Cart.IS_CHECKED,ischecked+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
    public static void deleteCart(Context context, int CartId, OkHttpUtils.OnCompleteListener<MessageBean> listener){
        OkHttpUtils<MessageBean> utils=new OkHttpUtils<MessageBean>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,CartId+"")
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
