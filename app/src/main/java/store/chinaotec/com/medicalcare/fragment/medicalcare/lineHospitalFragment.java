package store.chinaotec.com.medicalcare.fragment.medicalcare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import store.chinaotec.com.medicalcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class lineHospitalFragment extends Fragment {


    public lineHospitalFragment() {
        // Required empty public constructor
    }

    public static lineHospitalFragment instance() {
        Bundle bundle = new Bundle();
        lineHospitalFragment lineHospitalFragment = new lineHospitalFragment();
        lineHospitalFragment.setArguments(bundle);
        return lineHospitalFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_hospital, container, false);
    }

}
