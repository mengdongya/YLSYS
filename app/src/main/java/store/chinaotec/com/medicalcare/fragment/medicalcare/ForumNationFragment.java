package store.chinaotec.com.medicalcare.fragment.medicalcare;


import android.content.Context;
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
import store.chinaotec.com.medicalcare.activity.DoctorDiseaseActivity;
import store.chinaotec.com.medicalcare.activity.DoctorPatientForumActivity;
import store.chinaotec.com.medicalcare.activity.DoctorSpecializedHallActivity;
import store.chinaotec.com.medicalcare.utill.ResourseSum;

/**
 * A simple {@link Fragment} subclass.
 * 论坛导航页面
 */
public class ForumNationFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.iv_forum_nation)
    ImageView ivForumNation;
    @Bind(R.id.iv_special_hall)
    ImageView ivSpecialHall;
    @Bind(R.id.iv_sickness)
    ImageView ivSickness;
    @Bind(R.id.iv_doctor_friend)
    ImageView ivDoctorFriend;
    private String sid;

    public ForumNationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum_nation, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        sid = getActivity().getSharedPreferences(ResourseSum.Medica_SP, Context.MODE_PRIVATE).getString("sid", "");
        ivForumNation.setOnClickListener(this);
        ivSpecialHall.setOnClickListener(this);
        ivSickness.setOnClickListener(this);
        ivDoctorFriend.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sid = getActivity().getSharedPreferences(ResourseSum.Medica_SP, Context.MODE_PRIVATE).getString("sid", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_forum_nation:
                Intent intent = new Intent(getActivity(), DoctorPatientForumActivity.class);
                intent.putExtra("baseClassId", 3);
                startActivity(intent);
                break;
            case R.id.iv_special_hall:
                startActivity(new Intent(getActivity(), DoctorSpecializedHallActivity.class));
                break;
            case R.id.iv_sickness:
                startActivity(new Intent(getActivity(), DoctorDiseaseActivity.class));
                break;
            case R.id.iv_doctor_friend:
                Intent mIntent = new Intent(getActivity(), DoctorPatientForumActivity.class);
                mIntent.putExtra("baseClassId", 6);
                startActivity(mIntent);
                break;
        }
    }
}
