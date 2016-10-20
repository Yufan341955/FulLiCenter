package ucai.cn.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucai.cn.fulicenter.R;
import ucai.cn.fulicenter.bean.CategoryChildBean;
import ucai.cn.fulicenter.bean.CategoryGroupBean;
import ucai.cn.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/20.
 */
public class categoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;




    public categoryAdapter(Context mContext, ArrayList<CategoryGroupBean> mGroupList,
                           ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        this.mContext = mContext;
        this.mGroupList = new ArrayList<CategoryGroupBean>();
        this.mGroupList.addAll(mGroupList);
        this.mChildList = new ArrayList<ArrayList<CategoryChildBean>>();
        this.mChildList.addAll(mChildList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList == null ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupList != null && mChildList.get(groupPosition) != null ? mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_categorygroup, null);
            holder=new GroupViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (GroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean group= (CategoryGroupBean) getGroup(groupPosition);
        if(group!=null){
            ImageLoader.downloadImg(mContext,holder.ivCategoryGroup,group.getImageUrl());
            holder.tvCategory.setText(group.getName());
            holder.ivGroup.setImageResource(isExpanded?R.mipmap.expand_off:R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_categorychild, null);
            holder=new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ChildViewHolder) convertView.getTag();
        }
        CategoryChildBean child= (CategoryChildBean) getChild(groupPosition,childPosition);
        if(child!=null){
            ImageLoader.downloadImg(mContext,holder.ivChild,child.getImageUrl());
            holder.tvCategoryGoodsName.setText(child.getName());
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ChildViewHolder {
        @Bind(R.id.ivChild)
        ImageView ivChild;
        @Bind(R.id.tv_Category_Goods_Name)
        TextView tvCategoryGoodsName;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    class GroupViewHolder {
        @Bind(R.id.ivCategoryGroup)
        ImageView ivCategoryGroup;
        @Bind(R.id.tvCategory)
        TextView tvCategory;
        @Bind(R.id.ivGroup)
        ImageView ivGroup;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
