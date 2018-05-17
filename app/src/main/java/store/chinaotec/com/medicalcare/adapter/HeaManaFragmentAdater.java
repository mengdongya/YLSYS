package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.TakeExerciseBean;
import store.chinaotec.com.medicalcare.utill.CyilnderComputeUtil;
import store.chinaotec.com.medicalcare.utill.SizeUtils;
import store.chinaotec.com.medicalcare.view.MyViewHolder;

/**
 * Created by HYY on 2017/12/27.
 */

public class HeaManaFragmentAdater extends BaseAdapter {
    private Context mContext;
    private List<TakeExerciseBean> mTakeExerciseBeen;
    private Integer maxInteger = 0;
    public OnClickListener mSetOnClickListener;
    private Integer type = 0;//0日1周2月

    public interface OnClickListener {
        void setHeaManaOnClickListener(TakeExerciseBean manaOnClickListener, Integer type);
    }

    public HeaManaFragmentAdater(Context context) {
        mContext = context;
    }

    public HeaManaFragmentAdater(Context context, List<TakeExerciseBean> takeExerciseBeen, Integer maxInteger, Integer types) {
        mContext = context;
        mTakeExerciseBeen = takeExerciseBeen;
        this.maxInteger = maxInteger;
        this.type = types;
    }

    @Override
    public int getCount() {
        return mTakeExerciseBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mTakeExerciseBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TakeExerciseBean takeExerciseBean = mTakeExerciseBeen.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_hea_manage_item, null);
        final TextView tv_horizon_view1 = MyViewHolder.get(convertView, R.id.tv_horizon_view1);
        final TextView tv_horizontall_view1 = MyViewHolder.get(convertView, R.id.tv_horizontall_view1);
        if (takeExerciseBean.showData) {
            tv_horizontall_view1.setText(takeExerciseBean.stepNumber + "");
            tv_horizon_view1.setBackgroundColor(Color.parseColor("#cfcfcf"));
        }
        TextView tv_horizontall_view3 = MyViewHolder.get(convertView, R.id.tv_horizontall_view3);
        RelativeLayout.LayoutParams params = null;
        switch (type) {
            case 0:
                params = new RelativeLayout.LayoutParams(SizeUtils.dp2px(30), CyilnderComputeUtil.getComputeNumber(maxInteger, takeExerciseBean.stepNumber));
                break;
            case 1:
                params = new RelativeLayout.LayoutParams(SizeUtils.dp2px(35), CyilnderComputeUtil.getComputeNumber(maxInteger, takeExerciseBean.stepNumber));
                break;
            case 2:
                params = new RelativeLayout.LayoutParams(SizeUtils.dp2px(40), CyilnderComputeUtil.getComputeNumber(maxInteger, takeExerciseBean.stepNumber));
                break;
        }
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, R.id.tv_horizontall_view1);
        tv_horizon_view1.setLayoutParams(params);
        tv_horizontall_view3.setText(takeExerciseBean.takeTime);
        return convertView;
    }
}
