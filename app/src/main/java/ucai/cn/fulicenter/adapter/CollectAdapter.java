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
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    ArrayList<CollectBean> mList;

    boolean isMore=true;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CollectAdapter(Context context, ArrayList<CollectBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if(viewType==I.TYPE_FOOTER){
            L.e("TYPE=TYPE_FOOTER");
            holder=new FooterViewHolder(View.inflate(mContext,R.layout.item_footer,null));
        }else {
            L.e("TYPE=TYPE_ITEM");
            holder=new CollectViewHolder(View.inflate(mContext,R.layout.item_collect,null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            ((FooterViewHolder)holder).tvFooter.setText(getFooter());
            L.e("position="+position);
            return;
        }else {
            CollectViewHolder viewHolder= (CollectViewHolder) holder;
            final CollectBean collect=mList.get(position);
            L.e("position="+position);
            L.e("collect="+collect.toString());
            viewHolder.tvCategoryName.setText(collect.getGoodsName());
            final String username=collect.getUserName();
            ImageLoader.downloadImg(mContext,viewHolder.ivCollect,collect.getGoodsImg());
            viewHolder.ivCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,GoodsDetailsActivity.class);
                    intent.putExtra("goodsId",collect.getGoodsId());
                    MFGT.startActivity(mContext,intent);
                }
            });
            final int index=position;
            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  delteCollect(index,username,collect.getGoodsId());
                }
            });

        }

    }

    private void delteCollect(final int index, String username, int goodsId) {
        NetDao.deleteCollect(mContext, goodsId, username, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result.isSuccess()){
                    removeCollect(index);
                    CommonUtils.showLongToast(result.getMsg());
                }else {
                    CommonUtils.showLongToast(result.getMsg());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void removeCollect(int index){
        this.mList.remove(index);
        notifyDataSetChanged();
    }
    private String getFooter() {
        return isMore?"加载更多数据":"没有更多可加载";
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

    public void initData(ArrayList<CollectBean> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    class CollectViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCollect,ivDelete;
        TextView tvCategoryName;
        public CollectViewHolder(View itemView) {
            super(itemView);
            ivCollect= (ImageView) itemView.findViewById(R.id.iv_collect_goods);
            ivDelete= (ImageView) itemView.findViewById(R.id.iv_delete);
            tvCategoryName= (TextView) itemView.findViewById(R.id.tv_goods_name);

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
