package ucai.cn.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicenter.I;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.GoodsDetilsChildAdapter;
import ucai.cn.fulicenter.bean.NewGoodsBean;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class GoodsDetailsChildActivity extends AppCompatActivity {


    @Bind(R.id.backAuto)
    LinearLayout backAuto;
    @Bind(R.id.title_Name)
    TextView titleName;
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.Srl)
    SwipeRefreshLayout Srl;
    int PageId = 1;
    int Id;
    ArrayList<NewGoodsBean> mList;
    GridLayoutManager mManager;
    GoodsDetilsChildAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details_child);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Id = intent.getIntExtra("id", 0);
        titleName.setText(name);
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
                    downloadDetailsChild(I.ACTION_PULL_UP, Id);
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
                downloadDetailsChild(I.ACTION_PULL_DOWN, Id);
            }
        });
    }


    private void initData() {
        downloadDetailsChild(I.ACTION_DOWNLOAD, Id);
    }

    private void downloadDetailsChild(final int action, int Id) {
        NetDao.downlodaGoods(this, Id, PageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mAdapter.setMore(false);
                if (result == null && result.length == 0) {
                    return;
                }
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                switch (action) {
                    case I.ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                    case I.ACTION_PULL_DOWN:
                        mAdapter.initData(list);
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
        mAdapter = new GoodsDetilsChildAdapter(this, mList);
        rvNewGoods.setHasFixedSize(true);
        rvNewGoods.setLayoutManager(mManager);
        rvNewGoods.setAdapter(mAdapter);
    }

    @OnClick(R.id.backAuto)
    public void onClick() {
        MFGT.finish(this);
    }
}
