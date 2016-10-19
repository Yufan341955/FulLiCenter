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
import ucai.cn.fulicenter.activity.GoodsDetailsChildActivity;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.MFGT;

public class BoutiqueAdapter extends RecyclerView.Adapter{
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if(viewType==I.TYPE_FOOTER){
            holder=new GoodsAdapter.FooterViewHolder(View.inflate(mContext, R.layout.item_footer,null));
        }else{
            holder=new BoutiqueViewHolder(View.inflate(mContext,R.layout.item_boutique,null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER) {
            ((GoodsAdapter.FooterViewHolder) holder).tvhint.setText(getFooter());
            return;
        }
        BoutiqueBean boutique=mList.get(position);
        BoutiqueViewHolder viewHolder= (BoutiqueViewHolder) holder;
        viewHolder.tvTitle.setText(boutique.getTitle());
        viewHolder.tvBoutiqueName.setText(boutique.getName());
        viewHolder.tvDescription.setText(boutique.getDescription());
        viewHolder.itemView.setTag(boutique);
        ImageLoader.downloadImg(mContext,viewHolder.ivBoutique,boutique.getImageurl());

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
        return isMore?"加载更多":"没有更多数据可加载";
    }

    public void initData(ArrayList<BoutiqueBean> mlist) {
        mList.clear();
        mList.addAll(mlist);
        notifyDataSetChanged();
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBoutique;
        TextView tvTitle,tvBoutiqueName,tvDescription;
        public BoutiqueViewHolder(final View itemView) {
            super(itemView);
            ivBoutique= (ImageView) itemView.findViewById(R.id.iv_Boutique);
            tvTitle= (TextView) itemView.findViewById(R.id.tvTitle);
            tvBoutiqueName= (TextView) itemView.findViewById(R.id.tv_Boutique_Name);
            tvDescription= (TextView) itemView.findViewById(R.id.tvDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BoutiqueBean bq= (BoutiqueBean) itemView.getTag();
                    Intent intent=new Intent(mContext, GoodsDetailsChildActivity.class);
                    intent.putExtra("id",bq.getId());
                    intent.putExtra("name",bq.getName());
                    MFGT.startActivity(mContext,intent);
                }
            });
        }
    }
}
