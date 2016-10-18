package ucai.cn.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    ArrayList<NewGoodsBean> mNewGoodsList;
    GridLayoutManager mManager;
    GoodsAdapter mAdapter;
    MainActivity mContext;
    int pageId = 1;
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        NetDao.downlodaNewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
             srl.setRefreshing(false);
                tvRefesh.setVisibility(View.GONE);
                if (result == null && result.length == 0) {

                    return;
                }
                ArrayList<NewGoodsBean> mlist = ConvertUtils.array2List(result);
                mAdapter.setMore(true);
                mAdapter.initList(mlist);
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefesh.setVisibility(View.GONE);
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                L.e("error :" + error);
            }
        });
    }

    private void initView() {
        mContext = (MainActivity) getContext();
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)

        );
        mManager = new GridLayoutManager(getContext(), I.COLUM_NUM, GridLayoutManager.VERTICAL, false);
        mNewGoodsList = new ArrayList<NewGoodsBean>();
        mAdapter = new GoodsAdapter(mContext, mNewGoodsList);
        rvNewGoods.setHasFixedSize(true);
        rvNewGoods.setLayoutManager(mManager);
        rvNewGoods.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
