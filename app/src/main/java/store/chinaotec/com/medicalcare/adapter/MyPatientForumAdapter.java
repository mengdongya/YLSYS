package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class MyPatientForumAdapter extends RecyclerView.Adapter<MyPatientForumAdapter.ViewHolder> {
    private Context mContext = null;
    private String[] title = {"如何防止胸闷气短,有什么比较好的方法?", "如何防止腰酸腿疼,有什么比较好的方法?",
            "如何防止类风湿关节炎,有什么比较好的方法?", "如何防止脑膜炎,有什么比较好的方法?", "如何防止高血压,有什么比较好的方法?"};
    private String[] content = {"注意室内通风和卫生，保证新鲜空气流通。再多参加锻炼，多运动增强身体抵抗力。",
            "跟腱位于足跟的后上方。患者坐在床上，用两手拇指和食指的中节稍用力分别捏两侧跟腱。",
            "饮食上应选择容易消化(消化食品)的食物，少吃辛辣、油(油食品)腻及冰冷的食物。",
            "脑膜炎是一种可以传染的疾病，尽量不要去集会或者是其他公共场合，以免发生流行性脑膜炎的情况。",
            "改善生活行为：适用于所有高血压患者,包括服用降压药物治疗的患者。"};
    private int[] logos = {R.mipmap.writer_logo_two, R.mipmap.writer_logo_one};
    private String[] doctorName = {"蔡郑东", "白冲", "鲍南", "蔡三军", "曹子昂"};

    public MyPatientForumAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_patient_forum, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pstTitle.setText(title[position]);
        holder.pstContent.setText(content[position]);
        holder.writereName.setText(doctorName[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView writereLogo;
        private final TextView writereName;
        private final TextView pstTitle;
        private final TextView pstContent;

        public ViewHolder(View itemView) {
            super(itemView);
            writereLogo = (ImageView) itemView.findViewById(R.id.patient__foum_writer_logo);
            writereName = (TextView) itemView.findViewById(R.id.patient__foum_writer_name);
            pstTitle = (TextView) itemView.findViewById(R.id.patient__foum_pst_title);
            pstContent = (TextView) itemView.findViewById(R.id.patient__foum_pst_content);
        }
    }
}
