package ucai.cn.fulicenter.activity;

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
import ucai.cn.fulicenter.adapter.CollectAdapter;
import ucai.cn.fulicenter.bean.CollectBean;
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.MFGT;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

public class CollectActivity extends AppCompatActivity {

    @Bind(R.id.backAuto)
    LinearLayout backAuto;
    @Bind(R.id.tvRefesh)
    TextView tvRefesh;
    @Bind(R.id.rvNewGoods)
    RecyclerView rvNewGoods;
    @Bind(R.id.Srl)
    SwipeRefreshLayout Srl;
    ArrayList<CollectBean> mList;
    CollectAdapter mAdapter;
    GridLayoutManager mManger;
    int mPageId=1;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        username=getIntent().getStringExtra("userName");
        initView();
        initData();
        setListener();

    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
        mManger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
                int lastPosition = mManger.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastPosition >= mAdapter.getItemCount() - 1) {
                    mPageId++;
                    downloadCollect(mPageId, I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstposition = mManger.findFirstVisibleItemPosition();
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
                mPageId = 1;
                downloadCollect(mPageId, I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initView() {
        Srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));
        mList=new ArrayList<CollectBean>();
        mAdapter=new CollectAdapter(this,mList);
        mManger=new GridLayoutManager(this,2);
        rvNewGoods.setAdapter(mAdapter);
        rvNewGoods.setHasFixedSize(true);
        rvNewGoods.setLayoutManager(mManger);
    }

    private void initData() {
        downloadCollect(mPageId, I.ACTION_DOWNLOAD);
    }

    private void downloadCollect(int pageId, final int action) {
        NetDao.findCollects(this, username, pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                mAdapter.setMore(false);
                if(result==null||result.length==0){
                    return;
                }
                ArrayList<CollectBean> list= ConvertUtils.array2List(result);
                mAdapter.setMore(true);
                switch (action){
                    case I.ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                    case I.ACTION_PULL_DOWN:
                        mAdapter.initData(list);
                        tvRefesh.setVisibility(View.GONE);
                        Srl.setRefreshing(false);
                        break;
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick(R.id.backAuto)
    public void onClick() {
        MFGT.finish(this);
    }
}
