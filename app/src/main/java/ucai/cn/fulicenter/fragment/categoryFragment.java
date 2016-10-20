package ucai.cn.fulicenter.fragment;

import android.content.Context;
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
import ucai.cn.fulicenter.utils.ConvertUtils;
import ucai.cn.fulicenter.utils.L;
import ucai.cn.fulicenter.utils.NetDao;
import ucai.cn.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryFragment extends BaseFragment {


    @Bind(R.id.elv)
    ExpandableListView elv;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    CategoryAdapter mAdapter;
    Context mContext;
    int GroupCount;
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
       downloadGroup();

    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
              if(result!=null&&result.length>0){
                 ArrayList<CategoryGroupBean> groupList= ConvertUtils.array2List(result);
                  mGroupList.addAll(groupList);
                  L.e("groupList"+groupList.size());
               for(CategoryGroupBean g:groupList){
                   downloadChild(g.getId());
               }

              }
            }

            @Override
            public void onError(String error) {
                L.e("error",error);
            }
        });
    }

    private void downloadChild(int id) {
        NetDao.downloadCategoryChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                GroupCount++;
                if(result!=null&&result.length>0){

                    ArrayList<CategoryChildBean> childList=ConvertUtils.array2List(result);
                    mChildList.add(childList);
                    L.e("childList="+childList.size());
                }
                if(GroupCount==mGroupList.size()){
                    mAdapter.initData(mGroupList,mChildList);
                }
            }

            @Override
            public void onError(String error) {
              L.e("error",error);
            }
        });
    }

    @Override
    protected void initView() {
        elv.setGroupIndicator(null);
        mGroupList=new ArrayList<CategoryGroupBean>();
        mChildList=new ArrayList<ArrayList<CategoryChildBean>>();
        mContext=getContext();
        mAdapter=new CategoryAdapter(mContext,mGroupList,mChildList);
        elv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
