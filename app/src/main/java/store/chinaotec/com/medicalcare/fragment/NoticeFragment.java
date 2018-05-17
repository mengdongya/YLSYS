package store.chinaotec.com.medicalcare.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.AddRemindsActivity;
import store.chinaotec.com.medicalcare.activity.MedRemindLiveActivity;
import store.chinaotec.com.medicalcare.activity.MedicReminNoActivity;
import store.chinaotec.com.medicalcare.activity.WeatherActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.relative_apoint_regis)
    RelativeLayout relativeApointRegis;
    @Bind(R.id.relative_weather_remind)
    RelativeLayout relativeWeatherRemind;
    @Bind(R.id.relative_child_care)
    RelativeLayout relativeChildCare;
    @Bind(R.id.relative_logist_infor)
    RelativeLayout relativeLogistInfor;
    @Bind(R.id.relative_comunity_activity)
    RelativeLayout relativeComunityActivity;
    @Bind(R.id.relative_more)
    RelativeLayout relativeMore;
    @Bind(R.id.relative_medica_remind)
    RelativeLayout relativeMedicaRemind;

    public NoticeFragment() {
        // Required empty public constructor
    }

    public static NoticeFragment instance() {
        Bundle bundle = new Bundle();
        NoticeFragment noticeFragment = new NoticeFragment();
        noticeFragment.setArguments(bundle);
        return noticeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        relativeMedicaRemind.setOnClickListener(this);
        relativeComunityActivity.setOnClickListener(this);
        relativeMore.setOnClickListener(this);
        relativeWeatherRemind.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加体检提醒页面
            case R.id.relative_medica_remind:
                startActivity(new Intent(getContext(), AddRemindsActivity.class));
                break;
            //体检提醒页展示面
            case R.id.relative_comunity_activity:
                startActivity(new Intent(getContext(), MedRemindLiveActivity.class));
                break;
            //没有体检提醒展示页面
            case R.id.relative_more:
                startActivity(new Intent(getContext(), MedicReminNoActivity.class));
                break;
            case R.id.relative_weather_remind:
                startActivity(new Intent(getActivity(),WeatherActivity.class));
                break;
        }
    }
}
