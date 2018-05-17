package store.chinaotec.com.medicalcare.fragment.medicalcare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.SelfCheckActivity;

/**
 * A simple {@link Fragment} subclass.
 * 实用工具页面
 */
public class UtitiesFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.iv_self_check)
    ImageView ivSelfCheck;

    public UtitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utities, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ivSelfCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_self_check:
                Intent intent = new Intent(getActivity(), SelfCheckActivity.class);
                intent.putExtra("title","个人自检方法");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
