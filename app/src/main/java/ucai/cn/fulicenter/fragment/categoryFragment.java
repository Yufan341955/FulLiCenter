package ucai.cn.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.adapter.CategoryAdapter;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;

/**
 * Created by Administrator on 2016/10/20.
 */
public class categoryFragment extends BaseFragment {


    @Bind(R.id.elv)
    ExpandableListView elv;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        super.onCreateView(inflater,container,savedInstanceState);
        return layout;

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
