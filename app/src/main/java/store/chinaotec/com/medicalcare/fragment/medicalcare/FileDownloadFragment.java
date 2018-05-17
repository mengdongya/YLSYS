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
import store.chinaotec.com.medicalcare.activity.MedicalElectronicBooksActivity;
import store.chinaotec.com.medicalcare.activity.ScienceInformationActivity;

/**
 * A simple {@link Fragment} subclass.
 * 资料下载页面
 */
public class FileDownloadFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.iv_electron_book)
    ImageView ivElectronBook;

   /* @Bind(R.id.file_online_recycleview)
    RecyclerView fileOnlineRecycleview;*/

    public FileDownloadFragment() {
        // Required empty public constructor
    }

    public static FileDownloadFragment instance() {
        Bundle bundle = new Bundle();
        FileDownloadFragment fileDownloadFragment = new FileDownloadFragment();
        fileDownloadFragment.setArguments(bundle);
        return fileDownloadFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file_download, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
      /*  fileOnlineRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fileOnlineRecycleview.setAdapter(new MyFileDwonAdapter(getContext()));*/

        ivElectronBook.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_electron_book:
                intent.setClass(getActivity(), MedicalElectronicBooksActivity.class);
                intent.putExtra("type", "0");
                intent.putExtra("title", "医学电子图书");
                break;
        }
        startActivity(intent);
    }
}

