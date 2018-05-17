package store.chinaotec.com.medicalcare.fragment.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by wjc on 2018/3/1 0001.
 */

public class MedicalBookDetailFragment extends Fragment {

    @Bind(R.id.tv_medical_book_detail)
    TextView tvDetail;
    private String detail;

    public static MedicalBookDetailFragment newInstance(String detail) {
        MedicalBookDetailFragment fragment = new MedicalBookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("detail", detail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        detail = getArguments().getString("detail");
        tvDetail.setText(detail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
