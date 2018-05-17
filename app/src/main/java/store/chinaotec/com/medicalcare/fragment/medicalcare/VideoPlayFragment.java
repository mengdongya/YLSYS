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
import store.chinaotec.com.medicalcare.adapter.MyVideoPlayAdapter;

/**
 * A simple {@link Fragment} subclass.
 * 视频播放叶面
 */
public class VideoPlayFragment extends Fragment {


    @Bind(R.id.video_play_recycleview)
    RecyclerView videoPlayRecycleview;

    public VideoPlayFragment() {
        // Required empty public constructor
    }

    public static VideoPlayFragment instance() {
        Bundle bundle = new Bundle();
        VideoPlayFragment videoPlayFragment = new VideoPlayFragment();
        videoPlayFragment.setArguments(bundle);
        return videoPlayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_video_play, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, inflate);
        initListener();
        return inflate;
    }

    private void initListener() {
        videoPlayRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        videoPlayRecycleview.setAdapter(new MyVideoPlayAdapter(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
