package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsAdapter extends RecyclerView.Adapter{
    Context mContext;
    ArrayList<NewGoodsBean> mGooodsList;
    RecyclerView parent;
    String Footer;
    public GoodsAdapter(Context mContext, ArrayList<NewGoodsBean> mGooodsList) {
        this.mContext = mContext;
        this.mGooodsList = mGooodsList;
    }
    public void setFooter(String footer){
        this.Footer=footer;
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
           if(getItemViewType(position)==I.TYPE_FOOTER){
               ((FooterViewHolder)holder).tvhint.setText(Footer);
           }else{
               NewGoodsBean newgood=mGooodsList.get(position);
               GoodsViewHolder viewHolder= (GoodsViewHolder) holder;
               viewHolder.tvNewGoods.setText(newgood.getGoodsName());
               viewHolder.tvPerice.setText(newgood.getCurrencyPrice());
   //            ImageLoader.downloadImg(mContext,viewHolder.ivGoods,newgood.getGoodsThumb());
               ImageLoader.build(I.SERVER_ROOT+I.REQUEST_DOWNLOAD_IMAGE)
                       .addParam(I.IMAGE_URL,newgood.getGoodsThumb())
                       .defaultPicture(R.mipmap.goods_thumb)
                       .width(150)
                       .height(280)
                       .imageView(viewHolder.ivGoods)
                       .setDragging(true)
                       .listener(parent)
                       .showImage(mContext);

           }
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
    class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView tvhint;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tvhint= (TextView) itemView.findViewById(R.id.tvhint);
        }
    }
    class GoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoods;
        TextView tvNewGoods,tvPerice;
        public GoodsViewHolder(View itemView) {
            super(itemView);
            ivGoods= (ImageView) itemView.findViewById(R.id.iv_new_goods);
            tvNewGoods= (TextView) itemView.findViewById(R.id.tv_new_goods);
            tvPerice= (TextView) itemView.findViewById(R.id.tv_perice);
        }
    }

}
