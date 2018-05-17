package store.chinaotec.com.medicalcare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.RemindListActivity;
import store.chinaotec.com.medicalcare.activity.ValidateListActivity;
import store.chinaotec.com.medicalcare.javabean.LinkMessageNum;

/**
 * Created by HYY on 2018/3/12.
 */

public class MessageManageMentFragment extends Fragment {

    private static final String PAGE_NAME_KEY = "PAGE_NAME_KEY";
    @Bind(R.id.rl_fragment_message_manager_remind)
    RelativeLayout rlFragmentMessageManagerRemind;
    @Bind(R.id.rl_fragment_message_manager_validate)
    RelativeLayout rlFragmentMessageManagerValidate;
    @Bind(R.id.tv_notice_info)
    TextView tv_notice_info;
    @Bind(R.id.tv_add_friend)
    TextView addFriend;
    private LinkMessageNum.DataBean dataBean;

    public static MessageManageMentFragment getInstance(LinkMessageNum.DataBean dataBean) {
        Bundle args = new Bundle();
        args.putSerializable(PAGE_NAME_KEY, dataBean);
        MessageManageMentFragment pageFragment = new MessageManageMentFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBean = (LinkMessageNum.DataBean)getArguments().getSerializable(PAGE_NAME_KEY);
        View view = inflater.inflate(R.layout.fragment_message_manager, container, false);
        ButterKnife.bind(this,view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        if (dataBean != null){
            if (dataBean.getNoticeNum() > 0){
                addFriend.setText(dataBean.getNoticeTop().getBody());
            }
            if (dataBean.getRemindNum() > 0){
                tv_notice_info.setVisibility(View.VISIBLE);
            }else {
                tv_notice_info.setVisibility(View.GONE);
            }
        }
    }

    private void initData() {
        rlFragmentMessageManagerRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提醒消息
                startActivity(new Intent(getActivity(), RemindListActivity.class));
            }
        });

        rlFragmentMessageManagerValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证消息
                startActivity(new Intent(getActivity(), ValidateListActivity.class));
            }
        });
    }
}
