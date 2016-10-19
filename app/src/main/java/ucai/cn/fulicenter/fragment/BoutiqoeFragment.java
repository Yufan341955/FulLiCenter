package ucai.cn.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.activity.MainActivity;
import ucai.cn.fulicenter.adapter.BoutiqueAdapter;
import ucai.cn.fulicenter.bean.BoutiqueBean;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.ImageLoader;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;


/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqoeFragment extends Fragment {
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    ArrayList<BoutiqueBean> mList;
    LinearLayoutManager mManager;
    BoutiqueAdapter mAdapter;
    MainActivity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        mList=new ArrayList<>();
        mContext= (MainActivity) getContext();
        initView();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                srl.setEnabled(true);
                tvRefesh.setVisibility(View.VISIBLE);
                downloadBoutique(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData() {
        downloadBoutique(I.ACTION_DOWNLOAD);
    }

    private void downloadBoutique(final int action) {
        NetDao.downloadBoutidue(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                srl.setRefreshing(false);
                tvRefesh.setVisibility(View.GONE);
                if(result!=null&&result.length>0){
                    ArrayList<BoutiqueBean> mlist= ConvertUtils.array2List(result);
                    L.e("BoutiqueBean:",mlist.toString());
                    switch (action){
                        case I.ACTION_DOWNLOAD:
                           mAdapter.initData(mlist);
                        break;
                        case I.ACTION_PULL_DOWN:
                            mAdapter.initData(mlist);
                            srl.setRefreshing(false);
                            tvRefesh.setVisibility(View.GONE);
                            ImageLoader.release();
                        break;
                    }

                }else{
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)

        );
        mManager = new LinearLayoutManager(mContext);
        rvNewGoods.setHasFixedSize(true);
        rvNewGoods.setLayoutManager(mManager);
        mAdapter=new BoutiqueAdapter(mContext,mList);
        rvNewGoods.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
