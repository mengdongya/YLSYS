package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by seven on 2017/12/1 0001.
 */
public class CheckResultContentAdapter extends RecyclerView.Adapter<CheckResultContentAdapter.ViewHolder> {

    private final Context context;
    private final TextResultBean.DataBean.MedicalTypeListBean medicalTypeListBean;


    public CheckResultContentAdapter(Context context, TextResultBean.DataBean.MedicalTypeListBean medicalTypeList) {
        this.context = context;
        this.medicalTypeListBean = medicalTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_check_result, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rvTwoLevelData.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.rvTwoLevelData.setAdapter(new CheckResultTwoLevelDataListAdapter(context,medicalTypeListBean.getChildTypeList().get(position).getDataList()));
        if (medicalTypeListBean.getChildTypeList().size() > 0){
            holder.tvTwoLevelName.setVisibility(View.VISIBLE);
            holder.tvTwoLevelName.setText(medicalTypeListBean.getChildTypeList().get(position).getTypeName());
            holder.rvThreeLevel.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            holder.rvThreeLevel.setAdapter(new CheckResultTwoLevelDataAdapter(context,medicalTypeListBean.getChildTypeList().get(position)));
//            List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList> childTypeList =
//                    medicalTypeListBean.getChildTypeList().get(position).getChildTypeList();
//            List<List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList.ChildTypeThree.MemberSickDealListBean>> bean = new ArrayList<>();
//            for (int i = 0;i< childTypeList.size();i++){
//                List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList.ChildTypeThree.MemberSickDealListBean> list = new ArrayList<>();
//                List<TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList.DataChildTypeThreeList.ChildTypeThree.MemberSickDealListBean> memberSickDealList =
//                        medicalTypeListBean.getChildTypeList().get(position).getChildTypeList().get(i).getDataList().get(0).getMemberSickDealList();
//                for (int j = 0;j<memberSickDealList.size();j++){
//                    list.add(memberSickDealList.get(j));
//                }
//                bean.add(list);
//            }
//            holder.expandableListView.setAdapter(new CheckExpandableListViewAdapter(context,childTypeList,bean));

        }else {
            holder.tvTwoLevelName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return medicalTypeListBean.getChildTypeList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_two_level_name)
        TextView tvTwoLevelName;
        @Bind(R.id.rv_two_level_data)
        RecyclerView rvTwoLevelData;
        @Bind(R.id.rv_three_level)
        RecyclerView rvThreeLevel;
//        @Bind(R.id.expandableListView)
//        ExpandableListView expandableListView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
