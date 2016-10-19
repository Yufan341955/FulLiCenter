package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.GoodsDetailsActivity;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/19.
 */
public class GoodsDetilsChildAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore=true;
    public GoodsDetilsChildAdapter(Context context, ArrayList<NewGoodsBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if(viewType==I.TYPE_FOOTER){
            holder=new GoodsAdapter.FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer,parent,false));
        }else{
            holder=new GoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods,parent,false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(position==getItemCount()-1){
                ((GoodsAdapter.FooterViewHolder) holder).tvhint.setText(getFooter());
                return;
            }
        NewGoodsBean newgood=mList.get(position);
        GoodsViewHolder viewHolder= (GoodsViewHolder) holder;

        viewHolder.tvNewGoods.setText(newgood.getGoodsName());
        viewHolder.tvPerice.setText(newgood.getCurrencyPrice());
        viewHolder.itemView.setTag(newgood.getGoodsId());
        ImageLoader.downloadImg(mContext,viewHolder.ivGoods,newgood.getGoodsThumb());
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public String getFooter() {
        return isMore?"加载更多":"没有更多可加载";
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoods;
        TextView tvNewGoods, tvPerice;

        public GoodsViewHolder(final View itemView) {
            super(itemView);
            ivGoods = (ImageView) itemView.findViewById(R.id.iv_new_goods);
            tvNewGoods = (TextView) itemView.findViewById(R.id.tv_new_goods);
            tvPerice = (TextView) itemView.findViewById(R.id.tv_perice);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int goodsId = (int) itemView.getTag();
                    Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
                    intent.putExtra("goodsId", goodsId);
                    MFGT.startActivity(mContext, intent);
                }
            });
        }
    }
}
