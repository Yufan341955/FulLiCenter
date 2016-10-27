package ucai.cn.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        mList=new ArrayList<CartBean>();
        mContext= (MainActivity) getContext();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                srl.setEnabled(true);
                tvRefresh.setVisibility(View.VISIBLE);
                findCarts();
            }
        });
    }

    @Override
    protected void initData() {
       user = FuLiCenterApplication.getUser();
        if(user!=null) {
            findCarts();
        }
    }

    private void findCarts() {

        NetDao.findCarts(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                srl.setRefreshing(false);
                if (result != null && result.length > 0) {
                    ArrayList<CartBean> list= ConvertUtils.array2List(result);
                    mAdapter.init(list);
                }
            }
            @Override
            public void onError(String error) {

            }
        });
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
        mAdapter=new CartAdapter(mContext,mList);
        rvCart.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
