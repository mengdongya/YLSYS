package store.chinaotec.com.medicalcare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import store.chinaotec.com.medicalcare.activity.ChronicDiseaseManagerActivity2;
import store.chinaotec.com.medicalcare.activity.RemindManageActivity;
import store.chinaotec.com.medicalcare.dialog.LoadingView;
import store.chinaotec.com.medicalcare.javabean.LinkManBean;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by HYY on 2018/3/12.
 * 联系人
 */

public class LinkManagerFragment extends Fragment {
    @Bind(R.id.go_fragment_link_manager_list)
    LRecyclerView recyclerView;
    private static final String PAGE_NAME_KEY = "PAGE_NAME_KEY";
    private int pageIndex = 1;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private LinkManagerAdapter adapter;
    private String sid;
    private List<LinkManBean.DataBean.RefMemberInfos> dataList;
    private LoadingView loadingDialog;

    public static LinkManagerFragment getInstance(String pageName) {
        Bundle args = new Bundle();
        args.putString(PAGE_NAME_KEY, pageName);
        LinkManagerFragment pageFragment = new LinkManagerFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_manager, container, false);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        loadingDialog = new LoadingView(getActivity(), R.style.ProgressDialog);
        dataList = new ArrayList<>();
        adapter = new LinkManagerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getResponseData();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                getResponseData();
            }
        });

        getResponseData();

    }

    private void getResponseData() {
        loadingDialog.show();
        OkHttpUtils.post().url(MyUrl.GET_REF_MEMBER).addParams("sid", sid).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                LinkManBean linkManBean = JSON.parseObject(response, LinkManBean.class);
                if ("0".equals(linkManBean.getResponseCode())) {
                    loadingDialog.dismiss();
                    if (pageIndex == 1) {
                        dataList.clear();
                    }
                    dataList.addAll(linkManBean.getData().getRefMemberInfos());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    class LinkManagerAdapter extends RecyclerView.Adapter<LinkManagerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_link_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final LinkManBean.DataBean.RefMemberInfos refMemberInfos = dataList.get(position);
            Glide.with(getActivity()).load(refMemberInfos.getImg()).into(holder.ivItemLinkIcon);
            holder.tvItemLinkName.setText(refMemberInfos.getNickName());
//            holder.tvItemLinkAge.setText();
            holder.tvItemLinkPhone.setText(refMemberInfos.getPhone());
            holder.tvItemLinkSee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //查看慢性病
                    Intent intent = new Intent(getActivity(), ChronicDiseaseManagerActivity2.class);
                    intent.putExtra("refMemberId",refMemberInfos.getMemberId()+"");
                    startActivity(intent);
                }
            });

            holder.tvItemLinkSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //提醒
                    Intent intent = new Intent(new Intent(getActivity(), RemindManageActivity.class));
                    intent.putExtra("refMemberId",refMemberInfos.getMemberId());
                    startActivity(intent);
                }
            });
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
            @Bind(R.id.tv_item_link_see)
            TextView tvItemLinkSee;
            @Bind(R.id.tv_item_link_song)
            TextView tvItemLinkSong;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
