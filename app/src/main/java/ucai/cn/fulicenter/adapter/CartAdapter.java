package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.GoodsDetailsBean;
import ucai.cn.fulicenter.bean.MessageBean;
import ucai.cn.fulicenter.utils.CommonUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

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
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
        return holder;
    }
 public void remove(int index){
     mList.remove(index);
     notifyDataSetChanged();
 }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CartViewHolder viewHolder= (CartViewHolder) holder;
        final CartBean cart=mList.get(position);
        GoodsDetailsBean goods=cart.getGoods();
        if(goods!=null){
            Gson gson=new Gson();
            String json=cart.getGoods().toString();
            L.e("goods="+json);
            viewHolder.tvGoodsName.setText(goods.getGoodsName());
            viewHolder.tvOnePerice.setText(goods.getCurrencyPrice());
            ImageLoader.downloadImg(context,viewHolder.ivGoodsIm,goods.getGoodsThumb());
        }
        viewHolder.tvCount.setText("("+cart.getCount()+")");
        viewHolder.rbtnChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cart.setChecked(isChecked);
                context.sendBroadcast(new Intent(I.BROADCAST_UPDATECART));
            }
        });
        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=cart.getCount()+1;
                addCart(cart.getId(), count, false);
                viewHolder.tvCount.setText("("+count+")");
            }
        });
        viewHolder.ivDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.getCount()==1){
                    deleteCart(cart.getId(),position);

                }else {
                    int count=cart.getCount()-1;
                    addCart(cart.getId(), count, false);
                    viewHolder.tvCount.setText("("+count+")");
                }
            }
        });

    }

    private void deleteCart(int id, final int index) {
           NetDao.deleteCart(context, id, new OkHttpUtils.OnCompleteListener<MessageBean>() {
               @Override
               public void onSuccess(MessageBean result) {
                   if(result!=null){
                       if(result.isSuccess()){
                           CommonUtils.showLongToast(result.getMsg());
                           remove(index);
                       }
                   }
               }

               @Override
               public void onError(String error) {

               }
           });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void init(ArrayList<CartBean> list) {
        mList=list;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rbtn_checked)
        CheckBox rbtnChecked;
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
        @Bind(R.id.iv_de)
        ImageView ivDelet;

        public CartViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void addCart(int CartId, int count, boolean ischecked) {
        NetDao.updateCart(context, CartId, count, ischecked, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if(result!=null){
                    if(result.isSuccess()){

                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
