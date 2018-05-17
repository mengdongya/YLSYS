package store.chinaotec.com.medicalcare.fragment.medicalcare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyPatientForumAdapter;

/**
 * A simple {@link Fragment} subclass.
 * 医患论坛页面
 */
public class PatientForumFragment extends Fragment {


    @Bind(R.id.patient_forum_recycleview)
    RecyclerView patientForumRecycleview;

    public PatientForumFragment() {
        // Required empty public constructor
    }

    public static PatientForumFragment instance() {
        Bundle bundle = new Bundle();
        PatientForumFragment patientForumFragment = new PatientForumFragment();
        patientForumFragment.setArguments(bundle);
        return patientForumFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_patient_forum, container, false);
        ButterKnife.bind(this, inflate);
        initListener();
        return inflate;
    }

    private void initListener() {
        patientForumRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        patientForumRecycleview.setAdapter(new MyPatientForumAdapter(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
