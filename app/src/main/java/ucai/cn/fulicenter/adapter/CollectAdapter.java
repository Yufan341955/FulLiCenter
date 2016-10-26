package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.GoodsDetailsActivity;
import ucai.cn.fulicenter.bean.CollectBean;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CollectAdapter extends RecyclerView.Adapter{
    Context mContext;
    ArrayList<CollectBean> mList;

    public CollectAdapter(Context context, ArrayList<CollectBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if(viewType==I.TYPE_FOOTER){
            holder=new FooterViewHolder(View.inflate(mContext,R.layout.item_footer,null));
        }else {
            holder=new CollectViewHolder(View.inflate(mContext,R.layout.item_collect,null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            ((FooterViewHolder)holder).tvFooter.setText(getFooter());
            return;
        }else {

            CollectViewHolder viewHolder= (CollectViewHolder) holder;
            final CollectBean collect=mList.get(position);
            viewHolder.tvCategoryName.setText(collect.getGoodsName());
            ImageLoader.downloadImg(mContext,viewHolder.ivCollect,collect.getGoodsImg());
            viewHolder.ivCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,GoodsDetailsActivity.class);
                    intent.putExtra("goodsId",collect.getGoodsId());
                    MFGT.startActivity(mContext,intent);
                }
            });
//            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }

    }

    private String getFooter() {
        return mList==null?"没有更多可加载":"加载更多数据";
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()){
            return I.TYPE_FOOTER;
        }else {
            return I.TYPE_ITEM;
        }
    }

    class CollectViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCollect,ivDelete;
        TextView tvCategoryName;
        public CollectViewHolder(View itemView) {
            super(itemView);
            ivCollect= (ImageView) itemView.findViewById(R.id.iv_collect_goods);
            ivDelete= (ImageView) itemView.findViewById(R.id.iv_delete);
            tvCategoryName= (TextView) itemView.findViewById(R.id.tv_Category_Goods_Name);

        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView tvFooter;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tvFooter= (TextView) itemView.findViewById(R.id.tvhint);
        }
    }
}
