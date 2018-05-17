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
 * Created by Administrator on 2017/5/11 0011.
 * 在线资料下载适配器
 */

public class MyFileDwonAdapter extends RecyclerView.Adapter<MyFileDwonAdapter.ViewHolder> {
    private Context mContext = null;
    private String[] fileName = {"支气管舒张剂在儿童呼吸道常见疾病...", "儿童肺间质疾病诊断程序专家共识",
            "欧洲早产儿呼吸窘迫综合征管理指南", "全球哮喘防治创议新版指南解读方案..."};
    private String[] names = {"许永岚", "钱亚凤", "孔良超", "郭秀晶"};

    public MyFileDwonAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_file_online, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fileName.setText(fileName[position]);
        holder.writerName.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.file_name)
        TextView fileName;
        @Bind(R.id.writer_logo)
        ImageView writerLogo;
        @Bind(R.id.writer_name)
        TextView writerName;
        @Bind(R.id.up_time)
        TextView upTime;
        @Bind(R.id.down_time)
        TextView downTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
