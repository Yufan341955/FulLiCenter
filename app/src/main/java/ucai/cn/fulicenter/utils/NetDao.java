package ucai.cn.fulicenter.utils;

import android.content.Context;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {
    public static void downlodaNewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
          OkHttpUtils utils=new OkHttpUtils(context);
          utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                  .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(I.CAT_ID))
                  .addParam(I.PAGE_ID,String.valueOf(pageId))
                  .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                  .targetClass(NewGoodsBean[].class)
                  .execute(listener);
    }
    public static void downlodaGoods(Context context,int boutiqueId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener){
          OkHttpUtils utils=new OkHttpUtils(context);
          utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                  .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(boutiqueId))
                  .addParam(I.PAGE_ID,String.valueOf(pageId))
                  .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                  .targetClass(NewGoodsBean[].class)
                  .execute(listener);
    }
    public static void downlodaGoodsDetails(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodsId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);

    }
    public static void downloadBoutidue(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
    public static void downloadCategoryGroup(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener){
           OkHttpUtils utils = new OkHttpUtils(context);
          utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                  .targetClass(CategoryGroupBean[].class)
                  .execute(listener);
    }
    public static void downloadCategoryChild(Context context,int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener){
        OkHttpUtils utils=new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,parentId+"")
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

}
