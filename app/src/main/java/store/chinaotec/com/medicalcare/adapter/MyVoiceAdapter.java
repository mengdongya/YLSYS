package store.chinaotec.com.medicalcare.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.PhotoShowActivity;
import store.chinaotec.com.medicalcare.javabean.MesageBean;
import store.chinaotec.com.medicalcare.utill.MyCommonUtil;

/**
 * Created by hxk on 2017/7/28 0028 16:23
 * 自主医疗页面沟通展示适配器
 */

public class MyVoiceAdapter extends RecyclerView.Adapter<MyVoiceAdapter.ViewHolder> {

    private Context mContext;
    private List<MesageBean> mList;
    private SharedPreferences sharedPreferences;

    public MyVoiceAdapter(Context context, List<MesageBean> list, SharedPreferences sharedPreferences) {
        this.mContext = context;
        this.mList = list;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_voice_mesage, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MesageBean mesageBean = mList.get(position);
        //交流信息的种类收到的 发送的 和照片
        int type = mesageBean.getType();
        if (type == MesageBean.SEND_TYPE) {  //发送的信息
            holder.linearReceive.setVisibility(View.GONE);
            holder.sendPhoto.setVisibility(View.GONE);
            holder.linearSend.setVisibility(View.VISIBLE);
            holder.sendText.setVisibility(View.VISIBLE);
            holder.sendText.setText(mesageBean.getContent().trim());
        } else if (type == MesageBean.RECEIVE_TYPE) {   //收到的信息
            holder.linearSend.setVisibility(View.GONE);
            holder.sendPhoto.setVisibility(View.GONE);
            holder.receiveText.setText(Html.fromHtml(mesageBean.getContent()));
            holder.linearReceive.setVisibility(View.VISIBLE);
        } else {  //发送的照片 if (type == MesageBean.PHOTO_TYPE)
            holder.linearReceive.setVisibility(View.GONE);
            holder.sendText.setVisibility(View.GONE);
            holder.linearSend.setVisibility(View.VISIBLE);
            holder.sendPhoto.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(mesageBean.getUri()).into(holder.sendPhoto);
            holder.sendPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //保存发送图片的uri路径
                    sharedPreferences.edit().putString("photo_show", mesageBean.getUri().toString()).commit();
                    mContext.startActivity(new Intent(mContext, PhotoShowActivity.class));
                }
            });
        }

        //用户的在线logo路径
        String logo = sharedPreferences.getString("logo", "");
        //加载用户logo发送用户的
        Glide.with(mContext).load(logo).placeholder(R.mipmap.self_mine).override(60, 60).into(holder.userLogoSend);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.receive_text)
        TextView receiveText;
        @Bind(R.id.linear_receive)
        LinearLayout linearReceive;
        @Bind(R.id.send_text)
        TextView sendText;
        @Bind(R.id.send_photo)
        ImageView sendPhoto;
        @Bind(R.id.user_logo_send)
        ImageView userLogoSend;
        @Bind(R.id.linear_send)
        LinearLayout linearSend;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
