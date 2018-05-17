package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class MyVideoPlayAdapter extends RecyclerView.Adapter<MyVideoPlayAdapter.ViewHolder> {

    private Context mContext = null;
    private int[] logos = {R.mipmap.video_one, R.mipmap.video_two, R.mipmap.video_three,
            R.mipmap.video_four};
    private String[] title = {"杨钰莹养生大讲堂:自己的保养秘籍", "养生减肥茶",
            "百岁老人的神奇养生方法解密", "养生保健品,醋泡黑豆的制作方法"};
    private String[] doctorName = {"蔡郑东", "白冲", "鲍南", "蔡三军"};

    public MyVideoPlayAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_video_play, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.videoLogo.setImageResource(logos[position]);
        holder.videoTitle.setText(title[position]);
        holder.uploadWriter.setText(doctorName[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.video_title)
        TextView videoTitle;
        @Bind(R.id.upload_writer)
        TextView uploadWriter;
        @Bind(R.id.video_logo)
        ImageView videoLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
