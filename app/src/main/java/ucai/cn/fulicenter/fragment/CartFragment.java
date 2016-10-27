package ucai.cn.fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.FuLiCenterApplication;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.CartAdapter;
import ucai.cn.fulicenter.bean.CartBean;
import ucai.cn.fulicenter.bean.UserAvatar;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartFragment extends BaseFragment {
    @Bind(R.id.tv_AllPrice)
    TextView tvAllPrice;
    @Bind(R.id.btn_Buy)
    Button btnBuy;
    @Bind(R.id.tvRefesh)
    TextView tvRefresh;
    @Bind(R.id.rvCart)
    RecyclerView rvCart;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    ArrayList<CartBean> mList;
    LinearLayoutManager mManager;
    CartAdapter mAdapter;
    MainActivity mContext;
    UserAvatar user;
    @Bind(R.id.Layout_Buy)
    RelativeLayout LayoutBuy;
    @Bind(R.id.tv_NoCart)
    TextView tvNoCart;
    UpdateCartReceiver mReceiver;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        mList = new ArrayList<CartBean>();
        mContext = (MainActivity) getContext();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullWoenlistener();
        mReceiver=new UpdateCartReceiver();
        IntentFilter filter=new IntentFilter(I.BROADCAST_UPDATECART);
        mContext.registerReceiver(mReceiver,filter);
    }

    private void setPullWoenlistener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                srl.setEnabled(true);
                tvRefresh.setVisibility(View.VISIBLE);
                if(user != null){
                    findCarts();
                }
            }
        });
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            findCarts();
        }
    }

    private void findCarts() {

        NetDao.findCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                if (result != null && result.length > 0) {
                    mList = ConvertUtils.array2List(result);
                    refreshView(true);
                    mAdapter.init(mList);
                } else {
                    refreshView(false);
                }
            }

            @Override
            public void onError(String error) {
                refreshView(false);
            }
        });
    }

    private void refreshView(boolean hasCart) {
         tvNoCart.setVisibility(hasCart?View.GONE:View.VISIBLE);
         LayoutBuy.setVisibility(hasCart?View.VISIBLE:View.GONE);
         rvCart.setVisibility(hasCart?View.VISIBLE:View.GONE);
         sumPrice();
    }

    @Override
    protected void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)

        );
        mManager = new LinearLayoutManager(mContext);
        rvCart.setHasFixedSize(true);
        rvCart.setLayoutManager(mManager);
        mAdapter = new CartAdapter(mContext, mList);
        rvCart.setAdapter(mAdapter);
        refreshView(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private void sumPrice(){
        int AllPrice=0;
        if(mList!=null&&mList.size()>0){
            for(CartBean c:mList){
                if(c.isChecked()){
                    AllPrice+=getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                }
            }
            tvAllPrice.setText("合计:￥"+AllPrice);
            L.e("AllPrice");
        }else {
            tvAllPrice.setText("合计:￥0");
            L.e("0000");
        }
    }
    private int getPrice(String price) {
        String  p=price.substring(price.indexOf("￥")+1);
        L.e("Price="+p);
        return Integer.valueOf(p);
    }
    class UpdateCartReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            L.e("UpdateCartReceiver.onReceive()1");
            sumPrice();
            L.e("UpdateCartReceiver.onReceive()2");

        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            mContext.unregisterReceiver(mReceiver);
        }
    }
}
