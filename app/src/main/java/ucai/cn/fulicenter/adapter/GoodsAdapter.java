package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.GoodsDetailsActivity;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsAdapter extends RecyclerView.Adapter{
    Context mContext;
    ArrayList<NewGoodsBean> mGooodsList;
    RecyclerView parent;
    boolean isMore;
    int SortBy=I.SORT_BY_ADDTIME_ASC;

    public void setSortBy(int sortBy) {
        SortBy = sortBy;
        sortBy();
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context mContext, ArrayList<NewGoodsBean> mGooodsList) {
        this.mContext = mContext;
        this.mGooodsList = mGooodsList;
    }
    public void setMore(boolean more){
        this.isMore=more;
        notifyDataSetChanged();
    }
    public boolean isMore(){
        return this.isMore;
    }
    public String getFooter(){
        return isMore?"加载更多数据":"没有更多可加载";
    }
    public void initList(ArrayList<NewGoodsBean> mGooodsList){
        this.mGooodsList.clear();
        this.mGooodsList.addAll(mGooodsList);
        notifyDataSetChanged();
    }
    public void addList(ArrayList<NewGoodsBean> mGooodsList){
        this.mGooodsList.addAll(mGooodsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        this.parent= (RecyclerView) parent;
        if(viewType==I.TYPE_FOOTER){
            holder=new FooterViewHolder(View.inflate(mContext, R.layout.item_footer,null));
        }else{
            holder=new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods,null));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(getItemViewType(position)==I.TYPE_FOOTER) {
               ((FooterViewHolder) holder).tvhint.setText(getFooter());
               return;
           }
               NewGoodsBean newgood=mGooodsList.get(position);
               GoodsViewHolder viewHolder= (GoodsViewHolder) holder;

               viewHolder.tvNewGoods.setText(newgood.getGoodsName());
               viewHolder.tvPerice.setText(newgood.getCurrencyPrice());
               viewHolder.itemView.setTag(newgood.getGoodsId());
            //ImageLoader.downloadImg(mContext,viewHolder.ivGoods,newgood.getGoodsThumb());
               ImageLoader.build(I.SERVER_ROOT+I.REQUEST_DOWNLOAD_IMAGE)
                       .addParam(I.IMAGE_URL,newgood.getGoodsThumb())
                       .defaultPicture(R.mipmap.goods_thumb)
                       .width(160)
                       .height(220)
                       .imageView(viewHolder.ivGoods)
                       .setDragging(true)
                       .listener(parent)
                       .showImage(mContext);


    }

    @Override
    public int getItemCount() {
        return mGooodsList==null?0:mGooodsList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }else{
            return I.TYPE_ITEM;
        }
    }
    static class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView tvhint;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tvhint= (TextView) itemView.findViewById(R.id.tvhint);
        }
    }
     class GoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoods;
        TextView tvNewGoods,tvPerice;
        public GoodsViewHolder(final View itemView) {
            super(itemView);
            ivGoods= (ImageView) itemView.findViewById(R.id.iv_new_goods);
            tvNewGoods= (TextView) itemView.findViewById(R.id.tv_new_goods);
            tvPerice= (TextView) itemView.findViewById(R.id.tv_perice);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  int goodsId= (int) itemView.getTag();
                    Intent intent=new Intent(mContext, GoodsDetailsActivity.class);
                    intent.putExtra("goodsId",goodsId);
                    MFGT.startActivity(mContext,intent);
                }
            });
        }
    }
    private void sortBy(){
        Collections.sort(mGooodsList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean left, NewGoodsBean right) {
                int result=0;
                switch (SortBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result= (int) (left.getAddTime()-right.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result= (int) (right.getAddTime()-left.getAddTime());
                    break;
                    case I.SORT_BY_PRICE_ASC:
                        result=getPrice(left.getCurrencyPrice())-getPrice(right.getCurrencyPrice());
                    break;
                    case I.SORT_BY_PRICE_DESC:
                        result= getPrice(right.getCurrencyPrice())-getPrice(left.getCurrencyPrice());
                    break;

                }
                return result;
            }
        });
    }

    private int getPrice(String price) {
       String  p=price.substring(price.indexOf("￥")+1);
        L.e("Price="+p);
        return Integer.valueOf(p);
    }

}
