package store.chinaotec.com.medicalcare.fragment.medicalcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.DoctorForumReplyActivity;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.dialog.DoctorForumReplyDialog;
import store.chinaotec.com.medicalcare.dialog.ForumPicBannerPopWindow;
import store.chinaotec.com.medicalcare.javabean.MedicalForumBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.view.MyScrollGridView;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.TimeUtil;
import store.chinaotec.com.medicalcare.view.GlideCornerTransform;

/**
 * Created by wjc on 2017/11/13 0013.
 * 专科厅
 */
public class DoctorSpecializedHallFragment extends Fragment {

    @Bind(R.id.rv_hall_list)
    LRecyclerView recyclerView;
    private List<MedicalForumBean.ForumBean> data;
    private int pageIndex = 1;
    private HashMap<String, String> mParams;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int likes;
    private String sid;
    private ForumPicBannerPopWindow forumPicBannerPopWindow;
    private boolean mHandlePress = false;
    private int classId;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.REFRESH_FORUM)) {
                pageIndex = 1;
                initData();
            }
        }

    };
    private IntentFilter myIntentFilter;

    public static DoctorSpecializedHallFragment newInstance(int classId) {
        Bundle bundle = new Bundle();
        bundle.putInt("classId", classId);
        DoctorSpecializedHallFragment fragment = new DoctorSpecializedHallFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        classId = getArguments().getInt("classId");
        View inflate = inflater.inflate(R.layout.fragment_doctor_special_hall, container, false);
        ButterKnife.bind(this, inflate);
        initView();
        initData();
        return inflate;
    }

    private void initView() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");

        myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.REFRESH_FORUM);
        //注册广播
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);

        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyRecyclerAdapter());
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
        String url = MyUrl.DOCTOR_FORUM_LIST;
        mParams.put("classId", classId + "");
        mParams.put("sid", sid);
        mParams.put("pageIndex", pageIndex + "");
        mParams.put("pageSize", "10");
        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                MedicalForumBean medicalForumBean = JSON.parseObject(response, MedicalForumBean.class);
                if ("0".equals(medicalForumBean.getResponseCode())) {
                    if (pageIndex == 1) {
                        data.clear();
                    }
                    data.addAll(medicalForumBean.getData());
                    lRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), medicalForumBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_medical_forum, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            MedicalForumBean.ForumBean forumBean = data.get(position);
            Glide.with(getActivity()).load(forumBean.getMemberIcon()).transform(new GlideCornerTransform(
                    getActivity(),10)).placeholder(R.mipmap.icon_default_title).into(holder.ivForumImg);
            holder.tvForumNikeName.setText(forumBean.getNickName());
            holder.tvForumTime.setText(TimeUtil.getyMHm(forumBean.getCreateTime()));
            holder.tvForumBody.setText(forumBean.getBody());
            holder.tvForumLikeNumber.setText(forumBean.getLikeCount());
            holder.tvForumLikeNumber.setTextColor(!"0".equals(forumBean.getIsLikes()) ? getResources().getColor(R.color.black1) :
                    getResources().getColor(R.color.forum_like_gray));
            if (!"".equals(forumBean.getImageUrl())){
                holder.gv_forum_pic.setVisibility(View.VISIBLE);
                final String[] icons = forumBean.getImageUrl().split(",");
                List<String> list = Arrays.asList(icons);
                GridAdapter adapter = new GridAdapter(getActivity(),list);
                holder.gv_forum_pic.setAdapter(adapter);
                holder.gv_forum_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        forumPicBannerPopWindow = new ForumPicBannerPopWindow(getActivity(),
                                icons, position);
                        forumPicBannerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                forumPicBannerPopWindow.backgroundAlpha(1f);
                            }
                        });
                        forumPicBannerPopWindow.show(getActivity().findViewById(R.id.ll_forum));
                    }
                });
            }else {
                holder.gv_forum_pic.setVisibility(View.GONE);
            }

            if (forumBean.getBbsReplyMedicalDtos().size() > 2){
                holder.tvForumMore.setVisibility(View.VISIBLE);
                holder.tvForumMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),DoctorForumReplyActivity.class);
                        intent.putExtra("topicId",data.get(position).getTopicId());
                        startActivity(intent);
                    }
                });
                holder.tvForumMore.setText("更多（"+forumBean.getReplyCount()+"条评论） ");
            }else {
                holder.tvForumMore.setVisibility(View.GONE);
            }
            holder.ivForumLike.setSelected(!"0".equals(forumBean.getIsLikes()) ? true : false);
            holder.ivForumLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    forumLikeRequest(position,holder.ivForumLike,holder.tvForumLikeNumber);
                }
            });
            holder.llForumWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DoctorForumReplyDialog doctorForumReplyDialog = new DoctorForumReplyDialog(getActivity(), data.get(position));
                    doctorForumReplyDialog.show(getChildFragmentManager(), "forumComment");
                }
            });
            if (forumBean.getBbsReplyMedicalDtos().size() > 0){
                holder.llForumReply.setVisibility(View.VISIBLE);
                holder.llForumReply.removeAllViews();
                for (int i = 0;i < forumBean.getBbsReplyMedicalDtos().size();i++){
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_forum_reply, null);
                    ((TextView) view.findViewById(R.id.tv_forum_reply_name)).setText(forumBean.getBbsReplyMedicalDtos().get(i).getMemberName()+":");
                    ((TextView) view.findViewById(R.id.tv_forum_reply_body)).setText(forumBean.getBbsReplyMedicalDtos().get(i).getBody());
                    holder.llForumReply.addView(view);
                }
            }else {
                holder.llForumReply.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_forum_img)
            ImageView ivForumImg;
            @Bind(R.id.tv_forum_nike_name)
            TextView tvForumNikeName;
            @Bind(R.id.tv_forum_time)
            TextView tvForumTime;
            @Bind(R.id.tv_forum_body)
            TextView tvForumBody;
            @Bind(R.id.tv_forum_like_number)
            TextView tvForumLikeNumber;
            @Bind(R.id.iv_forum_like)
            ImageView ivForumLike;
            @Bind(R.id.ll_forum_write)
            LinearLayout llForumWrite;
            @Bind(R.id.ll_forum_reply)
            LinearLayout llForumReply;
            @Bind(R.id.tv_forum_more)
            TextView tvForumMore;
            @Bind(R.id.gv_forum_pic)
            MyScrollGridView gv_forum_pic;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    class GridAdapter extends BaseAdapter {
        private Context context;
        private List<String> pics;

        public GridAdapter(Context context, List<String> pics) {
            this.context = context;
            this.pics = pics;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_forum_list_pic, null);
                holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_item_pic);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String url = pics.get(position);
            Glide.with(getActivity()).load(url).placeholder(R.drawable.error_img).into(holder.iv_pic);
            return convertView;
        }

        @Override
        public int getCount() {
            return pics.size();
        }

        @Override
        public Object getItem(int position) {
            return pics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            ImageView iv_pic;
        }

    }

    private void forumLikeRequest(final int position, final ImageView ivForumLike, final TextView tvForumLikeNumber) {
        if ("".equals(sid)) {
            startActivityForResult(new Intent(MyApp.getContext(), LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
            return;
        }
        mParams.clear();
        String url = MyUrl.FORUM_THUMB_UP;
        mParams.put("topicId",data.get(position).getTopicId());
        mParams.put("sid",sid);
        mParams.put("type",(ivForumLike.isSelected() ? 1 : 0) +"");
        mParams.put("source","1");

        OkHttpUtils.post().url(url).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean result = JSON.parseObject(response, ResultBean.class);
                if ("0".equals(result.responseCode)){
                    Toast.makeText(getActivity(), ivForumLike.isSelected() ? "取消点赞" : "点赞成功", Toast.LENGTH_SHORT).show();
                    if(ivForumLike.isSelected()){
                        likes = Integer.parseInt(data.get(position).getLikeCount());
                        likes--;
                        tvForumLikeNumber.setText(likes+"");
                        tvForumLikeNumber.setTextColor(getResources().getColor(R.color.forum_like_gray));
                        data.get(position).setLikeCount(likes+"");
                    }else {
                        likes = Integer.parseInt(data.get(position).getLikeCount());
                        likes++;
                        tvForumLikeNumber.setText(likes+"");
                        tvForumLikeNumber.setTextColor(getResources().getColor(R.color.black1));
                        data.get(position).setLikeCount(likes+"");
                    }
                    ivForumLike.setSelected(!ivForumLike.isSelected());

                }else {
                    Toast.makeText(getActivity(), result.msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResourseSum.LOGIN_RESPONSE && resultCode == getActivity().RESULT_OK){
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    //定义回调函数及变量
    protected BackHandlerInterface backHandlerInterface;

    public interface BackHandlerInterface{
        public void setSelectedFragment(DoctorSpecializedHallFragment backHandledFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)){
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        }else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }

    /** 回退键监听 */
    public boolean onBackPress(){
//        if (!mHandlePress){
            if (forumPicBannerPopWindow != null && forumPicBannerPopWindow.isShowing()) {
                forumPicBannerPopWindow.dismiss();
//                mHandlePress = true;
                return true;
            }else {
                return false;
            }
//        }
//        return false;
    }

}
