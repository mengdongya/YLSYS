package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by seven on 2017/12/4 0004.
 */
public class CheckExpandableListViewAdapter implements ExpandableListAdapter {
    private Context context;
    private List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList> groupData;
    private List<List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList.ChildTypeThree.MemberSickDealListBean>> childData;

    public CheckExpandableListViewAdapter(Context context, List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList>
            groupData, List<List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.
            DataChildTypeThreeList.ChildTypeThree.MemberSickDealListBean>> childData) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    //获取分组个数
    @Override
    public int getGroupCount() {
        int ret = 0;
        if (groupData != null) {
            ret = groupData.size();
        }
        return ret;
    }

    //获取groupPosition分组，子列表数量
    @Override
    public int getChildrenCount(int groupPosition) {
        int ret = 0;
        if (childData != null) {
            ret = childData.get(groupPosition).size();
        }
        return ret;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_check_result_two, null);
            holder = new GroupViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.iv_check_two_level_up);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_check_two_level_name);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList groupData = this.groupData.get(groupPosition);
        //是否展开
        if (isExpanded) {
            holder.img.setImageResource(R.mipmap.arrow_down);
        } else {
            holder.img.setImageResource(R.mipmap.arrow_down);
        }
        holder.tv_name.setText(groupData.getTypeName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_check_result_three, null);
            holder = new ChildViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_check_three_level_name);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList.ChildTypeThree.MemberSickDealListBean childData =
                this.childData.get(groupPosition).get(childPosition);
        holder.tv_name.setText(childData.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class GroupViewHolder {
        ImageView img;
        TextView tv_name;
    }

    class ChildViewHolder {
        TextView tv_name;
    }

}
