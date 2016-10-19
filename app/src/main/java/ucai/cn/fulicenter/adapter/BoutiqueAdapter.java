package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.BoutiqueBean;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodsAdapter.FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_boutique, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            ((GoodsAdapter.FooterViewHolder)holder).tvhint.setText(getString());
        }else{
            BoutiqueViewHolder viewHolder= (BoutiqueViewHolder) holder;
            BoutiqueBean boutique = mList.get(position);
            viewHolder.tvTitle.setText(boutique.getTitle());
            viewHolder.tvBoutiqueName.setText(boutique.getName());
            viewHolder.tvDescription.setText(boutique.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return I.TYPE_FOOTER;
        }else {
            return I.TYPE_ITEM;
        }
    }

    public String getString() {
        return isMore?"加载更多数据":"没有更多可加载";
    }

    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_Boutique)
        ImageView ivBoutique;
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tv_Boutique_Name)
        TextView tvBoutiqueName;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        public BoutiqueViewHolder(View itemView) {
            super(itemView);
        }
    }
}
