package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.MedicalForumReplyBean;
import store.chinaotec.com.medicalcare.utill.TimeUtil;

/**
 * Created by wjc on 2017/10/27 0027.
 * 论坛回复列表
 */
public class DoctorForumReplyActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.rv_forum_reply_list)
    LRecyclerView recyclerView;
    private List<MedicalForumReplyBean.ForumReplyBean> data;
    private int pageIndex = 1;
    private HashMap<String, String> mParams;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private String topicId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicId = getIntent().getStringExtra("topicId");
        setContentView(R.layout.activity_doctor_forum_reply);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyRecyclerReplyAdapter());
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                initData();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                initData();
            }
        });
    }

    private void initData() {
        mParams.clear();
        String url = MyUrl.FORUM_FIND_REPLY;
        mParams.put("classId", "13");
        mParams.put("topicId", topicId);
        mParams.put("pageIndex", pageIndex + "");
        mParams.put("pageSize", "10");
        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalForumReplyBean medicalForumReplyBean = JSON.parseObject(response, MedicalForumReplyBean.class);
                if ("0".equals(medicalForumReplyBean.getResponseCode())) {
                    if (pageIndex == 1) {
                        data.clear();
                    }
                    data.addAll(medicalForumReplyBean.getData());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DoctorForumReplyActivity.this, medicalForumReplyBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(DoctorForumReplyActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class MyRecyclerReplyAdapter extends RecyclerView.Adapter<MyRecyclerReplyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(DoctorForumReplyActivity.this).inflate(R.layout.item_medical_forum_reply, parent, false));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            MedicalForumReplyBean.ForumReplyBean forumReplyBean = data.get(position);
            holder.tvForumReplyName.setText(forumReplyBean.getMemberName());
            holder.tvForumReplyTime.setText(TimeUtil.getYMD(forumReplyBean.getCreateTime()));
            holder.tvForumReplyBody.setText(forumReplyBean.getBody());
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_forum_reply_name)
            TextView tvForumReplyName;
            @Bind(R.id.tv_forum_reply_time)
            TextView tvForumReplyTime;
            @Bind(R.id.tv_forum_reply_body)
            TextView tvForumReplyBody;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
