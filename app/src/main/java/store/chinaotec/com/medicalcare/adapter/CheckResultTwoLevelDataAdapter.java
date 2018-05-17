package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.TextResultBean;

/**
 * Created by seven on 2017/12/1 0001.
 */
public class CheckResultTwoLevelDataAdapter extends RecyclerView.Adapter<CheckResultTwoLevelDataAdapter.ViewHolder> {

    private final Context context;
    private final TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList data;

    public CheckResultTwoLevelDataAdapter(Context context, TextResultBean.DataBean.MedicalTypeListBean.DataChildTypeList dataChildTypeList) {
        this.context = context;
        this.data = dataChildTypeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_check_result_two,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textResultName.setText(data.getChildTypeList().get(position).getTypeName());
        holder.rv_result_check.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.rv_result_check.setAdapter(new CheckResultThreeLevelDataAdapter(context,data.getChildTypeList().get(position).getDataList()
                .get(0).getMemberSickDealList()));
        holder.iv_check.setBackgroundResource(R.mipmap.arrow_down);
        holder.rl_three_level.setOnClickListener(new View.OnClickListener() {
            boolean isOpened = false;//改变展开状态
            @Override
            public void onClick(View view) {
                if (!isOpened){
                    holder.rv_result_check.setVisibility(View.VISIBLE);
                    holder.iv_check.setBackgroundResource(R.mipmap.arrow_up);
                    isOpened = true;
                }else {
                    holder.rv_result_check.setVisibility(View.GONE);
                    holder.iv_check.setBackgroundResource(R.mipmap.arrow_down);
                    isOpened = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getChildTypeList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_check_two_level_name)
        TextView textResultName;
        @Bind(R.id.iv_check_two_level_up)
        ImageView iv_check;
        @Bind(R.id.rl_three_level)
        RelativeLayout rl_three_level;
        @Bind(R.id.rv_result_check)
        RecyclerView rv_result_check;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
