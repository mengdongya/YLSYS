package store.chinaotec.com.medicalcare.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.HeaManaFragmentAdater;
import store.chinaotec.com.medicalcare.javabean.TakeExerciseBean;
import store.chinaotec.com.medicalcare.view.HorizontalListView;

import static store.chinaotec.com.medicalcare.R.id.tv_horizontall_view1;

/**
 * Created by HYY on 2018/1/5.
 */

public class TakeExerciseActivity extends BaseActivity implements View.OnClickListener, HeaManaFragmentAdater.OnClickListener {
    private HeaManaFragmentAdater mListViewAdapter;
    private List<TakeExerciseBean> mTakeExerciseBeen = new ArrayList<>();
    @Bind(R.id.hlv_fragment_hea_manage2_view)
    HorizontalListView hlvFragmentHeaManage2View;
    @Bind(R.id.tv_hea_manage_text)
    TextView tvHeaManageText;
    @Bind(R.id.tv_hea_manage_qk)
    TextView tvHeaManageQk;
    @Bind(R.id.ll_hea_manage_view1)
    LinearLayout llHeaManageView1;
    @Bind(R.id.tv_manage_day1)
    TextView tvManageDay1;
    @Bind(R.id.tv_manage_text1)
    TextView tvManageText1;
    @Bind(R.id.ll_hea_manage_view2)
    LinearLayout llHeaManageView2;
    @Bind(R.id.tv_manage_day2)
    TextView tvManageDay2;
    @Bind(R.id.tv_manage_text2)
    TextView tvManageText2;
    @Bind(R.id.ll_hea_manage_view3)
    LinearLayout llHeaManageView3;
    @Bind(R.id.tv_manage_day3)
    TextView tvManageDay3;
    @Bind(R.id.tv_manage_text3)
    TextView tvManageText3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_exercise);
        ButterKnife.bind(this);
        loadingData(0);

        llHeaManageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingData(0);
            }
        });
        llHeaManageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingData(1);
            }
        });
        llHeaManageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingData(2);
            }
        });
    }

    private void loadingData(Integer types) {
        type = types;
        setOnClickColor(type);
        Integer index = 9;
        mTakeExerciseBeen.clear();
        for (int i = 0; i < index; i++) {
            TakeExerciseBean m = new TakeExerciseBean();
            m.stepNumber = 100 * i;
            m.takeTime = "12/2" + i;
            mTakeExerciseBeen.add(m);
        }
        List m = new ArrayList();
        for (int i = 0; i < mTakeExerciseBeen.size(); i++) {
            TakeExerciseBean takeExerciseBean = mTakeExerciseBeen.get(i);
            m.add(takeExerciseBean.stepNumber);
        }
        Integer max = 0;
        if (m != null && m.size() > 0) {
            max = (Integer) Collections.max(m);
        }
        mListViewAdapter = new HeaManaFragmentAdater(this, mTakeExerciseBeen, max, type);
        hlvFragmentHeaManage2View.setAdapter(mListViewAdapter);
        hlvFragmentHeaManage2View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TakeExerciseBean takeExerciseBean = mTakeExerciseBeen.get(position);
                TextView tv = (TextView) view.findViewById(tv_horizontall_view1);
                for (int i = 0; i < mTakeExerciseBeen.size(); i++) {
                    mTakeExerciseBeen.get(i).showData = false;
                }
                tv.setText(takeExerciseBean.stepNumber + "");
                setOnClickColor(type);
                takeExerciseBean.showData = true;
                tvHeaManageText.setText(takeExerciseBean.stepNumber + "");
                tvHeaManageQk.setText(takeExerciseBean.stepNumber * 0.8 + "");
                mListViewAdapter.notifyDataSetChanged();
            }
        });

    }

    //==========================================================
    private Integer type = 0;//0日1周2月
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void setHeaManaOnClickListener(TakeExerciseBean takeExerciseBean, Integer type) {
        setOnClickColor(type);
        takeExerciseBean.showData = true;
        tvHeaManageText.setText(takeExerciseBean.stepNumber + "");
        tvHeaManageQk.setText(takeExerciseBean.stepNumber * 0.8 + "");
        mListViewAdapter.notifyDataSetChanged();
    }


    private void setOnClickColor(Integer type) {
        switch (type) {
            case 0:
                tvManageDay1.setBackgroundResource(R.drawable.round_selected_item);
                tvManageDay2.setBackgroundResource(R.drawable.round_item);
                tvManageDay3.setBackgroundResource(R.drawable.round_item);
                tvManageDay1.setTextColor(Color.parseColor("#75c2ee"));
                tvManageDay2.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageDay3.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageText1.setTextColor(Color.parseColor("#75c2ee"));
                tvManageText2.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageText3.setTextColor(Color.parseColor("#0f0f0f"));
                break;
            case 1:
                tvManageDay1.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageDay2.setTextColor(Color.parseColor("#75c2ee"));
                tvManageDay3.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageDay1.setBackgroundResource(R.drawable.round_item);
                tvManageDay2.setBackgroundResource(R.drawable.round_selected_item);
                tvManageDay3.setBackgroundResource(R.drawable.round_item);
                tvManageText1.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageText2.setTextColor(Color.parseColor("#75c2ee"));
                tvManageText3.setTextColor(Color.parseColor("#0f0f0f"));
                break;
            case 2:
                tvManageDay1.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageDay2.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageDay3.setTextColor(Color.parseColor("#75c2ee"));
                tvManageDay1.setBackgroundResource(R.drawable.round_item);
                tvManageDay2.setBackgroundResource(R.drawable.round_item);
                tvManageDay3.setBackgroundResource(R.drawable.round_selected_item);
                tvManageText1.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageText2.setTextColor(Color.parseColor("#0f0f0f"));
                tvManageText3.setTextColor(Color.parseColor("#75c2ee"));
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
