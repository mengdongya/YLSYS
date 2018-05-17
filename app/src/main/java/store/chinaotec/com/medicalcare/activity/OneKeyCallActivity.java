package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.lzy.okgo.OkGo;
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
import store.chinaotec.com.medicalcare.adapter.MyOneKeyCallAdapter;
import store.chinaotec.com.medicalcare.dialog.DeleteDoctorDialog;
import store.chinaotec.com.medicalcare.javabean.OneKeyPersBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 一键呼叫模块首页
 */
public class OneKeyCallActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.add_one_call_switch)
    TextView addOneCallSwitch;
    @Bind(R.id.contacts_recycleview)
    LRecyclerView recyclerView;
    TextView oneKey120;
    TextView oneKey110;
    TextView oneKey119;

    private List<OneKeyPersBean.DataBean.MemberCalloutListBean> data;
    private String saveSid;
    private HashMap<String, String> mParams;
    private int pageIndex = 1;
    private LRecyclerViewAdapter lRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_call);
        ButterKnife.bind(this);
        initView();
        iniListener();
    }

    private void initView() {
        saveSid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new MyOneKeyCallAdapter(this, data));
        recyclerView.setAdapter(lRecyclerViewAdapter);
        initHeader();
        recyclerView.setLoadMoreEnabled(true);

        if (!"".equals(saveSid)){

            recyclerView.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pageIndex = 1;
                    getContactData();
                }
            });
            recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pageIndex++;
                    getContactData();
                }
            });
            getContactData();
        }
    }

    private void initHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.header_contact, null);
        oneKey120 = (TextView) header.findViewById(R.id.one_key_120);
        oneKey110 = (TextView) header.findViewById(R.id.one_key_110);
        oneKey119 = (TextView) header.findViewById(R.id.one_key_119);
        lRecyclerViewAdapter.addHeaderView(header);
    }

    private void iniListener() {
        addOneCallSwitch.setOnClickListener(this);
        oneKey120.setOnClickListener(this);
        oneKey110.setOnClickListener(this);
        oneKey119.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private void getContactData() {

        //一键呼叫联系人数据获取并展示
        mParams.clear();
        mParams.put("sid",saveSid);
        mParams.put("pageIndex",pageIndex+"");
        mParams.put("type","1");

        OkHttpUtils.post().url(MyUrl.CONTACTS_LIST).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                OneKeyPersBean oneKeyPersBean = JSONObject.parseObject(response, OneKeyPersBean.class);
                if ("0".equals(oneKeyPersBean.getResponseCode())){
                    if (pageIndex == 1){
                        data.clear();
                    }
                    data.addAll(oneKeyPersBean.getData().getMemberCalloutList());
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
                Toast.makeText(OneKeyCallActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //一键呼叫120
            case R.id.one_key_120:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "120")));
                break;
            //一键呼叫110
            case R.id.one_key_110:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "110")));
                break;
            case R.id.one_key_119:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "119")));
                break;
            case R.id.add_one_call_switch:
                if ("".equals(saveSid)){
                    startActivityForResult(new Intent(this,LoginActivity.class),ResourseSum.LOGIN_RESPONSE);
                    return;
                }
                ResourseSum.TURN_TO_ADD_CONTACT = 1;
                startActivityForResult(new Intent(this,ContaAddActivity.class),ResourseSum.TURN_TO_ADD_CONTACT);
                break;
        }
    }

    public class MyOneKeyCallAdapter extends RecyclerView.Adapter<MyOneKeyCallAdapter.ViewHolder>{
        private Context mContext = null;
        private List<OneKeyPersBean.DataBean.MemberCalloutListBean> calloutList = null;

        public MyOneKeyCallAdapter(Context context, List<OneKeyPersBean.DataBean.MemberCalloutListBean> memberCalloutList) {
            this.mContext = context;
            this.calloutList = memberCalloutList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_contacts, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.contactName.setText(calloutList.get(position).getCalloutName());
            holder.contactEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    turnToAdd(calloutList.get(position));
                }
            });
            holder.contactDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteContact(calloutList.get(position).getMemberCalloutId());
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + calloutList.get(position).getTelephone())));
                }
            });
        }

        @Override
        public int getItemCount() {
            return calloutList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.contact_name)
            TextView contactName;
            @Bind(R.id.tv_contact_edit)
            TextView contactEdit;
            @Bind(R.id.tv_contact_delete)
            TextView contactDelete;
            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    private void deleteContact(final int memberCalloutId) {
        DeleteDoctorDialog dialog = new DeleteDoctorDialog();
        dialog.show(getSupportFragmentManager(), "deleteContact");
        dialog.setDeleteDoctor(new DeleteDoctorDialog.DeleteDoctorListener() {
            @Override
            public void deleteDoctor() {
                deleteCurrent(memberCalloutId);
            }
        });
    }

    private void deleteCurrent(int memberCalloutId) {
        OkHttpUtils.post().url(MyUrl.CONTACTS_DELETE).addParams("memberCalloutId",memberCalloutId+"").build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(resultBean.responseCode)){
                    Toast.makeText(OneKeyCallActivity.this, "联系人删除成功", Toast.LENGTH_SHORT).show();
                    getContactData();
                    pageIndex = 1;
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void turnToAdd(OneKeyPersBean.DataBean.MemberCalloutListBean memberCalloutListBean) {
        Intent intent = new Intent(this, ContaAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact",memberCalloutListBean);
        intent.putExtras(bundle);
        startActivityForResult(intent,ResourseSum.TURN_TO_ADD_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ResourseSum.LOGIN_RESPONSE){
            saveSid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        }
        if (requestCode == ResourseSum.TURN_TO_ADD_CONTACT){
            pageIndex = 1;
            getContactData();

        }
    }
}
