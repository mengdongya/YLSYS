package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.dialog.LoadingView;
import store.chinaotec.com.medicalcare.javabean.LinkMessageInfo;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.TimeUtil;

/**
 * Created by wjc on 2017/12/20 0020.
 * 提醒消息列表
 */
public class RemindListActivity extends BaseActivity {
    private TextView tv_include_title_view;
    private Context mContext = RemindListActivity.this;
    @Bind(R.id.rv_activity_remind_list)
    LRecyclerView recyclerView;
    private RemindListAdapter adapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int pageIndex = 1;
    private String sid;
    private List<LinkMessageInfo.DataBean.RefMemberInfoList> dataList;
    private LoadingView loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_list);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("消息提醒");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        loadingDialog = new LoadingView(this, R.style.ProgressDialog);
        dataList = new ArrayList<>();
        adapter = new RemindListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getMessage();

            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                getMessage();
            }
        });

        getMessage();
        getRead();
    }

    private void getMessage() {
        loadingDialog.show();
        OkHttpUtils.post().url(MyUrl.GET_REF_MESSAGE).addParams("sid", sid).addParams("type", "2").
                addParams("pageIndex", pageIndex + "").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                LinkMessageInfo messageInfo = JSON.parseObject(response, LinkMessageInfo.class);
                if ("0".equals(messageInfo.getResponseCode())) {
                    loadingDialog.dismiss();
                    if (pageIndex == 1) {
                        dataList.clear();
                    }
                    dataList.addAll(messageInfo.getData().getDataList());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                loadingDialog.dismiss();
            }

        });
    }

    private void getRead() {
        OkHttpUtils.post().url(MyUrl.GET_MESSAGE_READ).addParams("sid", sid).addParams("type", "2").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                JSON.parseObject(response, ResultBean.class);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    class RemindListAdapter extends RecyclerView.Adapter<RemindListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_remind, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            LinkMessageInfo.DataBean.RefMemberInfoList infoList = dataList.get(position);
            Glide.with(mContext).load(infoList.getMemberIcon()).into(holder.ivItemLinkIcon);
            holder.tvItemLinkName.setText("姓名:"+infoList.getMemberNickName());
            holder.tvItemLinkPhone.setText(infoList.getBody());
            holder.tvItemLinkAge.setText(TimeUtil.getDate(infoList.getCreateTime()));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_item_link_icon)
            ImageView ivItemLinkIcon;
            @Bind(R.id.tv_item_link_name)
            TextView tvItemLinkName;
            @Bind(R.id.tv_item_link_phone)
            TextView tvItemLinkPhone;
            @Bind(R.id.tv_item_link_age)
            TextView tvItemLinkAge;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
