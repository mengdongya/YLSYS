package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.TextResultBaseBean;

/**
 * Created by Administrator on 2017/5/3 0003.
 * 检查结果类别内容适配器
 */

public class MyTestResulAllClassAdapter extends RecyclerView.Adapter<MyTestResulAllClassAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext = null;
    private TestResultContentLisener mTestResultContentLisener;
    private List<TextResultBaseBean> diseList;

    public MyTestResulAllClassAdapter(Context context, List<TextResultBaseBean> diseList) {
        this.mContext = context;
        this.diseList = diseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_result_check_detail, parent, false);
        inflate.setOnClickListener(this);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textResultName.setText(diseList.get(position).getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return diseList.size();
    }

    public void setOnTextClickLisener(TestResultContentLisener testResultContentLisener) {
        this.mTestResultContentLisener = testResultContentLisener;
    }

    @Override
    public void onClick(View v) {
        if (mTestResultContentLisener != null) {
            mTestResultContentLisener.itemClick((int) v.getTag());
        }
    }

    public interface TestResultContentLisener {
        void itemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_result_name)
        TextView textResultName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
