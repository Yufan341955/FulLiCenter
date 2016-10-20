package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.GoodsAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;
import ucai.cn.fulicenter.views.CatChildFilterButton;

public class CategoryChildActivity extends AppCompatActivity {


    @Bind(R.id.btn_Price)
    Button btnPrice;
    @Bind(R.id.btn_Time)
    Button btnTime;
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.Srl)
    SwipeRefreshLayout Srl;
    @Bind(R.id.btnCatChildFilter)
    CatChildFilterButton btnCatChildFilter;
    int GoodsId;
    int PageId = 1;
    ArrayList<NewGoodsBean> mList;
    CategoryChildActivity mContext;
    GoodsAdapter mAdapter;
    GridLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        GoodsId = intent.getIntExtra("id", 0);
        btnCatChildFilter.setText(intent.getStringExtra("name"));
        mContext = this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mList.size()) {
                    return 2;
                }
                return 1;
            }
        });
    }

    private void setPullUpListener() {
        rvNewGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = mManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastPosition >= mAdapter.getItemCount() - 1) {
                    PageId++;
                    downloadGoodsDetails(I.ACTION_PULL_UP, GoodsId);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstposition = mManager.findFirstVisibleItemPosition();
                Srl.setEnabled(firstposition == 0);
            }
        });
    }

    private void setPullDownListener() {
        Srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Srl.setEnabled(true);
                Srl.setRefreshing(true);
                tvRefesh.setVisibility(View.VISIBLE);
                PageId = 1;
                downloadGoodsDetails(I.ACTION_PULL_DOWN, GoodsId);
            }
        });
    }

    private void initData() {
        downloadGoodsDetails(I.ACTION_DOWNLOAD, GoodsId);
    }

    private void downloadGoodsDetails(final int action, int goodsId) {
        NetDao.downlodaGoods(this, goodsId, PageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mAdapter.setMore(false);
                if (result == null && result.length == 0) {
                    return;
                }
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                if (list.size() == I.PAGE_SIZE_DEFAULT) {
                    mAdapter.setMore(true);
                }

                switch (action) {
                    case I.ACTION_DOWNLOAD:
                        mAdapter.initList(list);
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addList(list);
                        break;
                    case I.ACTION_PULL_DOWN:
                        mAdapter.initList(list);
                        Srl.setRefreshing(false);
                        tvRefesh.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        Srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));
        mManager = new GridLayoutManager(this, 2);
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(this, mList);
        rvNewGoods.setHasFixedSize(true);
        rvNewGoods.setLayoutManager(mManager);
        rvNewGoods.setAdapter(mAdapter);

    }


    @OnClick(R.id.backAuto)
    public void onClick() {
        MFGT.finish(this);
    }
}
        
