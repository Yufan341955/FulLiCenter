package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<CartBean> mList;


    public CartAdapter(Context context, ArrayList<CartBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(View.inflate(context, R.layout.item_cart, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewHolder viewHolder= (CartViewHolder) holder;
        CartBean cart=mList.get(position);
        viewHolder.rbtnChecked.setChecked(false);
        if(cart.getGoods()!=null){
            GoodsDetailsBean goods= (GoodsDetailsBean) cart.getGoods();
            viewHolder.tvGoodsName.setText(goods.getGoodsName());
            viewHolder.tvOnePerice.setText(goods.getCurrencyPrice());
            ImageLoader.downloadImg(context,viewHolder.ivGoodsIm,goods.getGoodsThumb());
        }
        viewHolder.tvCount.setText("("+cart.getCount()+")");


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rbtn_checked)
        RadioButton rbtnChecked;
        @Bind(R.id.iv_GoodsIm)
        ImageView ivGoodsIm;
        @Bind(R.id.tv_GoodsName)
        TextView tvGoodsName;
        @Bind(R.id.iv_add)
        ImageView ivAdd;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_OnePerice)
        TextView tvOnePerice;
        public CartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(context,itemView);
        }
    }
}
